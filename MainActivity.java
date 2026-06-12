package com.example.wellcare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                // User is already logged in, navigate to respective home based on saved role
                String role = getSharedPreferences("WellCarePrefs", MODE_PRIVATE)
                        .getString("userRole", "Patient");
                if (role.equals("Doctor")) {
                    startActivity(new Intent(MainActivity.this, DoctorHomeActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, PatientHomeActivity.class));
                }
            } else {
                // Not logged in, go to LoginActivity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
            finish();
        }, SPLASH_TIME);
    }
}
