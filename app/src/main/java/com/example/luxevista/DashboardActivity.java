package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout btnRooms, btnServices, btnAttractions, btnProfile;
    TextView txtWelcome;
    ImageButton btnNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // Adjust for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI
        btnRooms = findViewById(R.id.btnRooms);
        btnServices = findViewById(R.id.btnServices);
        btnAttractions = findViewById(R.id.btnAttractions);
        btnProfile = findViewById(R.id.btnProfile);
        txtWelcome = findViewById(R.id.txtWelcome);
        btnNotifications = findViewById(R.id.btnNotifications);

        // 🔔 Notification button click
        btnNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ActivityNotification.class);
            startActivity(intent);
        });
    }

    // 🏨 Navigate to Rooms
    public void openRooms(View view) {
        String notification = "🛏️ Room Service: 20% off Deluxe Rooms this weekend!";
        Intent intent = new Intent(this, RoomsActivity.class);
        intent.putExtra("notification", notification);
        startActivity(intent);
    }

    // 💆 Navigate to Services
    public void openServices(View view) {
        String notification = "💆 Spa Services: New aromatherapy packages available!";
        Intent intent = new Intent(this, ServicesActivity.class);
        intent.putExtra("notification", notification);
        startActivity(intent);
    }

    // 🎡 Navigate to Attractions
    public void openAttractions(View view) {
        String notification = "🎡 Attractions: Free guided tour this weekend!";
        Intent intent = new Intent(this, AttractionsActivity.class);
        intent.putExtra("notification", notification);
        startActivity(intent);
    }

    // 👤 Navigate to Profile
    public void openProfile(View view) {
        String notification = "📌 Profile: Check your booking status and updates.";
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("notification", notification);
        startActivity(intent);
    }
}
