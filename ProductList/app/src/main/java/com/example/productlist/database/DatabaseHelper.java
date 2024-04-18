package com.example.productlist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.productlist.dao.BrandNameDao;
import com.example.productlist.entity.BrandNames;
import com.example.productlist.entity.Converters;
import com.example.productlist.entity.DailySales;

@Database(entities = {BrandNames.class, DailySales.class}, exportSchema = false, version = 2)
@TypeConverters(Converters.class)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "productdb";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDb(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract BrandNameDao brandNameDao();
}
