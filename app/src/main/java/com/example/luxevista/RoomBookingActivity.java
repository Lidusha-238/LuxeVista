package com.example.luxevista;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RoomBookingActivity extends AppCompatActivity {

    EditText txtnameInput, txtemailInput, txtphoneInput, txtdateInput, txttimeInput, txtnightsInput;
    TextView txttotalPrice;
    Button btnconfirmBooking;
    Database db;
    String roomName;
    double roomPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booking);

        db = new Database(this);

        txtnameInput = findViewById(R.id.txtnameInput);
        txtemailInput = findViewById(R.id.txtemailInput);
        txtphoneInput = findViewById(R.id.txtphoneInput);
        txtdateInput = findViewById(R.id.txtdateInput);
        txttimeInput = findViewById(R.id.txttimeInput);
        txtnightsInput = findViewById(R.id.txtnightsInput);
        txttotalPrice = findViewById(R.id.txttotalPrice);
        btnconfirmBooking = findViewById(R.id.btnconfirmBooking);

        roomName = getIntent().getStringExtra("room_name");
        roomPrice = getIntent().getDoubleExtra("room_price", 0.0);

        // Date Picker
        txtdateInput.setOnClickListener(v -> showDatePicker());

        // Time Picker
        txttimeInput.setOnClickListener(v -> showTimePicker());

        txtnightsInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTotal();
            }
            @Override public void afterTextChanged(android.text.Editable s) {}
        });

        btnconfirmBooking.setOnClickListener(v -> confirmBooking());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    txtdateInput.setText(date);
                }, year, month, day);
        dialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                    txttimeInput.setText(time);
                }, hour, minute, true);
        dialog.show();
    }

    private void calculateTotal() {
        String nightsStr = txtnightsInput.getText().toString();
        if (!nightsStr.isEmpty()) {
            int nights = Integer.parseInt(nightsStr);
            double total = nights * roomPrice;
            txttotalPrice.setText("Total Price: $" + total);
        }
    }

    private void confirmBooking() {
        String name = txtnameInput.getText().toString();
        String email = txtemailInput.getText().toString();
        String phone = txtphoneInput.getText().toString();
        String date = txtdateInput.getText().toString();
        String time = txttimeInput.getText().toString();
        String nightsStr = txtnightsInput.getText().toString();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || date.isEmpty() || time.isEmpty() || nightsStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int nights = Integer.parseInt(nightsStr);
        double total = nights * roomPrice;

        new AlertDialog.Builder(this)
                .setTitle("Confirm Booking")
                .setMessage("Confirm your booking for $" + total + "?\nCheck-in: " + date + " at " + time)
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean success = db.saveBooking(roomName, name, email, phone, date + " " + time, nights, total);
                    if (success) {
                        Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Booking failed. Try again.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
