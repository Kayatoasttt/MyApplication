package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        Button signUpBtn = (Button)findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(signIn.this, signUp.class));
            }
        });

        Button afterSignInbtn = (Button)findViewById(R.id.signIn);

        afterSignInbtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(signIn.this, home_screen.class));
            }
        });
    }
}