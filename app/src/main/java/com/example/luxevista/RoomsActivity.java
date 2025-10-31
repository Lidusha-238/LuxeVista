package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RoomsActivity extends AppCompatActivity {

    Database database;
    RoomList adapter;
    ArrayList<Room> roomList;
    ListView listView;
    Spinner filterSpinner, sortSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        // 🔔 Show notification if passed from Dashboard
        String notification = getIntent().getStringExtra("notification");
        if (notification != null && !notification.isEmpty()) {
            showNotification("🛏️ Room Notification", notification);
        }

        database = new Database(this);
        listView = findViewById(R.id.roomList);
        filterSpinner = findViewById(R.id.filterSpinner);
        sortSpinner = findViewById(R.id.sortSpinner);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{"All", "Suite", "Deluxe", "Family"});
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(filterAdapter);

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{"Default", "Price Low-High", "Price High-Low"});
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        loadRooms("All", "Default");

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadRooms(filterSpinner.getSelectedItem().toString(), sortSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        filterSpinner.setOnItemSelectedListener(listener);
        sortSpinner.setOnItemSelectedListener(listener);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Room room = roomList.get(position);
            Intent intent = new Intent(RoomsActivity.this, RoomBookingActivity.class);
            intent.putExtra("roomName", room.getName());
            intent.putExtra("roomPrice", room.getPrice());
            startActivity(intent);
        });
    }

    private void loadRooms(String filter, String sort) {
        roomList = database.getAllRooms(filter, sort);
        adapter = new RoomList(this, roomList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Room selected = roomList.get(position);
            Intent intent = new Intent(RoomsActivity.this, RoomDetailsActivity.class);
            intent.putExtra("name", selected.getName());
            intent.putExtra("desc", selected.getDescription());
            intent.putExtra("price", selected.getPrice());
            intent.putExtra("image", selected.getImage());
            intent.putExtra("type", selected.getType());
            intent.putExtra("availability", selected.getAvailability());
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
