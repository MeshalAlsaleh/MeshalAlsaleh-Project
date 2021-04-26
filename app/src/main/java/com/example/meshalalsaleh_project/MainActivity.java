package com.example.meshalalsaleh_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Weather wt=new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnFirebase = (Button)findViewById(R.id.btnFirebase);
        Button btnDatabase = (Button)findViewById(R.id.btnDatabase);
        Button btnWeather = (Button)findViewById(R.id.btnWeather);
        ImageView imgWeather = (ImageView)findViewById(R.id.imgWeather);

        imgWeather.setImageResource(wt.getResurce());

        btnFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Firebase.class));

            }
        });

        btnDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Database.class));

            }
        });

        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Weather.class));

            }
        });


    }
}
