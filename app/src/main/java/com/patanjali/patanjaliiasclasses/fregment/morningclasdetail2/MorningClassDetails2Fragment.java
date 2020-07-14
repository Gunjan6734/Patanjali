package com.patanjali.patanjaliiasclasses.fregment.morningclasdetail2;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.MainActivity;
import com.patanjali.patanjaliiasclasses.adapter.SubjectTeacher2Adapter;
import com.patanjali.patanjaliiasclasses.fregment.freedownloads.FreeDowloadsFragment;
import com.patanjali.patanjaliiasclasses.fregment.home.HomeFragment;
import com.patanjali.patanjaliiasclasses.fregment.morningclass.MorningClassFragment;
import com.patanjali.patanjaliiasclasses.fregment.profile.ProfileFragment;
import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.model.Detail;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;
import com.patanjali.patanjaliiasclasses.service.GetData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.patanjali.patanjaliiasclasses.activity.MainActivity.golsidtext;
import static com.patanjali.patanjaliiasclasses.activity.MainActivity.linearout;

/**
 * A simple {@link Fragment} subclass.
 */
public class MorningClassDetails2Fragment extends Fragment {

    CardView cardview031;
    TextView textview03,resendotpedttxt,goalsidtext3,classname03,bredcrumbs03,onlnsetstext03,offlnsetstext03,onlnfeetext03,
            offlnfeetext03,clsdatetxt03,clastimtext03,aboutclas03,submitbutton,cancelbutton,setotp2;
    EditText registernumberedttxt,entrotpedttxt;
   // RelativeLayout paynowlayout02;

    RecyclerView listviewsubject;
    //SharedPreference Use
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARD_PREF = "GLOBLE_Application";
    ProgressDialog progressDialog;
    String cmid="";
    String message="";
    Config ut;
    ClassData classData;
    ArrayList<Detail> detailArrayLists=new ArrayList<>();
    String Mregisternumberedttxt,Mentrotpedttxt;
    MainActivity mainActivity;
    UserSession userSession;

    public MorningClassDetails2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_morning_class_details2, container, false);

        progressDialog=new ProgressDialog(getContext());
        mainActivity= (MainActivity) getActivity();

        goalsidtext3=root.findViewById(R.id.goalsidtext3);
        classname03=root.findViewById(R.id.classname03);
        bredcrumbs03=root.findViewById(R.id.bredcrumbs03);
        onlnsetstext03=root.findViewById(R.id.onlnsetstext03);
        onlnfeetext03=root.findViewById(R.id.onlnfeetext03);
        clsdatetxt03=root.findViewById(R.id.clsdatetxt03);
        clastimtext03=root.findViewById(R.id.clastimtext03);
        aboutclas03=root.findViewById(R.id.aboutclas03);
       // paynowlayout02=root.findViewById(R.id.paynowlayout02);

        listviewsubject=root.findViewById(R.id.listviewsubject);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listviewsubject.setLayoutManager(mLayoutManager);
        listviewsubject.setItemAnimator(new DefaultItemAnimator());

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

        Bundle mbundle=new Bundle();
        mbundle=getArguments();
        classData= (ClassData) mbundle.getSerializable("cmid");
        Log.d("cmid",cmid);
        detailArrayLists=classData.getDetails();

        hidefooter();
        getclasdetailss();
        cheloging();
        MainActivity.txtProfileName.setText(new UserSession(getActivity()).getMobile());
        MainActivity.userproname.setText(new UserSession(getActivity()).getUsername());


        SubjectTeacher2Adapter subjectTeacher2Adapter = new SubjectTeacher2Adapter(getContext(),detailArrayLists);
        listviewsubject.setAdapter(subjectTeacher2Adapter);

        return root;
    }

    private void cheloging() {
        if (UserSession.getIsLogin(mainActivity)) {
            MainActivity.linearout.setVisibility(View.GONE);
            golsidtext.setVisibility(View.VISIBLE);
            getclasdetailss();
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

            golsidtext.setVisibility(View.GONE);

        }
    }






    private void getclasdetailss() {
       // MainActivity.golsidtext.setText(classData.getGoalID());
        goalsidtext3.setText(classData.getGoalID());
        classname03.setText(classData.getClassname());
        bredcrumbs03.setText(classData.getBredcrumbs());
        onlnsetstext03.setText(classData.getOnlineclassseats());

       // offlnsetstext03.setText(classData.getOfflineclassseats());
        onlnfeetext03.setText("₹"+classData.getOnlineclassfee());
       // offlnfeetext03.setText(classData.getOfflineclassfee());

        clsdatetxt03.setText(classData.getStartdate());
        clastimtext03.setText(classData.getStarttime());
        aboutclas03.setText(classData.getRemarks());
    }

    private void hidefooter() {
         MainActivity.linearlayout.setVisibility(View.VISIBLE);
    }
}
