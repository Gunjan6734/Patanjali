package com.patanjali.patanjaliiasclasses.fregment.mylibrary;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.MainActivity;
import com.patanjali.patanjaliiasclasses.adapter.MyLibraryAdapter;
import com.patanjali.patanjaliiasclasses.fregment.freedownloads.FreeDowloadsFragment;
import com.patanjali.patanjaliiasclasses.fregment.morningclass.MorningClassFragment;
import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.model.LiveClass_Model;
import com.patanjali.patanjaliiasclasses.model.MorningClas_Model;
import com.patanjali.patanjaliiasclasses.model.Spinerlist_Model;
import com.patanjali.patanjaliiasclasses.retrofit_service.NetworkManager;
import com.patanjali.patanjaliiasclasses.service.GetData;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.patanjali.patanjaliiasclasses.activity.MainActivity.golsidtext;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLibraryFragment extends Fragment {

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

    TextView textcontent;
    RecyclerView recyclerViemylibrary;
    private List<ClassData> morningClas_modelList;

    ListView recyclerViewlist;
    List<Spinerlist_Model> spinerlist_models;
    MainActivity mainActivity;
    UserSession userSession;

    public MyLibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_my_library, container, false);

        mainActivity= (MainActivity) getActivity();
        userSession = new UserSession(getActivity());

        textcontent=root.findViewById(R.id.textcontent);
        recyclerViemylibrary=root.findViewById(R.id.recyclerViemylibrary);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViemylibrary.setLayoutManager(mLayoutManager);
        recyclerViemylibrary.setItemAnimator(new DefaultItemAnimator());

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

        morningClas_modelList = new ArrayList<>();

        viewmylibrarydata();
        hidefooter();
        cheloging();
        spinerlist_models=new ArrayList<Spinerlist_Model>();
        MainActivity.txtProfileName.setText(new UserSession(getActivity()).getMobile());
        MainActivity.userproname.setText(new UserSession(getActivity()).getUsername());


        return root;
    }

    private void hidefooter() {
        MainActivity.linearlayout.setVisibility(View.VISIBLE);
    }

    private void cheloging() {
        if (UserSession.getIsLogin(mainActivity)) {

            golsidtext.setVisibility(View.VISIBLE);
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

    List<ClassData> classDataList=new ArrayList<>();
    List<LiveClass_Model>liveClass_modelLists=new ArrayList<>();
    private void viewmylibrarydata() {
        sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
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
                        Log.e("response", response.toString());
                       /* classDataList=new ArrayList<>();
                        classDataList=response.body();*/
                        for (ClassData morningClas_model : response.body()){
                            if (morningClas_model.getOnlinepayment().equals("True")){

                                morningClas_modelList.add(morningClas_model);
                            }
                        }
                        MyLibraryAdapter myLibraryAdapter = new MyLibraryAdapter(getActivity(),morningClas_modelList);
                        recyclerViemylibrary.setAdapter(myLibraryAdapter);
                        if (morningClas_modelList.size()==0){
                            textcontent.setVisibility(View.VISIBLE);
                            recyclerViemylibrary.setVisibility(View.GONE);
                        }else {
                            textcontent.setVisibility(View.GONE);
                            recyclerViemylibrary.setVisibility(View.VISIBLE);
                        }

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
}
