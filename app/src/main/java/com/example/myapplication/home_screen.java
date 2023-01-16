package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class home_screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newhome_screen);

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


    }

//    public void showPopup(View v){
//        PopupMenu popup = new PopupMenu(this, v);
//        popup.setOnMenuItemClickListener(this);
//        popup.inflate(R.menu.main_menu);
//        popup.show();
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.homeMenu:
//                Intent i = new Intent(this, home_screen.class);
//                startActivity(i);
//                return true;
//
//            default: return false;
//        }
//
//    }
}
