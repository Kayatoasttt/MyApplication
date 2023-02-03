package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendsConversation extends AppCompatActivity {
    // This page is to list down all the conversation that the user has had with other users
    // For now, I'll just list down all the users we have from firebase

    private RecyclerView recyclerView;
    private ArrayList<User> users;
    private UsersAdapter userAdapter;
    UsersAdapter.OnUserClickListener onUserClickListener;
    private SwipeRefreshLayout swipeRefreshLayout;

    String currentUserImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_conversation);

        // Menu
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
            Log.d(TAG, selectedView +"here");
            switch (selectedView) {
                case "Home":
                    startActivity(new Intent(this, home_screen.class));
                    break;
                case "Conversations":
                    startActivity(new Intent(this, FriendsConversation.class));
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

        users = new ArrayList<>();
        recyclerView = findViewById(R.id.userRecyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllUser();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        onUserClickListener = new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(int position) {
                startActivity(new Intent(FriendsConversation.this, privateMessages.class)
                        .putExtra("recipient_name",users.get(position).getUsername())
                        .putExtra("recipient_email",users.get(position).getEmail())
                        .putExtra("recipient_img", users.get(position).getProfilePicture())
                        .putExtra("sender_img", currentUserImg)
                );
            }
        };

        getAllUser();
    }

    private void getAllUser() {
        users.clear(); // clear the list of users
        FirebaseDatabase.getInstance().getReference("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (!dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) { // won't add the current user to the list
                        users.add(dataSnapshot.getValue(User.class));
                    } else {
                        currentUserImg = dataSnapshot.getValue(User.class).getProfilePicture();
                    }
                }
                userAdapter = new UsersAdapter(users, FriendsConversation.this, onUserClickListener);
                recyclerView.setLayoutManager(new LinearLayoutManager(FriendsConversation.this));
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FriendsConversation.this, "Sorry we have issues in trying to fetch your friends please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

