package com.arkainfoteck.dabagalli.models;

public class FoodModel {

    String meal_id;
    String meal_name;
    String meal_type;
    String meal_cost;
    String meal_status;
    String meals_types_name;
    String meal_total_cost;
    String meal_quantity;

    public FoodModel(String meal_id, String meal_name, String meal_type, String meal_cost, String meal_status, String meals_types_name, String meal_total_cost, String meal_quantity) {
        this.meal_id = meal_id;
        this.meal_name = meal_name;
        this.meal_type = meal_type;
        this.meal_cost = meal_cost;
        this.meal_status = meal_status;
        this.meals_types_name = meals_types_name;
        this.meal_total_cost = meal_total_cost;
        this.meal_quantity = meal_quantity;
    }

    public String getMeal_quantity() {
        return meal_quantity;
    }

    public void setMeal_quantity(String meal_quantity) {
        this.meal_quantity = meal_quantity;
    }

    public String getMeal_total_cost() {
        return meal_total_cost;
    }

    public void setMeal_total_cost(String meal_total_cost) {
        this.meal_total_cost = meal_total_cost;
    }

    public String getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(String meal_id) {
        this.meal_id = meal_id;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public String getMeal_cost() {
        return meal_cost;
    }

    public void setMeal_cost(String meal_cost) {
        this.meal_cost = meal_cost;
    }

    public String getMeal_status() {
        return meal_status;
    }

    public void setMeal_status(String meal_status) {
        this.meal_status = meal_status;
    }

    public String getMeals_types_name() {
        return meals_types_name;
    }

    public void setMeals_types_name(String meals_types_name) {
        this.meals_types_name = meals_types_name;
    }
}
