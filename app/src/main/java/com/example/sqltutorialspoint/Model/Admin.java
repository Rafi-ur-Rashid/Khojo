package com.example.sqltutorialspoint.Model;

import android.content.Context;

import com.example.sqltutorialspoint.Service.dbConnection;

import static com.example.sqltutorialspoint.Utility.constants.ADMIN_INSERT_PHP;

public class Admin {
    Context context;

    dbConnection dbcon;
    public Admin(Context context) {
        this.context = context;
        dbcon=new dbConnection(context);
    }

    public boolean insertAdmin(String ID,String Name,String email,String pw)throws Exception{
        String s= (String) dbcon.execute("admin/"+ADMIN_INSERT_PHP+"?adminId="+ID+"&adminName="+Name+"&email_phone="+email+"&password="+pw).get();
        dbcon.cancel(true);
        return s.equals("success");
    }
}


