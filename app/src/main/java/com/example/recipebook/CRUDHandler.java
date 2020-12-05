package com.example.recipebook;

//Importing libraries that is needed to use their function to achieve outcome

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//This class purpose as a create,read,update,delete function handler for database
//In this project, it will be focus on create, read, and update rating

public class CRUDHandler extends SQLiteOpenHelper {

    private ContentResolver content_resolver;
    SQLiteDatabase database;

    //constructor for database handler
    public CRUDHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, RecipeContract.DATABASE_NAME, factory, RecipeContract.DATABASE_VERSION);
        database = getWritableDatabase();
        database= getReadableDatabase();
        content_resolver = context.getContentResolver();
    }

    //function that create the table
    //CREATE_RECIPES_TABLE = "CREATE TABLE "
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(RecipeContract.RecipeTable.CREATE_RECIPES_TABLE);

    }

    // upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        database.execSQL(RecipeContract.RecipeTable.DELETE_TABLE);
    }




}
