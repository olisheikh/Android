package com.example.inventorymanagement.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.adapter.CustomRecycleAdapter;
import com.example.inventorymanagement.models.OptionsModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<OptionsModel> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.categoryRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        options = new ArrayList<>();
        options.add(new OptionsModel(R.drawable.accounting, "Memo"));
        options.add(new OptionsModel(R.drawable.loan, "Purchases"));
        options.add(new OptionsModel(R.drawable.salary, "Sales"));
        options.add(new OptionsModel(R.drawable.portfolio, "Stock"));
        options.add(new OptionsModel(R.drawable.add_product, "Products"));

        CustomRecycleAdapter customGridViewAdapter = new CustomRecycleAdapter(MainActivity.this, options);
        mRecyclerView.setAdapter(customGridViewAdapter);
    }
}