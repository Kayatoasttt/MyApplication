package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPwEmail extends AppCompatActivity {

    private EditText inputEmail;
    private Button continueBtn;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pw_email);

        inputEmail = findViewById(R.id.inputEmail);
        continueBtn = findViewById(R.id.continueBtn);

        auth = FirebaseAuth.getInstance();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        ImageView backBtn = findViewById(R.id.back_arrow);
        backBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(forgetPwEmail.this, signIn.class));
            }
        });
    }

    private void resetPassword() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty()) {
            inputEmail.setError("Email is required");
            inputEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Please provide valid email");
            inputEmail.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(forgetPwEmail.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(forgetPwEmail.this, forgetPwConfirm.class));
                } else {
                    Toast.makeText(forgetPwEmail.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


