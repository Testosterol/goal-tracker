package com.example.goaltracker.Settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.goaltracker.R;

import org.w3c.dom.Text;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView faq;
    private TextView about;
    private TextView contact;
    private Switch vibrateSwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar myToolbar = view.findViewById(R.id.toolbar_settings_fragment);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(v -> Navigation.findNavController(requireView()).popBackStack());
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("");

        faq = view.findViewById(R.id.settings_faq_tv);
        contact = view.findViewById(R.id.settings_contact_tv);
        about = view.findViewById(R.id.settings_about_tv);
        vibrateSwitch = view.findViewById(R.id.switch1);

        faq.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_faqFragment));

        contact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
            intent.setData(Uri.parse("mailto:denistreb@gmail.com")); // TODO: CHANGE EMAIL
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            startActivity(intent);
        });

        about.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_aboutFragment));

        SharedPreferences preferences = requireContext().getSharedPreferences("goal_tracker_identifier", Context.MODE_PRIVATE);
        if (preferences.getBoolean("vibration_goal_tracker", false)) {
            vibrateSwitch.setChecked(true);
        } else {
            vibrateSwitch.setChecked(false);
        }

        vibrateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // do something, the isChecked will be
            // true if the switch is in the On position
            SharedPreferences.Editor editor = preferences.edit();
            if (isChecked) {
                editor.putBoolean("vibration_goal_tracker", true);
            } else {
                editor.putBoolean("vibration_goal_tracker", false);
            }
            editor.commit();
        });

    }
}
