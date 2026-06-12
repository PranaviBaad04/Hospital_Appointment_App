package com.example.wellcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DoctorsActivity extends AppCompatActivity {

    private static final String TAG = "DoctorsActivity";
    private FirebaseFirestore db;
    private DoctorAdapter adapter;
    private List<Doctor> doctorsList = new ArrayList<>();
    private List<Doctor> filteredDoctorsList = new ArrayList<>();
    private String speciality;

    private RecyclerView recyclerView;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        db = FirebaseFirestore.getInstance();

        // Get and normalize specialty
        String rawSpeciality = getIntent().getStringExtra("speciality");
        speciality = normalizeSpeciality(rawSpeciality);
        Log.d(TAG, "Normalized speciality: " + speciality);

        if (speciality == null || speciality.isEmpty()) {
            Toast.makeText(this, "Specialty not specified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recyclerDoctorList);
        searchBar = findViewById(R.id.searchBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorAdapter(this, filteredDoctorsList, doctor -> {
            Intent intent = new Intent(DoctorsActivity.this, SlotsActivity.class);
            intent.putExtra("doctorName", doctor.getName());
            intent.putExtra("speciality", doctor.getSpeciality());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        setupSearchBar();
        setupBottomNavigation();
        fetchDoctorsFromFirestore();
    }

    private String normalizeSpeciality(String raw) {
        switch (raw) {
            case "GeneralSurgery":
                return "General Surgery";
            case "InternalMedicine":
                return "Internal Medicine";
            case "FamilyMedicine":
                return "Family Medicine";
            case "ObstetricsAndGynecology":
                return "Obstetrics and Gynecology";
            case "Pediatrics":
                return "Pediatrics";
            default:
                return raw;
        }
    }

    private void fetchDoctorsFromFirestore() {
        db.collection("Doctors").document(speciality)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            Log.d(TAG, "Document snapshot: " + document.getData());
                            processDoctorsData(document);
                        } else {
                            Log.w(TAG, "No such document for speciality: " + speciality);
                            Toast.makeText(this, "No doctors found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e(TAG, "Error getting document", task.getException());
                        Toast.makeText(this, "Failed to load doctors", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void processDoctorsData(DocumentSnapshot document) {
        try {
            doctorsList.clear();
            List<Map<String, Object>> doctors = (List<Map<String, Object>>) document.get("doctors");

            if (doctors != null && !doctors.isEmpty()) {
                for (Map<String, Object> doctorMap : doctors) {
                    String name = (String) doctorMap.getOrDefault("name", "Unknown Doctor");
                    String experience = (String) doctorMap.getOrDefault("experience", "Not specified");
                    String specialization = (String) doctorMap.getOrDefault("specialization", speciality);
                    String address = (String) doctorMap.getOrDefault("address", "Not provided");

                    int imageResId = R.drawable.female_doctor; // Replace as needed

                    doctorsList.add(new Doctor(name, specialization, experience, address, imageResId));
                }
                filteredDoctorsList.clear();
                filteredDoctorsList.addAll(doctorsList);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No doctors available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing doctor data", e);
            Toast.makeText(this, "Error loading doctor information", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSearchBar() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                String searchText = query.toString().toLowerCase();
                filteredDoctorsList.clear();
                for (Doctor doctor : doctorsList) {
                    if (doctor.getName().toLowerCase().contains(searchText) ||
                            doctor.getSpeciality().toLowerCase().contains(searchText) ||
                            doctor.getAddress().toLowerCase().contains(searchText)) {
                        filteredDoctorsList.add(doctor);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupBottomNavigation() {
        ImageView homeNav = findViewById(R.id.homeNav);
        ImageView specialityNav = findViewById(R.id.specialityNav);
        ImageView doctorNav = findViewById(R.id.doctorNav);
        ImageView appointmentNav = findViewById(R.id.appointmentNav);
        ImageView communityNav = findViewById(R.id.communityNav);

        homeNav.setOnClickListener(v -> startActivity(new Intent(this, PatientHomeActivity.class)));
        specialityNav.setOnClickListener(v -> startActivity(new Intent(this, SpecialityActivity.class)));
        doctorNav.setOnClickListener(v -> recreate()); // reload this activity
        appointmentNav.setOnClickListener(v -> startActivity(new Intent(this, AppointmentFormActivity.class)));
        communityNav.setOnClickListener(v -> startActivity(new Intent(this, CommunityActivity.class)));
    }
}
