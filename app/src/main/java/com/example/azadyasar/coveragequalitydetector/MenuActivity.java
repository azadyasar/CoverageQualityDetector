package com.example.azadyasar.coveragequalitydetector;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button showMapButton, locationListButton;
    private static Context sContext;
    private CustomLocationManager locationManager;
    private Button resumeButton, cleanButton;

    private static boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sContext = getApplicationContext();
        showMapButton = (Button) findViewById(R.id.showmap_button);
        locationListButton = (Button) findViewById(R.id.listoflocations_button);
        resumeButton = (Button) findViewById(R.id.resume_button);
        resumeButton.setBackgroundResource(R.drawable.button_resume);
       // cleanButton = (Button) findViewById(R.id.clean_button);

        Intent i = new Intent(this, ScheduledService.class);
        startService(i);

        isPaused = false;

        locationListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, LocationListActivity.class);
                startActivity(i);
            }
        });

        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, LocationMapActivity.class);
                startActivity(i);
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused)
                    resumeButton.setBackgroundResource(R.drawable.button_resume);
                else
                    resumeButton.setBackgroundResource(R.drawable.button_pause);
            }
        });

        /*cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationLab locationLab = LocationLab.getLocationLab(getApplicationContext());
                locationLab.clearRandomly();
            }
        });*/


    }

    public static Context getContext() {
        return sContext;
    }
}
