package com.arkainfoteck.dabagalli.activitys;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.fragments.ProfileUpdate;

public class LunchScreen extends Fragment {
      View view;
      Fragment fragment;
      FragmentManager fm;
      FragmentTransaction ft;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_lunch_screen, container, false);
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
                    fragment=new ProfileUpdate();
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
