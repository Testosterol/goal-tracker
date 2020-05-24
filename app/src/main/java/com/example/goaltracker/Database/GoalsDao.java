package com.example.goaltracker.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.goaltracker.Goals.Goals;

import java.util.List;

@Dao
public interface GoalsDao {

    @Insert
    void insert(Goals... goals);


    // TODO: FIX DATE AND SHIT
    @Query("SELECT * FROM goals where goal_date_start like :input or goal_date_end like :input ORDER BY goal_name ASC" )
    LiveData<List<Goals>> getInitialGoalsItemsByDate(String input);
}
