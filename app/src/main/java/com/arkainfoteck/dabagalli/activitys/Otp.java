package com.arkainfoteck.dabagalli.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.database.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Otp extends AppCompatActivity {
    Button  otp;
    EditText otp_value;
    private SharedPreference sharedPreference;

    Activity context = this;
    private String mobilenumer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otp=findViewById(R.id.otp);
        sharedPreference = new SharedPreference();
        mobilenumer = sharedPreference.Registrationretrive(context);

        otp_value=findViewById(R.id.otp_value);
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(otp_value.getText().toString().isEmpty())
               {
                   otp_value.setError("enter Otp");
                   otp_value.requestFocus();
               }
               else{
                   verifyotp();

               }
            }
        });
    }
    public void verifyotp(){
        RequestQueue rq = Volley.newRequestQueue(Otp.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://dabbagalli.com/index.php/api/verifyOTP",
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("OTP Is Valid"))
                            {
                                Toast.makeText(Otp.this, "OTP Is Valid", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),FoodType.class);
                                startActivity(intent);

                            }
                            else if(message.equals("OTP Is Wrong"))
                            {
                                Toast.makeText(Otp.this, "OTP Is Wrong", Toast.LENGTH_SHORT).show();
                            }




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
                params.put("mobile",mobilenumer);
                params.put("otp",otp_value.getText().toString());

                return params;
            }
        };
        rq.add(stringRequest);
    }
}
