package com.example.sqltutorialspoint.View;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.sqltutorialspoint.Fragments.ReportFragment;
import com.example.sqltutorialspoint.Fragments.AdvertiseFragment;
import com.example.sqltutorialspoint.Presenter.AdvertisePresenter;
import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Utility.constants;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AdminTimelineActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int mInterval = 1000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    MemberPresenter memberPresenter;
    AdvertisePresenter advertisePresenter;
    ImageView ad_image;
    TextView sign_in;
    AutoCompleteTextView input_loc;
    Spinner spinner;
    boolean ad,logged_in=false;
    String member_id,location,type;
    Bundle bundle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_timeline);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        View v = inflator.inflate(R.layout.first_actionbar, null);
        getSupportActionBar().setCustomView(v, layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReportFragment(), "REPORTS");
        adapter.addFragment(new AdvertiseFragment(), "ADVERTISES");
        viewPager.setAdapter(adapter);
    }

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
        finish();
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
                            Toast.makeText(AdminTimelineActivity.this,"Signed out",Toast.LENGTH_LONG);
                            Intent intent = new Intent(getApplicationContext(), AdminTimelineActivity.class);
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

        if(logged_in) {
            item2.setVisible(true);
            item3.setVisible(true);
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
            Intent intent = new Intent(AdminTimelineActivity.this, MemberProfileAcivity.class);
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
                    Toast.makeText(AdminTimelineActivity.this,"You haven't published any Ad yet",Toast.LENGTH_LONG).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type = adapterView.getItemAtPosition(i).toString().toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}