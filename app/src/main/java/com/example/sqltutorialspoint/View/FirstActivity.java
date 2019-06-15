package com.example.sqltutorialspoint.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar.LayoutParams;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sqltutorialspoint.Adapter.Category_and_locationAdapter;
import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Utility.constants;

import java.util.concurrent.ExecutionException;

public class FirstActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int mInterval = 1000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    MemberPresenter memberPresenter;
    ImageView ad_image;
    TextView sign_in;
    boolean ad,logged_in=false;
    String member_id;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setIcon(R.drawable.ic_menu_share);
        ad_image=(ImageView)findViewById(R.id.imageView8);
        ad=false;
        memberPresenter=new MemberPresenter(this);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        View v = inflator.inflate(R.layout.first_actionbar, null);
        getSupportActionBar().setCustomView(v, layout);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        AutoCompleteTextView autocomplete = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);
        sign_in = (TextView) v.findViewById(R.id.sign_in);
        bundle = getIntent().getExtras();
        if(bundle!=null){
            sign_in.setText("Sign out");
            logged_in=true;
            member_id=bundle.getString("id");
        }
        spinner.setOnItemSelectedListener(this);
        Category_and_locationAdapter adapter=new Category_and_locationAdapter(this);
        spinner.setAdapter(adapter.getCategoryAdapter());
        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter.getLocationAdapter());
        mHandler = new Handler();
        startRepeatingTask();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                if(ad) {
                    ad_image.setImageResource(R.drawable.ads2);
                    ad=false;
                }else{
                    ad_image.setImageResource(R.drawable.ads);
                    ad=true;
                }
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    public void goTosignIn(View view) {
        if(!logged_in) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, constants.REQUEST_LOGIN);
            finish();
        }else{
            confirmDialog2(this);
        }

    }

    @Override
    public void onBackPressed() {
        confirmDialog(this);
    }

    private void confirmDialog(Context context) {

        new AlertDialog.Builder(context)
                .setMessage("Are you sure to quit?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(logged_in) {
                            try {
                                memberPresenter.updateSession(member_id,0);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void confirmDialog2(final Context context) {

        new AlertDialog.Builder(context)
                .setMessage("Are you sure to Sign Out?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            //memberPresenter.updateSession(member_id,0);
                            logged_in=false;
                            Toast.makeText(FirstActivity.this,"Signed out "+member_id,Toast.LENGTH_LONG);
                            Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_first, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void goToAd(View view) {

    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
}