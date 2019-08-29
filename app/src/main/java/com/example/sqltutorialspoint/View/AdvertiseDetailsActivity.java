package com.example.sqltutorialspoint.View;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sqltutorialspoint.Presenter.AdvertisePresenter;
import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Utility.constants;

import java.util.concurrent.ExecutionException;

public class AdvertiseDetailsActivity extends AppCompatActivity {
    Bundle bundle;
    LinearLayout[] feature_layout;
    ImageView[] feature_img;
    TextView[] feature_text;
    LinearLayout admin_layout;
    String member_id,ad_id,type,features,contact_no;
    boolean logged_in,user_ad,toggled,admin_tl,report;
    TextView sign_in;
    Bitmap[] bm;
    Button verifyAd,deleteAd;
    TextView title,price,location,address,spec1,spec2,desc;
    ImageView adImg;
    MemberPresenter memberPresenter;
    AdvertisePresenter advertisePresenter;
    String[] adData1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        View v = inflator.inflate(R.layout.first_actionbar, null);
        getSupportActionBar().setCustomView(v, layout);
        sign_in = (TextView) v.findViewById(R.id.sign_in);
        title=(TextView)findViewById(R.id.title);
        price=(TextView)findViewById(R.id.price);
        location=(TextView)findViewById(R.id.location);
        address=(TextView)findViewById(R.id.address);
        spec1=(TextView)findViewById(R.id.spec1);
        spec2=(TextView)findViewById(R.id.spec2);
        desc=(TextView)findViewById(R.id.desc);
        adImg=(ImageView)findViewById(R.id.adImg);
        verifyAd=(Button)findViewById(R.id.verifyAd);
        deleteAd=(Button)findViewById(R.id.deleteAd);
        feature_layout=new LinearLayout[4];
        feature_img=new ImageView[4];
        feature_text=new TextView[4];
        feature_layout[0]=(LinearLayout)findViewById(R.id.feature1_layout);
        feature_layout[1]=(LinearLayout)findViewById(R.id.feature2_layout);
        feature_layout[2]=(LinearLayout)findViewById(R.id.feature3_layout);
        feature_layout[3]=(LinearLayout)findViewById(R.id.feature4_layout);
        feature_img[0]=(ImageView)findViewById(R.id.feature1_img);
        feature_img[1]=(ImageView)findViewById(R.id.feature2_img);
        feature_img[2]=(ImageView)findViewById(R.id.feature3_img);
        feature_img[3]=(ImageView)findViewById(R.id.feature4_img);
        feature_text[0]=(TextView)findViewById(R.id.feature1);
        feature_text[1]=(TextView)findViewById(R.id.feature2);
        feature_text[2]=(TextView)findViewById(R.id.feature3);
        feature_text[3]=(TextView)findViewById(R.id.feature4);
        admin_layout=(LinearLayout)findViewById(R.id.adminLayout);
        memberPresenter=new MemberPresenter(this);
        advertisePresenter=new AdvertisePresenter(this);
        bundle = getIntent().getExtras();
        logged_in=bundle.getBoolean("logged_in");
        toggled=bundle.getBoolean("toggled");
        user_ad=bundle.getBoolean("user_ad");
        admin_tl=bundle.getBoolean("admin_tl");
        report=bundle.getBoolean("report");
        bm=new Bitmap[4];
        adData1= (String[]) bundle.getCharSequenceArray("adData");
        ad_id=adData1[5];
        if(admin_tl)
            admin_layout.setVisibility(View.VISIBLE);
        if(logged_in){
            member_id = bundle.getString("id");
            sign_in.setText("Sign out");
        }
        if(report) {
            admin_layout.setVisibility(View.VISIBLE);
            verifyAd.setVisibility(View.GONE);
            deleteAd.setVisibility(View.VISIBLE);
        }
        title.setText(adData1[0].toUpperCase());
        price.setText("৳"+adData1[2]);
        location.setText(adData1[3]);
        type=adData1[1];
        //byte[] byteArray = getIntent().getByteArrayExtra("img");


        final ProgressDialog progressBar= new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        try {
                            bm[0] = advertisePresenter.getImage(ad_id);//BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            adImg.setImageBitmap(bm[0]);
                            address.setText(advertisePresenter.getAddress(ad_id));
                            contact_no=advertisePresenter.getContact(ad_id);
                            features=advertisePresenter.getFeature(ad_id);
                            desc.setText(advertisePresenter.getDescription(ad_id));
                            if(type.equalsIgnoreCase("hostel")) {
                                String sex = advertisePresenter.getHostelType(ad_id);
                                String seatNo = advertisePresenter.getHostelSeatNo(ad_id);
                                    if (sex.equalsIgnoreCase("male"))
                                        spec1.setText("Hostel for Male");
                                    else
                                        spec1.setText("Hostel for Female");
                                spec2.setText(seatNo + " seats available");
                            }
                            else if(type.equalsIgnoreCase("roommate")) {
                                String sex = advertisePresenter.getRoommateType(ad_id);
                                String seatNo = advertisePresenter.getRoommateSeatNo(ad_id);
                                if (sex.equalsIgnoreCase("male"))
                                    spec1.setText("Gender: Male");
                                else
                                    spec1.setText("Gender: Female");
                                spec2.setText(seatNo + " seats available");
                            }
                            if(type.equalsIgnoreCase("hostel") || type.equalsIgnoreCase("roommate")){
                                if(features.charAt(0)=='1')
                                {
                                    feature_layout[0].setVisibility(View.VISIBLE);
                                    feature_img[0].setImageResource(R.mipmap.wifi_supply);
                                    feature_text[0].setText("Wifi");
                                }
                                else
                                {
                                    feature_layout[0].setVisibility(View.GONE);
                                }
                                if(features.charAt(1)=='1')
                                {
                                    feature_layout[1].setVisibility(View.VISIBLE);
                                    feature_img[1].setImageResource(R.mipmap.dining);
                                    feature_text[1].setText("Dining");
                                }
                                else
                                {
                                    feature_layout[1].setVisibility(View.GONE);
                                }
                                if(features.charAt(2)=='1')
                                {
                                    feature_layout[2].setVisibility(View.VISIBLE);
                                    feature_img[2].setImageResource(R.mipmap.balcony);
                                    feature_text[2].setText("Balcony");
                                }
                                else
                                {
                                    feature_layout[2].setVisibility(View.GONE);
                                }
                                if(features.charAt(3)=='1')
                                {
                                    feature_layout[3].setVisibility(View.VISIBLE);
                                    feature_img[3].setImageResource(R.mipmap.sweeper);
                                    feature_text[3].setText("Sweeper");
                                }
                                else
                                {
                                    feature_layout[3].setVisibility(View.GONE);
                                }
                            }else if(type.equalsIgnoreCase("flat")){
                                String roomNo=advertisePresenter.getFlatRoomNo(ad_id);
                                String size=advertisePresenter.getFlatSpace(ad_id);
                                spec1.setText(roomNo+" rooms");
                                spec2.setText("Space: "+size+" sq ft");
                                if(features.charAt(0)=='1')
                                {
                                    feature_layout[0].setVisibility(View.VISIBLE);
                                    feature_img[0].setImageResource(R.mipmap.gas_supply);
                                    feature_text[0].setText("Gas");
                                }
                                else
                                {
                                    feature_layout[0].setVisibility(View.GONE);
                                }
                                if(features.charAt(1)=='1')
                                {
                                    feature_layout[1].setVisibility(View.VISIBLE);
                                    feature_img[1].setImageResource(R.mipmap.garage);
                                    feature_text[1].setText("Garage");
                                }
                                else
                                {
                                    feature_layout[1].setVisibility(View.GONE);
                                }
                                if(features.charAt(2)=='1')
                                {
                                    feature_layout[2].setVisibility(View.VISIBLE);
                                    feature_img[2].setImageResource(R.mipmap.balcony);
                                    feature_text[2].setText("Balcony");
                                }
                                else
                                {
                                    feature_layout[2].setVisibility(View.GONE);
                                }
                                if(features.charAt(3)=='1')
                                {
                                    feature_layout[3].setVisibility(View.VISIBLE);
                                    feature_img[3].setImageResource(R.mipmap.exit);
                                    feature_text[3].setText("Fire Exit");
                                }
                                else
                                {
                                    feature_layout[3].setVisibility(View.GONE);
                                }
                            }
//                            type_hostel=advertisePresenter.getHostelType(ad_id);
//                            seat_no=advertisePresenter.getHostelSeatNo(ad_id);
//                            room_no=advertisePresenter.getFlatRoomNo(ad_id);
//                            flat_space=advertisePresenter.getFlatSpace(ad_id);
//                            desc=advertisePresenter.getDescription(ad_id);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        progressBar.dismiss();
                    }
                }, 1000);
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
                            Toast.makeText(AdvertiseDetailsActivity.this,"Signed out",Toast.LENGTH_LONG);
                            Intent intent = new Intent(getApplicationContext(), AdvertiseDetailsActivity.class);
                            intent.putExtra("id",member_id);
                            intent.putExtra("logged_in",logged_in);
                            intent.putExtra("user_ad",user_ad);
                            intent.putExtra("toggled",toggled);

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
    public void onBackPressed(){
        if(!toggled)
            finish();
        else {
            Intent intent = new Intent(AdvertiseDetailsActivity.this, FirstActivity.class);
            intent.putExtra("id", member_id);
            intent.putExtra("logged_in", logged_in);
            intent.putExtra("user_ad", user_ad);
            intent.putExtra("search_ad", false);
            startActivity(intent);
            finish();
        }
    }

    public void showContact(View view) {
        new AlertDialog.Builder(AdvertiseDetailsActivity.this)
                .setMessage(contact_no+"\n\nWant to call now?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        makeCall(contact_no);
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
    public void makeCall(String contact){
        if (ContextCompat.checkSelfPermission(AdvertiseDetailsActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(AdvertiseDetailsActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    constants.MY_PERMISSIONS_REQUEST_PHONE_CALL);


        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+contact));
            startActivity(callIntent);
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case constants.MY_PERMISSIONS_REQUEST_PHONE_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+contact_no));
                    startActivity(callIntent);

                } else {
                    return;
                }
                return;
            }

        }
    }

    public void verifyAd(View view){
        final ProgressDialog progressBar= new ProgressDialog(AdvertiseDetailsActivity.this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        try {
                            if(advertisePresenter.updateVerified(ad_id)) {
                            Toast.makeText(AdvertiseDetailsActivity.this, "Verified", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(AdvertiseDetailsActivity.this,AdminTimelineActivity.class);
                            startActivity(intent);
                            finish();
                            }else{
                                Toast.makeText(AdvertiseDetailsActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }, 1500);
        progressBar.dismiss();
    }
    public void deleteAd(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(AdvertiseDetailsActivity.this);
        alert.setMessage("Say something about this Ad.");

        final EditText input = new EditText(AdvertiseDetailsActivity.this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog2, int whichButton) {
                try {

                    if(advertisePresenter.updateReported(ad_id)) {
                        final ProgressDialog progressBar= new ProgressDialog(AdvertiseDetailsActivity.this);
                        progressBar.setCancelable(true);
                        progressBar.setMessage("Please wait...");
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.setProgress(0);
                        progressBar.setMax(100);
                        progressBar.show();

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        String remark=input.getText().toString();
                                        if(remark.length()<1)
                                            remark="Objectionable Content";
                                        try {
                                            advertisePresenter.updateReport_remark(ad_id,remark);
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 2000);
                        progressBar.dismiss();
                        Toast.makeText(AdvertiseDetailsActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(AdvertiseDetailsActivity.this,AdminTimelineActivity.class);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        Toast.makeText(AdvertiseDetailsActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
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
}
