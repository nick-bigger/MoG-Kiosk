package com.example.mogkiosk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView img = findViewById(R.id.dev_photo_3);

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
