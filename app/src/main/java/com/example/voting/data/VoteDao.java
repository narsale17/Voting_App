package com.example.voting.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VoteDao {
    @Query("SELECT * FROM votes WHERE pollId = :pollId")
    List<Vote> getVotesForPoll(int pollId);

    @Query("SELECT * FROM votes WHERE pollId = :pollId AND username = :username")
    Vote getUserVoteForPoll(int pollId, String username);

    @Query("SELECT COUNT(*) FROM votes WHERE pollId = :pollId AND selectedOption = :option")
    int getVoteCountForOption(int pollId, int option);

    @Insert
    long insertVote(Vote vote);

    @Update
    void updateVote(Vote vote);

    @Delete
    void deleteVote(Vote vote);

    @Query("DELETE FROM votes WHERE pollId = :pollId")
    void deleteVotesForPoll(int pollId);

    @Query("DELETE FROM votes")
    void deleteAllVotes();
} 