package com.example.villageconnect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceAssistantActivity extends AppCompatActivity {

    Button btnSpeak;
    TextView txtSpeech, txtReply;

    private final ActivityResultLauncher<Intent> speechLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK &&
                                result.getData() != null) {

                            ArrayList<String> speech =
                                    result.getData().getStringArrayListExtra(
                                            RecognizerIntent.EXTRA_RESULTS);

                            if (speech != null && !speech.isEmpty()) {

                                String user = speech.get(0);

                                txtSpeech.setText(user);

                                String prompt =
                                        "You are Village Connect AI. " +
                                                "Answer simply. " +
                                                "Question: " + user;

                                ApiClient.askGemini(prompt,
                                        new ApiClient.GeminiCallback() {

                                            @Override
                                            public void onResponse(String text) {

                                                runOnUiThread(() ->
                                                        txtReply.setText(text));

                                            }

                                            @Override
                                            public void onError(String error) {

                                                runOnUiThread(() ->
                                                        txtReply.setText("Error: " + error));

                                            }
                                        });
                            }
                        }
                    });

    private final ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    granted -> {
                        if (granted) {
                            startVoiceRecognition();
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_assistant);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        btnSpeak = findViewById(R.id.btnSpeak);
        txtSpeech = findViewById(R.id.txtSpeech);
        txtReply = findViewById(R.id.txtReply);

        btnSpeak.setOnClickListener(v -> {

            if (!SpeechRecognizer.isRecognitionAvailable(this)) {
                txtReply.setText("Speech recognition not available.");
                return;
            }

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED) {

                startVoiceRecognition();

            } else {

                permissionLauncher.launch(
                        Manifest.permission.RECORD_AUDIO);

            }

        });

    }

    private void startVoiceRecognition() {

        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());

        intent.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Speak now...");

        speechLauncher.launch(intent);

    }
}