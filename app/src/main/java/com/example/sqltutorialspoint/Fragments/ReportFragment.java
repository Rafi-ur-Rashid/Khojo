package com.example.sqltutorialspoint.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.sqltutorialspoint.Presenter.AdvertisePresenter;
import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.Presenter.ReportPresenter;
import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Utility.constants;
import com.example.sqltutorialspoint.View.AdvertiseDetailsActivity;

import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;


public class ReportFragment extends Fragment {
    ListView l;
    ReportPresenter reportPresenter;
    MemberPresenter memberPresenter;
    AdvertisePresenter advertisePresenter;
    String[] reportIds,reportAd_Ids,reportMemberIds,reportComments,reportDates;
    String[] values;
    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_one, container, false);
        reportPresenter=new ReportPresenter(getActivity());
        memberPresenter=new MemberPresenter(getActivity());
        advertisePresenter=new AdvertisePresenter(getActivity());
        try {
            
            reportIds=reportPresenter.getAllReportId();
            reportAd_Ids=reportPresenter.getAllReport_AdId();
            reportMemberIds=reportPresenter.getAllReport_memberId();
            reportComments=reportPresenter.getAllReport_Comment();
            reportDates=reportPresenter.getAllReport_Date();
            values=new String[reportIds.length];
            for(int i=0;i<reportIds.length;i++){
                String time=reportDates[reportIds.length-i-1];
                StringTokenizer st=new StringTokenizer(time," ");
                time=st.nextToken();
                st=new StringTokenizer(time,"-");
                String[] date_element={st.nextToken(),st.nextToken(),st.nextToken()};
                String date=constants.MONTH_MAP.get(Integer.parseInt(date_element[1]))+" "+date_element[2];
                values[i]="\n"+memberPresenter.getName(reportMemberIds[reportIds.length-i-1])+" reported an Advertise\n"+"on "+date+"\n";
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        l=(ListView)rootView.findViewById(R.id.reportList);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                try {
                    final String[] textData = new String[]{advertisePresenter.getTitle(reportAd_Ids[reportAd_Ids.length-position-1]),advertisePresenter.getType(reportAd_Ids[reportAd_Ids.length-position-1]),advertisePresenter.getPrice(reportAd_Ids[reportAd_Ids.length-position-1]),advertisePresenter.getLocation(reportAd_Ids[reportAd_Ids.length-position-1]),reportDates[reportAd_Ids.length-position-1],reportAd_Ids[reportAd_Ids.length-position-1]};

                    new AlertDialog.Builder(getActivity())
                            .setMessage("Report Description:\n"
                                    +reportComments[reportAd_Ids.length-position-1]+"\n\n"
                                    +"Do you want to view this Ad?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                        Intent intent=new Intent(getActivity(), AdvertiseDetailsActivity.class);
                                        //intent.putExtra("id",member_id);
                                        intent.putExtra("logged_in",true); //make it true later
                                        intent.putExtra("user_ad",false);
                                        intent.putExtra("admin_tl",false);
                                        intent.putExtra("report",true);
    //
                                        intent.putExtra("adData",textData);
                                        startActivity(intent);
                                        getActivity().finish();

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
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        return rootView;
    }

}