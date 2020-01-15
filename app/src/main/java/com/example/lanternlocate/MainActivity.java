package com.example.lanternlocate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

        public void onUser(View view) {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        }
    public void onDriver(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    }

