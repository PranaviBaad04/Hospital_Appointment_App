package com.example.wellcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wellcare.NotificationActivity;
import com.example.wellcare.ProfileActivity;
import com.example.wellcare.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DoctorHomeActivity extends AppCompatActivity {

    Button bookedAppointmentsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        bookedAppointmentsBtn = findViewById(R.id.btnBookedAppointments);

        bookedAppointmentsBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Navigating to Booked Appointments", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, BookedAppointmentsActivity.class));
        });


// Handle Home icon click
        ImageView homeNav = findViewById(R.id.homeNav);
        homeNav.setOnClickListener(v -> {
            // Handle Home item click
            Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, DoctorHomeActivity.class)); // Example of navigating to HomeActivity
        });


        ImageView communityNav = findViewById(R.id.bookedNav);
        communityNav.setOnClickListener(v -> {
            // Handle Community item click
            Toast.makeText(this, "Community clicked", Toast.LENGTH_SHORT).show();
            // You can navigate to the community screen or perform other actions
            startActivity(new Intent(this, CommunityActivity.class));
        });

    }
}
