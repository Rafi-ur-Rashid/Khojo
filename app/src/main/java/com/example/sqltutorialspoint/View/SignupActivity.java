package com.example.sqltutorialspoint.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignupActivity extends AppCompatActivity implements GoToFirstActivity {
    private static final String TAG = "SignupActivity";
    MemberPresenter memberPresenter;
    String member_id;
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
        ButterKnife.bind(this);
        memberPresenter=new MemberPresenter(this,this);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed(3);
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

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
                                memberPresenter.updateSession(member_id,1);
                                onSignupSuccess();
                            }
                            else
                                onSignupFailed(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Sign up Success", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, null);
        memberPresenter.changeActivity();
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

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

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

    @Override
    public void changeActivity() {
        Intent intent=new Intent(this,FirstActivity.class);
        intent.putExtra("id",member_id);
        startActivity(intent);
        finish();

    }
}