package com.example.inventorymanagement.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.inventorymanagement.models.CustomersInfoWithItems;
import com.example.inventorymanagement.models.SelectedItem;

import java.util.List;

@Dao
public interface CustomersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCustomer(CustomersInfoWithItems customersInfoWithItems);

}
