package com.arkainfoteck.dabagalli.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.activitys.LunchScreen;
import com.arkainfoteck.dabagalli.adopters.FoodAdopter;
import com.arkainfoteck.dabagalli.models.FoodModel;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {
    RecyclerView foodlist;
    FoodAdopter foodAdopter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<FoodModel> foodModels;
    View view;
    TextView vieworders;
    Fragment fragment,fragment1;
    FragmentManager fm;
    FragmentTransaction ft;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_products,container,false);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        foodlist=view.findViewById(R.id.products);
        vieworders=view.findViewById(R.id.vieworders);
        foodlist.setLayoutManager(linearLayoutManager);
        foodModels=new ArrayList<>();
       vieworders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new LunchScreen();
                fm= getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }
}
