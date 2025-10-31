package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ServicesActivity extends AppCompatActivity {

    Database database;
    ArrayList<Service> serviceList;
    ServiceList adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_services);

        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 🔔 Show notification if passed from Dashboard
        String notification = getIntent().getStringExtra("notification");
        if (notification != null && !notification.isEmpty()) {
            showNotification("💆 Service Notification", notification);
        }

        // Load services
        listView = findViewById(R.id.serviceList);
        database = new Database(this);
        serviceList = database.getAllServices();
        adapter = new ServiceList(this, serviceList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Service service = serviceList.get(position);
            Intent intent = new Intent(ServicesActivity.this, ServiceBookingActivity.class);
            intent.putExtra("serviceName", service.getName());
            intent.putExtra("servicePrice", service.getPrice());
            intent.putExtra("serviceDesc", service.getDesc());
            startActivity(intent);
        });
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
