package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class account extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private Button btnResetPassword, btnDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        imgBack = findViewById(R.id.back_arrow);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        imgBack.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);
        btnDeleteAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.back_arrow:
                i = new Intent(this, settings.class);
                startActivity(i);
                break;
            case R.id.btnResetPassword:
                i = new Intent(this, forgetPwEmail.class);
                startActivity(i);
                break;
            case R.id.btnDeleteAccount:
                deleteAccount();
                break;
        }
    }

    private void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(account.this);
        builder.setTitle("Are you sure?");
        builder.setMessage("This action cannot be undone. All your data will be permanently deleted.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the action here
                // TODO: Delete user and all other data related to user from realtime database and authentication
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String emailString = email.substring(0, email.indexOf("@"));
                // Delete all messages(aka chatrooms) from the user
                FirebaseDatabase.getInstance().getReference().child("Messages").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            if (child.getKey().contains(emailString)) {
                                child.getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // Delete user from realtime database
                FirebaseDatabase.getInstance().getReference("User/" + FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // Delete user from authentication
                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(account.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(account.this, signIn.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(account.this, "Account deletion failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        builder.show();
    }
}
