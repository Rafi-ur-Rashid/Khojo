package com.example.sqltutorialspoint.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.sqltutorialspoint.Interface.GoToFirstActivity;
import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Service.verifyMailAddress;

import java.util.Random;

public class VerifyMailActivity extends AppCompatActivity implements GoToFirstActivity {
    private PinView codeText;
    private TextView showMail,send_again;
    MemberPresenter memberPresenter;
    String member_id,email,name,password,code;
    Bundle bundle;
    boolean addAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verify_mail);
        getSupportActionBar().hide();
        codeText=(PinView)findViewById(R.id.pinView);
        showMail=(TextView)findViewById(R.id.show_mail);
        send_again=(TextView)findViewById(R.id.send_again);
        bundle = getIntent().getExtras();
        addAd=bundle.getBoolean("addAd");
        SpannableString ss=new SpannableString("Didn't get the Code? Send again");
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Random random=new Random();
                code=String.format("%06d", random.nextInt(999999));
                verifyMailAddress vm=new verifyMailAddress(VerifyMailActivity.this);
                vm.execute(email,code);
                Toast.makeText(VerifyMailActivity.this,"Code is sent again",Toast.LENGTH_LONG);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 21, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        send_again.setText(ss);
        send_again.setMovementMethod(LinkMovementMethod.getInstance());

        memberPresenter=new MemberPresenter(this,this);
        email=bundle.getString("email");
        name=bundle.getString("name");
        password=bundle.getString("password");
        code=bundle.getString("code");
        showMail.setText(email);


    }


    public void verifyCode(View view) throws Exception {
        String s= String.valueOf(codeText.getText());
        if(s.equals(code))
            onVerificationSuccess();
        else
            onVerificationFailed();
    }

    public void onVerificationSuccess() throws Exception {
        memberPresenter.insertMember(name,email,password);
        member_id=memberPresenter.getId(email);
        memberPresenter.updateSession(member_id,1);
        Toast.makeText(getBaseContext(), "Sign up Success", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, null);
        if(!addAd)
            memberPresenter.goToFirstActivity();
        else
        {
            Intent intent = new Intent(this, AddnewAdvertiseActivity.class);
            intent.putExtra("id",member_id);
            startActivity(intent);
            finish();
        }
    }

    public void onVerificationFailed() throws Exception {

        Toast.makeText(getBaseContext(), "Wrong Verification Code", Toast.LENGTH_LONG).show();

    }

    @Override
    public void changeActivity() {
        Intent intent=new Intent(VerifyMailActivity.this,FirstActivity.class);
        intent.putExtra("id",member_id);
        startActivity(intent);
        finish();
    }
}
