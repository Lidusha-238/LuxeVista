package com.example.luxevista;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Calendar;

public class ServiceBookingActivity extends AppCompatActivity {

    TextView txtServiceName, txtServicePrice;
    EditText txtGuestName, txtGuestEmail, txtGuestPhone, txtDate, txtTime;
    Button btnBookService;
    String serviceName;
    double servicePrice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_booking);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtServiceName = findViewById(R.id.txtServiceName);
        txtServicePrice = findViewById(R.id.txtServicePrice);
        txtGuestName = findViewById(R.id.txtName);
        txtGuestEmail = findViewById(R.id.txtEmail);
        txtGuestPhone = findViewById(R.id.txtPhone);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        btnBookService = findViewById(R.id.btnBookService);

        serviceName = getIntent().getStringExtra("serviceName");
        servicePrice = getIntent().getDoubleExtra("servicePrice", 0);

        txtServiceName.setText(serviceName);
        txtServicePrice.setText("$" + servicePrice);

        txtDate.setOnClickListener(v -> showDatePicker());
        txtTime.setOnClickListener(v -> showTimePicker());

        btnBookService.setOnClickListener(v -> bookService());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    String date = day + "/" + (month + 1) + "/" + year;
                    txtDate.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                    txtTime.setText(time);
                }, hour, minute, true);
        dialog.show();
    }

    private void bookService() {
        String guestName = txtGuestName.getText().toString();
        String guestEmail = txtGuestEmail.getText().toString();
        String guestPhone = txtGuestPhone.getText().toString();
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();

        if (guestName.isEmpty() || guestEmail.isEmpty() || guestPhone.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            return;
        }

        Database db = new Database(this);
        boolean success = db.insertServiceBooking(serviceName, guestName, guestEmail, guestPhone, date + " " + time, servicePrice);

        if (success) {
            Toast.makeText(this, "Reservation confirmed for " + guestName, Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Reservation failed. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
