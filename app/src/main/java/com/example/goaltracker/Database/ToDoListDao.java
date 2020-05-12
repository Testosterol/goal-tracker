package com.example.goaltracker.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.goaltracker.ToDoList.ToDoList;

import java.util.List;


@Dao
public interface ToDoListDao {


    @Insert
    void insert(ToDoList... ToDoList);

    @Delete
    void delete(ToDoList... ToDoList);

    @Query("SELECT * FROM to_do_list where LOWER(title) like LOWER(:input) or LOWER(title) like LOWER(:input) ORDER BY date ASC,time ASC" )
    LiveData<List<ToDoList>> getInitialToDoListItemsInSortedOrderFiltered(String input);

    @Query("SELECT * FROM to_do_list ORDER BY date ASC, time ASC")
    LiveData<List<ToDoList>> getInitialToDoListItems();

    @Query("SELECT * FROM to_do_list ORDER BY date ASC, time ASC")
    List<ToDoList> getrAllTimes();
}
