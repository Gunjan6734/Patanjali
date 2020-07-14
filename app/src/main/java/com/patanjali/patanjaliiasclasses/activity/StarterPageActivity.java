package com.patanjali.patanjaliiasclasses.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.UserSession;

import org.json.JSONObject;
import org.w3c.dom.Document;

public class StarterPageActivity extends AppCompatActivity {

    private Handler mWaitHandler = new Handler();

    ImageView appimage;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARD_PREF = "GLOBLE_Application";
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    TelephonyManager telephonyManager;
    String Device_id;
    Config ut;

    String currentVersion, latestVersion;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter_page);

        getCurrentVersion();
        progressDialog = new ProgressDialog(this);
        appimage = findViewById(R.id.appimage);
        if (UserSession.isFirtstTimeInstall(this)) {
            mWaitHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //The following code will execute after the 5 seconds.
                    try {

                        UserSession.setFirstTime(StarterPageActivity.this);

                        startActivity(new Intent(StarterPageActivity.this, SplaceActivity.class));
                        finish();

                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
            }, 5000);  // Give a 5 seconds delay.

            Device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            gettokendetail(Device_id);

        } else {
            startActivity(new Intent(StarterPageActivity.this, MainActivity.class));
            finish();

        }

    }

    private void gettokendetail(String Device_id) {
        Log.d("Device_id", Device_id);
        final String gettoknurl = ut.BASE_URL + "subscription/" + Device_id + "/" + ut.Securetkey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, gettoknurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("gettoknurl", gettoknurl);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Response", response);
                    String status = jsonObject.getString("status");
                    String name = jsonObject.getString("name");
                    String logoimg = jsonObject.getString("logoimg");
                    String bgimg = jsonObject.getString("bgimg");
                    String token = jsonObject.getString("token");
                    Log.d("Response", status + name + logoimg + bgimg + token);
                    Glide.with(StarterPageActivity.this).load(bgimg).placeholder(R.drawable.indicator_corner_bg).into(appimage);

                    //  Toast.makeText(StarterPageActivity.this, "Token Details"+response, Toast.LENGTH_SHORT).show();

                    sharedPreferences = getSharedPreferences(SHARD_PREF, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("logoimg", logoimg);
                    editor.putString("bgimg", bgimg);
                    editor.putString("token", token);
                    editor.apply();
                    Log.e("storeId", toString());

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
                    Toast.makeText(StarterPageActivity.this,
                            "Please Check Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;

        new GetLatestVersion().execute();

    }

    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                //It retrieves the latest version by scraping the content of current version from play store at runtime
               // Document doc = Jsoup.connect("patanjaliiasclasses").get();
               // latestVersion = doc.getElementsByClass("htlgb").get(5).text();

            } catch (Exception e) {
                e.printStackTrace();

            }

            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    if (!isFinishing()) { //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
                        showUpdateDialog();
                    }
                }
            } else
                //background.start();
            super.onPostExecute(jsonObject);
        }
    }

    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("A New Update is Available");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("market://details?id=patanjaliiasclasses")));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //background.start();
            }
        });

        builder.setCancelable(false);
        dialog = builder.show();
    }
}