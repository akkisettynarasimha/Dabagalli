package com.arkainfoteck.dabagalli.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arkainfoteck.dabagalli.RecyclerviewAdopterOnclick;
import com.arkainfoteck.dabagalli.activitys.AddToCart;
import com.arkainfoteck.dabagalli.adopters.FoodAdopter;
import com.arkainfoteck.dabagalli.adopters.FoodItemsAdopter;
import com.arkainfoteck.dabagalli.database.SharedPreference;
import com.arkainfoteck.dabagalli.helper.Sortbyroll;
import com.arkainfoteck.dabagalli.models.FoodModel;
import com.arkainfoteck.dabagalli.models.FoodSharedpreferenceModel;
import com.arkainfoteck.dabagalli.models.FooditemsModel;
import com.arkainfoteck.dabagalli.activitys.LunchScreen;
import com.arkainfoteck.dabagalli.R;
import com.arkainfoteck.dabagalli.database.CartDatabse;
import com.arkainfoteck.dabagalli.testing.MainActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DetailsFragment extends Fragment{
  View view;
    RecyclerView foodlist;
    FoodAdopter foodAdopter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<FoodModel> foodModels;

    RecyclerView fooditems;
    FoodItemsAdopter foodItemsAdopter;
    LinearLayoutManager linearLayoutManager1;
    ArrayList<FooditemsModel>fooditemsmodel;
    TextView vieworders;
    Fragment fragment,fragment1;
    FragmentManager fm;
    FragmentTransaction ft;
    TextView your_meals;

  //  private static final String TAG = MainActivity.class.getSimpleName();
    Button btnBottomSheet;
    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;
    RadioButton radioSexButton;
    public String get_type_of_item="Small";
    public TextView selected_item;
    CartDatabse cartDatabse;
    RecyclerviewAdopterOnclick itemclick;
    private  SharedPreference sharedPreference;
    ArrayList<FoodSharedpreferenceModel>foodSharedpreferenceModels;
    String foodtype;
    String foodcatageroeos;
    String fooddays,foodid;
    String itemname;
    TextView add_to_card;

    Context context;
    LinearLayout with_internet,without_internet;
    ImageView cart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.details_fragment, container, false);

        without_internet=view.findViewById(R.id.without_internet);
        with_internet=view.findViewById(R.id.with_internet);

        // card items
        cart=view.findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // cards items
        fooditems = view.findViewById(R.id.fooditems);
        linearLayoutManager1 = new LinearLayoutManager(getActivity());
        fooditems.setLayoutManager(linearLayoutManager1);

        foodlist = view.findViewById(R.id.foodlist);
        vieworders=view.findViewById(R.id.vieworders);
        your_meals = view.findViewById(R.id.your_meals);
        btnBottomSheet = view.findViewById(R.id.btn_bottom_sheet);
        add_to_card=view.findViewById(R.id.add_to_card);

        // this is food detais Sharedpreference
        sharedPreference=new SharedPreference();
        foodSharedpreferenceModels=new ArrayList<>();
        foodSharedpreferenceModels=sharedPreference.FoodRetrive(getActivity());
        foodtype=foodSharedpreferenceModels.get(0).getFoodtype();
        foodcatageroeos=foodSharedpreferenceModels.get(0).getFoodcategerie();
        fooddays=foodSharedpreferenceModels.get(0).getFoodday();
        foodid=foodSharedpreferenceModels.get(0).getFoodid();
        Toast.makeText(getActivity(), ""+foodtype+foodcatageroeos+fooddays+foodid, Toast.LENGTH_SHORT).show();

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));



        layoutBottomSheet = (LinearLayout) view.findViewById(R.id.bottom_sheet);

        cartDatabse=new CartDatabse(getActivity());


        String text = "<font color=#07324c>Your Per Meal Cost As Per Selection is</font> <font color=#d81b60>RS 85/-</font>";
        your_meals.setText(Html.fromHtml(text));
        //  your_meals.setText(""+R.string.meals+""+R.string.amount);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        foodlist.setLayoutManager(linearLayoutManager);





        boolean b=isNetworkConnected();
        if (b) {

            getMealsJson();

        } else {
            firstAdapter();

              foodModels = new ArrayList<>();
              Cursor res=cartDatabse.getdata("1");

            if (res.getCount()==0)
            {
                Toast.makeText(getActivity(), "cart is empty", Toast.LENGTH_SHORT).show();
            }
            while (res.moveToNext())
            {
                foodModels.add(new FoodModel(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getString(7),res.getString(8)));
            }
            foodAdopter = new FoodAdopter(getActivity(), foodModels,cartDatabse);
            foodlist.setAdapter(foodAdopter);


        }


        vieworders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    showBottomSheetDialog();

                String count=  cartDatabse.getNumberOfBoxes();
                int name=Integer.parseInt(count);
                System.out.println("get_count_name"+name);
                if(name>4){
                    fragment=new ProfileUpdate();
                    fm= getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }else {
                    Toast.makeText(getContext(), "Atleast Five Boxes Should Be Fill", Toast.LENGTH_SHORT).show();
                }

            }
        });
        add_to_card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             String count=  cartDatabse.getNumberOfBoxes();
             int name=Integer.parseInt(count);
             System.out.println("get_count_name"+name);
             if(name>4){
                 Toast.makeText(getContext(), "Data Is Saved In Server", Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(getContext(), "Atleast Five Boxes Should Be Fill", Toast.LENGTH_SHORT).show();
             }

            }
        });

    return view;
    }
    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void getMealsJson(){
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        with_internet.setVisibility(View.GONE);
        without_internet.setVisibility(View.VISIBLE);

            RequestQueue rq = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    "https://dabbagalli.com/index.php/api/get_food_catgeres",
                    new Response.Listener<String>() {

                        public void onResponse(String response) {
                            foodModels = new ArrayList<>();

                            try {
                                JSONArray  jsonObject=new JSONArray(response);

                                System.out.println("response_all_food"+jsonObject);
                                for(int i=0;i<jsonObject.length();i++){
                                    boolean resultt;
                                    JSONObject jsonObject1=jsonObject.getJSONObject(i);

                                    String meal_id=jsonObject1.getString("meal_id");
                                    String meal_name=jsonObject1.getString("meal_name");
                                    String meals_type=jsonObject1.getString("meal_type");
                                    String meal_cast=jsonObject1.getString("meal_cast");
                                    String meal_status=jsonObject1.getString("meal_status");
                                    String meals_types_name=jsonObject1.getString("meals_types_name");
                                    String meal_total_cost="00";
                                    String quantity="0";

                                    String data=cartDatabse.CheckInsertedData(meal_id);

                                    if(data.equals("")){

                                        System.out.println("check "+i);
                                        resultt = cartDatabse.insertdata(meal_id,meal_name,meals_type,meal_cast,meal_status, meals_types_name,meal_total_cost,quantity);

                                        if (resultt == true) {
                                            System.out.println("Data inserted"+resultt);
                                        } else {
                                        }
                                    }else {
                                        resultt=false;
                                        System.out.println("check not inserted "+i);
                                    }
                                }
                                Cursor res=cartDatabse.getdata("1");
                                if ( res.getCount()==0)
                                {
                                    Toast.makeText(getActivity(), "cart is empty", Toast.LENGTH_SHORT).show();
                                }
                                while (res.moveToNext())
                                {
                                    foodModels.add(new FoodModel(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getString(7),res.getString(8)));
                                }
                                foodAdopter = new FoodAdopter(getActivity(), foodModels,cartDatabse);
                                foodlist.setAdapter(foodAdopter);
                                firstAdapter();
                                progressDialog.dismiss();

                                without_internet.setVisibility(View.GONE);
                                with_internet.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        };
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub
                    //   pd.hide();
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
    public void showBottomSheetDialog() {
      final   View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_dialog, null);

        TextView proceed_to_go;

        RadioGroup radioSexGroup;

        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());

        proceed_to_go=view.findViewById(R.id.proceed_to_go);
        selected_item=view.findViewById(R.id.selected_item);
        selected_item.setText("Small");
        radioSexGroup=(RadioGroup)view.findViewById(R.id.radioGroup);
        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                int selectedId=group.getCheckedRadioButtonId();
                radioSexButton=(RadioButton)view.findViewById(selectedId);
          //      Toast.makeText(getContext(),""+radioSexButton.getText().toString(),Toast.LENGTH_SHORT).show();
                get_type_of_item= radioSexButton.getText().toString();
                selected_item.setText(""+get_type_of_item);
            }
        });
        selected_item.setText(""+get_type_of_item);
          /*    int selectedId=radioSexGroup.getCheckedRadioButtonId();
                radioSexButton=(RadioButton)view.findViewById(selectedId);
                Toast.makeText(getContext(),radioSexButton.getText(),Toast.LENGTH_SHORT).show();
        selected_item.setText(radioSexButton.getText().toString());*/
        dialog.setContentView(view);
        dialog.show();

        proceed_to_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new LunchScreen();
                fm= getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
                dialog.dismiss();
            }
        });
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            foodModels=new ArrayList<>();
            Cursor res=cartDatabse.getdata("1");
            if ( res.getCount()==0)
            {
                Toast.makeText(getActivity(), "cart is empty", Toast.LENGTH_SHORT).show();
            }
            while (res.moveToNext())
            {
                foodModels.add(new FoodModel(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getString(7),res.getString(8)));
            }
            foodAdopter = new FoodAdopter(getActivity(), foodModels,cartDatabse);
            foodlist.setAdapter(foodAdopter);



            fooditemsmodel = new ArrayList<>();

            String TotalAmount=""+cartDatabse.SlectedTotalCount();
            if( TotalAmount.equals("null")||TotalAmount.equals("")){
                System.out.println("reeeeee"+TotalAmount);
                Cursor res1=cartDatabse.getDataForBoxs("1");
                if ( res1.getCount()==0)
                {
                    Toast.makeText(getActivity(), "cart is empty", Toast.LENGTH_SHORT).show();
                }
                for(int i=0;i<res1.getCount();i++){

                    fooditemsmodel.add(new FooditemsModel(0));
                }
                foodItemsAdopter = new FoodItemsAdopter(getActivity(), fooditemsmodel,itemname);
                fooditems.setAdapter(foodItemsAdopter);

            }else {
                System.out.println("reeeeee12"+TotalAmount);

                double total=0+Double.parseDouble(TotalAmount);

                int k=1;
                int count=(int)total;

                Cursor res1=cartDatabse.getDataForBoxs("1");
                if ( res1.getCount()==0)
                {
                    Toast.makeText(getActivity(), "cart is empty", Toast.LENGTH_SHORT).show();
                }
                System.out.println("get_count_data_mounika"+res1.getCount());
                for(int i=0;i<res1.getCount();i++){
                    if(count>=k) {
                        k++;
                        fooditemsmodel.add(new FooditemsModel(k));
                    }else {
                        fooditemsmodel.add(new FooditemsModel(0));
                    }
                }
                for(int i=0;i<fooditemsmodel.size();i++){
                    Collections.sort(fooditemsmodel,new Sortbyroll());
                }
                foodItemsAdopter = new FoodItemsAdopter(getActivity(), fooditemsmodel,itemname);
                fooditems.setAdapter(foodItemsAdopter);
            }
        }
    };
    public void firstAdapter()
    {
        linearLayoutManager1 = new LinearLayoutManager(getActivity());
        fooditems.setLayoutManager(linearLayoutManager1);
        fooditemsmodel = new ArrayList<>();

        String TotalAmount=""+cartDatabse.SlectedTotalCount();
        if( TotalAmount.equals("null")||TotalAmount.equals("")){
            System.out.println("reeeeee"+TotalAmount);
            Cursor res1=cartDatabse.getDataForBoxs("1");
            if ( res1.getCount()==0)
            {
                Toast.makeText(getActivity(), "cart is empty", Toast.LENGTH_SHORT).show();
            }
            for(int i=0;i<res1.getCount();i++){

                    fooditemsmodel.add(new FooditemsModel(0));
            }
            foodItemsAdopter = new FoodItemsAdopter(getActivity(), fooditemsmodel,itemname);
            fooditems.setAdapter(foodItemsAdopter);

        }else {
            System.out.println("reeeeee12"+TotalAmount);

            double total=0+Double.parseDouble(TotalAmount);

            int k=1;
            int count=(int)total;

            Cursor res1=cartDatabse.getDataForBoxs("1");
            if ( res1.getCount()==0)
            {
                Toast.makeText(getActivity(), "cart is empty", Toast.LENGTH_SHORT).show();
            }
            System.out.println("get_count_data_mounika"+res1.getCount());
         for(int i=0;i<res1.getCount();i++){
            if(count>=k) {
                k++;
                fooditemsmodel.add(new FooditemsModel(k));
            }else {
                fooditemsmodel.add(new FooditemsModel(0));
            }
        }
        for(int i=0;i<fooditemsmodel.size();i++){
            Collections.sort(fooditemsmodel,new Sortbyroll());
        }
        foodItemsAdopter = new FoodItemsAdopter(getActivity(), fooditemsmodel,itemname);
        fooditems.setAdapter(foodItemsAdopter);
        }
    }


     @Override
    public void onResume() {
         super.onResume();

         getView().setFocusableInTouchMode(true);
         getView().requestFocus();
         getView().setOnKeyListener(new View.OnKeyListener() {
             @Override
             public boolean onKey(View v, int keyCode, KeyEvent event) {
                 if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                     fragment = new Home();
                     fm = getFragmentManager();
                     ft = fm.beginTransaction();
                     ft.replace(R.id.fragment, fragment);
                     ft.addToBackStack(null);
                     ft.commit();

                     return true;
                 }

                 return false;
             }
         });
     }
}
