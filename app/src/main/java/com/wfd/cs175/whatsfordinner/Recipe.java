package com.wfd.cs175.whatsfordinner;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by shwetasugnani on 9/15/16.
 */
public class Recipe {

    String name;
    String directions;
    int count;
    ArrayList<String> ingredients;

    public Recipe(String name, String directions, ArrayList<String> ingredients){
        this.name = name;
        this.directions = directions;
        this.ingredients = ingredients;

        int count = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void addCount(){
        count++;
    }

    public int getCount(){
        return count;
    }

}
