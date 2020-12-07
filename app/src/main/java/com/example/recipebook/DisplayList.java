package com.example.recipebook;

import android.view.View;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

// This class is to display the recipe list to the user after they choose in the dropdown

public class DisplayList extends AppCompatActivity  {

    private String sort_order;
    private ListView recipe_list;
    private String clicked_recipe;
    private CRUDHandler crud_handler;
    private ArrayList<String> all_recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_list);
        recipe_list= findViewById(R.id.all_recipe_listView);

        // crud_handler
        crud_handler= new CRUDHandler(this, null, null, 1);

        // Retrieve the user input's from option
        sort_order = getIntent().getStringExtra("sorting");
    }

    @Override
    public void onStart() {
        super.onStart();

        // list all the recipe and sort them
        // show all the recipe in listview

        all_recipe = crud_handler.all_recipe(sort_order);
        recipe_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, all_recipe));
        recipe_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clicked_recipe = recipe_list.getItemAtPosition(position).toString();
                clicked_recipe = clicked_recipe.split("Rating")[0].trim();
                display_requested_recipe();
            }
        });
    }

    // start next activity when user selects a recipe
    public void display_requested_recipe()
    {
        Intent i = new Intent(this, RateRecipe.class);
        i.putExtra("clicked recipe", clicked_recipe);
        startActivity(i);
    }

} //end of class
