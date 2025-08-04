package com.example.voting;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.voting.data.AppDatabase;
import com.example.voting.data.Poll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreatePollActivity extends AppCompatActivity {
    private EditText etQuestion, etOption1, etOption2, etOption3, etOption4;
    private Button btnCreatePoll;
    private AppDatabase database;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Create New Poll");
        }

        etQuestion = findViewById(R.id.etQuestion);
        etOption1 = findViewById(R.id.etOption1);
        etOption2 = findViewById(R.id.etOption2);
        etOption3 = findViewById(R.id.etOption3);
        etOption4 = findViewById(R.id.etOption4);
        btnCreatePoll = findViewById(R.id.btnCreatePoll);

        btnCreatePoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPoll();
            }
        });
    }

    private void createPoll() {
        String question = etQuestion.getText().toString().trim();
        String option1 = etOption1.getText().toString().trim();
        String option2 = etOption2.getText().toString().trim();
        String option3 = etOption3.getText().toString().trim();
        String option4 = etOption4.getText().toString().trim();

        if (question.isEmpty()) {
            Toast.makeText(this, "Please enter a question", Toast.LENGTH_SHORT).show();
            return;
        }

        if (option1.isEmpty() || option2.isEmpty()) {
            Toast.makeText(this, "Please enter at least 2 options", Toast.LENGTH_SHORT).show();
            return;
        }

        Poll poll = new Poll(question, option1, option2, option3, option4);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                database.pollDao().insertPoll(poll);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CreatePollActivity.this, "Poll created successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
} 