package com.example.azadyasar.coveragequalitydetector;

import android.location.Location;

/**
 * Created by azadyasar on 18/05/2016.
 */
public class LocationData {

    private String date;
    private int signalStrength;
    private double longitude, latitude;
    private String operatorName;

    public LocationData(String date, int signalStrength, double longitude, double latitude, String operatorName) {
        this.date = date;
        this.signalStrength = signalStrength;
        this.longitude = longitude;
        this.latitude = latitude;
        this.operatorName = operatorName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getSignalDescription() {
        //s>25 is Excellent | 20<s<25 is Very Good | 15 < s < 20 is Good | 10<s<15 is Normal
        // 5<s<10 is Bad | 0<s<5 is Very Bad | s=99 is unknown
        String description;
        if ( signalStrength == 99 )
            description = "Unknown";
        else if (signalStrength > 25)
            description = "Excellent";
        else if (signalStrength > 20)
            description = "Very Good";
        else if (signalStrength > 15 )
            description = "Good";
        else if (signalStrength > 10 )
            description = "Normal";
        else if (signalStrength > 5)
            description = "Bad";
        else description = "Very Bad";

        return description;
    }

    @Override
    public String toString() {
        return getOperatorName() + " Latitude: " + getLatitude()  + " Longitude: " + getLongitude()
                + " Date: " + getDate() + " Signal Strength: "  + getSignalDescription();

    }
}
