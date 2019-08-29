package com.example.sqltutorialspoint.View;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.sqltutorialspoint.Presenter.AdminPresenter;
import com.example.sqltutorialspoint.Presenter.AdvertisePresenter;
import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Utility.constants;

import java.util.concurrent.ExecutionException;

public class FirstActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int mInterval = 1000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    MemberPresenter memberPresenter;
    AdvertisePresenter advertisePresenter;
    AdminPresenter adminPresenter;
    ImageView ad_image;
    TextView sign_in;
    AutoCompleteTextView input_loc;
    Spinner spinner;
    boolean ad,logged_in=false;
    String member_id,location,type;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        memberPresenter=new MemberPresenter(this);
        advertisePresenter=new AdvertisePresenter(this);
        adminPresenter=new AdminPresenter(this);
        bundle = getIntent().getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        View v = inflator.inflate(R.layout.first_actionbar, null);
        getSupportActionBar().setCustomView(v, layout);
        ad_image=(ImageView)findViewById(R.id.imageView8);
        ad=false;
        spinner = (Spinner) findViewById(R.id.spinner);

        input_loc = (AutoCompleteTextView)
                findViewById(R.id.input_location);
        sign_in = (TextView) v.findViewById(R.id.sign_in);
        if(bundle!=null){
            sign_in.setText("Sign out");
            logged_in=true;
            member_id=bundle.getString("id");
            try {
                if(advertisePresenter.haveNon_notified_verified_Ads(member_id)) {
                    notify("Your Ad. is published!");
                    advertisePresenter.updateNotifiedByMemberId(member_id);
                }else if(advertisePresenter.haveNon_notified_deleted_Ads(member_id)) {
                    notify("Your Ad. is Deleted!");
                    advertisePresenter.updateNotifiedByMemberId(member_id);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        spinner.setOnItemSelectedListener(this);
        Category_and_locationAdapter adapter=new Category_and_locationAdapter(this);
        spinner.setAdapter(adapter.getCategoryAdapter());
        input_loc.setThreshold(1);
        input_loc.setAdapter(adapter.getLocationAdapter());
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
            intent.putExtra("addAd",false);
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
                            memberPresenter.updateSession(member_id,0);
                            logged_in=false;
                            Toast.makeText(FirstActivity.this,"Signed out",Toast.LENGTH_LONG);
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
        MenuItem item2=menu.getItem(1);
        MenuItem item3=menu.getItem(2);
        MenuItem item4=menu.getItem(3);
        if(logged_in) {
            item2.setVisible(true);
            item3.setVisible(true);
            try {
                if(adminPresenter.isAdmin(member_id))
                    item4.setVisible(true);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            item2.setVisible(false);
            item3.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_profile)
        {
            Intent intent = new Intent(FirstActivity.this, MemberProfileAcivity.class);
            intent.putExtra("id",member_id);
            startActivity(intent);
            finish();
        }
        if(item.getItemId()==R.id.action_editAd)
        {
            try {
                if(advertisePresenter.haveAds(member_id)) {
                    Intent intent = new Intent(this, AdvertiseListActivity.class);
                    intent.putExtra("logged_in", logged_in);
                    intent.putExtra("user_ad", true);
                    intent.putExtra("id", member_id);

                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(FirstActivity.this,"You haven't published any Ad yet",Toast.LENGTH_LONG).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(item.getItemId()==R.id.action_timeline) {
            Intent intent = new Intent(this, AdminTimelineActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type = adapterView.getItemAtPosition(i).toString().toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void goToAd(View view) throws ExecutionException, InterruptedException {
        if(advertisePresenter.haveAds()) {
            Intent intent = new Intent(this, AdvertiseListActivity.class);
            intent.putExtra("logged_in", logged_in);
            intent.putExtra("id", member_id);
            intent.putExtra("user_ad", false);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(FirstActivity.this,"No Ad to show",Toast.LENGTH_LONG).show();
        }
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    public void addnewAd(View view) {
        if(logged_in) {
            Intent intent = new Intent(this, AddnewAdvertiseActivity.class);
            intent.putExtra("id",member_id);
            startActivity(intent);
            finish();
        }else{

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("addAd",true);
            startActivityForResult(intent, constants.REQUEST_LOGIN);
            finish();
        }
    }

    public void searchNow(View view) throws ExecutionException, InterruptedException {
        location=input_loc.getText().toString();
        if(location.length()<1)
        {
            if(!advertisePresenter.haveAdsbyType(type)){
                Toast.makeText(FirstActivity.this,"No Ad matches to the given value",Toast.LENGTH_LONG).show();
                return;
            }
        }else{
            if(!advertisePresenter.haveAdsbyType_Loc(type,location)){
                Toast.makeText(FirstActivity.this,"No Ad matches to the given value",Toast.LENGTH_LONG).show();
                return;
            }
        }
        Intent intent = new Intent(this, AdvertiseListActivity.class);
        intent.putExtra("logged_in", logged_in);
        intent.putExtra("id", member_id);
        intent.putExtra("user_ad", false);
        intent.putExtra("search_ad", true);
        intent.putExtra("type", type);
        intent.putExtra("location", location);
        startActivity(intent);
        finish();
    }

    public void goOffice(View view) throws ExecutionException, InterruptedException {
        location="";
        if(!advertisePresenter.haveAdsbyType("office")){
            Toast.makeText(FirstActivity.this,"No Ad matches to the given value",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, AdvertiseListActivity.class);
        intent.putExtra("logged_in", logged_in);
        intent.putExtra("id", member_id);
        intent.putExtra("user_ad", false);
        intent.putExtra("search_ad", true);
        intent.putExtra("type", "office");
        intent.putExtra("location", "");
        startActivity(intent);
        finish();
    }
    public void goHostel(View view) throws ExecutionException, InterruptedException {
        location="";
        if(!advertisePresenter.haveAdsbyType("hostel")){
            Toast.makeText(FirstActivity.this,"No Ad matches to the given value",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, AdvertiseListActivity.class);
        intent.putExtra("logged_in", logged_in);
        intent.putExtra("id", member_id);
        intent.putExtra("user_ad", false);
        intent.putExtra("search_ad", true);
        intent.putExtra("type", "hostel");
        intent.putExtra("location", "");
        startActivity(intent);
        finish();
    }
    public void goFlat(View view) throws ExecutionException, InterruptedException {
        location="";
        if(!advertisePresenter.haveAdsbyType("flat")){
            Toast.makeText(FirstActivity.this,"No Ad matches to the given value",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, AdvertiseListActivity.class);
        intent.putExtra("logged_in", logged_in);
        intent.putExtra("id", member_id);
        intent.putExtra("user_ad", false);
        intent.putExtra("search_ad", true);
        intent.putExtra("type", "flat");
        intent.putExtra("location", "");
        startActivity(intent);
        finish();
    }
    public void goRoommate(View view) throws ExecutionException, InterruptedException {
        location="";
        if(!advertisePresenter.haveAdsbyType("roommate")){
            Toast.makeText(FirstActivity.this,"No Ad matches to the given value",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, AdvertiseListActivity.class);
        intent.putExtra("logged_in", logged_in);
        intent.putExtra("id", member_id);
        intent.putExtra("user_ad", false);
        intent.putExtra("search_ad", true);
        intent.putExtra("type", "roommate");
        intent.putExtra("location", "");
        startActivity(intent);
        finish();
    }
    public void goSublet(View view) throws ExecutionException, InterruptedException {
        location="";
        if(!advertisePresenter.haveAdsbyType("sublet")){
            Toast.makeText(FirstActivity.this,"No Ad matches to the given value",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, AdvertiseListActivity.class);
        intent.putExtra("logged_in", logged_in);
        intent.putExtra("id", member_id);
        intent.putExtra("user_ad", false);
        intent.putExtra("search_ad", true);
        intent.putExtra("type", "sublet");
        intent.putExtra("location", "");
        startActivity(intent);
        finish();
    }
    public void goShop(View view) throws ExecutionException, InterruptedException {
        location="";
        if(!advertisePresenter.haveAdsbyType("shop")){
            Toast.makeText(FirstActivity.this,"No Ad matches to the given value",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, AdvertiseListActivity.class);
        intent.putExtra("logged_in", logged_in);
        intent.putExtra("id", member_id);
        intent.putExtra("user_ad", false);
        intent.putExtra("search_ad", true);
        intent.putExtra("type", "shop");
        intent.putExtra("location", "");
        startActivity(intent);
        finish();
    }


    public void notify(String body){
        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentTitle("Khojo").setContentText(body).
                setContentTitle("Khojo").
                setPriority(Notification.PRIORITY_HIGH).
                setVibrate(new long[0]).
                setAutoCancel(true).setSmallIcon(R.drawable.name).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);
    }
}