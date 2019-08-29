package com.example.sqltutorialspoint.Service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.sqltutorialspoint.Utility.constants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class dbConnectionForTextData extends AsyncTask{
    private Context context;

    public dbConnectionForTextData(Context context) {
        this.context = context;

    }

    @Override
    protected Object doInBackground(Object[] arg0) {

        try{
            String php_script = (String)arg0[0];		//php fileName 
            String link = "http://"+ constants.dbServer+"/"+php_script;	//Directory address of the php file
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);	
            InputStream inputStream=response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer("");
            String line="";

            while ((line = in.readLine()) != null) {	//Read line by line of the response
                sb.append(line);
                break;
            }
            in.close();
            return sb.toString();

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

