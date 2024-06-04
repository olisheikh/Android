package com.example.inventorymanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.inventorymanagement.models.SelectedItem;

import java.util.Date;
import java.util.List;

@Dao
public interface SelectedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSelectedItem(SelectedItem selectedItems);


    @Query("SELECT * FROM selected_items WHERE strftime('%Y-%m-%d', purchased_date / 1000, 'unixepoch') = strftime('%Y-%m-%d', :targetDate / 1000, 'unixepoch')")
    LiveData<List<SelectedItem>> getSelectedItemByDate(Date targetDate);

    @Query("SELECT * FROM selected_items WHERE strftime('%Y-%m', purchased_date / 1000, 'unixepoch') = strftime('%Y-%m', :targetDate / 1000, 'unixepoch')")
    LiveData<List<SelectedItem>> getSelectedItemByMonth(Date targetDate);

}
