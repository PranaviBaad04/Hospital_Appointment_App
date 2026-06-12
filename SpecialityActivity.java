package com.example.wellcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecialityActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText searchBar;
    private List<SpecialityItem> specialitiesList = new ArrayList<>();

    // Layout containers for each specialty
    private LinearLayout cardiologyLayout, dermatologyLayout, neurologyLayout,
            orthopedicsLayout, pediatricsLayout, psychiatryLayout,
            radiologyLayout, surgeryLayout, urologyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialities);

        db = FirebaseFirestore.getInstance();
        searchBar = findViewById(R.id.searchSpeciality);

        // Initialize specialty layouts
        cardiologyLayout = findViewById(R.id.cardiology_layout);
        dermatologyLayout = findViewById(R.id.dermatology_layout);
        neurologyLayout = findViewById(R.id.neurology_layout);
        orthopedicsLayout = findViewById(R.id.orthopedics_layout);
        pediatricsLayout = findViewById(R.id.pediatrics_layout);
        psychiatryLayout = findViewById(R.id.psychiatry_layout);
        radiologyLayout = findViewById(R.id.radiology_layout);
        surgeryLayout = findViewById(R.id.surgery_layout);
        urologyLayout = findViewById(R.id.urology_layout);

        // Fetch specialties from Firestore
        fetchSpecialitiesFromFirestore();

        // Search bar filter
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSpecialities(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set specialty item click listeners
        setSpecialtyClickListeners();
    }

    private void fetchSpecialitiesFromFirestore() {
        db.collection("Specialities").document("specialitiesList")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        specialitiesList.clear();
                        Map<String, Object> specialitiesMap = documentSnapshot.getData();
                        if (specialitiesMap != null) {
                            for (Map.Entry<String, Object> entry : specialitiesMap.entrySet()) {
                                String name = entry.getValue().toString();
                                specialitiesList.add(new SpecialityItem(name));
                            }
                        }
                        filterSpecialities(searchBar.getText().toString().toLowerCase());
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error getting data", e));
    }

    private void filterSpecialities(String query) {
        if (specialitiesList.isEmpty()) return;

        for (SpecialityItem item : specialitiesList) {
            String specialtyName = item.getName().toLowerCase();
            boolean matchesQuery = specialtyName.contains(query);

            if (specialtyName.equalsIgnoreCase("cardiology")) {
                cardiologyLayout.setVisibility(matchesQuery ? View.VISIBLE : View.GONE);
            } else if (specialtyName.equalsIgnoreCase("dermatology")) {
                dermatologyLayout.setVisibility(matchesQuery ? View.VISIBLE : View.GONE);
            } else if (specialtyName.equalsIgnoreCase("neurology")) {
                neurologyLayout.setVisibility(matchesQuery ? View.VISIBLE : View.GONE);
            } else if (specialtyName.equalsIgnoreCase("orthopedics")) {
                orthopedicsLayout.setVisibility(matchesQuery ? View.VISIBLE : View.GONE);
            } else if (specialtyName.equalsIgnoreCase("pediatrics")) {
                pediatricsLayout.setVisibility(matchesQuery ? View.VISIBLE : View.GONE);
            } else if (specialtyName.equalsIgnoreCase("psychiatry")) {
                psychiatryLayout.setVisibility(matchesQuery ? View.VISIBLE : View.GONE);
            } else if (specialtyName.equalsIgnoreCase("radiology")) {
                radiologyLayout.setVisibility(matchesQuery ? View.VISIBLE : View.GONE);
            } else if (specialtyName.equalsIgnoreCase("general surgery")) {
                surgeryLayout.setVisibility(matchesQuery ? View.VISIBLE : View.GONE);
            } else if (specialtyName.equalsIgnoreCase("urology")) {
                urologyLayout.setVisibility(matchesQuery ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void setSpecialtyClickListeners() {
        cardiologyLayout.setOnClickListener(v -> startDoctorsActivity("Cardiology"));
        dermatologyLayout.setOnClickListener(v -> startDoctorsActivity("Dermatology"));
        neurologyLayout.setOnClickListener(v -> startDoctorsActivity("Neurology"));
        orthopedicsLayout.setOnClickListener(v -> startDoctorsActivity("Orthopedics"));
        pediatricsLayout.setOnClickListener(v -> startDoctorsActivity("Pediatrics"));
        psychiatryLayout.setOnClickListener(v -> startDoctorsActivity("Psychiatry"));
        radiologyLayout.setOnClickListener(v -> startDoctorsActivity("Radiology"));
        surgeryLayout.setOnClickListener(v -> startDoctorsActivity("General Surgery"));
        urologyLayout.setOnClickListener(v -> startDoctorsActivity("Urology"));
    }

    private void startDoctorsActivity(String specialty) {
        Intent intent = new Intent(SpecialityActivity.this, DoctorsActivity.class);
        intent.putExtra("speciality", specialty);
        startActivity(intent);
    }

    // Inner class for Speciality
    private static class SpecialityItem {
        private final String name;

        public SpecialityItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}