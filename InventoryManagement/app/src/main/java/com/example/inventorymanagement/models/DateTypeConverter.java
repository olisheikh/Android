package com.example.inventorymanagement.models;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long timeStamp) {
        return timeStamp == null ? null : new Date(timeStamp);
    }
}
