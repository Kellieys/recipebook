package com.example.recipebook;

import android.net.Uri;
import android.database.Cursor;
import android.content.ContentValues;
import android.content.ContentProvider;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

//The purpose of this class is to handle the queries

public class AppContentProvider extends ContentProvider {

    private CRUDHandler crud_handler;

    public AppContentProvider() { }

    @Override
    public boolean onCreate()
    {
        crud_handler = new CRUDHandler(getContext(), null, null, 1);
        return false;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Uri matching
        int uriType = RecipeContract.sURIMatcher.match(uri);

        // Get databases
        SQLiteDatabase sqlDB = crud_handler.getWritableDatabase();
        long id = 0;

        // Switch case to check for Uri
        switch (uriType){

            case RecipeContract.RECIPE:
                // Insert into database recipe database
                id = sqlDB.insert(RecipeContract.TABLE_RECIPES,null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(RecipeContract.TABLE_RECIPES + "/" + id);

            case RecipeContract.INGREDIENTS:
                // Insert into ingredient database
                id = sqlDB.insert(RecipeContract.TABLE_INGREDIENTS, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(RecipeContract.TABLE_INGREDIENTS + "/" + id);

            case RecipeContract.RECIPE_INGREDIENTS:
                // Insert into Many to Many table
                id = sqlDB.insert(RecipeContract.TABLE_RECIPE_INGREDIENTS, null, values);
                return uri;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    // Not Implemented
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Not needed for this application
        throw new UnsupportedOperationException("Not Implemented");
    }

    // Not Implemented
    @Override
    public String getType(Uri uri) {
        // Not needed for this application
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Build SQLite query
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // uri matching
        int uriType = RecipeContract.sURIMatcher.match(uri);

        // Switch case to check for Uri
        switch(uriType){
            case RecipeContract.RECIPE_TABLE_ID:
                // Set to recipe table, query information with recipe id
                queryBuilder.setTables(RecipeContract.TABLE_RECIPES);
                queryBuilder.appendWhere(RecipeContract.RECIPE_ID + '=' + uri.getLastPathSegment());
                break;
            case RecipeContract.RECIPE:
                // Set to recipe table,raw query
                queryBuilder.setTables(RecipeContract.TABLE_RECIPES);
                break;
            case RecipeContract.INGREDIENTS_TABLE_ID:
                // Set to ingredients table, query information with ingredients id
                queryBuilder.setTables(RecipeContract.TABLE_INGREDIENTS);
                queryBuilder.appendWhere(RecipeContract.INGREDIENT_ID + "=" + uri.getLastPathSegment());
                break;
            case RecipeContract.INGREDIENTS:
                // Set to recipe table, raw query
                queryBuilder.setTables(RecipeContract.TABLE_INGREDIENTS);
                break;
            case RecipeContract.RECIPE_INGREDIENTS:
                // Set to Many to Many table, raw query
                queryBuilder.setTables(RecipeContract.TABLE_RECIPE_INGREDIENTS);
                break;
            case RecipeContract.RECIPE_INGREDIENTS_TABLE_ID:
                // Set to Many to Many table, raw query
                queryBuilder.setTables(RecipeContract.TABLE_RECIPE_INGREDIENTS);
                break;
            default:
                throw new IllegalArgumentException("Unknown Identifier" + uri);
        }

        // Query from table and return result
        Cursor cursor = queryBuilder.query(crud_handler.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = RecipeContract.sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = crud_handler.getWritableDatabase();

        int rowsUpdated = 0;
        switch (uriType) {
            case RecipeContract.RECIPE:
                rowsUpdated =
                        sqlDB.update(RecipeContract.TABLE_RECIPES, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "
                        + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

} //end of class