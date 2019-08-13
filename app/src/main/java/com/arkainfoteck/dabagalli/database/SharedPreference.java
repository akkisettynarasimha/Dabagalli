package com.arkainfoteck.dabagalli.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.arkainfoteck.dabagalli.activitys.FoodType;
import com.arkainfoteck.dabagalli.models.FoodSharedpreferenceModel;
import com.arkainfoteck.dabagalli.models.LoginModel;

import java.util.ArrayList;

public class SharedPreference {
    public static final String PREFS_NAME_Login = "logindetails";
    public static final String login_name="login_name";
    public static final String login_email="login_email";
    public static final String login_phone="login_phone";
    public static final String Login_id="Login_id";
    public static final String Location="location";

     public static final String REGISTRATION="registraiondetails";
     public static final String Registration_mobile="Registration_mobile";

     public static  final String FooDTYPE="foodtype";
     public static final String Foodcatageries="Foodcatageries";
     public  static  final String Foodday="Foodday";
     public  static final String Foodpermonth="Foodpermonth";
     public static final String Foodid="Foodid";




    public SharedPreference() {
        super();
    }

    public void LOGIN(Context context, String text,String text1,String text2,String text3,String text4) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME_Login, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(login_name, text); //3
        editor.putString(login_email,text1);
        editor.putString(login_phone,text2);
        editor.putString(Login_id,text3);
        editor.putString(Location,text4);
        editor.commit(); //4
     }
    public void Registarion(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(REGISTRATION, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(Registration_mobile, text); //3
        editor.commit(); //4
    }
    public  void Food(Context context,String text,String text1,String text2,String text3)
    {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(FooDTYPE, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(Foodcatageries, text); //3
        editor.putString(Foodday,text1);
        editor.putString(Foodpermonth,text2);
        editor.putString(Foodid,text3);
        editor.commit(); //4
    }
    public String Registrationretrive(Context context)
    {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(REGISTRATION, Context.MODE_PRIVATE);
        text = settings.getString(Registration_mobile, null);
        return text;
    }

     public ArrayList<LoginModel> loginretrive(Context context) {
        SharedPreferences settings;
        String text,text1,text2,text3,text4;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME_Login, Context.MODE_PRIVATE);
        text = settings.getString(login_name, null);
        text1 = settings.getString(login_email, null);
        text2 = settings.getString(login_phone, null);
        text3=settings.getString(Login_id,null);
        text4=settings.getString(Location,null);

        ArrayList <LoginModel> loginModels=new ArrayList<>();
           loginModels.add(new LoginModel(text,text1,text2,text3,text4));
        return loginModels;
    }
    public ArrayList<FoodSharedpreferenceModel> FoodRetrive(Context context) {
        SharedPreferences settings;
        String text,text1,text2,text3;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(FooDTYPE, Context.MODE_PRIVATE);
        text = settings.getString(Foodcatageries, null);
        text1 = settings.getString(Foodday, null);
        text2 = settings.getString(Foodpermonth, null);
        text3=settings.getString(Foodid,null);



        ArrayList <FoodSharedpreferenceModel> foodSharedpreferenceModels=new ArrayList<>();
        foodSharedpreferenceModels.add(new FoodSharedpreferenceModel(text,text1,text2,text3));
        return foodSharedpreferenceModels;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME_Login, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }


  }

