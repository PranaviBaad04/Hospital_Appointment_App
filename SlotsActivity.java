package com.example.wellcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class SlotsActivity extends AppCompatActivity {

    LinearLayout slotContainer;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots);

        db = FirebaseFirestore.getInstance();
        slotContainer = findViewById(R.id.slot_container);

        String doctorName = getIntent().getStringExtra("doctorName");
        String specialityKey = getIntent().getStringExtra("speciality"); // Pass this from previous activity

        if (doctorName != null) {
            TextView title = findViewById(R.id.slot_title);
            if (title != null) {
                title.setText("Available Slots for " + doctorName);
            }

            fetchSlotsFromFirebase(doctorName, specialityKey);
        }

        // Bottom Nav Click Listeners
        findViewById(R.id.homeNav).setOnClickListener(v -> startActivity(new Intent(this, PatientHomeActivity.class)));
        findViewById(R.id.specialityNav).setOnClickListener(v -> startActivity(new Intent(this, SpecialityActivity.class)));
        findViewById(R.id.doctorNav).setOnClickListener(v -> startActivity(new Intent(this, DoctorsActivity.class)));
        findViewById(R.id.appointmentNav).setOnClickListener(v -> startActivity(new Intent(this, AppointmentFormActivity.class)));
        findViewById(R.id.communityNav).setOnClickListener(v -> startActivity(new Intent(this, CommunityActivity.class)));

        findViewById(R.id.menu_icon).setOnClickListener(v -> {
            Toast.makeText(this, "Menu clicked", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.profile_icon).setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
    }

    private void fetchSlotsFromFirebase(String doctorName, String specialityKey) {
        if (specialityKey == null) {
            Toast.makeText(this, "Speciality not provided", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("Slots")
                .document(specialityKey)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Map<String, Object>> doctors = (List<Map<String, Object>>) documentSnapshot.get("doctors");
                        if (doctors != null) {
                            for (Map<String, Object> doctor : doctors) {
                                String name = (String) doctor.get("name");
                                if (name != null && name.equalsIgnoreCase(doctorName)) {
                                    List<Map<String, String>> slots = (List<Map<String, String>>) doctor.get("slots");
                                    if (slots != null && !slots.isEmpty()) {
                                        for (Map<String, String> slot : slots) {
                                            addSlotToUI(slot.get("time"), slot.get("status"));
                                        }
                                    } else {
                                        Toast.makeText(this, "No slots available", Toast.LENGTH_SHORT).show();
                                    }
                                    return;
                                }
                            }
                            Toast.makeText(this, "No slots found for " + doctorName, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "No doctors found in Firestore", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Speciality document does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load slots", Toast.LENGTH_SHORT).show());
    }

    private void addSlotToUI(String time, String status) {
        View slotView = getLayoutInflater().inflate(R.layout.item_slot_card, null);
        TextView slotText = slotView.findViewById(R.id.slot_text);
        slotText.setText(time + "\nStatus: " + status);

        // Set color based on availability
        if ("Available".equalsIgnoreCase(status)) {
            slotText.setBackgroundColor(Color.parseColor("#C8E6C9")); // Light green background
        } else {
            slotText.setBackgroundColor(Color.parseColor("#FFCDD2")); // Light red background
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 16);
        slotView.setLayoutParams(layoutParams);

        slotView.setOnClickListener(v -> {
            if ("Available".equalsIgnoreCase(status)) {
                Intent intent = new Intent(SlotsActivity.this, AppointmentFormActivity.class);
                intent.putExtra("selectedSlot", time);
                startActivity(intent);
            } else {
                Toast.makeText(this, "This slot is already booked.", Toast.LENGTH_SHORT).show();
            }
        });

        slotContainer.addView(slotView);
    }
}
