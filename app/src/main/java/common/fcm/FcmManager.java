package common.fcm;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;

import common.libraries.Archive;
import common.libraries.PreferenceUtil;

import static common.fcm.TYPEDEF.FCM_EVENT_TOKEN;
import static common.fcm.TYPEDEF.FCM_REG_NEED;
import static common.fcm.TYPEDEF.FCM_TOKEN;
import static common.fcm.TYPEDEF.FCM_TOKEN_VER;

/**
 * Created by zipdoc on 2016. 11. 16..
 */

public class FcmManager {

    private static final String tag = FcmManager.class.getSimpleName();

    public static GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    private static boolean isOk(int resultCode) {
        return (resultCode == ConnectionResult.SUCCESS);
    }

    private static int checkPlayServices(Context context) {

        return googleAPI.isGooglePlayServicesAvailable(context);
    }

    private static boolean showSystemMessage(final Activity a, int resultCode) {

        boolean flag = false;

        if(googleAPI.isUserResolvableError(resultCode)) {
            googleAPI.getErrorDialog(a, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    a.finish();
                }
            }).show();
            flag = true;
        }
        return flag;
    }

    private static boolean isValidFcmToken(Context context, String fcm_token, String token_ver) {

        boolean flag = false;

        if (!TextUtils.isEmpty(fcm_token) && !TextUtils.isEmpty(token_ver)) {
            int regVersionCode = Integer.parseInt(token_ver);

            int curVersionCode = Archive.getVersionCode(context);
            if (regVersionCode == curVersionCode) {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean checkGoogleAPI(Activity a) {

        int resultCode = 0;
        resultCode = checkPlayServices(a);

        if(!isOk(resultCode)) {
            showSystemMessage(a, resultCode);
            return false;
        }
        return true;
    }

    //    public static void saveFcmToken(Context context, String fcm_token, String token_ver) {
//        PreferenceUtil.set(context, TYPEDEF.FCM_TOKEN, fcm_token);
//        PreferenceUtil.set(context, TYPEDEF.FCM_TOKEN_VER, token_ver);
//    }
//
    public static void setIgnore(Context context, boolean ignore) {
        PreferenceUtil.set(context, TYPEDEF.FCM_RCV_IGNORE, ignore);
    }

    public static boolean isIgnore(Context context) {
        return PreferenceUtil.getBoolean(context, TYPEDEF.FCM_RCV_IGNORE);
    }

    public static void updateToken(Context context, String token, String version) {
        PreferenceUtil.set(context, FCM_TOKEN, token);
        PreferenceUtil.set(context, FCM_TOKEN_VER, version);
        PreferenceUtil.set(context, FCM_REG_NEED, "");
    }

    public static boolean isNeedUpdateToken(Context context) {
        String token = PreferenceUtil.get(context, FCM_REG_NEED);
        return (token.length() > 0);
    }

    public static String getCurrentToken(Context context) {
        return PreferenceUtil.get(context, FCM_TOKEN);
    }

    public static String getUpdateToken(Context context) {
        return PreferenceUtil.get(context, FCM_REG_NEED);
    }

    public static boolean isInvokeFcmEvent(Context context) {
        return PreferenceUtil.get(context, FCM_EVENT_TOKEN).equals("Y");
    }

    public static void setToken(Context context) {
        String token = FirebaseInstanceId.getInstance().getToken();
        PreferenceUtil.set(context, TYPEDEF.FCM_REG_NEED, token);
    }

    public static void setInvokeFcmEvent(Context context, String string) {
        PreferenceUtil.set(context, FCM_EVENT_TOKEN, string);
    }


}
