package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settings extends AppCompatActivity implements View.OnClickListener{

    private TextView txtUsername, txtEmail;
    private ImageView userImageAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ImageButton btn = (ImageButton) findViewById(R.id.menuBtn);

        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        userImageAccount = findViewById(R.id.userImageAccount);


        CardView account = (CardView) findViewById(R.id.cardViewAccount);
        CardView profile = (CardView) findViewById(R.id.cardViewProfile);
        CardView conversations = (CardView) findViewById(R.id.cardViewConversations);
        CardView privacy = (CardView) findViewById(R.id.cardViewPrivacy);
        CardView notifications = (CardView) findViewById(R.id.cardViewNotifications);
        CardView support = (CardView) findViewById(R.id.cardViewSupport);
        CardView about = (CardView) findViewById(R.id.cardViewAbout);
        CardView logout = (CardView) findViewById(R.id.cardViewLogout);

        account.setOnClickListener(this);
        profile.setOnClickListener(this);
        conversations.setOnClickListener(this);
        privacy.setOnClickListener(this);
        notifications.setOnClickListener(this);
        support.setOnClickListener(this);
        about.setOnClickListener(this);
        logout.setOnClickListener(this);

        // set username and email based on logged in user
        FirebaseDatabase.getInstance().getReference("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        txtUsername.setText(dataSnapshot.getValue(User.class).getUsername());
                        txtEmail.setText(dataSnapshot.getValue(User.class).getEmail());
                        Glide.with(settings.this).load(dataSnapshot.getValue(User.class).getProfilePicture()).error(R.drawable.default_profile).placeholder(R.drawable.default_profile).into(userImageAccount);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(settings.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

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
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.cardViewAccount:
                i = new Intent(this, account.class);
                startActivity(i);
                break;
            case R.id.cardViewProfile:
                i = new Intent(this, editProfile.class);
                startActivity(i);
                break;
            case R.id.cardViewConversations:
                i = new Intent(this, FriendsConversation.class);
                startActivity(i);
                break;
            case R.id.cardViewNotifications:
                i = new Intent(this, notifications.class);
                startActivity(i);
                //this might need to be removed
                break;
            case R.id.cardViewLogout:
                // logout
                FirebaseAuth.getInstance().signOut();
                i = new Intent(this, signIn.class);
                startActivity(i);
                break;
            case R.id.cardViewSupport:
            case R.id.cardViewAbout:
            case R.id.cardViewPrivacy:
            default:
                Toast.makeText(this, "Sorry this feature has not been implemented yet", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
