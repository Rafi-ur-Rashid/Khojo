package com.example.sqltutorialspoint.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.sqltutorialspoint.Service.dbConnection;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

import static com.example.sqltutorialspoint.Utility.constants.AD_DATE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_LOCATION_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_NAME_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_PRICE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_TYPE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.GET_ALL_AD_ID;

public class Advertisement {
    Context context;

    dbConnection dbcon;
    public Advertisement(Context context) {
        this.context = context;
        dbcon=new dbConnection(context);
    }
    public String[] getAllId() throws ExecutionException, InterruptedException {
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("advertisement/"+GET_ALL_AD_ID).get();
        String[] id=s.split("##");
        dbcon.cancel(true);
        return id;
    }
    public String getName(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("advertisement/"+AD_NAME_BY_ID+"?adId="+id).get();
        dbcon.cancel(true);
        return s;
    }
    public String getType(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("advertisement/"+AD_TYPE_BY_ID+"?adId="+id).get();
        dbcon.cancel(true);
        return s;
    }
    public String getLocation(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("advertisement/"+AD_LOCATION_BY_ID+"?adId="+id).get();
        dbcon.cancel(true);
        return s;
    }
    public String getPrice(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("advertisement/"+AD_PRICE_BY_ID+"?adId="+id).get();
        dbcon.cancel(true);
        return s;
    }
    public String getDate(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("advertisement/"+AD_DATE_BY_ID+"?adId="+id).get();
        dbcon.cancel(true);
        return s;
    }
    public Bitmap getImage(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("advertisement/"+AD_DATE_BY_ID+"?adId="+id).get();
        InputStream inputStream = new ByteArrayInputStream(s.getBytes(Charset.forName("UTF-8")));
        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
        dbcon.cancel(true);
        return bitmap;
    }

}
