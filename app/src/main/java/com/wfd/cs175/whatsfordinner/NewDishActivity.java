package com.wfd.cs175.whatsfordinner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.*;
import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class NewDishActivity extends AppCompatActivity {

    EditText recipe_name;
    ImageView recipe_photo;
    EditText ingredient1;
    EditText ingredient2;
    EditText ingredient3;
    EditText ingredient4;
    EditText ingredient5;
    EditText ingredient6;
    EditText ingredient7;
    EditText ingredient8;
    EditText ingredient9;
    EditText ingredient10;
    EditText description;
    String[] ingredients_array;
    ArrayList<String> ingredients;
    Recipe recipe;
    ArrayList<Recipe> recipes;
    Button save;
    int PICK_IMAGE = 1;
    Bitmap bm = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.newdish_activity);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipes = new ArrayList<Recipe>();
        ingredients = new ArrayList<String>();

        recipe_name = (EditText)findViewById(R.id.recipe_name);
        recipe_photo = (ImageView) findViewById(R.id.recipe_picture);
        ingredient1 = (EditText)findViewById(R.id.ingredient1);
        ingredient2 = (EditText)findViewById(R.id.ingredient2);
        ingredient3 = (EditText)findViewById(R.id.ingredient3);
        ingredient4 = (EditText)findViewById(R.id.ingredient4);
        ingredient5 = (EditText)findViewById(R.id.ingredient5);
        ingredient6 = (EditText)findViewById(R.id.ingredient6);
        ingredient7 = (EditText)findViewById(R.id.ingredient7);
        ingredient8 = (EditText)findViewById(R.id.ingredient8);
        ingredient9 = (EditText)findViewById(R.id.ingredient9);
        ingredient10 = (EditText)findViewById(R.id.ingredient10);
        description = (EditText) findViewById(R.id.description);

        ingredient1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean gainFocus) {
                //onFocus
                if (gainFocus) {
                    SharedPreferences appSharedPrefs = getSharedPreferences("SharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = appSharedPrefs.edit();
                    Gson gson = new Gson();
                    String recipeName = recipe_name.getText().toString();
                    Map<String, ?> recipeNames = appSharedPrefs.getAll();
                    for(Map.Entry<String,?> entry : recipeNames.entrySet()){
                        if(entry.getKey().equals(recipeName)){
                            recipe_name.setError("Sorry, that recipe is already there!");
                        }
                    }
                }

            }
        });

        save = (Button) findViewById(R.id.save_recipe_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences appSharedPrefs = getSharedPreferences("SharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = appSharedPrefs.edit();
                Gson gson = new Gson();
                String recipeName = recipe_name.getText().toString();
                /*Map<String, ?> recipeNames = appSharedPrefs.getAll();
                for(Map.Entry<String,?> entry : recipeNames.entrySet()){
                    if(entry.getKey().equals(recipeName)){
                        recipe_name.setError("Sorry, that recipe is already there!");
                    }
                }*/
                ingredients_array = new String[]{ingredient1.getText().toString(), ingredient2.getText().toString(), ingredient3.getText().toString(), ingredient4.getText().toString(),
                        ingredient5.getText().toString(), ingredient6.getText().toString(), ingredient7.getText().toString(), ingredient8.getText().toString(), ingredient9.getText().toString(), ingredient10.getText().toString()};

                for(int i = 0; i < ingredients_array.length; i++){
                    String s = ingredients_array[i];
                    if (s.length() != 0){
                        ingredients.add(s);
                    }
                }
                String recipe_description = description.getText().toString();
                recipe = new Recipe(recipeName, recipe_description, ingredients);

                String json = gson.toJson(recipe);

                editor.putString(recipeName, json);
                editor.commit();

                Toast.makeText(getApplicationContext(),"Recipe saved successfully!",Toast.LENGTH_LONG).show();

            }
        });


    }

    public void newImage(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);

                // Set the Image in ImageView after decoding the String
                recipe_photo.setImageBitmap(bm);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            System.out.println(e);
        }

    }

}
