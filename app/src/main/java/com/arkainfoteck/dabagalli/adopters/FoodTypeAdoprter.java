package com.arkainfoteck.dabagalli.adopters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arkainfoteck.dabagalli.activitys.DashBord;
import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.database.SharedPreference;
import com.arkainfoteck.dabagalli.models.FoodTypeModel;

import java.util.ArrayList;

public class FoodTypeAdoprter extends RecyclerView.Adapter<FoodTypeAdoprter.MyView> {

    Context context;
    ArrayList<FoodTypeModel>foodTypeModels;
    public SharedPreference sharedPreference;


    public  FoodTypeAdoprter(Context context, ArrayList<FoodTypeModel> foodTypeModels) {
        this.context = context;
        this.foodTypeModels = foodTypeModels;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.food_type_items,viewGroup,false);
        MyView myView=new MyView(view,context,foodTypeModels);
        sharedPreference=new SharedPreference();
        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {
        FoodTypeModel foodTypeModel=foodTypeModels.get(i);
        myView.food_type_name.setText(foodTypeModel.getName());
    }

    @Override
    public int getItemCount() {
        return foodTypeModels.size();
    }

    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        ArrayList<FoodTypeModel>foodTypeModels;
        TextView food_type_name;

        public MyView(@NonNull View itemView,Context context,ArrayList<FoodTypeModel>foodTypeModels) {
            super(itemView);
            this.context=context;
            this.foodTypeModels=foodTypeModels;
            food_type_name=(TextView)itemView.findViewById(R.id.food_type_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int row = getAdapterPosition();
            FoodTypeModel foodTypeModel = foodTypeModels.get(row);
            String foodname=foodTypeModel.getName();
            String foodid=foodTypeModel.getId();
            Toast.makeText(context, ""+foodname, Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(context, DashBord.class);
            sharedPreference.Food(context,foodname,"","",foodid);
            context.startActivity(intent);
        }
    }
}
