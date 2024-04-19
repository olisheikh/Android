package com.example.todolist.design;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todolist.R;
import com.example.todolist.entity.TasksEntity;
import com.example.todolist.model.TaskDBHelper;

public class AddNewItemDialog {

    public static void showDialog(Context context, TaskDBHelper taskDBHelper) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_new_item_dialog);
        Button cancelBtn, saveBtn;
        EditText edtxt;

        cancelBtn = dialog.findViewById(R.id.cancelDialogBtn);
        saveBtn = dialog.findViewById(R.id.saveDialogBtn);
        edtxt = dialog.findViewById(R.id.addNewItemInput);

        saveBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String title = edtxt.getText().toString();
               taskDBHelper.tasksDao().addNewTask(new TasksEntity(title));
               dialog.dismiss();
           }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void updateText(Context context, TaskDBHelper taskDBHelper, TasksEntity tasksEntity) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_new_item_dialog);

        Button updateBtn, cancelBtn;
        EditText editTxt;

        updateBtn = dialog.findViewById(R.id.saveDialogBtn);
        cancelBtn = dialog.findViewById(R.id.cancelDialogBtn);
        editTxt = dialog.findViewById(R.id.addNewItemInput);

        updateBtn.setText("Update");
        editTxt.setText(tasksEntity.getTitle());
        updateBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String newData = editTxt.getText().toString();
               tasksEntity.setTitle(newData);
               taskDBHelper.tasksDao().updateTask(tasksEntity);
               dialog.dismiss();
           }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
        });

        dialog.show();
    }
}
