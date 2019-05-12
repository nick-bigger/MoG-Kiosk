package com.example.mogkiosk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.mogkiosk.LoginActivity;
import com.example.mogkiosk.R;

public class AboutActivity extends AppCompatActivity {
    private static int numClicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numClicks = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView img = findViewById(R.id.dev_photo_3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numClicks++;
                if (numClicks > 9) {
                    numClicks = 0;
                    startActivity(new Intent(AboutActivity.this, LoginActivity.class));
                }
            }
        });

    }




}
