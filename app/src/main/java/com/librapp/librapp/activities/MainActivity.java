package com.librapp.librapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.librapp.librapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button myLibraryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLibraryButton = findViewById(R.id.myLibraryButton);
        myLibraryButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == myLibraryButton) {
            Intent intent = new Intent(MainActivity.this, MyLibraryActivity.class);
            startActivity(intent);
        }
    }
}
