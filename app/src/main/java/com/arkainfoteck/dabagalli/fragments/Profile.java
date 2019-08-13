package com.arkainfoteck.dabagalli.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.activitys.CustomerSupport;
import com.arkainfoteck.dabagalli.activitys.Login;
import com.arkainfoteck.dabagalli.activitys.Passwordchange;
import com.arkainfoteck.dabagalli.database.SharedPreference;
import com.arkainfoteck.dabagalli.models.LoginModel;

import java.util.ArrayList;

public class Profile extends Fragment {
    View view;
    LinearLayout passwoed_change,customersupport;
    Button logout;
    ArrayList<LoginModel>loginModels;
    private SharedPreference sharedPreference;
    String user_name,user_email,user_mobile;
    TextView profile_name,profile_mail,profile_mobile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.fragment_profile,container,false);
       /* passwoed_change=view.findViewById(R.id.passwoed_change);*/
        customersupport=view.findViewById(R.id.customersupport);
        logout=view.findViewById(R.id.logout);
        profile_name=view.findViewById(R.id.profile_name);
        profile_mail=view.findViewById(R.id.profile_mail);
        profile_mobile=view.findViewById(R.id.profile_mobile);

        // retrive data from sharedpreference

        sharedPreference=new SharedPreference();
        loginModels=new ArrayList<>();
        loginModels=sharedPreference.loginretrive(getActivity());
        user_name=loginModels.get(0).getName();
        user_email=loginModels.get(0).getEmail();
        user_mobile=loginModels.get(0).getPassword();


        profile_name.setText(user_name);
        profile_mail.setText(user_email);
        profile_mobile.setText(user_mobile);


       /* passwoed_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Passwordchange.class);
                startActivity(intent);
            }
        });*/
        customersupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CustomerSupport.class);
                startActivity(intent);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Login.class);
                sharedPreference.clearSharedPreference(getActivity());
                startActivity(intent);



            }
        });

       return view;
    }
}
