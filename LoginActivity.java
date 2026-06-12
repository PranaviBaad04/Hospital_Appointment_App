package com.example.wellcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnPatient, btnDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnPatient = findViewById(R.id.btnPatient);
        btnDoctor = findViewById(R.id.btnDoctor);

        // Add scale effect on press
        btnPatient.setOnTouchListener(new ButtonTouchEffect());
        btnDoctor.setOnTouchListener(new ButtonTouchEffect());

        btnPatient.setOnClickListener(v -> {
            // Save role as Patient
            SharedPreferences.Editor editor = getSharedPreferences("WellCarePrefs", MODE_PRIVATE).edit();
            editor.putString("userRole", "Patient");
            editor.apply();

            // Navigate to PatientLoginActivity
            Intent intent = new Intent(LoginActivity.this, PatientLoginActivity.class);
            startActivity(intent);
        });

        btnDoctor.setOnClickListener(v -> {
            // Save role as Doctor
            SharedPreferences.Editor editor = getSharedPreferences("WellCarePrefs", MODE_PRIVATE).edit();
            editor.putString("userRole", "Doctor");
            editor.apply();

            // Navigate to DoctorLoginActivity
            Intent intent = new Intent(LoginActivity.this, DoctorLoginActivity.class);
            startActivity(intent);
        });
    }

    // Custom touch effect class
    public class ButtonTouchEffect implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ScaleAnimation scaleDown = new ScaleAnimation(
                            1.0f, 0.95f, 1.0f, 0.95f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleDown.setDuration(100);
                    scaleDown.setFillAfter(true);
                    view.startAnimation(scaleDown);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    ScaleAnimation scaleUp = new ScaleAnimation(
                            0.95f, 1.0f, 0.95f, 1.0f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleUp.setDuration(100);
                    scaleUp.setFillAfter(true);
                    view.startAnimation(scaleUp);
                    break;
            }
            return false;
        }
    }
}
