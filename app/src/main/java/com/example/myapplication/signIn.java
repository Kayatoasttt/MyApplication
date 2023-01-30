package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class signIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        Button signUpBtn = (Button)findViewById(R.id.signUpToggleButton);

        signUpBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(signIn.this, signUp.class));
            }
        });

        TextView forgotPwd = (TextView)findViewById(R.id.forgotPwd);

        forgotPwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(signIn.this, forgetPwEmail.class));
            }
        });

        Button afterSignInbtn = (Button)findViewById(R.id.signIn);

        afterSignInbtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(signIn.this, home_screen
                        .class));
            }
        });
    }
}