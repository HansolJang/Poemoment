package common.libraries;

import android.app.Activity;


/**
 * Created by bong on 16. 3. 3..
 */
public class ActivityCtrler {

    private long backKeyPressedTime = 0;
    private Activity activity = null;
    private int res = 0;

    public ActivityCtrler(Activity activity, int res) {
        this.activity = activity;
        this.res = res;
    }

    public boolean onBackPressed() {
        if (System.currentTimeMillis() <= backKeyPressedTime) {
            /*
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 100);
            */
            activity.finish();
            return true;
        }
        backKeyPressedTime = System.currentTimeMillis() + 3000;
        android.widget.Toast.makeText(activity, activity.getResources().getString(res),
                android.widget.Toast.LENGTH_SHORT).show();

        return false;
    }
}
