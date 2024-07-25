package com.example.inventorymanagement.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "purchased_products")
public class PurchasedItems {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name="product_name")
    private String productName;

    @ColumnInfo(name="product_quantity")
    private long quantity;

    @ColumnInfo(name="purchased_date")
    private Date purchasedDate;

    @Ignore
    public PurchasedItems(long id, long supplierSerialNumber, String supplierName, String supplierMobile, String productName,
                          long quantity, Date date) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.purchasedDate = date;
    }

    public PurchasedItems() {
    }

    @Ignore
    public PurchasedItems(String productName, long quantity, Date date) {
        this.productName = productName;
        this.quantity = quantity;
        this.purchasedDate = date;
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

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }
}
