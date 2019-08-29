package com.example.sqltutorialspoint.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.ExecutionException;

public class MemberProfileAcivity extends AppCompatActivity {
    MemberPresenter memberPresenter;
    String member_id,name,email,phone,address;
    boolean logged_in;
    TextView Name,Email,Phone,Location;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile_acivity);
        getSupportActionBar().hide();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        logged_in=true;
        Name=(TextView)findViewById(R.id.name);
        Location=(TextView)findViewById(R.id.loc);
        Email=(TextView)findViewById(R.id.email);
        Phone=(TextView)findViewById(R.id.phone);
        bundle = getIntent().getExtras();
        member_id=bundle.getString("id");
        memberPresenter=new MemberPresenter(this);
        try {
            name=memberPresenter.getName(member_id);
            email=memberPresenter.getEmail_Phone(member_id);
            address=memberPresenter.getAddress(member_id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Name.setText(name);
        if(address.length()>0)
            Location.setText(address);
        if(email.contains("@"))
            Email.setText(email);
        else
            Phone.setText(email);


    }
    @Override
    public void onBackPressed(){
        if(logged_in) {
            Intent intent = new Intent(MemberProfileAcivity.this, FirstActivity.class);
            intent.putExtra("id", member_id);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(MemberProfileAcivity.this, FirstActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
