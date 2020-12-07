package com.example.recipebook;

import java.util.Set;
import java.util.HashSet;
import android.os.Bundle;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

// This class is responsible for display the ingredient list

public class SpecialIngredients extends AppCompatActivity {

    private CRUDHandler crud_handler;
    private ListView ingredient_list;
    private ArrayList<String> all_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_ingredients);
        ingredient_list = findViewById(R.id.all_ingredients_listView);
        crud_handler = new CRUDHandler(this, null, null, 1);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        all_ingredients = crud_handler.search_ingredients();
        Set<String> set = new HashSet<>(all_ingredients);
        all_ingredients.clear();
        all_ingredients.addAll(set);
        ingredient_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, all_ingredients));
    }

} //end of class