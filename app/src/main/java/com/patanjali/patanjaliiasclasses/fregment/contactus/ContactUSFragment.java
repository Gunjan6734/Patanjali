package com.patanjali.patanjaliiasclasses.fregment.contactus;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.MainActivity;
import com.patanjali.patanjaliiasclasses.service.GetData;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.patanjali.patanjaliiasclasses.activity.MainActivity.golsidtext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUSFragment extends Fragment {

    TextView callingtest,whattsaptext;

    //SharedPreference Use
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARD_PREF = "GLOBLE_Application";
    String Usertoken="";
    Config ut;
    MainActivity mainActivity;
    UserSession userSession;

    public ContactUSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_contact_u, container, false);

        mainActivity= (MainActivity) getActivity();
        userSession = new UserSession(getActivity());

        callingtest=root.findViewById(R.id.callingtest);
        whattsaptext=root.findViewById(R.id.whattsaptext);

        callingtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callintent=new Intent(Intent.ACTION_DIAL);
                callintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                callintent.setData(Uri.parse("tel:"+callingtest.getText().toString()));
                startActivity(callintent);
            }
        });

       /* whattsaptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hiii i am hare help me");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(sendIntent);
                }else {
                    Toast.makeText(getActivity(),"whatsapp not install please install whatsapp", Toast.LENGTH_LONG).show();
                }*//*

               *//* String url = "https://api.whatsapp.com/send?phone="+whattsaptext;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);*//*
            }
        });*/

        hidefooter();
        cheloging();

        return root;
    }

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

    private void hidefooter() {
        MainActivity.linearlayout.setVisibility(View.VISIBLE);
    }

}
