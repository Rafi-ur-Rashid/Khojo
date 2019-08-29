package com.example.sqltutorialspoint.Model;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.sqltutorialspoint.Service.dbConnectionForAdvertiseInsert;
import com.example.sqltutorialspoint.Service.dbConnectionForImageData;
import com.example.sqltutorialspoint.Service.dbConnectionForTextData;

import java.util.concurrent.ExecutionException;

import static com.example.sqltutorialspoint.Utility.constants.AD_ADDRESS_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_CONTACT_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_DATES_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_DATE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_DATE_BY_PLACES;
import static com.example.sqltutorialspoint.Utility.constants.AD_DATE_BY_PRICES;
import static com.example.sqltutorialspoint.Utility.constants.AD_DATE_BY_TYPE;
import static com.example.sqltutorialspoint.Utility.constants.AD_DATE_BY_TYPES;
import static com.example.sqltutorialspoint.Utility.constants.AD_DATE_BY_TYPE_LOC;
import static com.example.sqltutorialspoint.Utility.constants.AD_DELETED_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_DESC_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_IDS_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_ID_BY_PLACES;
import static com.example.sqltutorialspoint.Utility.constants.AD_ID_BY_PRICES;
import static com.example.sqltutorialspoint.Utility.constants.AD_ID_BY_TYPE;
import static com.example.sqltutorialspoint.Utility.constants.AD_ID_BY_TYPES;
import static com.example.sqltutorialspoint.Utility.constants.AD_ID_BY_TYPE_LOC;
import static com.example.sqltutorialspoint.Utility.constants.AD_IMAGE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_LOCATIONS_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_LOCATION_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_LOCATION_BY_PRICES;
import static com.example.sqltutorialspoint.Utility.constants.AD_LOCATION_BY_TYPE;
import static com.example.sqltutorialspoint.Utility.constants.AD_LOCATION_BY_TYPES;
import static com.example.sqltutorialspoint.Utility.constants.AD_LOCATION_BY_TYPE_LOC;
import static com.example.sqltutorialspoint.Utility.constants.AD_NAMES_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_NAME_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_NAME_BY_PLACES;
import static com.example.sqltutorialspoint.Utility.constants.AD_NAME_BY_PRICES;
import static com.example.sqltutorialspoint.Utility.constants.AD_NAME_BY_TYPE;
import static com.example.sqltutorialspoint.Utility.constants.AD_NAME_BY_TYPES;
import static com.example.sqltutorialspoint.Utility.constants.AD_NAME_BY_TYPE_LOC;
import static com.example.sqltutorialspoint.Utility.constants.AD_PRICES_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_PRICE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_PRICE_BY_PLACES;
import static com.example.sqltutorialspoint.Utility.constants.AD_PRICE_BY_TYPE;
import static com.example.sqltutorialspoint.Utility.constants.AD_PRICE_BY_TYPES;
import static com.example.sqltutorialspoint.Utility.constants.AD_PRICE_BY_TYPE_LOC;
import static com.example.sqltutorialspoint.Utility.constants.AD_REPORT_REMARK_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_TYPES_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_TYPE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.AD_TYPE_BY_PLACES;
import static com.example.sqltutorialspoint.Utility.constants.AD_TYPE_BY_PRICES;
import static com.example.sqltutorialspoint.Utility.constants.AD_VERIFIED_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.DELETE_AD_BY_ADID;
import static com.example.sqltutorialspoint.Utility.constants.FEATURES_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.FLAT_ROOMNO_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.FLAT_SPACE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.GET_ADID_NON_NOTIFIED_DELETED;
import static com.example.sqltutorialspoint.Utility.constants.GET_ADID_NON_NOTIFIED_VERIFIED;
import static com.example.sqltutorialspoint.Utility.constants.GET_AD_DATE_UNVERIFIED;
import static com.example.sqltutorialspoint.Utility.constants.GET_AD_ID_UNVERIFIED;
import static com.example.sqltutorialspoint.Utility.constants.GET_AD_MEMBERID_UNVERIFIED;
import static com.example.sqltutorialspoint.Utility.constants.GET_AD_MODIFIED_UNVERIFIED;
import static com.example.sqltutorialspoint.Utility.constants.GET_ALL_AD_DATE;
import static com.example.sqltutorialspoint.Utility.constants.GET_ALL_AD_ID;
import static com.example.sqltutorialspoint.Utility.constants.GET_ALL_AD_LOCATION;
import static com.example.sqltutorialspoint.Utility.constants.GET_ALL_AD_NAME;
import static com.example.sqltutorialspoint.Utility.constants.GET_ALL_AD_PRICE;
import static com.example.sqltutorialspoint.Utility.constants.GET_ALL_AD_TYPE;
import static com.example.sqltutorialspoint.Utility.constants.GET_MAX_ID;
import static com.example.sqltutorialspoint.Utility.constants.HOSTEL_SEATNO_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.HOSTEL_TYPE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.ROOMMATE_NO_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.ROOMMATE_TYPE_BY_ID;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_CLICKED;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_FEATURES;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_FLAT_ROOM_NO;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_FLAT_SPACE;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_HOSTEL_SEAT_NO;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_HOSTEL_TYPE;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_MODIFIED;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_NOTIFIED;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_NOTIFIED_BY_MEMBERID;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_REPORTED;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_REPORT_REMARK;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_ROOMMATE_GENDER;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_ROOMMATE_NO;
import static com.example.sqltutorialspoint.Utility.constants.UPDATE_VERIFIED;

public class Advertisement {
    Context context;

    dbConnectionForTextData dbcon;
    dbConnectionForImageData dbcon2;
    dbConnectionForAdvertiseInsert dbcon3;
    public Advertisement(Context context) {
        this.context = context;
        dbcon=new dbConnectionForTextData(context);
    }

    public boolean insertAdvertise(Bitmap imgData,String imgName,String title,String member_id,String location,String type,String price,String address,String contact_no,String desc) throws ExecutionException, InterruptedException {
        dbcon3=new dbConnectionForAdvertiseInsert();
        String s= (String)dbcon3.execute(imgData,imgName,title,member_id,location,type,price,address,contact_no,desc).get();
        return s.equals("success");
    }

    public String[] getAllId() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_ALL_AD_ID).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllIdUnverified() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_AD_ID_UNVERIFIED).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllName() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_ALL_AD_NAME).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllType() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_ALL_AD_TYPE).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllLocation() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_ALL_AD_LOCATION).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllPrice() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_ALL_AD_PRICE).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllDate() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_ALL_AD_DATE).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllDateUnverified() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_AD_DATE_UNVERIFIED).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllModifiedUnverified() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_AD_MODIFIED_UNVERIFIED).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getIDs(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_IDS_BY_ID+"?member_id="+id).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }

    public String[] getNames(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_NAMES_BY_ID+"?member_id="+id).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getTypes(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_TYPES_BY_ID+"?member_id="+id).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getLocations(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_LOCATIONS_BY_ID+"?member_id="+id).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getPrices(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_PRICES_BY_ID+"?member_id="+id).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getDates(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_DATES_BY_ID+"?member_id="+id).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getVerifieds(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_VERIFIED_BY_ID+"?member_id="+id).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getDeleteds(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_DELETED_BY_ID+"?member_id="+id).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String getName(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_NAME_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getType(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_TYPE_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getLocation(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_LOCATION_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getAddress(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_ADDRESS_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getPrice(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_PRICE_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getDate(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_DATE_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getContact(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_CONTACT_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public Bitmap getImage(String id) throws ExecutionException, InterruptedException {
        dbcon2=new dbConnectionForImageData(context);
        Bitmap bitmap= (Bitmap) dbcon2.execute("advertisement/"+AD_IMAGE_BY_ID+"?adId="+id).get();
        //dbcon2.cancel(true);
        return bitmap;
    }
    public String getFeature(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+FEATURES_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getReport_remark(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_REPORT_REMARK_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public boolean updateHostelSeatNo(String id,String seatNo) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_HOSTEL_SEAT_NO+"?adID="+id+"&seat_no="+seatNo).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateHostelType(String id,String type) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_HOSTEL_TYPE+"?adID="+id+"&type="+type).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateFlatRoomNo(String id,String roomNo) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_FLAT_ROOM_NO+"?adID="+id+"&room_no="+roomNo).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateFlatSpace(String id,String space) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_FLAT_SPACE+"?adID="+id+"&space="+space).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateRoommateNo(String id,String seatNo) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_ROOMMATE_NO+"?adID="+id+"&seat_no="+seatNo).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateRoommateGender(String id,String type) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_ROOMMATE_GENDER+"?adID="+id+"&type="+type).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateFeature(String id,String feature) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_FEATURES+"?adID="+id+"&features="+feature).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateModified(String id,String mod) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_MODIFIED+"?adID="+id+"&mod="+mod).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateClicked(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_CLICKED+"?adID="+id).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public String getMaxId(String table) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+GET_MAX_ID+"?table="+table).get();
        //dbcon.cancel(true);
        return s;
    }
    public boolean deleteAd(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+DELETE_AD_BY_ADID+"?adID="+id).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public String getHostelSeatNo(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+HOSTEL_SEATNO_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getHostelType(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+HOSTEL_TYPE_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getFlatRoomNo(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+FLAT_ROOMNO_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getFlatSpace(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+FLAT_SPACE_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getRoommateNo(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+ROOMMATE_NO_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getRoommateType(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+ROOMMATE_TYPE_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String getDescription(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+AD_DESC_BY_ID+"?adId="+id).get();
        //dbcon.cancel(true);
        return s;
    }
    public String[] getIDbyType(String type) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_ID_BY_TYPE+"?Type="+type).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getTitlebyType(String type) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_NAME_BY_TYPE+"?Type="+type).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getLocationbyType(String type) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_LOCATION_BY_TYPE+"?Type="+type).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getPricebyType(String type) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_PRICE_BY_TYPE+"?Type="+type).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getDatebyType(String type) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_DATE_BY_TYPE+"?Type="+type).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getIDbyTypes(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_ID_BY_TYPES+"?Type1="+type1+"&Type2="+type2+"&Type3="+type3).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getTitlebyTypes(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_NAME_BY_TYPES+"?Type1="+type1+"&Type2="+type2+"&Type3="+type3).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getLocationbyTypes(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_LOCATION_BY_TYPES+"?Type1="+type1+"&Type2="+type2+"&Type3="+type3).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getPricebyTypes(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_PRICE_BY_TYPES+"?Type1="+type1+"&Type2="+type2+"&Type3="+type3).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getDatebyTypes(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_DATE_BY_TYPES+"?Type1="+type1+"&Type2="+type2+"&Type3="+type3).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getIDbyPrices(String type1,String type2) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_ID_BY_PRICES+"?Type1="+type1+"&Type2="+type2).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getTitlebyPrices(String type1,String type2) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_NAME_BY_PRICES+"?Type1="+type1+"&Type2="+type2).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getLocationbyPrices(String type1,String type2) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_LOCATION_BY_PRICES+"?Type1="+type1+"&Type2="+type2).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getDatebyPrices(String type1,String type2) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_DATE_BY_PRICES+"?Type1="+type1+"&Type2="+type2).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getTypebyPrices(String type1,String type2) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_TYPE_BY_PRICES+"?Type1="+type1+"&Type2="+type2).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getIDbyPlaces(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_ID_BY_PLACES+"?Type1="+type1+"&Type2="+type2+"&Type3="+type3).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getTitlebyPlaces(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_NAME_BY_PLACES+"?Type1="+type1+"&Type2="+type2+"&Type3="+type3).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getPricebyPlaces(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_PRICE_BY_PLACES+"?Type1="+type1+"&Type2="+type2+"&Type3="+type3).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getDatebyPlaces(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_DATE_BY_PLACES+"?Type1="+type1+"&Type2="+type2+"&Type3="+type3).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getTypebyPlaces(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_TYPE_BY_PLACES+"?Type1="+type1+"&Type2="+type2+"&Type3="+type3).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getIDbyType_Loc(String type,String location) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_ID_BY_TYPE_LOC+"?Type="+type+"&Location="+location).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getTitlebyType_Loc(String type,String location) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_NAME_BY_TYPE_LOC+"?Type="+type+"&Location="+location).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getLocationbyType_Loc(String type,String location) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_LOCATION_BY_TYPE_LOC+"?Type="+type+"&Location="+location).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getPricebyType_Loc(String type,String location) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_PRICE_BY_TYPE_LOC+"?Type="+type+"&Location="+location).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getDatebyType_Loc(String type,String location) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+AD_DATE_BY_TYPE_LOC+"?Type="+type+"&Location="+location).get();
        String[] S=s.split("##");
        //dbcon.cancel(true);
        return S;
    }
    public String[] getAllMemberIdUnverified() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_AD_MEMBERID_UNVERIFIED).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public boolean updateVerified(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_VERIFIED+"?adID="+id).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateNotified(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_NOTIFIED+"?adID="+id).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateReported(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_REPORTED+"?adID="+id).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateReport_remark(String id,String remark) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_REPORT_REMARK+"?adID="+id+"&remark="+remark).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public boolean updateNotifiedByMemerId(String id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+UPDATE_NOTIFIED_BY_MEMBERID+"?id="+id).get();
        //dbcon.cancel(true);
        return s.equals("success");
    }
    public String[] getNon_notified_verified_AdId(String member_id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+GET_ADID_NON_NOTIFIED_VERIFIED+"?id="+member_id).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getNon_notified_deleted_AdId(String member_id) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)  dbcon.execute("advertisement/"+GET_ADID_NON_NOTIFIED_DELETED+"?id="+member_id).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
}
