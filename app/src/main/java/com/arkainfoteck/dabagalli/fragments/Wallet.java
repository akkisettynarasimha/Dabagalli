package com.arkainfoteck.dabagalli.fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Wallet extends Fragment {
    View view;
    TextView startdatetext, enddatetext;
    LinearLayout startdate, enddate;
    public DatePickerDialog fromDatePickerDialog;
    public DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    Button confirmsbs;

    CheckBox alternativeday, everyday;
    TextView applycoupon;
    TextView dailogtext;
    ImageView location;
    TextView locationname;
    SharedPreferences sharedPreferences, sharedPreferences1, sharedPreferences2;
    SharedPreferences.Editor editor, editor1, editor2;
    String daysforrecharge;
    private TextView change;

    private String latitude, longitude;
    double l1, l2;
    Geocoder geocoder;
    List<Address> addresses;
    public static String result;

    public static String subcribe = "";
    Date startDatefinal, endDatefinal;
    String Dayfinding;
    public long days;


    Date milliTime1;
    Calendar newCalendar1;
    int dateintime1;

    Date milliTime123;
    Calendar newCalendar123;
    int dateintime123;
    ImageView backbutton;
    ArrayList<LoginModel>loginModels;
    private SharedPreference sharedPreference;
    String user_id;
    TextView vacation_submit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallet, container, false);
        vacation_submit=view.findViewById(R.id.vacation_submit);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        startdatetext = view.findViewById(R.id.startdatetext);
        enddatetext = view.findViewById(R.id.enddatetext);
        startdate = view.findViewById(R.id.startdate);
        enddate = view.findViewById(R.id.enddate);

        sharedPreference=new SharedPreference();
        loginModels=new ArrayList<>();
        loginModels=sharedPreference.loginretrive(getActivity());
        user_id=loginModels.get(0).getUser_id();
        vacation_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startdatetext.getText().toString().isEmpty())
                {
                    startdatetext.requestFocus();
                    startdatetext.setError("Enter Start Date");
                }
                else if(enddatetext.getText().toString().isEmpty())
                {
                    enddatetext.requestFocus();
                    enddatetext.setError("Enter End Date");
                }
                else
                {
                    Vecation();

                }


            }
        });



        getStartDate();
        getEndDate();
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/* fromDatePickerDialog.show();
fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
*/
                if (dateintime1 >= 21) {

                    fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 2*24*60*60*1000l);

                } else {
                    fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 1*24*60*60*1000l);

                }

                fromDatePickerDialog.getDatePicker().setSelected(false);

                fromDatePickerDialog.show();


                System.out.println("JDDKJ" + System.currentTimeMillis());
// System.out.printf( fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()));
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateintime123 >= 21) {

                    toDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 3*24*60*60*1000l);

                } else {
                    toDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 2*24*60*60*1000l);

                }

                toDatePickerDialog.getDatePicker().setSelected(false);

                toDatePickerDialog.show();


            }
        });
        return view;
    }

    private void getStartDate() {
        newCalendar1 = Calendar.getInstance();
// newCalendar1.add(Calendar.DAY_OF_YEAR, 1);


        milliTime1 = newCalendar1.getTime();
// Toast.makeText(Subscription.this,""+milliTime1,Toast.LENGTH_LONG).show();
        System.out.println("sdsss" + milliTime1);
        String time = "" + milliTime1;
        String times = time.substring(11, 13);
        dateintime1 = Integer.parseInt(times);
        System.out.print("fordatemine" + dateintime1);
// Toast.makeText(Subscription.this,"one "+times,Toast.LENGTH_LONG).show();
        System.out.println("sdsssdffdsdf" + times);
        if (dateintime1 >= 21) {
            newCalendar1.add(Calendar.DAY_OF_YEAR, 2);

        } else {
            newCalendar1.add(Calendar.DAY_OF_YEAR, 1);

        }
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startdatetext.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar1.get(Calendar.YEAR), newCalendar1.get(Calendar.MONTH), newCalendar1.get(Calendar.DAY_OF_MONTH));

    }






    public void getEndDate() {
        newCalendar123 = Calendar.getInstance();
// newCalendar.add(Calendar.DAY_OF_YEAR, 1);

        newCalendar123 = Calendar.getInstance();
        milliTime123 = newCalendar123.getTime();
// Toast.makeText(Subscription.this,""+milliTime123,Toast.LENGTH_LONG).show();
        System.out.println("sdsss"+milliTime123);
        String time=""+milliTime123;
        String times=time.substring(11,13);
        dateintime123=Integer.parseInt(times);
// Toast.makeText(Subscription.this,"one "+times,Toast.LENGTH_LONG).show();
        System.out.println("sdsssdffdsdf"+times);
        if(dateintime123>=21){
            newCalendar123.add(Calendar.DAY_OF_YEAR, 2);

        }else {
            newCalendar123.add(Calendar.DAY_OF_YEAR, 1);

        }
        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                enddatetext.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar123.get(Calendar.YEAR), newCalendar123.get(Calendar.MONTH), newCalendar123.get(Calendar.DAY_OF_MONTH));


    }
    public void Vecation(){
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://dabbagalli.com/index.php/api/vacation",
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("Vacation Set Successfully"))
                            {
                                Toast.makeText(getActivity(), "Vacation Set Successfully", Toast.LENGTH_SHORT).show();


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
                params.put("user_id",user_id);
                params.put("start_date",startdatetext.getText().toString());
                params.put("end_date",enddatetext.getText().toString());

                return params;
            }
        };
        rq.add(stringRequest);
    }
}
