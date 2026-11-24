package com.example.villageconnect;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private TextInputEditText etName, etPhone, etOccupation, etIncome;
    private Spinner spCountry, spState, spDistrict;
    private TextView txtOffice;
    private Button btnSaveProfile;

    private DatabaseHelper databaseHelper;
    private String loggedUsername = "";

    private String currentState = "";
    private String currentDistrict = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseHelper = new DatabaseHelper(this);

        toolbar = findViewById(R.id.toolbar);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etOccupation = findViewById(R.id.etOccupation);
        etIncome = findViewById(R.id.etIncome);

        spCountry = findViewById(R.id.spCountry);
        spState = findViewById(R.id.spState);
        spDistrict = findViewById(R.id.spDistrict);

        txtOffice = findViewById(R.id.txtOffice);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        toolbar.setNavigationOnClickListener(v -> finish());

        loadProfile();
        loadCountries();

        btnSaveProfile.setOnClickListener(v -> saveProfile());
    }

    // ================= LOAD PROFILE =================

    private void loadProfile() {

        SharedPreferences pref = getSharedPreferences("VillageConnect", MODE_PRIVATE);
        loggedUsername = pref.getString("username", "");

        Cursor cursor = databaseHelper.getUser(loggedUsername);

        if (cursor != null && cursor.moveToFirst()) {

            etName.setText(cursor.getString(cursor.getColumnIndexOrThrow("fullname")));
            etPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            etOccupation.setText(cursor.getString(cursor.getColumnIndexOrThrow("occupation")));
            etIncome.setText(cursor.getString(cursor.getColumnIndexOrThrow("income")));

            currentState = cursor.getString(cursor.getColumnIndexOrThrow("state"));
            currentDistrict = cursor.getString(cursor.getColumnIndexOrThrow("district"));
        }

        if (cursor != null) cursor.close();
    }

    // ================= SAVE PROFILE =================

    private void saveProfile() {

        boolean success = databaseHelper.updateUserProfile(
                loggedUsername,
                etName.getText().toString().trim(),
                etPhone.getText().toString().trim(),
                etOccupation.getText().toString().trim(),
                etIncome.getText().toString().trim(),
                spState.getSelectedItem().toString(),
                spDistrict.getSelectedItem().toString()
        );

        if(success){
            Toast.makeText(this,"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Update Failed",Toast.LENGTH_SHORT).show();
        }
    }

    // ================= COUNTRY =================

    private void loadCountries() {

        String[] countries = {"India"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                countries);

        spCountry.setAdapter(adapter);

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadStates();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // ================= STATES =================

    private void loadStates() {

        String[] states = {
                "Karnataka",
                "Tamil Nadu",
                "Kerala",
                "Maharashtra",
                "Telangana"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                states);

        spState.setAdapter(adapter);

        int index = 0;
        for(int i=0;i<states.length;i++){
            if(states[i].equals(currentState)){
                index=i;
                break;
            }
        }

        spState.setSelection(index);

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDistricts(states[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // ================= DISTRICTS =================

    private void loadDistricts(String state) {

        String[] districts;

        switch (state){

            case "Karnataka":
                districts = new String[]{
                        "Bangalore Urban","Mysuru","Mangalore","Hubli","Belagavi"
                };
                break;

            case "Tamil Nadu":
                districts = new String[]{
                        "Chennai","Coimbatore","Madurai","Salem","Trichy"
                };
                break;

            case "Kerala":
                districts = new String[]{
                        "Kochi","Thrissur","Kannur","Kozhikode","Alappuzha"
                };
                break;

            case "Maharashtra":
                districts = new String[]{
                        "Mumbai","Pune","Nagpur","Nashik","Aurangabad"
                };
                break;

            default:
                districts = new String[]{
                        "Hyderabad","Warangal","Karimnagar","Nizamabad","Khammam"
                };
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                districts);

        spDistrict.setAdapter(adapter);

        int index = 0;
        for(int i=0;i<districts.length;i++){
            if(districts[i].equals(currentDistrict)){
                index=i;
                break;
            }
        }

        spDistrict.setSelection(index);

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showNearestOffice(districts[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // ================= OFFICES =================

    private void showNearestOffice(String district) {

        String office;

        switch (district){

            case "Bangalore Urban":
                office = "🏛 Bangalore Taluk Office\n📍 MG Road\n\n☎ 080-22345678\n\n🏥 Victoria Government Hospital";
                break;

            case "Mysuru":
                office = "🏛 Mysuru Taluk Office\n📍 Sayyaji Rao Road\n\n☎ 0821-2456789\n\n🏥 KR Government Hospital";
                break;

            case "Mangalore":
                office = "🏛 Dakshina Kannada Office\n📍 Hampankatta\n\n☎ 0824-2223456\n\n🏥 Wenlock Hospital";
                break;

            case "Chennai":
                office = "🏛 Chennai Collector Office\n📍 Marina\n\n☎ 044-22334455";
                break;

            case "Hyderabad":
                office = "🏛 Hyderabad Taluk Office\n📍 Lakdikapul\n\n☎ 040-22334455";
                break;

            default:
                office = "🏛 Local Government Office\n📍 District Headquarters";
        }

        txtOffice.setText(office);
    }
}