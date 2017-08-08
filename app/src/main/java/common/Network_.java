package common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by zipdoc on 2017. 5. 14..
 */

public class Network_ {

    public Network_() {
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService("connectivity");
        if(manager == null) {
            return false;
        } else {
            NetworkInfo wifi = manager.getNetworkInfo(1);
            NetworkInfo mobile = manager.getNetworkInfo(0);
            return wifi.isConnected() || mobile.isConnected();
        }
    }

    public static boolean isSKTOpterator(Context context) {
        TelephonyManager tm = (TelephonyManager)context.getSystemService("phone");
        return tm.getNetworkOperatorName().equalsIgnoreCase("SKTelecom")?true:tm.getSimOperatorName().equalsIgnoreCase("SKTelecom");
    }

    public static String getMyPhoneNumber(Context context) {
        String number = null;
        TelephonyManager tm = (TelephonyManager)context.getSystemService("phone");
        number = tm.getLine1Number();
        return number;
    }
}
