package com.wfd.cs175.whatsfordinner;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by shwetasugnani on 9/15/16.
 */
public class RecipesHorizontal extends Fragment {
    ArrayList<Recipe> recipes;
    ArrayAdapter<String> recipes_adapter;
    ListView recipes_listView;
    TextView recipeTitle;
    TextView ingredientsV;
    TextView ingredientsLabel;
    TextView details;
    ImageView recipePhoto;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        View rootView = inflater.inflate(R.layout.recipes_horizontal, container, false);
        SharedPreferences appSharedPrefs = this.getActivity().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Map<String, ?> keys = appSharedPrefs.getAll();

        recipes = new ArrayList<Recipe>();
        recipes_listView = (ListView) rootView.findViewById(R.id.recipe_list_view);
        recipes_adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1);
        for(Map.Entry<String,?> entry : keys.entrySet()){
            String recipe = appSharedPrefs.getString(entry.getKey(), "");
            Recipe rec = gson.fromJson(recipe, Recipe.class);
            recipes.add(rec);

        }
        for(Recipe r: recipes){
            String rname = r.getName();
            recipes_adapter.add(rname);
        }
        recipes_listView.setAdapter(recipes_adapter);
        recipeTitle = (TextView) rootView.findViewById(R.id.recipeTitle);
        recipePhoto = (ImageView) rootView.findViewById(R.id.recipePhoto);
        ingredientsLabel = (TextView) rootView.findViewById(R.id.inglabel);
        ingredientsV = (TextView) rootView.findViewById(R.id.ingredientslist);
        details = (TextView) rootView.findViewById(R.id.directions);
        recipes_listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                String value = (String)arg0.getItemAtPosition(position);
                recipeTitle.setText(value);

                ingredientsLabel.setText("What you will need:");
                for(Recipe r: recipes){
                    if (r.getName() == value){

                        //recipePhoto.setImageBitmap(StringToBitMap(r.getRecipe_picture()));
                        ArrayList<String> ingredients = r.getIngredients();
                        String ingredientsS = "";
                        for(String i: ingredients){
                            ingredientsS = ingredientsS + "*" + i + "\n";
                        }
                        ingredientsV.setText(ingredientsS);

                        String description = r.getDirections();
                        details.setText(description);
                    }
                }

            }
        });
        recipes_listView.setSelection(0);

        return rootView;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
