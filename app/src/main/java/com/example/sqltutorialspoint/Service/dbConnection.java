package com.example.sqltutorialspoint.Service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.sqltutorialspoint.Utility.constants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class dbConnection  extends AsyncTask{
    private Context context;
    private int byGetOrPost = 0;

    //flag 0 means get and 1 means post.(By default it is get.)
    public dbConnection(Context context) {
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
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line="";

            while ((line = in.readLine()) != null) {
                sb.append(line);
                System.out.println(sb);
                break;
            }

            in.close();
            return sb.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }

    }

    protected void onPreExecute(){
    }
    protected void onPostExecute(String result){

    }
}