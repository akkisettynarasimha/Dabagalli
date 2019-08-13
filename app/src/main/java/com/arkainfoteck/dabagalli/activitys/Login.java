package com.arkainfoteck.dabagalli.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView register,forgetpassword;
    Button login;
    EditText login_name,login_password;
    String user_id,firstname, lastname,email,phone,fax,password,confirm,location,city,address1,address2,adders_type,otp,
            status,copon_status,approved,user_activation,mobactive,sample_meel,sample_subv,date_added;

    private SharedPreference sharedPreference;
    Activity context = this;
    ArrayList<LoginModel>loginModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        sharedPreference=new SharedPreference();
        register=findViewById(R.id.register);
        forgetpassword=findViewById(R.id.forgetpassword);
        login=findViewById(R.id.login_login);
        login_name=findViewById(R.id.login_name);
        login_password=findViewById(R.id.login_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_name.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if(login_name.getText().toString().isEmpty()){
                    login_name.requestFocus();
                    login_name.setError("Enter Password ");
                }
                if (email.matches(emailPattern)||email.length()==10) {
                    login_name.clearFocus();
                    login_password.clearFocus();
                    LoginValidation();

                }else {
                    login_name.requestFocus();
                    login_name.setError("Enter Proper details ");

                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "haii", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),Registraction.class);
                startActivity(intent);
            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(intent);

            }
        });
        loginModels=new ArrayList<>();
        loginModels=sharedPreference.loginretrive(getApplicationContext());
       String name=loginModels.get(0).getEmail();
       if (name != null) {
            Intent intent = new Intent(Login.this, FoodType.class);
            startActivity(intent);
        }
    }
    public void LoginValidation(){

        RequestQueue rq = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://dabbagalli.com/index.php/api/login",
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        try {
                            System.out.print("response"+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("User Not Valid")){
                                Toast.makeText(Login.this,"Please Enter Correct Details" ,Toast.LENGTH_LONG).show();

                                login_name.setText("");
                                login_password.setText("");
                            }else if(message.equals("User Valid")) {
                                Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_LONG).show();



                                String data = jsonObject.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    user_id = jsonObject1.getString("user_id");
                                    firstname = jsonObject1.getString("firstname");
                                    lastname = jsonObject1.getString("lastname");
                                    email = jsonObject1.getString("email");
                                    phone = jsonObject1.getString("phone");
                                    fax = jsonObject1.getString("fax");
                                    password = jsonObject1.getString("password");
                                    confirm = jsonObject1.getString("confirm");
                                    location = jsonObject1.getString("location");
                                    city = jsonObject1.getString("city");
                                    address1 = jsonObject1.getString("address1");
                                    address2 = jsonObject1.getString("address2");
                                    adders_type = jsonObject1.getString("adders_type");
                                    otp = jsonObject1.getString("otp");
                                    status = jsonObject1.getString("status");
                                    copon_status = jsonObject1.getString("copon_status");
                                    approved = jsonObject1.getString("approved");
                                    user_activation = jsonObject1.getString("user_activation");
                                    mobactive = jsonObject1.getString("mobactive");
                                    sample_meel = jsonObject1.getString("sample_meel");
                                    sample_subv = jsonObject1.getString("sample_subv");
                                    date_added = jsonObject1.getString("date_added");
                                    Intent intent = new Intent(Login.this, FoodType.class);
                                    sharedPreference.LOGIN(context, firstname,email,phone,user_id,location);
                                    startActivity(intent);

                                }
                            }




// System.out.println("Narasimharao:"+jsonArray);

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

                params.put("email",login_name.getText().toString());
                params.put("password",login_password.getText().toString());


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
