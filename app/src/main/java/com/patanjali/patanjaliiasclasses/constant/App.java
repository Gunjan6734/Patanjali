package com.patanjali.patanjaliiasclasses.constant;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.patanjali.patanjaliiasclasses.activity.MainActivity;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable e) {
                        Log.d("AppCrash", "Error just lunched ");
                        handleUncaughtException(thread,e);
                    }
                });
    }

    private void handleUncaughtException (Thread thread, Throwable e) {

        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }


}
