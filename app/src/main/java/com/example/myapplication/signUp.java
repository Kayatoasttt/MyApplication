package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class signUp extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private Button register;
    private EditText inputEmail, inputUsername, inputPassword, inputConfirmPassword, inputAge;
    private ProgressBar progressBar;
    private Spinner inputGender;

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
        inputAge = (EditText) findViewById(R.id.inputAge);
        inputGender = (Spinner) findViewById(R.id.inputGender);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        List<String> options = Arrays.asList("Male", "Female", "Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputGender.setAdapter(adapter);
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
        String inputStrAge = inputAge.getText().toString().trim();
        int age = Integer.parseInt(inputStrAge);
        String gender = inputGender.getSelectedItem().toString().trim();
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

        if(age == 0){
            inputAge.setError("Age is required");
            inputAge.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("UserCreate","success");
                            User user = new User(username, age, gender, email);

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
                                                Log.d("unsuccessful signup","here1");
                                            }
                                        }
                                    });
                        }else{
                            inputEmail.setError(task.getException().getLocalizedMessage());
                            inputEmail.requestFocus();
                            return;
                        }
                    }
                });
    }
}
