package com.arkainfoteck.dabagalli.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.adopters.FoodTypeAdoprter;
import com.arkainfoteck.dabagalli.database.SharedPreference;
import com.arkainfoteck.dabagalli.models.FoodTypeModel;
import com.arkainfoteck.dabagalli.models.LoginModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodType extends AppCompatActivity {

    CardView card1,card2,card3;
    RecyclerView food_type;
    LinearLayoutManager linearLayoutManager;
    ArrayList<FoodTypeModel> foodTypeModel;
    FoodTypeAdoprter foodTypeAdoprter;
    private SharedPreference sharedPreference;
    Activity context = this;
    ArrayList<LoginModel>loginModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type);
         food_type=(RecyclerView)findViewById(R.id.food_type);
         linearLayoutManager=new LinearLayoutManager(this);
         food_type.setLayoutManager(linearLayoutManager);

         getFoodType();
         sharedPreference=new SharedPreference();
        loginModels=new ArrayList<>();
        loginModels=sharedPreference.loginretrive(getApplicationContext());
        String name=loginModels.get(0).getEmail();


        if(sharedPreference!=null) {
            if (name != null || name == "") {

            } else {
                Intent it = new Intent(FoodType.this, Login.class);
                startActivity(it);
            }
        }

    }
    public void getFoodType(){
           RequestQueue rq = Volley.newRequestQueue(FoodType.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    "https://dabbagalli.com/index.php/api/get_Food_Type",
                    new Response.Listener<String>() {

                        public void onResponse(String response) {

                            System.out.println("food_type"+response);
                            // Toast.makeText(getActivity(), "data"+response, Toast.LENGTH_SHORT).show();

                            foodTypeModel=new ArrayList<>();

                            try {

                                JSONArray jsonArray=new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++) {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                String food_id=jsonObject.getString("food_id");
                                String food_name=jsonObject.getString("food_name");
                                foodTypeModel.add(new FoodTypeModel(food_id,food_name));

                                }
                                foodTypeAdoprter=new FoodTypeAdoprter(FoodType.this,foodTypeModel);
                                food_type.setAdapter(foodTypeAdoprter);


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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}

