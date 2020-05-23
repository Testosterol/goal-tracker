package com.example.goaltracker.ToDoList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "to_do_list")
public class ToDoList {

    private String title;
    private String notes;
    private String date;
    private boolean finished;
    private boolean closed;
    private String time;

    //state of the item
    private boolean expanded;


    public ToDoList(String title, String date, String notes, String time){
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.time = time;
    }

    public static DiffUtil.ItemCallback<ToDoList> DIFF_CALLBACK = new DiffUtil.ItemCallback<ToDoList>() {
        @Override
        public boolean areItemsTheSame(@NonNull ToDoList oldItem, @NonNull ToDoList newItem) {
            return oldItem.title.equals(newItem.title);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ToDoList oldItem, @NonNull ToDoList newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        ToDoList toDoList = (ToDoList) obj;

        return toDoList.title.equals(this.title);
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long _rowId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @NonNull
    public Long get_rowId() {
        return _rowId;
    }

    public void set_rowId(@NonNull Long _rowId) {
        this._rowId = _rowId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
