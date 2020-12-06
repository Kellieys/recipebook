package com.example.recipebook;

//Importing libraries that is needed to use their function to achieve outcome
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import java.util.ArrayList;

//This class purpose as a create,read,update,delete function handler for database
//In this project, it will be focus on create, read, and update rating

public class CRUDHandler extends SQLiteOpenHelper {

    private ContentResolver content_resolver;
    SQLiteDatabase database;

    //constructor for database handler
    public CRUDHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, RecipeContract.DATABASE_NAME, factory, RecipeContract.DATABASE_VERSION);
        database = getWritableDatabase();
        database= getReadableDatabase();
        content_resolver = context.getContentResolver();
    }

    //function that create the table
    //CREATE_RECIPES_TABLE = "CREATE TABLE "
    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(RecipeContract.RecipeTable.CREATE_RECIPES_TABLE);
        database.execSQL(RecipeContract.IngredientsTable.CREATE_INGREDIENTS_TABLE);
        database.execSQL(RecipeContract.RecipeIngredientsTable.CREATE_RECIPE_INGREDIENTS_TABLE);
    }

    //Drop correspond table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
            database.execSQL(RecipeContract.RecipeTable.DELETE_TABLE);
    }

    // Add Recipe Function for add recipe activity
    // args from RecipeFunction.java, new_recipe = inputted value
    public int add_recipe(RecipeFunction new_recipe)
    {
        Uri result;

        // Create contentValues that will be used to store recipe's input
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RECIPE_TITLE, new_recipe.getRecipeTitle());
        values.put(RecipeContract.RECIPE_INSTRUCTIONS, new_recipe.getRecipeInstruction());
        values.put(RecipeContract.RECIPE_RATING, new_recipe.getRecipeRating());

        // Insert to the RECIPETABLE by using content resolvers
        result = content_resolver.insert(RecipeContract.RECIPE_URI, values);

        // Assigned an ID to the new recipe
        StringBuffer tempStringBuffer = new StringBuffer(result.toString());
        tempStringBuffer.replace(0,8,"");

        return Integer.parseInt(tempStringBuffer.toString());
    }

    // Function to handle read all recipe and display into the view
    public ArrayList<String> all_recipe(String sortOrder)
    {
        ArrayList<String> arrayList = new ArrayList<>();

        // View by title selected
        if (sortOrder.equals("Title")) {

            //Sort RECIPE_TITLE by ascending order
            Cursor cursor = content_resolver.query(RecipeContract.RECIPE_URI, null, null, null, RecipeContract.RECIPE_TITLE + " ASC");

            // If the recipe exist, move the cursor to the first recipe title
            if (cursor.moveToFirst()) {

                // While loop until the cursor to reach the recipe
                while (!cursor.isAfterLast()) {

                    float rating = cursor.getFloat(cursor.getColumnIndex(RecipeContract.RECIPE_RATING));

                    // The default rating is 0 for all recipe
                    if (rating == 0) {
                        arrayList.add(cursor.getString(cursor.getColumnIndex(RecipeContract.RECIPE_TITLE)) + "    Rating: " + "Yet to Try Out");
                    }
                    // After rating show the list with rating points
                    else {
                        arrayList.add(cursor.getString(cursor.getColumnIndex(RecipeContract.RECIPE_TITLE)) + "    Rating: " + Float.toString(rating));
                    }

                    // Loop continue to next
                    cursor.moveToNext();
                }
            }
        }

        // View by rating selected
        else if (sortOrder.equals("Rating")) {

            //Sort RECIPE_RATING by descending order
            Cursor cursor = content_resolver.query(RecipeContract.RECIPE_URI, null, null, null, RecipeContract.RECIPE_RATING + " DESC");

            // If the recipe exist, move the cursor to the first recipe title
            if (cursor.moveToFirst()) {

                // While loop until the cursor to reach the recipe
                while (!cursor.isAfterLast()) {
                    float rating = cursor.getFloat(cursor.getColumnIndex(RecipeContract.RECIPE_RATING));

                    arrayList.add(cursor.getString(cursor.getColumnIndex(RecipeContract.RECIPE_TITLE)) + "    Rating: " + Float.toString(rating));

                    // Loop continue to next
                    cursor.moveToNext();
                }
            }
        }
        return arrayList;
    } //end all_recipe

    // Update function for recipe when the user rate the recipe
    public boolean update_rating(RecipeFunction new_recipe)
    {

        ContentValues values = new ContentValues();

        // Store new information in ContentValues
        values.put(RecipeContract.RECIPE_INSTRUCTIONS, new_recipe.getRecipeInstruction());
        values.put(RecipeContract.RECIPE_RATING, new_recipe.getRecipeRating());

        boolean result = false;

        // Query to update recipe
        String selection = "\""+ RecipeContract.RECIPE_TITLE + "\"=\"" + new_recipe.getRecipeTitle() + "\"";

        // Check if any rows is updated with respect to the recipe name
        int rowsUpdated = content_resolver.update(RecipeContract.RECIPE_URI, values, selection, null);

        // If there exist rows that have been updated
        if(rowsUpdated > 0)
        {
            result = true;
        }

        // return update recipe result
        return result;
    }




} //end class



