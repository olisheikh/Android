package com.example.inventorymanagement.view;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
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
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    String invoiceNumber;

    /*-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

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
                String invoiceNumber2 = customerNumber.getText().toString() + (random.nextInt(100 - 1 + 1) - 1);
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

        selectedItems = new ArrayList<> ();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        /*
        * calling select item dialog
        * */
        selectItemBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               selectItemDialogCreate();
           }
        });

        /*
        * Getting permission to write the external storage
        * */
        savePrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    long invoiceNumToSave = Integer.parseInt(invoiceNum.getText().toString());
                    Date dateOfMemo = simpleDateFormat.parse(memoDate.getText().toString());
                    String customerNameStr = customerName.getText().toString();
                    String customerNumberStr = customerNumber.getText().toString();
                    double customerTotal = Double.parseDouble(totalBtn.getText().toString());

                    CustomersInfoWithItems customersInfoWithItems = new CustomersInfoWithItems(
                            customerNameStr,
                            customerNumberStr,
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
                    createPdf(selectedItems, invoiceNumber);

                } catch (FileNotFoundException error) {
                    error.printStackTrace();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
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

                            List<String> itemName = new ArrayList<> ();

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                calculateTotalPrice(quantityOfItem, priceOfItem, totalPrice);
            }
            public void afterTextChanged(Editable editable) {}
        });

        priceOfItem.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                calculateTotalPrice(quantityOfItem, priceOfItem, totalPrice);
            }
            public void afterTextChanged(Editable editable) {}
        });

        /*
        * Creating recycler view adapter for selected Items
        * */
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                ));}

                SelectedItemRecycler selectedItemRecycler = new SelectedItemRecycler(selectedItems, MemoActivity.this);
                recyclerView.setAdapter(selectedItemRecycler);
                selectItemDialog.dismiss();
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
    public void calculateTotalPrice(EditText quantityOfItem, EditText priceOfItem, TextView totalPrice) {
        String priceStr = priceOfItem.getText().toString();
        String quantityStr = quantityOfItem.getText().toString();

        double price = priceStr.isEmpty() ? 0 : Double.parseDouble(priceStr);
        double quantity = quantityStr.isEmpty()? 0: Double.parseDouble(quantityStr);

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
    public void createPdf(List<SelectedItem> itemSolds, String invoiceNumber) throws FileNotFoundException{
        /*
        * Setting color of the tab and body of the receipt
        * */
        DeviceRgb invoiceTabColor = new DeviceRgb(159, 206, 243);
        DeviceRgb invoiceColor = new DeviceRgb(199, 212, 218);

        Style bold = new Style().setBold();

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, invoiceNumber + ".pdf");
        OutputStream outputStream = new FileOutputStream(file);

        date = new Date();

        if (itemSolds.isEmpty()) {
            Toast.makeText(this, "Some field is empty", Toast.LENGTH_SHORT);
        } else {

            /*
            * Created document related instances
            * */
            PdfWriter pdfWriter = new PdfWriter(outputStream);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            float columnWidth[] = {140, 140, 140, 140};
            Table topTable = new Table(columnWidth);

            /*
            * Creating top table
            * */
            topTable.addCell(new Cell(0,2).add(new Paragraph("Customer's Details")).addStyle(bold).setBackgroundColor(invoiceTabColor));
            topTable.addCell(new Cell(0,2).add(new Paragraph("Invoice")).addStyle(bold).setBackgroundColor(invoiceTabColor));

            topTable.addCell(new Cell().add(new Paragraph("Name")));
            topTable.addCell(new Cell().add(new Paragraph(customerName.getText().toString())));
            topTable.addCell(new Cell().add(new Paragraph("Invoice Number:")));
            topTable.addCell(new Cell().add(new Paragraph(invoiceNumber)));

            topTable.addCell(new Cell().add(new Paragraph("Address")));
            topTable.addCell(new Cell().add(new Paragraph("")));

            dateFormat = new SimpleDateFormat("dd / MM / yyyy");

            topTable.addCell(new Cell().add(new Paragraph("Invoice Date: ")));
            topTable.addCell(new Cell().add(new Paragraph(dateFormat.format(date))));

            topTable.addCell(new Cell().add(new Paragraph("Mobile Number")));
            topTable.addCell(new Cell().add(new Paragraph(customerNumber.getText().toString())));

            dateFormat = new SimpleDateFormat("HH:mm:ss");

            topTable.addCell(new Cell().add(new Paragraph("Time: ")));
            topTable.addCell(new Cell().add(new Paragraph(dateFormat.format(date))));

            topTable.addCell(new Cell(0,4).add(new Paragraph("\n")));

            /*
            * Body of the table
            * */
            float bodyColumnWidth[] = {62, 162, 112, 112, 112};
            Table table2 = new Table(bodyColumnWidth);

            int i = 0;

            table2.addCell(new Cell().add(new Paragraph("Sl.No")).addStyle(bold).setBackgroundColor(invoiceTabColor));
            table2.addCell(new Cell().add(new Paragraph("Item Name")).addStyle(bold).setBackgroundColor(invoiceTabColor));
            table2.addCell(new Cell().add(new Paragraph("QTY")).addStyle(bold).setBackgroundColor(invoiceTabColor));
            table2.addCell(new Cell().add(new Paragraph("Price")).addStyle(bold).setBackgroundColor(invoiceTabColor));
            table2.addCell(new Cell().add(new Paragraph("Total")).addStyle(bold).setBackgroundColor(invoiceTabColor));

            double grandtotal = 0;

            while(i != itemSolds.size()) {
                    table2.addCell(new Cell().add(new Paragraph(i + 1 + "")).setBackgroundColor(Color.WHITE));
                    table2.addCell(new Cell().add(new Paragraph(itemSolds.get(i).getItemName())).setBackgroundColor(Color.WHITE));
                    table2.addCell(new Cell().add(new Paragraph(itemSolds.get(i).getQuantity() + "")).setBackgroundColor(Color.WHITE));
                    table2.addCell(new Cell().add(new Paragraph(itemSolds.get(i).getPrice() + "")).setBackgroundColor(Color.WHITE));
                    table2.addCell(new Cell().add(new Paragraph(itemSolds.get(i).getTotal() + "")).setBackgroundColor(Color.WHITE));

                grandtotal += itemSolds.get(i).getTotal();
                i++;
            }


            table2.addCell(new Cell(2, 3).add(new Paragraph("")));
            table2.addCell(new Cell().add(new Paragraph("Grand Total: ")).setBackgroundColor(invoiceTabColor));
            table2.addCell(new Cell().add(new Paragraph(grandtotal + ""))).setBackgroundColor(invoiceTabColor);


            /*
            * Top Banner of the receipt
            * */
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.banner);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            byte[] byteArray = byteArrayOutputStream.toByteArray();

            Image image = new Image(ImageDataFactory.create(byteArray));
            image.setWidth(520);
            image.setHeight(100);

            Paragraph paragraph = new Paragraph();
            paragraph.add(image);
            paragraph.setBorder(new SolidBorder(Color.BLACK, 2));

            document.add(paragraph);
            document.add(topTable);
            document.add(table2);
            document.add(new Paragraph("\n\n\n(Authorize Signature)").addStyle(bold));
            document.close();

            Toast.makeText(MemoActivity.this, "Pdf created", Toast.LENGTH_SHORT).show();

        }
    }
}