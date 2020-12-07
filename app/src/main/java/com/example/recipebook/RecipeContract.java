package com.example.recipebook;

import android.net.Uri;
import android.content.UriMatcher;
import android.provider.BaseColumns;

//This class will be responsible for initializing the constant and
//creating table for 1.recipes, 2.ingredients and 3. recipe_ingredients

public class RecipeContract {

    //constant for database information
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recipe.db";

    //constant for table information corresponding to its name
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredient";

    //Place in my content provider
    public static final String AUTHORITY = "com.example.recipebook.ContentProvider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    //Put in constant defined for table information into uri to identify each correspond location
    public static final Uri RECIPE_URI =  Uri.parse("content://" + AUTHORITY + "/" + TABLE_RECIPES);
    public static final Uri INGREDIENT_URI = Uri.parse("content://" + AUTHORITY +"/" + TABLE_INGREDIENTS);
    public static final Uri RECIPE_INGREDIENTS_URI = Uri.parse("content://"+AUTHORITY + "/" + TABLE_RECIPE_INGREDIENTS);

    //Assign a number as identification
    public static final int RECIPE = 1;
    public static final int RECIPE_TABLE_ID = 2;
    public static final int INGREDIENTS = 3;
    public static final int INGREDIENTS_TABLE_ID = 4;
    public static final int RECIPE_INGREDIENTS = 5;
    public static final int RECIPE_INGREDIENTS_TABLE_ID = 6;

    //field names for recipe
    public static final String RECIPE_ID = "_id";
    public static final String RECIPE_TITLE = "title";
    public static final String RECIPE_INSTRUCTIONS = "instructions";
    public static final String RECIPE_RATING = "rating";

    //field names for ingredients
    public static final String INGREDIENT_ID = "_id";
    public static final String INGREDIENT_LIST = "ingredients";

    //field names for recipe_ingredients
    public static final String RECIPE_INGREDIENTS_RECIPE_ID = "recipe_id";
    public static final String RECIPE_INGREDIENTS_INGREDIENT_ID = "ingredient_id";

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/recipes.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/recipes.data.text";

    public static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, TABLE_RECIPES, RECIPE);
        sURIMatcher.addURI(AUTHORITY, TABLE_RECIPES+ "/#", RECIPE_TABLE_ID);
        sURIMatcher.addURI(AUTHORITY,TABLE_INGREDIENTS,INGREDIENTS);
        sURIMatcher.addURI(AUTHORITY,TABLE_INGREDIENTS + "/#",INGREDIENTS_TABLE_ID);
        sURIMatcher.addURI(AUTHORITY,TABLE_RECIPE_INGREDIENTS,RECIPE_INGREDIENTS);
        sURIMatcher.addURI(AUTHORITY,TABLE_RECIPE_INGREDIENTS + "/#",RECIPE_INGREDIENTS_TABLE_ID);
    }

    // Having an empty constructor to Avoid accidentally instantiating the contract class.
    private RecipeContract() {}


    // function to create table for recipe
    public static abstract class RecipeTable implements BaseColumns {
        // Suggested by coursework sheet
        public static final String CREATE_RECIPES_TABLE =
                "CREATE TABLE " +
                        TABLE_RECIPES +
                        "(" +
                        RECIPE_ID +
                        " INTEGER NOT NULL PRIMARY KEY, " +
                        RECIPE_TITLE +
                        " VARCHAR(128) NOT NULL, " +
                        RECIPE_INSTRUCTIONS +
                        " VARCHAR(128) NOT NULL, " +
                        RECIPE_RATING +
                        " INTEGER)";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_RECIPES;
    }

    // function to create table for ingredients
    public static abstract class IngredientsTable implements BaseColumns {
        // Suggested by coursework sheet
        public static final String CREATE_INGREDIENTS_TABLE =
                "CREATE TABLE " +
                        TABLE_INGREDIENTS +
                        "(" +
                        INGREDIENT_ID +
                        " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        INGREDIENT_LIST +
                        " VARCHAR(128) NOT NULL) ";

        public static final String DELETE_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_INGREDIENTS;
    }

    // function to create a table for many to many relationship
    public static abstract class RecipeIngredientsTable implements BaseColumns {
        // Suggested by coursework sheet
        public static final String CREATE_RECIPE_INGREDIENTS_TABLE =
                "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS +
                        " (  recipe_id INT NOT NULL,  " +
                        "ingredient_id INT NOT NULL, " +
                        "CONSTRAINT fk1 FOREIGN KEY (recipe_id) REFERENCES recipes (_id), " +
                        "CONSTRAINT fk2 FOREIGN KEY (ingredient_id) REFERENCES ingredients (_id), " +
                        "CONSTRAINT _id PRIMARY KEY (recipe_id, ingredient_id) ); ";

        public static final String DELETE_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS;

    }

} //end of class



 