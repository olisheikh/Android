package com.example.volumecalculate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VolumeActivity extends AppCompatActivity {
    TextView titleTV, result;
    EditText edTxt;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        String title = getIntent().getStringExtra("Title");
        calculateVolume(title.toLowerCase());

    }

    public void calculateVolume(String title) {
        titleTV = findViewById(R.id.nameOfValume);
        edTxt = findViewById(R.id.radius);
        btn = findViewById(R.id.submitBtn);
        result = findViewById(R.id.showResult);

        titleTV.setText(title + " Volume");

        btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int radius = Integer.parseInt(edTxt.getText().toString());

               if (title.equals("sphere")) {
                   double volume = (4/3.0) * 3.1459 * radius * radius * radius;
                   result.setText(String.format("%f",volume));
               } else if (title.equals("cylinder")) {
                   double volume = 3.1416 * radius * radius * 10;
                   result.setText(String.format("%f", volume));
               } else if (title.equals("cube")) {
                   double volume = radius * radius * radius;
                   result.setText(String.format("%f", volume));
               } else if (title.equals("prism")) {
                   double volume = radius * 10;
                   result.setText(String.format("%f", volume));
               } else {
                   result.setText("0");
               }
           }
        });
    }
}