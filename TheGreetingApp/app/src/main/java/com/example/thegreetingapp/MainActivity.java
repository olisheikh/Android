package com.example.thegreetingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edTxt;
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edTxt = findViewById(R.id.nameEdTxtView);
        submitBtn = findViewById(R.id.submitBtnView);

        submitBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String name = edTxt.getText().toString();

               Toast.makeText(MainActivity.this, "Welcome, " + name + "To Our App", Toast.LENGTH_LONG).show();
           }
        });
    }
}