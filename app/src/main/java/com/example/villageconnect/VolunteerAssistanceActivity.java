package com.example.villageconnect;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class VolunteerAssistanceActivity extends AppCompatActivity {

    DatabaseHelper db;

    TextView txtCitizenName;
    TextView txtCitizenOccupation;
    TextView txtCitizenDistrict;
    TextView txtRecommendedSchemes;
    TextView txtProgress;

    TextView txtAadhaarStatus;
    TextView txtIncomeStatus;
    TextView txtBankStatus;
    TextView txtLandStatus;

    Spinner spinnerRecommendedScheme;

    CheckBox checkAadhaar;
    CheckBox checkBank;
    CheckBox checkIncome;
    CheckBox checkLand;

    MaterialButton btnAadhaarDetails;
    MaterialButton btnIncomeDetails;
    MaterialButton btnBankDetails;
    MaterialButton btnLandDetails;
    MaterialButton btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_volunteer_assistance);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        db = new DatabaseHelper(this);

        // Citizen information
        txtCitizenName = findViewById(R.id.txtCitizenName);
        txtCitizenOccupation = findViewById(R.id.txtCitizenOccupation);
        txtCitizenDistrict = findViewById(R.id.txtCitizenDistrict);
        txtRecommendedSchemes = findViewById(R.id.txtRecommendedSchemes);
        txtProgress = findViewById(R.id.txtProgress);

        // Document status
        txtAadhaarStatus = findViewById(R.id.txtAadhaarStatus);
        txtIncomeStatus = findViewById(R.id.txtIncomeStatus);
        txtBankStatus = findViewById(R.id.txtBankStatus);
        txtLandStatus = findViewById(R.id.txtLandStatus);

        // Scheme spinner
        spinnerRecommendedScheme =
                findViewById(R.id.spinnerRecommendedScheme);

        // Checkboxes
        checkAadhaar = findViewById(R.id.checkAadhaar);
        checkBank = findViewById(R.id.checkBank);
        checkIncome = findViewById(R.id.checkIncome);
        checkLand = findViewById(R.id.checkLand);

        // Document details buttons
        btnAadhaarDetails = findViewById(R.id.btnAadhaarDetails);
        btnIncomeDetails = findViewById(R.id.btnIncomeDetails);
        btnBankDetails = findViewById(R.id.btnBankDetails);
        btnLandDetails = findViewById(R.id.btnLandDetails);

        // Continue button
        btnContinue = findViewById(R.id.btnContinue);

        String citizenName =
                getIntent().getStringExtra("citizenName");

        if (citizenName == null || citizenName.isEmpty()) {

            Toast.makeText(
                    this,
                    "Citizen information not found",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        loadCitizen(citizenName);
        loadDocuments(citizenName);

        // Continue to application
        btnContinue.setOnClickListener(v -> {

            if (!checkAadhaar.isChecked()
                    && !checkBank.isChecked()
                    && !checkIncome.isChecked()
                    && !checkLand.isChecked()) {

                Toast.makeText(
                        this,
                        "No verified documents available. Please ask the citizen to scan the required document.",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }

            Object selectedItem =
                    spinnerRecommendedScheme.getSelectedItem();

            if (selectedItem == null) {

                Toast.makeText(
                        this,
                        "No eligible scheme available for this citizen.",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }

            String selectedScheme =
                    selectedItem.toString();

            txtProgress.setText(
                    "✓ Scheme Selected\n" +
                            "✓ Documents Checked\n" +
                            "✓ Application Form\n" +
                            "4. Application Submitted"
            );

            android.content.Intent intent =
                    new android.content.Intent(
                            VolunteerAssistanceActivity.this,
                            SchemeApplicationActivity.class
                    );

            intent.putExtra(
                    "citizenName",
                    citizenName
            );

            intent.putExtra(
                    "schemeName",
                    selectedScheme
            );

            startActivity(intent);
        });
    }

    private void loadCitizen(String citizenName) {

        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT * FROM users WHERE fullname=? LIMIT 1",
                new String[]{citizenName}
        );

        if (cursor.moveToFirst()) {

            String occupation =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("occupation")
                    );

            String district =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("district")
                    );

            String incomeText =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("income")
                    );

            String gender =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("gender")
                    );

            String state =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("state")
                    );

            String disability =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("disability")
                    );

            String land =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("land")
                    );

            String dob =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("dob")
                    );

            int income = 0;

            try {

                income =
                        (int) Double.parseDouble(
                                incomeText.trim()
                        );

            } catch (Exception e) {

                income = 0;
            }

            int age = calculateAge(dob);

            txtCitizenName.setText(citizenName);

            txtCitizenOccupation.setText(
                    "Occupation: " + occupation
            );

            txtCitizenDistrict.setText(
                    "District: " + district
            );

            ArrayList<String> schemes =
                    EligibilityEngine.getEligibleSchemes(
                            occupation,
                            income,
                            gender,
                            state,
                            disability,
                            land,
                            age
                    );

            ArrayAdapter<String> schemeAdapter =
                    new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_spinner_item,
                            schemes
                    );

            schemeAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
            );

            spinnerRecommendedScheme.setAdapter(
                    schemeAdapter
            );

            if (schemes.isEmpty()) {

                txtRecommendedSchemes.setText(
                        "No eligible schemes found."
                );

            } else {

                StringBuilder result =
                        new StringBuilder();

                for (String scheme : schemes) {

                    result.append("• ")
                            .append(scheme)
                            .append("\n");
                }

                txtRecommendedSchemes.setText(
                        result.toString()
                );
            }
        }

        cursor.close();
    }

    private int calculateAge(String dob) {

        if (dob == null || dob.trim().isEmpty()) {
            return 0;
        }

        try {

            String[] parts = dob.split("/");

            int birthDay =
                    Integer.parseInt(parts[0]);

            int birthMonth =
                    Integer.parseInt(parts[1]);

            int birthYear =
                    Integer.parseInt(parts[2]);

            java.util.Calendar today =
                    java.util.Calendar.getInstance();

            int currentYear =
                    today.get(java.util.Calendar.YEAR);

            int currentMonth =
                    today.get(java.util.Calendar.MONTH) + 1;

            int currentDay =
                    today.get(java.util.Calendar.DAY_OF_MONTH);

            int age =
                    currentYear - birthYear;

            if (currentMonth < birthMonth
                    || (currentMonth == birthMonth
                    && currentDay < birthDay)) {

                age--;
            }

            return age;

        } catch (Exception e) {

            return 0;
        }
    }

    private void loadDocuments(String citizenName) {

        Cursor userCursor =
                db.getReadableDatabase().rawQuery(
                        "SELECT username FROM users WHERE fullname=? LIMIT 1",
                        new String[]{citizenName}
                );

        if (!userCursor.moveToFirst()) {

            userCursor.close();
            return;
        }

        String citizenUsername =
                userCursor.getString(
                        userCursor.getColumnIndexOrThrow("username")
                );

        userCursor.close();

        Cursor cursor =
                db.getDocuments(citizenUsername);

        // Reset document status
        checkAadhaar.setChecked(false);
        checkBank.setChecked(false);
        checkIncome.setChecked(false);
        checkLand.setChecked(false);

        txtAadhaarStatus.setText("Not Uploaded");
        txtBankStatus.setText("Not Uploaded");
        txtIncomeStatus.setText("Not Uploaded");
        txtLandStatus.setText("Not Uploaded");

        if (cursor.moveToFirst()) {

            do {

                String documentType =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "document_type"
                                )
                        );

                int verified =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        "verified"
                                )
                        );

                if (documentType.equals("Aadhaar")) {

                    checkAadhaar.setChecked(
                            verified == 1
                    );

                    txtAadhaarStatus.setText(
                            verified == 1
                                    ? "Verified ✓"
                                    : "Pending Verification"
                    );

                } else if (documentType.equals(
                        "Bank Passbook")) {

                    checkBank.setChecked(
                            verified == 1
                    );

                    txtBankStatus.setText(
                            verified == 1
                                    ? "Verified ✓"
                                    : "Pending Verification"
                    );

                } else if (documentType.equals(
                        "Income Certificate")) {

                    checkIncome.setChecked(
                            verified == 1
                    );

                    txtIncomeStatus.setText(
                            verified == 1
                                    ? "Verified ✓"
                                    : "Pending Verification"
                    );

                } else if (documentType.equals(
                        "Land Document")) {

                    checkLand.setChecked(
                            verified == 1
                    );

                    txtLandStatus.setText(
                            verified == 1
                                    ? "Verified ✓"
                                    : "Pending Verification"
                    );
                }

            } while (cursor.moveToNext());
        }

        cursor.close();

        // Verification buttons
        checkAadhaar.setOnClickListener(v ->
                verifyDocument(
                        citizenUsername,
                        "Aadhaar",
                        checkAadhaar.isChecked()
                )
        );

        checkBank.setOnClickListener(v ->
                verifyDocument(
                        citizenUsername,
                        "Bank Passbook",
                        checkBank.isChecked()
                )
        );

        checkIncome.setOnClickListener(v ->
                verifyDocument(
                        citizenUsername,
                        "Income Certificate",
                        checkIncome.isChecked()
                )
        );

        checkLand.setOnClickListener(v ->
                verifyDocument(
                        citizenUsername,
                        "Land Document",
                        checkLand.isChecked()
                )
        );

        // Aadhaar details
        btnAadhaarDetails.setOnClickListener(v ->
                showDocumentDetails(
                        citizenUsername,
                        "Aadhaar",
                        "Aadhaar Details"
                )
        );

        // Income Certificate details
        btnIncomeDetails.setOnClickListener(v ->
                showDocumentDetails(
                        citizenUsername,
                        "Income Certificate",
                        "Income Certificate Details"
                )
        );

        // Bank Passbook details
        btnBankDetails.setOnClickListener(v ->
                showDocumentDetails(
                        citizenUsername,
                        "Bank Passbook",
                        "Bank Passbook Details"
                )
        );

        // Land Document details
        btnLandDetails.setOnClickListener(v ->
                showDocumentDetails(
                        citizenUsername,
                        "Land Document",
                        "Land Document Details"
                )
        );
    }

    private void showDocumentDetails(
            String citizenUsername,
            String documentType,
            String title) {

        Cursor cursor =
                db.getDocuments(citizenUsername);

        String extractedText = null;

        if (cursor.moveToFirst()) {

            do {

                String type =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "document_type"
                                )
                        );

                if (type.equals(documentType)) {

                    extractedText =
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                            "extracted_text"
                                    )
                            );

                    break;
                }

            } while (cursor.moveToNext());
        }

        cursor.close();

        if (extractedText == null
                || extractedText.trim().isEmpty()) {

            Toast.makeText(
                    this,
                    "No " + documentType + " details found",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(extractedText)
                    .setPositiveButton(
                            "Close",
                            null
                    )
                    .show();
        }
    }

    private void verifyDocument(
            String citizenUsername,
            String documentType,
            boolean verified) {

        boolean updated =
                db.updateDocumentVerification(
                        citizenUsername,
                        documentType,
                        verified
                );

        if (updated) {

            if (verified) {

                Toast.makeText(
                        this,
                        documentType +
                                " verified successfully",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        this,
                        documentType +
                                " marked as not verified",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            Toast.makeText(
                    this,
                    "No " +
                            documentType +
                            " document found",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}