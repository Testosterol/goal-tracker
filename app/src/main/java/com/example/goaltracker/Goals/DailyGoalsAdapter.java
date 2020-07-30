package com.example.goaltracker.Goals;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.Database.AppDatabase;
import com.example.goaltracker.R;
import com.example.goaltracker.ToDoList.ToDoList;

import java.util.ArrayList;
import java.util.List;

public class DailyGoalsAdapter extends ListAdapter<Goals, DailyGoalsAdapter.RecyclerItemViewHolder> {

    private List<Goals> filterGoalsList = new ArrayList<>();
    private List<Goals> goalList = new ArrayList<>();
    private Goals goals;
    private Context mContext;

    protected DailyGoalsAdapter(Context context) {
        super(Goals.DIFF_CALLBACK);
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View contactView = inflater.inflate(R.layout.daily_goal_recycler_view, parent, false);
        return new DailyGoalsAdapter.RecyclerItemViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemViewHolder holder, int position) {
        if (position <= -1) {
            return;
        }

        try {
            goals = getItem(position);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        // Set movie data
        holder.bind(goals);


        holder.goalName.setText(goals.getGoalName());
        holder.goalDone.setOnClickListener(v -> {
            holder.goalFailed.setChecked(false);
            // save goal
            getItem(position).setGoalValueFinished("true");
            AppDatabase.getInstance(mContext).getGoalsDao().update(getItem(position));

            SharedPreferences preferences = mContext.getSharedPreferences("goal_tracker_identifier", Context.MODE_PRIVATE);
            if(preferences.getBoolean("vibration_goal_tracker", false)){
                Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (vibrator != null) {
                        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                }else{
                    if (vibrator != null) {
                        vibrator.vibrate(200);
                    }
                }
            }

        });
        holder.goalFailed.setOnClickListener(v -> {
            holder.goalDone.setChecked(false);
            // save goal
            getItem(position).setGoalValueFinished("false");
            AppDatabase.getInstance(mContext).getGoalsDao().update(getItem(position));

            SharedPreferences preferences = mContext.getSharedPreferences("goal_tracker_identifier", Context.MODE_PRIVATE);
            if(preferences.getBoolean("vibration_goal_tracker", false)){
                Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (vibrator != null) {
                        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                }else{
                    if (vibrator != null) {
                        vibrator.vibrate(200);
                    }
                }
            }
        });

        holder.goalNotes.setText(goals.getGoalNotes());
        holder.itemView.setOnClickListener(v -> {
            // Get the current state of the item
            boolean expanded = goals.isExpanded();
            // Change the state
            goals.setExpanded(!expanded);
            // Notify the adapter that item has changed
            notifyItemChanged(position);
        });
    }

    @Override
    public void submitList(@Nullable List<Goals> list) {
        super.submitList(list);
        this.goalList = list;
        this.filterGoalsList = goalList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (filterGoalsList != null) {
            return filterGoalsList.size();
        } else return 0;
    }

    public void removeAt(int position) {
        filterGoalsList.remove(position);
        notifyItemRemoved(position);
    }

    public List<Goals> getData() {
        return filterGoalsList;
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        TextView goalName;
        TextView goalNotes;
        CheckBox goalDone;
        CheckBox goalFailed;

        RecyclerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            goalName = itemView.findViewById(R.id.goal_name_recycler_view);
            goalDone = itemView.findViewById(R.id.goal_done_recycler_view);
            goalFailed = itemView.findViewById(R.id.goal_not_done_recycler_view);
            goalNotes = itemView.findViewById(R.id.goal_notes_recycler_view);
        }

        public void bind(Goals goals) {
            // Get the state
            boolean expanded = goals.isExpanded();
            // Set the visibility based on state
            goalNotes.setVisibility(expanded ? View.VISIBLE : View.GONE);
        }
    }
}
