package com.example.yallain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yallain.databinding.ActivitySignupBinding;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    private EditText dobInput;
    private ProgressBar passwordStrengthProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize views
        dobInput = findViewById(R.id.signup_dob);
        passwordStrengthProgressBar = findViewById(R.id.password_strength_progress_bar);

        // Date of birth input
        dobInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Password strength meter
        binding.signupPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Signup button click listener
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Signup logic here...
            }
        });

        // Login redirect click listener
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int selectedYear = calendar.get(Calendar.YEAR);
        int selectedMonth = calendar.get(Calendar.MONTH);
        int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String date = String.format("%02d-%02d-%04d", dayOfMonth, monthOfYear + 1, year);
                    EditText dobEditText = findViewById(R.id.signup_dob);
                    dobEditText.setText(date);
                }, selectedYear, selectedMonth, selectedDayOfMonth);

        datePickerDialog.show();
    }

    private void updatePasswordStrength(String password) {
        int strength = calculatePasswordStrength(password);
        passwordStrengthProgressBar.setProgress(strength);
    }

    private int calculatePasswordStrength(String password) {
        // Define your criteria for password strength calculation
        // For simplicity, let's assume:
        // 0-4 characters: Weak
        // 5-8 characters: Medium
        // 9+ characters: Strong
        if (password.length() <= 4) {
            return 25; // Weak
        } else if (password.length() <= 8) {
            return 50; // Medium
        } else {
            return 100; // Strong
        }
    }
}