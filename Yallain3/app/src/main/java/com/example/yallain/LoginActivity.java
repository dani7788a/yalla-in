package com.example.yallain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.yallain.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USER = "USER";
    private static final String KEY_REMEMBER_ME = "REMEMBER_ME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PASSWORD = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        // Check if Remember Me is enabled
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
        if (rememberMe) {
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
            String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");
            binding.loginEmail.setText(savedEmail);
            binding.loginPassword.setText(savedPassword);
            binding.rememberMeCheckbox.setChecked(true);
        }

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

                if(email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                    if(checkCredentials) {
                        loginSuccessful(email);
                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        // Listen for changes in the Remember Me checkbox state
        binding.rememberMeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_REMEMBER_ME, isChecked);
                editor.apply();
            }
        });
    }

    // Assuming this method is called when the user successfully logs in
    private void loginSuccessful(String userEmail) {
        // Save the email and password into SharedPreferences if Remember Me is enabled
        if (binding.rememberMeCheckbox.isChecked()) {
            String email = binding.loginEmail.getText().toString();
            String password = binding.loginPassword.getText().toString();
            SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASSWORD, password);
            editor.apply();
        }

        // Save the email into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER, userEmail);
        editor.apply();
    }
}
