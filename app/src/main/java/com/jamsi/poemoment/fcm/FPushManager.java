package com.jamsi.poemoment.fcm;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.messaging.FirebaseMessaging;

import common.fcm.FcmManager;

/**
 * Created by zipdoc on 2016. 11. 16..
 */

public class FPushManager {

    public interface IFPushListener {
        void onResult(boolean result);
    }

    public static boolean init(Activity a) {
        return FcmManager.checkGoogleAPI(a);
    }


    public static void registerToken(final Context context) {

        final String origin = FcmManager.getCurrentToken(context);
        final String update = FcmManager.getUpdateToken(context);
        if (!origin.equals(update)) {

            //todo server token

        }
    }

    public static void update(Context context) {

        if (!FcmManager.isInvokeFcmEvent(context)) {
            FcmManager.setToken(context);
            FcmManager.setInvokeFcmEvent(context, "Y");
        }

        if (FcmManager.isNeedUpdateToken(context)) {
            registerToken(context);
        }
    }

    public static void subscribeToTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
    }

    public static void unsubscribeFromTopic(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
    }

}
