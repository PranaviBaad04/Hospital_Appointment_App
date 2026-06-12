package com.example.wellcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    EditText firstName, lastName, dob, email, address, reason, slot;
    Button saveProfileBtn;
    ImageView menuIcon, notificationIcon;

    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "PatientProfile";
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email); // Added
        address = findViewById(R.id.address);
        reason = findViewById(R.id.reason);
        slot = findViewById(R.id.slot);
        saveProfileBtn = findViewById(R.id.saveProfileBtn);
        menuIcon = findViewById(R.id.menuIcon);
        notificationIcon = findViewById(R.id.notificationIcon);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        firestore = FirebaseFirestore.getInstance();

        // Load data from Intent or Firestore
        if (getIntent().hasExtra("firstName")) {
            loadFromIntent();
        } else {
            loadFromFirestore();
        }

        // Save button click listener
        saveProfileBtn.setOnClickListener(v -> saveProfile());

        // Notification icon click listener
        notificationIcon.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, NotificationActivity.class)));
    }

    private void loadFromIntent() {
        try {
            Intent intent = getIntent();
            String fName = intent.getStringExtra("firstName");
            String lName = intent.getStringExtra("lastName");
            String dobStr = intent.getStringExtra("dob");
            String emailStr = intent.getStringExtra("email");
            String addr = intent.getStringExtra("address");
            String reasonStr = intent.getStringExtra("reason");
            String slotStr = intent.getStringExtra("slot");

            if (!TextUtils.isEmpty(fName)) firstName.setText(fName);
            if (!TextUtils.isEmpty(lName)) lastName.setText(lName);
            if (!TextUtils.isEmpty(dobStr)) dob.setText(dobStr);
            if (!TextUtils.isEmpty(emailStr)) email.setText(emailStr);
            if (!TextUtils.isEmpty(addr)) address.setText(addr);
            if (!TextUtils.isEmpty(reasonStr)) reason.setText(reasonStr);
            if (!TextUtils.isEmpty(slotStr)) slot.setText(slotStr);

        } catch (Exception e) {
            Toast.makeText(this, "Error loading profile from Intent", Toast.LENGTH_SHORT).show();
            loadFromFirestore(); // Fallback
        }
    }

    private void loadFromFirestore() {
        String patientId = "User123"; // Replace with dynamic user ID if needed
        DocumentReference docRef = firestore.collection("Patients").document(patientId);

        docRef.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                firstName.setText(snapshot.getString("firstName"));
                lastName.setText(snapshot.getString("lastName"));
                dob.setText(snapshot.getString("dob"));
                email.setText(snapshot.getString("email")); // Fetch email
                address.setText(snapshot.getString("address"));
                reason.setText(snapshot.getString("reason"));
                slot.setText(snapshot.getString("slot"));
            } else {
                loadFromSharedPreferences();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to fetch profile from Firestore", Toast.LENGTH_SHORT).show();
            loadFromSharedPreferences();
        });
    }

    private void loadFromSharedPreferences() {
        firstName.setText(sharedPreferences.getString("firstName", ""));
        lastName.setText(sharedPreferences.getString("lastName", ""));
        dob.setText(sharedPreferences.getString("dob", ""));
        email.setText(sharedPreferences.getString("email", "")); // Load email
        address.setText(sharedPreferences.getString("address", ""));
        reason.setText(sharedPreferences.getString("reason", "Reason will appear here"));
        slot.setText(sharedPreferences.getString("slot", "No appointments yet"));
    }

    private void saveProfile() {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String dateOfBirth = dob.getText().toString().trim();
        String emailStr = email.getText().toString().trim(); // Save email
        String addr = address.getText().toString().trim();

        if (TextUtils.isEmpty(fName) || TextUtils.isEmpty(lName)
                || TextUtils.isEmpty(dateOfBirth) || TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(addr)) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstName", fName);
        editor.putString("lastName", lName);
        editor.putString("dob", dateOfBirth);
        editor.putString("email", emailStr);
        editor.putString("address", addr);
        editor.putString("reason", reason.getText().toString().trim());
        editor.putString("slot", slot.getText().toString().trim());
        editor.apply();

        Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
    }
}
