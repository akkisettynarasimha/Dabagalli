package com.arkainfoteck.dabagalli.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arkainfoteck.dabagalli.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Calender  extends Fragment {
    CompactCalendarView compactCalendarView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat sdf;
    TextView tx_date;
   // TextView tx_today;
    LinearLayout ly_detail;
    LinearLayout ly_left, ly_right;
    Calendar myCalendar;
   // ImageView im_back;
    Date c;
    SimpleDateFormat df;
    String formattedDate;
    String[] dates = new String[0];
    RecyclerView recyclerView;
    TextView tx_item;


    String[] day={"10","20","21","25","27"};
    String[] month={"10","10","11","11","12"};
    String[] year ={"2018","2018","2018","2018","2018"};
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_calender,container,false);
        init();
        calendarlistener();
        Setdate();


        tx_date.setText(""+formattedDate);


        ly_right.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                compactCalendarView.showCalendarWithAnimation();
                compactCalendarView.showNextMonth();
            }
        });

        ly_left.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                compactCalendarView.showCalendarWithAnimation();
                compactCalendarView.showPreviousMonth();
            }
        });

       /* tx_today.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Calender.class);
                startActivity(intent);


            }
        });

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

            }
        });*/
        return view;


    }

    //variable initialization

    public void init() {
        compactCalendarView = (CompactCalendarView)view.findViewById(R.id.compactcalendar_view);
        tx_date = (TextView)view.findViewById(R.id.text);
        ly_left = (LinearLayout)view.findViewById(R.id.layout_left);
        ly_right = (LinearLayout)view.findViewById(R.id.layout_right);
       /* im_back = (ImageView)view.findViewById(R.id.image_back);
        tx_today = (TextView)view.findViewById(R.id.text_today);*/
        //ly_detail = (LinearLayout)view.findViewById(R.id.layout_detail);



    }


    //calendar method

    public void calendarlistener() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override

            public void onDayClick(Date dateClicked) {

                if ( DateFormat.format(dateClicked).equals("2018-11-21")){
                    Toast.makeText(getActivity().getApplicationContext(),DateFormat.format(dateClicked)+" This day your brother birth day ",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),DateFormat.format(dateClicked)+" In This day no Events Available",Toast.LENGTH_LONG).show();
                }

            }

            @Override

            public void onMonthScroll(Date firstDayOfNewMonth) {

                compactCalendarView.removeAllEvents();
                Setdate();
                tx_date.setText(simpleDateFormat.format(firstDayOfNewMonth));

            }
        });
    }

    //get current date

    public void Setdate() {


        c = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c);

        compactCalendarView.setUseThreeLetterAbbreviation(true);

        sdf = new SimpleDateFormat("MMMM yyyy");


        myCalendar = Calendar.getInstance();

        for (int j = 0; j < month.length; j++) {

            int mon = Integer.parseInt(month[j]);
            myCalendar.set(Calendar.YEAR, Integer.parseInt(year[j]));
            myCalendar.set(Calendar.MONTH, mon - 1);
            myCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day[j]));

            Event event = new Event(Color.RED, myCalendar.getTimeInMillis(), "test");
            compactCalendarView.addEvent(event);
        }
    }

}
