package com.group4.gamecontrollershop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.group4.gamecontrollershop.database_helper.DatabaseHelper;

public class ProfileSetupActivity extends AppCompatActivity {

    private EditText editFullName, editUsername, editPhone;
    private ImageView avatarImage;
    private Button saveButton;
    private DatabaseHelper databaseHelper;
    private String avatarUrl; // Declare avatarUrl here

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup); // Use your layout file name

        // Initialize views
        editFullName = findViewById(R.id.fullname);
        editUsername = findViewById(R.id.username);
        editPhone = findViewById(R.id.phone);
        avatarImage = findViewById(R.id.avatarImage);
        saveButton = findViewById(R.id.saveButton);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Get Google Account information
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String fullName = account.getDisplayName();
            avatarUrl = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : ""; // Save avatarUrl

            // Set Full Name and Avatar Image
            editFullName.setText(fullName);
            // Load avatar image using Glide
            Glide.with(this)
                    .load(avatarUrl)
                    .placeholder(R.drawable.ic_profile) // Placeholder image
                    .into(avatarImage);
        }

        // Save button click listener
        saveButton.setOnClickListener(v -> saveUserProfile());
    }

    private void saveUserProfile() {
        String fullName = editFullName.getText().toString().trim();
        String username = editUsername.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        // Validate input fields
        if (fullName.isEmpty() || username.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save user data to database
        boolean isInserted = databaseHelper.insertUserProfile(fullName, username, phone, avatarUrl); // Use avatarUrl here
        if (isInserted) {
            Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileSetupActivity.this, MainActivity.class));
            finish(); // End this activity
        } else {
            Toast.makeText(this, "Failed to save profile", Toast.LENGTH_SHORT).show();
        }
    }
}
