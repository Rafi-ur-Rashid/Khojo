package com.example.sqltutorialspoint.Presenter;

import android.content.Context;

import com.example.sqltutorialspoint.Model.Admin;

import java.util.concurrent.ExecutionException;

public class AdminPresenter {
    Admin admin;
    Context context;

    public AdminPresenter(Context context) {
        this.context = context;
        admin=new Admin(context);
    }
    public boolean isAdmin(String id) throws ExecutionException, InterruptedException {
        String s = admin.getName(id);
        if (s.length() < 1)
            return false;
        else
            return true;
    }
}
