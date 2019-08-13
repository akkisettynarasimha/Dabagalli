package com.arkainfoteck.dabagalli.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {
    EditText  mobile;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mobile=findViewById(R.id.mobile);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobile.getText().toString().isEmpty()){
                    mobile.setError("Enter Valid Mobile Number");
                    mobile.requestFocus();
                }
                else {
                    Fotgotpassword();
                }

            }
        });
    }
    public void Fotgotpassword(){
        RequestQueue rq = Volley.newRequestQueue(ForgotPassword.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://dabbagalli.com/index.php/api/getPassword",
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("Password sent to your registered mobile."))
                            {
                                Toast.makeText(ForgotPassword.this, "Password sent to your registered mobile.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),Passwordchange.class);
                                startActivity(intent);

                            }
                            else if(message.equals("Valid Mobile required"))
                            {
                                Toast.makeText(ForgotPassword.this, "Valid Mobile required", Toast.LENGTH_SHORT).show();
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
                params.put("mobile",mobile.getText().toString());

                return params;
            }
        };
        rq.add(stringRequest);
    }
}
