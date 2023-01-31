package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class notifications extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ImageButton btn = (ImageButton) findViewById(R.id.menuBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.menuNav);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(view -> {
            String selectedView = (String) view.getTitle();
            Log.d(TAG, selectedView +"heree");
            switch (selectedView) {
                case "Home":
                    startActivity(new Intent(this, home_screen.class));
                    break;
                case "Conversations":
                    startActivity(new Intent(this, messages.class));
                    break;
                case "Notifications":
                    startActivity(new Intent(this, notifications.class));
                    break;
                case "Settings":
                    startActivity(new Intent(this, settings.class));
                    break;
                default:
                    break;
            }


            return true;
        });
    }
}
