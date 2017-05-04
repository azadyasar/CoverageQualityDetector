package com.example.azadyasar.coveragequalitydetector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by azadyasar on 18/05/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 3;
    private static final String TAG = "InsideDatabaseHelper";
    private static final String DATABASE_NAME = "LocationDatas.db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LocationDBSchema.LocationTable.TABLE_NAME + "( " +
                LocationDBSchema.LocationTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LocationDBSchema.LocationTable.Cols.OPERATOR_NAME + " TEXT, "
                + LocationDBSchema.LocationTable.Cols.LATITUDE + " REAL, "
                + LocationDBSchema.LocationTable.Cols.LONGITUDE + " REAL, "
                + LocationDBSchema.LocationTable.Cols.DATE + " TEXT, "
                + LocationDBSchema.LocationTable.Cols.SIGNAL_STRENGTH + " INTEGER)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LocationDBSchema.LocationTable.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(LocationData locationData){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LocationDBSchema.LocationTable.Cols.OPERATOR_NAME, locationData.getOperatorName());
        values.put(LocationDBSchema.LocationTable.Cols.LATITUDE, locationData.getLatitude());
        values.put(LocationDBSchema.LocationTable.Cols.LONGITUDE, locationData.getLongitude());
        values.put(LocationDBSchema.LocationTable.Cols.DATE, locationData.getDate() );
        values.put(LocationDBSchema.LocationTable.Cols.SIGNAL_STRENGTH, locationData.getSignalStrength());

        Log.d(TAG, "Lat: " + locationData.getLatitude() + " Long: " + locationData.getLongitude());

        long result = db.insert(LocationDBSchema.LocationTable.TABLE_NAME, null, values);

        db.close();

        if (result == -1 )
            return false;
        return true;
    }

    public List<LocationData> getAllData() {

        List<LocationData> locationDatas = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LocationDBSchema.LocationTable.TABLE_NAME, null);

        if (cursor.getCount() == 0){
            Log.d(TAG, " cursor.getCount() == 0");
            return locationDatas;
        }

        while (cursor.moveToNext() ) {

            String operatorName = cursor.getString( cursor.getColumnIndex(
                    LocationDBSchema.LocationTable.Cols.OPERATOR_NAME) );
            String date = cursor.getString( cursor.getColumnIndex(
                    LocationDBSchema.LocationTable.Cols.DATE ));
            double lat = cursor.getDouble( cursor.getColumnIndex(
                    LocationDBSchema.LocationTable.Cols.LATITUDE ));
            double longit = cursor.getDouble( cursor.getColumnIndex(
                    LocationDBSchema.LocationTable.Cols.LONGITUDE) );
            int strength = cursor.getInt( cursor.getColumnIndex(
                    LocationDBSchema.LocationTable.Cols.SIGNAL_STRENGTH));

            locationDatas.add(new LocationData(date, strength, longit, lat, operatorName ) );

        }

        cursor.close();
        db.close();

        return locationDatas;
    }

    public ArrayList<LocationData> getDataWithInInterval(Interval interval) {

        ArrayList<LocationData> locationDatas = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(LocationDBSchema.LocationTable.TABLE_NAME, new String[] {
                LocationDBSchema.LocationTable.Cols.ID, LocationDBSchema.LocationTable.Cols.OPERATOR_NAME,
                LocationDBSchema.LocationTable.Cols.DATE, LocationDBSchema.LocationTable.Cols.SIGNAL_STRENGTH,
                LocationDBSchema.LocationTable.Cols.LONGITUDE, LocationDBSchema.LocationTable.Cols.LATITUDE },
                LocationDBSchema.LocationTable.Cols.LATITUDE + " >= " + interval.getLatBeginning() +  " AND " +
                LocationDBSchema.LocationTable.Cols.LATITUDE + " <= " + interval.getLatEnd() + " AND " +
                LocationDBSchema.LocationTable.Cols.LONGITUDE + " >= " + interval.getLongBeginning() +
                " AND " + LocationDBSchema.LocationTable.Cols.LONGITUDE + " <= " + interval.getLongEnd(),
                null, null, null, null);

        Log.d(TAG, "Cursor Count: " + cursor.getCount());

        while (cursor.moveToNext()) {
            String operatorName = cursor.getString( cursor.getColumnIndex(
                    LocationDBSchema.LocationTable.Cols.OPERATOR_NAME) );
            String date = cursor.getString( cursor.getColumnIndex(
                    LocationDBSchema.LocationTable.Cols.DATE ));
            double lat = cursor.getDouble( cursor.getColumnIndex(
                    LocationDBSchema.LocationTable.Cols.LATITUDE ));
            double longit = cursor.getDouble( cursor.getColumnIndex(
                    LocationDBSchema.LocationTable.Cols.LONGITUDE) );
            int strength = cursor.getInt( cursor.getColumnIndex(
                    LocationDBSchema.LocationTable.Cols.SIGNAL_STRENGTH));

            locationDatas.add(new LocationData(date, strength, longit, lat, operatorName ) );
        }

        cursor.close();
        db.close();
        return locationDatas;
    }

    public void clearRandomly() {
        Random random = new Random();
        int times = 250;
        SQLiteDatabase database = getWritableDatabase();

        for(int i = 0; i<times; i++){
            int row = random.nextInt();
            long rows = database.delete(LocationDBSchema.LocationTable.TABLE_NAME,
                    LocationDBSchema.LocationTable.Cols.ID + " = " + row, null );

        }
    }
}










