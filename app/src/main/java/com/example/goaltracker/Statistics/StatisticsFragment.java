package com.example.goaltracker.Statistics;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
import com.example.goaltracker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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

    Spinner goalsType;
    MultiSpinner goalsSpecific;
    BarChart mpBarChart;

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

        goalsSpecific = view.findViewById(R.id.multispinner);
        mpBarChart = view.findViewById(R.id.statistics_bar_chart);


        Toolbar myToolbar = view.findViewById(R.id.toolbar_statistics_fragment);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(v -> Navigation.findNavController(requireView()).popBackStack());
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("");
        setHasOptionsMenu(true);

        goalsType = view.findViewById(R.id.spinner_statistics_type);

        String[] itemsGoalsType = new String[]{"Daily goals", "Weekly goals", "Monthly goals", "Overall"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, itemsGoalsType);
        //set the spinners adapter to the previously created one.
        goalsType.setAdapter(adapter);


        ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 1000));
        barEntryArrayList.add(new BarEntry(2, 212));
        barEntryArrayList.add(new BarEntry(3, 1204));
        barEntryArrayList.add(new BarEntry(4, 4556));
        barEntryArrayList.add(new BarEntry(5, 12));
        barEntryArrayList.add(new BarEntry(6, 444));

        ArrayList<BarEntry> barEntryArrayList1 = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 324));
        barEntryArrayList.add(new BarEntry(2, 5235));
        barEntryArrayList.add(new BarEntry(3, 234));
        barEntryArrayList.add(new BarEntry(4, 345));
        barEntryArrayList.add(new BarEntry(5, 6456));
        barEntryArrayList.add(new BarEntry(6, 66));


        ArrayList<BarEntry> barEntryArrayList2 = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(1, 63252));
        barEntryArrayList.add(new BarEntry(2, 234234));
        barEntryArrayList.add(new BarEntry(3, 53));
        barEntryArrayList.add(new BarEntry(4, 534));
        barEntryArrayList.add(new BarEntry(5, 534));
        barEntryArrayList.add(new BarEntry(6, 44));

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Dataset 1");
        barDataSet.setColor(Color.RED);

        BarDataSet barDataSet2 = new BarDataSet(barEntryArrayList1, "Dataset 2");
        barDataSet.setColor(Color.GREEN);

        BarDataSet barDataSet3 = new BarDataSet(barEntryArrayList2, "Dataset 3");
        barDataSet.setColor(Color.BLUE);

        BarData barData = new BarData(barDataSet, barDataSet2, barDataSet3);
        mpBarChart.setData(barData);

        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday"};
        XAxis xAxis = mpBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        mpBarChart.setDragEnabled(true);
        mpBarChart.setVisibleXRangeMaximum(3);

        float barSpace = 0.08f;
        float groupSpace = 0.44f;
        barData.setBarWidth(0.10f);

        mpBarChart.getXAxis().setAxisMinimum(0);
        mpBarChart.getXAxis().setAxisMaximum(0+mpBarChart.getBarData().getGroupWidth(groupSpace,barSpace));
        mpBarChart.getAxisLeft().setAxisMinimum(0);

        mpBarChart.groupBars(0,groupSpace,barSpace);
        mpBarChart.invalidate();


        // TODO: figure out why we are getting selected numebr of all items even if they are not selected
        goalsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Daily goals
                if(position == 0){
                    List<String> goals = AppDatabase.getInstance(getContext()).getGoalsDao().getDailyGoalsNames();
                    goalsSpecific.setItems(goals, "Select goals", new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected, List<String> selectedItems) {


                            Log.d("tesrasds", " MULTI SPINNER: " + selected.length);
                            Log.d("tesrasds", " MULTI SPINNER: " + selected[0]);
                            Log.d("tesrasds", " MULTI SPINNER: " + selected[1]);
                        }
                    });

                }
                // Weekly goals
                if(position == 1){
                    List<String> goals = AppDatabase.getInstance(getContext()).getGoalsDao().getWeeklyGoalsNames();
                    goalsSpecific.setItems(goals, "Select goals", new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected, List<String> selectedItems) {
                            Log.d("tesrasds", " MULTI SPINNER: " + selected.length);
                            Log.d("tesrasds", " MULTI SPINNER: " + selected[0]);
                        }
                    });

                }
                // Monthly goals
                if(position == 2){
                    List<String> goals = AppDatabase.getInstance(getContext()).getGoalsDao().getMonthlyGoalsNames();
                    goalsSpecific.setItems(goals, "Select goals", new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected, List<String> selectedItems) {
                            Log.d("tesrasds", " MULTI SPINNER: " + selected.length);
                            Log.d("tesrasds", " MULTI SPINNER: " + selected[0]);
                        }
                    });

                }
                // Overall
                if(position == 3){
                    List<String> goals = AppDatabase.getInstance(getContext()).getGoalsDao().getAllGoalsNames();

                    goalsSpecific.setItems(goals, "Select goals", new MultiSpinner.MultiSpinnerListener() {
                        @Override
                        public void onItemsSelected(boolean[] selected, List<String> selectedItems) {
                            Log.d("tesrasds", " MULTI SPINNER: " + selected.length);
                            Log.d("tesrasds", " MULTI SPINNER: " + selected[0]);
                        }
                    });

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
