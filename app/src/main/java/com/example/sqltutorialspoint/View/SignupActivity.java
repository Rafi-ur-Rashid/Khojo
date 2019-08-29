package com.example.sqltutorialspoint.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqltutorialspoint.Interface.GoToverifyMailorPhone;
import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Service.dbConnectionForTextData;
import com.example.sqltutorialspoint.Service.verifyMailAddress;
import com.google.android.material.button.MaterialButton;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sqltutorialspoint.Utility.constants.VERIFY_BY_SMS;


public class SignupActivity extends AppCompatActivity implements GoToverifyMailorPhone {
    MemberPresenter memberPresenter;
    String member_id,email,name,password;
    Bundle bundle;
    boolean addAd;
    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        _signupButton=(MaterialButton)findViewById(R.id.btn_signup);
        _loginLink=(TextView) findViewById(R.id.link_login);
        _emailText=(EditText)findViewById(R.id.input_email);
        _passwordText=(EditText)findViewById(R.id.input_password);
        _nameText=(EditText)findViewById(R.id.input_name);
        bundle = getIntent().getExtras();
        addAd=bundle.getBoolean("addAd");
        ButterKnife.bind(this);
        SpannableString ss=new SpannableString("Already a member? Login");
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                finish();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 18, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        _loginLink.setText(ss);
        _loginLink.setMovementMethod(LinkMovementMethod.getInstance());
        memberPresenter=new MemberPresenter(this,this);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    public void signup() {
        name = _nameText.getText().toString();
        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        if (!validate()) {
            onSignupFailed(3);
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressBar= new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        try {
                            ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                            if (netInfo == null){
                                onSignupFailed(2);
                            }
                            else if(memberPresenter.insertMember(name,email,password)) {
                                member_id=memberPresenter.getId(email);
                                memberPresenter.deleteMember(member_id);
                                onSignupSuccess();
                            }
                            else
                                onSignupFailed(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressBar.dismiss();
                    }
                }, 4000);
    }

    public boolean validate() {
        boolean valid = true;
        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && !Patterns.PHONE.matcher(email).matches())) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void onSignupSuccess() throws ExecutionException, InterruptedException {

        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        memberPresenter.goToVerifyActivity();
    }

    public void onSignupFailed(int id) {
        if(id==1) {
            Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "This Email/Phone is already used", Toast.LENGTH_LONG).show();
        }
        else if(id==2)
        {
            Toast.makeText(getBaseContext(), "No Internet", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();
        }
        _signupButton.setEnabled(true);
    }



    @Override
    public void changeActivity() throws ExecutionException, InterruptedException {
        Random random=new Random();
        String code=String.format("%06d", random.nextInt(999999));
        if(email.contains("@")) {
            verifyMailAddress vm = new verifyMailAddress(SignupActivity.this);
            vm.execute(email, code);
        }else
        {
            dbConnectionForTextData dbcon=new dbConnectionForTextData(SignupActivity.this);
            dbcon.execute("member/"+VERIFY_BY_SMS+"?phoneNumber="+email+"&"+"otp="+code).get();
        }
        Intent intent=new Intent(SignupActivity.this,VerifyMailActivity.class);
        intent.putExtra("email",email);
        intent.putExtra("name",name);
        intent.putExtra("password",password);
        intent.putExtra("code",code);
        intent.putExtra("addAd",addAd);
        startActivity(intent);
        finish();


    }

}