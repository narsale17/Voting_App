package com.example.voting.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "votes")
public class Vote {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int pollId;
    private String username;
    private int selectedOption;
    private long votedAt;

    public Vote(int pollId, String username, int selectedOption) {
        this.pollId = pollId;
        this.username = username;
        this.selectedOption = selectedOption;
        this.votedAt = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public long getVotedAt() {
        return votedAt;
    }

    public void setVotedAt(long votedAt) {
        this.votedAt = votedAt;
    }
} 