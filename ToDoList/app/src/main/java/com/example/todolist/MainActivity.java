package com.example.todolist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.adapter.TaskListRecyclerView;
import com.example.todolist.design.AddNewItemDialog;
import com.example.todolist.entity.TasksEntity;
import com.example.todolist.model.TaskDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton addBtn;
    TaskDBHelper taskDbHelper;

    ImageView delImg, editImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.taskRecyclerView);
        addBtn = findViewById(R.id.fab);
        taskDbHelper = TaskDBHelper.getDB(this);


        addBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AddNewItemDialog.showDialog(MainActivity.this, taskDbHelper);
           }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskDbHelper.tasksDao().getAllTasks().observe(this, new Observer<List<TasksEntity>>() {
            @Override
            public void onChanged(List<TasksEntity> tasksEntity) {
                TaskListRecyclerView taskAdapter = new TaskListRecyclerView(MainActivity.this, tasksEntity, taskDbHelper);
                recyclerView.setAdapter(taskAdapter);
            }
        });


    }

}