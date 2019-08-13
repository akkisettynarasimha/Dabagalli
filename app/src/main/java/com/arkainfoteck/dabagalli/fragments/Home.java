package com.arkainfoteck.dabagalli.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arkainfoteck.dabagalli.activitys.FoodType;
import com.arkainfoteck.dabagalli.adopters.MainPageImageSlideAdapter;
import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.adopters.SliderUtils;
import com.arkainfoteck.dabagalli.adopters.ViewPagerAdapter;
import com.arkainfoteck.dabagalli.database.SharedPreference;
import com.arkainfoteck.dabagalli.models.FoodSharedpreferenceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends Fragment {
    View view;
    ViewPager viewPager;
    MainPageImageSlideAdapter mainPageImageSlideAdapter;
    ArrayList<Integer> arrayList;
    private static int currentPage=0;
    /*Button next;*/
    Fragment fragment,fragment1;
    FragmentManager fm;
    FragmentTransaction ft;
    CardView card1,card2;
    String request_url = "https://dabbagalli.com/index.php/api/app_banner_slides";
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;
    private int dotscount;
    private ImageView[] dots;
    private SharedPreference sharedPreference;
    ArrayList<FoodSharedpreferenceModel>foodSharedpreferenceModels;
    String home_foodtype,home_foodid;
    TextView food_catageries;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.imageslide);
        card1=view.findViewById(R.id.card4);
        card2=view.findViewById(R.id.card5);
        food_catageries=view.findViewById(R.id.food_catageries);
        sharedPreference=new SharedPreference();
        foodSharedpreferenceModels=new ArrayList<>();
        foodSharedpreferenceModels=sharedPreference.FoodRetrive(getActivity());
        home_foodtype=foodSharedpreferenceModels.get(0).getFoodtype();
        home_foodid=foodSharedpreferenceModels.get(0).getFoodid();
        food_catageries.setText(home_foodtype);
        sharedPreference=new SharedPreference();
        sliderImg = new ArrayList<>();
        Mainpageimages();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
// dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

// dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getLunchPlanings();

            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               getDenerPlaining();
            }
        });

        return view;
    }

    public void getLunchPlanings() {
        final   View view = getLayoutInflater().inflate(R.layout.weekend_plainings, null);

      Button proceed;
        final CheckBox fiveday,sixday;
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        proceed=view.findViewById(R.id.proceed);
        dialog.setContentView(view);
        dialog.show();
        foodSharedpreferenceModels=new ArrayList<>();
        foodSharedpreferenceModels=sharedPreference.FoodRetrive(getActivity());
        home_foodtype=foodSharedpreferenceModels.get(0).getFoodtype();
        home_foodid=foodSharedpreferenceModels.get(0).getFoodid();


        // this is for Checkbox
        fiveday=view.findViewById(R.id.fivedays);
        sixday=view.findViewById(R.id.sixdays);
        final String foodtype="Lunch";



        fiveday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sixday.setChecked(false);
                    final String fooddays="5 Days Per Month";
                    sharedPreference.Food(getActivity(),home_foodtype,foodtype,fooddays,home_foodid);

                }

            }
        });
        sixday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    fiveday.setChecked(false);
                    final String fooddays="6 Days Per Month";
                    sharedPreference.Food(getActivity(),home_foodtype,foodtype,fooddays,home_foodid);

                }
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // sharedPreference.Food(getActivity(),"Lunch",);
                fragment=new DetailsFragment();
                fm= getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
                dialog.dismiss();
            }
        });
    }
    public void getDenerPlaining() {
        final   View view = getLayoutInflater().inflate(R.layout.weekend_plainings, null);

        Button proceed;
        CheckBox fiveday,sixday;
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        proceed=view.findViewById(R.id.proceed);

        dialog.setContentView(view);
        dialog.show();
        // this is for Checkbox
        fiveday=view.findViewById(R.id.fivedays);
        sixday=view.findViewById(R.id.sixdays);
        final String foodtype="Dinner";
        foodSharedpreferenceModels=new ArrayList<>();
        foodSharedpreferenceModels=sharedPreference.FoodRetrive(getActivity());
        home_foodtype=foodSharedpreferenceModels.get(0).getFoodtype();
        home_foodid=foodSharedpreferenceModels.get(0).getFoodid();


        fiveday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    final String fooddays="5 Days Per Month";
                    sharedPreference.Food(getActivity(),home_foodtype,foodtype,fooddays,home_foodid);

                }

            }
        });
        sixday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    final String fooddays="6 Days Per Month";
                    sharedPreference.Food(getActivity(),home_foodtype,foodtype,fooddays,home_foodid);

                }

            }
        });


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new DetailsFragment();
                fm= getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
                dialog.dismiss();
            }
        });
    }
    public void Mainpageimages(){

        RequestQueue rq = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                request_url,
                new Response.Listener<String>() {

                    public void onResponse(String response) {

// System.out.println("Narasimha"+response);
                        try {
                            JSONArray jsonObject=new JSONArray(response);

                            for(int i=0;i<jsonObject.length();i++){
                                JSONObject jsonObject1=jsonObject.getJSONObject(i);
                                SliderUtils sliderUtils = new SliderUtils();
                                sliderUtils.setSliderImageUrl(jsonObject1.getString("image_name"));
                                sliderImg.add(sliderUtils);
                                viewPagerAdapter = new ViewPagerAdapter(sliderImg, getActivity());

                                viewPager.setAdapter(viewPagerAdapter);

                            }

                            dotscount = viewPagerAdapter.getCount();
                            dots = new ImageView[dotscount];


                            for(int i = 0; i < dotscount; i++){

                                dots[i] = new ImageView(getActivity());
// dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                                params.setMargins(8, 0, 8, 0);

// sliderDotspanel.addView(dots[i], params);

                            }

// dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentPage == sliderImg.size()) {
                                        currentPage = 0;
                                    }
                                    viewPager.setCurrentItem(currentPage++, true);
                                }
                            };
                            Timer swipeTimer = new Timer();
                            swipeTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            }, 2500, 2500);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    };
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
// TODO Auto-generated method stub
// pd.hide();
            }
        })

        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();



                return params;
            }
        };
        rq.add(stringRequest);
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
                    Intent intent = new Intent(getActivity(), FoodType.class);
                    startActivity(intent);

                    return true;
                }

                return false;
            }
        });
    }
}
