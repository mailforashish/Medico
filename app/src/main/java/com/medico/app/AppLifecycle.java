package com.medico.app;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.medico.app.utils.SessionManager;

import java.util.HashMap;

import androidx.lifecycle.ProcessLifecycleOwner;

public class AppLifecycle extends Application implements LifecycleObserver {
    private static Context appContext;
    public static boolean wasInBackground;
    HashMap<String, String> user;
    private String AppID = "";
    private static AppLifecycle mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    public static Context getAppContext() {
        return appContext;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onMoveToForeground() {
        // app moved to foreground
        wasInBackground = true;
        //FirebaseDatabase.getInstance().goOnline();
        user = new SessionManager(appContext).getUserDetails();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onMoveToBackground() {
        // app moved to background
        wasInBackground = false;
    }

    public static AppLifecycle the() {
        return mInstance;
    }

    public AppLifecycle() {
        mInstance = this;
    }


}