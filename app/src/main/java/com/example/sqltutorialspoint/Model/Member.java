package com.example.sqltutorialspoint.Model;

import android.content.Context;

import com.example.sqltutorialspoint.Service.dbConnectionForTextData;

import java.util.concurrent.ExecutionException;

import static com.example.sqltutorialspoint.Utility.constants.DELETE_MEMBER_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.EMAIL_PHONE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.MEMBER_ADDRESS_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.MEMBER_ID_BY_EMAIL;
import static com.example.sqltutorialspoint.Utility.constants.MEMBER_INSERT_PHP;
import static com.example.sqltutorialspoint.Utility.constants.MEMBER_NAME_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.MEMBER_PASS_BY_EMAIL;
import static com.example.sqltutorialspoint.Utility.constants.PASSWORD_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_SESSION_BY_ID;

public class Member {
    Context context;

    dbConnectionForTextData dbcon;

    public Member(Context context) {
        this.context = context;
    }
    public boolean insertMember(String Name,String email,String pw)throws Exception{
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("member/"+MEMBER_INSERT_PHP+"?memberName="+Name.replace(" ","%20")+"&email_phone="+email+"&password="+pw).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean matchPassword(String email,String pw) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("member/"+MEMBER_PASS_BY_EMAIL+"?email_phone="+email).get();
        //dbcon.cancel(true);
        return s.equals(pw);
    }
    public String getId(String email) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("member/"+MEMBER_ID_BY_EMAIL+"?email_phone="+email).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getName(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("member/"+MEMBER_NAME_BY_ID+"?member_id="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getEmail_Phone(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("member/"+EMAIL_PHONE_BY_ID+"?member_id="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getAddress(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("member/"+MEMBER_ADDRESS_BY_ID+"?member_id="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getPassword(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("member/"+PASSWORD_BY_ID+"?member_id="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public boolean updateSession(String id,int stat) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("member/"+UPDATE_SESSION_BY_ID+"?memberID="+id+"&loginStat="+stat).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean deleteMember(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("member/"+DELETE_MEMBER_BY_ID+"?memberID="+id).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }

}
