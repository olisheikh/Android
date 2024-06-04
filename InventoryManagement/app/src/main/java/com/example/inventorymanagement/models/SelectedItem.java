package com.example.inventorymanagement.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="selected_items",
        foreignKeys = @ForeignKey(
                entity = CustomersInfoWithItems.class,
                parentColumns = "customer_id",
                childColumns = "customer_id",
                onDelete = ForeignKey.CASCADE))
public class SelectedItem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name="item_name")
    private String itemName;
    @ColumnInfo(name="quantity")
    private int quantity;

    @ColumnInfo(name="price")
    private double price;

    @ColumnInfo(name="total")
    private double total;


    @ColumnInfo(name="customer_id")
    private long customerId;

    @ColumnInfo(name="purchased_date")
    private Date purchasedDate;


    @Ignore
    public SelectedItem(long id, String itemName, int quantity, double price, double total, long customerId, Date purchasedDate) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.customerId = customerId;
        this.purchasedDate = purchasedDate;
    }
    @Ignore
    public SelectedItem(String itemName, int quantity, double price, double total, long customerId, Date purchasedDate) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.customerId = customerId;
        this.purchasedDate = purchasedDate;
    }
    public SelectedItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }
}

