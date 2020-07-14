package com.patanjali.patanjaliiasclasses.fregment.home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.text.TextUtilsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.GooglePlayStoreAppVersionNameLoader;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.VersionCheckListner;
import com.patanjali.patanjaliiasclasses.activity.MainActivity;
import com.patanjali.patanjaliiasclasses.activity.SplaceActivity;
import com.patanjali.patanjaliiasclasses.adapter.SpineerListAdapter;
import com.patanjali.patanjaliiasclasses.fregment.freedownloads.FreeDowloadsFragment;
import com.patanjali.patanjaliiasclasses.fregment.morningclass.MorningClassFragment;
import com.patanjali.patanjaliiasclasses.fregment.notification.NotificationFragment;
import com.patanjali.patanjaliiasclasses.fregment.recordingvedio.VediosPlayFragment;
import com.patanjali.patanjaliiasclasses.fregment.videoplay.VedioPlayerFragment;
import com.patanjali.patanjaliiasclasses.model.Spinerlist_Model;
import com.patanjali.patanjaliiasclasses.networkservice.NetworkStateReceiver;
import com.patanjali.patanjaliiasclasses.service.GetDataCheckStatus;
import com.patanjali.patanjaliiasclasses.service.GetLiveData;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;
import com.patanjali.patanjaliiasclasses.service.GetData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.patanjali.patanjaliiasclasses.activity.MainActivity.golsidtext;
import static com.patanjali.patanjaliiasclasses.activity.MainActivity.mobilenumber;
//import static com.patanjali.patanjaliiasclasses.activity.MainActivity.nextimage;
import static com.patanjali.patanjaliiasclasses.activity.MainActivity.nextimagebtn;
import static com.patanjali.patanjaliiasclasses.activity.MainActivity.notifyimage;
//import static com.patanjali.patanjaliiasclasses.activity.MainActivity.selectitemetdtext;
//import static com.patanjali.patanjaliiasclasses.activity.MainActivity.selectitemetdtext202;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CountDownTimer timer;
    private int check=1;
    private Context context;
    int doubleBackToExitPressed = 1;

    CardView cartid,cartid2,cartid3,cartid4,cartid5,cartid6;
    ImageView whatsapimage,callingimage;
    TextView otptext,callingnumtext,whatsapnumtext;
    private  int forgotPasswordCheck=0;
    private int dialogChecker=0;

    ListView recyclerViewlist;
    List<Spinerlist_Model> spinerlist_models;
    SpineerListAdapter mAdapter;
    String EMobilenumber,msetpasswordedttext,mconfirmsetpasswordedttext,Mregisterpassedttext,mregistermobilenumber,mregisterotpedttext;

    private NetworkStateReceiver networkStateReceiver;


    //SharedPreference Use
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARD_PREF = "GLOBLE_Application";
    String Username="";
    String applogo="";
    String appimage="";
    String Usertoken="";
    String goalid="";
    String message="";
    String data="";
    Config ut;
    ProgressDialog progressDialog;
    MainActivity mainActivity;
    UserSession userSession;
    private static final long START_TIME_IN_MILLIS=30000;
    private CountDownTimer countDownTimer;
    private boolean mTimerRunning;
    private long mTimerLeftInMilllis=START_TIME_IN_MILLIS;
    private ImageView live;
    private ImageButton notification;
    private ImageView home;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // MainActivity.toolbar.setTitle("Leave History");
        //progressDialog=new ProgressDialog(getContext());
        mainActivity= (MainActivity) getActivity();
        userSession = new UserSession(getActivity());
        //linearout.setVisibility(View.VISIBLE);
        context=getActivity();
        sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, Context.MODE_PRIVATE);
        notification=mainActivity.findViewById(R.id.notifyimage);
        home=mainActivity.findViewById(R.id.homepage);

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


        //connection chewcking
        if (UserSession.getIsLogin(mainActivity)){

        }else {
            openlogindialogbox();
        }
            new GooglePlayStoreAppVersionNameLoader(getActivity(), new VersionCheckListner() {
            @Override
            public void onGetResponse(boolean isUpdateAvailable) {
                if (isUpdateAvailable){
                    openGoToPlayStoreAlert();
                }
            }
        }).execute();

        new SaveSharedPreference(getActivity());
        live=root.findViewById(R.id.live);
        cartid=root.findViewById(R.id.cartid);
        cartid2=root.findViewById(R.id.cartid2);
        cartid3=root.findViewById(R.id.cartid3);
        cartid4=root.findViewById(R.id.cartid4);
        cartid5=root.findViewById(R.id.cartid5);
        cartid6=root.findViewById(R.id.cartid6);
        callingimage=root.findViewById(R.id.callingimage);
        whatsapimage=root.findViewById(R.id.whatsapimage);
        whatsapnumtext=root.findViewById(R.id.whatsapnumtext);
        callingnumtext=root.findViewById(R.id.callingnumtext);
       // otptext=root.findViewById(R.id.otptext);

        callingimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callintent=new Intent(Intent.ACTION_DIAL);
                callintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                callintent.setData(Uri.parse("tel:"+callingnumtext.getText().toString()));
                startActivity(callintent);
            }
        });

        cartid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MorningClassFragment morningClassFragment = new MorningClassFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, morningClassFragment);
                ft.addToBackStack(null);
                ft.commit();
                editor = sharedPreferences.edit();
                editor.putBoolean("homechecker",true);
                editor.apply();
                Log.d("booleanchecker",""+sharedPreferences.getBoolean("homechecker",false));
            }
        });

       /* cartid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VediosPlayFragment vedioPlayerFragment = new VediosPlayFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, vedioPlayerFragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });*/

        cartid6.setOnClickListener(new View.OnClickListener() {
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

       /* mainActivity.notifyimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationFragment notificationFragment = new NotificationFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.nav_host_fragment, notificationFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });*/

       /* selectitemetdtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openspinerdialog();
            }
        });

        selectitemetdtext202.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openspinerdialog();
            }
        });*/

        /*nextimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mobilenumber.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(),  message, Toast.LENGTH_SHORT).show();
                   // mobilenumber.requestFocus();
                   // mobilenumber.setError("Please Enter Mobile No.");
                    Log.d("MobileNumber", SelectedId);
                    return;

                } else if (mobilenumber.length()==10){

                    Log.d("OpenField", SelectedId+"");
                    openotpfield();
                }else {
                    Log.d("ToastValid", SelectedId+"");
                    Toast.makeText(getActivity(), "Enter Valid Mobile No.!" + message, Toast.LENGTH_SHORT).show();
                }
                }
        });*/

       /* MainActivity. otpedttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("after", String.valueOf(after));
                Log.d("count1", String.valueOf(count));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("start", String.valueOf(start));
                Log.d("s", String.valueOf(s));
                if (s.length() == 6) {
                    //findPlaceWaypoints2();
                    try {
                        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                        MainActivity.otpedttext.requestFocus();
                        inputManager.hideSoftInputFromWindow(MainActivity.otpedttext.getWindowToken(),
                                InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            //findPlaceWaypoints();
            }
        });*/

        /*nextimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.otpedttext.getText().toString().equalsIgnoreCase(""))
                {
                    MainActivity.otpedttext.requestFocus();
                    MainActivity.otpedttext.setError("Please enter OTP");
                }else {
                    postotpnumber();
                }
            }
        });*/

        /* mobilenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("after", String.valueOf(after));
                Log.d("count1", String.valueOf(count));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("start", String.valueOf(start));
                Log.d("s", String.valueOf(s));
                if (s.length() == 10) {
                    findPlaceWaypoints();
                    try {
                        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                        mobilenumber.requestFocus();
                        inputManager.hideSoftInputFromWindow(mobilenumber.getWindowToken(),
                                InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                findPlaceWaypoints();
            }
        });*/

        MainActivity.txtProfileName.setText(new UserSession(getActivity()).getMobile());
        MainActivity.userproname.setText(new UserSession(getActivity()).getUsername());

       // golsidtext.setText(new UserSession(getActivity()).getGolsid());
       // MainActivity.golsidtext.setText(data);

        final Handler h;
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
                                    TimeUnit.MINUTES.sleep(1);
                        }
                        catch(Exception ex){
                        }
                    }
                }
            }).start();



        hidefooter();
      // cheloging();
       spinerlist_models=new ArrayList<Spinerlist_Model>();

        return root;
    }

    Dialog dialog3;
    TextView resendotptimer;
    TextView resendotp;
    private void openlogindialogbox() {

        dialog3 = new Dialog(getActivity());
        dialog3.setContentView(R.layout.logindialogbox_layout);
        final RelativeLayout loginrelativouyt =dialog3.findViewById(R.id.loginrelativouyt);
        final RelativeLayout setpassrelativouyt =dialog3.findViewById(R.id.setpassrelativouyt);
        final EditText registermobilenumber = dialog3.findViewById(R.id.registermobilenumber);
        final EditText registerotpedttext=dialog3.findViewById(R.id.registerotpedttext);
        final EditText setloginpassword=dialog3.findViewById(R.id.setloginpassword);
        final EditText setloginotppassword=dialog3.findViewById(R.id.setloginotppassword);
        final TextView registeritemetdtext=dialog3.findViewById(R.id.registeritemetdtext);
        final TextView textregister=dialog3.findViewById(R.id.textregister);
        final TextView textregsetpass=dialog3.findViewById(R.id.textregsetpass);
        final TextView forgetpassword=dialog3.findViewById(R.id.forgetpassword);
        resendotp=dialog3.findViewById(R.id.resendotp);
        resendotptimer=dialog3.findViewById(R.id.resendotptimer);
        final EditText setpasswordedttext=dialog3.findViewById(R.id.setpasswordedttext);
        final EditText confirmsetpasswordedttext=dialog3.findViewById(R.id.confirmsetpasswordedttext);
        final Button loginbutton = dialog3.findViewById(R.id.loginbutton);
        final Button registerbutton=dialog3.findViewById(R.id.registerbutton);
        final Button setpassbutton=dialog3.findViewById(R.id.setpassbutton);
        final Button cancelbutton=dialog3.findViewById(R.id.cancelbutton);
        final Button setolduserpassbutton=dialog3.findViewById(R.id.setolduserpassbutton);
        final Button registerbuttonoldusr=dialog3.findViewById(R.id.registerbuttonoldusr);
        final  TextView textView=dialog3.findViewById(R.id.text);

        /*registerotpedttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("after", String.valueOf(after));
                Log.d("count1", String.valueOf(count));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("start", String.valueOf(start));
                Log.d("s", String.valueOf(s));
                if (s.length() == 6) {
                    //findPlaceWaypoints2();
                    try {
                        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                        MainActivity.otpedttext.requestFocus();
                        inputManager.hideSoftInputFromWindow(MainActivity.otpedttext.getWindowToken(),
                                InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                findPlaceWaypoints();
            }
        });*/

        registermobilenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("after", String.valueOf(after));
                Log.d("count1", String.valueOf(count));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("start", String.valueOf(start));
                Log.d("s", String.valueOf(s));
                if (s.length() == 10) {

                    if(isNetworkAvailable())
                    {
                        findPlaceWaypoints(registermobilenumber,registerotpedttext,setloginpassword,registerbutton,loginbutton,forgetpassword, setloginotppassword,resendotptimer,loginrelativouyt,setpassrelativouyt,setpassbutton,setolduserpassbutton,registerbuttonoldusr);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please check your internet connection!",Toast.LENGTH_SHORT).show();
                    }

                    try {
                        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                        mobilenumber.requestFocus();
                        inputManager.hideSoftInputFromWindow(mobilenumber.getWindowToken(),
                                InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }



            @Override
            public void afterTextChanged(Editable s) {
//                findPlaceWaypoints();
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registermobilenumber.getText().toString().equalsIgnoreCase("")){
                    registermobilenumber.requestFocus();
                    registermobilenumber.setError("Enter Valid Mobile No!");
                    return;

                }else if (setloginpassword.getText().toString().equalsIgnoreCase("")) {
                    setloginpassword.requestFocus();
                    setloginpassword.setError("Enter Valid Password!");
                    return;

                }else {
                    postotpnumberregstr(registermobilenumber,setloginpassword);

                }
            }
        });

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registermobilenumber.getText().toString().equalsIgnoreCase("")){
                    registermobilenumber.requestFocus();
                    registermobilenumber.setError("Enter Valid Mobile No!");
                    return;

                }else if (registerotpedttext.getText().toString().equalsIgnoreCase("")) {
                    registerotpedttext.requestFocus();
                    registerotpedttext.setError("Enter Valid OTP!");
                    return;

                }else {
                   /* textregister.setVisibility(View.GONE);
                    loginrelativouyt.setVisibility(View.GONE);
                    textregsetpass.setVisibility(View.VISIBLE);
                    setpassrelativouyt.setVisibility(View.VISIBLE);*/
                    chechkvalidotp(textregister,loginrelativouyt,textregsetpass,setpassrelativouyt,registerotpedttext,textView);
                    String otp=registerotpedttext.getText().toString();
                    SaveSharedPreference.setSession_Id(otp);
                    Log.d("registerOTp",SaveSharedPreference
                            .getSession_Id());
                }
            }
        });

        registerbuttonoldusr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registermobilenumber.getText().toString().equalsIgnoreCase("")){
                    registermobilenumber.requestFocus();
                    registermobilenumber.setError("Enter Valid Mobile No!");
                    return;

                }
                else if (setloginpassword.getText().toString().equalsIgnoreCase("")) {
                    setloginpassword.requestFocus();
                    setloginpassword.setError("Enter Valid Password");
                    return;
                }

                else if (!registermobilenumber.getText().toString().equals(setloginpassword.getText().toString())) {
                    setloginpassword.requestFocus();
                    setloginpassword.setError("Enter Valid Password");

                  //  Toast.makeText(getActivity(),"Enter valid password!",Toast.LENGTH_LONG).show();
                    return;

                }

                else if (registermobilenumber.getText().toString().equals(setloginpassword.getText().toString())) {

                    textregister.setVisibility(View.GONE);
                    loginrelativouyt.setVisibility(View.GONE);
                    textregsetpass.setVisibility(View.VISIBLE);
                    if(forgotPasswordCheck==2)
                    {
                       // textView.setText("");
                        textView.setVisibility(View.GONE);
                        textregsetpass.setText("Reset Login Password");
                    }
                  // textView.setText("");
                    textView.setVisibility(View.GONE);
                    textregsetpass.setText("Reset Login Password");
                    setpassrelativouyt.setVisibility(View.VISIBLE);
                }
            }
        });

        setpassbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setpasswordedttext.getText().toString().equalsIgnoreCase("")|| setpasswordedttext.getText().toString().length() <4) {
                    setpasswordedttext.requestFocus();
                    setpasswordedttext.setError("Enter Min.4 Digit Password!");
                    return;

                } else if (confirmsetpasswordedttext.getText().toString().equalsIgnoreCase("")|| confirmsetpasswordedttext.getText().toString().length() <4) {
                    confirmsetpasswordedttext.requestFocus();
                    confirmsetpasswordedttext.setError("Enter Min.4 Digit Password!");
                    return;

                } else if (setpasswordedttext.getText().toString().equals(confirmsetpasswordedttext.getText().toString())) {
                    //Toast.makeText(getActivity(),"Password Match", Toast.LENGTH_SHORT).show();
                    callingsetpasswordapi(setpasswordedttext, confirmsetpasswordedttext, registermobilenumber, setloginpassword);
                    postotpnumberregstrsetpass(registermobilenumber, confirmsetpasswordedttext);
                    return;

                } else {
                    //Toast.makeText(getActivity(), "Password Not Match", Toast.LENGTH_SHORT).show();
                    confirmsetpasswordedttext.setError("Password Not Match");
                }
            }
        });

        setolduserpassbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setpasswordedttext.getText().toString().equalsIgnoreCase("")||setpasswordedttext.getText().toString().length()<4) {
                    setpasswordedttext.requestFocus();
                    setpasswordedttext.setError("Enter Min.4 Digit Password!");
                    return;

                } else if (confirmsetpasswordedttext.getText().toString().equalsIgnoreCase("")||confirmsetpasswordedttext.getText().toString().length()<4) {
                    confirmsetpasswordedttext.requestFocus();
                    confirmsetpasswordedttext.setError("Enter Min.4 Digit Password!");
                    return;

                } else if (setpasswordedttext.getText().toString().equals(confirmsetpasswordedttext.getText().toString())) {
                    //Toast.makeText(getActivity(),"Password Match", Toast.LENGTH_SHORT).show();
                    callingsetpassolduserapi(setpasswordedttext, confirmsetpasswordedttext, registermobilenumber, setloginpassword);
                    postotpnumberregstrsetpass(registermobilenumber, confirmsetpasswordedttext);
                    return;

                } else {
                   // Toast.makeText(getActivity(), "Password Not Match", Toast.LENGTH_SHORT).show();
                    confirmsetpasswordedttext.setError("Password Not Match");
                }
            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerbutton.setVisibility(View.VISIBLE);
                registerotpedttext.setVisibility(View.VISIBLE);
                resendotp.setVisibility(View.VISIBLE);
                forgetpassword.setVisibility(View.GONE);
                loginbutton.setVisibility(View.GONE);
                setloginpassword.setVisibility(View.GONE);
                callingforgetpasswordapi();
                forgotPasswordCheck=2;

            }
        });

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callingresendotpapi(registermobilenumber,resendotptimer,resendotp);
            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registermobilenumber.setFocusable(true);
                registermobilenumber.setFocusableInTouchMode(true);
                registermobilenumber.requestFocus();
                registermobilenumber.setBackgroundResource(R.drawable.inputs);
            }
        });

        dialog3.setCancelable(false);
        dialog3.show();

                dialog3.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey (DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK &&
                                event.getAction() == KeyEvent.ACTION_UP &&
                                !event.isCanceled()) {

                            if (doubleBackToExitPressed == 2) {
                                getActivity().finishAffinity();
                                System.exit(0);
                            }
                            else {
                                doubleBackToExitPressed++;
                                Toast.makeText(getActivity(), "Please Click Back again To Exit", Toast.LENGTH_SHORT).show();
                            }

                           /* new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().finish();
                                }
                            }, 2000);*/

                            return true;
                        }
                        return false;
                    }
                });
    }

    private void callingsetpassolduserapi(EditText setpasswordedttext, EditText confirmsetpasswordedttext, EditText registermobilenumber, EditText setloginpassword) {
        msetpasswordedttext = setpasswordedttext.getText().toString();
        mconfirmsetpasswordedttext = confirmsetpasswordedttext.getText().toString();

        Log.d("mobiileuserid",SaveSharedPreference.getUserId());

        //final String setpassurl = ut.BASE_URL +"Authentication/SetPassword/"+ ut.Securetkey +"/"+SaveSharedPreference.getUserId()+"/"+SaveSharedPreference.getSession_Id()+"/"+mconfirmsetpasswordedttext;
       final String uldusersetpassurl=ut.BASE_URL +"Authentication/SetPassword/"+ ut.Securetkey +"/"+SaveSharedPreference.getUserId()+"/0/"+mconfirmsetpasswordedttext;
        Log.d("uldusersetpassurl", uldusersetpassurl);
        try {
            JSONObject jsonObject = new JSONObject();
            //  jsonObject.put("",Emobilenumber);
            new GetData(getActivity(), jsonObject, uldusersetpassurl, new OnTaskCompleted<String>() {
                @Override
                public void onTaskCompleted(String response) {
                    Log.d("message", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        if (message.contains("Enter Valid OTP!")) {

                            setpasswordedttext.setError(message);
                            confirmsetpasswordedttext.setError(message);
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            Log.d("message", response);

                            //return;

                        } else {
                            // postotpnumberregstr(registermobilenumber,setloginpassword);
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            dialog3.dismiss();
                            check=2;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, true).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callingresendotpapi(EditText registermobilenumber,TextView resendotptimer,TextView resendotp) {
        String EMobilenumber = registermobilenumber.getText().toString();
        SaveSharedPreference.setUserId(EMobilenumber);


        if (registermobilenumber.length() == 10) {
            final String resendotpurl = ut.BASE_URL +"Authentication/ValidateMobile/"+ut.Securetkey+"/" + EMobilenumber;
            Log.d("resendotpurl", resendotpurl);
            try {
                JSONObject jsonObject = new JSONObject();
                new GetData(getActivity(), jsonObject, resendotpurl, new OnTaskCompleted<String>() {
                    @Override
                    public void onTaskCompleted(String response) {
                        Log.d("mobilenumber", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            message = jsonObject.getString("message");
                            SelectedId = jsonObject.getString("goalid");
                            Log.d("message", message + UserSession.getIsLogin(mainActivity));
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            //resendotptimer.setVisibility(View.VISIBLE);
                            //resendotp.setVisibility(View.GONE);


                                    starttimer(resendotptimer,resendotp);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, true).execute();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("updateException", e.toString());
            }
        } else {
            Toast.makeText(getActivity(), "Enter Valid Mobile No.!", Toast.LENGTH_SHORT).show();
        }
    }
    int second=0;

    private void starttimer(TextView resendotptimer,TextView resendotp) {

        resendotp.setVisibility(View.GONE);
        resendotptimer.setVisibility(View.VISIBLE);
        countDownTimer=new CountDownTimer(mTimerLeftInMilllis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
              //  updateCountDownText(resendotptimer);
                resendotptimer.setText((" Resend OTP In "+ millisUntilFinished / 1000+" Seconds"));

             /*   second++;
                if (second<10){
                    resendotptimer.setText("00:0" + millisUntilFinished);
                }else {
                    resendotptimer.setText("00:" + millisUntilFinished);
                }*/
             }

            @Override
            public void onFinish() {
                mTimerRunning=false;
                resendotp.setVisibility(View.VISIBLE);
                resendotptimer.setVisibility(View.GONE);
            }
        }.start();
        mTimerRunning=true;
    }

    private void updateCountDownText(TextView resendotptimer) {
        int minutes=(int)(mTimerLeftInMilllis/1000)/60;
        int seconds=(int)(mTimerLeftInMilllis/1000)%60;

        String timeLeftFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        resendotptimer.setText(timeLeftFormatted);
    }

    private void chechkvalidotp(TextView textregister,RelativeLayout loginrelativouyt,TextView textregsetpass,RelativeLayout setpassrelativouyt,EditText registerotpedttext,TextView textView) {
       final String chechkotpurl=ut.BASE_URL +"Authentication/ValidateOTP/"+ut.Securetkey+"/"+SaveSharedPreference.getUserId()+"/1/"+SaveSharedPreference.getSession_Id();
        final String chechkotpurl1=ut.BASE_URL +"Authentication/ValidateOTP/"+ut.Securetkey+"/"+SaveSharedPreference.getUserId()+"/1/"+registerotpedttext.getText().toString();

        Log.d("chechkotpurl", chechkotpurl);
        try {
            JSONObject jsonObject=new JSONObject();
            new GetData(getActivity(), jsonObject, chechkotpurl1, new OnTaskCompleted<String>() {
                @Override
                public void onTaskCompleted(String response) {
                    Log.d("message", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        String responsecode=jsonObject.getString("responsecode");

                        if(responsecode.equalsIgnoreCase("Success"))
                        {
                            Log.d("message", response);
                            textregister.setVisibility(View.GONE);
                            loginrelativouyt.setVisibility(View.GONE);
                            textregsetpass.setVisibility(View.VISIBLE);
                            setpassrelativouyt.setVisibility(View.VISIBLE);

                            if(forgotPasswordCheck==2)
                            {
                                //textregsetpass.setVisibility(View.GONE);
                                textView.setVisibility(View.GONE);
                                textregsetpass.setText("Reset Login Password");
                            }
                            //registerotpedttext.setError(message);
                        }
                        else {
                            //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            registerotpedttext.setError(message);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            },true).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postotpnumberregstrsetpass(EditText registermobilenumber, EditText confirmsetpasswordedttext) {
        sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
        Username = sharedPreferences.getString("name", null);
        Usertoken = sharedPreferences.getString("token", null);
        goalid = sharedPreferences.getString("goalid", null);
        Log.e("token", Usertoken + Username);

        String Eregistermobilenumber = registermobilenumber.getText().toString();
        mregistermobilenumber = registermobilenumber.getText().toString();
        SaveSharedPreference.setnId(mregistermobilenumber);

        SaveSharedPreference.setUserId(mregistermobilenumber);

        Mregisterpassedttext = confirmsetpasswordedttext.getText().toString();

        /*Log.d("mobilenumber", mregistermobilenumber + Mregisterotpedttext);
           if (SelectedId.equals("0") && MainActivity.otpedttext.getText().toString().equalsIgnoreCase("")) {
               Toast.makeText(getActivity(), "Please Select A Goals", Toast.LENGTH_SHORT).show();
              return;
           }*/
        final String postotpurl = ut.BASE_URL + "Authentication/ValidatePass/" + ut.Securetkey + "/" + mregistermobilenumber + "/1/0/"+Mregisterpassedttext;
        Log.d("postotpurl", postotpurl);
        try {
            JSONObject jsonObject = new JSONObject();
            //  jsonObject.put("",Emobilenumber);
            new GetData(getActivity(), jsonObject, postotpurl, new OnTaskCompleted<String>() {
                @Override
                public void onTaskCompleted(String response) {
                    Log.d("message", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        if (message.contains("Enter Valid Password!"))
                        {
                            confirmsetpasswordedttext.setError(message);
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            Log.d("message", response);

                            //return;

                        } else {
                            mainActivity.navigationView.getMenu().findItem(R.id.nav_mylibrary).setVisible(true);
                            mainActivity.navigationView.getMenu().findItem(R.id.nav_aboutus).setVisible(true);
                            mainActivity.navigationView.getMenu().findItem(R.id.nav_yourprofile).setVisible(true);
                            mainActivity.navigationView.getMenu().findItem(R.id.nav_contact).setVisible(true);
                            mainActivity.navigationView.getMenu().findItem(R.id.nav_logoutfregment).setVisible(true);

                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            // getlistview(registeritemetdtext);
                            Log.d("message", response);
                            UserSession.setLoginRemember(getActivity());

                            sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("message", message);

                            UserSession userSession = new UserSession(getActivity());
                            userSession.setMobile(mregistermobilenumber);
                            MainActivity.txtProfileName.setText(userSession.getMobile());
//                          MainActivity.linearout.setVisibility(View.GONE);
                            registermobilenumber.getText().clear();
                            golsidtext.setVisibility(View.VISIBLE);
                            dialog3.dismiss();



                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }, true).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callingforgetpasswordapi() {
        final String forgetapi = ut.BASE_URL +"Authentication/ForgetPassword/"+ut.Securetkey+"/" +SaveSharedPreference.getUserId();
        Log.d("mobilenumber", forgetapi);
        try {
            JSONObject jsonObject = new JSONObject();
            new GetData(getActivity(), jsonObject, forgetapi, new OnTaskCompleted<String>() {
                @Override
                public void onTaskCompleted(String response) {
                    Log.d("mobilenumber", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        message = jsonObject.getString("message");
                        Log.d("message", message + UserSession.getIsLogin(mainActivity));
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, true).execute();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("updateException", e.toString());
        }
    }

    private void callingsetpasswordapi(EditText setpasswordedttext,EditText confirmsetpasswordedttext,EditText registermobilenumber,EditText setloginpassword) {
        msetpasswordedttext = setpasswordedttext.getText().toString();
        mconfirmsetpasswordedttext = confirmsetpasswordedttext.getText().toString();

        Log.d("mobiileuserid",SaveSharedPreference.getUserId());

        final String setpassurl = ut.BASE_URL +"Authentication/SetPassword/"+ ut.Securetkey +"/"+SaveSharedPreference.getUserId()+"/"+SaveSharedPreference.getSession_Id()+"/"+mconfirmsetpasswordedttext;
        Log.d("setpassurl", setpassurl);
        try {
            JSONObject jsonObject = new JSONObject();
            //  jsonObject.put("",Emobilenumber);
            new GetData(getActivity(), jsonObject, setpassurl, new OnTaskCompleted<String>() {
                @Override
                public void onTaskCompleted(String response) {
                    Log.d("message", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        if (message.contains("Enter Valid OTP!")) {

                            setpasswordedttext.setError(message);
                            confirmsetpasswordedttext.setError(message);
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            Log.d("message", response);

                            //return;

                        } else {
                           // postotpnumberregstr(registermobilenumber,setloginpassword);
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            dialog3.dismiss();
                            check=2;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, true).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postotpnumberregstr(EditText registermobilenumber, EditText setloginpassword) {
        sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
        Username = sharedPreferences.getString("name", null);
        Usertoken = sharedPreferences.getString("token", null);
        goalid = sharedPreferences.getString("goalid", null);
        Log.e("token", Usertoken + Username);

        String Eregistermobilenumber = registermobilenumber.getText().toString();
        mregistermobilenumber = registermobilenumber.getText().toString();
        SaveSharedPreference.setnId(mregistermobilenumber);

        SaveSharedPreference.setUserId(mregistermobilenumber);

        Mregisterpassedttext = setloginpassword.getText().toString();


        /*Log.d("mobilenumber", mregistermobilenumber + Mregisterotpedttext);
           if (SelectedId.equals("0") && MainActivity.otpedttext.getText().toString().equalsIgnoreCase("")) {
               Toast.makeText(getActivity(), "Please Select A Goals", Toast.LENGTH_SHORT).show();
              return;
           }*/
        final String postotpurl = ut.BASE_URL + "Authentication/ValidatePass/" + ut.Securetkey + "/" + mregistermobilenumber + "/1/0/"+Mregisterpassedttext;
        Log.d("postotpurl", postotpurl);
        try {
            JSONObject jsonObject = new JSONObject();
            //  jsonObject.put("",Emobilenumber);
            new GetData(getActivity(), jsonObject, postotpurl, new OnTaskCompleted<String>() {
                @Override
                public void onTaskCompleted(String response) {
                    Log.d("message", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        if (message.contains("Enter Valid Password!"))
                        {
                            setloginpassword.setError(message);
                           // Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            Log.d("message", response);

                            //return;

                        } else {
                            mainActivity.navigationView.getMenu().findItem(R.id.nav_mylibrary).setVisible(true);
                            mainActivity.navigationView.getMenu().findItem(R.id.nav_aboutus).setVisible(true);
                            mainActivity.navigationView.getMenu().findItem(R.id.nav_yourprofile).setVisible(true);
                            mainActivity.navigationView.getMenu().findItem(R.id.nav_contact).setVisible(true);
                            mainActivity.navigationView.getMenu().findItem(R.id.nav_logoutfregment).setVisible(true);


                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            // getlistview(registeritemetdtext);
                            Log.d("message", response);
                            UserSession.setLoginRemember(getActivity());

                            sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("message", message);

                            UserSession userSession = new UserSession(getActivity());
                            userSession.setMobile(mregistermobilenumber);
                            MainActivity.txtProfileName.setText(userSession.getMobile());
//                               MainActivity.linearout.setVisibility(View.GONE);
                            registermobilenumber.getText().clear();
                            setloginpassword.getText().clear();
                            golsidtext.setVisibility(View.VISIBLE);
                            dialog3.dismiss();
                            check=2;


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                }, true).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    private void openGoToPlayStoreAlert() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("UPDATE PATANJALI IAS CLASSES");
        builder.setMessage("Recommend you to update app to the latest version");

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        }).setNegativeButton("Not Now", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void cheloging() {
        if (UserSession.getIsLogin(mainActivity)) {
            sharedPreferences = mainActivity.getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
            data = sharedPreferences.getString("data", null);

            notifyimage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // change color
                        mainActivity.relativeoutnotify.setBackgroundColor(Color.argb(155, 185, 185, 185));
                        NotificationFragment notificationFragment = new NotificationFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction ft=fragmentManager.beginTransaction();
                        ft.replace(R.id.nav_host_fragment, notificationFragment);
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP) {
                        // set to normal color
                        mainActivity.relativeoutnotify.setBackgroundColor(Color.argb(0, 185, 185, 185));

                    }

                    return true;
                }
            });

            /*golsidtext.setText(data);
            MainActivity.golsidtext.setText(data);*/
//            MainActivity.linearout.setVisibility(View.GONE);
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
            //openlogindialogbox();
           // MainActivity.linearout.setVisibility(View.VISIBLE);
            golsidtext.setVisibility(View.GONE);
        }
    }

    private void openotpfield() {
        sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
        message = sharedPreferences.getString("message", null);
        Usertoken = sharedPreferences.getString("token", null);
        Log.e("token", Usertoken + message);

//        otptext.setText(message);

        if (SelectedId.equals("0"))
        {
           /* selectitemetdtext.setVisibility(View.VISIBLE);
            otpedttext.setVisibility(View.GONE);*/
            Toast.makeText(getActivity(), "Please Select A Goals", Toast.LENGTH_SHORT).show();

            return;
        }else {
           // selectitemetdtext.setVisibility(View.GONE);
            MainActivity.otpedttext.setVisibility(View.VISIBLE);
           // nextimage.setVisibility(View.GONE);
            nextimagebtn.setVisibility(View.VISIBLE);
        }
    }
    private void findPlaceWaypoints(EditText registermobilenumber,EditText registerotpedttext,EditText setloginpassword,Button registerbutton,
                                    Button loginbutton,TextView forgetpassword,EditText setloginotppassword,TextView resendotptimer,
                                    RelativeLayout loginrelativouyt,RelativeLayout setpassrelativouyt,Button setpassbutton,Button setolduserpassbutton,Button registerbuttonoldusr) {
        String EMobilenumber = registermobilenumber.getText().toString();
        SaveSharedPreference.setUserId(EMobilenumber);
        Log.d("EMobilenumber",EMobilenumber);

       /* Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("etmobileno",SaveSharedPreference.getUserId());

            }
        },1000);*/

        registermobilenumber.setFocusable(false);
        registermobilenumber.onEndBatchEdit();
        if (registermobilenumber.length() == 10) {
            registermobilenumber.setBackgroundResource(R.drawable.inputssetback);
            final String posturl = ut.BASE_URL +"Authentication/Validateuser/"+ut.Securetkey+"/" + EMobilenumber;
            try {
                JSONObject jsonObject = new JSONObject();
                new GetData(getActivity(), jsonObject, posturl, new OnTaskCompleted<String>() {
                    @Override
                    public void onTaskCompleted(String response) {
                        Log.d("mobilenumberuser", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            message = jsonObject.getString("message");
                            SelectedId = jsonObject.getString("goalid");
                            String olduser=jsonObject.getString("olduser");
                            Log.d("message", message + UserSession.getIsLogin(mainActivity));
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                           /* selectitemetdtext202.setVisibility(View.VISIBLE);
                            selectitemetdtext.setVisibility(View.GONE);*/
                            sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("goalid", SelectedId);
                            editor.putString("message", message);
                            editor.apply();
                            Log.e("storeId", toString());

                            if (SelectedId.equals("0")) {
                                registerbutton.setVisibility(View.VISIBLE);
                                registerotpedttext.setVisibility(View.VISIBLE);
                                setloginotppassword.setVisibility(View.GONE);
                                setloginpassword.setVisibility(View.GONE);
                                loginbutton.setVisibility(View.GONE);
                                forgetpassword.setVisibility(View.GONE);
                                resendotptimer.setVisibility(View.VISIBLE);
                                starttimernewno();

                            } else {
                                registerbutton.setVisibility(View.GONE);
                                registerotpedttext.setVisibility(View.GONE);
                                loginbutton.setVisibility(View.VISIBLE);
                                setloginpassword.setVisibility(View.VISIBLE);
                                setloginotppassword.setVisibility(View.GONE);
                                forgetpassword.setVisibility(View.VISIBLE);
                                resendotptimer.setVisibility(View.GONE);
                            }
                            if (olduser.equals("true")){
                                /*loginrelativouyt.setVisibility(View.GONE);
                                setpassrelativouyt.setVisibility(View.VISIBLE);*/
                                setpassbutton.setVisibility(View.GONE);
                                setolduserpassbutton.setVisibility(View.VISIBLE);
                                loginbutton.setVisibility(View.GONE);
                                resendotp.setVisibility(View.GONE);
                                registerbutton.setVisibility(View.GONE);
                                registerbuttonoldusr.setVisibility(View.VISIBLE);

                            }else {
                                resendotp.setVisibility(View.GONE);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, true).execute();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("updateException", e.toString());
            }
        } else {
            Toast.makeText(getActivity(), "Enter Valid Mobile No.!", Toast.LENGTH_SHORT).show();
        }
    }

    int seconds=0;
    private void starttimernewno() {
        resendotptimer.setVisibility(View.VISIBLE);
        countDownTimer=new CountDownTimer(mTimerLeftInMilllis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //  updateCountDownText(resendotptimer);

                resendotptimer.setText((" Resend OTP In "+ millisUntilFinished / 1000+" Seconds"));

              /*  seconds++;
                if (seconds<10){
                    resendotptimer.setText("00:0" + millisUntilFinished/1000);
                }else {
                    resendotptimer.setText("00:" + millisUntilFinished);
                }*/
            }

            @Override
            public void onFinish() {
                mTimerRunning=false;
               resendotp.setVisibility(View.VISIBLE);
                resendotptimer.setVisibility(View.GONE);
            }
        }.start();
        mTimerRunning=true;
    }

    private void hidefooter() {

        MainActivity.linearlayout.setVisibility(View.GONE);
    }

    Dialog dialog;
    private void openspinerdialog(TextView registeritemetdtext) {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.spinnerlistitem_dialog);
        recyclerViewlist =  dialog.findViewById(R.id.recyclerViewlist);

        getlistview(registeritemetdtext);

        dialog.show();
    }

    List<String> dataList=new ArrayList<>();
    String SelectedId="0";
    private void getlistview(TextView registeritemetdtext) {

        sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
        Username = sharedPreferences.getString("name", null);
        Usertoken = sharedPreferences.getString("token", null);
        Log.e("token", Usertoken + Username);

        String approveurl =ut.BASE_URL +"Datavalue/goal/"+ut.Securetkey;
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
                        if (SelectedId.equals(value)){
                            //golsidtext.setText(data);
                            sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("data", data);
                            editor.apply();
                        }
                        Spinerlist_Model spinerlist_model = new Spinerlist_Model(value, data);
                        spinerlist_models.add(spinerlist_model);
                        dataList.add(data);
                    }

                    ArrayAdapter<String> spineerListAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_single_choice ,dataList);
                   if (recyclerViewlist!=null){
                       recyclerViewlist.setAdapter(spineerListAdapter);
                       recyclerViewlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               SelectedId=spinerlist_models.get(position).getValue();
                              // MainActivity.selectitemetdtext.setText(spinerlist_models.get(position).getData());
                               registeritemetdtext.setText(spinerlist_models.get(position).getData());
                               UserSession userSession = new UserSession(getActivity());
                               userSession.setGolsID(spinerlist_models.get(position).getData());
                               //MainActivity.golsidtext.setText(spinerlist_models.get(position).getData());
                               /*selectitemetdtext202.setVisibility(View.GONE);
                               selectitemetdtext.setVisibility(View.VISIBLE);*/
                               sharedPreferences = getActivity().getSharedPreferences(SHARD_PREF, Context.MODE_PRIVATE);
                               editor = sharedPreferences.edit();
                               editor.putString("data", spinerlist_models.get(position).getData());
                               editor.apply();

                            /*selectitemetdtext.setVisibility(View.GONE);
                            otpedttext.setVisibility(View.VISIBLE);*/
                               dialog.dismiss();
                               check=2;
                               // Toast.makeText(getActivity(), "Value is "+SelectedId+"\nData is "+spinerlist_models.get(position).getData(), Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },true).execute();
    }
/*
    @Override
    public void onStart() {

        liveClassAPi();

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

    private void liveClassAPi() {

        final String posturl = ut.BASE_URL +"Liveclasses"+"/"+ut.Securetkey+"/"+SaveSharedPreference.getUserId();
       // Log.e("responsedisplaydata",posturl);

        new GetLiveData(context, new JSONObject(), posturl, new OnTaskCompleted<String>() {
            @Override
            public void onTaskCompleted(String response) {
                Log.e("responsedisplaydata",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    Log.e("jsonArray",""+jsonArray);
                    if(jsonArray.length()>0 &&jsonArray!=null)
                    {
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String status = jsonObject.getString("meeting");
                            Log.e("displaydata",status);
                            if(status.equalsIgnoreCase("0"))
                            {
                                live.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                live.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute();
    }*/

    /*@Override
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
    }*/


    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}