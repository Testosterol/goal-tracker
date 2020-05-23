package com.example.goaltracker.Database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.goaltracker.Goals.Goals;

@Dao
public interface GoalsDao {

    @Insert
    void insert(Goals... goals);

}
