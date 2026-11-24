package com.example.villageconnect;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class VolunteerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText etSearch;

    DatabaseHelper db;

    ArrayList<Citizen> list;
    CitizenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerCitizen);
        etSearch = findViewById(R.id.etSearch);

        db = new DatabaseHelper(this);

        list = new ArrayList<>();

        loadCitizens();

        adapter = new CitizenAdapter(this,list);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,int a,int b,int c){}

            @Override
            public void onTextChanged(CharSequence s,int a,int b,int c){

                filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s){}
        });

    }

    private void loadCitizens(){

        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT * FROM users",
                null);

        while(cursor.moveToNext()){

            list.add(new Citizen(
                    cursor.getString(cursor.getColumnIndexOrThrow("fullname")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                    cursor.getString(cursor.getColumnIndexOrThrow("occupation")),
                    cursor.getString(cursor.getColumnIndexOrThrow("district"))
            ));

        }

        cursor.close();

    }

    private void filter(String text){

        ArrayList<Citizen> filtered = new ArrayList<>();

        for(Citizen c:list){

            if(c.getName().toLowerCase().contains(text.toLowerCase())){

                filtered.add(c);

            }

        }

        adapter = new CitizenAdapter(this,filtered);

        recyclerView.setAdapter(adapter);

    }
}