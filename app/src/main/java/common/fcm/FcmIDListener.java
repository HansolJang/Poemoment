package common.fcm;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import common.libraries.PreferenceUtil;


/**
 * Created by zipdoc on 2016. 11. 16..
 */

abstract public class FcmIDListener extends FirebaseInstanceIdService {

    private static final String tag = FcmIDListener.class.getSimpleName();

    @Override
    public void onTokenRefresh() {

        Log.d(tag, "refreshed token: " + FirebaseInstanceId.getInstance().getToken());

        String token = FirebaseInstanceId.getInstance().getToken();
        Context context = getApplicationContext();
        PreferenceUtil.set(context, TYPEDEF.FCM_REG_NEED, token);
        PreferenceUtil.set(context, TYPEDEF.FCM_EVENT_TOKEN, "Y");

        onTokenRefresh(context, token);
    }

    public abstract void onTokenRefresh(Context context, String token);

}
