package com.example.gps_enable;

import android.app.Application;

public class MyApplication extends Application {
    public static boolean activityVisible;


    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed(){
        activityVisible = true;
    }
    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }

}