package com.group4.gamecontrollershop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.group4.gamecontrollershop.database_helper.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    SQLiteDatabase myDB;

    private EditText username, password;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialize views
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        // Initialize SQLite Database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        myDB = dbHelper.getWritableDatabase();

        // Configure Google Sign-In
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, options);

        // Google Sign-In button
        findViewById(R.id.google).setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            activityResultLauncher.launch(signInIntent);


        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    // ActivityResultLauncher for Google Sign-In
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        if (account != null) {
                            handleGoogleSignInResult(account);
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Google Sign-In failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    // Handle Google Sign-In result
    private void handleGoogleSignInResult(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String googleId = account.getId(); // Google unique ID
                String email = account.getEmail();
                String avatarUrl = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";

                // Check if user exists in local DB
                if (!isGoogleUserExists(googleId)) {
                    // If not, insert the new user into the local database
                    insertGoogleUser(googleId, email, avatarUrl);
                }

                // Navigate to MainActivity
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish(); // End this activity
            } else {
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // This method is triggered when the user clicks on the Sign Up link
    public void onSignUpClick(View view) {
        // Navigate to the RegisterActivity
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    // Check if Google user already exists in the local database
    private boolean isGoogleUserExists(String googleId) {
        String query = "SELECT * FROM User WHERE googleId = ?";
        Cursor cursor = myDB.rawQuery(query, new String[]{googleId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Insert new Google user into the local database
    private void insertGoogleUser(String googleId, String email, String avatarUrl) {
        ContentValues values = new ContentValues();
        values.put("googleId", googleId);
        values.put("username", email);
        values.put("avatarUrl", avatarUrl);
        myDB.insert("User", null, values);
    }


    private void loginUser() {
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        // Validate fields
        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check user credentials
        Cursor cursor = myDB.rawQuery("SELECT * FROM User WHERE username = ? AND password = ?", new String[]{user, pass});

        if (cursor.moveToFirst()) {
            // Login successful
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            // Navigate to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close LoginActivity
        } else {
            // Login failed
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
