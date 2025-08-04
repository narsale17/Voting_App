package com.example.voting.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PollDao {
    @Query("SELECT * FROM polls ORDER BY createdAt DESC")
    List<Poll> getAllPolls();

    @Query("SELECT * FROM polls WHERE id = :pollId")
    Poll getPollById(int pollId);

    @Insert
    long insertPoll(Poll poll);

    @Update
    void updatePoll(Poll poll);

    @Delete
    void deletePoll(Poll poll);

    @Query("DELETE FROM polls")
    void deleteAllPolls();
} 