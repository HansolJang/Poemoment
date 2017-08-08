package com.jamsi.poemoment.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.jamsi.poemoment.R;

import java.util.Map;

import common.fcm.FcmManager;
import common.fcm.FcmReceiver;


/**
 * Created by zipdoc on 2016. 11. 16..
 */

public class FPushReceiver extends FcmReceiver {

    private static final String tag = FPushReceiver.class.getSimpleName();

    private int id = 0;
    private Intent intent = null;


    @Override
    protected void onFcmReceiver(RemoteMessage remoteMessage, Map<String, String> data) {

        Log.e(tag, "received message : " + data.get("1"));


        // TODO: 2017. 5. 7. receive message

    }

    private void sendPushNotification(String title, String content, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);
//                .setVibrate(new long[]{0, 500});

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(contentIntent);
        notificationManager.notify((++id % 999), builder.build());

    }

}
