package com.example.azadyasar.coveragequalitydetector;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LocationMapActivity extends FragmentActivity implements OnMapReadyCallback {

    // North border
    private static final double LATITUDE_START = 37.891380;
    private static final double LATITUDE_END = 40.8126;
    // West border
    private static final double LONGITUDE_START = 40.227470;
    private static final double LONGITUDE_END = 29.4735;
    //Threshold to divide city subsquares
    private static final double THRESHOLD = 0.01;

    private static final String TAG = "InsideMapAct";

    private int latitudeNum;
    private int longituteNum;

    private GoogleMap mMap;
    private Interval subsquares[][];
    private LocationLab mLocationLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
/*
        latitudeNum = (int) ((LATITUDE_START-LATITUDE_END)/THRESHOLD);
        longituteNum = (int) ( (LONGITUDE_END-LONGITUDE_START)/THRESHOLD);
        Log.d(TAG, "Longitude Num:" + longituteNum + "Latitude Num: " + latitudeNum);
        subsquares = new Interval[latitudeNum][longituteNum];*/

       /* for(int i = 0; i < latitudeNum; i++) {
            for(int j = 0; j < longituteNum; j++) {
                double latBeg = (LATITUDE_START) + (THRESHOLD*(i));
                double latEnd = latBeg + THRESHOLD;
                double longBeg = (LONGITUDE_START) + (THRESHOLD*(j));
                double longEnd = longBeg - THRESHOLD;
                subsquares[i][j] = new Interval(latBeg, latEnd, longBeg, longEnd);
            }
        }*/

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mLocationLab = LocationLab.getLocationLab(getApplicationContext());
        //ArrayList<LocationData> locationDatas;
        LatLng point = new LatLng(LATITUDE_START, LONGITUDE_START);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point,10));
        /*for(int i = 0; i < latitudeNum; i++) {
            for(int j = 0; j < longituteNum; j++) {
                Interval interval = subsquares[i][j];
                locationDatas = getLocationDataInInterval(interval);
                if (locationDatas.size() > 0) {
                    int avgSignalStrength = calculateAvgSigStr(locationDatas);
                    LatLng center = new LatLng((interval.getLatBeginning()+THRESHOLD/2),
                            interval.getLongBeginning()+THRESHOLD/2);
                    int color = getColorForCircle(avgSignalStrength);
                    mMap.addCircle(new CircleOptions().center(center).radius(250).strokeColor(Color.TRANSPARENT).fillColor(color));
                }
            }
        }*/

        ArrayList<LocationData> locationDatas = (ArrayList<LocationData>)mLocationLab.getDataFromDB();

        for(int i = 0; i<locationDatas.size(); i+=1) {
            double lat = locationDatas.get(i).getLatitude();
            double longitute = locationDatas.get(i).getLongitude();
           // float color = getColorForMarker(locationData.getSignalStrength());
          //  mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(color)).position(new LatLng(lat, longitute)));
           int color = getColorForCircle(locationDatas.get(i).getSignalStrength());
           mMap.addCircle(new CircleOptions().center(new LatLng(lat, longitute)).radius(250).strokeColor(Color.TRANSPARENT).fillColor(color));
        }


    }

    private int calculateAvgSigStr(ArrayList<LocationData> locationDatas) {
        int sum = 0;
        for (LocationData locationData: locationDatas ) {
            sum += locationData.getSignalStrength();
        }
        return (sum/locationDatas.size());
    }

    private float getColorForMarker(int signalStrength) {

        if ( signalStrength == 99 )
            return BitmapDescriptorFactory.HUE_AZURE;
        else if (signalStrength > 25)
            return BitmapDescriptorFactory.HUE_GREEN;
        else if (signalStrength > 20)
            return BitmapDescriptorFactory.HUE_GREEN;
        else if (signalStrength > 15 )
            return BitmapDescriptorFactory.HUE_CYAN;
        else if (signalStrength > 10 )
            return BitmapDescriptorFactory.HUE_ROSE;
        else if (signalStrength > 5)
            return BitmapDescriptorFactory.HUE_RED;
        else return BitmapDescriptorFactory.HUE_RED;
    }

    private int getColorForCircle(int signalStrength) {

        if ( signalStrength == 99 )
            return Color.BLACK;
        else if (signalStrength > 25)
            return Color.rgb(11, 189, 40);
        else if (signalStrength > 20)
            return Color.rgb(44,225,56);
        else if (signalStrength > 15 )
            return Color.rgb(146, 244, 117);
        else if (signalStrength > 10 )
            return Color.rgb(225,127,49);
        else if (signalStrength > 5)
            return Color.rgb(187,57,25);
        else return Color.rgb(63,5, 5);
    }

    private ArrayList<LocationData> getLocationDataInInterval(Interval interval) {
        return mLocationLab.getDataWithInInterval(interval);
    }
}
