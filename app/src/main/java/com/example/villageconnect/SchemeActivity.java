package com.example.villageconnect;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class SchemeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText etSearch;
    TextView txtCount;

    ArrayList<String> schemeList;
    SchemeAdapter adapter;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);

        recyclerView = findViewById(R.id.recyclerScheme);
        etSearch = findViewById(R.id.etSearch);
        txtCount = findViewById(R.id.txtCount);

        db = new DatabaseHelper(this);

        loadPersonalizedSchemes();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s,
                    int start,
                    int count,
                    int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s,
                    int start,
                    int before,
                    int count) {

                adapter.filter(s.toString());

                txtCount.setText(
                        adapter.getItemCount() + " schemes found"
                );
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void loadPersonalizedSchemes() {

        // Get the username of the currently logged-in user
        SharedPreferences preferences =
                getSharedPreferences(
                        "VillageConnect",
                        MODE_PRIVATE
                );

        String username =
                preferences.getString("username", "");

        // If no user is logged in
        if (username.isEmpty()) {

            schemeList = new ArrayList<>();

            adapter = new SchemeAdapter(
                    this,
                    schemeList
            );

            txtCount.setText("0 schemes found");

            return;
        }

        // Get the actual logged-in user's details
        Cursor cursor = db.getUser(username);

        if (cursor != null && cursor.moveToFirst()) {

            String occupation =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "occupation"
                            )
                    );

            String incomeText =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "income"
                            )
                    );

            int income = 0;

            try {
                income = (int) Double.parseDouble(incomeText.trim());
            } catch (Exception e) {
                income = 0;
            }

            String gender =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "gender"
                            )
                    );

            String state =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "state"
                            )
                    );

            String disability =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "disability"
                            )
                    );

            String land =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "land"
                            )
                    );

            String dob =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "dob"
                            )
                    );

            // Calculate the user's actual age
            int age = calculateAge(dob);

            // Get personalized schemes using the
            // user's actual profile information
            schemeList =
                    EligibilityEngine.getEligibleSchemes(
                            occupation,
                            income,
                            gender,
                            state,
                            disability,
                            land,
                            age
                    );

            cursor.close();

        } else {

            schemeList = new ArrayList<>();

            if (cursor != null) {
                cursor.close();
            }
        }

        // Create adapter with personalized schemes
        adapter = new SchemeAdapter(
                this,
                schemeList
        );

        // Update result count
        txtCount.setText(
                schemeList.size() + " schemes found"
        );
    }

    private int calculateAge(String dob) {

        // If DOB is empty
        if (dob == null || dob.trim().isEmpty()) {
            return 0;
        }

        try {

            // DOB format from RegisterActivity:
            // DD/MM/YYYY
            String[] parts = dob.split("/");

            int birthDay =
                    Integer.parseInt(parts[0]);

            int birthMonth =
                    Integer.parseInt(parts[1]);

            int birthYear =
                    Integer.parseInt(parts[2]);

            Calendar today =
                    Calendar.getInstance();

            int currentYear =
                    today.get(Calendar.YEAR);

            int currentMonth =
                    today.get(Calendar.MONTH) + 1;

            int currentDay =
                    today.get(Calendar.DAY_OF_MONTH);

            int age =
                    currentYear - birthYear;

            // If birthday has not happened this year,
            // subtract one year
            if (currentMonth < birthMonth ||
                    (currentMonth == birthMonth &&
                            currentDay < birthDay)) {

                age--;
            }

            return age;

        } catch (Exception e) {

            // If DOB cannot be read correctly
            return 0;
        }
    }
}