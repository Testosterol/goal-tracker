package com.example.goaltracker.Goals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.Database.AppDatabase;
import com.example.goaltracker.Database.GoalsDao;
import com.example.goaltracker.Database.ToDoListDao;

import java.util.List;

public class DailyGoalsViewModel extends AndroidViewModel {

    public GoalsDao goalsDao;
    public LiveData<List<Goals>> allGoalsItems;
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();

    public DailyGoalsViewModel(@NonNull Application application) {
        super(application);
        goalsDao = AppDatabase.getInstance(this.getApplication()).getGoalsDao();
    }

    public void init(GoalsDao goalsDao) {
        allGoalsItems = Transformations.switchMap(filterTextAll, input -> goalsDao.getInitialGoalsItemsByDate(input));
    }
}
