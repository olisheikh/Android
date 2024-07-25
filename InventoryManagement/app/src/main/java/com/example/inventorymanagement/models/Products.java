package com.example.inventorymanagement.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="products_entity")
public class Products {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name="product_name")
    private String productName;


    public Products(long id, String productName) {
        this.id = id;
        this.productName = productName;
    }

    @Ignore
    public Products() {
    }

    @Ignore
    public Products(String productName) {
        this.productName = productName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
