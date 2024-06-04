package com.example.inventorymanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inventorymanagement.models.Stock;

import java.util.List;

@Dao
public interface StockDao {

    @Insert
    void insertItemInStock(Stock stock);

    @Update
    void updateItemInStock(Stock stock);

    @Query("select * from daily_stock where product_name = :targetProductName")
    Stock retrieveAllAvailableProductByProductName(String targetProductName);

    @Query("select * from daily_stock")
    LiveData<List<Stock>> retrieveAllAvailableProducts();
}
