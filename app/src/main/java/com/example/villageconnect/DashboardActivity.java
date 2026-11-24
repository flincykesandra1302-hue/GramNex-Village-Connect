package com.example.villageconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Random;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private ImageButton btnNotifications;

    private TextView txtUserName, txtUsersCount, txtSchemesCount;

    private RecyclerView recyclerDashboard;
    private MaterialButton btnExplore;

    private DatabaseHelper databaseHelper;

    private ArrayList<DashboardItem> list;
    private DashboardAdapter adapter;

    private String[] facts = {
            "PM-Kisan has supported over 12 crore farmers across India.",
            "Ayushman Bharat provides health coverage up to ₹5 lakh.",
            "Village Connect recommends schemes using AI eligibility.",
            "More than 5 lakh CSC centres serve rural citizens.",
            "Digital India is making government services available online."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        databaseHelper = new DatabaseHelper(this);

        initViews();
        setupToolbar();
        loadUser();
        loadStatistics();
        loadDashboardCards();
        setupButtons();
    }

    private void initViews() {

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        btnNotifications=findViewById(R.id.btnNotifications);

        txtUserName = findViewById(R.id.txtUserName);
        txtUsersCount = findViewById(R.id.txtUsersCount);
        txtSchemesCount = findViewById(R.id.txtSchemesCount);

        recyclerDashboard = findViewById(R.id.recyclerDashboard);
        btnExplore = findViewById(R.id.btnExplore);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupToolbar() {

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // ================= LOAD USER =================

    private void loadUser() {

        SharedPreferences pref =
                getSharedPreferences("VillageConnect", MODE_PRIVATE);

        String username = pref.getString("username", "");

        Cursor cursor = databaseHelper.getUser(username);

        if (cursor.moveToFirst()) {

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("fullname"));

            txtUserName.setText(name);

            // Drawer Header
            android.view.View header = navigationView.getHeaderView(0);

            TextView drawerName = header.findViewById(R.id.txtUserName);

            if (drawerName != null)
                drawerName.setText(name);
        }

        cursor.close();
    }

    // ================= LOAD STATISTICS =================

    private void loadStatistics() {

        txtUsersCount.setText(String.valueOf(databaseHelper.getTotalUsers()));

        Cursor cursor = databaseHelper.getSchemes();

        txtSchemesCount.setText(String.valueOf(cursor.getCount()));

        cursor.close();

        // Random fact (optional later)
        Random random = new Random();
        String fact = facts[random.nextInt(facts.length)];
    }

    // ================= DASHBOARD CARDS =================

    private void loadDashboardCards() {

        list = new ArrayList<>();

        list.add(new DashboardItem(
                "AI Assistant",
                "Smart Guidance",
                R.drawable.ic_ai));

        list.add(new DashboardItem(
                "Find Schemes",
                "Eligibility Check",
                R.drawable.ic_scheme));

        list.add(new DashboardItem(
                "OCR Scanner",
                "Scan Documents",
                R.drawable.ic_ocr));

        list.add(new DashboardItem(
                "Voice Search",
                "Talk Naturally",
                R.drawable.ic_mic));

        list.add(new DashboardItem(
                "Nearby Offices",
                "Google Maps",
                R.drawable.ic_map));

        list.add(new DashboardItem(
                "Profile",
                "Citizen Details",
                R.drawable.ic_profile));

        list.add(new DashboardItem(
                "Track Application",
                "Check Application Status",
                R.drawable.ic_scheme));


        adapter = new DashboardAdapter(this, list);

        recyclerDashboard.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerDashboard.setAdapter(adapter);
    }

    // ================= BUTTON =================

    private void setupButtons() {

        btnExplore.setOnClickListener(v ->
                startActivity(new Intent(this, SchemeActivity.class)));

        btnNotifications.setOnClickListener(v ->
                startActivity(new Intent(this, NotificationActivity.class)));
    }

    // ================= DRAWER MENU =================

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            drawerLayout.closeDrawers();

        } else if (id == R.id.nav_profile) {

            startActivity(new Intent(this, ProfileActivity.class));

        } else if (id == R.id.nav_ai) {

            startActivity(new Intent(this, AIChatActivity.class));

        } else if (id == R.id.nav_ocr) {

            startActivity(new Intent(this, OCRActivity.class));

        } else if (id == R.id.nav_map) {

            startActivity(new Intent(this, MapsActivity.class));

        } else if (id == R.id.nav_scheme) {

            startActivity(new Intent(this, SchemeActivity.class));

        } else if (id == R.id.nav_voice) {

            startActivity(new Intent(this, VoiceAssistantActivity.class));

        } else if (id == R.id.nav_logout) {

            SharedPreferences.Editor editor =
                    getSharedPreferences("VillageConnect", MODE_PRIVATE).edit();

            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        drawerLayout.closeDrawers();
        return true;
    }
}