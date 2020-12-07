package com.example.recipebook;

//Importing libraries that is needed to use their functions to achieve certain functionalities
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
        ContentValues inputs = new ContentValues();
        inputs.put(RecipeContract.RECIPE_TITLE, new_recipe.getRecipeTitle());
        inputs.put(RecipeContract.RECIPE_INSTRUCTIONS, new_recipe.getRecipeInstruction());
        inputs.put(RecipeContract.RECIPE_RATING, new_recipe.getRecipeRating());

        // Insert to the RECIPETABLE by using content resolvers
        result = content_resolver.insert(RecipeContract.RECIPE_URI, inputs);

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
        // Create contentValues that will be used to store input
        ContentValues inputs = new ContentValues();

        inputs.put(RecipeContract.RECIPE_RATING, new_recipe.getRecipeRating());

        boolean result = false;

        String selection = "\""+ RecipeContract.RECIPE_TITLE + "\"=\"" + new_recipe.getRecipeTitle() + "\"";

        int rowsUpdated = content_resolver.update(RecipeContract.RECIPE_URI, inputs, selection, null);

        if(rowsUpdated > 0)
        {
            result = true;
        }

        // return update recipe result
        return result;
    }

    // Look for all recipe
    public RecipeFunction search_recipe(String recipe_title)
    {
        // Use recipe title to find the correspond recipe
        String selection = "\""+ RecipeContract.RECIPE_TITLE + "\"=\"" + recipe_title + "\"";
        Cursor cursor = content_resolver.query(RecipeContract.RECIPE_URI,null, selection, null, null);
        RecipeFunction recipe_function = new RecipeFunction();

        // If recipe exist, move cursor and point to it
        // else return null
        if (cursor.moveToFirst()) {
            // Store details in recipe object
            recipe_function.setRecipeId(cursor.getInt(cursor.getColumnIndex(RecipeContract.RECIPE_ID)));
            recipe_function.setRecipeTitle(cursor.getString(cursor.getColumnIndex(RecipeContract.RECIPE_TITLE)));
            recipe_function.setRecipeInstruction(cursor.getString(cursor.getColumnIndex(RecipeContract.RECIPE_INSTRUCTIONS)));
            recipe_function.setRecipeRating(cursor.getFloat(cursor.getColumnIndex(RecipeContract.RECIPE_RATING)));
            cursor.close();
        }
        else
            {
            recipe_function = null;
        }
        return recipe_function;
    }

    // Function to add ingredients into ingredients table
    public int add_ingredients(String new_ingredients)
    {
        Uri result;

        // Create contentValues that will be used to store ingredients's input
        ContentValues inputs = new ContentValues();
        inputs.put(RecipeContract.INGREDIENT_LIST, new_ingredients);

        // Insert to the INGREDIENTSTABLE by using content resolvers
        result = content_resolver.insert(RecipeContract.INGREDIENT_URI,inputs);

        // Assigned an ID to the new ingredients
        StringBuffer tempStringBuffer = new StringBuffer(result.toString());

        // Delete space inputted by user
        tempStringBuffer.replace(0,12,"");

        return Integer.parseInt(tempStringBuffer.toString());
    }

    // Function that responsible to find the ingredients id given recipe ID
    public ArrayList<Integer> recipe_ingredients_id(int id)
    {
        ArrayList<Integer> arrayList = new ArrayList<>();

        // Find correspond ID
        String selection = "\""+ RecipeContract.RECIPE_INGREDIENTS_RECIPE_ID + "\"=\"" + id + "\"";

        Cursor cursor = content_resolver.query(RecipeContract.RECIPE_INGREDIENTS_URI,null, selection, null, null);

        // If recipe ingredients exist, move cursor and point to it
        if (cursor.moveToFirst()) {

            // While loop until the cursor reach
            while (!cursor.isAfterLast()) {
                arrayList.add(cursor.getInt(cursor.getColumnIndex(RecipeContract.RECIPE_INGREDIENTS_INGREDIENT_ID)));

                // Loop continue to next
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

    // Look for all ingredients
    public ArrayList<String> search_ingredients()
    {
        ArrayList<String> arrayList = new ArrayList<>();

        // Sort by ascending order
        Cursor cursor = content_resolver.query(RecipeContract.INGREDIENT_URI, null, null, null, RecipeContract.INGREDIENT_LIST + " ASC");

        // If the ingredients exist, move the cursor to the first ingredients title
        if (cursor.moveToFirst()) {

            // While loop until the cursor to reach the last
            while (!cursor.isAfterLast()) {
                arrayList.add(cursor.getString(cursor.getColumnIndex(RecipeContract.INGREDIENT_LIST)));

                // Loop continue to next
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

    // Function to add value to many to many table
    public void add_recipe_ingredients(int insert_recipe_id, int insert_ingredients_id)
    {

        // Create contentValues that will be used to store recipe's input
        ContentValues inputs = new ContentValues();
        inputs.put(RecipeContract.RECIPE_INGREDIENTS_RECIPE_ID, insert_recipe_id);
        inputs.put(RecipeContract.RECIPE_INGREDIENTS_INGREDIENT_ID,insert_ingredients_id);

        // Insert to the TABLE by using content resolvers
        content_resolver.insert(RecipeContract.RECIPE_INGREDIENTS_URI, inputs);
    }

    // Use ID of the ingredients to GET all the ingredients that needed to be display on UI as list
    public String search_ingredients_id(int id)
    {
        String ingredients_list;

        // Find correspond ID
        String selection = "\""+ RecipeContract.INGREDIENT_ID + "\"=\"" + id + "\"";

        Cursor cursor = content_resolver.query(RecipeContract.INGREDIENT_URI,null, selection, null, null);

        // Consecutively move the cursor to point to the first one
        // when the cursor still in the boundary of >=1
        if(cursor.moveToFirst() && cursor.getCount() >= 1) {

            // Do while loop to keep getting the ingredients
            // until the cursor points to end
            do {
                ingredients_list = cursor.getString(cursor.getColumnIndex(RecipeContract.INGREDIENT_LIST));
            } while (cursor.moveToNext());
        }
        else{
            ingredients_list = "No ingredients found";
        }

        return ingredients_list;
    }

} //end class