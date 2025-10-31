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

public class LoginActivity extends AppCompatActivity {

    EditText txtEmailAddress, txtPassword;
    Button btnLogin, btnCancel;
    TextView txtGoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtEmailAddress = findViewById(R.id.txtEmailAddress);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnSignup);
        btnCancel = findViewById(R.id.btnCancel);
        txtGoRegister = findViewById(R.id.txtGoRegister);
    }
    public void LoginClick(View view) {
        String email = txtEmailAddress.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        // 🔍 Input validation
        if (email.isEmpty()) {
            txtEmailAddress.setError("Email is required");
            txtEmailAddress.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmailAddress.setError("Enter a valid email");
            txtEmailAddress.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            txtPassword.setError("Password is required");
            txtPassword.requestFocus();
            return;
        }

        Database db = new Database(this);
        boolean isValid = db.validateUser(email, password);


        if (isValid) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DashboardActivity.class));
            finish(); // Optional: Prevent back to login on back button
        } else {
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
        }
    }

    public void  Cancel(View view){
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

    public void RegisterOnClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
