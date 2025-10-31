package com.example.luxevista;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ActivityNotification extends AppCompatActivity {

    private static ArrayList<String> notifications = new ArrayList<>(); // store notifications
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listView = findViewById(R.id.notificationList);

        // 🔔 Hardcoded notifications
        String[] notifications = {
                "🎉 Grand Opening Event – Join us this Friday for live music & cocktails!",
                "💸 Exclusive Discount – 20% off on Deluxe Rooms this weekend!",
                "🧖 Spa Update – New aromatherapy packages now available!",
                "🍽️ Dining – Chef’s Special Menu now available at Luxe Restaurant!",
                "🛎️ Service Notice – Room service available 24/7 starting this month."
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notifications
        );

        listView.setAdapter(adapter);
    }
}