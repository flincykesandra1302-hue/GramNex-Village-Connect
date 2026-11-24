package com.example.villageconnect;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class OCRActivity extends AppCompatActivity {

    private ImageView imagePreview;
    private TextView txtResult;
    private Uri imageUri;
    private String loggedUsername;

    // Gallery
    private final ActivityResultLauncher<String> galleryLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            imageUri = uri;
                            imagePreview.setImageURI(uri);
                            runOCR(uri);
                        }
                    });

    // Camera
    private final ActivityResultLauncher<Void> cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.TakePicturePreview(),
                    bitmap -> {
                        if (bitmap != null) {
                            imagePreview.setImageBitmap(bitmap);
                            runOCR(bitmap);
                        }
                    });

    // Camera permission
    private final ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    granted -> {
                        if (granted) {
                            cameraLauncher.launch(null);
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ocr);

        // Get logged-in username
        loggedUsername = getSharedPreferences(
                "VillageConnect",
                MODE_PRIVATE
        ).getString("username", "");

        // Find views
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        MaterialButton btnGallery = findViewById(R.id.btnGallery);
        MaterialButton btnCamera = findViewById(R.id.btnCamera);

        imagePreview = findViewById(R.id.imagePreview);
        txtResult = findViewById(R.id.txtResult);

        // Back button
        toolbar.setNavigationOnClickListener(v -> finish());

        // Gallery button
        btnGallery.setOnClickListener(v ->
                galleryLauncher.launch("image/*"));

        // Camera button
        btnCamera.setOnClickListener(v ->
                openCamera());
    }

    // =====================================================
    // CAMERA
    // =====================================================

    private void openCamera() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) {

            cameraLauncher.launch(null);

        } else {

            permissionLauncher.launch(
                    Manifest.permission.CAMERA
            );
        }
    }

    // =====================================================
    // OCR FROM GALLERY
    // =====================================================

    private void runOCR(Uri uri) {

        try {

            InputImage image =
                    InputImage.fromFilePath(this, uri);

            recognizeText(image);

        } catch (IOException e) {

            txtResult.setText(
                    "Error loading image."
            );
        }
    }

    // =====================================================
    // OCR FROM CAMERA
    // =====================================================

    private void runOCR(Bitmap bitmap) {

        InputImage image =
                InputImage.fromBitmap(bitmap, 0);

        recognizeText(image);
    }

    // =====================================================
    // TEXT RECOGNITION
    // =====================================================

    private void recognizeText(InputImage image) {

        txtResult.setText(
                "Scanning document..."
        );

        TextRecognition
                .getClient(
                        TextRecognizerOptions.DEFAULT_OPTIONS
                )
                .process(image)
                .addOnSuccessListener(
                        this::showResult
                )
                .addOnFailureListener(
                        e -> txtResult.setText(
                                "OCR Failed!\n" +
                                        e.getMessage()
                        )
                );
    }

    // =====================================================
    // SHOW OCR RESULT
    // =====================================================

    private void showResult(Text visionText) {

        String result = visionText.getText();

        if (result.isEmpty()) {

            txtResult.setText(
                    "No text found."
            );

            return;
        }

        // Display extracted text
        txtResult.setText(result);

        // Document types
        String[] documentTypes = {
                "Aadhaar",
                "Income Certificate",
                "Bank Passbook",
                "Land Document"
        };

        // Store selected document type
        final String[] selectedDocument = {null};

        // Document selection dialog
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Select Document Type")

                .setSingleChoiceItems(
                        documentTypes,
                        -1,
                        (dialog, which) -> {

                            selectedDocument[0] =
                                    documentTypes[which];
                        })

                .setNegativeButton(
                        "Cancel",
                        null
                )

                .setPositiveButton(
                        "Save",
                        (dialog, which) -> {

                            // Make sure user selected something
                            if (selectedDocument[0] == null) {

                                android.widget.Toast.makeText(
                                        this,
                                        "Please select a document type",
                                        android.widget.Toast.LENGTH_SHORT
                                ).show();

                                return;
                            }

                            boolean saved = false;

                            // Save document
                            if (!loggedUsername.isEmpty()) {

                                saved =
                                        new DatabaseHelper(this)
                                                .saveDocument(
                                                        loggedUsername,
                                                        selectedDocument[0],
                                                        result,
                                                        false
                                                );
                            }

                            // Show result
                            if (saved) {

                                android.widget.Toast.makeText(
                                        this,
                                        selectedDocument[0]
                                                + " details saved successfully",
                                        android.widget.Toast.LENGTH_SHORT
                                ).show();

                            } else {

                                android.widget.Toast.makeText(
                                        this,
                                        "Could not save document details",
                                        android.widget.Toast.LENGTH_SHORT
                                ).show();
                            }
                        })

                .show();
    }
}