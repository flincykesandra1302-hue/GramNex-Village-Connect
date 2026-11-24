package com.example.villageconnect;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class ApplicationTrackingActivity extends AppCompatActivity {

    TextView txtTrackingCitizen;
    TextView txtTrackingScheme;
    TextView txtApplicationId;
    TextView txtApplicationStatus;
    TextView txtStatusMessage;
    TextView txtProgressSteps;
    MaterialButton btnBackToVolunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_application_tracking);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        txtTrackingCitizen = findViewById(R.id.txtTrackingCitizen);
        txtTrackingScheme = findViewById(R.id.txtTrackingScheme);
        txtApplicationId = findViewById(R.id.txtApplicationId);
        txtApplicationStatus = findViewById(R.id.txtApplicationStatus);
        txtStatusMessage = findViewById(R.id.txtStatusMessage);
        txtProgressSteps = findViewById(R.id.txtProgressSteps);
        btnBackToVolunteer=findViewById(R.id.btnBackToVolunteer);
        btnBackToVolunteer.setOnClickListener(v->finish());

        String citizenName =
                getIntent().getStringExtra("citizenName");

        if (citizenName == null || citizenName.isEmpty()) {
            citizenName = "Citizen";
        }

        DatabaseHelper db = new DatabaseHelper(this);

        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT * FROM applications WHERE citizen_name=? ORDER BY id DESC LIMIT 1",
                new String[]{citizenName}
        );

        if (cursor.moveToFirst()) {

            int applicationId =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("id")
                    );

            String savedScheme =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("scheme_name")
                    );

            String status =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("status")
                    );

            txtTrackingCitizen.setText(
                    "Citizen: " + citizenName
            );

            txtTrackingScheme.setText(
                    "Scheme: " + savedScheme
            );

            txtApplicationId.setText(
                    "Application ID: VC-" + applicationId
            );

            txtApplicationStatus.setText(
                    status
            );

            txtStatusMessage.setText(
                    "Your application has been submitted successfully."
            );

            txtProgressSteps.setText(
                    "✓ Application Submitted\n" +
                            "2. Under Verification\n" +
                            "3. Processing\n" +
                            "4. Final Decision"
            );

        } else {

            txtTrackingCitizen.setText(
                    "Citizen: " + citizenName
            );

            txtTrackingScheme.setText(
                    "Scheme: No application found"
            );

            txtApplicationId.setText(
                    "Application ID: —"
            );

            txtApplicationStatus.setText(
                    "No Application"
            );

            txtStatusMessage.setText(
                    "No submitted application was found."
            );
        }

        cursor.close();
    }
}