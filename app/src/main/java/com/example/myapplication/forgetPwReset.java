package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class forgetPwReset extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pw_reset);

        ImageView backBtn = findViewById(R.id.back_arrow);
        backBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(forgetPwReset.this, forgetPwEmail.class));
            }
        });

        Button continueBtn = (Button)findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(forgetPwReset.this, forgetPwConfirm.class));
            }
        });
    }
}