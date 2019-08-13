package com.arkainfoteck.dabagalli.models;

public class FoodSharedpreferenceModel {
    String foodtype;
    String foodcategerie;
    String foodday;
    String foodid;

    public FoodSharedpreferenceModel(String foodtype, String foodcategerie, String foodday, String foodid) {
        this.foodtype = foodtype;
        this.foodcategerie = foodcategerie;
        this.foodday = foodday;
        this.foodid = foodid;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public String getFoodcategerie() {
        return foodcategerie;
    }

    public void setFoodcategerie(String foodcategerie) {
        this.foodcategerie = foodcategerie;
    }

    public String getFoodday() {
        return foodday;
    }

    public void setFoodday(String foodday) {
        this.foodday = foodday;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }
}
