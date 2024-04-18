package com.example.inventorymanagement;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.adapter.CustomRecycleAdapter;
import com.example.inventorymanagement.models.CategoryOfGridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CustomRecycleAdapter customRecycleAdapter;
    private List<CategoryOfGridView> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.categoryRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        categories = new ArrayList<>();
        categories.add(new CategoryOfGridView(R.drawable.accounting, "Inventory"));
        categories.add(new CategoryOfGridView(R.drawable.loan, "Purchases"));
        categories.add(new CategoryOfGridView(R.drawable.salary, "Sales"));
        categories.add(new CategoryOfGridView(R.drawable.portfolio, "Stock"));

        CustomRecycleAdapter customGridViewAdapter = new CustomRecycleAdapter(categories);
        mRecyclerView.setAdapter(customGridViewAdapter);
    }
}