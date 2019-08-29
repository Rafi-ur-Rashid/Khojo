package com.example.sqltutorialspoint.Service;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import com.example.sqltutorialspoint.Utility.constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static com.example.sqltutorialspoint.Utility.constants.INSERT_ADVERTISE;

public class dbConnectionForAdvertiseInsert  extends AsyncTask{

    RequestHandler rh = new RequestHandler();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] arg) {
        Bitmap bitmap =(Bitmap)arg[0];
        String imgName=(String)arg[1];
        String title=(String)arg[2];
        String id=(String)arg[3];
        String location=(String)arg[4];
        String type=(String)arg[5];
        String price=(String)arg[6];
        String address=(String)arg[7];
        String contact_no=(String)arg[8];
        String description=(String)arg[9];
        String uploadImage = getStringImage(bitmap);
        String url="http://"+ constants.dbServer+"/advertisement/"+INSERT_ADVERTISE;
        HashMap<String,String> data = new HashMap<>();
        data.put("image_data", uploadImage);
        data.put("image_name",imgName);
        data.put("title",title);
        data.put("memberId",id);
        data.put("location",location);
        data.put("address",address);
        data.put("type",type);
        data.put("price",price);
        data.put("contact_no",contact_no);
        data.put("description",description);
        System.out.println("Hiiii "+description);
        String result = rh.sendPostRequest(url,data);
        return result;
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
/**
 * Created by Belal on 8/19/2015.
 */

class RequestHandler {

    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams) {

        URL url;

        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;
                while ((response = br.readLine()) != null){
                    sb.append(response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}