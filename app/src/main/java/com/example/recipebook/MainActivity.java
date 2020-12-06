package com.example.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //initialize
    private EditText recipe_title;
    private EditText recipe_instruction;
    private EditText recipe_ingredients;
    private ContentProvider content_provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipe_title = findViewById(R.id.recipe_title_editText);
        recipe_instruction = findViewById(R.id.recipe_instruct_editText);
        recipe_ingredients = findViewById(R.id.recipe_ingredient_editText);
        content_provider = new ContentProvider();
    }

    //handle add recipe button when it is click
    public void add_recipe_onclick(View view)
    {
        //app should only allow add  if all the field have been filled, else don't allow to add
        if(recipe_title.getText().toString().matches("") || recipe_instruction.getText().toString().matches("")|| recipe_ingredients.getText().toString().matches(""))
        {
            return;
        }
        else
        {
            //create a CRUDHandler for handling add recipe
            CRUDHandler crud_handler = new CRUDHandler(this, null, null, 1);

            //create a new recipe that is going to be added into the recipe list later
            RecipeFunction recipe = new RecipeFunction(recipe_title.getText().toString(), recipe_instruction.getText().toString(), (float)0);

            //give an ID to the recipe that is going to be added
            int recipeID = crud_handler.add_recipe(recipe);

            // Suggested by coursework sheet
            String ingredients[] = recipe_ingredients.getText().toString().split("\\r?\\n");

            // Add the recipe's ingredients to respective table
            ArrayList<Integer> ingredientsInsertId = new ArrayList<Integer>();
            for(String ingredient : ingredients)
            {
                ingredientsInsertId.add(crud_handler.addIngredients(ingredient));
            }

            // Connect recipe and ingredients relation with a many to many table
            for(int i = 0 ; i < ingredientsInsertId.size(); i++){
                crud_handler.addRecipeIngredients(recipeID,ingredientsInsertId.get(i));
            }

        }

    }

    //handle show all button when it is click
    public void show_all_onclick(View view)
    {
        Intent i =  new Intent(this, sorting.class);
        startActivity(i);
    }

}