package com.example.inventorymanagement.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "daily_stock")
public class Stock {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "product_available")
    private int productAvailable;

    @Ignore
    public Stock(long id, String productName, int productAvailable) {
        this.id = id;
        this.productName = productName;
        this.productAvailable = productAvailable;
    }

    public Stock() {
    }

    @Ignore
    public Stock(String productName, int productAvailable) {
        this.productName = productName;
        this.productAvailable = productAvailable;
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

    public int getProductAvailable() {
        return productAvailable;
    }

    public void setProductAvailable(int productAvailable) {
        this.productAvailable = productAvailable;
    }
}
