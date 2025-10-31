package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText txtFullName, txtEmail, txtPhoneNumber, txtPassword;
    Button btnRegister;
    TextView txtGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtFullName = findViewById(R.id.txtFullName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtGoLogin = findViewById(R.id.txtGoLogin);

        }
        public void Register(View view) {
            String name = txtFullName.getText().toString().trim();
            String email = txtEmail.getText().toString().trim();
            String phone = txtPhoneNumber.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            // Error handling
            if (name.isEmpty()) {
                txtFullName.setError("Full name is required");
                txtFullName.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                txtEmail.setError("Email is required");
                txtEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtEmail.setError("Enter a valid email");
                txtEmail.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                txtPhoneNumber.setError("Phone number is required");
                txtPhoneNumber.requestFocus();
                return;
            }

            if (phone.length() < 10) {
                txtPhoneNumber.setError("Enter a valid phone number");
                txtPhoneNumber.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                txtPassword.setError("Password is required");
                txtPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                txtPassword.setError("Password must be at least 6 characters");
                txtPassword.requestFocus();
                return;
            }

            Database db = new Database(this);
            if (db.checkUserExists(email)) {
                Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = db.insertUser(name, email, password, phone);
                if (inserted) {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish(); // Optional: close current activity
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }

    public void  Cancel(View view){
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

        public void LoginOnClick(View view) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }


