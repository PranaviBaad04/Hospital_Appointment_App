package com.example.wellcare;

public class Doctor {
    private String name;
    private String speciality;
    private String experience;
    private String address;
    private int imageResId;

    public Doctor(String name, String speciality, String experience, String address, int imageResId) {
        this.name = name;
        this.speciality = speciality;
        this.experience = experience;
        this.address = address;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getSpeciality() { return speciality; }
    public String getExperience() { return experience; }
    public String getAddress() { return address; }
    public int getImageResId() { return imageResId; }
}

