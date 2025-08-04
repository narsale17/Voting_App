package com.example.voting;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.voting.utils.SharedPrefsManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(this);
        
        // Check if user is already logged in
        if (sharedPrefsManager.isLoggedIn()) {
            // User is logged in, go to PollsActivity
            Intent intent = new Intent(this, PollsActivity.class);
            startActivity(intent);
        } else {
            // User is not logged in, go to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        
        finish(); // Close MainActivity
    }
}