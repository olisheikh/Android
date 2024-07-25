package com.example.inventorymanagement.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.adapter.StockAdapter;
import com.example.inventorymanagement.db.DatabaseHelper;
import com.example.inventorymanagement.models.Stock;

import java.util.List;

public class StockActivity extends AppCompatActivity {
    SearchView itemSearchInStock;
    RecyclerView itemsAvailableInStock;
    DatabaseHelper databaseHelperInStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        itemSearchInStock = findViewById(R.id.stockSearch);
        itemsAvailableInStock = findViewById(R.id.stockRecyclerView);
        itemsAvailableInStock.setLayoutManager(new LinearLayoutManager(this));
        databaseHelperInStock = DatabaseHelper.getDB(this);

        itemSearchInStock.setQueryHint("Search Products");

        databaseHelperInStock.stockDao().retrieveAllAvailableProducts().observe(this, new Observer<List<Stock>> () {
            @Override
            public void onChanged(List<Stock> stocksPara) {
                StockAdapter stockAdapter = new StockAdapter(StockActivity.this,stocksPara);
                itemsAvailableInStock.setAdapter(stockAdapter);

                itemSearchInStock.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        stockAdapter.getFilter().filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        stockAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }
        });


    }
}