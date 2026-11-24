package com.example.villageconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private RadioGroup radioGroupRole;
    private Button btnLogin, btnRegister;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        radioGroupRole = findViewById(R.id.radioGroupRole);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Open Register Screen
        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        // Login
        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this,
                    "Enter username and password",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = radioGroupRole.getCheckedRadioButtonId();

        if(selectedId == -1){
            Toast.makeText(this,
                    "Please select a role",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(selectedId);
        String role = rb.getText().toString().trim();

        // ---------------- ADMIN ----------------

        if(role.equalsIgnoreCase("Admin")
                && username.equals("admin")
                && password.equals("admin123")){

            saveSession(username, role);

            NotificationHelper.addNotification(
                    this,
                    "Welcome, Admin! 👋",
                    "Welcome to Village Connect AI. You can manage users, schemes and applications."
            );

            Toast.makeText(this,
                    "Welcome Admin 👋",
                    Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, AdminActivity.class));
            finish();
            return;
        }

        // ---------------- USER / VOLUNTEER ----------------

        boolean success = databaseHelper.loginUser(username,password,role);

        if(success){

            saveSession(username, role);

            NotificationHelper.addNotification(
                    this,
                    "Welcome, " + username + "! 👋",
                    "Welcome to Village Connect AI. We're happy to have you here."
            );

            Toast.makeText(this,
                    "Welcome " + username + " 👋",
                    Toast.LENGTH_SHORT).show();

            if(role.equalsIgnoreCase("Volunteer")){
                startActivity(new Intent(this, VolunteerActivity.class));
            }else{
                startActivity(new Intent(this, DashboardActivity.class));
            }

            finish();

        }else{

            Toast.makeText(this,
                    "Invalid Username, Password or Role",
                    Toast.LENGTH_LONG).show();
        }
    }

    // ================= SESSION =================

    private void saveSession(String username,String role){

        SharedPreferences pref =
                getSharedPreferences("VillageConnect",MODE_PRIVATE);

        pref.edit()
                .putBoolean("isLoggedIn",true)
                .putString("username",username)
                .putString("role",role)
                .apply();
    }
}