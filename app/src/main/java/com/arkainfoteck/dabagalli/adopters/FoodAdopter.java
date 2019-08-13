package com.arkainfoteck.dabagalli.adopters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.arkainfoteck.dabagalli.activitys.FoodType;
import com.arkainfoteck.dabagalli.activitys.Login;
import com.arkainfoteck.dabagalli.database.CartDatabse;
import com.arkainfoteck.dabagalli.models.FoodModel;
import com.arkainfoteck.dabagalli.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodAdopter extends RecyclerView.Adapter<FoodAdopter.MtviewHolder> {
    Context context;
    ArrayList<FoodModel>foodModels;
    RadioButton radioSexButton;
    public String get_type_of_item="Small";
    public TextView selected_item;
    int selectedId;
    int pos,getposfordecrement;
    TextView item_type;
    TextView proceed_to_go ;
    String  meal_cast;
    CartDatabse cartDatabse;
    private  RecyclerviewAdopterOnclick recyclerviewAdopterOnclick;
    String lastFourDigits;
    public FoodAdopter(Context context, ArrayList<FoodModel> foodModels,CartDatabse cartDatabse ) {
        this.context = context;
        this.foodModels = foodModels;
        this.cartDatabse=cartDatabse;

    }

    @NonNull
    @Override
    public MtviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.productdetails,viewGroup,false);
        return new  MtviewHolder(view,context,foodModels);
    }

    @Override
    public void onBindViewHolder(@NonNull MtviewHolder mtviewHolder, int i) {

        FoodModel foodModel=foodModels.get(i);
        mtviewHolder.rice_name.setText(foodModel.getMeals_types_name());
        mtviewHolder.total_amount.setText(foodModel.getMeal_total_cost());
        mtviewHolder.itemquantity.setText(foodModel.getMeal_quantity());
    }

    @Override
    public int getItemCount()
    {
        return foodModels.size();
    }//  foodModels.size()

    public class MtviewHolder extends RecyclerView.ViewHolder  {
        TextView rice_name,itemquantity;
        ImageView sub_image_count,add_image_count;
        Context context;
        ArrayList<FoodModel>foodModels;
        TextView total_amount;

        public MtviewHolder(@NonNull View itemView, final Context context, ArrayList<FoodModel>foodModels)
        {
            super(itemView);
            this.context=context;
            this.foodModels=foodModels;

            rice_name=(TextView)itemView.findViewById(R.id.rice_name);
            sub_image_count=(ImageView)itemView.findViewById(R.id.sub_image_count);
            add_image_count=(ImageView)itemView.findViewById(R.id.add_image_count);
            total_amount=(TextView)itemView.findViewById(R.id.total_amount);
            itemquantity=(TextView)itemView.findViewById(R.id.itemquantity);
            add_image_count.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String TotalAmount=cartDatabse.SlectedTotalCount();
                            double total=Double.parseDouble(TotalAmount);
                            if(total<6){
                                pos=getAdapterPosition();
                                showBottomSheetDialog();

                            }else {
                                Toast.makeText(context, "We Can't Select More Then Six Items", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            sub_image_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos=getAdapterPosition();

                    String selectedItem= ""+cartDatabse.decrementTotalItem(""+pos);


                    if(selectedItem.equals("null")||selectedItem.equals("")||selectedItem.equals("0")) {


                        Toast.makeText(context, "Please Select Atleast One Item", Toast.LENGTH_SHORT).show();
                    }else {

                        pos=getAdapterPosition();
                        removeShowBottomSheetDialog();
                    }



                /*    getposfordecrement=getAdapterPosition();

                    String selectedPositionCardSize=cartDatabse.DecrementSelectQuery(""+getposfordecrement);
                    if(selectedPositionCardSize.equals("null")||selectedPositionCardSize.equals("")) {

                        Toast.makeText(context, "one"+selectedPositionCardSize, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "two"+selectedPositionCardSize, Toast.LENGTH_SHORT).show();


                        int selectedPositioncount=Integer.parseInt(selectedPositionCardSize);
                        System.out.println("selectedPositioncount"+selectedPositioncount);


                        String getCostFromDatabase= ""+cartDatabse.getReduceAmountCost(getposfordecrement);
                        System.out.println("getCostFromDatabase"+getCostFromDatabase);


                        if(selectedPositioncount>=1){

                            selectedPositioncount--;


                            //  reduce amount here
                            double updatedCost=Double.parseDouble(getCostFromDatabase)-Double.parseDouble(lastFourDigits);

                            System.out.println("updatedCost"+updatedCost);
                            boolean resultt= cartDatabse.UpdateQueryForBoxItems(foodModel1.getMeals_types_name(),""+selectedPositioncount,""+updatedCost,""+pos);
                            if(resultt==true){
                                Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show();

                                boolean b=cartDatabse.UpdateQuery(""+updatedCost,""+count_quantity,foodModel1.getMeals_types_name());
                                if(b==true){
                                    Toast.makeText(context, "Updated In First", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent("custom-message");
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                                }else {
                                    Toast.makeText(context, "Not Updated In First", Toast.LENGTH_SHORT).show();
                                }


                            }else {
                                Toast.makeText(context, "Data Not Updated", Toast.LENGTH_SHORT).show();
                            }

                        }

*/
                    }
            });
        }
    }
    public void showBottomSheetDialog() {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        final   View view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_dialog, null);

        proceed_to_go=view.findViewById(R.id.proceed_to_go);
        selected_item=view.findViewById(R.id.selected_item);

        item_type=view.findViewById(R.id.item_type);
        final FoodModel foodModel=foodModels.get(pos);
        Toast.makeText(context, " pos 1--> "+pos, Toast.LENGTH_SHORT).show();

        item_type.setText(foodModel.getMeals_types_name());

        final RadioGroup radioSexGroup;

        final BottomSheetDialog dialog = new BottomSheetDialog(context);

        radioSexGroup=(RadioGroup)view.findViewById(R.id.radioGroup);




        RequestQueue rq = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://dabbagalli.com/index.php/api/get_perticular_item_list",
                new Response.Listener<String>() {

                    public void onResponse(final String response) {

                        try {


                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                String  meal_id = jsonObject1.getString("meal_id");
                                String  meal_name = jsonObject1.getString("meal_name");
                                String  meal_type = jsonObject1.getString("meal_type");
                                meal_cast = jsonObject1.getString("meal_cast");
                                String  meal_status = jsonObject1.getString("meal_status");
                                String  meals_types_name=jsonObject1.optString("meals_types_name");
                                System.out.println("maeal_cost_data_sending"+meal_cast);

                                ((RadioButton) radioSexGroup.getChildAt(i)).setText(""+meal_name+"          "+meal_cast);
                            }

                            radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                            {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    // checkedId is the RadioButton selected
                                    selectedId=group.getCheckedRadioButtonId();
                                    radioSexButton=(RadioButton)view.findViewById(selectedId);

                                    // Toast.makeText(context,""+radioSexButton.getText().toString(),Toast.LENGTH_SHORT).show();
                                    //    selected_item.setText(""+get_type_of_item);
                                    get_type_of_item= radioSexButton.getText().toString();

                                    if (get_type_of_item.length() > 5)
                                    {
                                        lastFourDigits = get_type_of_item.substring(get_type_of_item.length() - 5);
                                    }
                                    else
                                    {
                                        lastFourDigits = get_type_of_item;
                                    }
                                    System.out.println("lastFourDigits"+lastFourDigits);
                                    selected_item.setText(""+get_type_of_item);
                                }
                            });
                            //   selected_item.setText(""+get_type_of_item);
                            dialog.setContentView(view);
                            dialog.show();
                            proceed_to_go.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String integerposition=String.valueOf(pos);
                                    FoodModel foodModel1=foodModels.get(pos);

                                    //      Toast.makeText(context, "postion"+integerposition+" ", Toast.LENGTH_SHORT).show();
                                    System.out.println("maeal_cost"+meal_cast);
                                    System.out.println("mounka_mounika"+pos);

                                    String selectedItem= ""+cartDatabse.CheckInsertedOrNot(pos);
                                    System.out.println("mounka_mounika123"+selectedItem);
                                    if(selectedItem.equals("null")||selectedItem.equals("")||selectedItem.equals("0")){


                                        boolean resultt= cartDatabse.insertcarditmes(foodModel1.getMeals_types_name(),"1",lastFourDigits,""+pos);

                                        System.out.println("Inserted"+resultt);
                                        if(resultt==true){
                                            Toast.makeText(context, "Inserted", Toast.LENGTH_SHORT).show();

                                          boolean b=cartDatabse.UpdateQuery(lastFourDigits,"1",foodModel1.getMeals_types_name());
                                          if(b==true){
                                              Toast.makeText(context, "Updated In First", Toast.LENGTH_SHORT).show();
                                              Intent intent = new Intent("custom-message");
                                              LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                                          }else {
                                              Toast.makeText(context, "Not Updated In First", Toast.LENGTH_SHORT).show();
                                          }
                                        }else {
                                            Toast.makeText(context, "Not Inserted", Toast.LENGTH_SHORT).show();
                                        }
                                    }else {

                                        int count_quantity=Integer.parseInt(selectedItem);
                                        System.out.println("Data_Update"+count_quantity);

                                        String getCostFromDatabase= ""+cartDatabse.GetCostFromDataBase(pos);
                                        System.out.println("getCostFromDatabase"+getCostFromDatabase);

                                        if(count_quantity>=1){

                                             count_quantity++;

                                             double updatedCost=Double.parseDouble(lastFourDigits)+Double.parseDouble(getCostFromDatabase);

                                             System.out.println("updatedCost"+updatedCost);
                                             boolean resultt= cartDatabse.UpdateQueryForBoxItems(foodModel1.getMeals_types_name(),""+count_quantity,""+updatedCost,""+pos);
                                            if(resultt==true){
                                                Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show();

                                                boolean b=cartDatabse.UpdateQuery(""+updatedCost,""+count_quantity,foodModel1.getMeals_types_name());
                                                if(b==true){
                                                    Toast.makeText(context, "Updated In First", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent("custom-message");
                                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                                                }else {
                                                    Toast.makeText(context, "Not Updated In First", Toast.LENGTH_SHORT).show();
                                                }

                                            }else {
                                                Toast.makeText(context, "Data Not Updated", Toast.LENGTH_SHORT).show();
                                            }

                                        }else {


                                        System.out.println("mounikadata");
                                         /*   boolean resultt= cartDatabse.insertcarditmes(foodModel1.getMeals_types_name(),"1",lastFourDigits,""+pos);

                                            System.out.println("Inserted"+resultt);
                                            if(resultt==true){
                                                Toast.makeText(context, "Inserted", Toast.LENGTH_SHORT).show();

                                            }else {
                                                Toast.makeText(context, "Not Inserted", Toast.LENGTH_SHORT).show();
                                            }*/
                                        }
                                    }


                               /*     System.out.println("maeal_cost_list"+selectedItem);
                                    Toast.makeText(context, "postion"+selectedItem, Toast.LENGTH_SHORT).show();
*/

                             /*     if(selectedItem.equals("")||selectedItem.equals("null")){

                                    }else {

                                      boolean resultt= cartDatabse.UpdateQueryForBoxItems(foodModel1.getMeals_types_name(),"1",lastFourDigits,pos);

                                      if(resultt==true){
                                          Toast.makeT  ext(context, "Updated", Toast.LENGTH_SHORT).show();
                                      }else {
                                          Toast.makeText(context, "Not Updated", Toast.LENGTH_SHORT).show();
                                      }

                                    }*/
                                    dialog.dismiss();
                                }
                            });

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
                params.put("catid",foodModel.getMeals_types_name());

                return params;
            }
        };
        rq.add(stringRequest);

    }

   public void removeShowBottomSheetDialog(){

       LayoutInflater layoutInflater=LayoutInflater.from(context);
       final   View view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_dialog, null);

       proceed_to_go=view.findViewById(R.id.proceed_to_go);
       selected_item=view.findViewById(R.id.selected_item);

       item_type=view.findViewById(R.id.item_type);
       final FoodModel foodModel=foodModels.get(pos);
       Toast.makeText(context, " pos 1--> "+pos, Toast.LENGTH_SHORT).show();

       item_type.setText(foodModel.getMeals_types_name());

       final RadioGroup radioSexGroup;

       final BottomSheetDialog dialog = new BottomSheetDialog(context);

       radioSexGroup=(RadioGroup)view.findViewById(R.id.radioGroup);

       RequestQueue rq = Volley.newRequestQueue(context);
       StringRequest stringRequest = new StringRequest(Request.Method.POST,
               "https://dabbagalli.com/index.php/api/get_perticular_item_list",
               new Response.Listener<String>() {

                   public void onResponse(final String response) {

                       try {


                           JSONArray jsonArray = new JSONArray(response);
                           for (int i = 0; i < jsonArray.length(); i++) {
                               JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                               String  meal_id = jsonObject1.getString("meal_id");
                               String  meal_name = jsonObject1.getString("meal_name");
                               String  meal_type = jsonObject1.getString("meal_type");
                               meal_cast = jsonObject1.getString("meal_cast");
                               String  meal_status = jsonObject1.getString("meal_status");
                               String  meals_types_name=jsonObject1.optString("meals_types_name");
                               System.out.println("maeal_cost_data_sending"+meal_cast);

                               ((RadioButton) radioSexGroup.getChildAt(i)).setText(""+meal_name+"          "+meal_cast);
                           }

                           radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                           {
                               @Override
                               public void onCheckedChanged(RadioGroup group, int checkedId) {
                                   // checkedId is the RadioButton selected
                                   selectedId=group.getCheckedRadioButtonId();
                                   radioSexButton=(RadioButton)view.findViewById(selectedId);

                                   // Toast.makeText(context,""+radioSexButton.getText().toString(),Toast.LENGTH_SHORT).show();
                                   //    selected_item.setText(""+get_type_of_item);
                                   get_type_of_item= radioSexButton.getText().toString();

                                   if (get_type_of_item.length() > 5)
                                   {
                                       lastFourDigits = get_type_of_item.substring(get_type_of_item.length() - 5);
                                   }
                                   else
                                   {
                                       lastFourDigits = get_type_of_item;
                                   }
                                   System.out.println("lastFourDigits"+lastFourDigits);
                                   selected_item.setText(""+get_type_of_item);
                               }
                           });
                           //   selected_item.setText(""+get_type_of_item);
                           dialog.setContentView(view);
                           dialog.show();
                           proceed_to_go.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {

                                   String integerposition=String.valueOf(pos);
                                   FoodModel foodModel1=foodModels.get(pos);

                                   //      Toast.makeText(context, "postion"+integerposition+" ", Toast.LENGTH_SHORT).show();
                                   System.out.println("maeal_cost"+meal_cast);
                                   System.out.println("mounka_mounika"+pos);

                                   String selectedItem= ""+cartDatabse.CheckInsertedOrNot(pos);
                                   System.out.println("mounka_mounika123"+selectedItem);
                                   if(selectedItem.equals("null")||selectedItem.equals("")){


                                       boolean resultt= cartDatabse.insertcarditmes(foodModel1.getMeals_types_name(),"1",lastFourDigits,""+pos);

                                       System.out.println("Inserted"+resultt);
                                       if(resultt==true){
                                           Toast.makeText(context, "Inserted", Toast.LENGTH_SHORT).show();

                                           boolean b=cartDatabse.UpdateQuery(lastFourDigits,"1",foodModel1.getMeals_types_name());
                                           if(b==true){
                                               Toast.makeText(context, "Updated In First", Toast.LENGTH_SHORT).show();
                                               Intent intent = new Intent("custom-message");
                                               LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                                           }else {
                                               Toast.makeText(context, "Not Updated In First", Toast.LENGTH_SHORT).show();
                                           }
                                       }else {
                                           Toast.makeText(context, "Not Inserted", Toast.LENGTH_SHORT).show();
                                       }
                                   }
                                   else {

                                       int count_quantity=Integer.parseInt(selectedItem);
                                       System.out.println("Data_Update"+count_quantity);

                                       String getCostFromDatabase= ""+cartDatabse.GetCostFromDataBase(pos);
                                       System.out.println("getCostFromDatabase"+getCostFromDatabase);

                                       if(count_quantity>=1){

                                           count_quantity--;

                                           double updatedCost=Double.parseDouble(getCostFromDatabase)-Double.parseDouble(lastFourDigits);

                                           System.out.println("updatedCost"+updatedCost);
                                           boolean resultt= cartDatabse.UpdateQueryForBoxItems(foodModel1.getMeals_types_name(),""+count_quantity,""+updatedCost,""+pos);
                                           if(resultt==true){
                                               Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show();

                                               boolean b=cartDatabse.UpdateQuery(""+updatedCost,""+count_quantity,foodModel1.getMeals_types_name());
                                               if(b==true){
                                                   Toast.makeText(context, "Updated In First", Toast.LENGTH_SHORT).show();
                                                   Intent intent = new Intent("custom-message");
                                                   LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                                               }else {
                                                   Toast.makeText(context, "Not Updated In First", Toast.LENGTH_SHORT).show();
                                               }

                                           }else {
                                               Toast.makeText(context, "Data Not Updated", Toast.LENGTH_SHORT).show();
                                           }

                                       }else {


                                           System.out.println("mounikadata");
                                       }
                                   }
                                   dialog.dismiss();
                               }
                           });

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
               params.put("catid",foodModel.getMeals_types_name());

               return params;
           }
       };
       rq.add(stringRequest);



   }
}
