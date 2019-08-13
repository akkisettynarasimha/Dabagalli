package com.arkainfoteck.dabagalli.helper;

import com.arkainfoteck.dabagalli.models.FooditemsModel;

import java.util.Comparator;

public class Sortbyroll implements Comparator<FooditemsModel>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(FooditemsModel a, FooditemsModel b)

    {
      /*  if(a.isData()){
            return 1;
        }else {
            return 0;
        }*/
        // return a.getCount()-b.getCount();
        return (a.getData()-b.getData());
    }
}

