package com.example.sportsapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Sport> sports;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.sportOptionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sports = new ArrayList<> ();

        sports.add(new Sport("BasketBall", R.drawable.basketball));
        sports.add(new Sport("Footbass", R.drawable.football));
        sports.add(new Sport("Ping", R.drawable.ping));
        sports.add(new Sport("Tennis", R.drawable.tennis));
        sports.add(new Sport("Volley", R.drawable.volley));

        SportCustomAdapter adapter = new SportCustomAdapter(sports);
        recyclerView.setAdapter(adapter);
    }
}