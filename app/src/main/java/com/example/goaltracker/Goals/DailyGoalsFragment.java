package com.example.goaltracker.Goals;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.goaltracker.R;
import com.example.goaltracker.Util.LinedEditText;
import com.example.goaltracker.Util.ListItemAddProg;
import com.example.goaltracker.Util.Spinner2GoalAdapter;
import com.example.goaltracker.Util.SpinnerGoalAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private EditText goalName;
    private LinedEditText goalNotes;

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
                Objects.requireNonNull(dialog.getWindow()).setLayout((6 * width) / 7, (4 * height) / 5);
            }

            goalName = dialog.findViewById(R.id.goaL_item_name);
            goalNotes = dialog.findViewById(R.id.goals_notes);

            // spinners
            // Declaring the String Array with the Text Data for the Spinners


            // Declaring the Integer Array with resourse Id's of Images for the Spinners


            ArrayList<ListItemAddProg> itemAddProgs = new ArrayList<>();
            itemAddProgs.add(new ListItemAddProg("Goal Type", R.drawable.ic_log_type));
            itemAddProgs.add(new ListItemAddProg("Checkbox", R.drawable.checkbox_spinner1_goal));
            itemAddProgs.add(new ListItemAddProg("Time", R.drawable.time_spinner1_goal));
            itemAddProgs.add(new ListItemAddProg("Count/Amount", R.drawable.amount_spinner1_goal));

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
                    }else if(id == 1){
                        goalTypeSpinner2.setVisibility(View.INVISIBLE);
                    } else if(id == 0){
                        goalTypeSpinner2.setVisibility(View.INVISIBLE);
                    }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }
}
