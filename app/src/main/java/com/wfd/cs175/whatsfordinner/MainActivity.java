package com.wfd.cs175.whatsfordinner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton mainPicture;
    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        /*SharedPreferences appSharedPrefs = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = appSharedPrefs.edit();
        edit.clear();
        edit.commit();*/

        mainPicture = (ImageButton) findViewById(R.id.banner_image);
        mainPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogBuilder.setMessage("Author: Shweta Sugnani\nVersion: 1.1\nFor help, please contact Shweta Sugnani\nCopyright © WFD, Inc.\n");

                alertDialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG);
                    }
                });


                AlertDialog bannerDialog = alertDialogBuilder.create();
                bannerDialog.show();

            }
        });

        recipes = new ArrayList<Recipe>();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newDishClick(View view) {
        Intent newdish_intent = new Intent(this, NewDishActivity.class);
        startActivity(newdish_intent);


    }

    public void recipesClick(View view) {
        Intent recipes_intent = new Intent(this, RecipesActivity.class);
        startActivity(recipes_intent);

    }

    public void groceriesClick(View view) {
        Intent groceries_intent = new Intent(this, GroceriesActivity.class);
        startActivity(groceries_intent);

    }
    public void mealsClick(View view) {

        Intent meals_intent = new Intent(this, MealsActivity.class);
        startActivity(meals_intent);

    }
}