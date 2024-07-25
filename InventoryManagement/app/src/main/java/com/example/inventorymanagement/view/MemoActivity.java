package com.example.inventorymanagement.view;

import static com.itextpdf.io.font.otf.LanguageTags.TODO;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inventorymanagement.R;
import com.example.inventorymanagement.adapter.SelectedItemRecycler;
import com.example.inventorymanagement.db.DatabaseHelper;
import com.example.inventorymanagement.models.CustomersInfoWithItems;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.models.SelectedItem;
import com.example.inventorymanagement.models.Stock;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class MemoActivity extends AppCompatActivity {
    /*
     * Declaration of all fields
     * */
    TextView invoiceNum, memoDate;
    EditText customerName, customerNumber;
    Button savePrintBtn, selectItemBtn, totalBtn;
    Bitmap headerImg, scaledBmp;
    Date date;
    DatabaseHelper databaseHelper;
    DateFormat dateFormat;
    List<SelectedItem> selectedItems;
    RecyclerView recyclerView;
    double showTotalBtn = 0;
    String invoiceNumber, customerNumberInString;
    BluetoothController bluetoothController;
    Toolbar memoToolbar;

    /*-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        bluetoothController = new BluetoothController(MemoActivity.this);
        memoToolbar = findViewById(R.id.memoToolBar);
        setSupportActionBar(memoToolbar);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, 1);
        }

        /*
         * Initialization of the fields
         * */
        customerName = findViewById(R.id.customerName);
        databaseHelper = DatabaseHelper.getDB(this);

        /*
         * Taking customer's mobile number
         * */
        customerNumber = findViewById(R.id.customerNumber);
        customerNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            public void afterTextChanged(Editable editable) {
                Random random = new Random();
                customerNumberInString = "" + ((int)(Integer.parseInt(customerNumber.getText().toString()) / 1000000));
                String invoiceNumber2 = customerNumberInString + (random.nextInt(10));
                invoiceNumber = invoiceNumber2;
                invoiceNum.setText(invoiceNumber2.toString());
            }
        });


        invoiceNum = findViewById(R.id.invoiceNum);
        memoDate = findViewById(R.id.memoDate);
        /*
         * Setting the date in the header of the memo
         * */
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        memoDate.setText(simpleDateFormat.format(cd.getTime()));

        /*
         * Setting the invoice number in the header of the memo
         * */
        savePrintBtn = findViewById(R.id.savePrintBtn);
        selectItemBtn = findViewById(R.id.selectItem);
        recyclerView = findViewById(R.id.customerSelectedItems);
        totalBtn = findViewById(R.id.totalBtn);

        selectedItems = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        /*
         * calling select item dialog
         * */
        selectItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customerName.getText().toString().isEmpty() || customerNumber.getText().toString().isEmpty()) {
                    if (customerName.getText().toString().isEmpty()) {
                        customerName.setError("Please Enter Customer Name");
                    }
                    if (customerNumber.getText().toString().isEmpty()) {
                        customerNumber.setError("Please Enter Customer number");
                    }
                } else {
                    selectItemDialogCreate();

                }
            }
        });

        /*
         * Getting permission to write the external storage
         * */
        savePrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customerName.getText().toString().isEmpty() || customerNumber.getText().toString().isEmpty() || selectedItems.isEmpty()) {
                    if (customerName.getText().toString().isEmpty()) {
                        customerName.setError("Enter Customer Name");
                    }
                    if (customerName.getText().toString().isEmpty()) {
                        customerNumber.setError("Enter Customer Number");
                    }
                    if (selectedItems.isEmpty()) {
                        Toast.makeText(MemoActivity.this, "Please select the item", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        long invoiceNumToSave = Integer.parseInt(customerNumberInString);
                        Date dateOfMemo = simpleDateFormat.parse(memoDate.getText().toString());
                        String customerNameStr = customerName.getText().toString();
                        String customerNumberStr = customerNumber.getText().toString();
                        double customerTotal = Double.parseDouble(totalBtn.getText().toString());

                        CustomersInfoWithItems customersInfoWithItems = new CustomersInfoWithItems(
                                customerNameStr,
                                customerNumberInString,
                                invoiceNumToSave,
                                cd.getTime(),
                                customerTotal
                        );

                        long cusId = databaseHelper.customersDao().insertCustomer(customersInfoWithItems);
                        for (int i = 0; i < selectedItems.size(); i++) {
                            selectedItems.get(i).setCustomerId(cusId);
                            selectedItems.get(i).setPurchasedDate(cd.getTime());
                            databaseHelper.selectedItemDao().insertSelectedItem(selectedItems.get(i));

                            Stock tempStock = databaseHelper.stockDao().retrieveAllAvailableProductByProductName(selectedItems.get(i).getItemName());

                            if (tempStock != null) {
                                int tempQuantity = selectedItems.get(i).getQuantity();
                                tempStock.setProductAvailable(tempStock.getProductAvailable() - tempQuantity);
                                databaseHelper.stockDao().updateItemInStock(tempStock);
                            }
                        }
                        createPdf(selectedItems, invoiceNumber, customerNameStr, customerNumberStr, MemoActivity.this);

                        bluetoothController.printBill(customerNameStr, customerNumberStr, selectedItems,dateOfMemo);

                    } catch (FileNotFoundException error) {
                        error.printStackTrace();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    customerName.setText("");
                    customerNumber.setText("");
                    invoiceNum.setText("");

                    selectedItems.clear();
                    totalBtn.setText("Total");
                    showTotalBtn = 0;
                    SelectedItemRecycler selectedItemRecycler = new SelectedItemRecycler(selectedItems, MemoActivity.this);
                    recyclerView.setAdapter(selectedItemRecycler);
                }
               }
        });
    }

    /*
     * Creating the dialog to show the selected item
     * */
    public void selectItemDialogCreate() {
        // For showing all the items in a list
        Dialog dialog = new Dialog(MemoActivity.this);

        // Select Item dialog which will take all info about name, quantity, price
        Dialog selectItemDialog = new Dialog(MemoActivity.this);

        // Initializing database

        // Retrieving all product's name
        LiveData<List<Products>> liveData = databaseHelper.productsDao().getAllProducts();

        // Initializing the item, quantity, price dialog
        selectItemDialog.setContentView(R.layout.purchased_item_dialog);
        calculateSize(selectItemDialog);

        TextView selectTV = selectItemDialog.findViewById(R.id.selectedProductNameTV);

        selectItemDialog.show();


        if (liveData != null) {
            selectTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    liveData.observe(MemoActivity.this, new Observer<List<Products>>() {
                        @Override
                        public void onChanged(List<Products> pro) {
                            dialog.setContentView(R.layout.drop_down_selected_product);
                            calculateSize(dialog);
                            dialog.show();

                            List<String> itemName = new ArrayList<>();

                            for (int i = 0; i < pro.size(); i++) {
                                itemName.add(pro.get(i).getProductName());
                            }


                            EditText itemSelected = dialog.findViewById(R.id.allItemsToBeSelected);
                            ListView listSelected = dialog.findViewById(R.id.listOfAllItemToBeSelected);

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MemoActivity.this,
                                    android.R.layout.simple_list_item_1, itemName);

                            listSelected.setAdapter(adapter);

                            itemSelected.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                                    adapter.getFilter().filter(charSequence);
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });

                            listSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    selectTV.setText(adapter.getItem(i));
                                    selectTV.setError(null);
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                }
            });
        }

        EditText quantityOfItem = selectItemDialog.findViewById(R.id.quantityOfSelectedProductETV);
        EditText priceOfItem = selectItemDialog.findViewById(R.id.priceOfSelectedItemETV);
        TextView totalPrice = selectItemDialog.findViewById(R.id.totalOfSelectedProductNameTV);
        Button cancelBtn = selectItemDialog.findViewById(R.id.cancelSelectedProductBTN);
        Button addBtn = selectItemDialog.findViewById(R.id.addSelectedProductBtn);

        quantityOfItem.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                calculateTotalPrice(quantityOfItem, priceOfItem, totalPrice, 1);
            }

            public void afterTextChanged(Editable editable) {
            }
        });

        priceOfItem.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                calculateTotalPrice(quantityOfItem, priceOfItem, totalPrice, 1);
            }

            public void afterTextChanged(Editable editable) {
            }
        });

        /*
         * Creating recycler view adapter for selected Items
         * */
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectTV.getText().toString().isEmpty() || quantityOfItem.getText().toString().isEmpty()
                        || priceOfItem.getText().toString().isEmpty()) {
                    if (selectTV.getText().toString().isEmpty()) {
                        selectTV.setError("Select the item");
                    }
                    if (quantityOfItem.getText().toString().isEmpty()) {
                        quantityOfItem.setError("Set the quantity");
                    }
                    if (priceOfItem.getText().toString().isEmpty()) {
                        priceOfItem.setError("Set the price");
                    }
                } else {
                    double tempTotal = Double.parseDouble(quantityOfItem.getText().toString()) * Double.parseDouble(priceOfItem.getText().toString());
                    showTotalBtn += tempTotal;
                    totalBtn.setText(showTotalBtn + "");

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                    Date tempDate;
                    try {
                        tempDate = simpleDateFormat.parse(memoDate.getText().toString());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    /*
                     * Getting data from different fields
                     * */
                    String nameTemp = selectTV.getText().toString();
                    int quantityTemp = Integer.parseInt(quantityOfItem.getText().toString());
                    double priceTemp = Double.parseDouble(priceOfItem.getText().toString());
                    boolean isSame = false;

                    if (!selectedItems.isEmpty()) {
                        for (int i = 0; i < selectedItems.size(); i++) {
                            if (selectedItems.get(i).getItemName().equalsIgnoreCase(nameTemp)) {
                                selectedItems.get(i).setQuantity(selectedItems.get(i).getQuantity() + quantityTemp);
                                selectedItems.get(i).setTotal(selectedItems.get(i).getTotal() + tempTotal);
                                isSame = true;
                                break;
                            }
                        }
                    }
                    if (!isSame) {
                        selectedItems.add(new SelectedItem(
                                nameTemp,
                                quantityTemp,
                                priceTemp,
                                tempTotal,
                                Long.parseLong(invoiceNum.getText().toString()),
                                tempDate
                        ));
                    }

                    SelectedItemRecycler selectedItemRecycler = new SelectedItemRecycler(selectedItems, MemoActivity.this);
                    recyclerView.setAdapter(selectedItemRecycler);
                    selectItemDialog.dismiss();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectItemDialog.dismiss();
            }
        });
    }

    /*
     * calculate the total price
     * */
    public void calculateTotalPrice(EditText quantityOfItem, EditText priceOfItem, TextView totalPrice, int makeZero) {
        String priceStr = priceOfItem.getText().toString();
        String quantityStr = quantityOfItem.getText().toString();

        double price = priceStr.isEmpty() ? 0 : Double.parseDouble(priceStr);
        double quantity = quantityStr.isEmpty() ? 0 : Double.parseDouble(quantityStr);

        totalPrice.setText((price * quantity) + "");

    }

    public void calculateSize(Dialog itemDialog) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        int dialogWidth = (int) (screenWidth * .9);
        int dialogHeight = (int) (screenHeight * .62);

        itemDialog.getWindow().setLayout(dialogWidth, dialogHeight);
    }


    /*
     * Create the create pdf file to show the memo
     * */

    public void createPdf(List<SelectedItem> itemSolds, String invoiceNumber, String customerName, String customerNumber, Context context) throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        String pdfFileName = invoiceNumber + ".pdf";
        File file = new File(pdfPath, pdfFileName);
        OutputStream outputStream = new FileOutputStream(file);

        Date date = new Date();

        if (itemSolds.isEmpty()) {
            Toast.makeText(context, "Some field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        float pageWidthMM = 150f; // 3 inches
        float pageWidthPoints = pageWidthMM * 72 / 25.4f;
        float itemHeight = 20f; // Height per item row (adjust as needed)
        float headerHeight = 300f; // Height for header (adjust as needed)
        float footerHeight = 200f; // Height for footer (adjust as needed)

        // Calculate content height based on number of items
        float pageHeightPoints = itemSolds.size() * itemHeight + headerHeight + footerHeight;

        pdfDocument.setDefaultPageSize(new PageSize(pageWidthPoints, pageHeightPoints));
        Document document = new Document(pdfDocument);

        float margin = 5f;
        float contentWidth = pageWidthPoints - 2 * margin;
        /*
         * Header title of a pdf
         * */
        Paragraph titlePara = new Paragraph("RUPALI TRADING\nSaidpur").setBold();
        titlePara.setTextAlignment(TextAlignment.CENTER);
        titlePara.setFontSize(24);
        document.add(titlePara);

        // Header Table
        float[] headerWidth = {contentWidth};
        Table topTable = new Table(headerWidth);

        topTable.addCell(new Cell().add(new Paragraph("Invoice Number: " + invoiceNumber)).setBorder(Border.NO_BORDER).setFontSize(19));

        //topTable.addCell(new Cell().add(new Paragraph("Name")));
        topTable.addCell(new Cell().add(new Paragraph("Name: " + customerName)).setBorder(Border.NO_BORDER).setFontSize(19));
        // topTable.addCell(new Cell().add(new Paragraph("Invoice")));

        //topTable.addCell(new Cell().add(new Paragraph("Mobile")));
        topTable.addCell(new Cell().add(new Paragraph("Mobile: " + customerNumber)).setBorder(Border.NO_BORDER).setFontSize(19));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // topTable.addCell(new Cell().add(new Paragraph("Date")));
        topTable.addCell(new Cell().add(new Paragraph("Date: " + dateFormat.format(date))).setBorder(Border.NO_BORDER).setFontSize(19));

//        dateFormat = new SimpleDateFormat("HH:mm:ss");
//        topTable.addCell(new Cell().add(new Paragraph("Time")));
//        topTable.addCell(new Cell().add(new Paragraph(dateFormat.format(date))));

        topTable.addCell(new Cell(0, 4).add(new Paragraph("\n")).setBorder(Border.NO_BORDER));

        // Body Table
        float[] bodyColumnWidth = {contentWidth / 10, (contentWidth * 5) / 5, contentWidth / 10, contentWidth / 5, contentWidth / 5};
        Table table2 = new Table(bodyColumnWidth);

        table2.addCell(new Cell().add(new Paragraph("No")).setFontSize(19).setTextAlignment(TextAlignment.CENTER).setBold());
        table2.addCell(new Cell().add(new Paragraph("Item Name")).setFontSize(19).setTextAlignment(TextAlignment.CENTER).setBold());
        table2.addCell(new Cell().add(new Paragraph("QTY")).setFontSize(19).setTextAlignment(TextAlignment.CENTER).setBold());
        table2.addCell(new Cell().add(new Paragraph("Price")).setFontSize(19).setTextAlignment(TextAlignment.CENTER).setBold());
        table2.addCell(new Cell().add(new Paragraph("Total")).setFontSize(19).setTextAlignment(TextAlignment.CENTER).setBold());

        double grandTotal = 0;
        for (int i = 0; i < itemSolds.size(); i++) {
            SelectedItem item = itemSolds.get(i);
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1))).setFontSize(19).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(item.getItemName())).setFontSize(19).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))).setFontSize(19).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(item.getPrice()))).setFontSize(19).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(item.getTotal()))).setFontSize(19).setTextAlignment(TextAlignment.CENTER));
            grandTotal += item.getTotal();
        }

        table2.addCell(new Cell(1, 3).add(new Paragraph("")));
        table2.addCell(new Cell().add(new Paragraph("Total").setFontSize(19).setTextAlignment(TextAlignment.CENTER)));
        table2.addCell(new Cell().add(new Paragraph(String.valueOf(grandTotal))).setFontSize(19).setTextAlignment(TextAlignment.CENTER));

        document.add(topTable);
        document.add(table2);
        document.add(new Paragraph("\n\n\n(Authorize Signature)"));
        document.close();

        Toast.makeText(context, "PDF created", Toast.LENGTH_SHORT).show();
        /*Pdf to Image converter in android......................*/
    }

    /*
     * Show the pdf printer using bluetooth printer
     * */

    /*
     * Convert Pdf to image
     * */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scan_device, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.search) {
                bluetoothController.scanDevices();
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothController.disconnect();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bluetoothController.disconnect();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bluetoothController.handleActivityResult(requestCode, resultCode, data);
    }

}
