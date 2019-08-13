package com.arkainfoteck.dabagalli.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class CartDatabse extends SQLiteOpenHelper {
    String meal_list_query,product_to_card,table_texting;
    private static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "dabbagalli.db";

    public static final String TABLE_NAME = "meals_list";
    public static final String MEAL_PRIMARY = "meal_primary";
    public static final String MEAL_ID = "meal_id";
    public static final String MEAL_NAME = "meal_name";
    public static final String MEAL_TYPE = "meals_type";
    public static final String MEAL_CAST = "meal_cast";
    public static final String MEAL_STATUS = "meal_status";
    public static final String MEALS_TYPES_NAME = "meals_types_name";
    public static final String MEALS_TOTAL_COST = "meals_total_cost";
    public static final String MEALS_QUANTITY = "meals_quantity";



    public static final String TABLE_INSERTING="product_to_card";
    public static final String CARD_ITEM_COUNT = "card_item_count";
    public static final String CARD_ITEM_POSITION = "card_item_position";
    public static final String CARD_ITEM_TYPE = "card_item_type";
    public static final String CARD_SIZE = "card_size";
    public static final String CARD_QUANTITY = "card_quantity";

    public  static final String TABLE_TESTING="testing";
    public  static final String TABLE_TESTING_INCREMENT="testing_increment";
    public  static final String TABLE_TESTING_ID="testing_id";
    public  static final String TABLE_TESTING_NAME="testing_name";
    public  static final String TABLE_TESTING_PRODUCT="testing_product";

    String c;
    String quantitysize;
    String checkInsertedData;;
    String updatecost;
    String TotalAmount;
    String DecreseCount;
    public CartDatabse(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

         meal_list_query = "CREATE TABLE "
                + TABLE_NAME + "("+MEAL_PRIMARY + " INTEGER PRIMARY KEY,"+ MEAL_ID
                + " TEXT," + MEAL_NAME + " TEXT," + MEAL_TYPE+ " TEXT,"  + MEAL_CAST+ " TEXT,"  + MEAL_STATUS+ " TEXT,"+ MEALS_TYPES_NAME+ " TEXT,"+ MEALS_TOTAL_COST+ " TEXT,"  + MEALS_QUANTITY
                + " TEXT" + ")";
        product_to_card = "CREATE TABLE "
                + TABLE_INSERTING + "(" + CARD_ITEM_COUNT + " INTEGER PRIMARY KEY," + CARD_ITEM_TYPE
                + " TEXT," + CARD_SIZE + " TEXT," + CARD_ITEM_POSITION + " TEXT,"+ CARD_QUANTITY
                + " TEXT" + ")";

        table_texting = "CREATE TABLE "
                + TABLE_TESTING + "(" + TABLE_TESTING_INCREMENT + " INTEGER PRIMARY KEY," + TABLE_TESTING_ID
                + " TEXT," + TABLE_TESTING_NAME + " TEXT,"+ TABLE_TESTING_PRODUCT
                + " TEXT" + ")";


        db.execSQL(meal_list_query);
        db.execSQL(product_to_card);
        db.execSQL(table_texting);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSERTING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTING);

    }

    public boolean insertingdata_texting(String meal_id,String meal_name,String meals_product) {

        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_TESTING_ID, meal_id);
        contentValues.put(TABLE_TESTING_NAME, meal_name);
        contentValues.put(TABLE_TESTING_PRODUCT, meals_product);

    //    long result = sqLiteDatabas.insert(TABLE_TESTING, null, contentValues);
        long rows = sqLiteDatabas.insertWithOnConflict(TABLE_TESTING, null, contentValues,SQLiteDatabase.CONFLICT_REPLACE);

        if (rows == -1)
            return false;
        else
            return true;
    }

    public boolean insertdata(String meal_id,String meal_name,String meals_type,String meal_cast,String meal_status,String meals_types_name,String meal_total_cost,String quantity) {

        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEAL_ID, meal_id);
        contentValues.put(MEAL_NAME, meal_name);
        contentValues.put(MEAL_TYPE, meals_type);
        contentValues.put(MEAL_CAST, meal_cast);
        contentValues.put(MEAL_STATUS, meal_status);
        contentValues.put(MEALS_TYPES_NAME, meals_types_name);
        contentValues.put(MEALS_TOTAL_COST,meal_total_cost);
        contentValues.put(MEALS_QUANTITY,quantity);

        //   long result = sqLiteDatabas.insert(TABLE_NAME, null, contentValues);
        long rows = sqLiteDatabas.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

        System.out.println("rows_data_false" + rows);

        if (rows == -1){
        return false;
        }else{
            return true;
        }
    }
    public Cursor getdata(String meal_type) {
        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();
        String countQuery = "SELECT * FROM "+TABLE_NAME+" WHERE meals_type='1'"+" GROUP BY "+MEALS_TYPES_NAME +" ORDER by meal_primary ASC ";//+"ORDER BY "+MEAL_ID;//  meal_type=1 ";

        System.out.println("Get_data_details"+countQuery);
        Cursor res = sqLiteDatabas.rawQuery( countQuery,null);
        System.out.println("Get_data_details_1"+res);

        return res;
    }

    public Cursor getDataForBoxs(String meal_type){
        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();

      //  String countQuery = "  SELECT meals_quantity FROM "+TABLE_NAME+" WHERE meals_type='1'"+" GROUP BY "+MEALS_TYPES_NAME +" ORDER by meal_primary ASC ";//+"ORDER BY "+MEAL_ID;//  meal_type=1 ";
        String countQuery="SELECT meals_quantity FROM meals_list WHERE meals_type='1' GROUP BY meals_types_name ORDER by meal_primary ASC";
        Cursor res = sqLiteDatabas.rawQuery( countQuery,null);

      return res;
    }

    public String CheckInsertedData(String data){
        checkInsertedData="";
        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();

        String countQuery="SELECT meal_id FROM 'meals_list' WHERE meal_id="+data;
        System.out.println("countQuery"+countQuery);
        Cursor cursor=sqLiteDatabas.rawQuery(countQuery,null);
        while (cursor.moveToNext())
        {
            checkInsertedData=cursor.getString(0);
        }
      return checkInsertedData;
    }
    public  String getAmount(String name){
        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();
           String countQuery="SELECT sum(card_quantity) ,sum(card_size) FROM 'product_to_card' WHERE card_item_type= '"+name+"'";

        System.out.println("countQuery"+countQuery);
           Cursor cursor=sqLiteDatabas.rawQuery(countQuery,null);
        while (cursor.moveToNext())
        {
           c=  cursor.getString(0)+", "+cursor.getString(1);
        }
        return  c;
    }
    public  String getNumberOfBoxes(){
        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();
        String countQuery=" SELECT COUNT(*) FROM (SELECT meals_quantity FROM meals_list WHERE meals_type='1' GROUP BY meals_types_name ORDER by meal_primary ASC)WHERE meals_quantity>=1";

        System.out.println("countQuery"+countQuery);
        Cursor cursor=sqLiteDatabas.rawQuery(countQuery,null);
        while (cursor.moveToNext())
        {
            c=  cursor.getString(0);
        }
        return  c;
    }
    public int deleteConformOrderData(){
        SQLiteDatabase db=getWritableDatabase();
        int count= db.delete(TABLE_NAME,null,null);
        db.close();
        return count;
    }





    public boolean UpdateQuery(String id,String quantity,String rid){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MEALS_TOTAL_COST, id);
        cv.put(MEALS_QUANTITY, quantity);

        db.update(TABLE_NAME, cv, "meals_types_name = ?  ",new String[] { rid});
        return true;
    }
    public boolean insertcarditmes(String cash_item_name,String cash_item_size,String cash_item_price,String card_item_position ){
        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CARD_ITEM_TYPE, cash_item_name);
        contentValues.put(CARD_SIZE, cash_item_size);
        contentValues.put(CARD_QUANTITY, cash_item_price);
        contentValues.put(CARD_ITEM_POSITION, card_item_position);

        long result = sqLiteDatabas.insert(TABLE_INSERTING, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public  Boolean UpdateQueryForBoxItems(String cash_item_name,String cash_item_size,String cash_item_price ,String card_item_position ){

        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CARD_ITEM_TYPE, cash_item_name);
        cv.put(CARD_SIZE,cash_item_size);
        cv.put(CARD_QUANTITY, cash_item_price);
        cv.put(CARD_ITEM_POSITION,card_item_position);

        db.update(TABLE_INSERTING, cv, "card_item_position = ? ",new String[] { ""+card_item_position});

        return  true;
    }

    // write a query to check data is inserted or not in database
    public  String CheckInsertedOrNot(int position){
        System.out.println("narasimha"+position);
        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();
        String countQuery="SELECT * FROM 'product_to_card' WHERE card_item_position="+position;

        System.out.println("countQuery"+countQuery);
        Cursor cursor=sqLiteDatabas.rawQuery(countQuery,null);
        c="";
        while (cursor.moveToNext())
        {
            c= cursor.getString(2);
        }
        return  c;
    }
   public String  GetCostFromDataBase(int position){
       System.out.println("narasimha"+position);
       SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();
       String countQuery="SELECT * FROM 'product_to_card' WHERE card_item_position="+position;

       System.out.println("countQuery"+countQuery);
       Cursor cursor=sqLiteDatabas.rawQuery(countQuery,null);
       updatecost="";
       while (cursor.moveToNext())
       {
           updatecost= cursor.getString(4);
     //      System.out.println("narasimha123"+cursor.getString(3));
       }
       return  updatecost;
    }
    public int deleteProduct(){

        SQLiteDatabase db=getWritableDatabase();
        int count= db.delete(TABLE_NAME,null,null);
        db.close();
        return count;
    }


    public String DecrementSelectQuery(String getDrecentData){

        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();
        String countQuery="SELECT * FROM 'product_to_card' WHERE card_item_position="+getDrecentData;

        System.out.println("countQuery"+countQuery);
        Cursor cursor=sqLiteDatabas.rawQuery(countQuery,null);
        updatecost="";
        while (cursor.moveToNext())
        {
            updatecost= cursor.getString(2);
        }
        return  updatecost;
    }

    public String  getReduceAmountCost(int position){
        System.out.println("narasimha"+position);
        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();
        String countQuery="SELECT * FROM 'product_to_card' WHERE card_item_position="+position;

        System.out.println("countQuery"+countQuery);
        Cursor cursor=sqLiteDatabas.rawQuery(countQuery,null);
        updatecost="";
        while (cursor.moveToNext())
        {
            updatecost= cursor.getString(4);
            //      System.out.println("narasimha123"+cursor.getString(3));
        }
        return  updatecost;
    }

    public  Boolean UpdateQueryForReduce(String cash_item_name,String cash_item_size,String cash_item_price ,String card_item_position ){

        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CARD_ITEM_TYPE, cash_item_name);
        cv.put(CARD_SIZE,cash_item_size);
        cv.put(CARD_QUANTITY, cash_item_price);
        cv.put(CARD_ITEM_POSITION,card_item_position);

        db.update(TABLE_INSERTING, cv, "card_item_position = ? ",new String[] { ""+card_item_position});

        return  true;
    }


    public  String SlectedTotalCount(){
        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();

       String countQuery="SELECT sum(meals_quantity) FROM (SELECT meals_quantity FROM 'meals_list' Group By meals_types_name ORDER BY meal_primary)";
        Cursor cursor=sqLiteDatabas.rawQuery(countQuery,null);
        TotalAmount="";
        while (cursor.moveToNext())
        {
            TotalAmount= cursor.getString(0);
            //      System.out.println("narasimha123"+cursor.getString(3));
        }
        return  TotalAmount;
    }
    public  String decrementTotalItem(String name){

        SQLiteDatabase sqLiteDatabas = this.getWritableDatabase();

        String countQuery="SELECT card_size FROM 'product_to_card' WHERE card_item_position ="+name;
        Cursor cursor=sqLiteDatabas.rawQuery(countQuery,null);
        DecreseCount="";
        while (cursor.moveToNext())
        {
            DecreseCount= cursor.getString(0);
        }
        return  DecreseCount;
    }
}