package com.psu.dxj5305.chatproject.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "thoughts")
public class Thought {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "text")
    public String text;

    public Thought(String text) {
        this.text = text;
    }
}




