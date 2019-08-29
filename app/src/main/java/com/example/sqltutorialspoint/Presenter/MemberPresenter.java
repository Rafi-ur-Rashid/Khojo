package com.example.sqltutorialspoint.Presenter;

import android.content.Context;

import com.example.sqltutorialspoint.Interface.GoToFirstActivity;
import com.example.sqltutorialspoint.Interface.GoToverifyMailorPhone;
import com.example.sqltutorialspoint.Model.Member;

import java.util.concurrent.ExecutionException;

public class MemberPresenter {
    Context context;
    Member member;
    GoToFirstActivity goToFirstActivity;
    GoToverifyMailorPhone goToverifyMailorPhone;
    public MemberPresenter(Context context) {
        this.context = context;
        member=new Member(context);
    }
    public MemberPresenter(Context context, GoToFirstActivity goToFirstActivity) {
        this.context = context;
        this.goToFirstActivity=goToFirstActivity;
        member=new Member(context);
    }
    public MemberPresenter(Context context, GoToverifyMailorPhone goToverifyMailorPhone) {
        this.context = context;
        this.goToverifyMailorPhone=goToverifyMailorPhone;
        member=new Member(context);
    }
    public boolean insertMember(String name,String email,String pw) throws Exception {
        return member.insertMember(name,email,pw);
    }
    public String getId(String email) throws ExecutionException, InterruptedException {
        return member.getId(email);
    }
    public String getEmail_Phone(String id) throws ExecutionException, InterruptedException {
        return member.getEmail_Phone(id);
    }
    public String getAddress(String id) throws ExecutionException, InterruptedException {
        return member.getAddress(id);
    }
    public String getName(String id) throws ExecutionException, InterruptedException {
        return member.getName(id);
    }
    public String getPassword(String id) throws ExecutionException, InterruptedException {
        return member.getPassword(id);
    }
    public boolean matchPassword(String email,String pw) throws ExecutionException, InterruptedException {
       return member.matchPassword(email,pw);
    }
    public boolean updateSession(String id,int stat) throws ExecutionException, InterruptedException {
       return member.updateSession(id,stat);
    }
    public void deleteMember(String id) throws ExecutionException, InterruptedException {
        member.deleteMember(id);
    }
    public void goToFirstActivity(){
        goToFirstActivity.changeActivity();
    }
    public void goToVerifyActivity() throws ExecutionException, InterruptedException {
        goToverifyMailorPhone.changeActivity();
    }
}
