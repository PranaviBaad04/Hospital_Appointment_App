package com.example.wellcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private Context context;
    private List<Doctor> doctorList;
    private OnDoctorClickListener listener;

    public interface OnDoctorClickListener {
        void onDoctorClick(Doctor doctor);
    }

    public DoctorAdapter(Context context, List<Doctor> doctorList, OnDoctorClickListener listener) {
        this.context = context;
        this.doctorList = doctorList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);

        holder.doctorName.setText(doctor.getName());
        holder.doctorSpeciality.setText(doctor.getSpeciality());
        holder.doctorExperience.setText(doctor.getExperience());
        holder.doctorImage.setImageResource(doctor.getImageResId());

        // Handle click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDoctorClick(doctor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView doctorImage;
        TextView doctorName, doctorSpeciality, doctorExperience;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorImage = itemView.findViewById(R.id.doctorImage);
            doctorName = itemView.findViewById(R.id.textViewDoctorName);
            doctorSpeciality = itemView.findViewById(R.id.doctorSpeciality);
            doctorExperience = itemView.findViewById(R.id.textViewSpecialization);

        }
    }
}

