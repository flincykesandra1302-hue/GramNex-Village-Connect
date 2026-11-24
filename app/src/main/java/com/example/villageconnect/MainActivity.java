package com.example.villageconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    ImageView imgLogo;
    LinearLayout badgeAI;
    MaterialButton btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        imgLogo = findViewById(R.id.imgLogo);
        badgeAI = findViewById(R.id.badgeAI);
        btnJoin = findViewById(R.id.btnJoin);

        // Logo Animation
        Animation logoAnim =
                AnimationUtils.loadAnimation(this, R.anim.logo_intro);

        imgLogo.startAnimation(logoAnim);

        // Floating Badge Animation
        Animation floatAnim =
                AnimationUtils.loadAnimation(this, R.anim.floating);

        badgeAI.startAnimation(floatAnim);

        // Join Button
        btnJoin.setOnClickListener(v -> {

            Intent intent =
                    new Intent(MainActivity.this, LoginActivity.class);

            startActivity(intent);

            finish();

        });

    }
}