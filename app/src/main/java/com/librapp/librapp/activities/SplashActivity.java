package com.librapp.librapp.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.librapp.librapp.R;

public class SplashActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 3000;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initializeVariables();

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(SplashActivity.this, MyLibraryActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
    }

    private void initializeVariables() {
        imageView = findViewById(R.id.imageSplash);
        imageView.setImageResource(R.mipmap.ic_splash);
    }
}
