package com.arkainfoteck.dabagalli.adopters;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arkainfoteck.dabagalli.models.FoodTypeModel;
import com.arkainfoteck.dabagalli.models.FooditemsModel;
import com.arkainfoteck.dabagalli.R;

import java.util.ArrayList;

public class FoodItemsAdopter extends RecyclerView.Adapter<FoodItemsAdopter.MyViewData> {
   Context context;
   ArrayList<FooditemsModel> fooditems;
   String position;


    public FoodItemsAdopter(Context context, ArrayList<FooditemsModel> fooditems,String position) {
        this.context = context;
        this.fooditems = fooditems;
        this.position=position;
    }
    @NonNull
    @Override
    public MyViewData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.box_items,viewGroup,false);
        //Toast.makeText(context, "qwer"+position, Toast.LENGTH_SHORT).show();
        return new MyViewData(view,position,fooditems);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewData myViewData, int i) {
        final FooditemsModel fooditemsModel=fooditems.get(i);
      //  myViewData.box.setText(fooditemsModel.getData());

        if(fooditemsModel.getData()>=1){

        //    myViewData.box.setBackgroundColor(Color.CYAN);
            myViewData.box.setBackgroundResource(R.drawable.select_item_color);
        }else {
            myViewData.box.setBackgroundResource(R.drawable.un_selected_item_color);

            //       myViewData.box.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return fooditems.size();
    }

    public class MyViewData extends RecyclerView.ViewHolder
    {
        LinearLayout ll1;
        TextView box;
        String positon;
        ArrayList<FooditemsModel>fooditemsModels;

        public MyViewData(@NonNull View itemView,String positon, ArrayList<FooditemsModel>fooditemsModels) {
            super(itemView);
            this.positon=positon;
            this.fooditemsModels=fooditemsModels;
            ll1=itemView.findViewById(R.id.ll1);
            box=itemView.findViewById(R.id.box);


        }
    }
}
