package com.example.sqltutorialspoint.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(Context theContext, String[] objects, int theLayoutResId) {
        super(theContext, theLayoutResId, objects);
    }

    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}