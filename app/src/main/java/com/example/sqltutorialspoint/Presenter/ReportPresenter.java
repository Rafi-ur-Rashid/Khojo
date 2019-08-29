package com.example.sqltutorialspoint.Presenter;

import android.content.Context;

import com.example.sqltutorialspoint.Model.Reports;

import java.util.concurrent.ExecutionException;

public class ReportPresenter {
    Context context;
    Reports reports;

    public ReportPresenter(Context context) {
        this.context = context;
        reports=new Reports(context);
    }
    public boolean insertAd_report(String ad_id,String member_id,String comment) throws ExecutionException, InterruptedException {
        return reports.insertAd_report(ad_id,member_id,comment);
    }
    public String[] getAllReportId() throws ExecutionException, InterruptedException {
        return reports.getAllReportId();
    }
    public String[] getAllReport_AdId() throws ExecutionException, InterruptedException {
        return reports.getAllReport_AdId();
    }
    public String[] getAllReport_memberId() throws ExecutionException, InterruptedException {
        return reports.getAllReport_memberId();
    }
    public String[] getAllReport_Comment() throws ExecutionException, InterruptedException {
        return reports.getAllReport_Comment();
    }
    public String[] getAllReport_Date() throws ExecutionException, InterruptedException {
        return reports.getAllReport_Date();
    }
}
