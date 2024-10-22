package com.group4.gamecontrollershop;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

    public class ProfileActivity extends AppCompatActivity {


        // Add more TextViews as needed for other user data

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            // Initialize more TextViews as needed

            // Get the current user
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user != null) {
                // User is signed in
                String userName = user.getDisplayName(); // Get the user's name
                String userEmail = user.getEmail(); // Get the user's email


        }
    }


}