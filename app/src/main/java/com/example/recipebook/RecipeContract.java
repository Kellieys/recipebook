package com.example.recipebook;

import android.net.Uri;
import android.content.UriMatcher;
import android.provider.BaseColumns;

//This class will be responsible for initializing the constant and
//creating table for 1.recipes, 2.ingredients and 3. recipe_ingredients

public class RecipeContract {

    public static final String AUTHORITY = "com.example.recipebook.MyContentProvider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri RECIPE_URI = Uri.parse("content://"+AUTHORITY+"/recipe/");
    public static final Uri INGREDIENT_URI = Uri.parse("content://"+AUTHORITY+"/ingredient/");
    public static final Uri RECIPE_INGREDIENTS_URI = Uri.parse("content://"+AUTHORITY+"/recipeingredient/");

    //field names
    public static final String RECIPE_ID = "_id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_INSTRUCTIONS = "instructions";
    public static final String RECIPE_RATING = "rating";

    public static final String INGREDIENT_ID = "_id";
    public static final String INGREDIENT_NAME = "ingredientname";

    public static final String RECIPE_INGREDIENTS_RECIPE_ID = "recipe_id";
    public static final String RECIPE_INGREDIENTS_INGREDIENT_ID = "ingredient_id";

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/recipes.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/recipes.data.text";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private RecipeContract() {}

    // function to create table
    public static abstract class RecipeTable implements BaseColumns {
        public static final String TABLE_RECIPES = "recipes";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_RECIPENAME = "recipename";
        public static final String COLUMN_RECIPEINFO = "recipeinfo";
        public static final String COLUMN_RECIPERATING = "reciperating";
        public static final String CREATE_RECIPES_TABLE = "CREATE TABLE " +
                TABLE_RECIPES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_RECIPENAME
                + " TEXT, " + COLUMN_RECIPEINFO + " TEXT, "
                + COLUMN_RECIPERATING + " REAL" + ")";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_RECIPES;
    }
}



 