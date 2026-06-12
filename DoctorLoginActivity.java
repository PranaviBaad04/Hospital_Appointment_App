package com.example.wellcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        etEmail = findViewById(R.id.etDoctorEmail);
        etPassword = findViewById(R.id.etDoctorPassword);
        btnLogin = findViewById(R.id.btnDoctorLogin);
        btnRegister = findViewById(R.id.btnDoctorRegister);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> loginDoctor());
        btnRegister.setOnClickListener(v -> registerDoctor());
    }

    private void loginDoctor() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    // If login is successful, go to DoctorHomeActivity
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DoctorLoginActivity.this, DoctorHomeActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void registerDoctor() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnSuccessListener(result -> {
                    if (result.getSignInMethods() != null && !result.getSignInMethods().isEmpty()) {
                        // If user is already registered, show a message and allow login
                        Toast.makeText(this, "You are already registered, please login", Toast.LENGTH_SHORT).show();
                    } else {
                        // Register the doctor if not already registered
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener(authResult -> {
                                    // If registration is successful, inform user to login
                                    Toast.makeText(this, "Registration Successful. Please login now.", Toast.LENGTH_SHORT).show();
                                    // Optionally, auto-login after successful registration
                                    loginDoctor();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
