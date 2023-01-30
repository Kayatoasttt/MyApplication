package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signIn extends AppCompatActivity implements View.OnClickListener{

    private EditText inputEmail, inputPassword;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        Button signUpToggleBtn = (Button)findViewById(R.id.signUpToggleButton);
        signUpToggleBtn.setOnClickListener(this);

        TextView forgotPwd = (TextView)findViewById(R.id.forgotPwd);
        forgotPwd.setOnClickListener(this);

        Button afterSignInbtn = (Button)findViewById(R.id.signIn);
        afterSignInbtn.setOnClickListener(this);

        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
    switch(view.getId()) {
        case R.id.signUpToggleButton:
            startActivity(new Intent(signIn.this, signUp.class));
            break;
        case R.id.forgotPwd:
            startActivity(new Intent(signIn.this, forgetPwEmail.class));
            break;
        case R.id.signIn:
            userLogin();
            break;
    }
    }

    private void userLogin() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if(email.isEmpty()){
            inputEmail.setError("Email is required");
            inputEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError("Please provide a valid email");
            inputEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            inputPassword.setError("Password is required");
            inputPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            inputPassword.setError("Password should be at least 6 characters");
            inputPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to user profile
                    startActivity(new Intent(signIn.this, home_screen.class));
                }else{
                    Toast.makeText(signIn.this, "Login Failed, Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}