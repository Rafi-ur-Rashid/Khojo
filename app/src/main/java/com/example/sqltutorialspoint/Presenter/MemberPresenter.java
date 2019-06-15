package com.example.sqltutorialspoint.Presenter;

import android.content.Context;

import com.example.sqltutorialspoint.Interface.GoToFirstActivity;
import com.example.sqltutorialspoint.Model.Member;

import java.util.concurrent.ExecutionException;

public class MemberPresenter {
    Context context;
    Member member;
    GoToFirstActivity goToFirstActivity;
    public MemberPresenter(Context context) {
        this.context = context;
        member=new Member(context);
    }
    public MemberPresenter(Context context, GoToFirstActivity goToFirstActivity) {
        this.context = context;
        this.goToFirstActivity=goToFirstActivity;
        member=new Member(context);
    }
    public boolean insertMember(String name,String email,String pw) throws Exception {
        return member.insertMember(name,email,pw);
    }
    public String getId(String email) throws ExecutionException, InterruptedException {
        return member.getId(email);
    }
    public boolean matchPassword(String email,String pw) throws ExecutionException, InterruptedException {
       return member.matchPassword(email,pw);
    }
    public void updateSession(String id,int stat) throws ExecutionException, InterruptedException {
        member.updateSession(id,stat);
    }
    public void changeActivity(){
        goToFirstActivity.changeActivity();
    }
}
