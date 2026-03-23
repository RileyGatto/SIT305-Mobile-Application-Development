package com.example.passtask41p.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Entity to represent database table
@Entity(tableName = "events")
public class Event {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String category;
    public String location;
    public String date;
    public String time;
}