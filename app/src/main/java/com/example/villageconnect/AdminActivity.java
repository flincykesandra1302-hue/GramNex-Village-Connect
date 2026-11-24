package com.example.villageconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class AdminActivity extends AppCompatActivity {

    TextView txtUsers, txtVolunteers, txtSchemes;

    Button btnManageSchemes, btnNotifications;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Database
        db = new DatabaseHelper(this);

        // Analytics TextViews
        txtUsers = findViewById(R.id.txtUsers);
        txtVolunteers = findViewById(R.id.txtVolunteers);
        txtSchemes = findViewById(R.id.txtSchemes);

        // Buttons
        btnManageSchemes = findViewById(R.id.btnManageSchemes);
        btnNotifications = findViewById(R.id.btnNotifications);

        // Load analytics
        loadAnalytics();

        // Open Manage Schemes
        btnManageSchemes.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this,
                    ManageSchemeActivity.class);
            startActivity(intent);
        });

        // Notifications (Next Module)
        btnNotifications.setOnClickListener(v -> {
            Toast.makeText(this,
                    "Notification module coming soon",
                    Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAnalytics();
    }

    private void loadAnalytics() {

        // Total Users
        Cursor userCursor = db.getReadableDatabase().rawQuery(
                "SELECT COUNT(*) FROM users WHERE role='User'",
                null);

        if (userCursor.moveToFirst()) {
            txtUsers.setText(userCursor.getString(0));
        }
        userCursor.close();

        // Total Volunteers
        Cursor volunteerCursor = db.getReadableDatabase().rawQuery(
                "SELECT COUNT(*) FROM users WHERE role='Volunteer'",
                null);

        if (volunteerCursor.moveToFirst()) {
            txtVolunteers.setText(volunteerCursor.getString(0));
        }
        volunteerCursor.close();

        // Total Schemes
        Cursor schemeCursor = db.getReadableDatabase().rawQuery(
                "SELECT COUNT(*) FROM schemes",
                null);

        if (schemeCursor.moveToFirst()) {
            txtSchemes.setText(schemeCursor.getString(0));
        }
        schemeCursor.close();
    }
}