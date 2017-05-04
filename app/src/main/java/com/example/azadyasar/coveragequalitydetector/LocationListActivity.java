package com.example.azadyasar.coveragequalitydetector;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LocationListActivity extends AppCompatActivity {

    private ListView locationListView;
    private CustomAdapter adapter;
    private List<LocationData> locationDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        locationListView = (ListView) findViewById(R.id.locationdata_listview);

        locationDatas = LocationLab.getLocationLab(getApplicationContext()).getDataFromDB();

        adapter = new CustomAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
                locationDatas);

//        setListViewBackGroundColors();
        locationListView.setAdapter(adapter);

    }

    private void setListViewBackGroundColors() {

        for(int i = 0; i < locationListView.getChildCount(); i++) {

            locationListView.getChildAt(i).setBackgroundColor(getCorrespondingColor(locationDatas.get(i)));

        }
    }

    private int getCorrespondingColor(LocationData locationData) {
        int signalStrength = locationData.getSignalStrength();
        if ( signalStrength


                == 99 )
            return Color.RED;
        else if (signalStrength > 25)
            return Color.GREEN;
        else if (signalStrength > 20)
            return Color.BLUE;
        else if (signalStrength > 15 )
            return Color.CYAN;
        else if (signalStrength > 10 )
            return Color.YELLOW;
        else if (signalStrength > 5)
            return Color.LTGRAY;
        else return Color.DKGRAY;

    }

}
