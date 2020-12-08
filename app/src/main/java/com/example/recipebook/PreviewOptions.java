package com.example.recipebook;

import android.view.View;
import android.os.Bundle;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

// This class is responsible for user to select what they want to view

public class PreviewOptions extends AppCompatActivity {

    private Spinner dropdown;
    private String[] dropdown_item = new String[]{"Title (Alphabetical Order)", "Rating (High to Low)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_options);
        dropdown = findViewById(R.id.dropdown);

        // Reference https://developer.android.com/reference/android/widget/ArrayAdapter
        // Returns a view for each object in a collection of data objects you provide,
        // and can be used with list-based user interface widgets
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        int dropdownPosition = adapter.getPosition("Rating (High to Low)");
        dropdown.setSelection(dropdownPosition);
    }

    // Start new activity according to the user input
    public void select_onclick(View view)
    {
        Intent i = new Intent(this, DisplayList.class);
        i.putExtra("sorting", dropdown.getSelectedItem().toString());
        startActivity(i);
    }

    // Start another activity if user click on check button to see ingredients
    public void check_onclick(View view)
    {
        Intent i = new Intent(this, SpecialIngredients.class);
        startActivity(i);
    }

} //end class
