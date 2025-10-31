package com.example.luxevista;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    EditText txtName, txtEmail, txtPhone, txtPassword;
    Button btnSaveProfile, btnLogout;
    ListView lvBookingHistory;
    Database database;
    ArrayList<String> bookingHistory;
    ArrayAdapter<String> adapter;
    int userId = 1; // Example: currently logged-in user ID

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPassword);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnLogout = findViewById(R.id.btnLogout);
        lvBookingHistory = findViewById(R.id.lvBookingHistory);

        database = new Database(this);

        loadUserProfile();
        loadBookingHistory();

        lvBookingHistory.setOnItemClickListener((parent, view, position, id) -> {
            // Determine if it's a room booking or service booking
            Cursor roomCursor = database.getUserBookingsCursor(userId);
            Cursor serviceCursor = database.getUserServiceBookingsCursor(userId);

            int totalRoom = roomCursor.getCount();

            if (position < totalRoom) {
                // Room booking
                if (roomCursor.moveToPosition(position)) {
                    int bookingId = roomCursor.getInt(roomCursor.getColumnIndexOrThrow(Database.BOOK_ID));
                    String roomName = roomCursor.getString(roomCursor.getColumnIndexOrThrow(Database.BOOK_ROOM));
                    new AlertDialog.Builder(this)
                            .setTitle("Cancel Booking")
                            .setMessage("Cancel room booking: " + roomName + "?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                database.cancelBooking(bookingId);
                                loadBookingHistory();
                                Toast.makeText(this, "Room booking canceled", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            } else {
                // Service booking
                int servicePos = position - totalRoom;
                if (serviceCursor.moveToPosition(servicePos)) {
                    int bookingId = serviceCursor.getInt(serviceCursor.getColumnIndexOrThrow(Database.SERVICE_BOOKING_ID));
                    String serviceName = serviceCursor.getString(serviceCursor.getColumnIndexOrThrow(Database.SERVICE_BOOKING_NAME));
                    new AlertDialog.Builder(this)
                            .setTitle("Cancel Service Booking")
                            .setMessage("Cancel service: " + serviceName + "?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                database.cancelServiceBooking(bookingId);
                                loadBookingHistory();
                                Toast.makeText(this, "Service booking canceled", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
            roomCursor.close();
            serviceCursor.close();
        });

        btnSaveProfile.setOnClickListener(v -> saveUserProfile());
        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void loadUserProfile() {
        Cursor cursor = database.getUserByIdCursor(userId);
        if (cursor.moveToFirst()) {
            txtName.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.USER_NAME)));
            txtEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.USER_EMAIL)));
            txtPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.USER_PHONE)));
            txtPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow(Database.USER_PASSWORD)));
        }
        cursor.close();
    }

    private void loadBookingHistory() {
        bookingHistory = new ArrayList<>();

        // Load room bookings
        Cursor roomCursor = database.getUserBookingsCursor(userId);
        if (roomCursor.moveToFirst()) {
            do {
                String roomName = roomCursor.getString(roomCursor.getColumnIndexOrThrow(Database.BOOK_ROOM));
                String date = roomCursor.getString(roomCursor.getColumnIndexOrThrow(Database.BOOK_DATE));
                bookingHistory.add("Room - " + roomName + " (" + date + ")");
            } while (roomCursor.moveToNext());
        }
        roomCursor.close();

        // Load service bookings
        Cursor serviceCursor = database.getUserServiceBookingsCursor(userId);
        if (serviceCursor.moveToFirst()) {
            do {
                String serviceName = serviceCursor.getString(serviceCursor.getColumnIndexOrThrow(Database.SERVICE_BOOKING_NAME));
                String date = serviceCursor.getString(serviceCursor.getColumnIndexOrThrow(Database.SERVICE_DATE));
                bookingHistory.add("Service - " + serviceName + " (" + date + ")");
            } while (serviceCursor.moveToNext());
        }
        serviceCursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookingHistory);
        lvBookingHistory.setAdapter(adapter);
    }

    private void saveUserProfile() {
        String name = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        boolean updated = database.updateUser(userId, name, email, phone, password);
        if (updated) {
            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void logoutUser() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                    // Redirect to LoginActivity and clear the activity stack
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
