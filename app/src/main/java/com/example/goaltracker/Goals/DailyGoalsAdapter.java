package com.example.goaltracker.Goals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;

public class DailyGoalsAdapter  extends ListAdapter<Goals, DailyGoalsAdapter.RecyclerItemViewHolder> implements Filterable {


    protected DailyGoalsAdapter() {
        super(Goals.DIFF_CALLBACK);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //View contactView = inflater.inflate(R.layout., parent, false);
        //return new DailyGoalsAdapter.RecyclerItemViewHolder(contactView);
     return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemViewHolder holder, int position) {

    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

      /*  TextView title;
        TextView notes;
        TextView date;
        TextView time;*/

        RecyclerItemViewHolder(@NonNull View itemView) {
            super(itemView);
        /*    title = itemView.findViewById(R.id.toDoListRecyclerViewTitle);
            notes = itemView.findViewById(R.id.toDoListRecyclerViewNotes);
            date = itemView.findViewById(R.id.toDoListRecyclerViewDate);
            time = itemView.findViewById(R.id.toDoListRecyclerViewTime);*/
        }

        public void bind(Goals goals) {
            // Get the state
            boolean expanded = goals.isExpanded();
            // Set the visibility based on state
          /*  notes.setVisibility(expanded ? View.VISIBLE : View.GONE);

            title.setText(toDoList.getTitle());
            date.setText(toDoList.getDate());
            notes.setText(toDoList.getNotes());
            time.setText(toDoList.getTime());*/
        }
    }
}
