package com.example.goaltracker.Goals;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goals")
public class Goals {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long _rowId;

    @NonNull
    public Long get_rowId() {
        return _rowId;
    }

    public void set_rowId(@NonNull Long _rowId) {
        this._rowId = _rowId;
    }
}
