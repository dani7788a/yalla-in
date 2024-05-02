package com.example.yallain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView listViewHistory;
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USER = "USER";
    private DatabaseHelper databaseHelper;
    private ArrayAdapter<String> historyAdapter;
    private ImageView homeButton;
    private ImageView profileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            // Navigate back to MainActivity
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish the current activity
        });
        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedInEmail = getLoggedInUser(); // Retrieve the logged-in email
                Log.d("MainActivity", "Logged-in email: " + loggedInEmail);
                Intent intent = new Intent(HistoryActivity.this, ProfileActivity.class);
                intent.putExtra("USER", loggedInEmail); // Pass the logged-in email to ProfileActivity
                startActivity(intent);
            }
        });
        databaseHelper = new DatabaseHelper(this);

        listViewHistory = findViewById(R.id.listViewHistory);

        try {

            ArrayList<String> rentalHistory = databaseHelper.getRentalHistory();


            historyAdapter = new ArrayAdapter<>(this,R.layout.custom_item, rentalHistory);

            listViewHistory.setAdapter(historyAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getLoggedInUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER, "");
    }

}
