package com.example.sqltutorialspoint.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.sqltutorialspoint.Presenter.AdvertisePresenter;
import com.example.sqltutorialspoint.Presenter.MemberPresenter;
import com.example.sqltutorialspoint.R;
import com.example.sqltutorialspoint.Utility.constants;
import com.example.sqltutorialspoint.View.AdvertiseDetailsActivity;

import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;


public class AdvertiseFragment extends Fragment {
    ListView l;
    MemberPresenter memberPresenter;
    AdvertisePresenter advertisePresenter;
    String[] memberIds,adDates,adIds,adModified;
    String[] values;
    public AdvertiseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        memberPresenter=new MemberPresenter(getActivity());
        advertisePresenter=new AdvertisePresenter(getActivity());
        View rootView= inflater.inflate(R.layout.fragment_two, container, false);

        try {
            adIds=advertisePresenter.getAllIdUnverified();
            memberIds=advertisePresenter.getAllMemberIdUnverified();
            adDates=advertisePresenter.getAllDateUnverified();
            adModified=advertisePresenter.getAllModifiedUnverified();
            values=new String[adIds.length];
            for(int i=0;i<adIds.length;i++){
                String time=adDates[adIds.length-i-1];
                String modified=adModified[adIds.length-i-1];
                StringTokenizer st=new StringTokenizer(time," ");
                time=st.nextToken();
                st=new StringTokenizer(time,"-");
                String[] date_element={st.nextToken(),st.nextToken(),st.nextToken()};
                String date= constants.MONTH_MAP.get(Integer.parseInt(date_element[1]))+" "+date_element[2];
                if(modified.equals("0"))
                    values[i]="\n"+memberPresenter.getName(memberIds[adIds.length-i-1])+" posted an Advertise\n"+"on "+date+"\n";
                else
                    values[i]="\n"+memberPresenter.getName(memberIds[adIds.length-i-1])+" updated an Advertise\n"+"on "+date+"\n";
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        l=(ListView)rootView.findViewById(R.id.adRequestList);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                try {
                    String[] textData = new String[]{advertisePresenter.getTitle(adIds[adIds.length-position-1]),advertisePresenter.getType(adIds[adIds.length-position-1]),advertisePresenter.getPrice(adIds[adIds.length-position-1]),advertisePresenter.getLocation(adIds[adIds.length-position-1]),adDates[adIds.length-position-1],adIds[adIds.length-position-1]};
                    Intent intent=new Intent(getActivity(), AdvertiseDetailsActivity.class);
                    //intent.putExtra("id",member_id);
                    intent.putExtra("logged_in",true); //make it true later
                    intent.putExtra("user_ad",false);
                    intent.putExtra("admin_tl",true);
//                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
//                    advertisePresenter.getImage(adIds[adIds.length-position-1]).compress(Bitmap.CompressFormat.PNG, 100, bStream);
//                    byte[] byteArray = bStream.toByteArray();
//                    intent.putExtra("img",byteArray);
                    intent.putExtra("adData",textData);
                    startActivity(intent);
                    getActivity().finish();
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