package com.example.inventorymanagement.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagement.R;
import com.example.inventorymanagement.adapter.ProductsAdapter;
import com.example.inventorymanagement.db.DatabaseHelper;
import com.example.inventorymanagement.models.Products;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    RecyclerView productListRecyclerView;
    Button saveBtn;
    EditText productName, productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productListRecyclerView = findViewById(R.id.productListRecyclerView);
        productListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = DatabaseHelper.getDB(this);
        saveBtn = findViewById(R.id.addItemBtn);
        productName = findViewById(R.id.productName);
        productQuantity = findViewById(R.id.productQuantity);

        if (databaseHelper.productsDao().getAllProducts() != null) {
            databaseHelper.productsDao().getAllProducts().observe(this, new Observer<List<Products>>() {
                @Override
                public void onChanged(List<Products> pro) {
                    ProductsAdapter proAdapter = new ProductsAdapter(ProductListActivity.this, pro, databaseHelper);
                    productListRecyclerView.setAdapter(proAdapter);
                }
            });
        }
       saveBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String name = productName.getText().toString();
               String quantity = productQuantity.getText().toString();
               Products products = new Products(name + " " + quantity);

               databaseHelper.productsDao().addNewProduct(products);
               databaseHelper.productsDao().getAllProducts().observe(ProductListActivity.this, new Observer<List<Products>>() {
                   @Override
                   public void onChanged(List<Products> products) {
                       ProductsAdapter adapter = new ProductsAdapter(ProductListActivity.this, products,databaseHelper);
                       productListRecyclerView.setAdapter(adapter);
                   }
               });
           }
       });
    }
}