package com.patanjali.patanjaliiasclasses.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.fregment.contactus.ContactUSFragment;
import com.patanjali.patanjaliiasclasses.fregment.freedownloads.FreeDowloadsFragment;
import com.patanjali.patanjaliiasclasses.fregment.home.HomeFragment;
import com.patanjali.patanjaliiasclasses.fregment.morningclass.MorningClassFragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.patanjali.patanjaliiasclasses.fregment.mylibrary.MyLibraryFragment;
import com.patanjali.patanjaliiasclasses.fregment.videoplay.VedioPlayerFragment;
import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.service.GetData;
import com.patanjali.patanjaliiasclasses.service.GetLiveData;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static Toolbar toolbar;
    TextView username;
    ImageView logoimage, imageView,live;
    ProgressDialog progressDialog;
    UserSession userSession;
    MainActivity mainActivity;
    String Device_id;
    Config ut;
    String meetingId;
    //shared prefernce
    //SharedPreference Use
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARD_PREF = "GLOBLE_Application";

    public static EditText mobilenumber,otpedttext;
    public static LinearLayout linearlayout,linearout;
//  public static TextView usernumber, golsidtext,selectitemetdtext,selectitemetdtext202;
//  public static ImageView opnarroimage,nextimage,nextimagebtn;
    public static ImageView nextimagebtn;
    public static ImageButton notifyimage,home_icon;
    public static TextView userproname,golsidtext;
    public static TextView txtProfileName;
    public static RelativeLayout relativeoutnotify;
    public static LinearLayout linearoutfooter1,linearoutfooter6;

    public NavigationView navigationView;

    MediaProjectionManager mProjectionManager;
    FragmentManager manager;
    private CountDownTimer timer;
    int doubleBackToExitPressed = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
       mProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        if(android.os.Build.VERSION.SDK_INT >= 20) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getApplicationContext().getSharedPreferences(SHARD_PREF,MODE_PRIVATE);
        new SaveSharedPreference(getApplicationContext());

        progressDialog = new ProgressDialog(this);
        userSession = new UserSession(this);
        mainActivity = (this);

        editor = sharedPreferences.edit();
        editor.putBoolean("firsttime",true);
        editor.apply();

        //  mobilenumber=findViewById(R.id.mobilenumber);
        //  otpedttext=findViewById(R.id.otpedttext);
        /*selectitemetdtext=findViewById(R.id.selectitemetdtext);
        selectitemetdtext202=findViewById(R.id.selectitemetdtext202);
        nextimage=findViewById(R.id.nextimage);*/
//      nextimagebtn=findViewById(R.id.nextimagebtn);
//      linearout=findViewById(R.id.linearout);
        //username=findViewById(R.id.username);
        golsidtext = findViewById(R.id.golsidtext);
        // opnarroimage=findViewById(R.id.opnarroimage);
        // usernumber=findViewById(R.id.usernumber);
        logoimage = findViewById(R.id.logoimage);
        imageView = findViewById(R.id.imageView);
        live=findViewById(R.id.live);
        notifyimage=findViewById(R.id.notifyimage);
        relativeoutnotify=findViewById(R.id.relativeoutnotify);
        linearlayout = findViewById(R.id.linearlayout);
        linearoutfooter1 = findViewById(R.id.linearoutfooter1);
        linearoutfooter6=findViewById(R.id.linearoutfooter6);

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingApi();
            }
        });

        Device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        getnamelogo(Device_id);
       // checkdevice();

        /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_liveclass, R.id.nav_morningclasdetail, R.id.nav_onlineclas, R.id.nav_logoutfregment)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.nav_home){
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    manager=fragmentManager;
                    FragmentTransaction ft=fragmentManager.beginTransaction();
                    ft.replace(R.id.nav_host_fragment, homeFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    DrawerLayout mDrawerLayout;
                    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                    mDrawerLayout.closeDrawers();

                }else if (id==R.id.nav_liveclass) {
                    MorningClassFragment morningClassFragment = new MorningClassFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    manager=fragmentManager;
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.nav_host_fragment, morningClassFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    DrawerLayout mDrawerLayout;
                    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                    mDrawerLayout.closeDrawers();

                }else if (id==R.id.nav_mylibrary) {
                    MyLibraryFragment myLibraryFragment = new MyLibraryFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    manager=fragmentManager;
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.nav_host_fragment, myLibraryFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    DrawerLayout mDrawerLayout;
                    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                    mDrawerLayout.closeDrawers();

                }else if (id==R.id.nav_contact){
                    ContactUSFragment contactUSFragment = new ContactUSFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    manager=fragmentManager;
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.nav_host_fragment, contactUSFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    DrawerLayout mDrawerLayout;
                    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                    mDrawerLayout.closeDrawers();

                }else if (id==R.id.nav_freedonloas){
                    FreeDowloadsFragment myLibraryFragment = new FreeDowloadsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    manager=fragmentManager;
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.nav_host_fragment, myLibraryFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    DrawerLayout mDrawerLayout;
                    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                    mDrawerLayout.closeDrawers();

                    if (id==R.id.nav_mylibrary){
                        mainActivity.navigationView.getMenu().findItem(R.id.nav_mylibrary).setVisible(true);
                       /* HomeFragment homeFragment = new HomeFragment();
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        ft.replace(R.id.nav_host_fragment, fragmentManager1);
                        ft.addToBackStack(null);
                        ft.commit();  */
                    }else {

                    }

                }else if (id==R.id.nav_logoutfregment){
                    final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Exit");
                    builder.setMessage("Are you sure you want to leave ?");

                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mainActivity.navigationView.getMenu().findItem(R.id.nav_freedonloas).setVisible(false);
                                    mainActivity.navigationView.getMenu().findItem(R.id.nav_mylibrary).setVisible(false);
                                    mainActivity.navigationView.getMenu().findItem(R.id.nav_aboutus).setVisible(false);
                                    mainActivity.navigationView.getMenu().findItem(R.id.nav_yourprofile).setVisible(false);
                                    mainActivity.navigationView.getMenu().findItem(R.id.nav_contact).setVisible(false);
                                    mainActivity.navigationView.getMenu().findItem(R.id.nav_logoutfregment).setVisible(false);
                                    // Clear the session data
                                    // This will clear all session data and
                                    // redirect user to LoginActivity
                                    userSession.logoutUser();
                                    Intent intent=new Intent(MainActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    MainActivity.this.finish();
                                    MainActivity.this.finishAffinity();
                                   // clearApplicationData();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    android.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                return false;
            }
        });

        txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userphonenumber);
        userproname = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userproname);

    }

    private void checkdevice() {
        try {
            new GetData(MainActivity.this, new JSONObject(), ut.BASE_URL +"Checklogin/" + ut.Securetkey + "/" + SaveSharedPreference.getUserId(),
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
                                    Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                                } else if (responseCode.equals("0")) {
                                    Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                    userSession.logoutUser();
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    finishAffinity();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                             /* try {
                                JSONArray jsonArray=new JSONArray(response);
                                JSONObject object=jsonArray.getJSONObject(0);
                                  String responseCode=object.getString("responsecode");
                                  String message=object.getString("message");
                                if (message.equals("true")){
                                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();

                                }else if (message.equals("false")){
                                   // userSession.logoutUser();
                                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/


                        }
                    }, true).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getnamelogo(String Device_id) {

        final String gettoknurl = ut.BASE_URL +"subscription/"+Device_id+"/"+ut.Securetkey;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, gettoknurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("gettoknurl", gettoknurl);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.d("Response",response);
                    String status=jsonObject.getString("status");
                    String name=jsonObject.getString("name");
                    String logoimg=jsonObject.getString("logoimg");
                    String bgimg=jsonObject.getString("bgimg");
                    String token=jsonObject.getString("token");
                    Log.d("Response", status + name + logoimg + bgimg + token);
                    Glide.with(MainActivity.this).load(logoimg).placeholder(R.drawable.indicator_corner_bg).into(logoimage);
                    //username.setText(name);
                   // userproname.setText(name);
                    //Glide.with(MainActivity.this).load(logoimg).placeholder(R.drawable.indicator_corner_bg).into(imageView);

                   // Toast.makeText(MainActivity.this, "Token Details"+response, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Log.d("LoginException", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    /*Toast.makeText(MainActivity.this,
                            "Please Check Internet",
                            Toast.LENGTH_LONG).show();*/
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() >=0) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            manager=fragmentManager;
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.replace(R.id.nav_host_fragment, homeFragment);
            ft.addToBackStack(null);
            ft.commit();

        } else {
            getFragmentManager().popBackStack();
        }
        editor = sharedPreferences.edit();
        editor.putBoolean("homechecker",false);
        editor.apply();

       /* if(sharedPreferences.getBoolean("firsttime",false))
        {
            this.finishAffinity();
            System.exit(0);
        }*/
    }

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

        new GetLiveData(mainActivity, new JSONObject(), posturl, new OnTaskCompleted<String>() {
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
                            meetingId=jsonObject.getString("meeting");
                            Log.e("displaydata",status);
                            if(status.equalsIgnoreCase("0"))
                            {
                               live.setVisibility(View.INVISIBLE);
                               // live.setVisibility(View.VISIBLE);
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
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
            Log.i("Mainjdjdjkjkd", "cancel timer");
            timer = null;
        }
        Log.d("mainactivity","mainactivity");

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

    public void clearApplicationData()
    {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir1(new File(appDir, s));
                }
            }
        }
    }

    public static boolean deleteDir1(File dir1)
    {
        if (dir1 != null && dir1.isDirectory()) {
        String[] children = dir1.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = deleteDir1(new File(dir1, children[i]));
            Log.d("sucesss",""+success);
            if (!success) {
                return false;
            }
        }
    }
        return dir1.delete();
    }


    public void callingApi() {


        try {
            new GetData(mainActivity, new JSONObject(), ut.BASE_URL +"joinmeeting/"+ut.Securetkey+"/"+userSession.getMobile()+"/"+meetingId,
                    new OnTaskCompleted<String>() {
                        @Override
                        public void onTaskCompleted(String response) {

                            Log.d("jfdkjsnjkfnskjf","jsdhnkjsnkjs");
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                JSONObject object=jsonArray.getJSONObject(0);
                                int responseCode=object.getInt("responsecode");
                                if (responseCode==1){
                                    String url=object.getString("message");
                                    try {
                                        // VedioPlayerFragment vedioPlayerFragment = new VedioPlayerFragment();
                                        Intent bundle = new Intent(getApplicationContext(), VedioPlayerFragment.class);
                                        bundle.putExtra("url",url);
                                        startActivity(bundle);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },true).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

