package com.example.goaltracker.Statistics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.goaltracker.Database.AppDatabase;
import com.example.goaltracker.Goals.Goals;
import com.example.goaltracker.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner goalsType, goalsSpecif;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Toolbar myToolbar = view.findViewById(R.id.toolbar_statistics_fragment);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(v -> Navigation.findNavController(requireView()).popBackStack());
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("");
        setHasOptionsMenu(true);

        goalsType = view.findViewById(R.id.spinner_statistics_type);
        goalsSpecif = view.findViewById(R.id.spinner_statistics_goals);

        String[] itemsGoalsType = new String[]{"Daily goals", "Weekly goals", "Monthly goals", "Overall"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, itemsGoalsType);
        //set the spinners adapter to the previously created one.
        goalsType.setAdapter(adapter);

        goalsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Daily goals
                if(position == 0){
                    List<String> select_qualification = new ArrayList<>();
                    select_qualification.add("Select goals");
                    select_qualification.addAll(AppDatabase.getInstance(getContext()).getGoalsDao().getDailyGoalsNames());
                    ArrayList<GoalsItem> listVOs = new ArrayList<>();
                    for (int i = 0; i < select_qualification.size(); i++) {
                        GoalsItem stateVO = new GoalsItem();
                        stateVO.setTitle(select_qualification.get(i));
                        stateVO.setSelected(false);
                        listVOs.add(stateVO);
                    }
                    StatisticsAdapter myAdapter = new StatisticsAdapter(getContext(), 0, listVOs);
                    goalsSpecif.setAdapter(myAdapter);
                }
                // Weekly goals
                if(position == 1){
                    List<String> select_qualification = new ArrayList<>();
                    select_qualification.add("Select goals");
                    select_qualification.addAll(AppDatabase.getInstance(getContext()).getGoalsDao().getWeeklyGoalsNames());
                    ArrayList<GoalsItem> listVOs = new ArrayList<>();
                    for (int i = 0; i < select_qualification.size(); i++) {
                        GoalsItem stateVO = new GoalsItem();
                        stateVO.setTitle(select_qualification.get(i));
                        stateVO.setSelected(false);
                        listVOs.add(stateVO);
                    }
                    StatisticsAdapter myAdapter = new StatisticsAdapter(getContext(), 0, listVOs);
                    goalsSpecif.setAdapter(myAdapter);
                }
                // Monthly goals
                if(position == 2){
                    List<String> select_qualification = new ArrayList<>();
                    select_qualification.add("Select goals");
                    select_qualification.addAll(AppDatabase.getInstance(getContext()).getGoalsDao().getMonthlyGoalsNames());
                    ArrayList<GoalsItem> listVOs = new ArrayList<>();
                    for (int i = 0; i < select_qualification.size(); i++) {
                        GoalsItem stateVO = new GoalsItem();
                        stateVO.setTitle(select_qualification.get(i));
                        stateVO.setSelected(false);
                        listVOs.add(stateVO);
                    }
                    StatisticsAdapter myAdapter = new StatisticsAdapter(getContext(), 0, listVOs);
                    goalsSpecif.setAdapter(myAdapter);
                }
                // Overall
                if(position == 3){
                    List<String> select_qualification = new ArrayList<>();
                    select_qualification.add("Select goals");
                    select_qualification.addAll(AppDatabase.getInstance(getContext()).getGoalsDao().getAllGoalsNames());
                    ArrayList<GoalsItem> listVOs = new ArrayList<>();
                    for (int i = 0; i < select_qualification.size(); i++) {
                        GoalsItem stateVO = new GoalsItem();
                        stateVO.setTitle(select_qualification.get(i));
                        stateVO.setSelected(false);
                        listVOs.add(stateVO);
                    }
                    StatisticsAdapter myAdapter = new StatisticsAdapter(getContext(), 0, listVOs);
                    goalsSpecif.setAdapter(myAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_statistics_screen, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {// Not implemented here
            Navigation.findNavController(requireView()).navigate(R.id.action_statisticsFragment_to_settingsFragment);
        }
        return super.onOptionsItemSelected(item);
    }
}
