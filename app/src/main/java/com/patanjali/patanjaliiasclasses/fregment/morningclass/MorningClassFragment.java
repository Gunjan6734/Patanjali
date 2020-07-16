package com.patanjali.patanjaliiasclasses.fregment.morningclass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.MainActivity;
import com.patanjali.patanjaliiasclasses.adapter.MoringClassAdapter;
import com.patanjali.patanjaliiasclasses.fregment.freedownloads.FreeDowloadsFragment;
import com.patanjali.patanjaliiasclasses.fregment.home.HomeFragment;
import com.patanjali.patanjaliiasclasses.fregment.mornningclasdetail.MorningClassDetailFragment;
import com.patanjali.patanjaliiasclasses.fregment.recordingvedio.VediosPlayFragment;
import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.model.LiveClass_Model;
import com.patanjali.patanjaliiasclasses.model.LiveclassModel;
import com.patanjali.patanjaliiasclasses.model.MorningClas_Model;
import com.patanjali.patanjaliiasclasses.model.Spinerlist_Model;
import com.patanjali.patanjaliiasclasses.retrofit_service.NetworkManager;
import com.patanjali.patanjaliiasclasses.service.GetDataCheckStatus;
import com.patanjali.patanjaliiasclasses.service.GetLiveData;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;
import com.patanjali.patanjaliiasclasses.service.PostData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.patanjali.patanjaliiasclasses.R.drawable.notificatonicon;
import static com.patanjali.patanjaliiasclasses.activity.MainActivity.golsidtext;

public class MorningClassFragment extends Fragment implements MoringClassAdapter.Onitemclick {

    private MorningViewModel galleryViewModel;

    //SharedPreference Use
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARD_PREF = "GLOBLE_Application";
    String Usertoken="";
    String mMobilenumber="";
    String goalID="";
    String message="";
    Config ut;
    ProgressDialog progressDialog;
    MainActivity mainActivity;
    private Handler handler;

    RecyclerView recyclerViewmrngcls;
    private List<MorningClas_Model> morningClas_modelList;
    private List<ClassData> classDataList;
    // private List<ClassData> morningClas_modelList;

    ListView recyclerViewlist;
    List<Spinerlist_Model> spinerlist_models;
    UserSession userSession;
    private CountDownTimer timer;
    private Context context;
    private ImageButton notification;
    private ImageView home;

    private List<LiveClass_Model>liveClass_modelList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(MorningViewModel.class);
        View root = inflater.inflate(R.layout.fragment_morningclass, container, false);
        mainActivity= (MainActivity) getActivity();
        userSession = new UserSession(getActivity());

        recyclerViewmrngcls=root.findViewById(R.id.recyclerViewmrngcls);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewmrngcls.setLayoutManager(mLayoutManager);
        recyclerViewmrngcls.setItemAnimator(new DefaultItemAnimator());
        context=getActivity();

        //home icon checker

        notification=mainActivity.findViewById(R.id.notifyimage);
        home=mainActivity.findViewById(R.id.homepage);

        if(sharedPreferences!=null)
        {
            sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
            Usertoken = sharedPreferences.getString("token", null);
            mMobilenumber = sharedPreferences.getString("mMobilenumber",null);
        }
        sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, MODE_PRIVATE);

        if(sharedPreferences.getBoolean("homechecker",false))
        {
            notification.setVisibility(View.GONE);
            home.setVisibility(View.VISIBLE);
        }
        else
        {
            notification.setVisibility(View.VISIBLE);
            home.setVisibility(View.GONE);
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeFragment morningClassFragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, morningClassFragment);
                ft.addToBackStack(null);
                ft.commit();
                editor = sharedPreferences.edit();
                editor.putBoolean("homechecker",false);
                editor.apply();

            }
        });


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


       /* final Handler h;
        h = new Handler();
        new Thread(new Runnable() {
            public void run(){
                while(true){
                    try{
                        h.post(new Runnable(){
                            public void run(){
                                cheloging();
                            }
                        });
                        TimeUnit.SECONDS.sleep(5);
                    }
                    catch(Exception ex){
                    }
                }
            }
        }).start();*/

        morningClas_modelList = new ArrayList<>();
        liveClass_modelList=new ArrayList<>();

        // viewmorningdata();
        hidefooter();
        cheloging();
        spinerlist_models=new ArrayList<Spinerlist_Model>();
        MainActivity.txtProfileName.setText(new UserSession(getActivity()).getMobile());
        MainActivity.userproname.setText(new UserSession(getActivity()).getUsername());

        return root;
    }

    private void cheloging() {

        if (UserSession.getIsLogin(mainActivity)) {
         //MainActivity.linearout.setVisibility(View.GONE);
            viewmorningdatawithlogin();
            golsidtext.setVisibility(View.VISIBLE);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_mylibrary).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_aboutus).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_yourprofile).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_contact).setVisible(true);
            mainActivity.navigationView.getMenu().findItem(R.id.nav_logoutfregment).setVisible(true);

            try {
                new GetDataCheckStatus(getActivity(), new JSONObject(), ut.BASE_URL +"Checklogin/"+ut.Securetkey+"/"+ SaveSharedPreference.getUserId(),
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
                                        //Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
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

            viewmorningdata();
            golsidtext.setVisibility(View.GONE);

        }
    }

    // List<ClassData> classDataList=new ArrayList<>();

    private void viewmorningdatawithlogin() {

            sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
            Log.d("jnskdnks",""+sharedPreferences);
            Usertoken = sharedPreferences.getString("token", null);
            mMobilenumber = sharedPreferences.getString("mMobilenumber",null);
        // Toast.makeText(mainActivity, "mobile: "+ SaveSharedPreference.getUserId(), Toast.LENGTH_SHORT).show();

        String myliburl=ut.BASE_URL +"Onlinecourses/"+ut.Securetkey;
        Log.e("myliburl",myliburl);
        try {
            //Here the json data is add to a hash map with key data
            Map<String,String> params = new HashMap<String, String>();

            params.put("userid",SaveSharedPreference.getUserId());
            Log.e("response", toString());

            Call<ArrayList<ClassData>> listCall= NetworkManager.getInstance().getApiServices().getMyLibrary("Bearer "+Usertoken,
                    params);
            listCall.enqueue(new Callback<ArrayList<ClassData>>() {
                @Override
                public void onResponse(Call<ArrayList<ClassData>> call, Response<ArrayList<ClassData>> response) {
                    if (response.isSuccessful() && response.body().size()>0){
                        Log.e("responsewithlogin", response.toString());
                        classDataList=new ArrayList<>();
                        classDataList=response.body();
                        /*for (ClassData morningClas_model : response.body()){
                            if (morningClas_model.getOnlinepayment().equals("True")){
                                classDataList.add(morningClas_model);
                            }
                        }*/
                        MoringClassAdapter moringClassAdapter= new MoringClassAdapter(getActivity(),classDataList,MorningClassFragment.this);
                        recyclerViewmrngcls.setAdapter(moringClassAdapter);
                    }else {
                       Toast.makeText(mainActivity, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ClassData>> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hidefooter() {
        MainActivity.linearlayout.setVisibility(View.VISIBLE);
    }

    List<LiveClass_Model>liveClass_modelLists=new ArrayList<>();

    private void viewmorningdata() {
        String morngurl=ut.BASE_URL +"Onlinecourses/"+ut.Securetkey;
        Log.e("morngurl",morngurl);
        new PostData(getActivity(), new JSONObject(), morngurl, new OnTaskCompleted<String>() {
            @Override
            public void onTaskCompleted(String response) {
                Log.e("response",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String cmid = jsonObject.getString("cmid");
                        String classname = jsonObject.getString("classname");
                        String bredcrumbs = jsonObject.getString("bredcrumbs");
                        String onlineclassseats = jsonObject.getString("onlineclassseats");
                        String offlineclassseats = jsonObject.getString("offlineclassseats");
                        String onlineclassfee = jsonObject.getString("onlineclassfee");
                        String offlineclassfee = jsonObject.getString("offlineclassfee");
                        String startdate = jsonObject.getString("startdate");
                        String starttime = jsonObject.getString("starttime");
                        String classdescription = jsonObject.getString("classdescription");
                        String goalID = jsonObject.getString("goalID");
                        String meetingID = jsonObject.getString("meetingID");
                        String livestreaming=jsonObject.getString("livestreaming");
                        String offlinepayment=jsonObject.getString("offlinepayment");
                        String onlinepayment=jsonObject.getString("onlinepayment");
                        Log.e("onlinepayment", response.toString());

                        sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("cmid",cmid);
                        editor.putString("classname",classname);
                        editor.putString("bredcrumbs", bredcrumbs);
                        editor.putString("onlineclassseats", onlineclassseats);
                        editor.putString("offlineclassseats", offlineclassseats);
                        editor.putString("onlineclassfee", onlineclassfee);
                        editor.putString("offlineclassfee",offlineclassfee);
                        editor.putString("startdate",startdate);
                        editor.putString("starttime",starttime);
                        editor.putString("classdescription",classdescription);
                        editor.putString("goalID",goalID);
                        editor.putString("meetingID",meetingID);
                        editor.apply();
                        Log.e("storeId", toString());

                        MorningClas_Model morningClas_model = new MorningClas_Model(cmid,classname, bredcrumbs, onlineclassseats,
                                offlineclassseats, onlineclassfee, offlineclassfee, startdate, starttime, classdescription,goalID,
                                livestreaming,offlinepayment,onlinepayment,meetingID);
                        morningClas_modelList.add(morningClas_model);
                    }
                    classDataList= Arrays.asList(new Gson().fromJson(response,ClassData[].class));
                    MoringClassAdapter moringClassAdapter = new MoringClassAdapter(getActivity(),classDataList,MorningClassFragment.this);
                    recyclerViewmrngcls.setAdapter(moringClassAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },true).execute();
    }

    @Override
    public void itemclick(int position) {

        try {
            MorningClassDetailFragment morningClassDetailFragment = new MorningClassDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("cmid", classDataList.get(position));
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

    @Override
    public void startRecord(int position) {

        try {
           /* VediosPlayFragment vedioPlayerFragment = new VediosPlayFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.addToBackStack("MorningClassFragment");
            ft.add(R.id.nav_host_fragment, vedioPlayerFragment, "MorningClassFragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();*/

            VediosPlayFragment vedioPlayerFragment = new VediosPlayFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.replace(R.id.nav_host_fragment, vedioPlayerFragment);
            ft.addToBackStack(null);
            ft.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {

       // liveClassAPi();

        timer = new CountDownTimer(86400000 , 20000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if((UserSession.getIsLogin(mainActivity)))
                {
                    liveClassAPi();
                }
            }

            @Override
            public void onFinish() {
                try{

                }catch(Exception e){
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();


        if((UserSession.getIsLogin(mainActivity)))
        {
            liveClassAPi();
        }
        super.onStart();

    }

    private void liveClassAPi( ) {


        if((UserSession.getIsLogin(mainActivity))) {

        final String posturl = ut.BASE_URL +"Liveclasses"+"/"+ut.Securetkey+"/"+SaveSharedPreference.getUserId();

        new GetLiveData(mainActivity, new JSONObject(), posturl, new OnTaskCompleted<String>() {
            @Override
            public void onTaskCompleted(String response) {
                Log.e("responsedlive",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String status = jsonObject.getString("meeting");
                        Log.e("displaydata",status);
                        if(status.equalsIgnoreCase("0"))
                        {
                            if(UserSession.getIsLogin(mainActivity))
                                {
                                    viewmorningdatawithlogin();
                                }
                        }
                        else
                            {
                                if (UserSession.getIsLogin(mainActivity)) {
                                    viewmorningdatawithlogin();

                                    Log.e("displaydatahello","djsbdjsbjs");
                                }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).execute();
    }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            Log.i("Mainjdjdjkjkd", "cancel timer");
            timer = null;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            Log.i("Mainjdjdjkjkd", "cancel timer");
            timer = null;
        }
    }
}