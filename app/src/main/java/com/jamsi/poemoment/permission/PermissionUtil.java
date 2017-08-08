package com.jamsi.poemoment.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipdoc on 2017. 4. 21..
 */

public class PermissionUtil {

    public static final int OVERLAY_REQ = 1001;

    /**
     * 퍼미션 요청
     * @param activity
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissions(Activity activity, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }


    /**
     * 퍼미션 요청 결과 체크
     * @param grantResults
     * @return
     */
    public static boolean verifyPermissions(int... grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 퍼미션 여부 체크
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasSelfPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkSelfPermission(Context context, String... permissions) {
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static String[] checkSelfPermissions(Context context, String... permissions) {

        List<String> granted = new ArrayList<String>();
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                granted.add(permission);
            }
        }

        if (granted.size() > 0) {
            String[] arr = new String[granted.size()];
            return granted.toArray(arr);
        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkDrawOverlayPermission(Context context) {
        return Settings.canDrawOverlays(context);
    }
}
