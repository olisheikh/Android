package com.example.productlist.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "daily_sales")
public class DailySales {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "product_size")
    private String productSize;

    @ColumnInfo(name = "product_quantity")
    private int productQuantity;

    @ColumnInfo(name = "product_price")
    private long productPrice;

    @ColumnInfo(name = "total_price")
    private long totalPrice;

    @ColumnInfo(name = "date")
    private Date date;

    public DailySales(long id, String productName, String productSize, int productQuantity, long productPrice, long totalPrice, Date date) {
        this.id = id;
        this.productName = productName;
        this.productSize = productSize;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    @Ignore
    public DailySales(String productName, String productSize, int productQuantity, long productPrice, long totalPrice, Date date) {
        this.productName = productName;
        this.productSize = productSize;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.date = date;
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

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
