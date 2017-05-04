package com.example.azadyasar.coveragequalitydetector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by azadyasar on 22/05/2016.
 */
public class CustomAdapter extends ArrayAdapter<LocationData>{

    private ArrayList<LocationData> locationDatas;
    private Context context;


    public CustomAdapter(Context context, int resource,  List<LocationData> objects) {
        super(context, resource, objects);
        locationDatas = (ArrayList<LocationData>) objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int color = getCorrespondingColor(locationDatas.get(position));
        view.setBackgroundColor(color);
        return view;
    }

    private int getCorrespondingColor(LocationData locationData) {
        int signalStrength = locationData.getSignalStrength();
        if ( signalStrength == 99 )
            return Color.rgb(0, 0, 0);
        else if (signalStrength > 25)
            return Color.rgb(01, 8*16+14, 03);
        else if (signalStrength > 20)
            return Color.rgb(5*16 + 12,16*13+11,14);
        else if (signalStrength > 15 )
            return Color.rgb(3*16+14,14*16+7,4*16+0);
        else if (signalStrength > 10 )
            return Color.rgb(10*16+6, 16*15+7,16*10 +7);
        else if (signalStrength > 5)
            return Color.LTGRAY;
        else return Color.DKGRAY;

    }

}
