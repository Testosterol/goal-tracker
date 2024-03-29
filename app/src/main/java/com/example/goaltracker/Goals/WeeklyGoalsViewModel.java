package com.example.goaltracker.Goals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.goaltracker.Database.AppDatabase;
import com.example.goaltracker.Database.GoalsDao;

import java.util.List;

public class WeeklyGoalsViewModel extends AndroidViewModel {

    public GoalsDao goalsDao;
    public LiveData<List<Goals>> allGoalsItems;
    public MutableLiveData<Long> filterTextAll = new MutableLiveData<>();

    public WeeklyGoalsViewModel(@NonNull Application application) {
        super(application);
        goalsDao = AppDatabase.getInstance(this.getApplication()).getGoalsDao();
    }

    public void init(GoalsDao goalsDao) {
        allGoalsItems = Transformations.switchMap(filterTextAll, input -> goalsDao.getInitialWeeklyGoalsItemsByDate(input));
    }
}

