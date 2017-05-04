package com.example.azadyasar.coveragequalitydetector;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by azadyasar on 18/05/2016.
 */
public class CustomLocationManager {

    private static final String TAG = "InsideCustomLocMan";
    private static final long DISTANCE_CHANGE = 20;
    private static final long DURATION = 1000 * 30;


    private Location mCurrentLocation;
    private LocationManager mLocationManager;
    private CustomLocationListener locationListener;
    private static CustomLocationManager INSTANCE;
    private Context mContext;
    private String carrierName;
    private String date;
    private TelephonyManager telephonyManager;
    private SignalStrength mCurrentSignalStrength;

    public CustomLocationManager(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mContext = context;
        locationListener = new CustomLocationListener();
    }

    public static CustomLocationManager getINSTANCE(Context context) {
        if (INSTANCE == null)
            INSTANCE = new CustomLocationManager(context);
        return INSTANCE;
    }

    public void startUpdateLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);

        String infoProvider = mLocationManager.getBestProvider(criteria, true);

        if (infoProvider == null) {
            List<String> infoProviders = mLocationManager.getAllProviders();
            Log.d(TAG, "infoProvider == null");

            for (String temp : infoProviders)
                if (mLocationManager.isProviderEnabled(temp))
                    infoProvider = temp;
        }

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "Permission is not taken");
            return;
        }

        telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        CustomPhoneStateListener psListener = new CustomPhoneStateListener();
        telephonyManager.listen(psListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

        mCurrentLocation = mLocationManager.getLastKnownLocation(infoProvider);
        processInput();

        mLocationManager.requestLocationUpdates(infoProvider, DURATION, DISTANCE_CHANGE, locationListener);
    }

    public void stopUpdateLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "Permission is not taken");
            return;
        }
        if (mLocationManager != null)
            mLocationManager.removeUpdates(locationListener);
    }

    public Location getCurrentLocation() {
        return mCurrentLocation;
    }

    public void checkDate() {

        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int day = c.get( Calendar.DAY_OF_MONTH );
        int month = c.get( Calendar.MONTH);
        month++;
        int year = c.get( Calendar.YEAR);
        int hourofday = c.get( Calendar.HOUR_OF_DAY);
        int minute = c.get( Calendar.MINUTE);
        date = day + "-" + month + "-" + year + " - " + hourofday + ":" + minute;
    }

    public void processInput() {

        checkDate();
/*
//        CellInfoGsm cellinfogsm = null;
        CellInfoWcdma cellInfoWcdma = null;
        int level = -1;
//        int level2 = -1;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            cellInfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
//            cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
            CellSignalStrengthWcdma cellSignalStrengthWcdma = null;
//            CellSignalStrengthGsm cellSignalStrengthGsm = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
//                cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();

                level = cellSignalStrengthWcdma.getAsuLevel();
//                level2 = cellSignalStrengthGsm.getAsuLevel();

                Log.d(TAG, "Signal level obtained -" + level);
//                Log.d(TAG, "Signal level obtained2 -" + level2);
            }

        }*/

        int signalStrengthValue = -1;
        int signalStrengthValueCalc = -1;

        if (mCurrentSignalStrength != null){

            signalStrengthValue = mCurrentSignalStrength.getGsmSignalStrength();
            signalStrengthValueCalc = (2 * signalStrengthValue) - 113; // -> dBm

            Log.d(TAG, "SignalStrengthValue: " + signalStrengthValue);
            Log.d(TAG, "SignalStrengthValueCalc: " + signalStrengthValueCalc);

        }else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    CellSignalStrengthWcdma signalStrength = cellInfoWcdma.getCellSignalStrength();
                    signalStrengthValue = signalStrength.getAsuLevel();
                    // ASU ranges from 0 to 31 - TS 27.007 Sec 8.5
                    // asu = 0 (-113dB or less) is very weak
                    // signal, its better to show 0 bars to the user in such cases.
                    // asu = 99 is a special case, where the signal strength is unknown.
                }
            }
        }


        LocationData locationData = new LocationData(date, signalStrengthValue, mCurrentLocation.getLongitude(),
                mCurrentLocation.getLatitude(),
                carrierName);

        LocationLab.getLocationLab( mContext ).writeToDB(locationData);


        carrierName = telephonyManager.getNetworkOperatorName();
        Log.d(TAG, "Operator Name obtained: " + carrierName);

    }

    private class CustomLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location location) {
            mCurrentLocation = location;
            processInput();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(mContext, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(mContext, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
        }
    }

    class CustomPhoneStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);

            checkDate();

            mCurrentSignalStrength = signalStrength;

            int signalStrengthValue = signalStrength.getGsmSignalStrength();
            int signalStrengthValueCalc = (2 * signalStrengthValue) - 113; // -> dBm

            Log.d(TAG, "SignalStrengthValue: " + signalStrengthValue);
            Log.d(TAG, "SignalStrengthValueCalc: " + signalStrengthValueCalc);

            // for example value of first element
//            CellInfoGsm cellinfogsm = null;
            CellInfoWcdma cellInfoWcdma = null;
            int level = -1;
            int level2 = -1;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cellInfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
//                cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                CellSignalStrengthWcdma cellSignalStrengthWcdma = null;
                CellSignalStrengthGsm cellSignalStrengthGsm = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
//                    cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();

                    level = cellSignalStrengthWcdma.getAsuLevel();
//                    level2 = cellSignalStrengthGsm.getAsuLevel();

                    Log.d(TAG, "Signal level obtained -" + level);
//                    Log.d(TAG, "Signal level obtained2 -" + level2);
                }

            }


            LocationData locationData = new LocationData(date, signalStrengthValue, mCurrentLocation.getLongitude(),
                    mCurrentLocation.getLatitude(),
                    carrierName);

            LocationLab.getLocationLab( mContext ).writeToDB(locationData);
        }

    }

}
