package com.example.sqltutorialspoint.Model;

import android.content.Context;

import com.example.sqltutorialspoint.Service.dbConnectionForTextData;

import java.util.concurrent.ExecutionException;

import static com.example.sqltutorialspoint.Utility.constants.GET_AD_REPORT_ADID;
import static com.example.sqltutorialspoint.Utility.constants.GET_AD_REPORT_COMMENT;
import static com.example.sqltutorialspoint.Utility.constants.GET_AD_REPORT_DATE;
import static com.example.sqltutorialspoint.Utility.constants.GET_AD_REPORT_ID;
import static com.example.sqltutorialspoint.Utility.constants.GET_AD_REPORT_MEMBERID;
import static com.example.sqltutorialspoint.Utility.constants.INSERT_AD_REPORT;

public class Reports {
    Context context;
    dbConnectionForTextData dbcon;

    public Reports(Context context) {
        this.context = context;
        dbcon = new dbConnectionForTextData(context);
    }
    public boolean insertAd_report(String ad_id,String member_id,String comment) throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String)dbcon.execute("advertisement/"+INSERT_AD_REPORT+"?ad_id="+ad_id+"&member_id="+member_id+"&comment="+comment).get();
        return s.equals("success");
    }
    public String[] getAllReportId() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_AD_REPORT_ID).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllReport_AdId() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_AD_REPORT_ADID).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllReport_memberId() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_AD_REPORT_MEMBERID).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllReport_Comment() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_AD_REPORT_COMMENT).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }
    public String[] getAllReport_Date() throws ExecutionException, InterruptedException {
        dbcon=new dbConnectionForTextData(context);
        String s= (String) dbcon.execute("advertisement/"+GET_AD_REPORT_DATE).get();
        String[] id=s.split("##");
        //dbcon.cancel(true);
        return id;
    }

}
