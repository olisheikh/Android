package com.example.luckynumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView txtView = findViewById(R.id.showNumTxtView);
        Button btn = findViewById(R.id.shareBtn);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        int randomNum = generateNumber();
        txtView.setText(String.format("%d", randomNum));

        btn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               shareData(name, randomNum);
           }
        });

    }

    public int generateNumber() {
        Random random = new Random();

        return random.nextInt(100);
    }

    public void shareData(String name, int randomNum) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_SUBJECT, name + " You got lucky");
        intent.putExtra(Intent.EXTRA_TEXT, "His lucky number is " + randomNum);

        startActivity(Intent.createChooser(intent, "Choose your medium"));
    }
}