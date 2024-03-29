package com.example.goaltracker.Goals;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

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
 * Use the {@link DailyGoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyGoalsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Dialog dialog;
    private EditText goalName, goalDateFrom, goalDateTo;
    private TextView goalsTitleBar;
    private LinedEditText goalNotes;
    private ImageButton goalAdd, goalNextRight, goalPreviousLeft, goalDelete;
    private DailyGoalsViewModel goalsViewModel;
    private RecyclerView mRecyclerView;
    private DailyGoalsAdapter mRecyclerViewAdapter;

    private int mYear, mMonth, mDay, mYear2, mMonth2, mDay2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DailyGoalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyGoals.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyGoalsFragment newInstance(String param1, String param2) {
        DailyGoalsFragment fragment = new DailyGoalsFragment();
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
        return inflater.inflate(R.layout.fragment_daily_goals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        goalNextRight = view.findViewById(R.id.image_button_toolbar_daily_goals_right);
        goalPreviousLeft = view.findViewById(R.id.image_button_toolbar_daily_goals_left);
        goalsTitleBar = view.findViewById(R.id.toolbar_daily_goals_title);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE dd-MM-yyyy", Locale.ENGLISH);
        String formattedDate = df.format(c.getTime());

        goalsTitleBar.setText(formattedDate);

        goalNextRight.setOnClickListener(v -> {
            c.add(Calendar.DATE, +1);
            String formattedDattee = df.format(c.getTime());
            goalsTitleBar.setText(formattedDattee);
            if (goalsViewModel != null) {
                String[] cutTitleDate = goalsTitleBar.getText().toString().split(" ");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date date = null;
                try {
                    date = dateFormat.parse(cutTitleDate[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date != null) {
                    long millis = date.getTime();
                    goalsViewModel.filterTextAll.setValue(millis);
                }
            }
        });


        goalPreviousLeft.setOnClickListener(v -> {
            c.add(Calendar.DATE, -1);
            String formattedDatee = df.format(c.getTime());
            goalsTitleBar.setText(formattedDatee);
            if (goalsViewModel != null) {
                String[] cutTitleDate = goalsTitleBar.getText().toString().split(" ");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date date = null;
                try {
                    date = dateFormat.parse(cutTitleDate[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date != null) {
                    long millis = date.getTime();
                    goalsViewModel.filterTextAll.setValue(millis);
                }
            }
        });

        goalsViewModel = new ViewModelProvider(this).get(DailyGoalsViewModel.class);
        mRecyclerView = view.findViewById(R.id.recycler_view_daily_goals_content);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        goalsViewModel.init(goalsViewModel.goalsDao);

        mRecyclerViewAdapter = new DailyGoalsAdapter(getContext());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        goalsViewModel.allGoalsItems.observe(getViewLifecycleOwner(), goalsList -> {
            // update UI
            try {
                //to prevent animation recyclerView when change the list
                mRecyclerView.setItemAnimator(null);
                ((SimpleItemAnimator) Objects.requireNonNull(mRecyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRecyclerViewAdapter.submitList(goalsList);
        });

        if (goalsViewModel != null) {
            String[] cutTitleDate = goalsTitleBar.getText().toString().split(" ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date date = null;
            try {
                date = dateFormat.parse(cutTitleDate[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                long millis = date.getTime();
                goalsViewModel.filterTextAll.setValue(millis);
            }
        }


        FloatingActionButton addGoalButton = view.findViewById(R.id.button_add_goal_item);
        addGoalButton.setOnClickListener(v -> inflateToDoListDialog());


    }


    private void inflateToDoListDialog() {
        Context mContext = getContext();
        final View dialogView = View.inflate(mContext, R.layout.daily_goal_item, null);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String formattedDate = String.format(Locale.ENGLISH, "%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year);
                        goalDateFrom.setText(formattedDate);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            });

            goalDateTo.setOnClickListener(v -> {
                goalDateTo.setText("");
                final Calendar c = Calendar.getInstance();
                mYear2 = c.get(Calendar.YEAR);
                mMonth2 = c.get(Calendar.MONTH);
                mDay2 = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                        (view, year, monthOfYear, dayOfMonth) -> {
                            String formattedDate = String.format(Locale.ENGLISH, "%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year);
                            goalDateTo.setText(formattedDate);
                        }, mYear2, mMonth2, mDay2);
                datePickerDialog.show();
            });

            goalDelete.setOnClickListener(v -> dialog.dismiss());

            goalAdd.setOnClickListener(v -> {
                if (!"".contentEquals(goalName.getText()) && !"".contentEquals(goalDateFrom.getText())) {
                    if ((!goalDateFrom.getText().toString().matches("\\d{2}-\\d{2}-\\d{4}"))) {
                        Toast.makeText(getContext(), "Date must be in DD-MM-YYY format", Toast.LENGTH_LONG).show();
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
                        goal.setGoalCategory("daily");

                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
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
                                if ((!goalDateTo.getText().toString().matches("\\d{2}-\\d{2}-\\d{4}"))) {
                                    Toast.makeText(getContext(), "Date must be in DD-MM-YYY format", Toast.LENGTH_LONG).show();
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
    }

    private static void calculateDaysBetweenEventsAndCreateThem(Context context, Date startDate, Date endDate, Goals goals) {
        Calendar startCalender = Calendar.getInstance();
        startCalender.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        // 1.st
        goals.setGoalDateStart(startDate.getTime());
        goals.setGoalDateEnd(endDate.getTime());

        // loop
        for (; startCalender.compareTo(endCalendar) <= 0; startCalender.add(Calendar.DATE, 1)) {
            goals.setGoalDateStart(startCalender.getTime().getTime());
            AppDatabase.getInstance(context).getGoalsDao().insert(goals);
        }

    }
}
