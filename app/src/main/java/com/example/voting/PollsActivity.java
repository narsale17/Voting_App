package com.example.voting;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voting.data.AppDatabase;
import com.example.voting.data.Poll;
import com.example.voting.utils.SharedPrefsManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PollsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PollAdapter pollAdapter;
    private List<Poll> pollList;
    private AppDatabase database;
    private ExecutorService executorService;
    private SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls);

        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();
        sharedPrefsManager = new SharedPrefsManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fabAddPoll = findViewById(R.id.fabAddPoll);

        pollList = new ArrayList<>();
        pollAdapter = new PollAdapter(pollList, new PollAdapter.OnPollClickListener() {
            @Override
            public void onPollClick(Poll poll) {
                Intent intent = new Intent(PollsActivity.this, VotingActivity.class);
                intent.putExtra("poll_id", poll.getId());
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pollAdapter);

        fabAddPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PollsActivity.this, CreatePollActivity.class);
                startActivity(intent);
            }
        });

        loadPolls();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPolls();
    }

    private void loadPolls() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final List<Poll> polls = database.pollDao().getAllPolls();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pollList.clear();
                        pollList.addAll(polls);
                        pollAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_polls, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            showLogoutConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogoutConfirmationDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    sharedPrefsManager.logout();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
} 