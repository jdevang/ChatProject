package com.psu.dxj5305.chatproject.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ThoughtDao {
    @Insert
    void insert(Thought thought);

    @Query("SELECT * FROM thoughts ORDER BY RANDOM() LIMIT 1")
    Thought getRandomThought();

}

