package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

public class messages extends AppCompatActivity {
    // This page is to list down all the conversation that the user has had with other users
    // For now, I'll just list down all the users we have from firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}

