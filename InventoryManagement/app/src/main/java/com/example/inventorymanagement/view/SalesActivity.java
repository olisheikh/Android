package com.example.inventorymanagement.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.adapter.SalesAdapter;
import com.example.inventorymanagement.db.DatabaseHelper;
import com.example.inventorymanagement.models.SelectedItem;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SalesActivity extends AppCompatActivity {

    Calendar calendar;
    ImageView incrementDateSales, decrementDateSales, emptyFolder;
    TabLayout tabLayout;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    TextView dateShowTextView, totalAmountTextView;
    public static final int DAILY = 0;
    public static final int MONTHLY = 1;
    public static final int CALENDAR = 2;
    public static int SELECTED_TAB = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        /*
        * Initializing views
        * */
        incrementDateSales = findViewById(R.id.incrementDateImgViewSales);
        decrementDateSales = findViewById(R.id.decrementDateImgViewSales);
        tabLayout = findViewById(R.id.optionsTabLayoutSales);
        dateShowTextView = findViewById(R.id.dateShowingTextViewSales);
        totalAmountTextView = findViewById(R.id.totalAmountSales);
        emptyFolder = findViewById(R.id.emptyFolderImgView);

        recyclerView = findViewById(R.id.salesDisplayRecyclerViewSales);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = DatabaseHelper.getDB(SalesActivity.this);

        calendar = Calendar.getInstance();
        currentDate(calendar.getTime());

        incrementDateSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (SELECTED_TAB == DAILY) {
                    calendar.add(Calendar.DATE, 1);
               } else if (SELECTED_TAB == MONTHLY) {
                    calendar.add(Calendar.MONTH, 1);
               }
                currentDate(calendar.getTime());
            }
        });

        decrementDateSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SELECTED_TAB == DAILY) {
                    calendar.add(Calendar.DATE, -1);
                } else if (SELECTED_TAB == MONTHLY) {
                    calendar.add(Calendar.MONTH,-1);
                }
                currentDate(calendar.getTime());
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Daily")) {
                    SELECTED_TAB = 0;
                    currentDate(calendar.getTime());
                } else if (tab.getText().equals("Monthly")) {
                    SELECTED_TAB = 1;
                    currentDate(calendar.getTime());
                } else if (tab.getText().equals("Calendar")) {
                    SELECTED_TAB = 2;

                    /*
                    * Creating date picker for calendar tab
                    * */
                    DatePickerDialog datePickerDialog = new DatePickerDialog(tabLayout.getContext());

                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            Calendar cd = Calendar.getInstance();

                            cd.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                            cd.set(Calendar.MONTH, datePicker.getMonth());
                            cd.set(Calendar.YEAR, datePicker.getYear());

                            currentDate(cd.getTime());
                        }
                    });

                    datePickerDialog.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getText().equals("Calendar")) {
                    SELECTED_TAB = 2;

                    /*
                     * Creating date picker for calendar tab
                     * */
                    DatePickerDialog datePickerDialog = new DatePickerDialog(tabLayout.getContext());

                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            Calendar cd = Calendar.getInstance();

                            cd.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                            cd.set(Calendar.MONTH, datePicker.getMonth());
                            cd.set(Calendar.YEAR, datePicker.getYear());

                            currentDate(cd.getTime());
                        }
                    });

                    datePickerDialog.show();
                }
            }
        });
    }

    public void currentDate(Date dateValue) {

       if (SELECTED_TAB == DAILY) {
           incrementDateSales.setVisibility(View.VISIBLE);
           decrementDateSales.setVisibility(View.VISIBLE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
            String targetDate = simpleDateFormat.format(dateValue);
            dateShowTextView.setText(targetDate);

            databaseHelper.selectedItemDao().getSelectedItemByDate(dateValue).observe(this, new Observer<List<SelectedItem>>() {
                @Override
                public void onChanged(List<SelectedItem> selectedItems2) {
                        Set<String> uniqueItems = new HashSet<>();
                        List<SelectedItem> newSalesList = new ArrayList<>();

                        for (int i = 0; i < selectedItems2.size(); i++) {
                            uniqueItems.add(selectedItems2.get(i).getItemName());
                        }
                        List<String> uniqueList = new ArrayList<>(uniqueItems);

                        int dailyTotal = 0;
                        boolean isAvailable = false;

                        for (int i = 0; i < uniqueList.size(); i++) {
                            int countQuantity = 0;
                            double countAmount = 0;
                            for (int j = 0; j < selectedItems2.size(); j++) {
                                if (uniqueList.get(i).equals(selectedItems2.get(j).getItemName())) {
                                    countQuantity += selectedItems2.get(j).getQuantity();
                                    countAmount += selectedItems2.get(j).getTotal();
                                    isAvailable = true;
                                }
                            }
                            dailyTotal += countAmount;
                            if (isAvailable) {
                                newSalesList.add(new SelectedItem(
                                        uniqueList.get(i),
                                        countQuantity,
                                        selectedItems2.get(i).getPrice(),
                                        countAmount,
                                        selectedItems2.get(i).getCustomerId(),
                                        selectedItems2.get(i).getPurchasedDate()));
                                isAvailable = false;
                            }
                        }
                        SalesAdapter salesAdapter = new SalesAdapter(SalesActivity.this, newSalesList);
                        recyclerView.setAdapter(salesAdapter);
                        totalAmountTextView.setText(dailyTotal + "");
                    if (!selectedItems2.isEmpty()) {
                        emptyFolder.setVisibility(View.INVISIBLE);
                    } else {
                        emptyFolder.setVisibility(View.VISIBLE);
                    }
                }
            });
       } else if (SELECTED_TAB == MONTHLY) {
           incrementDateSales.setVisibility(View.VISIBLE);
           decrementDateSales.setVisibility(View.VISIBLE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM, yyyy");
            String targetDate = simpleDateFormat.format(dateValue);
            dateShowTextView.setText(targetDate);

            databaseHelper.selectedItemDao().getSelectedItemByMonth(dateValue).observe(this, new Observer<List<SelectedItem>>() {
                @Override
                public void onChanged(List<SelectedItem> selectedItems2) {
                        Set<String> uniqueItems = new HashSet<>();
                        List<SelectedItem> newSalesList = new ArrayList<>();

                        for (int i = 0; i < selectedItems2.size(); i++) {
                            uniqueItems.add(selectedItems2.get(i).getItemName());
                        }
                        List<String> uniqueList = new ArrayList<>(uniqueItems);

                        int dailyTotal = 0;
                        boolean isAvailable = false;

                        for (int i = 0; i < uniqueList.size(); i++) {
                            int countQuantity = 0;
                            double countAmount = 0;
                            for (int j = 0; j < selectedItems2.size(); j++) {
                                if (uniqueList.get(i).equals(selectedItems2.get(j).getItemName())) {
                                    countQuantity += selectedItems2.get(j).getQuantity();
                                    countAmount += selectedItems2.get(j).getTotal();
                                    isAvailable = true;
                                }
                            }
                            dailyTotal += countAmount;
                            if (isAvailable) {
                                newSalesList.add(new SelectedItem(
                                        uniqueList.get(i),
                                        countQuantity,
                                        selectedItems2.get(i).getPrice(),
                                        countAmount,
                                        selectedItems2.get(i).getCustomerId(),
                                        selectedItems2.get(i).getPurchasedDate()));
                                isAvailable = false;
                            }
                        }
                        SalesAdapter salesAdapter = new SalesAdapter(SalesActivity.this, newSalesList);
                        recyclerView.setAdapter(salesAdapter);
                        totalAmountTextView.setText(dailyTotal + "");
                        if (!selectedItems2.isEmpty()) {
                            emptyFolder.setVisibility(View.INVISIBLE);
                        } else {
                        emptyFolder.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else if (SELECTED_TAB == CALENDAR) {
           incrementDateSales.setVisibility(View.INVISIBLE);
           decrementDateSales.setVisibility(View.INVISIBLE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
            String targetDate = simpleDateFormat.format(dateValue);
            dateShowTextView.setText(targetDate);

            databaseHelper.selectedItemDao().getSelectedItemByDate(dateValue).observe(this, new Observer<List<SelectedItem>>() {
                @Override
                public void onChanged(List<SelectedItem> selectedItems2) {
                        Set<String> uniqueItems = new HashSet<>();
                        List<SelectedItem> newSalesList = new ArrayList<>();

                        for (int i = 0; i < selectedItems2.size(); i++) {
                            uniqueItems.add(selectedItems2.get(i).getItemName());
                        }
                        List<String> uniqueList = new ArrayList<>(uniqueItems);

                        int dailyTotal = 0;
                        boolean isAvailable = false;

                        for (int i = 0; i < uniqueList.size(); i++) {
                            int countQuantity = 0;
                            double countAmount = 0;
                            for (int j = 0; j < selectedItems2.size(); j++) {
                                if (uniqueList.get(i).equals(selectedItems2.get(j).getItemName())) {
                                    countQuantity += selectedItems2.get(j).getQuantity();
                                    countAmount += selectedItems2.get(j).getTotal();
                                    isAvailable = true;
                                }
                            }
                            dailyTotal += countAmount;
                            if (isAvailable) {
                                newSalesList.add(new SelectedItem(
                                        uniqueList.get(i),
                                        countQuantity,
                                        selectedItems2.get(i).getPrice(),
                                        countAmount,
                                        selectedItems2.get(i).getCustomerId(),
                                        selectedItems2.get(i).getPurchasedDate()));
                                isAvailable = false;
                            }
                        }
                        SalesAdapter salesAdapter = new SalesAdapter(SalesActivity.this, newSalesList);
                        recyclerView.setAdapter(salesAdapter);
                        totalAmountTextView.setText(dailyTotal + "");

                    if (!selectedItems2.isEmpty()) {
                        emptyFolder.setVisibility(View.INVISIBLE);
                    } else {
                        emptyFolder.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}