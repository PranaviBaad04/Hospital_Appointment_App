package com.example.wellcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class PatientHomeActivity extends AppCompatActivity {

    ImageView menuButton, notificationButton, profileButton;
    ImageView homeNav, specialityNav, doctorNav, appointmentNav, communityNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        // Top Bar Icons
        menuButton = findViewById(R.id.menuIcon);
        notificationButton = findViewById(R.id.notificationIcon);
        profileButton = findViewById(R.id.profileIcon);

        // Bottom Navigation Icons
        homeNav = findViewById(R.id.homeNav);
        specialityNav = findViewById(R.id.specialityNav);
        doctorNav = findViewById(R.id.doctorNav);
        appointmentNav = findViewById(R.id.appointmentNav);
        communityNav = findViewById(R.id.communityNav);

        // Menu Popup
        menuButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(PatientHomeActivity.this, menuButton);
            popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_about_us) {
                    startActivity(new Intent(PatientHomeActivity.this, AboutUsActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_contact) {
                    startActivity(new Intent(PatientHomeActivity.this, ContactActivity.class));
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        // Notification icon click
        notificationButton.setOnClickListener(v ->
                startActivity(new Intent(PatientHomeActivity.this, NotificationActivity.class))
        );

        // Profile icon click
        profileButton.setOnClickListener(v ->
                startActivity(new Intent(PatientHomeActivity.this, ProfileActivity.class))
        );

        // Bottom navigation clicks
        homeNav.setOnClickListener(v -> recreate()); // Refresh current page

        specialityNav.setOnClickListener(v ->
                startActivity(new Intent(PatientHomeActivity.this, SpecialityActivity.class))
        );

        doctorNav.setOnClickListener(v ->
                startActivity(new Intent(PatientHomeActivity.this, DoctorsActivity.class))
        );

        appointmentNav.setOnClickListener(v ->
                startActivity(new Intent(PatientHomeActivity.this, AppointmentFormActivity.class))
        );

        communityNav.setOnClickListener(v ->
                startActivity(new Intent(PatientHomeActivity.this, CommunityActivity.class))
        );
    }
}

