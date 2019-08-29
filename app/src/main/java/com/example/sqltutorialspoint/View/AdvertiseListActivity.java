package com.example.sqltutorialspoint.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sqltutorialspoint.Adapter.Advertise_listAdapter;
import com.example.sqltutorialspoint.Adapter.DropdownListAdapter;
import com.example.sqltutorialspoint.Adapter.SpinnerAdapter;
import com.example.sqltutorialspoint.Presenter.AdvertisePresenter;
import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.Presenter.ReportPresenter;
import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Utility.constants;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AdvertiseListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ListView l;

    ArrayList<String[]> ad_Text_data;
    ArrayList<Bitmap> ad_Image_data;
    ArrayList<Integer> types,places,prices;
    ArrayList<String> filter_types;
    ArrayList<String> filter_prices;
    ArrayList<String> filter_places;
    int pos,type_count,place_count,price_count;
    Advertise_listAdapter adapter;
    private int mInterval = 1500;
    MemberPresenter memberPresenter;
    ReportPresenter reportPresenter;
    private Handler mHandler;
    boolean ad=false;
    Button add_AD;
    LinearLayout addAdLayout;
    Bundle bundle;
    boolean logged_in,user_ad,search_ad;
    String member_id,location,type;
    boolean[] placeSelected,typeSelected,priceSelected;
    TextView sign_in;
    Spinner spinner;
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
        View v = inflator.inflate(R.layout.second_actionbar, null);
        getSupportActionBar().setCustomView(v, layout);
        memberPresenter=new MemberPresenter(this);
        reportPresenter=new ReportPresenter(this);
        sign_in = (TextView) v.findViewById(R.id.sign_in);
        l=(ListView)findViewById(R.id.advertiseList);
        add_AD=(Button)findViewById(R.id.addAd);
        addAdLayout=(LinearLayout)findViewById(R.id.addAdlayout);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        types=new ArrayList<>();
        places=new ArrayList<>();
        prices=new ArrayList<>();
        placeSelected=new boolean[constants.PLACES.length];
        typeSelected=new boolean[constants.CATEGORIES.length];
        priceSelected=new boolean[constants.PRICES.length];
        place_count=price_count=type_count=0;
        advertisePresenter=new AdvertisePresenter(this);
        bundle = getIntent().getExtras();
        logged_in=bundle.getBoolean("logged_in");
        user_ad=bundle.getBoolean("user_ad");
        search_ad=bundle.getBoolean("search_ad");

        if(search_ad){
            location = bundle.getString("location");
            type = bundle.getString("type");
        }
        if(logged_in) {
            member_id = bundle.getString("id");
            sign_in.setText("Sign out");
        }
        spinner.setOnItemSelectedListener(this);
        String[] spinnerItems={"    Location","     Price","     Type","       Filter by"};
        SpinnerAdapter spinneradapter=new SpinnerAdapter(this,spinnerItems,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);
        spinner.setSelection(spinnerItems.length-1);
        final ProgressDialog progressBar= new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        progressBar.dismiss();
                    }
                }, 3000);
        try {
            filter_prices=bundle.getStringArrayList("filter_prices");
            filter_types=bundle.getStringArrayList("filter_types");
            filter_places=bundle.getStringArrayList("filter_places");
            if(filter_places!=null) {
                ad_Text_data=advertisePresenter.getAdvertiseListTextsbyPlaces(filter_places.get(0),filter_places.get(1),filter_places.get(2));
                ad_Image_data=advertisePresenter.getImagesByPlaces(filter_places.get(0),filter_places.get(1),filter_places.get(2));
                adapter = new Advertise_listAdapter(AdvertiseListActivity.this, ad_Text_data, ad_Image_data, user_ad);

            }else if(filter_prices!=null) {
                ad_Text_data=advertisePresenter.getAdvertiseListTextsbyPrices(filter_prices.get(0),filter_prices.get(1));
                ad_Image_data=advertisePresenter.getImagesByPrices(filter_prices.get(0),filter_prices.get(1));
                adapter = new Advertise_listAdapter(AdvertiseListActivity.this, ad_Text_data, ad_Image_data, user_ad);

            }else if(filter_types!=null) {
                ad_Text_data=advertisePresenter.getAdvertiseListTextsbyTypes(filter_types.get(0),filter_types.get(1),filter_types.get(2));
                ad_Image_data=advertisePresenter.getImagesByTypes(filter_types.get(0),filter_types.get(1),filter_types.get(2));
                adapter = new Advertise_listAdapter(AdvertiseListActivity.this, ad_Text_data, ad_Image_data, user_ad);

            }else{
                if (user_ad) {
                    ad_Text_data = advertisePresenter.getAdvertiseListTexts2(member_id);
                    ad_Image_data = advertisePresenter.getAdvertiseImages2(member_id);
                    adapter = new Advertise_listAdapter(AdvertiseListActivity.this, ad_Text_data, ad_Image_data, user_ad);
                    //addAdLayout.setVisibility(View.GONE);
                } else if (search_ad) {
                    if (location.length() < 1) {
                        ad_Text_data = advertisePresenter.getAdvertiseListTextsbyType(type);
                        ad_Image_data = advertisePresenter.getImagesByType(type);
                        adapter = new Advertise_listAdapter(AdvertiseListActivity.this, ad_Text_data, ad_Image_data, user_ad);
                    } else {
                        ad_Text_data = advertisePresenter.getAdvertiseListTextsbyType_Location(type, location);
                        ad_Image_data = advertisePresenter.getImagesByType_Loc(type, location);
                        adapter = new Advertise_listAdapter(AdvertiseListActivity.this, ad_Text_data, ad_Image_data, user_ad);
                    }
                } else {
                    ad_Text_data = advertisePresenter.getAdvertiseListTexts();
                    ad_Image_data = advertisePresenter.getAdvertiseImages();
                    adapter = new Advertise_listAdapter(AdvertiseListActivity.this, ad_Text_data, ad_Image_data, user_ad);
                }
            }
            l.setAdapter(adapter);
            l.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if(user_ad) {
                        try {
                            final String ad_id=ad_Text_data.get(ad_Text_data.size()-position-1)[7];

                            if(ad_Text_data.get(ad_Text_data.size()-position-1)[6].equals("1")){
                                new AlertDialog.Builder(AdvertiseListActivity.this)
                                        .setMessage("This Ad. is deleted for following reason:\n-"
                                                +advertisePresenter.getReport_remark(ad_id)+"\n\n"
                                                +"Do you want to update this Ad?")

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(AdvertiseListActivity.this, UpdateAdvertiseActivity.class);
                                                intent.putExtra("id",member_id);
                                                intent.putExtra("ad_id",ad_id);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })

                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();

                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }else{
                                new AlertDialog.Builder(AdvertiseListActivity.this)
                                        .setMessage("Do you want to update this Ad?")

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(AdvertiseListActivity.this, UpdateAdvertiseActivity.class);
                                                intent.putExtra("id",member_id);
                                                intent.putExtra("ad_id",ad_id);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })

                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();

                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else{

                        String[] textData=ad_Text_data.get(ad_Text_data.size()-position-1);
                        final String ad_id=textData[5];
                        try {
                            advertisePresenter.updateClicked(ad_id);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent=new Intent(AdvertiseListActivity.this,AdvertiseDetailsActivity.class);
                        intent.putExtra("id",member_id);
                        intent.putExtra("logged_in",logged_in);
                        intent.putExtra("user_ad",user_ad);
//                        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
//                        ad_Image_data.get(ad_Image_data.size()-position-1).compress(Bitmap.CompressFormat.PNG, 100, bStream);
//                        byte[] byteArray = bStream.toByteArray();
//                        intent.putExtra("img",byteArray);
                        intent.putExtra("adData",textData);
                        startActivity(intent);

                    }
                }
            });
            l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final int pos=position;
                if(user_ad) {
                    new AlertDialog.Builder(AdvertiseListActivity.this)
                        .setMessage("Are you sure to Delete this Ad?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            String[] adIds= new String[0];
                            try {
                                adIds = advertisePresenter.getIDs(member_id);
                                String ad_id=adIds[adIds.length-pos-1];
                                advertisePresenter.deleteAd(ad_id);
                                Intent intent = new Intent(getApplicationContext(), AdvertiseListActivity.class);
                                intent.putExtra("logged_in", logged_in);
                                intent.putExtra("user_ad",true);
                                intent.putExtra("id",member_id);

                                startActivity(intent);
                                finish();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
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

                }else if(logged_in){
                    new AlertDialog.Builder(AdvertiseListActivity.this)
                        .setMessage("Are you sure to Report this Ad?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                                final AlertDialog.Builder alert = new AlertDialog.Builder(AdvertiseListActivity.this);
                                alert.setMessage("Say something about this Ad.");

                                final EditText input = new EditText(AdvertiseListActivity.this);
                                alert.setView(input);

                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog2, int whichButton) {
                                        try {
                                            String ad_id=ad_Text_data.get(ad_Text_data.size()-pos-1)[5];
                                            if(reportPresenter.insertAd_report(ad_id,member_id,input.getText().toString())) {
                                                Toast.makeText(AdvertiseListActivity.this, "Successfully Reported", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        }
                                });

                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog2, int whichButton) {
                                        dialog2.cancel();
                                    }
                                });

                                alert.show();

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                }
                return true;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mHandler = new Handler();
        startRepeatingTask();

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

    }@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_first, menu);
        MenuItem item=menu.getItem(1);
        if(logged_in)
            item.setVisible(true);
        else
            item.setVisible(false);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Dialog dialog = new Dialog(AdvertiseListActivity.this);
        dialog.setContentView(R.layout.filter_dropdown_list);
        ListView listView = (ListView) dialog.findViewById(R.id.dropdownList);

        if (i != 3) {

            if (i == 0) {
                DropdownListAdapter dropdownListAdapter = new DropdownListAdapter(this, constants.PLACES,placeSelected);
                listView.setAdapter(dropdownListAdapter);

            }else if (i == 1) {

                DropdownListAdapter dropdownListAdapter = new DropdownListAdapter(this, constants.PRICES,priceSelected);
                listView.setAdapter(dropdownListAdapter);

            }
            else if (i == 2) {
                DropdownListAdapter dropdownListAdapter = new DropdownListAdapter(this, constants.CATEGORIES,typeSelected);
                listView.setAdapter(dropdownListAdapter);

            }
            dialog.show();
            pos=i;

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout rl = view.findViewById(R.id.lout);
                if(pos==0){
                    if(!placeSelected[position]) {
                        if(place_count>=3)
                            Toast.makeText(AdvertiseListActivity.this,"Can't select more than 3 place",Toast.LENGTH_SHORT).show();
                        else {
                            placeSelected[position] = true;
                            place_count++;
                            rl.setBackgroundColor(Color.GRAY);
                        }
                    }else{
                        placeSelected[position] = false;
                        place_count--;
                        rl.setBackgroundColor(Color.WHITE);
                    }
                }else if(pos==1){
                    if(!priceSelected[position]) {
                        if(price_count>=1)
                            Toast.makeText(AdvertiseListActivity.this,"Can't select more than one option",Toast.LENGTH_SHORT).show();
                        else {
                            priceSelected[position] = true;
                            price_count++;
                            rl.setBackgroundColor(Color.GRAY);
                        }
                    }else{
                        priceSelected[position] = false;
                        price_count--;
                        rl.setBackgroundColor(Color.WHITE);
                    }
                }else if(pos==2){
                    if(!typeSelected[position]) {
                        if(type_count>=3)
                            Toast.makeText(AdvertiseListActivity.this,"Can't select more than 3 type",Toast.LENGTH_SHORT).show();
                        else {
                            typeSelected[position] = true;
                            type_count++;
                            rl.setBackgroundColor(Color.GRAY);
                        }
                    }else{
                        typeSelected[position] = false;
                        type_count--;
                        rl.setBackgroundColor(Color.WHITE);
                    }
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                            Toast.makeText(AdvertiseListActivity.this,"Signed out",Toast.LENGTH_LONG);
                            if(user_ad){
                                Intent intent = new Intent(AdvertiseListActivity.this,FirstActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Intent intent = new Intent(getApplicationContext(), AdvertiseListActivity.class);
                                intent.putExtra("logged_in", logged_in);
                                startActivity(intent);
                                finish();
                            }
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
    public void onBackPressed() {
        // disable going back to the MainActivity
        Intent intent=new Intent(this,FirstActivity.class);
        if(logged_in)
            intent.putExtra("id",member_id);
        startActivity(intent);
        finish();
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
                    add_AD.setText("ADVERTISE HERE");
                    //add_AD.setTextColor(Color.parseColor("#000000"));
                    ad=false;
                }else{
                    add_AD.setText("HAVE YOUR OWN PROPERTY?");
                    //add_AD.se+tTextColor(Color.parseColor("#ffffff"));
                    ad=true;
                }
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    public void addAnewAd(View view) {
        if(logged_in){
            Intent intent = new Intent(this, AddnewAdvertiseActivity.class);
            intent.putExtra("id",member_id);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("addAd",true);
            startActivityForResult(intent, constants.REQUEST_LOGIN);
            finish();
        }
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    public void setFilter(View view) throws ExecutionException, InterruptedException {
        ArrayList<String> types = new ArrayList<>();
        Intent intent = new Intent(getApplicationContext(), AdvertiseListActivity.class);
        if(pos==0) {
            for (int i = 0; i < placeSelected.length; i++) {
                if (placeSelected[i])
                    types.add(constants.PLACES[i].toLowerCase());
            }
            if (types.size() == 1) {
                types.add(types.get(0));
                types.add(types.get(0));
            } else if (types.size() == 2) {
                types.add(types.get(0));
            }
            intent.putExtra("filter_places",types);
        }else if(pos==1) {
            for (int i = 0; i < priceSelected.length; i++) {
                if (priceSelected[i]) {
                    types.add(constants.PRICE_BOUNDS[i][0]);
                    types.add(constants.PRICE_BOUNDS[i][1]);
                    break;
                }
            }
            intent.putExtra("filter_prices",types);

        }else if(pos==2) {
            for (int i = 0; i < typeSelected.length; i++) {
                if (typeSelected[i])
                    types.add(constants.CATEGORIES[i].toLowerCase());
            }
            if (types.size() == 1) {
                types.add(types.get(0));
                types.add(types.get(0));
            } else if (types.size() == 2) {
                types.add(types.get(0));
            }
            intent.putExtra("filter_types",types);
        }
        intent.putExtra("logged_in", logged_in);
        intent.putExtra("user_ad",user_ad);
        intent.putExtra("id",member_id);

        startActivity(intent);
        finish();





    }
}
