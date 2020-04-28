package com.example.goaltracker.ToDoList;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.goaltracker.R;
import com.example.goaltracker.Util.LinedEditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    RecyclerView mRecyclerView;
    ToDoListAdapter mRecyclerViewAdapter;
    private FloatingActionButton toDoListItemButton, toDoListenItemButtonAdd;
    Dialog dialog;
    private EditText toDoListTitle, toDoListDate;
    private LinedEditText toDoListNotes;

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

     /*   mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerViewAdapter = new ToDoListAdapter(getContext());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);*/

        toDoListItemButton = view.findViewById(R.id.buttonAddToDoItem);

        toDoListItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflateToDoListDialog();
            }
        });





    }

    private void inflateToDoListDialog() {
        Context mContext = getContext();
        final View dialogView = View.inflate(mContext ,R.layout.to_do_list_items, null);
        if(mContext != null) {
            dialog = new Dialog(mContext);
            dialog.setContentView(dialogView);
            dialog.show();

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(dialog.getWindow()).setLayout((6 * width)/7, (4 * height)/5);
            }

            toDoListDate = dialog.findViewById(R.id.toDoListDate);
            toDoListTitle = dialog.findViewById(R.id.toDoListTitle);
            toDoListNotes = dialog.findViewById(R.id.toDoListNotes);
            toDoListenItemButtonAdd = dialog.findViewById(R.id.addToDoListItem);
        }
    }

}
