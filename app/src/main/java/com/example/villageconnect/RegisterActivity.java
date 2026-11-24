package com.example.villageconnect;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private TextInputEditText etFullName, etUsername, etPhone, etEmail;
    private TextInputEditText etPassword, etConfirmPassword;
    private TextInputEditText etDOB, etOccupation, etIncome;
    private TextInputEditText etState, etDistrict, etFamilySize;

    private Spinner spGender, spLanguage, spDisability, spLand;
    private RadioGroup radioRole;
    private Button btnRegister;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Text Fields
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);

        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        etDOB = findViewById(R.id.etDOB);
        etOccupation = findViewById(R.id.etOccupation);
        etIncome = findViewById(R.id.etIncome);

        etState = findViewById(R.id.etState);
        etDistrict = findViewById(R.id.etDistrict);
        etFamilySize = findViewById(R.id.etFamilySize);

        // Spinners
        spGender = findViewById(R.id.spGender);
        spLanguage = findViewById(R.id.spLanguage);
        spDisability = findViewById(R.id.spDisability);
        spLand = findViewById(R.id.spLand);

        // Role
        radioRole = findViewById(R.id.radioRole);

        btnRegister = findViewById(R.id.btnRegister);

        loadSpinnerData();

        // Calendar DOB
        setupDatePicker();

        btnRegister.setOnClickListener(v -> registerUser());
    }

    // ================= SPINNERS =================

    private void loadSpinnerData() {

        spGender.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Male", "Female", "Other"}));

        spLanguage.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"English", "Kannada", "Hindi", "Tamil", "Telugu"}));

        spDisability.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"No", "Yes"}));

        spLand.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Yes", "No"}));
    }

    // ================= DATE PICKER =================

    private void setupDatePicker() {

        etDOB.setFocusable(false);
        etDOB.setClickable(true);

        etDOB.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    RegisterActivity.this,
                    (view, y, m, d) -> {

                        String dob = String.format("%02d/%02d/%04d",
                                d, m + 1, y);

                        etDOB.setText(dob);

                    },
                    year,
                    month,
                    day
            );

            // Prevent future dates
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

            dialog.show();
        });
    }

    // ================= REGISTER =================

    private void registerUser() {

        String fullname = etFullName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        String password = etPassword.getText().toString().trim();
        String confirm = etConfirmPassword.getText().toString().trim();

        String dob = etDOB.getText().toString().trim();
        String occupation = etOccupation.getText().toString().trim();
        String income = etIncome.getText().toString().trim();

        String state = etState.getText().toString().trim();
        String district = etDistrict.getText().toString().trim();
        String family = etFamilySize.getText().toString().trim();

        String gender = spGender.getSelectedItem().toString();
        String language = spLanguage.getSelectedItem().toString();
        String disability = spDisability.getSelectedItem().toString();
        String land = spLand.getSelectedItem().toString();

        int selectedId = radioRole.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this,
                    "Please select a role",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(selectedId);
        String role = rb.getText().toString();

        // Validation
        if (fullname.isEmpty() ||
                username.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty()) {

            Toast.makeText(this,
                    "Please fill all required fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (dob.isEmpty()) {
            Toast.makeText(this,
                    "Please select your Date of Birth",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirm)) {
            Toast.makeText(this,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        boolean inserted = databaseHelper.insertUser(
                fullname,
                username,
                phone,
                email,
                password,
                gender,
                dob,
                occupation,
                income,
                state,
                district,
                language,
                disability,
                land,
                family,
                role
        );

        if (inserted) {

            Toast.makeText(this,
                    "Registration Successful 🎉",
                    Toast.LENGTH_LONG).show();

            finish();

        } else {

            Toast.makeText(this,
                    "Username already exists",
                    Toast.LENGTH_LONG).show();
        }
    }
}