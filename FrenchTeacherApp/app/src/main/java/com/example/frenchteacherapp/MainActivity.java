package com.example.frenchteacherapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button blackBtn, greenBtn, redBtn, purpleBtn, yellowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blackBtn = findViewById(R.id.blackBtn);
        greenBtn = findViewById(R.id.greenBtn);
        redBtn = findViewById(R.id.redBtn);
        purpleBtn = findViewById(R.id.purpleBtn);
        yellowBtn = findViewById(R.id.yellowBtn);

        blackBtn.setOnClickListener(this);
        greenBtn.setOnClickListener(this);
        redBtn.setOnClickListener(this);
        purpleBtn.setOnClickListener(this);
        yellowBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int clicked = view.getId();

        if (clicked == R.id.blackBtn)
            playMusic(R.raw.black);
        else if (clicked == R.id.greenBtn)
            playMusic(R.raw.green);
        else if (clicked == R.id.redBtn)
            playMusic(R.raw.red);
        else if (clicked == R.id.purpleBtn)
            playMusic(R.raw.purple);
        else if (clicked == R.id.yellowBtn)
            playMusic(R.raw.yellow);

    }

    public void playMusic(int id) {
        MediaPlayer mediaPlayer = MediaPlayer.create(
                this,
                id
        );
        mediaPlayer.start();
    }
}