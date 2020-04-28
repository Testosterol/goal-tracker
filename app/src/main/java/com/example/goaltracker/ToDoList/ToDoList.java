package com.example.goaltracker.ToDoList;

import android.widget.CheckBox;

import java.util.Date;

public class ToDoList {

    private String title;
    private String notes;
    private Date date;
    private boolean finished;
    private boolean closed;

    //state of the item
    private boolean expanded;


    public ToDoList(String title, String notes, Date date){
        this.title = title;
        this.notes = notes;
        this.date = date;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
