package com.example.inventorymanagement.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.inventorymanagement.dao.CustomersDao;
import com.example.inventorymanagement.dao.ProductsDao;
import com.example.inventorymanagement.dao.PurchasedItemDao;
import com.example.inventorymanagement.dao.SelectedItemDao;
import com.example.inventorymanagement.dao.StockDao;
import com.example.inventorymanagement.models.CustomersInfoWithItems;
import com.example.inventorymanagement.models.DateTypeConverter;
import com.example.inventorymanagement.models.Products;
import com.example.inventorymanagement.models.PurchasedItems;
import com.example.inventorymanagement.models.SelectedItem;
import com.example.inventorymanagement.models.Stock;

@Database(entities = {Products.class, PurchasedItems.class, CustomersInfoWithItems.class, SelectedItem.class, Stock.class}, exportSchema = false, version = 30)
@TypeConverters({DateTypeConverter.class})
public abstract class DatabaseHelper extends RoomDatabase {

    private static final String DB_NAME = "productsdb";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDB(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract ProductsDao productsDao();
    public abstract PurchasedItemDao purchasedItemDao();

    public abstract CustomersDao customersDao();

    public abstract SelectedItemDao selectedItemDao();

    public abstract StockDao stockDao();

}
