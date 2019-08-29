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
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqltutorialspoint.Interface.GoToFirstActivity;
import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.R;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sqltutorialspoint.Utility.constants.REQUEST_SIGNUP;

public class LoginActivity extends AppCompatActivity implements GoToFirstActivity{
    MemberPresenter memberPresenter;
    String member_id;
    Bundle bundle;
    boolean addAd;
    private static final String TAG = "LoginActivity";

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
         _loginButton=(MaterialButton)findViewById(R.id.btn_login);
        _signupLink=(TextView)findViewById(R.id.link_signup);
        _emailText=(EditText)findViewById(R.id.input_email);
        _passwordText=(EditText)findViewById(R.id.input_password);
        bundle = getIntent().getExtras();
        addAd=bundle.getBoolean("addAd");
        if(addAd){
            Toast.makeText(LoginActivity.this,"You Have to Log in First",Toast.LENGTH_LONG).show();
        }
        ButterKnife.bind(this);
        SpannableString ss=new SpannableString("No account yet? Create one");
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                intent.putExtra("addAd",addAd);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 16, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        _signupLink.setText(ss);
        _signupLink.setMovementMethod(LinkMovementMethod.getInstance());
        memberPresenter=new MemberPresenter(this,this);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed(3);
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressBar= new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Authenticating...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        try {
                            ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                            if (netInfo == null){
                                onLoginFailed(2);
                            }
                            else if(memberPresenter.matchPassword(email,password)) {
                                member_id=memberPresenter.getId(email);
                                System.out.println("Hellooooo "+member_id+" "+memberPresenter.updateSession(member_id,1));

                                onLoginSuccess();
                            }
                            else
                                onLoginFailed(1);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progressBar.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        Intent intent=new Intent(this,FirstActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Login success", Toast.LENGTH_LONG).show();
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

    public void onLoginFailed(int id) {
        if(id==1) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "Invalid Email/Password", Toast.LENGTH_LONG).show();
        }
        else if(id==2){
            Toast.makeText(getBaseContext(), "No Internet", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        }

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

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

    @Override
    public void changeActivity() {
        Intent intent=new Intent(this,FirstActivity.class);
        intent.putExtra("id",member_id);
        startActivity(intent);
        finish();

    }
}
