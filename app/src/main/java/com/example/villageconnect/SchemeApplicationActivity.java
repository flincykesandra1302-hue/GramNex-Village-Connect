package com.example.villageconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class SchemeApplicationActivity extends AppCompatActivity {

    DatabaseHelper db;

    String schemeName;

    TextView txtApplicationCitizen;
    TextView txtApplicationScheme;
    TextView txtDocumentsStatus;

    EditText etApplicationPhone;
    EditText etApplicationDistrict;
    EditText etApplicationIncome;

    MaterialButton btnSubmitApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_application);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        db = new DatabaseHelper(this);

        txtApplicationCitizen =
                findViewById(R.id.txtApplicationCitizen);

        txtApplicationScheme =
                findViewById(R.id.txtApplicationScheme);

        txtDocumentsStatus =
                findViewById(R.id.txtDocumentsStatus);

        etApplicationPhone =
                findViewById(R.id.etApplicationPhone);

        etApplicationDistrict =
                findViewById(R.id.etApplicationDistrict);

        etApplicationIncome =
                findViewById(R.id.etApplicationIncome);

        btnSubmitApplication =
                findViewById(R.id.btnSubmitApplication);

        String citizenName =
                getIntent().getStringExtra("citizenName");

        schemeName =
                getIntent().getStringExtra("schemeName");

        if (citizenName == null) {
            citizenName = "";
        }

        if (schemeName == null || schemeName.isEmpty()) {
            schemeName = "Selected Welfare Scheme";
        }

        txtApplicationCitizen.setText(
                citizenName
        );

        schemeName=schemeName.replace("For","for");

        txtApplicationScheme.setText(
                "Scheme: " + schemeName
        );

        txtDocumentsStatus.setText(
                "Documents will be checked before submission."
        );

        loadCitizenDetails(citizenName);

        btnSubmitApplication.setOnClickListener(v -> {

            String phone =
                    etApplicationPhone.getText()
                            .toString()
                            .trim();

            String district =
                    etApplicationDistrict.getText()
                            .toString()
                            .trim();

            String income =
                    etApplicationIncome.getText()
                            .toString()
                            .trim();

            if (phone.isEmpty() ||
                    district.isEmpty() ||
                    income.isEmpty()) {

                Toast.makeText(
                        this,
                        "Please complete all application details",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            String username = "";

            Cursor userCursor =
                    db.getReadableDatabase().rawQuery(
                            "SELECT username FROM users WHERE fullname=? LIMIT 1",
                            new String[]{txtApplicationCitizen.getText().toString()}
                    );

            if (userCursor.moveToFirst()) {
                username = userCursor.getString(
                        userCursor.getColumnIndexOrThrow("username")
                );
            }

            userCursor.close();

            Toast.makeText(
                    this,
                    "Application for"+ schemeName +"has been submitted successfully!",
                    Toast.LENGTH_LONG
            ).show();

            txtDocumentsStatus.setText(
                    "✓ Documents Verified\n" +
                            "✓ Application Details Completed\n" +
                            "✓ Application Submitted"
            );

            btnSubmitApplication.setText(
                    "Application Submitted"
            );

            btnSubmitApplication.setEnabled(false);

            Intent intent = new Intent(
                    SchemeApplicationActivity.this,
                    ApplicationTrackingActivity.class
            );

            intent.putExtra("citizenName",txtApplicationCitizen.getText().toString());
            intent.putExtra("schemeName", schemeName);

            startActivity(intent);
        });
    }

    private void loadCitizenDetails(String citizenName) {

        if (citizenName.isEmpty()) {
            return;
        }

        Cursor cursor =
                db.getReadableDatabase().rawQuery(
                        "SELECT * FROM users WHERE fullname=? LIMIT 1",
                        new String[]{citizenName}
                );

        if (cursor.moveToFirst()) {

            String phone =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "phone"
                            )
                    );

            String district =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "district"
                            )
                    );

            String income =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "income"
                            )
                    );

            etApplicationPhone.setText(phone);
            etApplicationDistrict.setText(district);
            etApplicationIncome.setText(income);
        }

        cursor.close();
    }
}