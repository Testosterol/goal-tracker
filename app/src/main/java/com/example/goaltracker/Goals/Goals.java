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
    private String goalDateStart;

    @ColumnInfo(name = "goal_date_end")
    private String goalDateEnd;

    @ColumnInfo(name = "goal_type_1")
    private String goalType1;

    @ColumnInfo(name = "goal_type_2")
    private String goalType2;

    @ColumnInfo(name = "goal_notes")
    private String goalNotes;

    @ColumnInfo(name = "goal_value_finished")
    private String goalValueFinished;

    @ColumnInfo(name = "goal_amount")
    private String goalAmount;

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

    public String getGoalDateStart() {
        return goalDateStart;
    }

    public void setGoalDateStart(String goalDateStart) {
        this.goalDateStart = goalDateStart;
    }

    public String getGoalDateEnd() {
        return goalDateEnd;
    }

    public void setGoalDateEnd(String goalDateEnd) {
        this.goalDateEnd = goalDateEnd;
    }

    public String getGoalType1() {
        return goalType1;
    }

    public void setGoalType1(String goalType1) {
        this.goalType1 = goalType1;
    }

    public String getGoalType2() {
        return goalType2;
    }

    public void setGoalType2(String goalType2) {
        this.goalType2 = goalType2;
    }

    public String getGoalNotes() {
        return goalNotes;
    }

    public void setGoalNotes(String goalNotes) {
        this.goalNotes = goalNotes;
    }

    public String getGoalValueFinished() {
        return goalValueFinished;
    }

    public void setGoalValueFinished(String goalValueFinished) {
        this.goalValueFinished = goalValueFinished;
    }

    public String getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(String goalAmount) {
        this.goalAmount = goalAmount;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
