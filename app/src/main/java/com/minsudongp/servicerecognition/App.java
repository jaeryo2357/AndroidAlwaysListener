package com.minsudongp.servicerecognition;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANEL_ID="ServiceChannel";

    private static volatile App instance = null;



    public static App getGlobalApplicationContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel serviceChannel=new NotificationChannel(
                    CHANEL_ID,
                    "Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }
}
