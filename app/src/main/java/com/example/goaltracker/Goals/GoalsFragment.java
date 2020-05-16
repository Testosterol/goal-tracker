package com.example.goaltracker.Goals;

import android.graphics.drawable.shapes.RoundRectShape;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.goaltracker.R;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GoalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoalsFragment newInstance(String param1, String param2) {
        GoalsFragment fragment = new GoalsFragment();
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
        return inflater.inflate(R.layout.fragment_goals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar myToolbar = view.findViewById(R.id.toolbar_goals_fragment);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(v ->Navigation.findNavController(requireView()).popBackStack());
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("");
        setHasOptionsMenu(true);

        View dailyGoalsConstraintLayoutView = view.findViewById(R.id.constraintLayout_daily_goals);
        View weeklyGoalsConstraintLayoutView = view.findViewById(R.id.constraintLayout_weekly_goals);
        View monthlyGoalsConstraintLayoutView = view.findViewById(R.id.constraintLayout_monthly_goals);

        View dailyGoalsRectangleView = view.findViewById(R.id.rectangle_daily_goals);
        View weeklyGoalsRectangleView = view.findViewById(R.id.rectangle_weekly_goals);
        View monthlyGoalsRectangleView = view.findViewById(R.id.rectangle_monthly_goals);

        TextView dailyGoalsTextView = view.findViewById(R.id.daily_goals_TextView_goals_screen);
        TextView weeklyGoalsTextView = view.findViewById(R.id.weekly_goals_TextView_goals_screen);
        TextView monthlyGoalsTextView = view.findViewById(R.id.monthly_goals_TextView_goals_screen);

        ImageButton dailyGoalsImageButton = view.findViewById(R.id.daily_goals_image_button_goals_screen);
        ImageButton weeklyGoalsImageButton = view.findViewById(R.id.weekly_goals_image_button_goals_screen);
        ImageButton monthlyGoalsImageButton = view.findViewById(R.id.monthly_goals_image_button_goals_screen);

        dailyGoalsConstraintLayoutView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_dailyGoals));
        weeklyGoalsConstraintLayoutView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_weeklyGoalsFragment));
        monthlyGoalsConstraintLayoutView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_monthlyGoalsFragment));

        dailyGoalsRectangleView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_dailyGoals));
        weeklyGoalsRectangleView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_weeklyGoalsFragment));
        monthlyGoalsRectangleView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_monthlyGoalsFragment));

        dailyGoalsTextView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_dailyGoals));
        weeklyGoalsTextView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_weeklyGoalsFragment));
        monthlyGoalsTextView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_monthlyGoalsFragment));

        dailyGoalsImageButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_dailyGoals));
        weeklyGoalsImageButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_weeklyGoalsFragment));
        monthlyGoalsImageButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_goalsFragment_to_monthlyGoalsFragment));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO SEARCH BAR
        if (item.getItemId() == R.id.action_settings) {// Not implemented here
            Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_settingsFragment);
        }
        return super.onOptionsItemSelected(item);
    }
}
