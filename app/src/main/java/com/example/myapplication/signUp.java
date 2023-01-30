package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private Button register;
    private EditText inputEmail, inputUsername, inputPassword, inputConfirmPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button signInTogglebtn = (Button)findViewById(R.id.signInToggleBtn);
        signInTogglebtn.setOnClickListener(this);

        register = (Button) findViewById(R.id.continueBtn);
        register.setOnClickListener(this);

        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputConfirmPassword = (EditText) findViewById(R.id.inputConfirmPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.signInToggleBtn:
                startActivity(new Intent(signUp.this, signIn.class));
                break;
            case R.id.continueBtn:
                registerUser();
        }
    }

    private void registerUser() {
        String email = inputEmail.getText().toString().trim();
        String username = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String confirmPassword = inputConfirmPassword.getText().toString().trim();

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

        if(username.isEmpty()){
            inputUsername.setError("Username is required");
            inputUsername.requestFocus();
            return;
        }

        if(password.isEmpty()){
            inputPassword.setError("Password is required");
            inputPassword.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            inputConfirmPassword.setError("Confirm Password is required");
            inputConfirmPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            inputPassword.setError("Password should be at least 6 characters");
            inputPassword.requestFocus();
            return;
        }

        if(!confirmPassword.equals(password)){
            inputConfirmPassword.setError("Passwords don't match");
            inputConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("UserCreate","success");
                            User user = new User(username, email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(signUp.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);

                                                // redirect to Login Layout
                                                startActivity(new Intent(signUp.this, signIn.class));
                                            }else{
                                                Toast.makeText(signUp.this, "Registration failed. Please try again", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }else{
                            Log.d("onComplete: Failed=",task.getException().getMessage());
                        }
                    }
                });
    }
}
