package com.example.goaltracker.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.goaltracker.Goals.Goals;
import com.example.goaltracker.ToDoList.ToDoList;

@Database(entities = {ToDoList.class, Goals.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "to_do_list_db";

    private static AppDatabase instance;

     public abstract ToDoListDao getToDoListDao();
        public abstract GoalsDao getGoalsDao();

    public synchronized static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = createToDoListDatabaseInstance(context);

        }
        return instance;
    }


    private static AppDatabase createToDoListDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

}
