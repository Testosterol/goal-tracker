package com.example.goaltracker.Goals;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goaltracker.Database.AppDatabase;
import com.example.goaltracker.R;
import com.example.goaltracker.Util.LinedEditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyGoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyGoalsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Dialog dialog;
    private EditText goalName, goalDateFrom, goalDateTo;
    private TextView goalsTitleBar;
    private LinedEditText goalNotes;
    private ImageButton goalAdd, goalNextRight, goalPreviousLeft, goalDelete;
    private WeeklyGoalsViewModel goalsViewModel;
    private RecyclerView mRecyclerView;
    private WeeklyGoalsAdapter mRecyclerViewAdapter;

    private int mYear, mMonth, mDay, mYear2, mMonth2, mDay2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeeklyGoalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeeklyGoalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyGoalsFragment newInstance(String param1, String param2) {
        WeeklyGoalsFragment fragment = new WeeklyGoalsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_goals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goalNextRight = view.findViewById(R.id.image_button_toolbar_weekly_goals_right);
        goalPreviousLeft = view.findViewById(R.id.image_button_toolbar_weekly_goals_left);
        goalsTitleBar = view.findViewById(R.id.toolbar_weekly_goals_title);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("'Week'-ww-YYYY", Locale.ENGLISH);
        String formattedDate = df.format(c.getTime());

        goalsTitleBar.setText(formattedDate);

        // change
        goalNextRight.setOnClickListener(v -> {
            c.add(Calendar.WEEK_OF_YEAR, +1);
            String formattedDattee = df.format(c.getTime());
            goalsTitleBar.setText(formattedDattee);
            if (goalsViewModel != null) {
                try {
                    Date parsedDate = df.parse(goalsTitleBar.getText().toString());
                    goalsViewModel.filterTextAll.setValue(parsedDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        goalPreviousLeft.setOnClickListener(v -> {
            c.add(Calendar.WEEK_OF_YEAR, -1);
            String formattedDattee = df.format(c.getTime());
            goalsTitleBar.setText(formattedDattee);
            c.get(Calendar.DATE);
            if (goalsViewModel != null) {
                try {
                    Date parsedDate = df.parse(goalsTitleBar.getText().toString());
                    goalsViewModel.filterTextAll.setValue(parsedDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        goalsViewModel = new ViewModelProvider(this).get(WeeklyGoalsViewModel.class);
        mRecyclerView = view.findViewById(R.id.recycler_view_weekly_goals_content);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        goalsViewModel.init(goalsViewModel.goalsDao);

        mRecyclerViewAdapter = new WeeklyGoalsAdapter(getContext());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        goalsViewModel.allGoalsItems.observe(getViewLifecycleOwner(), goalsList -> {
            // update UI
            try {
                //to prevent animation recyclerView when change the list
                mRecyclerView.setItemAnimator(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRecyclerViewAdapter.submitList(goalsList);

        });

        if (goalsViewModel != null) {
            try {
                Date dateParsed = df.parse(goalsTitleBar.getText().toString());
                goalsViewModel.filterTextAll.setValue(dateParsed.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        FloatingActionButton addGoalButton = view.findViewById(R.id.button_add_goal_item_weekly);
        addGoalButton.setOnClickListener(v -> inflateToDoListDialog());
    }

    private void inflateToDoListDialog() {
        Context mContext = getContext();
        final View dialogView = View.inflate(mContext, R.layout.weekly_goal_item, null);
        if (mContext != null) {
            dialog = new Dialog(mContext);
            dialog.setContentView(dialogView);
            dialog.show();

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(dialog.getWindow()).setLayout((int) ((6.5 * width) / 7), (int) ((4.5 * height) / 7));
            }

            goalName = dialog.findViewById(R.id.goaL_item_name);
            goalDateFrom = dialog.findViewById(R.id.from_date_goal);
            goalDateTo = dialog.findViewById(R.id.to_date_goal);
            goalAdd = dialog.findViewById(R.id.goal_add);
            goalDelete = dialog.findViewById(R.id.goal_delete);
            goalNotes = dialog.findViewById(R.id.linedEditText);


            goalDateFrom.setOnClickListener(v -> {
                goalDateFrom.setText("");
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year, monthOfYear, dayOfMonth) -> {
                    SimpleDateFormat df = new SimpleDateFormat("'Week'-ww-YYYY", Locale.ENGLISH);
                    c.set(year, monthOfYear, dayOfMonth, 0, 0);
                    String formattedDate = df.format(c.getTime());
                    goalDateFrom.setText(formattedDate);
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            });

            // TODO DATE FORMAT SHJIOT
            goalDateTo.setOnClickListener(v -> {
                goalDateTo.setText("");
                final Calendar c = Calendar.getInstance();
                mYear2 = c.get(Calendar.YEAR);
                mMonth2 = c.get(Calendar.MONTH);
                mDay2 = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                        (view, year, monthOfYear, dayOfMonth) -> {
                            SimpleDateFormat df = new SimpleDateFormat("'Week'-ww-YYYY", Locale.ENGLISH);
                            c.set(year, monthOfYear, dayOfMonth, 0, 0);
                            String formattedDate = df.format(c.getTime());
                            goalDateTo.setText(formattedDate);
                        }, mYear2, mMonth2, mDay2);
                datePickerDialog.show();
            });
        }


        goalDelete.setOnClickListener(v -> dialog.dismiss());

        goalAdd.setOnClickListener(v -> {
            if (!"".contentEquals(goalName.getText()) && !"".contentEquals(goalDateFrom.getText())) {
                if ((!goalDateFrom.getText().toString().matches("[A-Za-z]{4}-\\d{2}-\\d{4}"))) {
                    Toast.makeText(getContext(), "Date must be in WEEK - NUMBER - YYYY format", Toast.LENGTH_LONG).show();
                } else {
                    Goals goal = new Goals();
                    goal.setGoalValueFinished("");
                    goal.setGoalName(goalName.getText().toString());
                    if ("".contentEquals(goalNotes.getText())) {
                        goal.setGoalNotes("");
                    }else{
                        goal.setGoalNotes(goalNotes.getText().toString());
                    }

                    goal.setExpanded(false);
                    goal.setGoalCategory("weekly");

                    SimpleDateFormat df = new SimpleDateFormat("'Week'-ww-YYYY", Locale.ENGLISH);
                    Date date = null;
                    Date date1 = null;
                    try {
                        if ("".contentEquals(goalDateTo.getText())) {
                            Calendar startCalender = Calendar.getInstance();
                            startCalender.setTime(Objects.requireNonNull(df.parse(goalDateFrom.getText().toString())));
                            startCalender.add((Calendar.MONTH), 1);
                            date1 = startCalender.getTime();
                            date = df.parse(goalDateFrom.getText().toString());
                        } else {
                            if ((!goalDateTo.getText().toString().matches("[A-Za-z]{4}-\\d{2}-\\d{4}"))) {
                                Toast.makeText(getContext(), "Date must be in WEEK - NUMBER - YYYY format", Toast.LENGTH_LONG).show();
                            } else {
                                date1 = df.parse(goalDateTo.getText().toString());
                                date = df.parse(goalDateFrom.getText().toString());
                            }
                        }
                        if (date1.getTime() >= date.getTime()) {
                            calculateDaysBetweenEventsAndCreateThem(getContext(), date, date1, goal);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "end date must be in the future, not in the past", Toast.LENGTH_LONG).show();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(getContext(), "Goal Name must not be empty and Starting date must be set", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void calculateDaysBetweenEventsAndCreateThem(Context context, Date startDate, Date endDate, Goals goals) {
        Calendar startCalender = Calendar.getInstance();
        startCalender.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        // 1.st
        goals.setGoalDateStart(startDate.getTime());
        goals.setGoalDateEnd(endDate.getTime());

        // loop
        for (; startCalender.compareTo(endCalendar) <= 0; startCalender.add(Calendar.WEEK_OF_YEAR, 1)) {
            goals.setGoalDateStart(startCalender.getTime().getTime());
            AppDatabase.getInstance(context).getGoalsDao().insert(goals);
        }
    }
}

