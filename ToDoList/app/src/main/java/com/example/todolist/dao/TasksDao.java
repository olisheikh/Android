package com.example.todolist.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist.entity.TasksEntity;

import java.util.List;

import kotlinx.coroutines.scheduling.Task;


@Dao
public interface TasksDao {
    @Query(value = "select * from task_list")
    LiveData<List<TasksEntity>> getAllTasks();

    @Insert
    void addNewTask(TasksEntity task);

    @Delete
    void deleteTaskById(TasksEntity task);

    @Update
    void updateTask(TasksEntity task);

    @Query("select * from task_list where id = :taskId")
    TasksEntity getTaskById(int taskId);
}
