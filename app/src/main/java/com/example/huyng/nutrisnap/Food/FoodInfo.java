package com.example.huyng.nutrisnap.Food;


public class FoodInfo {
    String name;
    String unit;
    int serving;
    int cal;
    double protein;
    double fat;
    String color;

    public FoodInfo(String name, int serving, String unit, int cal, double protein, double fat,String color) {
        this.name = name;
        this.serving = serving;
        this.unit = unit;
        this.cal = cal;
        this.protein = protein;
        this.fat = fat;
        this.color = color;
    }
}
