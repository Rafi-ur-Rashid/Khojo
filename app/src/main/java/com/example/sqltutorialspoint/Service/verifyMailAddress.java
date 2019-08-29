package com.example.sqltutorialspoint.Service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.sqltutorialspoint.Utility.GMailSender;

public class verifyMailAddress extends AsyncTask {
    private Context context;

    //flag 0 means get and 1 means post.(By default it is get.)
    public verifyMailAddress(Context context) {
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] arg0) {
        String subject="Khojo-User Email Veification";
        String sender="khojobasabari@gmail.com";
        String recipient= (String) arg0[0];
        String code=(String) arg0[1];
        GMailSender gMailSender=new GMailSender("khojobasabari@gmail.com","aA123456$");
        try {
            gMailSender.sendMail(subject,"Your Khojo-User verification code is :"+code,sender,recipient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPreExecute(){
    }
    protected void onPostExecute(String result){

    }
}
