package com.arkainfoteck.dabagalli.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.activitys.LunchScreen;
import com.arkainfoteck.dabagalli.database.SharedPreference;
import com.arkainfoteck.dabagalli.models.LoginModel;

import java.util.ArrayList;

public class ProfileUpdate extends Fragment {
    View view;
    private SharedPreference sharedPreference;
    private String name,email,phone_number,location;
    ProfileUpdate context = this;
    ArrayList<LoginModel>loginModels;
    TextView shoipping_name,shoipping_email,shoipping_phone,shoipping_location;
    Button next;
    Fragment fragment;
    FragmentTransaction ft;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profileupdate,container,false);
        sharedPreference = new SharedPreference();
        next=view.findViewById(R.id.next);
        shoipping_name=view.findViewById(R.id.shoipping_name);
        shoipping_email=view.findViewById(R.id.shoipping_email);
        shoipping_phone=view.findViewById(R.id.shoipping_phone);
        shoipping_location=view.findViewById(R.id.shoipping_location);


         loginModels=new ArrayList<>();
         loginModels=sharedPreference.loginretrive(getActivity());
         name=loginModels.get(0).getName();
         email=loginModels.get(0).getEmail();
         phone_number=loginModels.get(0).getPassword();
         location=loginModels.get(0).getLocation();

         Toast.makeText(getContext(), "vbn"+location, Toast.LENGTH_SHORT).show();

         shoipping_name.setText(name);
         shoipping_email.setText(email);
         shoipping_phone.setText(phone_number);
         shoipping_location.setText(location);
         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 fragment=new LunchScreen();
                 fm=getFragmentManager();
                 ft=fm.beginTransaction();
                 ft.replace(R.id.fragment, fragment);
                 ft.addToBackStack(null);
                 ft.commit();
             }
         });

         return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    fragment=new DetailsFragment();
                    fm= getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();

                    return true;
                }
                return false;
            }
        });
    }
}
