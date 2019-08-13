package com.arkainfoteck.dabagalli.activitys;

import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.adopters.FoodAdopter;
import com.arkainfoteck.dabagalli.adopters.SliderUtils;
import com.arkainfoteck.dabagalli.adopters.ViewPagerAdapter;
import com.arkainfoteck.dabagalli.models.FoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SaveData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);
    }


    public void  reda(){
    RequestQueue rq = Volley.newRequestQueue(SaveData.this);
    StringRequest stringRequest = new StringRequest(Request.Method.GET,
            "https://dabbagalli.com/index.php/api/get_food_catgeres",
            new Response.Listener<String>() {

                public void onResponse(String response) {

                    try {
                        JSONArray jsonObject = new JSONArray(response);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ;
            }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError arg0) {
            // TODO Auto-generated method stub
            //   pd.hide();
        }
    }) {
        @Override
        public Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            return params;
        }
    };
            rq.add(stringRequest);
}
}
