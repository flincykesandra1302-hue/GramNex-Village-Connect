package com.example.villageconnect;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class EligibilityActivity extends AppCompatActivity {

    TextView txtEligible;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligibility);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        txtEligible = findViewById(R.id.txtEligible);

        db = new DatabaseHelper(this);

        loadEligibility();

    }

    private void loadEligibility(){

        Cursor c = db.getReadableDatabase().rawQuery(
                "SELECT * FROM users ORDER BY id DESC LIMIT 1",
                null);

        if(c.moveToFirst()){

            String occupation = c.getString(c.getColumnIndexOrThrow("occupation"));

            int income = Integer.parseInt(
                    c.getString(c.getColumnIndexOrThrow("income")));

            String gender = c.getString(c.getColumnIndexOrThrow("gender"));

            String state = c.getString(c.getColumnIndexOrThrow("state"));

            String disability = c.getString(c.getColumnIndexOrThrow("disability"));

            String land = c.getString(c.getColumnIndexOrThrow("land"));

            int age = 30; // We'll calculate from DOB later

            ArrayList<String> schemes =
                    EligibilityEngine.getEligibleSchemes(
                            occupation,
                            income,
                            gender,
                            state,
                            disability,
                            land,
                            age);

            StringBuilder builder = new StringBuilder();

            builder.append("🎉 Great News!\n\n");

            builder.append("You are eligible for:\n\n");

            for(String s : schemes){

                builder.append("• ").append(s).append("\n");

            }

            txtEligible.setText(builder.toString());

        }

        c.close();

    }

}