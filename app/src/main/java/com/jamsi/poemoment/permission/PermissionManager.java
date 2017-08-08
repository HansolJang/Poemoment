package com.jamsi.poemoment.permission;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by zipdoc on 2017. 4. 21..
 */

public class PermissionManager {

    public static boolean permissionCheckDialog(Activity a, String permissions[]) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int requestCode = 0;

            permissions = PermissionUtil.checkSelfPermissions(a, permissions);
            if (permissions != null) {
                PermissionUtil.requestPermissions(a, requestCode, permissions);
                return false;
            }
        }
        return true;
    }

    public static boolean overlayPermissionCheck(Activity a) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if(!PermissionUtil.checkDrawOverlayPermission(a)) {

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + a.getPackageName()));
                a.startActivityForResult(intent, PermissionUtil.OVERLAY_REQ);
                return false;
            }
        }
        return true;
    }
}
