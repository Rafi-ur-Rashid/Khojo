package com.example.sqltutorialspoint.Model;

import android.content.Context;

import com.example.sqltutorialspoint.Service.dbConnection;

import java.util.concurrent.ExecutionException;

import static com.example.sqltutorialspoint.Utility.constants.MEMBER_ID_BY_EMAIL;
import static com.example.sqltutorialspoint.Utility.constants.MEMBER_INSERT_PHP;
import static com.example.sqltutorialspoint.Utility.constants.MEMBER_PASS_BY_EMAIL;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_SESSION_BY_ID;

public class Member {
    Context context;

    dbConnection dbcon;

    public Member(Context context) {
        this.context = context;
    }
    public boolean insertMember(String Name,String email,String pw)throws Exception{
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("member/"+MEMBER_INSERT_PHP+"?memberName="+Name+"&email_phone="+email+"&password="+pw).get();
        dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean matchPassword(String email,String pw) throws ExecutionException, InterruptedException {
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("member/"+MEMBER_PASS_BY_EMAIL+"?email_phone="+email).get();
        dbcon.cancel(true);
        return s.equals(pw);
    }
    public String getId(String email) throws ExecutionException, InterruptedException {
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("member/"+MEMBER_ID_BY_EMAIL+"?email_phone="+email).get();
        dbcon.cancel(true);
        return s;
    }
    public boolean updateSession(String id,int stat) throws ExecutionException, InterruptedException {
        dbcon=new dbConnection(context);
        String s= (String) dbcon.execute("member/"+UPDATE_SESSION_BY_ID+"?memberID="+id+"&loginStat="+stat).get();
        dbcon.cancel(true);
        return s.equals("success");
    }

}
