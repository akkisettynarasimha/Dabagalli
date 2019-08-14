package com.arkainfoteck.dabagalli.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.fragments.Home;

public class Main_Home extends Fragment {
    View view;
    Fragment fragment;

     ImageView cart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        cart=(ImageView)view.findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MainActivity.class);
               getContext().startActivity(intent);
            }
        });
        fragment=new Home();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment,new Home()).commit();

        return view;
    }
}
