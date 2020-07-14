package com.patanjali.patanjaliiasclasses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.patanjali.patanjaliiasclasses.activity.MainActivity;
import com.patanjali.patanjaliiasclasses.activity.StarterPageActivity;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class UserSession {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    public static final String Key_Login_Check="KeyLogin";

    // Sharedpref file name
    public static final String SHARD_PREF = "GLOBLE_Application";

    public static final String KEY_FIRST_TIME="First_Time_Install";


    // All Shared Preferences Keys
    public static final String IS_LOGIN = "LoginCheck";

    //User Id...
    public static final String KEY_ID="message";

    // User name (make variable public to access from outside)
    public static final String KEY_GOALS = "goalid";
    public static final String KEY_PHONE = "Mobile_number";
    public static final String KEY_USERNAME = "UserName";


    // Constructor
    public UserSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(SHARD_PREF, PRIVATE_MODE);
        editor = pref.edit();
    }


    public boolean getBooleanValue(String key) {
        return this.pref.getBoolean(key, false);
    }


    public void setBooleanValue(String key, boolean value) {
        this.editor.putBoolean(key, value);
        this.editor.commit();
    }

    /**
     * Create login session
     * */

    //    public void createLoginSession(String userID,String name, String email){
    public void createLoginSession(String message,String goalid){

        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        //Strong id in pref..
        editor.putString(KEY_ID,message);

        // Storing name in pref
        editor.putString(KEY_GOALS, goalid);

        // commit changes
        editor.commit();
    }
    public void setGolsID(String golsid){
        editor.putString(KEY_GOALS,golsid);
        editor.apply();
    }
    public String getGolsid(){
        return pref.getString(KEY_GOALS,"");
    }

    public void setMobile(String mobile){
        editor.putString(KEY_PHONE,mobile);
        editor.apply();
    }

    public String getMobile(){
       return pref.getString(KEY_PHONE,"");
    }

    public void setUsername(String name){
        editor.putString(KEY_USERNAME,name);
        editor.apply();
    }

    public String getUsername(){
        return pref.getString(KEY_USERNAME,"");

    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        //user id..
        user.put(KEY_ID,pref.getString(KEY_ID,null));

        // user name
        user.put(KEY_GOALS, pref.getString(KEY_GOALS, null));


        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.putString(KEY_PHONE,"");
        editor.putString(KEY_GOALS,"");
        editor.putString(KEY_ID,"");
        editor.putString(KEY_USERNAME,"");
        editor.putString("data","");

        editor.apply();
        UserSession.setLBoolean(_context,false,UserSession.Key_Login_Check);

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);


    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public static void setLoginRemember(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Key_Login_Check, true);
        editor.apply();
    }

    public static void setLBoolean(Context context,Boolean value, String KEY) {
        SharedPreferences sp = context.getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, value);
        editor.apply();
    }


    public static boolean getIsLogin(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
        //  ..SharedPreferences.Editor editor = sp.edit();

        //editor.commit();
        return   sp.getBoolean(Key_Login_Check, false);

    }

    public static void setFirstTime(Context _context)
    {
        try {
            SharedPreferences sp = _context.getSharedPreferences(SHARD_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(KEY_FIRST_TIME, false);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean isFirtstTimeInstall(Context context)
    {
        SharedPreferences pref=context.getSharedPreferences(SHARD_PREF,Context.MODE_PRIVATE);

        return pref.getBoolean(KEY_FIRST_TIME,true);
    }

}
