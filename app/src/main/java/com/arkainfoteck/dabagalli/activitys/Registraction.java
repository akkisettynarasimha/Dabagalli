package com.arkainfoteck.dabagalli.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.adopters.CustomizedAdopter;
import com.arkainfoteck.dabagalli.database.SharedPreference;
import com.arkainfoteck.dabagalli.models.SpinnerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Registraction extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button login;
    EditText reg_name,reg_email,reg_phone,reg_password;
    Spinner location;
    ArrayList<SpinnerModel> spinnerModels;
    String locationname;
    String loc_id,location_name;
    int send_location;
    private SharedPreference sharedPreference;
    Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  spinnerModels =new ArrayList<>();
        spinnerModels.add(new SpinnerModel("","-- Select Location --"));
        Toast.makeText(Registraction.this, "haiii", Toast.LENGTH_SHORT).show();
        locatonData();

        sharedPreference=new SharedPreference();
        location=findViewById(R.id.location);
        login=findViewById(R.id.login);
        reg_name=findViewById(R.id.reg_name);
        reg_email=findViewById(R.id.reg_email);
        reg_phone=findViewById(R.id.reg_phone);
        reg_password=findViewById(R.id.reg_password);
        location.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = reg_email.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(reg_name.getText().toString().isEmpty())
                {
                    reg_name.setError("Enter Name");
                    reg_name.requestFocus();
                }
                else if(reg_email.getText().toString().isEmpty())
                {
                    reg_email.setError("Enter email");
                    reg_email.requestFocus();
                }
                else if(reg_phone.getText().toString().isEmpty())
                {
                    reg_phone.setError("Enter phone");
                    reg_phone.requestFocus();
                }
                else if(reg_password.getText().toString().isEmpty())
                {
                    reg_password.setError("Enter password");
                    reg_password.requestFocus();
                }
                if (email.matches(emailPattern)) {
                    reg_email.clearFocus();
                    reg_name.clearFocus();
                    reg_phone.clearFocus();
                    reg_password.clearFocus();
                    Registrationvalidation();
                }

            }
        });


    }
    protected void Registrationvalidation() {

        RequestQueue rq = Volley.newRequestQueue(Registraction.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://dabbagalli.com/index.php/api/registration",
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject1=new JSONObject(response);
                            String message=jsonObject1.getString("message");
                            System.out.print("message"+message);

                            if(message.equals("Email Already Exist")){
                                reg_email.requestFocus();
                                reg_email.setError("This Mail alredy Exist");
                            }else if(message.equals("Phone Number Already Exist")){
                                reg_phone.requestFocus();
                                reg_phone.setError("This Phone Number already Exist");

                            }else if(message.equals("User Successfully Registered")) {
                                Intent intent = new Intent(Registraction.this, Otp.class);
                                sharedPreference.Registarion(context,reg_phone.getText().toString() );
                                startActivity(intent);
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

                params.put("fname",reg_name.getText().toString());
                params.put("email",reg_email.getText().toString());
                params.put("mobile",reg_phone.getText().toString());
                params.put("pass",reg_password.getText().toString());
                params.put("location",locationname);



                return params;
            }
        };
        rq.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        SpinnerModel spinnerModel=spinnerModels.get(position);
        locationname=spinnerModel.getLoc_id();




    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void locatonData(){


        RequestQueue rq = Volley.newRequestQueue(Registraction.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://dabbagalli.com/index.php/api/get_delivery_all_locations",
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        try {
                            JSONArray jsonObject=new JSONArray(response);

                           // Toast.makeText(context, "qwertyui"+response, Toast.LENGTH_SHORT).show();

                            for(int i=0;i<jsonObject.length();i++){
                                JSONObject jsonObject1=jsonObject.getJSONObject(i);
                                loc_id=jsonObject1.getString("loc_id");
                                location_name=jsonObject1.getString("location");
                                int location_id=Integer.parseInt(loc_id);

                                spinnerModels.add(new SpinnerModel(loc_id,location_name));

                            }
                            CustomizedAdopter customizedAdopter=new CustomizedAdopter(Registraction.this,spinnerModels);

                            location.setAdapter(customizedAdopter);


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
}