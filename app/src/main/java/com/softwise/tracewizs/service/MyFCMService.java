package com.softwise.tracewizs.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

/*import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;*/
import com.softwise.tracewizs.R;
import com.softwise.tracewizs.activitys.LauncherActivity;

import java.util.Map;

/*
@SuppressLint("MissingFirebaseInstanceTokenRefresh")

public class MyFCMService extends FirebaseMessagingService {
    private static final String TAG = "MyFCMService";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // log the getting message from firebase
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        //  if remote message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            String jobType = data.get("type");
        }
        // if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void handleNow(Map<String, String> data) {
        if (data.containsKey("title") && data.containsKey("message")) {
            sendNotification(data.get("title"), data.get("message"));
        }
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, LauncherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 */
/* Request code *//*
, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Channel human readable title
            NotificationChannel channel = new NotificationChannel(channelId,
                    "TraceWiz Messaging Service",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 */
/* ID of notification *//*
, notificationBuilder.build());
    }
}
*/
public class MyFCMService{

}
