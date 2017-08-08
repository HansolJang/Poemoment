package common.libraries;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;

/**
 * Created by bong on 16. 3. 3..
 */
public class Archive {

    private static final String tag = Archive.class.getSimpleName();

    public static String getAppKeyHash(Context context) {
        String something = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                something = new String(Base64.encode(md.digest(), 0));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return something;
    }

    public static String getVersion(Context context) {

        String version= null;

        PackageInfo info = getPackageInfo(context);
        if (info != null) {
            version= info.versionName;
        }

        return version;
    }

    public static int getVersionCode(Context context) {

        int code = 0;

        PackageInfo info = getPackageInfo(context);
        if (info != null) {
            code = info.versionCode;
        }

        return code;
    }



    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;

        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

        }
        catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return info;
    }

    public static String getPackageName(Context context) {

        String packageName = null;

        PackageInfo info = getPackageInfo(context);
        if (info != null) {
            packageName = info.packageName;
        }

        return packageName;
    }


    public static String getLanguageCode(Context context) {
        return context.getResources().getConfiguration().locale.getDisplayLanguage();
    }

    public static final boolean isInstallPackage(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
