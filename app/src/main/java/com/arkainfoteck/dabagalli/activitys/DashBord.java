package com.arkainfoteck.dabagalli.activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.arkainfoteck.dabagalli.fragments.Profile;
import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.fragments.Wallet;
import com.arkainfoteck.dabagalli.fragments.Calender;

public class DashBord extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {



            Fragment fragment=null;
            switch (item.getItemId()) {
                case R.id.action_search:

                    fragment=new Main_Home();
                    break;

                case R.id.action_cart:

                    fragment=new Wallet();
                    break;

                case R.id.action_hot_deals:

                    fragment=new Calender();
                    break;
                case R.id.action_small_deals:

                    fragment=new Profile();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
    };
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_bord);
      navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new Main_Home()).commit();
    }
    @Override
    public void onBackPressed() {
        if (navigation.getSelectedItemId() == R.id.action_search) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        } else {
            navigation.setSelectedItemId(R.id.action_search);
        }
    }
}
