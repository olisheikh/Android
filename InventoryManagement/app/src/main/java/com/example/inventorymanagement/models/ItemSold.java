package com.example.inventorymanagement.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "items_sold")
public class ItemSold {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "product_name")
    private List<String> productName;

    @ColumnInfo(name = "product_quantity")
    private List<Integer> productQuantity;

    @ColumnInfo(name = "product_price")
    private List<Double> productPrice;

    @ColumnInfo(name = "total_price")
    private List<Double> totalPrice;

    @ColumnInfo(name = "grand_total")
    private double grandTotal;

    public ItemSold(long id, List<String> productName, List<Integer> productQuantity, List<Double> productPrice, List<Double> totalPrice, double grandTotal) {
        this.id = id;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.grandTotal = grandTotal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getProductName() {
        return productName;
    }

    public void setProductName(List<String> productName) {
        this.productName = productName;
    }

    public List<Integer> getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(List<Integer> productQuantity) {
        this.productQuantity = productQuantity;
    }

    public List<Double> getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(List<Double> productPrice) {
        this.productPrice = productPrice;
    }

    public List<Double> getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(List<Double> totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }
}
