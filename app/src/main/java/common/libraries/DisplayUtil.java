package common.libraries;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by bong on 16. 3. 8..
 */
public class DisplayUtil {

    public static float getDestiny(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int DPToPixel(Context context, double dp) {
        return (int)(dp * getDestiny(context) + 0.5);
    }

    public static int PixelToDP(Context context, int px) {
        return (int)(px / getDestiny(context));
    }

    public static int getDisplayWidth(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getDisplayHeight(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static Display getDisplay(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    public static int getDisplayWidth(Context context) {
        return getDisplay(context).getWidth();
    }

    public static int getDisplayHeight(Context context) {
        return getDisplay(context).getHeight();
    }

}
