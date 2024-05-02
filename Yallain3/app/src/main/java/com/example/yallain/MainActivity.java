package com.example.yallain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private Spinner spinnerSports;
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USER = "USER";
    DatabaseHelper databaseHelper;
    private Spinner spinnerStadiums;
    private Button btnViewHistory;
    private ImageButton profileButton; // Change Button to ImageButton


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

        // Initialize spinners
        spinnerSports = findViewById(R.id.spinnerSports);
        spinnerStadiums = findViewById(R.id.spinnerStadiums);

        btnViewHistory = findViewById(R.id.btnViewHistory);
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });

        Button btnRentStadium = findViewById(R.id.btnRent);

        btnRentStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedSport = spinnerSports.getSelectedItem().toString();
                String selectedStadium = spinnerStadiums.getSelectedItem().toString();

                rentStadium(selectedSport, selectedStadium);
            }
        });


        setupSpinners();


        spinnerSports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                updateStadiums(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerStadiums.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedInEmail = getLoggedInUser(); // Retrieve the logged-in email
                Log.d("MainActivity", "Logged-in email: " + loggedInEmail);
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("USER", loggedInEmail); // Pass the logged-in email to ProfileActivity
                startActivity(intent);
            }
        });
    }



    private void setupSpinners() {

        List<String> sportsList = Arrays.asList(getResources().getStringArray(R.array.sports_array));
        List<Drawable> sportIcons = Arrays.asList(
                getResources().getDrawable(R.drawable.football),
                getResources().getDrawable(R.drawable.basketball
                ), getResources().getDrawable(R.drawable.tennis), getResources().getDrawable(R.drawable.racket));

        CustomSpinnerAdapter sportsAdapter = new CustomSpinnerAdapter(this, sportsList, sportIcons);
        spinnerSports.setAdapter(sportsAdapter);

    }

    private void updateStadiums(int sportPosition) {

        String[] stadiums;

        if (sportPosition == 0) {
            stadiums = getResources().getStringArray(R.array.football_stadiums);
        } else if (sportPosition == 1) {
            stadiums = getResources().getStringArray(R.array.basketball_stadiums);
        } else if (sportPosition == 2) {
            stadiums = getResources().getStringArray(R.array.tennis_stadiums);
        } else {
            stadiums = getResources().getStringArray(R.array.paddle_stadiums);
        }

        CustomAdapter stadiumsAdapter = new CustomAdapter(this, Arrays.asList(stadiums));
        spinnerStadiums.setAdapter(stadiumsAdapter);
    }


    private void rentStadium(String sport, String stadiumName) {

        long rentDateMillis = System.currentTimeMillis();


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String rentDate = dateFormat.format(new Date(rentDateMillis));


        boolean success = databaseHelper.insertRent(sport, stadiumName, rentDate);
        if (success) {
            Toast.makeText(MainActivity.this, "Stadium rented successfully", Toast.LENGTH_SHORT).show();


        } else {

            Toast.makeText(MainActivity.this, "Failed to rent stadium", Toast.LENGTH_SHORT).show();
        }
    }
    private String getLoggedInUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER, "");
    }
}
