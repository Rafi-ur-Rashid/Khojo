package com.example.sqltutorialspoint.Model;

import android.content.Context;

import com.example.sqltutorialspoint.Service.dbConnectionForTextData;

import java.util.concurrent.ExecutionException;

import static com.example.sqltutorialspoint.Utility.constants.ADMIN_NAME_BY_ID;

public class Admin {
    Context context;
    dbConnectionForTextData dbcon;

    public Admin(Context context) {
        this.context = context;
        dbcon=new dbConnectionForTextData(context);
    }
    public String getName(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("admin/"+ADMIN_NAME_BY_ID+"?admin_id="+id).get();
        //dbcon.cancel(true);
        return s;
    }
}
