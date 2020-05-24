package com.example.goaltracker.Goals;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.goaltracker.Database.AppDatabase;
import com.example.goaltracker.R;
import com.example.goaltracker.ToDoList.ToDoListViewModel;
import com.example.goaltracker.Util.LinedEditText;
import com.example.goaltracker.Util.ListItemAddProg;
import com.example.goaltracker.Util.Spinner2GoalAdapter;
import com.example.goaltracker.Util.SpinnerGoalAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private Spinner goalTypeSpinner1, goalTypeSpinner2;
    private EditText goalName, goalDateFrom, goalDateTo, goalAmount;
    private TextView goalsTitleBar;
    private LinedEditText goalNotes;
    private ImageButton goalAdd, goalNextRight, goalPreviousLeft;
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
            String formattedDatee = df.format(c.getTime());
            goalsTitleBar.setText(formattedDatee);
            if (goalsViewModel != null) {
                String[] cutTitleDate = goalsTitleBar.getText().toString().split(" ");
                Log.d("kokot", "goalNextRight: " + cutTitleDate[1]);
                goalsViewModel.filterTextAll.setValue(cutTitleDate[1]);
            }
        });

        goalPreviousLeft.setOnClickListener(v -> {
            c.add(Calendar.DATE, -1);
            String formattedDatee = df.format(c.getTime());
            goalsTitleBar.setText(formattedDatee);
            if (goalsViewModel != null) {
                String[] cutTitleDate = goalsTitleBar.getText().toString().split(" ");
                Log.d("kokot", "goalPreviousLeft: " + cutTitleDate[1]);
                goalsViewModel.filterTextAll.setValue(cutTitleDate[1]);
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

        mRecyclerViewAdapter = new DailyGoalsAdapter();
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
            Log.d("kokot", "goalsViewModel.allGoalsItems: " + goalsList);
            for(Goals goals : goalsList){
                Log.d("kokot", "goalsViewModel.getGoalDateStart: " + goals.getGoalDateStart());
                Log.d("kokot", "goalsViewModel.getGoalDateEnd: " + goals.getGoalDateEnd());
            }
            mRecyclerViewAdapter.submitList(goalsList);
        });

        if (goalsViewModel != null) {
            String[] cutTitleDate = goalsTitleBar.getText().toString().split(" ");
            goalsViewModel.filterTextAll.setValue(cutTitleDate[1]);
        }


        FloatingActionButton toDoListItemButton = view.findViewById(R.id.button_add_goal_item);
        toDoListItemButton.setOnClickListener(v -> inflateToDoListDialog());



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
                Objects.requireNonNull(dialog.getWindow()).setLayout((int) ((6.5 * width) / 7), (int) ((4.5 * height) / 5));
            }

            goalName = dialog.findViewById(R.id.goaL_item_name);
            goalNotes = dialog.findViewById(R.id.goals_notes);
            goalDateFrom = dialog.findViewById(R.id.from_date_goal);
            goalDateTo = dialog.findViewById(R.id.to_date_goal);
            goalAdd = dialog.findViewById(R.id.goal_add);
            goalAmount = dialog.findViewById(R.id.goal_amount);

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



            // Declaring the Integer Array with resourse Id's of Images for the Spinners
            ArrayList<ListItemAddProg> itemAddProgs = new ArrayList<>();
            itemAddProgs.add(new ListItemAddProg("Goal Type", R.drawable.ic_log_type));
            itemAddProgs.add(new ListItemAddProg("Checkbox", R.drawable.checkbox_spinner1_goal));
            itemAddProgs.add(new ListItemAddProg("Time", R.drawable.time_spinner1_goal));
            itemAddProgs.add(new ListItemAddProg("Count", R.drawable.amount_spinner1_goal));

            // Setting a Custom Adapter to the Spinner
            goalTypeSpinner1 = dialog.findViewById(R.id.goal_item_log_type_spinner_1);
            goalTypeSpinner2 = dialog.findViewById(R.id.goal_item_log_type_spinner_2);

            goalTypeSpinner1.setAdapter(new SpinnerGoalAdapter(getContext(), R.layout.spinner1_row_goal_type, itemAddProgs));
            goalTypeSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    if(id == 2){
                        ArrayList<String> logTypesSpinner2 = new ArrayList<>();
                        logTypesSpinner2.add("Hours");
                        logTypesSpinner2.add("Minutes");
                        logTypesSpinner2.add("Seconds");
                        goalTypeSpinner2.setVisibility(View.VISIBLE);
                        goalTypeSpinner2.setAdapter(new Spinner2GoalAdapter(getContext(), R.layout.spinner2_row_goal_type, logTypesSpinner2));
                        goalAmount.setVisibility(View.VISIBLE);
                    }else if(id == 3){
                        ArrayList<String> logTypesSpinner2 = new ArrayList<>();
                        logTypesSpinner2.add("Pcs");
                        logTypesSpinner2.add("Pounds");
                        logTypesSpinner2.add("Kgs");
                        logTypesSpinner2.add("bits");
                        logTypesSpinner2.add("slices");
                        logTypesSpinner2.add("portions");
                        logTypesSpinner2.add("parcels");
                        logTypesSpinner2.add("%");
                        goalTypeSpinner2 = dialog.findViewById(R.id.goal_item_log_type_spinner_2);
                        goalTypeSpinner2.setVisibility(View.VISIBLE);
                        goalTypeSpinner2.setAdapter(new Spinner2GoalAdapter(getContext(), R.layout.spinner2_row_goal_type, logTypesSpinner2));
                        goalAmount.setVisibility(View.VISIBLE);
                    }else if(id == 1){
                        goalTypeSpinner2.setVisibility(View.INVISIBLE);
                        goalAmount.setVisibility(View.GONE);
                    } else if(id == 0){
                        goalTypeSpinner2.setVisibility(View.INVISIBLE);
                        goalAmount.setVisibility(View.GONE);
                    }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            goalAdd.setOnClickListener(v -> {
                if(goalName.getText() != null  && !goalTypeSpinner1.getSelectedItem().toString().equals("Type")) {
                    Goals goal = new Goals();
                    goal.setGoalName(goalName.getText().toString());
                    if(goalNotes.getText() != null){
                        goal.setGoalNotes(goalNotes.getText().toString());
                    }else{
                        goal.setGoalNotes("");
                    }
                    ListItemAddProg listItemAddProg1 = (ListItemAddProg) goalTypeSpinner1.getSelectedItem();
                    goal.setGoalType1(listItemAddProg1.getName());
                    if(goalTypeSpinner2.getSelectedItem() != null) {
                        goal.setGoalType2(goalTypeSpinner2.getSelectedItem().toString());
                    }else{
                        goal.setGoalType2("");
                    }
                    goal.setGoalDateStart(goalDateFrom.getText().toString());
                    goal.setGoalDateEnd(goalDateTo.getText().toString());
                    Log.d("kokot", "setGoalDateEnd(goalDateTo.getText().toString()): " + goalDateTo.getText().toString());
                    if(goalAmount.getVisibility() == View.VISIBLE){
                        goal.setGoalAmount(goalAmount.getText().toString());
                    }else{
                        goal.setGoalAmount("");
                    }
                    AppDatabase.getInstance(getContext()).getGoalsDao().insert(goal);
                }else{
                    Snackbar.make(requireView(), "Goal Name must not be empty and Goal type must be chosen", Snackbar.LENGTH_LONG);
                }
            });
        }
    }
}
