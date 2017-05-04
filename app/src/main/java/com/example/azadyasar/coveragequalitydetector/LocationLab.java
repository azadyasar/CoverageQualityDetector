package com.example.azadyasar.coveragequalitydetector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azadyasar on 18/05/2016.
 */
public class LocationLab {

    private static LocationLab sLocationLab;
    private Context mContext;
    private DatabaseHelper databaseHelper;

    private LocationLab(Context context) {
        mContext = context;
        databaseHelper = new DatabaseHelper(mContext);
    }

    public static LocationLab getLocationLab(Context context) {
        if ( sLocationLab == null)
            sLocationLab = new LocationLab( context );
        return sLocationLab;
    }

    public void writeToDB(LocationData locationData) {
        databaseHelper.insertData(locationData);
    }

    public List<LocationData> getDataFromDB() {
        return databaseHelper.getAllData();
    }

    public ArrayList<LocationData> getDataWithInInterval(Interval interval) {

        return databaseHelper.getDataWithInInterval(interval);

    }

    public void clearRandomly() {
        databaseHelper.clearRandomly();
    }
}
