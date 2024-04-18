package com.example.todolist.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todolist.dao.TasksDao;
import com.example.todolist.entity.TasksEntity;

@Database(entities = TasksEntity.class, exportSchema = false, version = 1)
public abstract class TaskDBHelper extends RoomDatabase {

    public static String DB_NAME = "todolistdb";
    public static TaskDBHelper instance;

    public static synchronized TaskDBHelper getDB(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, TaskDBHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract TasksDao tasksDao();
}
