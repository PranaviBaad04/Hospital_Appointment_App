package com.example.wellcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CommunityActivity extends AppCompatActivity {

    ImageView menuIcon;
    ImageView homeNav, specialityNav, doctorNav, appointmentNav, communityNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        // Top menu icon
        menuIcon = findViewById(R.id.menu_icon);

        // Bottom navigation icons
        homeNav = findViewById(R.id.homeNav);
        specialityNav = findViewById(R.id.specialityNav);
        doctorNav = findViewById(R.id.doctorNav);
        appointmentNav = findViewById(R.id.appointmentNav);
        communityNav = findViewById(R.id.communityNav); // optional: disable click here since it's the current page

        // Menu click listener (you can implement a popup or drawer if needed)
        menuIcon.setOnClickListener(v -> {
            // Example: show toast or open drawer
        });

        // Navigation click listeners
        homeNav.setOnClickListener(v ->
                startActivity(new Intent(CommunityActivity.this, PatientHomeActivity.class))
        );

        specialityNav.setOnClickListener(v ->
                startActivity(new Intent(CommunityActivity.this, SpecialityActivity.class))
        );

        doctorNav.setOnClickListener(v ->
                startActivity(new Intent(CommunityActivity.this, DoctorsActivity.class))
        );

        appointmentNav.setOnClickListener(v ->
                startActivity(new Intent(CommunityActivity.this, AppointmentFormActivity.class))
        );

        // Optional: no need to navigate to CommunityActivity again
        communityNav.setOnClickListener(v -> recreate()); // or disable it
    }
}
