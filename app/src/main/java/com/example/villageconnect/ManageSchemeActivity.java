package com.example.villageconnect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class ManageSchemeActivity extends AppCompatActivity {

    TextInputEditText etName, etCategory, etState;
    TextInputEditText etEligibility, etBenefits, etDeadline;

    Button btnSave;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_scheme);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        db = new DatabaseHelper(this);

        etName = findViewById(R.id.etSchemeName);
        etCategory = findViewById(R.id.etCategory);
        etState = findViewById(R.id.etState);
        etEligibility = findViewById(R.id.etEligibility);
        etBenefits = findViewById(R.id.etBenefits);
        etDeadline = findViewById(R.id.etDeadline);

        btnSave = findViewById(R.id.btnSaveScheme);

        btnSave.setOnClickListener(v -> saveScheme());

    }

    private void saveScheme() {

        String name = etName.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String state = etState.getText().toString().trim();
        String eligibility = etEligibility.getText().toString().trim();
        String benefits = etBenefits.getText().toString().trim();
        String deadline = etDeadline.getText().toString().trim();

        if(name.isEmpty() || category.isEmpty()){

            Toast.makeText(this,
                    "Fill required fields",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        boolean inserted = db.insertScheme(
                name,
                category,
                state,
                eligibility,
                benefits,
                deadline
        );

        if(inserted){

            Toast.makeText(this,
                    "Scheme Added Successfully",
                    Toast.LENGTH_LONG).show();

            finish();

        }else{

            Toast.makeText(this,
                    "Failed",
                    Toast.LENGTH_SHORT).show();

        }

    }

}