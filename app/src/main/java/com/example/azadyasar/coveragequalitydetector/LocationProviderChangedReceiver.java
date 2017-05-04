package com.example.azadyasar.coveragequalitydetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by azadyasar on 18/05/2016.
 */
public class LocationProviderChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "InsideLocProvReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED") ) {
            CustomLocationManager locationManager = CustomLocationManager.getINSTANCE( MenuActivity.getContext() );
            locationManager.processInput();
        }
    }

}
