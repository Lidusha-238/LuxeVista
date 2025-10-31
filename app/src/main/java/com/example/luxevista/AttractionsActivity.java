package com.example.luxevista;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AttractionsActivity extends AppCompatActivity {

    Database database;
    AttractionList adapter;
    ArrayList<Attraction> attractionList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attractions);

        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = new Database(this);
        listView = findViewById(R.id.attractionList);

        loadAttractions();

        // 🔔 Notification part
        String notification = getIntent().getStringExtra("notification");
        if (notification != null && !notification.isEmpty()) {
            showNotification("🎡 Attraction Notification", notification);
        }
    }

    private void loadAttractions() {
        attractionList = database.getAllAttractions();
        adapter = new AttractionList(this, attractionList);
        listView.setAdapter(adapter);
    }

    // Helper method to show notification
    private void showNotification(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
