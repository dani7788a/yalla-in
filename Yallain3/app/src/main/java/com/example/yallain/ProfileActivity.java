package com.example.yallain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private static final String PREF_NAME = "MyPrefs"; // Define PREF_NAME constant
    private static final String KEY_USER = "USER";
    private ImageView profilePicture;
    private TextView usernameTextView;

    private ImageView homeButton; // Add ImageButton for home navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePicture = findViewById(R.id.profilePicture);
        usernameTextView = findViewById(R.id.usernameTextView);

        homeButton = findViewById(R.id.homeButton); // Initialize home button

        // Set the user's profile picture, username, and email address
        String loggedInEmail = getLoggedInUserEmail();

        // Display the logged-in email in a TextView
        usernameTextView.setText(loggedInEmail);
        Log.d("ProfileActivity", "Retrieved email from SharedPreferences: " + loggedInEmail);
        // Display the email in a TextView

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear user session data
                clearUserSession();

                // Navigate back to the login screen or any other appropriate screen
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity
            }
        });

        // Retrieve the username associated with the logged-in user from the database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);


        // Set the username to the TextView
        if (loggedInEmail != null) {
            usernameTextView.setText(loggedInEmail);
        } else {
            // Handle case where username is null (not found in the database)
            usernameTextView.setText("Unknown");
        }

        // Set onClickListener for home button
        homeButton.setOnClickListener(v -> {
            // Navigate back to MainActivity
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish the current activity
        });
    }
    public String getLoggedInUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER, ""); // Assuming you stored the email with the key "user"
    }
    private void clearUserSession() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
