package com.example.goaltracker.ToDoList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends ListAdapter<ToDoList, ToDoListAdapter.RecyclerItemViewHolder> implements Filterable {

    private List<ToDoList> filterToDoListList = new ArrayList<>();
    private List<ToDoList> toDoLists = new ArrayList<>();
    private ToDoList toDoList;

    ToDoListAdapter() {
        super(ToDoList.DIFF_CALLBACK);
    }


    @NonNull
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.to_do_list_recycler_view, parent, false);
        return new RecyclerItemViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemViewHolder holder, int position) {

        if (position <= -1) {
            return;
        }

        try {
            toDoList = getItem(position);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        // Set movie data
        holder.bind(toDoList);

        holder.title.setText(toDoList.getTitle());
        holder.notes.setText(toDoList.getNotes());
        holder.date.setText(toDoList.getDate());
        holder.time.setText(toDoList.getTime());
        holder.itemView.setOnClickListener(v -> {
            // Get the current state of the item
            boolean expanded = toDoList.isExpanded();
            // Change the state
            toDoList.setExpanded(!expanded);
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
                    filterToDoListList = toDoLists;
                } else {
                    List<ToDoList> filteredList = new ArrayList<>();
                    for (ToDoList row : toDoLists) {
                        if (row.getTitle() != null) {
                            if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                        if (row.getTitle() != null) {
                            if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                                if (!filteredList.contains(row)) filteredList.add(row);
                            }
                        }
                    }
                    filterToDoListList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterToDoListList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterToDoListList = (List<ToDoList>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void submitList(@Nullable List<ToDoList> list) {
        super.submitList(list);
        this.toDoLists = list;
        this.filterToDoListList = toDoLists;
    }

    @Override
    public int getItemCount() {
        if (filterToDoListList != null) {
            return filterToDoListList.size();
        } else return 0;
    }

    public void removeAt(int position){
        filterToDoListList.remove(position);
        notifyItemRemoved(position);
    }

    public List<ToDoList> getData() {
        return filterToDoListList;
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView notes;
        TextView date;
        TextView time;

        RecyclerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.toDoListRecyclerViewTitle);
            notes = itemView.findViewById(R.id.toDoListRecyclerViewNotes);
            date = itemView.findViewById(R.id.toDoListRecyclerViewDate);
            time = itemView.findViewById(R.id.toDoListRecyclerViewTime);
        }

        public void bind(ToDoList toDoList) {
            // Get the state
            boolean expanded = toDoList.isExpanded();
            // Set the visibility based on state
            notes.setVisibility(expanded ? View.VISIBLE : View.GONE);

            title.setText(toDoList.getTitle());
            date.setText(toDoList.getDate());
            notes.setText(toDoList.getNotes());
            time.setText(toDoList.getTime());
        }
    }

}
