package com.wfd.cs175.whatsfordinner;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class RecipesActivity extends AppCompatActivity {
    ArrayList<Recipe> recipes;
    ArrayAdapter<String> recipes_adapter;
    ListView recipes_listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipes);

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

        /*SharedPreferences appSharedPrefs = getSharedPreferences("SharedPref", MODE_PRIVATE);
        Gson gson = new Gson();
        Map<String, ?> keys = appSharedPrefs.getAll();

        recipes_listView = (ListView) findViewById(R.id.recipe_list_view);
        recipes_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for(Map.Entry<String,?> entry : keys.entrySet()){
           recipes_adapter.add(entry.getKey());
        }
        recipes_listView.setAdapter(recipes_adapter);*/
        Configuration config = getResources().getConfiguration();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /**
         * Check the device orientation and act accordingly
         */
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            /**
             * Landscape mode of the device
             */
            RecipesHorizontal ls_fragment = new RecipesHorizontal();
            fragmentTransaction.replace(R.id.fragment_container, ls_fragment);
        }else{
            /**
             * Portrait mode of the device
             */
            RecipesVertical pm_fragment = new RecipesVertical();
            fragmentTransaction.replace(R.id.fragment_container, pm_fragment);
        }
        fragmentTransaction.commit();
    }


}
