package com.example.goaltracker.ToDoList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goaltracker.R;

import java.util.ArrayList;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.RecyclerItemViewHolder> {

    private Context mContext;
    private ArrayList<ToDoList> myList;

    ToDoListAdapter(Context context){
        this.myList = myList;
        this.mContext = context;
    }

    @NonNull
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.to_do_list_items, parent, false);
        return new RecyclerItemViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ToDoList toDoList = myList.get(position);
        holder.bind(toDoList);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        public RecyclerItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(ToDoList toDoList) {
            // Get the state
            /*boolean expanded = toDoList.isExpanded();*/
            // Set the visibility based on state
           /* subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            title.setText(toDoList.getTitle());
            genre.setText("Genre: " + toDoList.getNotes());
            year.setText("Year: " + toDoList.getDate());*/
        }
    }
}
