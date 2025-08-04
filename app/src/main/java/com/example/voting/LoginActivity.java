package com.example.voting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.voting.utils.SharedPrefsManager;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private Button btnLogin;
    private SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefsManager = new SharedPrefsManager(this);

        // Check if user is already logged in
        if (sharedPrefsManager.isLoggedIn()) {
            startActivity(new Intent(this, PollsActivity.class));
            finish();
            return;
        }

        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                
                if (username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save username and login
                sharedPrefsManager.saveUsername(username);
                
                // Navigate to PollsActivity
                Intent intent = new Intent(LoginActivity.this, PollsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
} 