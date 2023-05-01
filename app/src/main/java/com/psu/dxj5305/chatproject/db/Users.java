package com.psu.dxj5305.chatproject.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.concurrent.TimeUnit;

@Entity(tableName="users")
public class Users {

    public Users()
    {

    }
    public Users(int id, String username, String date_modified) {
        this.id = id;
        this.username = username;
        this.date_modified = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDate_modified() {
        return date_modified;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "date_modified")
    public String date_modified;
}