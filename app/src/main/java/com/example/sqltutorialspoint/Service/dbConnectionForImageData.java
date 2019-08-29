package com.example.sqltutorialspoint.Service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.sqltutorialspoint.Utility.constants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class dbConnectionForImageData extends AsyncTask{
    private Context context;

    public dbConnectionForImageData(Context context) {
        this.context = context;

    }

    @Override
    protected Object doInBackground(Object[] arg0) {

        try{
            String php_script = (String)arg0[0];
            String link = "http://"+ constants.dbServer+"/"+php_script;
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            InputStream inputStream=response.getEntity().getContent();
            Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;


        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected void onPreExecute(){
    }
    protected void onPostExecute(String result){

    }
}

