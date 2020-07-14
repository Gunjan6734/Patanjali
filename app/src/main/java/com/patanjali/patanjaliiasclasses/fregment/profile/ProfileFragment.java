package com.patanjali.patanjaliiasclasses.fregment.profile;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.MainActivity;
import com.patanjali.patanjaliiasclasses.fregment.freedownloads.FreeDowloadsFragment;
import com.patanjali.patanjaliiasclasses.fregment.home.HomeFragment;
import com.patanjali.patanjaliiasclasses.fregment.morningclass.MorningClassFragment;
import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.model.Detail;
import com.patanjali.patanjaliiasclasses.model.State_Model;
import com.patanjali.patanjaliiasclasses.model.Response_Model;
import com.patanjali.patanjaliiasclasses.retrofit_service.NetworkManager;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;
import com.patanjali.patanjaliiasclasses.service.GetData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView goalsidtext04,classname04,bredcrumbs04,onlinefeeamount,onlineseetcount,teachertext,classescount,
            subjecttext,clsdatetxt04,clastimtext04,selectstatetext,meetingid2;

    Button continubybuton;
    EditText yournumber,fstnameedttext,lastnameedttext,emailedttext,cityedittext,addresedttext,pincodeedttext;

    ListView recyclerViewstate;
    List<State_Model> statelist_models;
    String Pyournumber,Pfstnameedttext,Plastnameedttext,Pemailedttext,Pcityedittext,Paddresedttext,Ppincodeedttext;

    //SharedPreference Use
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARD_PREF = "GLOBLE_Application";
    String Username="";
    String Usertoken="";
    String cmid="";
    Config ut;
    ProgressDialog progressDialog;
    UserSession userSession;
    MainActivity mainActivity;

    ClassData classData;
    ArrayList<Detail> detailArrayLists=new ArrayList<>();


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_profile, container, false);

        mainActivity= (MainActivity) getActivity();
        userSession = new UserSession(getActivity());

        goalsidtext04=root.findViewById(R.id.goalsidtext04);
        bredcrumbs04=root.findViewById(R.id.bredcrumbs04);
        classname04=root.findViewById(R.id.classname04);
        onlinefeeamount=root.findViewById(R.id.onlinefeeamount);
        onlineseetcount=root.findViewById(R.id.onlineseetcount);
        teachertext=root.findViewById(R.id.teachertext);
        subjecttext=root.findViewById(R.id.subjecttext);
        classescount=root.findViewById(R.id.classescount);
        clsdatetxt04=root.findViewById(R.id.clsdatetxt04);
        clastimtext04=root.findViewById(R.id.clastimtext04);
        meetingid2=root.findViewById(R.id.meetingid2);

        selectstatetext=root.findViewById(R.id.selectstatetext);
        yournumber=root.findViewById(R.id.yournumber);
        fstnameedttext=root.findViewById(R.id.fstnameedttext);
        lastnameedttext=root.findViewById(R.id.lastnameedttext);
        emailedttext=root.findViewById(R.id.emailedttext);
        cityedittext=root.findViewById(R.id.cityedittext);
        addresedttext=root.findViewById(R.id.addresedttext);
        pincodeedttext=root.findViewById(R.id.pincodeedttext);
        continubybuton=root.findViewById(R.id.continubybuton);

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

        selectstatetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openspinerdialog();
            }
        });

        Log.d("YourNumber",new UserSession(getActivity()).getMobile() +" "+UserSession.getIsLogin(mainActivity));
        if (UserSession.getIsLogin(mainActivity)) {
            yournumber.setText(new UserSession(getActivity()).getMobile());
            yournumber.setFocusable(false);
        }

        continubybuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validation used ...
                if (fstnameedttext.getText().toString().equalsIgnoreCase("")) {
                    fstnameedttext.requestFocus();
                    fstnameedttext.setError("Please Enter First Name");
                    return;
                }
                if (lastnameedttext.getText().toString().equalsIgnoreCase("")) {
                    lastnameedttext.requestFocus();
                    lastnameedttext.setError("Please Enter Last Name");
                    return;
                } else {
                    callingpostapi();
                }
            }
        });

        getpprofiledetail();
        hidefooter();
        getmeetingid();
        cheloging();
        MainActivity.txtProfileName.setText(new UserSession(getActivity()).getMobile());
        MainActivity.userproname.setText(new UserSession(getActivity()).getUsername());
        // MainActivity.golsidtext.setText(new UserSession(getActivity()).getGolsid());

        statelist_models=new ArrayList<State_Model>();

        return root;
    }

    private void cheloging() {
        if (UserSession.getIsLogin(mainActivity)) {

//            MainActivity.linearout.setVisibility(View.GONE);
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

        }else {

        }
    }

    private void getpprofiledetail() {
        String userdetailurl=ut.BASE_URL +"Getprofile/"+ut.Securetkey+"/" +yournumber.getText().toString();
        Log.e("userdetailurl",userdetailurl);
        new GetData(getActivity(), new JSONObject(), userdetailurl, new OnTaskCompleted<String>() {
            @Override
            public void onTaskCompleted(String response) {
                Log.e("response",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String fname = jsonObject.getString("fname");
                        String lname = jsonObject.getString("lname");
                        String mobile = jsonObject.getString("mobile");
                        String emailid = jsonObject.getString("emailid");
                        String stateid = jsonObject.getString("stateid");
                        String statename = jsonObject.getString("statename");
                        String city = jsonObject.getString("city");
                        String pincode = jsonObject.getString("pincode");
                        String address = jsonObject.getString("address");
                        Log.e("response", response.toString());

                        if (stateid.equals("0")){
                            // Toast.makeText(getActivity(), "New Number", Toast.LENGTH_SHORT).show();
                        }else {
                            fstnameedttext.setText(fname);
                            lastnameedttext.setText(lname);
                            emailedttext.setText(emailid);
                            cityedittext.setText(city);
                            addresedttext.setText(address);
                            pincodeedttext.setText(pincode);
                            selectstatetext.setText(statename);
                        }

                       /* if (stateid.equals("0")){
                            selectstatetext.setVisibility(View.VISIBLE);
                        }else {
                            selectstatetext.setText(statename);
                        }*/
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },true).execute();
    }

    private void getmeetingid() {
        meetingid2.setText(classData.getMeetingID());
        // MainActivity.golsidtext.setText(classData.getGoalID());
        // MainActivity.golsidtext.setText(new UserSession(getActivity()).getGolsid());
        goalsidtext04.setText(classData.getGoalID());
        classname04.setText(classData.getClassname());
        bredcrumbs04.setText(classData.getBredcrumbs());
        onlineseetcount.setText(classData.getOnlineclassseats());
        onlinefeeamount.setText("₹"+classData.getOnlineclassfee());
        clsdatetxt04.setText(classData.getStartdate());
        clastimtext04.setText(classData.getStarttime());
    }

    private void hidefooter() {
        MainActivity.linearlayout.setVisibility(View.VISIBLE);
    }

    private void callingpostapi() {
        sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
        Usertoken = sharedPreferences.getString("token", null);
        Log.e("token", Usertoken);

        //use for  shared preferences store data...
        final String profilnumber = yournumber.getText().toString();
        final String profilfstname = fstnameedttext.getText().toString();
        final String profillastname = lastnameedttext.getText().toString();
        final String profileemailid = emailedttext.getText().toString();
        final String profilcity = cityedittext.getText().toString();
        final String profiladddress = addresedttext.getText().toString();
        final String profilepincode = pincodeedttext.getText().toString();
        Log.e("response", toString());

        Pyournumber = yournumber.getText().toString();
        Pfstnameedttext = fstnameedttext.getText().toString();
        Plastnameedttext = lastnameedttext.getText().toString();
        Pemailedttext = emailedttext.getText().toString();
        Pcityedittext = cityedittext.getText().toString();
        Paddresedttext = addresedttext.getText().toString();
        Ppincodeedttext = pincodeedttext.getText().toString();

        String profileurl = ut.BASE_URL +"Payment/"+ut.Securetkey;
        Log.e("profileurl", profileurl);
        try {

            //Here the json data is add to a hash map with key data
            Map<String,String> params = new HashMap<String, String>();

            params.put("meetingid", classData.getMeetingID());
            params.put("BillMobile", yournumber.getText().toString());
            params.put("billfname", profilfstname);
            params.put("billlname", profillastname);
            params.put("billEmail", profileemailid);
            params.put("billstateid", SelectedId);
            params.put("billcity", profilcity);
            params.put("billaddress", profiladddress);
            params.put("billpincode", profilepincode);
//            MainActivity.userproname.setText(profilfstname);
            Log.e("response", toString());

            Call<List<Response_Model>> listCall= NetworkManager.getInstance().getApiServices().ValidateMobile("Bearer "+Usertoken,
                    params);
            listCall.enqueue(new Callback<List<Response_Model>>() {
                @Override
                public void onResponse(Call<List<Response_Model>> call, Response<List<Response_Model>> response) {
                    if (response.isSuccessful() && response.body().size()>0){
                        Log.e("response", response.toString());
                        /*try {
                            JSONArray jsonArray=new JSONArray(response);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String message = jsonObject.getString("message");
                               // oprndialog(message);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/

                        fstnameedttext.getText().clear();
                        lastnameedttext.getText().clear();
                        emailedttext.getText().clear();
                        cityedittext.getText().clear();
                        addresedttext.getText().clear();
                        pincodeedttext.getText().clear();
                        oprndialog();
                        UserSession userSession = new UserSession(getActivity());
                        userSession.setUsername(profilfstname);
                        MainActivity.userproname.setText(userSession.getUsername());
                        MainActivity.userproname.setText(profilfstname);

                    }else {
                        //Toast.makeText(getActivity(), "Profile Not Save", Toast.LENGTH_SHORT).show();
                        oprndialog2();
                    }
                }

                @Override
                public void onFailure(Call<List<Response_Model>> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void oprndialog2() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Payment Status");
        builder.setMessage("Please Try Again Something Wrong");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //dialog gone..
                dialog.dismiss();
            }
        });
        // create the dialog and show it..
        builder.create().show();
    }

    Dialog dialog2;
    private void oprndialog() {
        dialog2 = new Dialog(getActivity());
        dialog2.setContentView(R.layout.paymentsuccessdialogbox);
        final TextView oktextview = dialog2.findViewById(R.id.oktextview);

        oktextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               redirecthomepage();
            }
        });

        dialog2.show();
    }

    private void redirecthomepage() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.nav_host_fragment, homeFragment);
        ft.addToBackStack(null);
        ft.commit();
        dialog2.dismiss();
    }

    Dialog dialog;
    private void openspinerdialog() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.statelistitem_dialog);
        recyclerViewstate =  dialog.findViewById(R.id.recyclerViewstate);

        getlistview();
        dialog.show();
    }

    List<String> dataList=new ArrayList<>();
    String SelectedId="0";
    private void getlistview() {
        sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
        Username = sharedPreferences.getString("name", null);
        Usertoken = sharedPreferences.getString("token", null);
        Log.e("token", Usertoken + Username);

        String approveurl =ut.BASE_URL +"Datavalue/state/"+ut.Securetkey;
        new GetData(getActivity(), new JSONObject(), approveurl, new OnTaskCompleted<String>() {
            @Override
            public void onTaskCompleted(String response) {
                Log.e("token",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String value = jsonObject.getString("value");
                        String data = jsonObject.getString("data");
                        Log.e("UserId", response.toString());

                        State_Model state_model = new State_Model(value, data);
                        statelist_models.add(state_model);
                        dataList.add(data);
                    }
                    ArrayAdapter<String> stateListAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_single_choice ,dataList);
                    recyclerViewstate.setAdapter(stateListAdapter);
                    recyclerViewstate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SelectedId=statelist_models.get(position).getData();
                            selectstatetext.setText(statelist_models.get(position).getValue());

                            dialog.dismiss();
                            // Toast.makeText(getActivity(), "Value is "+SelectedId+"\nData is "+statelist_models.get(position).getData(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },true).execute();
    }
}
