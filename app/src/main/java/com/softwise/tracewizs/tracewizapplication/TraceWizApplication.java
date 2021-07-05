package com.softwise.tracewizs.tracewizapplication;

import android.app.Application;

//import com.google.firebase.messaging.FirebaseMessaging;
import com.softwise.tracewizs.receiver.InternetConnectionReceiver;

public class TraceWizApplication extends Application {
    private static TraceWizApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //FirebaseMessaging.getInstance().setAutoInitEnabled(true);

    }
    public static synchronized TraceWizApplication getInstance() {
        return mInstance;
    }



    public void setInternetConnectedListeners(InternetConnectionReceiver.ConnectivityReceiverListener connectedListeners){
        InternetConnectionReceiver.connectivityReceiverListener = connectedListeners;
   }
}
