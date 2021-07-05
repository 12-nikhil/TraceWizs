package com.softwise.tracewizs.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.softwise.tracewizs.tracewizapplication.TraceWizApplication;
import com.softwise.tracewizs.utils.ConnectionUtils;
import com.softwise.tracewizs.utils.TraceWizConstant;

public class InternetConnectionReceiver extends BroadcastReceiver {
    public static ConnectivityReceiverListener connectivityReceiverListener;
    static Context mcontext;

    public InternetConnectionReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mcontext = context;
        /*get action name from activity which is trigger the broadcast receiver*/
        String action = intent.getAction();

        /*check action name*/
        if (("android.net.conn.CONNECTIVITY_CHANGE").equals(action)) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null
                    && activeNetwork.isConnectedOrConnecting();
            Log.e("receiver","on");
            if (connectivityReceiverListener != null) {
                connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
            }

        }
    }

    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) TraceWizApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }

}
