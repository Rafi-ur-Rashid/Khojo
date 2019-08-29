package com.example.sqltutorialspoint.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.sqltutorialspoint.Adapter.Category_and_locationAdapter;
import com.example.sqltutorialspoint.Presenter.AdvertisePresenter;
import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Utility.util_functions;
import com.google.android.material.snackbar.Snackbar;
import com.shuhart.stepview.StepView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.sqltutorialspoint.Utility.constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
import static com.example.sqltutorialspoint.Utility.constants.REQUEST_CAMERA;
import static com.example.sqltutorialspoint.Utility.constants.SELECT_FILE;

public class AddnewAdvertiseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    StepView sv;
    EditText input_heading,input_price,input_address,input_contact_no;
    AutoCompleteTextView input_location;
    CoordinatorLayout coordinatorLayout;
    TextView hostel_type,no_seat,flat_size,flat_room_no;
    EditText input_no_seat,input_flat_size,input_flat_room_no,input_desc1,input_desc2;
    String type_hostel,seat_no,flat_space,room_no,desc;
    CheckBox male,female,checkWifi,checkGas,checkBalcony,checkDining,checkSweeper,checkGarage,checkExit;
    int currentStep;
    String userChoosenTask;
    Bundle bundle;
    Bitmap[] bm;
    int current_selected_image;
    AdvertisePresenter advertisePresenter;
    LinearLayout layout1,layout2,layout3,layout4;
    ImageButton currentImage;
    String member_id;
    String title,ad_type,price,address,location,contact_no,features;
    String[] img_dir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_advertise);
        getSupportActionBar().hide();
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorAddAd);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout4 = (LinearLayout) findViewById(R.id.layout4);
        sv=(StepView)findViewById(R.id.step_view);
        bundle = getIntent().getExtras();
        member_id=bundle.getString("id");
        List<String> l=new ArrayList<String>(){{add("Step 1");add("Step 2");add("Step 3");}};
        sv.setSteps(l);
        bm=new Bitmap[4];
        img_dir=new String[4];
        advertisePresenter=new AdvertisePresenter(this);
        currentStep=0;
        sv.go(0,true);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        input_heading=(EditText)findViewById(R.id.input_heading);
        input_address=(EditText)findViewById(R.id.input_address);
        input_price=(EditText)findViewById(R.id.input_price);
        input_location=(AutoCompleteTextView) findViewById(R.id.input_location);
        input_contact_no=(EditText)findViewById(R.id.input_contact);
        input_flat_room_no=(EditText)findViewById(R.id.input_room_no);
        input_flat_size=(EditText)findViewById(R.id.input_flat_size);
        input_no_seat=(EditText)findViewById(R.id.input_seat_no);
        input_desc1=(EditText)findViewById(R.id.input_description);
        input_desc2=(EditText)findViewById(R.id.input_description2);

        hostel_type=(TextView)findViewById(R.id.hostel_type);
        no_seat=(TextView)findViewById(R.id.no_seat);
        flat_size=(TextView)findViewById(R.id.flat_size);
        flat_room_no=(TextView)findViewById(R.id.no_room);
        male=(CheckBox)findViewById(R.id.checkMale);
        female=(CheckBox)findViewById(R.id.checkfeMale);
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(male.isChecked()){
                    female.setChecked(false);
                }else
                    female.setChecked(true);
            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(female.isChecked()){
                    male.setChecked(false);
                }else
                    male.setChecked(true);
            }
        });
        Category_and_locationAdapter adapter=new Category_and_locationAdapter(this);
        spinner.setAdapter(adapter.getCategoryAdapter());
        input_location.setThreshold(1);
        input_location.setAdapter(adapter.getLocationAdapter());

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity

        if(currentStep==0) {
            Intent intent = new Intent(this, FirstActivity.class);
            intent.putExtra("id", member_id);
            startActivity(intent);
            finish();
        }else if(currentStep==1){
            currentStep--;
            sv.go(currentStep,true);
            layout2.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);
        }else{
            currentStep--;
            sv.go(currentStep,true);
            if(ad_type.equalsIgnoreCase("hostel"))
                layout3.setVisibility(View.GONE);
            else if(ad_type.equalsIgnoreCase("flat"))
                layout4.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ad_type = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        ad_type = adapterView.getItemAtPosition(0).toString();
    }

    public void goSecondStep(View view) {
        currentStep++;
        sv.go(currentStep, true);
        title = input_heading.getText().toString();
        price = input_price.getText().toString();
        location = input_location.getText().toString();
        address = input_address.getText().toString();
        contact_no = input_contact_no.getText().toString();
        if (title.length()<1 || location.length()<1 || price.length()<1 || address.length()<1 || contact_no.length()<1) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No Field Should be Left Empty", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
        }
    }

    public void goBackFirstStep(View view) {
        currentStep--;
        sv.go(currentStep,true);
        layout2.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
    }
    private void selectImage() {
        final CharSequence[] items = { "From Camera", "from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddnewAdvertiseActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= util_functions.checkPermission(AddnewAdvertiseActivity.this);
                if (items[item].equals("From Camera")) {
                    userChoosenTask="From Camera";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("from Gallery")) {
                    userChoosenTask="from Gallery";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        int i=current_selected_image-1;
        if (data != null) {
            try {
                bm[i] = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        currentImage.setImageBitmap(bm[i]);
        currentImage.setScaleType(ImageView.ScaleType.FIT_XY);
    }
    private void onCaptureImageResult(Intent data) {
        int i=current_selected_image-1;
        bm[i] = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm[i].compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentImage.setImageBitmap(bm[i]);
        currentImage.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public void add1stImage(View view) {
        currentImage=(ImageButton)view;
        current_selected_image=1;
        selectImage();
    }

    public void add2ndImage(View view) {
        currentImage=(ImageButton)view;
        current_selected_image=2;
        selectImage();
    }

    public void add3rdImage(View view) {
        currentImage=(ImageButton)view;
        current_selected_image=3;
        selectImage();
    }

    public void add4thImage(View view) {
        currentImage=(ImageButton)view;
        current_selected_image=4;
        selectImage();
    }

    public void goThirdStep(View view) {
        features="";
        if (bm[0] == null) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Minimum One Picture is Required", Snackbar.LENGTH_LONG);

            snackbar.show();
        } else {
            currentStep++;
            sv.go(currentStep, true);
            layout2.setVisibility(View.GONE);
            if (ad_type.equalsIgnoreCase("hostel")) {
                checkWifi=(CheckBox)findViewById(R.id.checkWifi);
                checkBalcony=(CheckBox)findViewById(R.id.checkBalcony);
                checkDining=(CheckBox)findViewById(R.id.checkDining);
                checkSweeper=(CheckBox)findViewById(R.id.checkSweeper);

                hostel_type.setText(Html.fromHtml(getString(R.string.hostel_type)));
                no_seat.setText(Html.fromHtml(getString(R.string.seat_no)));
                layout3.setVisibility(View.VISIBLE);
            } else if (ad_type.equalsIgnoreCase("flat")) {
                checkGas=(CheckBox)findViewById(R.id.checkGas);
                checkBalcony=(CheckBox)findViewById(R.id.checkBalcony2);
                checkGarage=(CheckBox)findViewById(R.id.checkGarage);
                checkExit=(CheckBox)findViewById(R.id.checkExit);
                flat_size.setText(getString(R.string.flat_size));
                flat_room_no.setText(Html.fromHtml(getString(R.string.flat_room_no)));
                layout4.setVisibility(View.VISIBLE);
            } else if (ad_type.equalsIgnoreCase("sublet")) {
                checkGas=(CheckBox)findViewById(R.id.checkGas);
                checkBalcony=(CheckBox)findViewById(R.id.checkBalcony2);
                checkGarage=(CheckBox)findViewById(R.id.checkGarage);
                checkExit=(CheckBox)findViewById(R.id.checkExit);
                layout4.setVisibility(View.VISIBLE);
            }else if (ad_type.equalsIgnoreCase("roommate")) {
                checkWifi=(CheckBox)findViewById(R.id.checkWifi);
                checkBalcony=(CheckBox)findViewById(R.id.checkBalcony);
                checkDining=(CheckBox)findViewById(R.id.checkDining);
                checkSweeper=(CheckBox)findViewById(R.id.checkSweeper);
                hostel_type.setText(Html.fromHtml(getString(R.string.roommate_gender)));
                no_seat.setText(Html.fromHtml(getString(R.string.seat_no)));
                layout3.setVisibility(View.VISIBLE);
            }


        }
    }

    public void goBackSecondStep(View view) {
        currentStep--;
        sv.go(currentStep,true);
        if(ad_type.equalsIgnoreCase("hostel"))
            layout3.setVisibility(View.GONE);
        else if(ad_type.equalsIgnoreCase("flat"))
            layout4.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
    }

    public void publishAd(View view) {
        if(ad_type.equalsIgnoreCase("hostel") || ad_type.equalsIgnoreCase("roommate")){
            seat_no=input_no_seat.getText().toString();
            if(male.isChecked())
                type_hostel="male";
            else
                type_hostel="female";
            desc=input_desc1.getText().toString();
            if(checkWifi.isChecked())
                features=features+"1";
            else
                features=features+"0";
            if(checkDining.isChecked())
                features=features+"1";
            else
                features=features+"0";
            if(checkBalcony.isChecked())
                features=features+"1";
            else
                features=features+"0";
            if(checkSweeper.isChecked())
                features=features+"1";
            else
                features=features+"0";
        }
        if(ad_type.equalsIgnoreCase("flat")){
            room_no=input_flat_room_no.getText().toString();
            flat_space=input_flat_size.getText().toString();
            desc=input_desc2.getText().toString();
            if(checkGas.isChecked())
                features=features+"1";
            else
                features=features+"0";
            if(checkGarage.isChecked())
                features=features+"1";
            else
                features=features+"0";
            if(checkBalcony.isChecked())
                features=features+"1";
            else
                features=features+"0";
            if(checkExit.isChecked())
                features=features+"1";
            else
                features=features+"0";
        }
        new androidx.appcompat.app.AlertDialog.Builder(AddnewAdvertiseActivity.this)
                .setMessage("Are you sure to pubish this Ad?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        final String curr_time=System.currentTimeMillis()+"";
                        final ProgressDialog progressBar= new ProgressDialog(AddnewAdvertiseActivity.this);
                        progressBar.setCancelable(true);
                        progressBar.setMessage("Please wait...");
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.setProgress(0);
                        progressBar.setMax(100);
                        progressBar.show();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    for (int i=0;i<bm.length;i++) {
//                                        if (bm[i] != null) {
//                                            img_dir[i]="C:/xampp/htdocs/upload/"+curr_time+"_"+(i+1)+".png";
//                                            advertisePresenter.uploadImage(bm[i], curr_time + "_"+(i+1)+".png");
//
//                                        }
                                    }
                                    if (bm[0] != null) {
                                        img_dir[0] = "C:/xampp/htdocs/upload/" + curr_time + "_1.png";
                                        if(advertisePresenter.insertAdvertise(bm[0], curr_time + "_1.png", title, member_id, location, ad_type, price, address,contact_no,desc)){
                                            String ad_Id=advertisePresenter.getMaxId(ad_type.toLowerCase());
                                            advertisePresenter.updateFeatures(ad_Id,features);
                                            if(ad_type.equalsIgnoreCase("hostel"))
                                            {
                                                advertisePresenter.updateHostelSeatNo(ad_Id,seat_no);
                                                advertisePresenter.updateHostelType(ad_Id,type_hostel);
                                            }else if(ad_type.equalsIgnoreCase("flat"))
                                            {
                                                advertisePresenter.updateFlatRoomNo(ad_Id,room_no);
                                                advertisePresenter.updateFlatSpace(ad_Id,flat_space);
                                            }else if(ad_type.equalsIgnoreCase("roommate"))
                                            {
                                                advertisePresenter.updateRoommateNo(ad_Id,seat_no);
                                                advertisePresenter.updateRoommateGender(ad_Id,type_hostel);
                                            }
                                            Toast.makeText(AddnewAdvertiseActivity.this, "Successfully Submitted", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(AddnewAdvertiseActivity.this, FirstActivity.class);
                                            intent.putExtra("id", member_id);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(AddnewAdvertiseActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                progressBar.dismiss();
                                    }

                        }, 4000);
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
}
