package com.wfd.cs175.whatsfordinner;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by shwetasugnani on 9/15/16.
 */
public class RecipesVertical extends Fragment {
    ArrayList<Recipe> recipes;
    ArrayAdapter<String> recipes_adapter;
    ListView recipes_listView;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /**
         * Inflate the layout for this fragment
         */
        View rootView = inflater.inflate(R.layout.recipes_vertical, container, false);
        SharedPreferences appSharedPrefs = this.getActivity().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = appSharedPrefs.edit();
        final Gson gson = new Gson();
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


        recipes_listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                String value = (String)arg0.getItemAtPosition(position);
                for(Recipe r: recipes){
                    if (r.getName() == value){
                        r.addCount();
                        String json = gson.toJson(r);
                        editor.putString(r.getName(), json);
                        editor.apply();
                    }
                }

            }
        });
        return rootView;
    }
}
