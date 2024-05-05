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

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USER = "USER";

    private ImageView profilePicture;
    private TextView usernameTextView;
    private TextView userBioTextView;
    private Button editProfileButton;
    private ImageView homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePicture = findViewById(R.id.profilePicture);
        usernameTextView = findViewById(R.id.usernameTextView);
        userBioTextView = findViewById(R.id.userBioTextView);
        editProfileButton = findViewById(R.id.editProfileButton);
        homeButton = findViewById(R.id.homeButton);

        String loggedInEmail = getLoggedInUserEmail();
        usernameTextView.setText(loggedInEmail);
        Log.d("ProfileActivity", "Retrieved email from SharedPreferences: " + loggedInEmail);

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearUserSession();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (loggedInEmail != null) {
            usernameTextView.setText(loggedInEmail);
        } else {
            usernameTextView.setText("Unknown");
        }

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Set onClickListener for the profile picture
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to allow users to change their profile picture
                // This could involve opening a dialog or another activity for selecting/uploading an image
                // Example:
                // Intent intent = new Intent(ProfileActivity.this, ChangeProfilePictureActivity.class);
                // startActivity(intent);
            }
        });

        // Set onClickListener for the edit profile button
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to allow users to edit their profile information
                // This could involve opening an activity or fragment for editing profile details
                // Example:
                // Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                // startActivity(intent);
            }
        });

        // Retrieve and display user bio
        String userBio = getUserBio();
        userBioTextView.setText(userBio);
    }

    public String getLoggedInUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER, "");
    }

    private void clearUserSession() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private String getUserBio() {
        // Implement logic to retrieve user bio from the database or SharedPreferences
        // Example:
        // SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // return sharedPreferences.getString(KEY_BIO, "");
        return "User Bio Placeholder"; // Placeholder text
    }
}
