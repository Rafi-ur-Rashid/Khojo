package com.example.sqltutorialspoint.View;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sqltutorialspoint.Adapter.Advertise_listAdapter;
import com.example.sqltutorialspoint.Presenter.AdvertisePresenter;
import com.example.sqltutorialspoint.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AdvertiseListActivity extends AppCompatActivity {
    ListView l;
    ArrayList<String[]> data;
    Advertise_listAdapter adapter;
    AdvertisePresenter advertisePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        View v = inflator.inflate(R.layout.first_actionbar, null);
        getSupportActionBar().setCustomView(v, layout);
        l=(ListView)findViewById(R.id.advertiseList);
        advertisePresenter=new AdvertisePresenter(this);

        try {
            adapter=new Advertise_listAdapter(this,advertisePresenter.getAdvertiseListTexts(),advertisePresenter.getAdvertiseImages());
            l.setAdapter(adapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void addAnewAd(View view) {
    }
}
