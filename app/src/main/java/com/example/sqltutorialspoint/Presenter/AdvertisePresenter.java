package com.example.sqltutorialspoint.Presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.sqltutorialspoint.Model.Advertisement;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AdvertisePresenter {
    Context context;
    Advertisement advertisement;
    public AdvertisePresenter(Context context) {
        this.context = context;
        advertisement = new Advertisement(context);
    }

    public boolean insertAdvertise(Bitmap imgData, String imgName, String title, String id, String location, String type, String price, String address, String contact_no,String desc) throws ExecutionException, InterruptedException {
        return advertisement.insertAdvertise(imgData, imgName, title, id, location, type, price, address, contact_no,desc);
    }

        public ArrayList<String[]> getAdvertiseListTexts() throws ExecutionException, InterruptedException {
        String[] id = advertisement.getAllId();
        String[] name = advertisement.getAllName();
        String[] type = advertisement.getAllType();
        String[] location = advertisement.getAllLocation();
        String[] price = advertisement.getAllPrice();
        String[] date = advertisement.getAllDate();
        ArrayList<String[]> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            String[] s = new String[6];
            s[0] = name[i];
            s[1] = type[i];
            s[2] = price[i];
            s[3] = location[i];
            s[4] = date[i];
            s[5]=id[i];
            arr.add(s);

        }
        return arr;
    }
    public String[] getIdbyType(String type) throws ExecutionException, InterruptedException {
        return advertisement.getIDbyType(type);
    }
    public ArrayList<String[]> getAdvertiseListTextsbyType(String type) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyType(type);
        String[] name = advertisement.getTitlebyType(type);
        String[] ad_type=new String[id.length];
        for (int i=0;i<id.length;i++)
            ad_type[i]=type;
        String[] location = advertisement.getLocationbyType(type);
        String[] price = advertisement.getPricebyType(type);
        String[] date = advertisement.getDatebyType(type);
        ArrayList<String[]> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            String[] s = new String[6];
            s[0] = name[i];
            s[1]=ad_type[i];
            s[2] = price[i];
            s[3] = location[i];
            s[4] = date[i];
            s[5]=id[i];
            arr.add(s);

        }
        return arr;
    }
    public ArrayList<String[]> getAdvertiseListTextsbyTypes(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyTypes(type1,type2,type3);
        String[] name = advertisement.getTitlebyTypes(type1,type2,type3);
        String[] ad_type=new String[id.length];
        for (int i=0;i<id.length;i++)
            ad_type[i]=advertisement.getType(id[i]);
        String[] location = advertisement.getLocationbyTypes(type1,type2,type3);
        String[] price = advertisement.getPricebyTypes(type1,type2,type3);
        String[] date = advertisement.getDatebyTypes(type1,type2,type3);
        ArrayList<String[]> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            String[] s = new String[6];
            s[0] = name[i];
            s[1]=ad_type[i];
            s[2] = price[i];
            s[3] = location[i];
            s[4] = date[i];
            s[5]=id[i];
            arr.add(s);

        }
        return arr;
    }
    public ArrayList<String[]> getAdvertiseListTextsbyPrices(String type1,String type2) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyPrices(type1,type2);
        String[] name = advertisement.getTitlebyPrices(type1,type2);
        String[] ad_type= advertisement.getTypebyPrices(type1,type2);
        String[] price =new String[id.length];
        for (int i=0;i<id.length;i++)
            price[i]=advertisement.getPrice(id[i]);
        String[] location = advertisement.getLocationbyPrices(type1,type2);
        String[] date = advertisement.getDatebyPrices(type1,type2);
        ArrayList<String[]> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            String[] s = new String[6];
            s[0] = name[i];
            s[1]=ad_type[i];
            s[2] = price[i];
            s[3] = location[i];
            s[4] = date[i];
            s[5]=id[i];
            arr.add(s);

        }
        return arr;
    }
    public ArrayList<String[]> getAdvertiseListTextsbyPlaces(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyPlaces(type1,type2,type3);
        String[] name = advertisement.getTitlebyPlaces(type1,type2,type3);
        String[] ad_type= advertisement.getTypebyPlaces(type1,type2,type3);
        String[] location =new String[id.length];
        for (int i=0;i<id.length;i++)
            location[i]=advertisement.getLocation(id[i]);
        String[] price = advertisement.getPricebyPlaces(type1,type2,type3);
        String[] date = advertisement.getDatebyPlaces(type1,type2,type3);
        ArrayList<String[]> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            String[] s = new String[6];
            s[0] = name[i];
            s[1]=ad_type[i];
            s[2] = price[i];
            s[3] = location[i];
            s[4] = date[i];
            s[5]=id[i];
            arr.add(s);

        }
        return arr;
    }
    public String[] getIdbyType_Location(String type,String location) throws ExecutionException, InterruptedException {
        return advertisement.getIDbyType_Loc(type,location);
    }
    public ArrayList<String[]> getAdvertiseListTextsbyType_Location(String type,String loc) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyType_Loc(type,loc);
        String[] name = advertisement.getTitlebyType_Loc(type,loc);
        String[] ad_type=new String[id.length];
        for (int i=0;i<id.length;i++)
            ad_type[i]=type;
        String[] location =new String[id.length];
        for (int i=0;i<id.length;i++)
            location[i]=loc;
        String[] price = advertisement.getPricebyType_Loc(type,loc);
        String[] date = advertisement.getDatebyType_Loc(type,loc);
        ArrayList<String[]> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            String[] s = new String[6];
            s[0] = name[i];
            s[1]=ad_type[i];
            s[2] = price[i];
            s[3] = location[i];
            s[4] = date[i];
            s[5]=id[i];
            arr.add(s);

        }
        return arr;
    }
    public boolean haveAds(String member_id) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDs(member_id);
        if (id[0].length() < 1)
            return false;
        else
            return true;
    }
    public boolean haveAds() throws ExecutionException, InterruptedException {
        String[] id = advertisement.getAllId();
        if (id[0].length() < 1)
            return false;
        else
            return true;
    }
    public boolean haveNon_notified_verified_Ads(String member_id) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getNon_notified_verified_AdId(member_id);
        if (id[0].length() < 1)
            return false;
        else
            return true;
    }
    public boolean haveNon_notified_deleted_Ads(String member_id) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getNon_notified_deleted_AdId(member_id);
        if (id[0].length() < 1)
            return false;
        else
            return true;
    }
    public boolean haveAdsbyType(String type) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyType(type);
        if (id[0].length() < 1)
            return false;
        else
            return true;
    }
    public boolean haveAdsbyType_Loc(String type,String loc) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyType_Loc(type,loc);
        if (id[0].length() < 1)
            return false;
        else
            return true;
    }
    public String[] getIDs(String member_id) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDs(member_id);
        return id;
    }

    public ArrayList<String[]> getAdvertiseListTexts2(String member_id) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDs(member_id);
        String[] name = advertisement.getNames(member_id);
        String[] type = advertisement.getTypes(member_id);
        String[] location = advertisement.getLocations(member_id);
        String[] price = advertisement.getPrices(member_id);
        String[] date = advertisement.getDates(member_id);
        String[] verified=advertisement.getVerifieds(member_id);
        String[] deleteds=advertisement.getDeleteds(member_id);
        ArrayList<String[]> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            String[] s = new String[8];
            s[0] = name[i];
            s[1] = type[i];
            s[2] = price[i];
            s[3] = location[i];
            s[4] = date[i];
            s[5]=verified[i];
            s[6]=deleteds[i];
            s[7]=id[i];
            arr.add(s);

        }
        return arr;
    }
    public String[] getAllId() throws ExecutionException, InterruptedException {
        return advertisement.getAllId();
    }
    public ArrayList<Bitmap> getAdvertiseImages() throws ExecutionException, InterruptedException {
        String[] id = advertisement.getAllId();
        ArrayList<Bitmap> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            arr.add(advertisement.getImage(id[i]));
        }
        return arr;
    }
    public ArrayList<Bitmap> getImagesByType(String type) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyType(type);
        ArrayList<Bitmap> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            arr.add(advertisement.getImage(id[i]));
        }
        return arr;
    }
    public ArrayList<Bitmap> getImagesByTypes(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyTypes(type1,type2,type3);
        ArrayList<Bitmap> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            arr.add(advertisement.getImage(id[i]));
        }
        return arr;
    }
    public ArrayList<Bitmap> getImagesByPlaces(String type1,String type2,String type3) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyPlaces(type1,type2,type3);
        ArrayList<Bitmap> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            arr.add(advertisement.getImage(id[i]));
        }
        return arr;
    }
    public ArrayList<Bitmap> getImagesByPrices(String type1,String type2) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyPrices(type1,type2);
        ArrayList<Bitmap> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            arr.add(advertisement.getImage(id[i]));
        }
        return arr;
    }
    public ArrayList<Bitmap> getImagesByType_Loc(String type,String loc) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDbyType_Loc(type,loc);
        ArrayList<Bitmap> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            arr.add(advertisement.getImage(id[i]));
        }
        return arr;
    }

    public ArrayList<Bitmap> getAdvertiseImages2(String member_id) throws ExecutionException, InterruptedException {
        String[] id = advertisement.getIDs(member_id);
        ArrayList<Bitmap> arr = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            arr.add(advertisement.getImage(id[i]));
        }
        return arr;
    }

    public Bitmap getImage(String id) throws ExecutionException, InterruptedException {
        return advertisement.getImage(id);
    }

    public String getTitle(String id) throws ExecutionException, InterruptedException {
        return advertisement.getName(id);
    }

    public String getType(String id) throws ExecutionException, InterruptedException {
        return advertisement.getType(id);
    }

    public String getPrice(String id) throws ExecutionException, InterruptedException {
        return advertisement.getPrice(id);
    }

    public String getLocation(String id) throws ExecutionException, InterruptedException {
        return advertisement.getLocation(id);
    }

    public String getAddress(String id) throws ExecutionException, InterruptedException {
        return advertisement.getAddress(id);
    }

    public String getContact(String id) throws ExecutionException, InterruptedException {
        return advertisement.getContact(id);
    }
    public String getFeature(String id) throws ExecutionException, InterruptedException {
        return advertisement.getFeature(id);
    }
    public String getReport_remark(String id) throws ExecutionException, InterruptedException {
        return advertisement.getReport_remark(id);
    }

        public boolean updateRoommateNo(String id, String seatNo) throws ExecutionException, InterruptedException {
        return advertisement.updateRoommateNo(id, seatNo);
    }

    public boolean updateRoommateGender(String id, String type) throws ExecutionException, InterruptedException {
        return advertisement.updateRoommateGender(id, type);
    }

    public boolean updateFlatRoomNo(String id, String roomNo) throws ExecutionException, InterruptedException {
        return advertisement.updateFlatRoomNo(id, roomNo);
    }

    public boolean updateFlatSpace(String id, String space) throws ExecutionException, InterruptedException {
        return advertisement.updateFlatSpace(id, space);
    }
    public boolean updateHostelSeatNo(String id, String seatNo) throws ExecutionException, InterruptedException {
        return advertisement.updateHostelSeatNo(id, seatNo);
    }

    public boolean updateHostelType(String id, String type) throws ExecutionException, InterruptedException {
        return advertisement.updateHostelType(id, type);
    }
    public boolean updateFeatures(String id, String feature) throws ExecutionException, InterruptedException {
        return advertisement.updateFeature(id, feature);
    }
    public String getMaxId(String table) throws ExecutionException, InterruptedException {
        return advertisement.getMaxId(table);
    }

    public boolean deleteAd(String id) throws ExecutionException, InterruptedException {
        return advertisement.deleteAd(id);
    }

    public String getHostelSeatNo(String id) throws ExecutionException, InterruptedException {
        return advertisement.getHostelSeatNo(id);
    }

    public String getHostelType(String id) throws ExecutionException, InterruptedException {
        return advertisement.getHostelType(id);
    }

    public String getFlatRoomNo(String id) throws ExecutionException, InterruptedException {
        return advertisement.getFlatRoomNo(id);
    }

    public String getFlatSpace(String id) throws ExecutionException, InterruptedException {
        return advertisement.getFlatSpace(id);
    }
    public String getRoommateSeatNo(String id) throws ExecutionException, InterruptedException {
        return advertisement.getRoommateNo(id);
    }

    public String getRoommateType(String id) throws ExecutionException, InterruptedException {
        return advertisement.getRoommateType(id);
    }

    public String getDescription(String id) throws ExecutionException, InterruptedException {
        return advertisement.getDescription(id);
    }
    public String[] getAllMemberIdUnverified() throws ExecutionException, InterruptedException {
        return advertisement.getAllMemberIdUnverified();
    }
    public String[] getAllDateUnverified() throws ExecutionException, InterruptedException {
        return advertisement.getAllDateUnverified();
    }
    public String[] getAllModifiedUnverified() throws ExecutionException, InterruptedException {
        return advertisement.getAllModifiedUnverified();
    }
    public String[] getAllIdUnverified() throws ExecutionException, InterruptedException {
        return advertisement.getAllIdUnverified();
    }
    public boolean updateVerified(String id) throws ExecutionException, InterruptedException {
        return advertisement.updateVerified(id);
    }
    public boolean updateNotified(String id) throws ExecutionException, InterruptedException {
        return advertisement.updateNotified(id);
    }
    public boolean updateReported(String id) throws ExecutionException, InterruptedException {
        return advertisement.updateReported(id);
    }
    public boolean updateModified(String id,String mod) throws ExecutionException, InterruptedException {
        return advertisement.updateModified(id,mod);
    }
    public boolean updateClicked(String id) throws ExecutionException, InterruptedException {
        return advertisement.updateClicked(id);
    }
        public boolean updateReport_remark(String id,String remark) throws ExecutionException, InterruptedException {
        return advertisement.updateReport_remark(id,remark);
    }
        public boolean updateNotifiedByMemberId(String id) throws ExecutionException, InterruptedException {
        return advertisement.updateNotifiedByMemerId(id);
    }
}