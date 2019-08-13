package com.arkainfoteck.dabagalli.adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.models.SpinnerModel;


import java.util.ArrayList;

public class CustomizedAdopter extends BaseAdapter {
    Context context;
    ArrayList<SpinnerModel>spinnerModels;

    public CustomizedAdopter(Context context, ArrayList<SpinnerModel> spinnerModels) {
        this.context = context;
        this.spinnerModels = spinnerModels;
    }

    @Override
    public int getCount() {
        return spinnerModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpinnerModel spinnerModel=spinnerModels.get(position);
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        convertView=layoutInflater.inflate(R.layout.spinner_items,parent,false);
        TextView textView=convertView.findViewById(R.id.textdata);
        textView.setText(spinnerModel.getLoc_name());
        return convertView;
    }
}

