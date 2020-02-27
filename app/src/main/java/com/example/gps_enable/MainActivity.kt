package com.example.gps_enable

import androidx.appcompat.app.AppCompatActivity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import android.location.LocationManager
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_location_permission.view.*

class MainActivity : AppCompatActivity() {
    val MY_PERMISSIONS_REQUEST_LOCATION = 99
    var gpsCheck: Boolean = false
    lateinit var mGoogleApiClient: GoogleApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gps_check.setOnClickListener {
            val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)==true) {
                checkGPSEnable()
                changeGPSDisable(this)
            }else{
                mapReadyToast()
                changeGPSEnable(this)

            }
        }

    }

     fun changeGPSEnable(context: Context) {
         Log.e("GPS Enable","GPS on")
         Toast.makeText(context, "GPS is On!", Toast.LENGTH_LONG).show()


    }

     fun changeGPSDisable(context: Context) {
         Log.e("GPS Disable","GPS off")
         Toast.makeText(context, "GPS is Off!", Toast.LENGTH_LONG).show()


     }

    private fun mapReadyToast(){
        Toast.makeText(applicationContext, "Map is Ready!", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@MainActivity,HomeActivity::class.java))


    }


    private fun checkGPSEnable() {
        val factory = LayoutInflater.from(this)
        val deleteDialogView = factory.inflate(R.layout.dialog_location_permission, null)
        val deleteDialog = AlertDialog.Builder(this).create()
        deleteDialog.setView(deleteDialogView)
        deleteDialogView.btn_yes?.setOnClickListener {
            enablegps()
            Toast.makeText(applicationContext, "Location enable", Toast.LENGTH_LONG).show()
            deleteDialog.dismiss()
        }
        deleteDialogView.btn_no?.setOnClickListener {
            deleteDialog.cancel()
            Toast.makeText(applicationContext, "Location not enable", Toast.LENGTH_LONG).show()

        }
        deleteDialog.show()

    }
  /*  private fun checkGPSEnable() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id
                    ->
                    enablegps()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
        val alert = dialogBuilder.create()
        alert.show()
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("onActivityResult()", Integer.toString(resultCode))

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> when (resultCode) {
                Activity.RESULT_OK -> {
                    gpsCheck = true
                    // All required changes were successfully made
//                    Toast.makeText(this@MainActivity, "Location enabled by user!", Toast.LENGTH_LONG).show()

                    Log.e("Yes", gpsCheck.toString())
                    mapReadyToast()

                }
                Activity.RESULT_CANCELED -> {

                    // The user was asked to change settings, but chose not to
                //    Toast.makeText(this@MainActivity, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show()
                    enablegps()
                    gpsCheck = false
                    Log.e("No", gpsCheck.toString())

                }
                else -> {

                }
            }
        }
    }
    fun enablegps() {
        val mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(2000)
                .setFastestInterval(1000)

        val settingsBuilder = LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest)
        settingsBuilder.setAlwaysShow(true)

        val result = LocationServices.getSettingsClient(this).checkLocationSettings(settingsBuilder.build())
        result.addOnCompleteListener { task ->

            //getting the status code from exception
            try {
                task.getResult(ApiException::class.java)
            } catch (ex: ApiException) {

                when (ex.statusCode) {

                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        gpsCheck = false
                        Log.e("No1", gpsCheck.toString())

                  //      Toast.makeText(this, "GPS IS OFF", Toast.LENGTH_SHORT).show()
                        // Show the dialog by calling startResolutionForResult(), and check the result
                        // in onActivityResult().
                        val resolvableApiException = ex as ResolvableApiException
                        resolvableApiException.startResolutionForResult(this, MY_PERMISSIONS_REQUEST_LOCATION

                        )
                        Log.e("No", "Not Network Avialble")


                    } catch (e: IntentSender.SendIntentException) {
                        Toast.makeText(this, "PendingIntent unable to execute request.", Toast.LENGTH_SHORT).show()

                    }
                    LocationSettingsStatusCodes.SUCCESS -> {
                        Log.e("Yes", "Network Avialble")

                        Toast.makeText(this, "Network Avialble", Toast.LENGTH_SHORT).show()

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                        Toast.makeText(this, "PendingIntent unable to execute request.", Toast.LENGTH_SHORT).show()

                    }

                }

            }
        }
    }
}


