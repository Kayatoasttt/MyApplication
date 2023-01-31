package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class home_screen extends AppCompatActivity {
    //vars
    private ArrayList<String> gameNames = new ArrayList<>();
    private ArrayList<String> gameIcons = new ArrayList<>();

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

        getImages();


    }

    protected void onClick(View view) {
        Log.d(TAG, view.getTransitionName());
    }

    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        gameIcons.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        gameNames.add("Havasu Falls");

        gameIcons.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        gameNames.add("Trondheim");

        gameIcons.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        gameNames.add("Portugal");

        gameIcons.add("https://i.redd.it/j6myfqglup501.jpg");
        gameNames.add("Rocky Mountain National Park");


        gameIcons.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        gameNames.add("Mahahual");

        gameIcons.add("https://i.redd.it/k98uzl68eh501.jpg");
        gameNames.add("Frozen Lake");


        gameIcons.add("https://i.redd.it/glin0nwndo501.jpg");
        gameNames.add("White Sands Desert");

        gameIcons.add("https://i.redd.it/obx4zydshg601.jpg");
        gameNames.add("Austrailia");

        gameIcons.add("https://i.imgur.com/ZcLLrkY.jpg");
        gameNames.add("Washington");

        initGamesRecyclerView();

    }

    private void initGamesRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.commonGamesRecycler);
        recyclerView.setLayoutManager(layoutManager);
        commonGamesRecyclerAdapter adapter = new commonGamesRecyclerAdapter(gameNames, gameIcons, this);
        recyclerView.setAdapter(adapter);
    }
}
