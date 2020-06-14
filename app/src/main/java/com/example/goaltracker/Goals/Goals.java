package com.example.goaltracker.Goals;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goals")
public class Goals {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long _rowId;

    @ColumnInfo(name = "goal_name")
    private String goalName;

    @ColumnInfo(name = "goal_date_start")
    private long goalDateStart;

    @ColumnInfo(name = "goal_date_end")
    private long goalDateEnd;

    @ColumnInfo(name = "goal_value_finished")
    private String goalValueFinished;

    @ColumnInfo(name = "goal_category")
    private String goalCategory;

    @ColumnInfo(name = "goal_notes")
    private String goalNotes;

    //state of the item
    private boolean expanded;

    public static DiffUtil.ItemCallback<Goals> DIFF_CALLBACK = new DiffUtil.ItemCallback<Goals>() {
        @Override
        public boolean areItemsTheSame(@NonNull Goals oldItem, @NonNull Goals newItem) {
            return oldItem.goalName.equals(newItem.goalName);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Goals oldItem, @NonNull Goals newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Goals goals = (Goals) obj;

        return goals.goalName.equals(this.goalName);
    }


    @NonNull
    public Long get_rowId() {
        return _rowId;
    }

    public void set_rowId(@NonNull Long _rowId) {
        this._rowId = _rowId;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public long getGoalDateStart() {
        return goalDateStart;
    }

    public void setGoalDateStart(long goalDateStart) {
        this.goalDateStart = goalDateStart;
    }

    public long getGoalDateEnd() {
        return goalDateEnd;
    }

    public void setGoalDateEnd(long goalDateEnd) {
        this.goalDateEnd = goalDateEnd;
    }

    public String getGoalValueFinished() {
        return goalValueFinished;
    }

    public void setGoalValueFinished(String goalValueFinished) {
        this.goalValueFinished = goalValueFinished;
    }

    public String getGoalCategory() {
        return goalCategory;
    }

    public void setGoalCategory(String goalCategory) {
        this.goalCategory = goalCategory;
    }

    public String getGoalNotes() {
        return goalNotes;
    }

    public void setGoalNotes(String goalNotes) {
        this.goalNotes = goalNotes;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
