package com.example.goaltracker.Main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.goaltracker.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton todoListButton, goalsButton, statisticsButton, settingsButton;
    private TextView todolistTextView, goalsTextView, statisticsTextView, settingsTextView;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        todoListButton = view.findViewById(R.id.toDoList);
        goalsButton = view.findViewById(R.id.goals);
        statisticsButton = view.findViewById(R.id.statistics);
        settingsButton = view.findViewById(R.id.settings);
        todolistTextView = view.findViewById(R.id.to_do_list_TextView_main_screen);
        goalsTextView = view.findViewById(R.id.goals_TextView_main_screen);
        statisticsTextView = view.findViewById(R.id.statistics_TextView_main_screen);
        settingsTextView = view.findViewById(R.id.settings_TextView_main_screen);


        Toolbar myToolbar = view.findViewById(R.id.toolbar_main_fragment);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(myToolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("");


        todoListButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_toDoListFragment));
        goalsButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_goalsFragment));
        statisticsButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_statisticsFragment));
        settingsButton.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_settingsFragment));
        todolistTextView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_toDoListFragment));
        goalsTextView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_goalsFragment));
        statisticsTextView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_statisticsFragment));
        settingsTextView.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_settingsFragment));

    }

}
