package com.example.sqltutorialspoint.Presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.sqltutorialspoint.Model.Advertisement;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AdvertisePresenter {
    Context context;
    Advertisement advertisement;
    public AdvertisePresenter(Context context) {
        this.context = context;
        advertisement=new Advertisement(context);
    }
    public ArrayList<String[]> getAdvertiseListTexts() throws ExecutionException, InterruptedException {
        String[]id=advertisement.getAllId();
        ArrayList<String[]> arr=new ArrayList<>();
        for (int i=0;i<id.length;i++){
            String[]s=new String[5];
            s[0]=advertisement.getName(id[i]);
            s[1]=advertisement.getType(id[i]);
            s[2]=advertisement.getPrice(id[i]);
            s[3]=advertisement.getLocation(id[i]);
            s[4]=advertisement.getDate(id[i]);
            arr.add(s);

        }
        return arr;
    }
    public ArrayList<Bitmap> getAdvertiseImages() throws ExecutionException, InterruptedException {
        String[]id=advertisement.getAllId();
        ArrayList<Bitmap> arr=new ArrayList<>();
        for (int i=0;i<id.length;i++){
            arr.add(advertisement.getImage(id[i]));
        }
        return arr;
    }
}
