package com.example.goaltracker.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.goaltracker.Goals.Goals;

import java.util.List;

@Dao
public interface GoalsDao {

    @Insert
    void insert(Goals... goals);

    @Update
    void update(Goals... goals);


    @Query("SELECT * FROM goals where goal_date_start == :input AND goal_value_finished == '' AND goal_category LIKE 'daily' ORDER BY goal_name ASC" )
    LiveData<List<Goals>> getInitialGoalsItemsByDate(Long input);


    @Query("SELECT * FROM goals where goal_date_start == :input AND goal_value_finished == '' AND goal_category LIKE 'weekly' ORDER BY goal_name ASC" )
    LiveData<List<Goals>> getInitialWeeklyGoalsItemsByDate(Long input);

    @Query("SELECT * FROM goals where goal_date_start == :input AND goal_value_finished == '' AND goal_category LIKE 'monthly' ORDER BY goal_name ASC" )
    LiveData<List<Goals>> getInitialMonthlyGoalsItemsByDate(Long input);
}
