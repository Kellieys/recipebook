package com.example.recipebook;

import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import android.widget.Toast;
import android.content.Intent;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

// This class is the first executed, when user launch the app
// The activity_main.xml will be displayed
// and will prompt for user input

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
            ArrayList<Integer> ingredientsID = new ArrayList<Integer>();

            // For loop for one ingredients to all ingredients and add to table
            for(String one_ingredient : ingredients)
            {
                ingredientsID.add(crud_handler.add_ingredients(one_ingredient));
            }

            // Add recipe ID and ingredients ID to the RECIPE_INGREDIENTS_TABLE
            for(int i = 0 ; i < ingredientsID.size(); i++){
                crud_handler.add_recipe_ingredients(recipeID,ingredientsID.get(i));
            }

            // Once the recipe is added, hint user
            Toast.makeText(this, "Recipe Added", Toast.LENGTH_SHORT).show();

            // Once inputted, empty the EditText for next input
            recipe_title.setText("");
            recipe_instruction.setText("");
            recipe_ingredients.setText("");
        }
    }

    //handle show all button when it is click
    public void show_all_onclick(View view)
    {
        Intent i =  new Intent(this, PreviewOptions.class);
        startActivity(i);
    }

}