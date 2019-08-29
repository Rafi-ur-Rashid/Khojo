package com.example.sqltutorialspoint.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sqltutorialspoint.R;

public class DropdownListAdapter extends BaseAdapter {
    Context c;
    TextView name;
    RelativeLayout rl;
    String[] list;
    boolean[] selected;
    int place_count,price_count,type_count;
    public DropdownListAdapter(Context c, String[] list,boolean[] selected) {
        this.c = c;
        this.list = list;
        this.selected=selected;
        place_count=price_count=type_count=0;

    }
    public void setList(String[] list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.dropdown_list_element, parent, false);
        name = (TextView) convertView.findViewById(R.id.name);
        rl=(RelativeLayout)convertView.findViewById(R.id.lout);
        name.setText(list[position]);
        if(selected[position])
            rl.setBackgroundColor(Color.GRAY);
        else
            rl.setBackgroundColor(Color.WHITE);
        return convertView;
    }
}
