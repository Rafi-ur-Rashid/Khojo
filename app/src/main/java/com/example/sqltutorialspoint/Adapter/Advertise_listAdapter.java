package com.example.sqltutorialspoint.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqltutorialspoint.R;

import java.util.ArrayList;

public class Advertise_listAdapter extends BaseAdapter {
    Context c;
    TextView name,type,price,location,date;
    ImageView photo;
    ArrayList<String[]> list;
    ArrayList<Bitmap>bm;
    public Advertise_listAdapter(Context c, ArrayList<String[]> list,ArrayList<Bitmap>bm) {
        this.c = c;
        this.list = list;
        this.bm=bm;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.advertise_list_element, parent, false);
        name = (TextView) convertView.findViewById(R.id.name);
        type= (TextView) convertView.findViewById(R.id.type);
        price= (TextView) convertView.findViewById(R.id.price);
        location= (TextView) convertView.findViewById(R.id.location);
        date= (TextView) convertView.findViewById(R.id.date);
        photo = (ImageView) convertView.findViewById(R.id.photo);
        photo.setImageBitmap(bm.get(list.size()-position-1));
        name.setText(list.get(list.size()-position-1)[0]);
        type.setText(list.get(list.size()-position-1)[1]);
        price.setText("৳"+list.get(list.size()-position-1)[2]);
        location.setText(list.get(list.size()-position-1)[3]);
        //date.setText("Hello");
        return convertView;
    }
}
