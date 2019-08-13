package com.arkainfoteck.dabagalli.activitys;

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
import com.arkainfoteck.dabagalli.models.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Passwordchange extends AppCompatActivity {
    EditText old_password,new_password,confirm_password;
    Button password_submit;
    ArrayList<LoginModel>loginModels;
    private SharedPreference sharedPreference;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordchange);
        old_password=findViewById(R.id.old_password);
        new_password=findViewById(R.id.new_password);
        confirm_password=findViewById(R.id.confirm_password);
        password_submit=findViewById(R.id.password_submit);
        // user id
        sharedPreference=new SharedPreference();
        loginModels=new ArrayList<>();
        loginModels=sharedPreference.loginretrive(getApplicationContext());
        user_id=loginModels.get(0).getUser_id();



        password_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(old_password.getText().toString().isEmpty()){
                    old_password.setError("Enter old Password");
                    old_password.requestFocus();
                }
                else if(new_password.getText().toString().isEmpty())
                {
                    new_password.setError("Enter new Password");
                    new_password.requestFocus();
                }
                else if(confirm_password.getText().toString().isEmpty())
                {
                    confirm_password.setError("Enter password");
                    confirm_password.requestFocus();
                }
                else if(!confirm_password .getText().toString().equals(new_password.getText().toString())){
                    Toast.makeText(Passwordchange.this,"Password Not matching",Toast.LENGTH_SHORT).show();

                }
                else {
                    PasswordChange();
                }

            }
        });

    }
    public void PasswordChange(){

        RequestQueue rq = Volley.newRequestQueue(Passwordchange.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://dabbagalli.com/index.php/api/change_password",
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Toast.makeText(Passwordchange.this, "qwerty"+jsonObject, Toast.LENGTH_SHORT).show();
                            String message=jsonObject.getString("message");
                            if(message.equals("Success"))
                            {
                                Toast.makeText(Passwordchange.this, "Success", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);


                            }
                            else if(message.equals("Your Old Password is Wrong."))
                            {
                                Toast.makeText(Passwordchange.this, "Your Old Password is Wrong.", Toast.LENGTH_SHORT).show();
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
                params.put("registerid",user_id);
                params.put("oldpassword",old_password.getText().toString());
                params.put("newpassword",new_password.getText().toString());
                return params;
            }
        };
        rq.add(stringRequest);
    }
}
