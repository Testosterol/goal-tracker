package com.example.goaltracker.Goals;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.goaltracker.R;
import com.example.goaltracker.Util.LinedEditText;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthlyGoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthlyGoalsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Dialog dialog;
    private Spinner goalTypeSpinner1, goalTypeSpinner2;
    private EditText goalName, goalDateFrom, goalDateTo, goalAmount;
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

    public MonthlyGoalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthlyGoalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthlyGoalsFragment newInstance(String param1, String param2) {
        MonthlyGoalsFragment fragment = new MonthlyGoalsFragment();
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
        return inflater.inflate(R.layout.fragment_monthly_goals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goalNextRight = view.findViewById(R.id.image_button_toolbar_monthly_goals_right);
        goalPreviousLeft = view.findViewById(R.id.image_button_toolbar_monthly_goals_left);
        goalsTitleBar = view.findViewById(R.id.toolbar_monthly_goals_title);
    }
}
