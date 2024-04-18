package com.example.productlist.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="brand_names")
public class BrandNames {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="product_qualtity")
    private String productQualtity;

    public BrandNames(int id, String name, String productName) {
        this.id = id;
        this.name = name;
        this.productQualtity = productName;
    }

    @Ignore
    public BrandNames(String name, String productName) {
        this.name = name;
        this.productQualtity = productName;
    }

    public BrandNames() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductQualtity() {
        return productQualtity;
    }

    public void setProductQualtity(String productName) {
        this.productQualtity = productName;
    }
}
