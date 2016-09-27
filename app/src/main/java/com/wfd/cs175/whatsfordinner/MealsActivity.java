package com.wfd.cs175.whatsfordinner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MealsActivity extends AppCompatActivity {
    ArrayList<Recipe> recipes;
    ListView mealsListView;
    ArrayAdapter<String> meals_adapter;
    Spinner monday_breakfast, monday_lunch, monday_dinner, tuesday_breakfast, tuesday_lunch, tuesday_dinner, wednesday_breakfast, wednesday_lunch, wednesday_dinner;
    Spinner thursday_breakfast, thursday_lunch, thursday_dinner, friday_breakfast, friday_lunch, friday_dinner, saturday_breakfast, saturday_lunch, saturday_dinner, sunday_breakfast, sunday_lunch, sunday_dinner;

    ArrayList<Spinner> spinners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
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

        monday_breakfast = (Spinner) findViewById(R.id.monday_breakfast);
        monday_lunch = (Spinner) findViewById(R.id.monday_lunch);
        monday_dinner = (Spinner) findViewById(R.id.monday_dinner);

        tuesday_breakfast = (Spinner) findViewById(R.id.tuesday_breakfast);
        tuesday_lunch = (Spinner) findViewById(R.id.tuesday_lunch);
        tuesday_dinner = (Spinner) findViewById(R.id.tuesday_dinner);

        wednesday_breakfast = (Spinner) findViewById(R.id.wednesday_breakfast);
        wednesday_lunch = (Spinner) findViewById(R.id.wednesday_lunch);
        wednesday_dinner = (Spinner) findViewById(R.id.wednesday_dinner);

        thursday_breakfast = (Spinner) findViewById(R.id.thursday_breakfast);
        thursday_lunch = (Spinner) findViewById(R.id.thursday_lunch);
        thursday_dinner = (Spinner) findViewById(R.id.thursday_dinner);

        friday_breakfast = (Spinner) findViewById(R.id.friday_breakfast);
        friday_lunch = (Spinner) findViewById(R.id.friday_lunch);
        friday_dinner = (Spinner) findViewById(R.id.friday_dinner);

        saturday_breakfast = (Spinner) findViewById(R.id.saturday_breakfast);
        saturday_lunch = (Spinner) findViewById(R.id.saturday_lunch);
        saturday_dinner = (Spinner) findViewById(R.id.saturday_dinner);

        sunday_breakfast = (Spinner) findViewById(R.id.sunday_breakfast);
        sunday_lunch = (Spinner) findViewById(R.id.sunday_lunch);
        sunday_dinner = (Spinner) findViewById(R.id.sunday_dinner);

        spinners = new ArrayList<Spinner>(Arrays.asList(monday_breakfast, monday_lunch, monday_dinner, tuesday_breakfast, tuesday_lunch, tuesday_dinner, wednesday_breakfast, wednesday_lunch, wednesday_dinner, thursday_breakfast, thursday_lunch, thursday_dinner, friday_breakfast, friday_lunch, friday_dinner, saturday_breakfast, saturday_lunch, saturday_dinner, sunday_breakfast, sunday_lunch, sunday_dinner));

        SharedPreferences appSharedPrefs = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        Map<String, ?> keys = appSharedPrefs.getAll();
        Gson gson = new Gson();
        recipes = new ArrayList<Recipe>();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            String recipe = appSharedPrefs.getString(entry.getKey(), "");
            Recipe rec = gson.fromJson(recipe, Recipe.class);
            recipes.add(rec);

        }
        //mealsListView = (ListView) findViewById(R.id.mealslistView);
        meals_adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1);
        for (Recipe r : recipes) {
            int count = r.getCount();
            if (count > 0) {
                for (int i = 1; i <= count; i++) {

                    meals_adapter.add(r.getName());
                }
            }
        }

        meals_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //mealsListView.setAdapter(meals_adapter);

        for(Spinner s: spinners){
            s.setAdapter(meals_adapter);
        }
    }

}
