package com.example.goaltracker.Goals;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;
import com.example.goaltracker.ToDoList.ToDoList;

import java.util.ArrayList;
import java.util.List;

public class DailyGoalsAdapter  extends ListAdapter<Goals, DailyGoalsAdapter.RecyclerItemViewHolder> implements Filterable {

    private List<Goals> filterGoalsList = new ArrayList<>();
    private List<Goals> goalList = new ArrayList<>();
    private Goals goals;

    protected DailyGoalsAdapter() {
        super(Goals.DIFF_CALLBACK);
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
        holder.goalNotes.setText(goals.getGoalNotes());
        holder.goalType.setText("/" + goals.getGoalAmount() + " " + goals.getGoalType2());
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
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterGoalsList = goalList;
                } else {
                    List<Goals> filteredList = new ArrayList<>();
                    for (Goals row : goalList) {
                        if (row.getGoalName() != null) {
                            if (row.getGoalName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                        if (row.getGoalName() != null) {
                            if (row.getGoalName().toLowerCase().contains(charString.toLowerCase())) {
                                if (!filteredList.contains(row)) filteredList.add(row);
                            }
                        }
                    }
                    filterGoalsList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterGoalsList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterGoalsList = (List<Goals>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void submitList(@Nullable List<Goals> list) {
        super.submitList(list);
        this.goalList = list;
        this.filterGoalsList = goalList;
    }

    @Override
    public int getItemCount() {
        if (filterGoalsList != null) {
            return filterGoalsList.size();
        } else return 0;
    }

    public void removeAt(int position){
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
        TextView goalType;
        TextView goalAmount;

        RecyclerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            goalName = itemView.findViewById(R.id.goal_name_recycler_view);
            goalNotes = itemView.findViewById(R.id.goal_notes_recycler_view);
            goalDone = itemView.findViewById(R.id.goal_done_recycler_view);
            goalFailed = itemView.findViewById(R.id.goal_not_done_recycler_view);
            goalType = itemView.findViewById(R.id.goal_amount_type_recycler_view);
            goalAmount = itemView.findViewById(R.id.goaL_amount_input_recycler_view);
        }

        public void bind(Goals goals) {
            // Get the state
            boolean expanded = goals.isExpanded();
            // Set the visibility based on state
            goalNotes.setVisibility(expanded ? View.VISIBLE : View.GONE);
            if(goals.getGoalType1().equals("Checkbox")) {
                goalType.setVisibility(View.GONE);
                goalAmount.setVisibility(View.GONE);
                goalFailed.setVisibility(View.VISIBLE);
                goalDone.setVisibility(View.VISIBLE);
            }else{
                goalType.setVisibility(View.VISIBLE);
                goalAmount.setVisibility(View.VISIBLE);

                goalFailed.setVisibility(View.GONE);
                goalDone.setVisibility(View.GONE);


              /*  goalAmount.setText(goals.getGoalAmount());*/
            }
        }
    }
}
