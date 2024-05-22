package com.example.inventorymanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inventorymanagement.models.PurchasedItems;

import java.util.Date;
import java.util.List;

@Dao
public interface PurchasedItemDao {
    @Query("select * from purchased_products")
    LiveData<List<PurchasedItems>> getAllPurchasedItem();

    @Query("select * from purchased_products")
    List<PurchasedItems> getAllPurchasedItemList();
    @Insert
    public void insertPurchasedItem(PurchasedItems purchasedItems);

    @Delete
    public void deletePurchasedItem(PurchasedItems purchasedItems);

    @Update
    public void updatePurchasedItem(PurchasedItems purchasedItems);

    @Query("select * from purchased_products where product_name = :product")
    PurchasedItems getPurchasedItem(String product);

    @Query(" SELECT * FROM purchased_products WHERE purchased_date / (1000 /* drop millis*/ * 60 /* drop seconds */ * 60 /* drop minutes */ * 24 /* drop hours */) = :targetDate / 86400000")
    LiveData<List<PurchasedItems>> getPurchasedItemAccordingToDate(Date targetDate);

    @Query("select * from purchased_products where strftime('%Y-%m', purchased_date / 1000, 'unixepoch') = strftime('%Y-%m', :targetDate / 1000, 'unixepoch') ")
    LiveData<List<PurchasedItems>> getPurchasedItemByMonth(Date targetDate);
}
