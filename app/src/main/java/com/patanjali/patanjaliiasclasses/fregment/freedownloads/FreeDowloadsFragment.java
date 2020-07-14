package com.patanjali.patanjaliiasclasses.fregment.freedownloads;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.MainActivity;
import com.patanjali.patanjaliiasclasses.activity.PdfViewActivity;
import com.patanjali.patanjaliiasclasses.adapter.FreeDownloadAdapter;
import com.patanjali.patanjaliiasclasses.adapter.MoringClassAdapter;
import com.patanjali.patanjaliiasclasses.fregment.morningclass.MorningClassFragment;
import com.patanjali.patanjaliiasclasses.fregment.mornningclasdetail.MorningClassDetailFragment;
import com.patanjali.patanjaliiasclasses.fregment.videoplay.VedioPlayerFragment;
import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.model.FreeDownloadModel;
import com.patanjali.patanjaliiasclasses.model.MorningClas_Model;
import com.patanjali.patanjaliiasclasses.service.GetData;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;
import com.patanjali.patanjaliiasclasses.service.PostData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.patanjali.patanjaliiasclasses.activity.MainActivity.golsidtext;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreeDowloadsFragment extends Fragment implements FreeDownloadAdapter.Onitemclick{

    //SharedPreference Use
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARD_PREF = "GLOBLE_Application";
    String Usertoken="";
    Config ut;
    MainActivity mainActivity;
    UserSession userSession;

    TextView textcontent2;
    RecyclerView recycelrvewi_fd;
    private List<FreeDownloadModel> freeDownloadModelList;

    public FreeDowloadsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_free_dowloads, container, false);

        mainActivity= (MainActivity) getActivity();
        userSession = new UserSession(getActivity());

        textcontent2=root.findViewById(R.id.textcontent2);
        recycelrvewi_fd=root.findViewById(R.id.recycelrvewi_fd);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycelrvewi_fd.setLayoutManager(mLayoutManager);
        recycelrvewi_fd.setItemAnimator(new DefaultItemAnimator());

        MainActivity.linearoutfooter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorningClassFragment morningClassFragment = new MorningClassFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
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
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, freeDowloadsFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        freeDownloadModelList = new ArrayList<>();

        cheloging();
        hidefooter();
        viewdownloadlist();
        MainActivity.txtProfileName.setText(new UserSession(getActivity()).getMobile());
        MainActivity.userproname.setText(new UserSession(getActivity()).getUsername());

        return root;
    }

    private void cheloging() {
        if (UserSession.getIsLogin(mainActivity)) {
//            MainActivity.linearout.setVisibility(View.GONE);
            golsidtext.setVisibility(View.VISIBLE);
            recycelrvewi_fd.setVisibility(View.VISIBLE);
            textcontent2.setVisibility(View.GONE);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_mylibrary).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_aboutus).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_yourprofile).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_contact).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_logoutfregment).setVisible(true);

            try {
                new GetData(getActivity(), new JSONObject(), ut.BASE_URL +"Checklogin/"+ut.Securetkey+"/"+ SaveSharedPreference.getUserId(),
                        new OnTaskCompleted<String>() {
                            @Override
                            public void onTaskCompleted(String response) {
                                Log.d("response", response);
                                try {
                                    JSONArray jsonArray=new JSONArray(response);
                                    JSONObject object=jsonArray.getJSONObject(0);
                                    String responseCode=object.getString("responsecode");
                                    String message=object.getString("message");
                                    if (responseCode.equals("1")){
                                        // Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();

                                    }else if (responseCode.equals("0")){
                                        // Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                                        userSession.logoutUser();
                                        Intent intent=new Intent(getActivity(),MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                        getActivity().finishAffinity();

                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        },true).execute();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {

            textcontent2.setVisibility(View.VISIBLE);
            recycelrvewi_fd.setVisibility(View.GONE);
            golsidtext.setVisibility(View.GONE);

        }
    }

    private void hidefooter() {
        MainActivity.linearlayout.setVisibility(View.VISIBLE);
    }

    private void viewdownloadlist() {
        String downldurl=ut.BASE_URL +"Freedownload/"+ut.Securetkey;
        Log.e("downldurl",downldurl);
        new GetData(getActivity(), new JSONObject(), downldurl, new OnTaskCompleted<String>() {
            @Override
            public void onTaskCompleted(String response) {
                Log.e("response",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String type=jsonObject.getString("type");
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String url = jsonObject.getString("url");
                        Log.e("response", response.toString());

                        FreeDownloadModel freeDownloadModel=new FreeDownloadModel(title,type,description,url);
                        freeDownloadModelList.add(freeDownloadModel);

                    }

                    FreeDownloadAdapter freeDownloadAdapter=new FreeDownloadAdapter(getActivity(),freeDownloadModelList,FreeDowloadsFragment.this);
                    recycelrvewi_fd.setAdapter(freeDownloadAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },true).execute();
    }

    @Override
    public void itemclick(int position) {

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
