# 🏥 Hospital Appointment Booking App

## 📌 Project Overview

Hospital Appointment Booking App is an Android application that enables patients to easily book appointments with doctors. Users can select medical specialties, view available doctors, check appointment slots, and reserve appointments in real time.

The application helps hospitals and clinics manage appointments efficiently while providing a smooth booking experience for patients.

---

## 🚀 Features

### Patient Features

* User Registration and Login
* Browse Medical Specialties
* View Available Doctors
* Check Available Appointment Slots
* Book Appointments
* View Booking Status

### Doctor/Admin Features

* Manage Doctor Profiles
* Add and Update Available Slots
* View Appointments
* Manage Appointment Status

### System Features

* Real-Time Slot Availability Updates
* Prevention of Double Booking
* Secure User Authentication
* Cloud-Based Data Storage

---

## 🏗️ Architecture

### Frontend

* Android Application
* Java
* XML Layouts

### Backend

* Firebase Authentication
* Firebase Realtime Database / Firestore

### Database

Firebase stores:

* User Information
* Doctor Details
* Appointment Records

### Application Flow

```text
Android App
      ↓
Firebase Authentication
      ↓
Firebase Database
      ↓
Appointment Management
```

---

## 🛠️ Tech Stack

| Technology                             | Purpose                         |
| -------------------------------------- | ------------------------------- |
| Java                                   | Android Application Development |
| XML                                    | User Interface Design           |
| Firebase Authentication                | User Authentication             |
| Firebase Realtime Database / Firestore | Data Storage                    |
| Android Studio                         | Development Environment         |

---


## 🔄 Application Workflow

1. User logs into the application.
2. User selects a medical specialty.
3. Application fetches doctors from Firebase.
4. User selects a doctor.
5. Available appointment slots are displayed.
6. User books an appointment.
7. Appointment information is stored in Firebase.
8. Slot availability updates automatically.

---

## 🔐 Authentication & Authorization

### Authentication

Firebase Authentication using:

* Email and Password

### Authorization

#### Patient

* View doctors
* Check available slots
* Book appointments

#### Doctor/Admin

* Manage appointment slots
* View appointments
* Update appointment status

---

## 🗄️ Database Schema

### Users

| Field   | Type   |
| ------- | ------ |
| user_id | String |
| name    | String |
| email   | String |
| role    | String |

### Doctors

| Field           | Type       |
| --------------- | ---------- |
| doctor_id       | String     |
| specialty       | String     |
| available_slots | Array/List |

### Appointments

| Field          | Type   |
| -------------- | ------ |
| appointment_id | String |
| patient_id     | String |
| doctor_id      | String |
| slot_time      | String |
| booking_status | String |

---

## 📱 Screens

* Login Screen
* Registration Screen
* Specialty Selection Screen
* Doctor List Screen
* Slot Booking Screen
* Appointment Confirmation Screen

---

## ⚙️ Installation

### Prerequisites

* Android Studio
* Java JDK
* Firebase Project

### Steps

1. Clone the repository:

```bash
git clone https://github.com/your-username/hospital-appointment-booking-app.git
```

2. Open the project in Android Studio.

3. Connect Firebase:

   * Create a Firebase project.
   * Download `google-services.json`.
   * Place it inside the `app/` directory.

4. Sync Gradle files.

5. Run the application on an emulator or Android device.

---

## 🚧 Challenges Faced

* Managing real-time slot synchronization
* Preventing double bookings
* Firebase integration and database design
* Maintaining appointment consistency

---

## 🔮 Future Enhancements

* Video Consultation
* Online Payment Integration
* Push Notifications
* Appointment Reminders
* Doctor Ratings & Reviews
* Medical Record Management
* Multi-language Support

---

## 👨‍💻 Developer

**Pranavi Baad**

---

## 📄 License

This project is developed for educational and learning purposes.
