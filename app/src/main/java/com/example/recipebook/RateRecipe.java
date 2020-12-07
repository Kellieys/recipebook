package com.example.recipebook;

import android.view.View;
import android.os.Bundle;
import java.util.ArrayList;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;

// This class is responsible for displaying a recipe and
// letting the user to rate the recipe

public class RateRecipe extends AppCompatActivity {

    private Button rate_button;
    private RatingBar star_bar;
    private String recipe_title;
    private TextView recipe_instruction;
    private TextView recipe_ingredients;
    private RecipeFunction recipe;
    private CRUDHandler crud_handler;
    private TextView recipe_title_textview;
    private TextView recipe_instruction_textview;
    private TextView recipe_ingredients_textview;
    private ArrayList<Integer> recipe_ingredients_ID;
    private ArrayList<String> ingredient_title = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_recipe);
        recipe_title_textview = findViewById(R.id.rate_recipe_title_text);
        recipe_instruction_textview = findViewById(R.id.rate_recipe_instruction_text);
        recipe_ingredients_textview = findViewById(R.id.rate_recipe_ingredients_text);
        star_bar = findViewById(R.id.rate_recipe_rating_starbar);
        rate_button = findViewById(R.id.rateBtn);

        crud_handler = new CRUDHandler(this, null, null, 1);

        // get recipe selected by user
        recipe_title = getIntent().getStringExtra("selected recipe");
        recipe_title_textview.setText(recipe_title);

        star_bar_handler();
    }

    // references
    // https://developer.android.com/reference/android/widget/RatingBar
    // https://developer.android.com/reference/android/widget/RatingBar.OnRatingBarChangeListener
    // Handle
    public void star_bar_handler() {
        star_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                recipe.setRecipeRating(star_bar.getRating());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // Find the correspond recipe by recipe title
        recipe = crud_handler.search_recipe(recipe_title);

        // Access the ingredients ID using recipe ID
        recipe_ingredients_ID = crud_handler.recipe_ingredients_id(recipe.getRecipeID());

        // Retrieve each ingredient that is correspond to the ID
        for(int i = 0; i < recipe_ingredients_ID.size(); i++ )
        {
            // Add each ingredient to an array
            ingredient_title.add(crud_handler.search_ingredients_id(recipe_ingredients_ID.get(i)));
        }

        // Transform to each single ingredient
        String array_to_string = "";
        for (String s : ingredient_title) { array_to_string += s + "\n"; }

        // Retrieve the recipe instruction, ingredients and rating and display into their respective UI
        recipe_instruction.setText(recipe.getRecipeInstruction());
        recipe_ingredients.setText(array_to_string);
        star_bar.setRating(recipe.getRecipeRating());
    }

    // Function for rate button
    public void rate_onclick(View view)
    {
        // update the rating and the info
        boolean rate = crud_handler.update_rating(recipe);

        // Rate success
        if(rate) {
            Toast toast = Toast.makeText(getApplicationContext(),"Recipe Rated", Toast.LENGTH_SHORT);
            toast.show();
        }
        // Rate failed
        else {
            Toast toast = Toast.makeText(getApplicationContext(),"Please Try Again", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


} //end class