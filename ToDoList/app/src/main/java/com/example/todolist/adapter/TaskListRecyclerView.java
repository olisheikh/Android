package com.example.todolist.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.todolist.MainActivity;
import com.example.todolist.R;
import com.example.todolist.design.AddNewItemDialog;
import com.example.todolist.entity.TasksEntity;
import com.example.todolist.model.TaskDBHelper;

import java.util.List;

public class TaskListRecyclerView extends RecyclerView.Adapter<TaskListRecyclerView.ViewHolder>{

    Context context;
    List<TasksEntity> tasks;
    TaskDBHelper taskDBHelper;
    public TaskListRecyclerView(Context context, List<TasksEntity> tasks, TaskDBHelper taskDBHelper) {
        this.context = context;
        this.tasks = tasks;
        this.taskDBHelper = taskDBHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.design_recycler_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        TasksEntity tasksEntity = tasks.get(position);
        viewHolder.titleTxtView.setText(tasks.get(position).getTitle());

        int pos = viewHolder.getAbsoluteAdapterPosition();
            viewHolder.delImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskDBHelper.tasksDao().deleteTaskById(tasks.get(pos));
                    tasks.remove(pos);
                    notifyItemRemoved(pos);
                }
            });

            viewHolder.edImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNewItemDialog.showDialog(context, taskDBHelper);

                }
            });
        }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxtView;
        ImageView delImg, edImg;
        public ViewHolder(@NonNull View item) {
            super(item);
            titleTxtView = item.findViewById(R.id.titleTodoList);
            delImg = item.findViewById(R.id.delImg);
            edImg = item.findViewById(R.id.editImg);
        }
    }


    public void setTasks(List<TasksEntity> newTasks) {
        tasks.clear();
        tasks.addAll(newTasks);
        notifyDataSetChanged();
    }


}
