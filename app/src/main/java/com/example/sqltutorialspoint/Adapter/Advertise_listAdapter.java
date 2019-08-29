package com.example.sqltutorialspoint.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Utility.constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class Advertise_listAdapter extends BaseAdapter{
    Context c;
    TextView name,type,price,location,date,verify;
    boolean user_ad,verified,deleted;
    ImageView photo;
    ArrayList<String[]> list;
    ArrayList<Bitmap>bm;
    public Advertise_listAdapter(Context c, ArrayList<String[]> list,ArrayList<Bitmap>bm,boolean user_ad) {
        this.c = c;
        this.list = list;
        this.bm=bm;
        this.user_ad=user_ad;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(user_ad) {
            convertView = inflater.inflate(R.layout.advertise_list_element, parent, false);
            if(list.get(list.size()-position-1)[5].equals("1"))
                verified=true;
            else
                verified=false;
            if(list.get(list.size()-position-1)[6].equals("1"))
                deleted=true;
            else
                deleted=false;
        }else
            convertView = inflater.inflate(R.layout.advertise_list_element, parent, false);
        name = (TextView) convertView.findViewById(R.id.name);
        type= (TextView) convertView.findViewById(R.id.type);
        price= (TextView) convertView.findViewById(R.id.price);
        location= (TextView) convertView.findViewById(R.id.location);
        date= (TextView) convertView.findViewById(R.id.time);
        verify=(TextView)convertView.findViewById(R.id.verified);
        photo = (ImageView) convertView.findViewById(R.id.photo);
        photo.setImageBitmap(bm.get(list.size()-position-1));
        name.setText(list.get(list.size()-position-1)[0].toUpperCase());
        type.setText("@"+list.get(list.size()-position-1)[1]);
        price.setText("৳"+list.get(list.size()-position-1)[2]);
        location.setText(list.get(list.size()-position-1)[3]);
        if(user_ad){
            verify.setVisibility(View.VISIBLE);
            if(verified) {
                verify.setText("PUBLISHED");
                verify.setTextColor(ContextCompat.getColor(c,R.color.colorPrimary));
            }else if(deleted){
                verify.setText("DELETED");
                verify.setTextColor(ContextCompat.getColor(c,R.color.colorAccent));
            }
        }

        try {
            String time=list.get(list.size()-position-1)[4];
            String format = "yyyy-MM-dd hh:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String timeStamp = sdf.format(Calendar.getInstance().getTime());
            Date dateObj1 = sdf.parse(time);
            Date dateObj2 = sdf.parse(timeStamp);
            long diff = dateObj2.getTime() - dateObj1.getTime();
            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
            StringTokenizer st=new StringTokenizer(time," ");
            time=st.nextToken();
            st=new StringTokenizer(time,"-");
            String[] date_element={st.nextToken(),st.nextToken(),st.nextToken()};
            date.setText(constants.MONTH_MAP.get(Integer.parseInt(date_element[1]))+" "+date_element[2]);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
