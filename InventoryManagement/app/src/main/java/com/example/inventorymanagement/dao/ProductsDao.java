package com.example.inventorymanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inventorymanagement.models.Products;

import java.util.List;

@Dao
public interface ProductsDao {
    @Query("select * from products_entity")
    LiveData<List<Products>> getAllProducts();

    @Query("select * from products_entity where id = :productId")
    Products getProduct(Long productId);

    @Insert
    void addNewProduct(Products product);

    @Delete
    void deleteProduct(Products product);

    @Update
    void updateProduct(Products product);
}
