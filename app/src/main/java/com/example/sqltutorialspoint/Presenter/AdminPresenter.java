package com.example.sqltutorialspoint.Presenter;

import android.content.Context;

import com.example.sqltutorialspoint.Model.Admin;

public class AdminPresenter {
    Context context;
    Admin admin;
    public AdminPresenter(Context context) {
        this.context = context;
        admin=new Admin(context);
    }
    public void insertAdmin(String id,String name,String email,String pw) throws Exception {
         admin.insertAdmin(id,name,email,pw);
    }
    public boolean matchPassword(String email,String pw){
        return false;
    }
}
