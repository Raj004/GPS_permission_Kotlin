package com.example.gps_enable

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService


class InternetConnector_Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /* override fun onReceive(context: Context, intent: Intent) {

         try {
             val isVisible = MyApplication.isActivityVisible()// Check if
             Log.i("Activity is Visible ", "Is activity visible : $isVisible")
             // If it is visible then trigger the task else do nothing
             if (isVisible == true) {
                 val connectivityManager = context
                         .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                 val networkInfo = connectivityManager
                         .activeNetworkInfo
                 // Check internet connection and accrding to state change the
                 if (networkInfo != null && networkInfo.isConnected) {

                     MainActivity().changeTextStatus(true)
                 } else {
                     MainActivity().changeTextStatus(false)
                 }
             }
         } catch (e: Exception) {
             e.printStackTrace()
         }

     }*/

}

