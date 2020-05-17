package com.example.goaltracker.Util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goaltracker.R;

import java.util.ArrayList;

public class Spinner2GoalAdapter extends ArrayAdapter<String> {
    LayoutInflater inflater;
    ArrayList<String> objects;
    SpinnerGoalAdapter.ViewHolder holder = null;

    public Spinner2GoalAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        inflater = ((Activity) context).getLayoutInflater();
        this.objects = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        String kek= objects.get(position);
        View row = convertView;
        if (null == row) {
            holder = new SpinnerGoalAdapter.ViewHolder();
            row = inflater.inflate(R.layout.spinner2_row_goal_type, parent, false);
            holder.name = (TextView) row.findViewById(R.id.spinner_name_2);
            row.setTag(holder);
        } else {
            holder = (SpinnerGoalAdapter.ViewHolder) row.getTag();
        }
        holder.name.setText(kek);
        return row;
    }

    static class ViewHolder {
        TextView name;
    }
}