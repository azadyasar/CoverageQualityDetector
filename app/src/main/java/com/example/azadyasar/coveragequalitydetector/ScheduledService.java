package com.example.azadyasar.coveragequalitydetector;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ScheduledService extends Service {

    private static final long DURATION = 1000 * 60 * 5;

    private CustomLocationManager customLocationManager;
    private boolean mCondition;

    @Override
    public void onCreate() {
        super.onCreate();
        customLocationManager = CustomLocationManager.getINSTANCE(getApplicationContext());
        customLocationManager.startUpdateLocation();
        new Thread(new ScheduledDataListener()).start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setCondition(boolean condition) {
        this.mCondition = condition;
    }

    private class ScheduledDataListener implements Runnable {

        @Override
        public void run() {
            try {
                while (mCondition) {
                    customLocationManager.processInput();
                    Thread.sleep(DURATION);
                }
            }catch (InterruptedException e){
                Log.d("ScheduledService", e.getMessage());
            }
        }
    }
}
