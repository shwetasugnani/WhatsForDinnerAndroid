package com.wfd.cs175.whatsfordinner;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceriesActivity extends AppCompatActivity {
    HashMap<String, Integer> ingredientlist;
    ArrayList<Recipe> recipes;
    SwipeMenuListView groceriesListView;
    ArrayAdapter<String> groceries_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);
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
        ingredientlist = new HashMap<String, Integer>();
        recipes = new ArrayList<Recipe>();

        SharedPreferences ingredientPrefs = getSharedPreferences("IngredientPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = ingredientPrefs.edit();
        Gson gson = new Gson();


        SharedPreferences appSharedPrefs = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        Map<String, ?> keys = appSharedPrefs.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            String recipe = appSharedPrefs.getString(entry.getKey(), "");
            Recipe rec = gson.fromJson(recipe, Recipe.class);
            recipes.add(rec);

        }

        for(Recipe r: recipes){
            ArrayList<String> ingredientRec = r.getIngredients();
            int count = r.getCount();
            for (String i: ingredientRec){
                if (ingredientlist.containsKey(i)){
                    int value = ingredientlist.get(i);
                    value = value + count;
                    ingredientlist.remove(i);
                    ingredientlist.put(i, value);
                }
                else{
                    ingredientlist.put(i, count);
                }
            }
        }

        groceriesListView = (SwipeMenuListView) findViewById(R.id.grocerylist);
    groceries_adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1);

        for(Map.Entry<String,?> entry : ingredientlist.entrySet()){
            String name = entry.getKey();
            int value = ingredientlist.get(name);
            if (value > 0) {
            groceries_adapter.add(name + "  --  " + value);
            }
            String valueJson = gson.toJson(value);
            editor.putString(name, valueJson);
            editor.commit();
        }

        groceriesListView.setAdapter(groceries_adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item back
                // ground
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(150);
                // set item title
                openItem.setTitle("+");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(150);
                // set a icon
                deleteItem.setTitle("-");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        groceriesListView.setMenuCreator(creator);


        groceriesListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String key = groceries_adapter.getItem(position);
                switch (index) {
                    case 0:
                        // add

                        break;
                    case 1:
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

}
