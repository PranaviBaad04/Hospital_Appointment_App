// src/main/java/com/example/wellcare/NotificationActivity.java
package com.example.wellcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class NotificationActivity extends AppCompatActivity {

    private ImageView menuIcon, profileIcon;
    private TextView tvRegister, tvLogin, tvAppointment;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        menuIcon       = findViewById(R.id.menuIcon);
        profileIcon    = findViewById(R.id.profileIcon);
        tvRegister     = findViewById(R.id.tvRegisterNotification);
        tvLogin        = findViewById(R.id.tvLoginNotification);
        tvAppointment  = findViewById(R.id.tvAppointmentNotification);

        db = FirebaseFirestore.getInstance();

        // Load each notification
        loadNotification("Register", tvRegister);
        loadNotification("Login",    tvLogin);
        loadNotification("Appointment", tvAppointment);

        profileIcon.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class))
        );

        menuIcon.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, menuIcon);
            popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_about_us) {
                    startActivity(new Intent(this, AboutUsActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_contact) {
                    startActivity(new Intent(this, ContactActivity.class));
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }

    private void loadNotification(String docId, TextView target) {
        db.collection("Notifications")
                .document(docId)
                .get()
                .addOnSuccessListener(ds -> {
                    String msg = ds.getString("message");
                    if (msg != null && !msg.isEmpty()) {
                        target.setText(msg);
                    }
                })
                .addOnFailureListener(e ->
                        target.setText("Failed to load " + docId + ".")
                );
    }
}
