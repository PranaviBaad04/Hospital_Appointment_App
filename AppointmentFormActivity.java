package com.example.wellcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AppointmentFormActivity extends AppCompatActivity {

    EditText firstName, lastName, phone, dob, address, email, reason;
    Button bookBtn;
    TextView selectedSlotText;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    DatabaseReference notifRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_form);

        // Firebase instances
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        notifRef = FirebaseDatabase.getInstance().getReference("Notifications");

        // Form fields
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        reason = findViewById(R.id.reason);
        bookBtn = findViewById(R.id.bookBtn);
        selectedSlotText = findViewById(R.id.selectedSlotText);

        String selectedSlot = getIntent().getStringExtra("selectedSlot");
        if (selectedSlot != null) {
            selectedSlotText.setText("Selected Slot: " + selectedSlot);
        }

        // Book Button Logic
        bookBtn.setOnClickListener(v -> {
            String fname = firstName.getText().toString().trim();
            String lname = lastName.getText().toString().trim();
            String ph = phone.getText().toString().trim();
            String dobStr = dob.getText().toString().trim();
            String addr = address.getText().toString().trim();
            String em = email.getText().toString().trim();
            String reas = reason.getText().toString().trim();

            if (fname.isEmpty() || lname.isEmpty() || ph.isEmpty() || dobStr.isEmpty() || addr.isEmpty() || em.isEmpty() || reas.isEmpty() || selectedSlot.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields and select a slot", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> patientData = new HashMap<>();
            patientData.put("firstName", fname);
            patientData.put("lastName", lname);
            patientData.put("phone", ph);
            patientData.put("dob", dobStr);
            patientData.put("address", addr);
            patientData.put("email", em);
            patientData.put("reason", reas);
            patientData.put("slot", selectedSlot);

            db.collection("Patients").add(patientData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Appointment Booked", Toast.LENGTH_SHORT).show();

                        // 🔔 Send appointment notification
                        if (firebaseAuth.getCurrentUser() != null) {
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            String notifId = notifRef.push().getKey();
                            if (notifId != null) {
                                notifRef.child(userId).child(notifId).setValue("Your appointment has been successfully booked for slot: " + selectedSlot);
                            }
                        }

                        clearFormFields();

                        Intent intent = new Intent(AppointmentFormActivity.this, ProfileActivity.class);
                        intent.putExtra("firstName", fname);
                        intent.putExtra("lastName", lname);
                        intent.putExtra("phone", ph);
                        intent.putExtra("dob", dobStr);
                        intent.putExtra("address", addr);
                        intent.putExtra("email", em);
                        intent.putExtra("reason", reas);
                        intent.putExtra("slot", selectedSlot);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to book appointment: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });

        // Setup navigation
        setupNavigation();
    }

    private void setupNavigation() {
        ImageView menuIcon = findViewById(R.id.menuIcon);
        ImageView profileIcon = findViewById(R.id.profileIcon);
        ImageView notificationIcon = findViewById(R.id.notificationIcon);

        profileIcon.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });

        notificationIcon.setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationActivity.class));
        });

        ImageView homeNav = findViewById(R.id.homeNav);
        ImageView specialityNav = findViewById(R.id.specialityNav);
        ImageView doctorNav = findViewById(R.id.doctorNav);
        ImageView appointmentNav = findViewById(R.id.appointmentNav);
        ImageView communityNav = findViewById(R.id.communityNav);

        homeNav.setOnClickListener(v -> {
            startActivity(new Intent(this, PatientHomeActivity.class));
        });

        specialityNav.setOnClickListener(v -> {
            startActivity(new Intent(this, SpecialityActivity.class));
        });

        doctorNav.setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorsActivity.class));
        });

        appointmentNav.setOnClickListener(v -> {
            startActivity(new Intent(this, AppointmentFormActivity.class));
        });

        communityNav.setOnClickListener(v -> {
            startActivity(new Intent(this, CommunityActivity.class));
        });
    }

    private void clearFormFields() {
        firstName.setText("");
        lastName.setText("");
        phone.setText("");
        dob.setText("");
        address.setText("");
        email.setText("");
        reason.setText("");
    }
}
