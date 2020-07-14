package com.patanjali.patanjaliiasclasses.fregment.mornningclasdetail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.MainActivity;
import com.patanjali.patanjaliiasclasses.adapter.SubjTeacherAdapter;
import com.patanjali.patanjaliiasclasses.fregment.freedownloads.FreeDowloadsFragment;
import com.patanjali.patanjaliiasclasses.fregment.home.HomeFragment;
import com.patanjali.patanjaliiasclasses.fregment.morningclasdetail2.MorningClassDetails2Fragment;
import com.patanjali.patanjaliiasclasses.fregment.morningclass.MorningClassFragment;
import com.patanjali.patanjaliiasclasses.fregment.profile.ProfileFragment;
import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.model.Detail;
import com.patanjali.patanjaliiasclasses.model.Spinerlist_Model;
import com.patanjali.patanjaliiasclasses.model.SubjTecher_Model;
import com.patanjali.patanjaliiasclasses.service.GetData;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MorningClassDetailFragment extends Fragment {

    private MorningdetailViewModel slideshowViewModel;

    TextView paymettext02,goalsidtext2,classname02,bredcrumbs02,onlnsetstext02,offlnsetstext02,onlnfeetext02,offlnfeetext02,clsdatetxt02,
            clastimtext02,aboutclas02;

    RelativeLayout paynowlayout;
    RecyclerView listviewsubj;
    List<SubjTecher_Model> subjTecher_modelList;
    SubjTeacherAdapter mAdapter;

    ListView recyclerViewlist;
    List<Spinerlist_Model> spinerlist_models;


    //SharedPreference Use
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARD_PREF = "GLOBLE_Application";
    ProgressDialog progressDialog;
    String Usertoken="";
    String goalID="";
    String cmid="";
    String message="";
    Config ut;
    ClassData classData;
    ArrayList<Detail> detailArrayList=new ArrayList<>();
    MainActivity mainActivity;
    UserSession userSession;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(MorningdetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_morningclasdetail, container, false);

        progressDialog=new ProgressDialog(getContext());
        mainActivity= (MainActivity) getActivity();
        userSession = new UserSession(getActivity());

        goalsidtext2=root.findViewById(R.id.goalsidtext2);
        classname02=root.findViewById(R.id.classname02);
        bredcrumbs02=root.findViewById(R.id.bredcrumbs02);
        onlnsetstext02=root.findViewById(R.id.onlnsetstext02);
        onlnfeetext02=root.findViewById(R.id.onlnfeetext02);
        clsdatetxt02=root.findViewById(R.id.clsdatetxt02);
        clastimtext02=root.findViewById(R.id.clastimtext02);
        aboutclas02=root.findViewById(R.id.aboutclas02);
        paynowlayout=root.findViewById(R.id.paynowlayout);
        paymettext02=root.findViewById(R.id.paymettext02);

        listviewsubj=root.findViewById(R.id.listviewsubj);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listviewsubj.setLayoutManager(mLayoutManager);
        listviewsubj.setItemAnimator(new DefaultItemAnimator());

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
        detailArrayList=classData.getDetails();

        paynowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classData.getIsenquiry().equals("True")&&classData.getOnlinepayment().equals("False")){
                    openAlertDialog();
                }else {
                    opendreloginorprofile();
                }

            }
        });

        SubjTeacherAdapter subjTeacherAdapter = new SubjTeacherAdapter(getContext(),detailArrayList);
        listviewsubj.setAdapter(subjTeacherAdapter);

        getclassdetails();
        hidefooter();
        cheloging();
        spinerlist_models=new ArrayList<Spinerlist_Model>();
        MainActivity.txtProfileName.setText(new UserSession(getActivity()).getMobile());
        MainActivity.userproname.setText(new UserSession(getActivity()).getUsername());

        return root;
    }

    private void openAlertDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setMessage("Your Enquiry Is Already Submitted!!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, homeFragment);
                ft.addToBackStack(null);
                ft.commit();
                dialog.dismiss();
                getActivity().finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void cheloging() {
        if (UserSession.getIsLogin(mainActivity)) {

//            MainActivity.linearout.setVisibility(View.GONE);
            getclassdetails();
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
                                      //  Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
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

        } else {

        }
    }

    private void opendreloginorprofile() {
        if (UserSession.getIsLogin(getActivity())) {

            try {
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("cmid", classData);
                profileFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, profileFragment);
                ft.addToBackStack(null);
                ft.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                MorningClassDetails2Fragment morningClassDetailFragment = new MorningClassDetails2Fragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("cmid", classData);
                morningClassDetailFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, morningClassDetailFragment);
                ft.addToBackStack(null);
                ft.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void hidefooter() {
        MainActivity.linearlayout.setVisibility(View.VISIBLE);
    }
    // List<ClassData> classDataList=new ArrayList<>();

    private void getclassdetails() {

        goalsidtext2.setText(classData.getGoalID());
        classname02.setText(classData.getClassname());
        bredcrumbs02.setText(classData.getBredcrumbs());
        onlnsetstext02.setText(classData.getOnlineclassseats());

       // offlnsetstext02.setText(classData.getOfflineclassseats());
        onlnfeetext02.setText("₹"+classData.getOnlineclassfee());
        paymettext02.setText("₹"+classData.getOnlineclassfee());
        //offlnfeetext02.setText(classData.getOfflineclassfee());

        clsdatetxt02.setText(classData.getStartdate());
        clastimtext02.setText(classData.getStarttime());
        aboutclas02.setText(classData.getRemarks());

    }
}