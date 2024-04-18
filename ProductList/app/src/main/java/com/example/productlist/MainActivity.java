package com.example.productlist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.productlist.database.DatabaseHelper;
import com.example.productlist.entity.BrandNames;
import com.example.productlist.fragments.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // Declaring the ui elements
    EditText dateEdTxt, productName, productSize;
    Calendar calendarMainAct;
    Button cancelDialogBtn, saveDialogBtn, addNewItem;
    DatabaseHelper dbHelper;
    Spinner spinner;
    List<BrandNames> brands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = DatabaseHelper.getDb(this);

        dateEdTxt = findViewById(R.id.currentDate);
        addNewItem = findViewById(R.id.addMoreItem);

        calendarMainAct = Calendar.getInstance();

        showCurrentDate();

        dateEdTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        addNewItem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Dialog dialog = new Dialog(MainActivity.this);
               dialog.setContentView(R.layout.add_new_product);
               dialog.show();
               dialog.setCancelable(false);

               cancelDialogBtn = dialog.findViewById(R.id.cancelBtn);
               saveDialogBtn = dialog.findViewById(R.id.saveBtn);
               productName = dialog.findViewById(R.id.productnameEdTxt);
               productSize = dialog.findViewById(R.id.quantityEdTxt);

               cancelDialogBtn.setOnClickListener(new View.OnClickListener(){
                  @Override
                  public void onClick(View v) {
                      dialog.dismiss();
                  }
               });

               saveDialogBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       String productNameStr = productName.getText().toString();
                       String productQuantity = productSize.getText().toString();

                       dbHelper.brandNameDao().addNewBrand(new BrandNames(productNameStr, productQuantity));
                       Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                       dialog.dismiss();
                   }
               });
           }
        });


        // Spinner of Brand names
        spinner = findViewById(R.id.itemList);
        dbHelper.brandNameDao().getAllBrandNames().observe(this, new Observer<List<BrandNames>>() {
            @Override
            public void onChanged(List<BrandNames> brandNames) {
                List<String> newItems = new ArrayList<>();
                newItems.add("Enter the Product Name");
                for(BrandNames temp: brandNames) {
                    newItems.add(temp.getName() + temp.getProductQualtity());
                };
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,newItems);
                spinner.setAdapter(arrayAdapter);
            }
        });


    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        datePickerFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker datePicker, int year, int month, int day) {
               calendarMainAct.set(Calendar.YEAR, year);
               calendarMainAct.set(Calendar.MONTH, month);
               calendarMainAct.set(Calendar.DATE, day);

               SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault());
               String formattedDate = simpleDateFormat.format(calendarMainAct.getTime());
               dateEdTxt.setText(formattedDate);
           }
        });

        datePickerFragment.show(getSupportFragmentManager(), "Date Picker");
    }

    public void showCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        dateEdTxt.setText(simpleDateFormat.format(calendarMainAct.getTime()));
    }
}