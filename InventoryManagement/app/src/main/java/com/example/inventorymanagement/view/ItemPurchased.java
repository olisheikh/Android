package com.example.inventorymanagement.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.adapter.PurchasedItemAdapter;
import com.example.inventorymanagement.db.DatabaseHelper;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.models.PurchasedItems;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemPurchased extends AppCompatActivity {
    FloatingActionButton addItemBtn;
    TextView showDate;
    ImageView incrementDate, decrementDate;
    RecyclerView recyclerView;

    DatabaseHelper databaseHelper;
    Calendar calendarToolbar;
    ImageView emptyImg;
    TabLayout tabLayout;

    /*
    * Constant Value for the tab layout
    * */
    public static final int DAILY = 0;
    public static final int MONTHLY = 1;
    public static final int CALENDAR = 2;
    public static int SELECTED_TAB = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_purchased);

        /*
        * Declaration & Initialization of variables in ItemPurchased activity
        * */
        addItemBtn = findViewById(R.id.purchasesAddBtn);
        showDate = findViewById(R.id.purchasesDateTextView);
        incrementDate = findViewById(R.id.purchasesDateIncrementImgView);
        decrementDate = findViewById(R.id.purchasesDateDecrementImgView);
        emptyImg = findViewById(R.id.emptyFolder);
        tabLayout = findViewById(R.id.purchasesTabletTab);

        recyclerView = findViewById(R.id.purchasedItemRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database helper
        databaseHelper = DatabaseHelper.getDB(this);

        // Observe LiveData for all products
        LiveData<List<Products>> allProductsAvailableLiveData = databaseHelper.productsDao().getAllProducts();

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddPurchasedItemDialog();
            }
        });

        /*
        * Adding the functionality to the toolbar
        * */
        calendarToolbar = Calendar.getInstance();
        upDate(calendarToolbar.getTime());


        /*
        * Date increament
        * */
        incrementDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SELECTED_TAB == DAILY) {
                    calendarToolbar.add(Calendar.DATE, 1);
                } else if (SELECTED_TAB == MONTHLY) {
                    calendarToolbar.add(Calendar.MONTH, 1);
                }
                upDate(calendarToolbar.getTime());
            }
        });

        decrementDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (SELECTED_TAB == DAILY) {
                    calendarToolbar.add(Calendar.DATE, -1);
                } else if (SELECTED_TAB == MONTHLY) {
                    calendarToolbar.add(Calendar.MONTH, -1);
                }
                upDate(calendarToolbar.getTime());
            }
        });

        /*
        * Adding the functionality to the tab layout
        * */
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Daily")) {
                    SELECTED_TAB = 0;
                    upDate(calendarToolbar.getTime());
                }  else if (tab.getText().equals("Monthly")) {
                    SELECTED_TAB = 1;
                    upDate(calendarToolbar.getTime());
                } else if (tab.getText().equals("Calendar")) {
                    SELECTED_TAB = 2;
                    DatePickerDialog datePickerDialog = new DatePickerDialog(tabLayout.getContext());

                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            Calendar calendar = Calendar.getInstance();

                            calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                            calendar.set(Calendar.MONTH, datePicker.getMonth());
                            calendar.set(Calendar.YEAR, datePicker.getYear());

                            upDate(calendar.getTime());
                        }
                    });
                    datePickerDialog.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getText().equals("Calendar")) {
                    SELECTED_TAB = 2;
                    DatePickerDialog datePickerDialog = new DatePickerDialog(tabLayout.getContext());

                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            Calendar calendar = Calendar.getInstance();

                            calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                            calendar.set(Calendar.MONTH, datePicker.getMonth());
                            calendar.set(Calendar.YEAR, datePicker.getYear());

                            upDate(calendar.getTime());
                        }
                    });
                    datePickerDialog.show();
                }
            }
        });
    }
    /*------------------------------OnCreate method ends------------------------*/

    /*
    * Update date
    * */
    public void upDate(Date date) {
        if (SELECTED_TAB == DAILY) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");

            showDate.setText(simpleDateFormat.format(date));

            databaseHelper.purchasedItemDao().getPurchasedItemAccordingToDate(date).observe(ItemPurchased.this, new Observer<List<PurchasedItems>>() {
                @Override
                public void onChanged(List<PurchasedItems> newPurchasedItem) {
                    PurchasedItemAdapter purchasedItemAdapter = new PurchasedItemAdapter(ItemPurchased.this, newPurchasedItem);
                    recyclerView.setAdapter(purchasedItemAdapter);
                    if (newPurchasedItem.size() > 0) {
                        emptyImg.setVisibility(View.GONE);
                    } else {
                        emptyImg.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else if (SELECTED_TAB == MONTHLY) {
            SimpleDateFormat monthlyDateFormat = new SimpleDateFormat("MMMM, yyyy");
            showDate.setText(monthlyDateFormat.format(date));

            databaseHelper.purchasedItemDao().getPurchasedItemByMonth(date).observe(ItemPurchased.this, new Observer<List<PurchasedItems>>() {
                @Override
                public void onChanged(List<PurchasedItems> purchasedItems) {
                    Set<String> hashSet = new HashSet<> ();

                    for (int i = 0; i < purchasedItems.size(); i++) {
                        hashSet.add(purchasedItems.get(i).getProductName().toLowerCase());
                    }

                    List<String> uniqueItems = new ArrayList<>(hashSet);
                    List<PurchasedItems> updatePurchased = new ArrayList<> ();

                    for (int i = 0; i < uniqueItems.size(); i++) {
                        long countQuantity = 0;
                        for (int j = 0; j < purchasedItems.size(); j++) {
                            if (uniqueItems.get(i).equals(purchasedItems.get(j).getProductName().toLowerCase())) {
                                countQuantity += purchasedItems.get(j).getQuantity();
                            }
                        }
                        updatePurchased.add(new PurchasedItems(uniqueItems.get(i), countQuantity, date));
                    }
                    PurchasedItemAdapter adapter = new PurchasedItemAdapter(ItemPurchased.this, updatePurchased);
                    recyclerView.setAdapter(adapter);

                    if (purchasedItems.size() > 0) {
                        emptyImg.setVisibility(View.GONE);
                    } else {
                        emptyImg.setVisibility(View.VISIBLE);
                    }
                }
            });

        } else if (SELECTED_TAB == CALENDAR) {
            SimpleDateFormat yearDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
            String showTime = yearDateFormat.format(date);
            showDate.setText(yearDateFormat.format(date));

            databaseHelper.purchasedItemDao().getPurchasedItemAccordingToDate(date).observe(ItemPurchased.this, new Observer<List<PurchasedItems>> () {
                @Override
                public void onChanged(List<PurchasedItems> purchasedItems) {
                    PurchasedItemAdapter adapter = new PurchasedItemAdapter(ItemPurchased.this, purchasedItems);
                    recyclerView.setAdapter(adapter);

                    if (purchasedItems.size() > 0) {
                        emptyImg.setVisibility(View.GONE);
                    } else {
                        emptyImg.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    /* ----------------------Dialog to add Items--------------------------------*/
    private void showAddPurchasedItemDialog() {
        // Create dialogs
        Dialog addPurchasedValueDialog = new Dialog(ItemPurchased.this);
        Dialog showSearchIndexDialog = new Dialog(ItemPurchased.this);

        // Set layout and show dialog
        addPurchasedValueDialog.setContentView(R.layout.fragment_add_purchased_item);
        calculateWindowSize(addPurchasedValueDialog);
        addPurchasedValueDialog.show();

        // Initialize dialog views
        TextInputEditText purchasedItemDateFromAddDialog = addPurchasedValueDialog.findViewById(R.id.datePicker);
        TextView purchasedItemNameFromAddDialog = addPurchasedValueDialog.findViewById(R.id.selectPurchasedProductName);
        EditText purchasedItemQuantityFromAddDialog = addPurchasedValueDialog.findViewById(R.id.purchasedItemProductQuantity);
        Button purchasedItemSaveBtn = addPurchasedValueDialog.findViewById(R.id.purchasedItemSave);
        Button purchasedItemCancelBtn = addPurchasedValueDialog.findViewById(R.id.purchasedItemCancel);

        /*
        * Working on purchased Item date dialog
        * */
        PurchasedItems newPurchasedItem = new PurchasedItems();

        purchasedItemDateFromAddDialog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               /*
                * Creating new empty object for new item purchased
                * */
               DatePickerDialog datePicker = new DatePickerDialog(addPurchasedValueDialog.getContext());
               datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker1, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, datePicker1.getDayOfMonth());
                        calendar.set(Calendar.MONTH, datePicker1.getMonth());
                        calendar.set(Calendar.YEAR, datePicker1.getYear());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                        String dateToShow = dateFormat.format(calendar.getTime());
                        purchasedItemDateFromAddDialog.setText(dateToShow);
                        newPurchasedItem.setPurchasedDate(calendar.getTime());
                    }
                });
               datePicker.show();
           }
        });
        // Set onClickListener for product name TextView
        purchasedItemNameFromAddDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchIndexDialog.setContentView(R.layout.drop_down_selected_product);
                calculateWindowSize(showSearchIndexDialog);
                showSearchIndexDialog.show();

                // Initialize views in search dialog
                EditText textFilteringFromListEditVeiw = showSearchIndexDialog.findViewById(R.id.allItemsToBeSelected);
                ListView toShowAllProductsListView = showSearchIndexDialog.findViewById(R.id.listOfAllItemToBeSelected);

                // Observe LiveData for all products
                databaseHelper.productsDao().getAllProducts().observe(ItemPurchased.this, new Observer<List<Products>>() {
                    @Override
                    public void onChanged(List<Products> productList) {
                        List<String> nameOfAllProducts = new ArrayList<>();
                        for (Products product : productList) {
                            nameOfAllProducts.add(product.getProductName());
                        }

                        ArrayAdapter<String> productsNameShowArrayAdapter = new ArrayAdapter<>(
                                ItemPurchased.this,
                                android.R.layout.simple_list_item_1,
                                nameOfAllProducts
                        );

                        toShowAllProductsListView.setAdapter(productsNameShowArrayAdapter);

                        textFilteringFromListEditVeiw.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                productsNameShowArrayAdapter.getFilter().filter(charSequence);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {}
                        });

                        toShowAllProductsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                purchasedItemNameFromAddDialog.setText(productsNameShowArrayAdapter.getItem(position));
                                showSearchIndexDialog.dismiss();
                            }
                        });
                    }
                });
            }
        });

        // Set onClickListener for save button
        purchasedItemSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newPurchasedItem.setProductName(purchasedItemNameFromAddDialog.getText().toString());
                newPurchasedItem.setQuantity(Long.parseLong(purchasedItemQuantityFromAddDialog.getText().toString()));
                // Observe LiveData for all purchased items
                List<PurchasedItems> allPurchasedItem = databaseHelper.purchasedItemDao().getAllPurchasedItemList();

                        boolean isPresent = false;
                        for (PurchasedItems item : allPurchasedItem) {

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                            String newDate = simpleDateFormat.format(new Date(String.valueOf(newPurchasedItem.getPurchasedDate())));
                            String oldDate = simpleDateFormat.format(new Date(String.valueOf(item.getPurchasedDate())));

                            if (item.getProductName().equals(newPurchasedItem.getProductName()) && newDate.equals(oldDate)) {
                                long tempQuantity = item.getQuantity() + newPurchasedItem.getQuantity();
                                item.setQuantity(tempQuantity);
                                databaseHelper.purchasedItemDao().updatePurchasedItem(item);
                                isPresent = true;
                                break;
                            }
                        }
                        if (!isPresent) {
                            databaseHelper.purchasedItemDao().insertPurchasedItem(newPurchasedItem);
                        }
                        addPurchasedValueDialog.dismiss();
                    }
        });

        // Set onClickListener for cancel button
        purchasedItemCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPurchasedValueDialog.dismiss();
            }
        });
    }

    private void calculateWindowSize(Dialog dialogToSetCustomWindowSize) {
        DisplayMetrics windowSize = getResources().getDisplayMetrics();

        int windowWidth = windowSize.widthPixels;
        int windowHeight = windowSize.heightPixels;

        int customWidth = windowWidth;
        int customHeight = (int) (windowHeight * .48);

        dialogToSetCustomWindowSize.getWindow().setLayout(customWidth, customHeight);
    }

    /*
    * Retrieving data according to the date
    * */
    private void getItemsToDate(Date date) {
        databaseHelper.purchasedItemDao().getPurchasedItemAccordingToDate(date).observe(ItemPurchased.this,
                new Observer<List<PurchasedItems>>() {
           @Override
           public void onChanged(List<PurchasedItems> purchasedItems) {
               PurchasedItemAdapter adapter = new PurchasedItemAdapter(ItemPurchased.this, purchasedItems);
               recyclerView.setAdapter(adapter);
           }
        });
    }
}
