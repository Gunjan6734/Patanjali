package com.patanjali.patanjaliiasclasses.fregment.recordingvedio;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.MainActivity;
import com.patanjali.patanjaliiasclasses.adapter.FreeDownloadAdapter;
import com.patanjali.patanjaliiasclasses.adapter.VedioRecordingAdapter;
import com.patanjali.patanjaliiasclasses.fregment.freedownloads.FreeDowloadsFragment;
import com.patanjali.patanjaliiasclasses.fregment.morningclass.MorningClassFragment;
import com.patanjali.patanjaliiasclasses.fregment.videoplay.VedioPlayerFragment;
import com.patanjali.patanjaliiasclasses.model.FreeDownloadModel;
import com.patanjali.patanjaliiasclasses.model.Spinerlist_Model;
import com.patanjali.patanjaliiasclasses.model.VedioRecord_Model;
import com.patanjali.patanjaliiasclasses.service.GetData;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.patanjali.patanjaliiasclasses.activity.MainActivity.golsidtext;

/**
 * A simple {@link Fragment} subclass.
 */
public class VediosPlayFragment extends Fragment implements VedioRecordingAdapter.Onitemclick {

    //SharedPreference Use
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARD_PREF = "GLOBLE_Application";
    String Usertoken = "";
    Config ut;
    MainActivity mainActivity;
    UserSession userSession;

    RecyclerView recyclerViewvedios;
    private List<VedioRecord_Model> vedioRecord_modelList;

    public VediosPlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_vedios_play, container, false);

        mainActivity = (MainActivity) getActivity();
        userSession = new UserSession(getActivity());

     /*   String strtext = getArguments().getString("MeetingID");
        Log.d("MeetingID",""+strtext);*/

       /* Bundle arguments = getArguments();

        if (arguments!=null) {
            String MeetingID1 = arguments.getString("MeetingID");
            Log.d("MeetingID",""+MeetingID1);
            //Toast.makeText(getActivity(), "zvdfvzfz"+MeetingID, Toast.LENGTH_SHORT).show();

        }*/

        recyclerViewvedios = root.findViewById(R.id.recyclerViewvedios);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewvedios.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewvedios.setItemAnimator(new DefaultItemAnimator());

        MainActivity.linearoutfooter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorningClassFragment morningClassFragment = new MorningClassFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, morningClassFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        MainActivity.linearoutfooter6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FreeDowloadsFragment freeDowloadsFragment = new FreeDowloadsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, freeDowloadsFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        viewrecordinglist();
        hidefooter();
        cheloging();
        //getmeetingid();
        MainActivity.txtProfileName.setText(new UserSession(getActivity()).getMobile());
        MainActivity.userproname.setText(new UserSession(getActivity()).getUsername());

        return root;
    }

   /* private void getmeetingid() {
        String getmettingurl=" http://api.globalclasses.live/v1/Liveclasses/PATANJALI@09765432/"+SaveSharedPreference.getUserId();
        Log.e("getmettingurl",getmettingurl);
        new GetData(getActivity(), new JSONObject(), getmettingurl, new OnTaskCompleted<String>() {
            @Override
            public void onTaskCompleted(String response) {
                Log.e("response",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String meeting = jsonObject.getString("meeting");
                        Log.e("meeting", response.toString());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },true).execute();
    }*/

    private void cheloging() {
        if (UserSession.getIsLogin(mainActivity)) {

//            MainActivity.linearout.setVisibility(View.GONE);
            golsidtext.setVisibility(View.VISIBLE);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_mylibrary).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_aboutus).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_yourprofile).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_contact).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_logoutfregment).setVisible(true);

            try {
                new GetData(getActivity(), new JSONObject(), ut.BASE_URL + "Checklogin/" + ut.Securetkey + "/" + SaveSharedPreference.getUserId(),
                        new OnTaskCompleted<String>() {
                            @Override
                            public void onTaskCompleted(String response) {
                                Log.d("response", response);
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    String responseCode = object.getString("responsecode");
                                    String message = object.getString("message");
                                    if (responseCode.equals("1")) {
                                        // Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();

                                    } else if (responseCode.equals("0")) {
                                        // Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                                        userSession.logoutUser();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                        getActivity().finishAffinity();

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, true).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            golsidtext.setVisibility(View.GONE);
        }
    }

    private void hidefooter() {
        MainActivity.linearlayout.setVisibility(View.VISIBLE);
    }

    private void viewrecordinglist() {
        String recordurl = ut.BASE_URL + "Getrecording/" + ut.Securetkey + "/" + SaveSharedPreference.getSession_Token();
        Log.e("recordurl", recordurl);
        new GetData(getActivity(), new JSONObject(), recordurl, new OnTaskCompleted<String>() {
            @Override
            public void onTaskCompleted(String response) {
                Log.e("response", response);
                try {
                    vedioRecord_modelList = new ArrayList<>();

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String subject = jsonObject.getString("subject");
                        String teacher = jsonObject.getString("teacher");
                        String topic = jsonObject.getString("topic");
                        String date = jsonObject.getString("date");
                        String videourl = jsonObject.getString("videourl");
                        String presentationurl = jsonObject.getString("presentationurl");
                        String Urltype = jsonObject.getString("type");
                        Log.e("response", "" + Urltype);

                        VedioRecord_Model vedioRecord_model = new VedioRecord_Model(subject, teacher, topic, date, videourl, presentationurl, Urltype);
                        vedioRecord_modelList.add(vedioRecord_model);

                    }

                    VedioRecordingAdapter vedioRecordingAdapter = new VedioRecordingAdapter(getActivity(), vedioRecord_modelList, VediosPlayFragment.this);
                    recyclerViewvedios.setAdapter(vedioRecordingAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, true).execute();
    }

    @Override
    public void itemclick(int position) {

    }

}
