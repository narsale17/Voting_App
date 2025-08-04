package com.example.voting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.voting.data.AppDatabase;
import com.example.voting.data.Poll;
import com.example.voting.data.Vote;
import com.example.voting.utils.SharedPrefsManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VotingActivity extends AppCompatActivity {
    private TextView tvQuestion;
    private LinearLayout llVotingOptions;
    private LinearLayout llResults;
    private BarChart barChart;
    private AppDatabase database;
    private ExecutorService executorService;
    private SharedPrefsManager sharedPrefsManager;
    private Poll currentPoll;
    private String username;
    private boolean hasVoted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();
        sharedPrefsManager = new SharedPrefsManager(this);
        username = sharedPrefsManager.getUsername();

        int pollId = getIntent().getIntExtra("poll_id", -1);
        if (pollId == -1) {
            Toast.makeText(this, "Error loading poll", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Vote");
        }

        tvQuestion = findViewById(R.id.tvQuestion);
        llVotingOptions = findViewById(R.id.llVotingOptions);
        llResults = findViewById(R.id.llResults);
        barChart = findViewById(R.id.barChart);

        loadPoll(pollId);
        setupBarChart();
    }

    private void loadPoll(int pollId) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                currentPoll = database.pollDao().getPollById(pollId);
                Vote userVote = database.voteDao().getUserVoteForPoll(pollId, username);
                
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPoll != null) {
                            displayPoll();
                            if (userVote != null) {
                                hasVoted = true;
                                showResults();
                            } else {
                                showVotingOptions();
                            }
                        }
                    }
                });
            }
        });
    }

    private void displayPoll() {
        tvQuestion.setText(currentPoll.getQuestion());
    }

    private void showVotingOptions() {
        llVotingOptions.setVisibility(View.VISIBLE);
        llResults.setVisibility(View.GONE);

        llVotingOptions.removeAllViews();

        String[] options = {currentPoll.getOption1(), currentPoll.getOption2(), 
                          currentPoll.getOption3(), currentPoll.getOption4()};

        for (int i = 0; i < options.length; i++) {
            if (!options[i].isEmpty()) {
                Button button = new Button(this);
                button.setText(options[i]);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 8, 0, 8);
                button.setLayoutParams(params);
                button.setPadding(32, 16, 32, 16);
                button.setBackgroundColor(Color.parseColor("#2196F3"));
                button.setTextColor(Color.WHITE);
                
                final int optionIndex = i + 1;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vote(optionIndex);
                    }
                });
                
                llVotingOptions.addView(button);
            }
        }
    }

    private void vote(int selectedOption) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Vote vote = new Vote(currentPoll.getId(), username, selectedOption);
                database.voteDao().insertVote(vote);
                
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hasVoted = true;
                        showResults();
                        Toast.makeText(VotingActivity.this, "Vote recorded!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showResults() {
        llVotingOptions.setVisibility(View.GONE);
        llResults.setVisibility(View.VISIBLE);
        
        updateChart();
    }

    private void setupBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);
    }

    private void updateChart() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final List<BarEntry> entries = new ArrayList<>();
                final List<String> labels = new ArrayList<>();
                
                String[] options = {currentPoll.getOption1(), currentPoll.getOption2(), 
                                  currentPoll.getOption3(), currentPoll.getOption4()};
                
                for (int i = 0; i < options.length; i++) {
                    if (!options[i].isEmpty()) {
                        int voteCount = database.voteDao().getVoteCountForOption(currentPoll.getId(), i + 1);
                        entries.add(new BarEntry(i, voteCount));
                        labels.add(options[i]);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BarDataSet dataSet = new BarDataSet(entries, "Votes");
                        dataSet.setColor(Color.parseColor("#2196F3"));
                        dataSet.setValueTextColor(Color.BLACK);
                        dataSet.setValueTextSize(12f);

                        BarData barData = new BarData(dataSet);
                        barChart.setData(barData);

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                        xAxis.setLabelCount(labels.size(), true);

                        barChart.invalidate();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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