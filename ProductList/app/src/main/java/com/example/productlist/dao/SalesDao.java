package com.example.productlist.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.productlist.entity.DailySales;

import java.util.List;

@Dao
public interface SalesDao {

    @Query("select * from daily_sales")
    List<DailySales> getAllSales();

    @Insert
    void addNewSale(DailySales dailySales);

    @Update
    void updateSales(DailySales dailySales);

    @Delete
    void deleteSales(DailySales dailySales);
}
