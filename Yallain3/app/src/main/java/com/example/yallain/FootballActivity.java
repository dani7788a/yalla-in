package com.example.yallain;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FootballActivity extends AppCompatActivity {
    private ImageButton profileButton;
    private String rentTimeStart = "";
    private String rentTimeEnd = "";
    DatabaseHelper databaseHelper;
    private ImageView homeButton;
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football);
        databaseHelper = new DatabaseHelper(this);
        TextView timeTextView = findViewById(R.id.timeTextView);
        timeTextView.setText("Select Time");


        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        TextView timeTextView2 = findViewById(R.id.timeTextView2);
        timeTextView2.setText("Select Time");


        timeTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog2(v);
            }
        });
        homeButton = findViewById(R.id.homeButton);
        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedInEmail = getLoggedInUser();
                Log.d("MainActivity", "Logged-in email: " + loggedInEmail);
                Intent intent = new Intent(FootballActivity.this, ProfileActivity.class);
                intent.putExtra("USER", loggedInEmail);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(v -> {

            Intent intent = new Intent(FootballActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.rent1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedInEmail = getLoggedInUser();
                rentStadium("Football", "Saida Stadium", loggedInEmail);
            }
        });

        findViewById(R.id.rent2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedInEmail = getLoggedInUser();
                rentStadium("Football", "Tripoli Stadium", loggedInEmail);
            }
        });

    }

    private void rentStadium(String sport, String stadiumName, String userEmail) {
        String rentDateTime = getRentDateTime();

        if (rentDateTime.isEmpty()) {
            Toast.makeText(this, "Please select a valid time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.hasUserAlreadyRentedStadiumAtSameTime(userEmail, stadiumName, rentDateTime)) {
            Toast.makeText(this, "You have already rented this stadium at the same time", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = databaseHelper.insertRent(sport, stadiumName, rentDateTime, userEmail);

        if (success) {
            Toast.makeText(this, "Stadium rented successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to rent stadium", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRentDateTime() {
        if (rentTimeStart.isEmpty() || rentTimeEnd.isEmpty()) {
            return "";
        }

        long rentDateMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String rentDate = dateFormat.format(new Date(rentDateMillis));

        return rentDate + " " + rentTimeStart + " - " + rentTimeEnd;
    }






    public void showTimePickerDialog(View v) {
        TimePickerDialog.OnTimeSetListener startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                rentTimeStart = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                TimePickerDialog.OnTimeSetListener endTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        rentTimeEnd = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        TextView timeTextView = findViewById(R.id.timeTextView);
                        timeTextView.setText(rentTimeStart + " - " + rentTimeEnd);
                    }
                };


                TimePickerDialog endTimePickerDialog = new TimePickerDialog(FootballActivity.this, endTimeSetListener, 0, 0, true);
                endTimePickerDialog.show();
            }
        };


        TimePickerDialog startTimePickerDialog = new TimePickerDialog(this, startTimeSetListener, 0, 0, true);
        startTimePickerDialog.show();
    }

    public void showTimePickerDialog2(View v) {
        TimePickerDialog.OnTimeSetListener startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                rentTimeStart = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                TimePickerDialog.OnTimeSetListener endTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        rentTimeEnd = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        TextView timeTextView2 = findViewById(R.id.timeTextView2);
                        timeTextView2.setText(rentTimeStart + " - " + rentTimeEnd);
                    }
                };


                TimePickerDialog endTimePickerDialog = new TimePickerDialog(FootballActivity.this, endTimeSetListener, 0, 0, true);
                endTimePickerDialog.show();
            }
        };


        TimePickerDialog startTimePickerDialog = new TimePickerDialog(this, startTimeSetListener, 0, 0, true);
        startTimePickerDialog.show();
    }


    private String getLoggedInUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER, "");
    }



}

