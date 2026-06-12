package com.example.wellcare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BookedAppointmentsActivity extends AppCompatActivity {

    ImageView homeNav, bookedNav;
    LinearLayout patientListLayout;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_appointments);

        homeNav = findViewById(R.id.homeNav);
        bookedNav = findViewById(R.id.bookedNav);
        patientListLayout = findViewById(R.id.patientListLayout);

        firestore = FirebaseFirestore.getInstance();

        homeNav.setOnClickListener(v -> Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show());
        bookedNav.setOnClickListener(v -> Toast.makeText(this, "Community clicked", Toast.LENGTH_SHORT).show());

        fetchBookedAppointments();
    }

    private void fetchBookedAppointments() {
        firestore.collection("Patients").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    patientListLayout.removeAllViews();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        String firstName = snapshot.getString("firstName");
                        String lastName = snapshot.getString("lastName");
                        String dob = snapshot.getString("dob");
                        String address = snapshot.getString("address");
                        String reason = snapshot.getString("reason");
                        String slot = snapshot.getString("slot");

                        View patientCard = LayoutInflater.from(this).inflate(R.layout.patient_card_item, null);

                        ((TextView) patientCard.findViewById(R.id.nameText)).setText("Name: " + firstName + " " + lastName);
                        ((TextView) patientCard.findViewById(R.id.dobText)).setText("DOB: " + dob);
                        ((TextView) patientCard.findViewById(R.id.addressText)).setText("Address: " + address);
                        ((TextView) patientCard.findViewById(R.id.reasonText)).setText("Reason: " + reason);
                        ((TextView) patientCard.findViewById(R.id.slotText)).setText("Slot: " + slot);

                        patientListLayout.addView(patientCard);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show());
    }
}
