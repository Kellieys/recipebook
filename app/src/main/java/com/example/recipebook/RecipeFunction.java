package com.example.recipebook;

// This class purpose is to
// locate all function related to recipe in one file to keep it neat

public class RecipeFunction {

    // Initialization for all variables
        private int id;
        private String recipe_title;
        private String recipe_instruction;
        private float recipe_rating;

    // Empty constructor if there are no arguments for reflection
    public RecipeFunction() {
    }

    // Constructor that contain arguments to take care of
    public RecipeFunction(String recipe_title, String recipe_instruction, float recipe_rating) {
        this.recipe_title = recipe_title;
        this.recipe_instruction = recipe_instruction;
        this.recipe_rating = recipe_rating;
    }

    // Getter and setters functions that helped to protect the data
    public int getRecipeId(){
        return this.id;
    }
    public String getRecipeTitle() {
        return this.recipe_title;
    }
    public String getRecipeInstruction() {
        return this.recipe_instruction;
    }
    public float getRecipeRating() {
        return this.recipe_rating;
    }

    public void setRecipeId(int id){
        this.id = id;
    }
    public void setRecipeTitle(String s) {
        this.recipe_title = s;
    }
    public void setRecipeInstruction(String s) { this.recipe_instruction = s; }
    public void setRecipeRating(float value) {
        this.recipe_rating = value;
    }

}
