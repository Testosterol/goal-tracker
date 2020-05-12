package com.example.goaltracker.ToDoList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.goaltracker.Database.AppDatabase;
import com.example.goaltracker.Database.ToDoListDao;

import java.util.List;

public class ToDoListViewModel extends AndroidViewModel {

    public ToDoListDao toDoListDao;
    public LiveData<List<ToDoList>> allToDoListItems;
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();

    public ToDoListViewModel(@NonNull Application application) {
        super(application);
        toDoListDao = AppDatabase.getInstance(this.getApplication()).getToDoListDao();
    }

    public void init(ToDoListDao toDoListDao) {
        allToDoListItems = Transformations.switchMap(filterTextAll, input -> {
                if (input == null || input.equals("") || input.equals("%%")) {
                    return toDoListDao.getInitialToDoListItems();
                }else{
                    return toDoListDao.getInitialToDoListItemsInSortedOrderFiltered(input);
                }
            });

    }

}
