package com.example.volumecalculate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Shape> shapeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        gridView.setNumColumns(2);

        shapeArrayList = new ArrayList<>();

        shapeArrayList.add(new Shape(R.drawable.sphere, "Sphere"));
        shapeArrayList.add(new Shape(R.drawable.cube, "Cube"));
        shapeArrayList.add(new Shape(R.drawable.cylinder, "Cylinder"));
        shapeArrayList.add(new Shape(R.drawable.prism, "Prism"));

        ShapeGridAdapter shapeGridAdapter = new ShapeGridAdapter(getApplicationContext(), shapeArrayList);
        gridView.setAdapter(shapeGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, VolumeActivity.class);

                intent.putExtra("Title", shapeArrayList.get(position).getTitle());

                startActivity(intent);
            }
        });
    }
}