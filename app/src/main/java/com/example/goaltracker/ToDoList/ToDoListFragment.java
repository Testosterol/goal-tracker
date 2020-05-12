package com.example.goaltracker.ToDoList;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TimePicker;

import com.example.goaltracker.Database.AppDatabase;
import com.example.goaltracker.R;
import com.example.goaltracker.Util.LinedEditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToDoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private ToDoListAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private FloatingActionButton toDoListItemButton;
    Dialog dialog;
    private EditText toDoListTitle, toDoListDate, toDoListTime;
    private LinedEditText toDoListNotes;
    private ImageButton addToDoListItem;
    private int mYear, mMonth, mDay;
    ToDoListViewModel toDoListViewModel;


    public ToDoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToDoListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToDoListFragment newInstance(String param1, String param2) {
        ToDoListFragment fragment = new ToDoListFragment();
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
        return inflater.inflate(R.layout.fragment_to_do_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(myToolbar);

        myToolbar.setNavigationOnClickListener(v ->{
            Navigation.findNavController(requireView()).popBackStack();
        });
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("");
        setHasOptionsMenu(true);

        toDoListViewModel = new ViewModelProvider(this).get(ToDoListViewModel.class);
        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        toDoListViewModel.init(toDoListViewModel.toDoListDao);

        mRecyclerViewAdapter = new ToDoListAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final ToDoList item = mRecyclerViewAdapter.getData().get(position);
                AppDatabase.getInstance(getContext()).getToDoListDao().delete(item);
                //mRecyclerViewAdapter.removeItem(position);
                Snackbar snackbar = Snackbar.make(view, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", view1 -> {
                    AppDatabase.getInstance(getContext()).getToDoListDao().insert(item);
                    //mRecyclerViewAdapter.restoreItem(item, position);
                    mRecyclerView.scrollToPosition(position);
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mRecyclerView);


        toDoListViewModel.allToDoListItems.observe(getViewLifecycleOwner(), toDoListList -> {
            // update UI
            try {
                //to prevent animation recyclerView when change the list
                mRecyclerView.setItemAnimator(null);
                ((SimpleItemAnimator) Objects.requireNonNull(mRecyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRecyclerViewAdapter.submitList(toDoListList);
        });

        if (toDoListViewModel != null) {
            toDoListViewModel.filterTextAll.setValue("");
        }

        toDoListItemButton = view.findViewById(R.id.buttonAddToDoItem);

        toDoListItemButton.setOnClickListener(v -> inflateToDoListDialog());


    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation, menu);

        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));
        }
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                toDoListViewModel.filterTextAll.setValue("%" + query + "%");
                return false;
            }
        });
    }

    private void inflateToDoListDialog() {
        Context mContext = getContext();
        final View dialogView = View.inflate(mContext, R.layout.to_do_list_items, null);
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

            toDoListDate = dialog.findViewById(R.id.toDoListDate);
            toDoListTitle = dialog.findViewById(R.id.toDoListTitle);
            toDoListNotes = dialog.findViewById(R.id.toDoListNotes);
            addToDoListItem = dialog.findViewById(R.id.toDoListAdd);
            toDoListTime = dialogView.findViewById(R.id.toDoListTime);

            toDoListDate.setFocusable(false);
            toDoListDate.setClickable(true);

            toDoListDate.setOnClickListener(v -> {
                toDoListDate.setText("");
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (view, year, monthOfYear, dayOfMonth) -> {
                            String formattedDate = String.format(Locale.ENGLISH, "%02d-%02d-%d", dayOfMonth, (monthOfYear + 1), year);
                            toDoListDate.setText(formattedDate);
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            });

            toDoListTime.setOnClickListener(v->{
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), (timePicker, selectedHour, selectedMinute) ->
                        toDoListTime.setText( selectedHour + ":" + selectedMinute), hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            });

            addToDoListItem.setFocusable(false);
            addToDoListItem.setClickable(true);

            addToDoListItem.setOnClickListener(v -> {
                // add to db ?
                if (!toDoListTitle.getText().toString().equals("")) {
                    if (null == toDoListNotes.getText()) {
                        ToDoList toDoList = new ToDoList(toDoListTitle.getText().toString(),
                                toDoListDate.getText().toString(), "", toDoListTime.getText().toString());
                        AppDatabase.getInstance(getContext()).getToDoListDao().insert(toDoList);
                        dialog.dismiss();
                    } else {
                        ToDoList toDoList = new ToDoList(toDoListTitle.getText().toString(),
                                toDoListDate.getText().toString(), toDoListNotes.getText().toString(), toDoListTime.getText().toString());
                        AppDatabase.getInstance(getContext()).getToDoListDao().insert(toDoList);
                        dialog.dismiss();
                    }
                }


            });
        }
    }

}
