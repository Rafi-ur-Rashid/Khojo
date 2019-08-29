package com.example.sqltutorialspoint.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.sqltutorialspoint.Utility.constants;

public class Category_and_locationAdapter {
    Context context;
    public Category_and_locationAdapter(Context context) {
        this.context = context;
    }
    public ArrayAdapter getCategoryAdapter(){
        // Spinner Drop down elements
        // Creating adapter for spinner
        SpinnerAdapter dataAdapter = new SpinnerAdapter(context,constants.CATEGORIES,android.R.layout.simple_spinner_item);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return dataAdapter;
    }
    public ArrayAdapter getLocationAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (context,android.R.layout.select_dialog_item, constants.PLACES);
        // Drop down layout style - list view with radio button
        return adapter;
    }
}
