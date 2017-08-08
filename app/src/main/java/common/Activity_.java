package common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.jamsi.poemoment.R;

import common.libraries.ActivityCtrler;


/**
 * Created by bong on 16. 3. 3..
 */
public class Activity_ extends FragmentActivity {

    private static final String tag = Activity_.class.getSimpleName();
    private boolean br_flag = false;
    private int backPressedToastRes = 0;
    private ActivityCtrler activityCtrler = null;
    private int transitionId = 0;

    private int finishTransitionId = 0;
    private int startTransitionId = 0;

    public static final int TRANSITION_BACKOFF = 1;
    public static final int TRANSITION_FLIP_HORIZONTAL = 2;
    public static final int TRANSITION_FLIP_VERTICAL = 3;
    public static final int TRANSITION_FADE = 4;
    public static final int TRANSITION_DISAPPEAR_TOP_LEFT = 5;
    public static final int TRANSITION_APPEAR_TOP_LEFT = 6;
    public static final int TRANSITION_DISAPPEAR_BOTTOM_RIGHT = 7;
    public static final int TRANSITION_APPEAR_BOTTOM_RIGHT = 8;
    public static final int TRANSITION_UNZOOM = 9;
    public static final int TRANSITION_PULL_RIGHT_PUSH_LEFT = 10;
    public static final int TRANSITION_PULL_LEFT_PUSH_RIGHT = 11;

    public static final int TRANSITION_SLIDE_IN_BOTTOM = 12;
    public static final int TRANSITION_SLIDE_IN_LEFT = 13;
    public static final int TRANSITION_SLIDE_IN_RIGHT = 14;
    public static final int TRANSITION_SLIDE_IN_TOP = 15;

    public static final int TRANSITION_SLIDE_BOTTOM_IN = 16;
    public static final int TRANSITION_HEAD = 17;
    public static final int TRANSITION_TV_OFF = 18;

    public static final int TRANSITION_FAST_FADE = 19;

    public static final int TRANSITION_NONE = 20;


    private final String TRANSITION_TYPE = "transition_type";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (startTransitionId) {
            case TRANSITION_SLIDE_BOTTOM_IN: {
                overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
                break;
            }
            default: {
                break;
            }
        }

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(TRANSITION_TYPE)) {
                transitionId = intent.getIntExtra(TRANSITION_TYPE, 0);
            }
        }

        try {
            ActivityStack stack = ActivityStack.getInstance(ActivityStack.class);
            stack.addStack(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (br_flag) {
            unregisterBroadcastReceiver();
        }
        try {
            ActivityStack stack = ActivityStack.getInstance(ActivityStack.class);
            stack.remStack(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {

        }

    }

    private static final String action_name = Activity_.class.getSimpleName();
    public static boolean verifyReceiverAction(Intent intent) {
        boolean flag = false;
        String action = intent.getAction();
        if (action != null) {
            flag = action.equals(action_name);
        }
        return flag;
    }
    protected void registerBroadcastReceiver() {

        IntentFilter filter = new IntentFilter();
        filter.addAction(action_name);
        LocalBroadcastManager.getInstance(this).registerReceiver(onListener, filter);
        br_flag = true;
    }

    protected void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onListener);
        br_flag = false;
    }

    private BroadcastReceiver onListener = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(action_name)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    onBroadcastReceiver(intent);
                }
            }
        }
    };

    protected void onBroadcastReceiver(Intent intent) {}

    public boolean sendBroadcastReceiver(String key, String value) {
        Intent intent = NEW_DEFAULT_INTENT(key, value);
        return sendBroadcastReceiver(intent);
    }

    public boolean sendBroadcastReceiver(Intent intent) {
        boolean result = LocalBroadcastManager.getInstance(Activity_.this).sendBroadcast(intent);
        Log.d(tag, "sendBroadcastReceiver : " + result);
        return result;
    }

    public static boolean sendBroadcastReceiver(Context context, Intent intent) {
        boolean result = LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        Log.d(tag, "sendBroadcastReceiver : " + result);
        return result;
    }

    public static Intent NEW_DEFAULT_INTENT() {
        return new Intent(action_name);
    }

    public static Intent NEW_DEFAULT_INTENT(String key, String value) {
        Intent intent = NEW_DEFAULT_INTENT();
        intent.putExtra(key, value);
        return intent;
    }

    public static Intent NEW_DEFAULT_INTENT(Intent src) {
        Intent intent = NEW_DEFAULT_INTENT();
        Bundle bundle = src.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                String s = value.toString();
                intent.putExtra(key, s);
            }
        }
        return intent;
    }

    protected void finish_fade() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    protected void finish_none() {
        finish();
        overridePendingTransition(0, 0);
    }

    protected void finish_fade(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
        finish_fade();
    }

    protected void finish_fade(Intent intent) {
        startActivity(intent);
        finish_fade();
    }

    protected void finish_tvoff() {
        finish();
        overridePendingTransition(R.anim.tv_off, R.anim.tv_off);
    }

    protected void finish_backoff() {
        finish();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    public void startActivityTransition(Context context, Class clazz, int transitionId) {
        Intent intent = new Intent(context, clazz);
        startActivityTransition(intent, transitionId);
    }

    protected void setBackPressedToast(int backPressedToastRes) {
        activityCtrler = null;
        this.backPressedToastRes = backPressedToastRes;
        if (this.backPressedToastRes != 0) {
            activityCtrler = new ActivityCtrler(this, backPressedToastRes);
        }
    }

    protected void setFinishTransition(int finishTransitionId) {
        this.finishTransitionId = finishTransitionId;
    }

    protected void setStartTransitionId(int startTransitionId) {
        this.startTransitionId = startTransitionId;
    }

    protected void onFinish() {}

    @Override
    public void onBackPressed() {
        if (activityCtrler != null) {
            boolean flag = activityCtrler.onBackPressed();
            if (flag) {
                if (finishTransitionId != 0) {
                    switch (finishTransitionId) {
                        case TRANSITION_TV_OFF: {
                            overridePendingTransition(R.anim.tv_off, R.anim.tv_off);
                            break;
                        }
                        case TRANSITION_UNZOOM: {
                            overridePendingTransition(R.anim.unzoom_in, R.anim.unzoom_out);
                            break;
                        }
                        case TRANSITION_FADE: {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            break;
                        }
                        case TRANSITION_SLIDE_BOTTOM_IN: {
                            overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
                            break;
                        }
                        case TRANSITION_NONE: {
                            overridePendingTransition(0, 0);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
                onFinish();
            }
        }
        else {
            super.onBackPressed();
            switch (transitionId) {
                case TRANSITION_BACKOFF: {
                    overridePendingTransition (R.anim.open_main, R.anim.close_next);
                    break;
                }
                case TRANSITION_SLIDE_IN_BOTTOM: {
                    overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
                    break;
                }
                case TRANSITION_SLIDE_IN_LEFT: {
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                }
                case TRANSITION_SLIDE_IN_RIGHT: {
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;
                }
                case TRANSITION_SLIDE_IN_TOP: {
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                    break;
                }
                case TRANSITION_NONE: {
                    overridePendingTransition(0, 0);
                    break;
                }
                default:{
                    break;
                }
            }
        }
    }

    public void startActivityTransition(Intent intent, int transitionId) {

        intent.putExtra(TRANSITION_TYPE, transitionId);
        startActivity(intent);

        switch (transitionId) {
            case TRANSITION_TV_OFF: {
                overridePendingTransition(R.anim.tv_off, R.anim.tv_off);
                break;
            }
            case TRANSITION_HEAD: {
                overridePendingTransition(R.anim.head_in, 0);
                break;
            }
            case TRANSITION_SLIDE_BOTTOM_IN: {
                overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
                break;
            }
            case TRANSITION_SLIDE_IN_BOTTOM: {
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                break;
            }
            case TRANSITION_SLIDE_IN_LEFT: {
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            }
            case TRANSITION_SLIDE_IN_RIGHT: {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
            case TRANSITION_SLIDE_IN_TOP: {
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
                break;
            }
            case TRANSITION_BACKOFF: {
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                break;
            }
            case TRANSITION_FLIP_HORIZONTAL: {
                overridePendingTransition(R.anim.flip_horizontal_in, R.anim.flip_horizontal_out);
                break;
            }
            case TRANSITION_FLIP_VERTICAL: {
                overridePendingTransition(R.anim.flip_vertical_in, R.anim.flip_vertical_out);
                break;
            }
            case TRANSITION_FAST_FADE: {
                //overridePendingTransition(R.anim.fast_fade_in, R.anim.fade_out);
                overridePendingTransition(0, 0);
                break;
            }
            case TRANSITION_FADE: {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            }
            case TRANSITION_DISAPPEAR_TOP_LEFT: {
                overridePendingTransition(R.anim.disappear_top_left_in, R.anim.disappear_top_left_out);
                break;
            }
            case TRANSITION_APPEAR_TOP_LEFT: {
                overridePendingTransition(R.anim.appear_top_left_in, R.anim.appear_top_left_out);
                break;
            }
            case TRANSITION_DISAPPEAR_BOTTOM_RIGHT: {
                overridePendingTransition(R.anim.disappear_bottom_right_in, R.anim.disappear_bottom_right_out);
                break;
            }
            case TRANSITION_APPEAR_BOTTOM_RIGHT: {
                overridePendingTransition(R.anim.appear_bottom_right_in, R.anim.appear_bottom_right_out);
                break;
            }
            case TRANSITION_UNZOOM: {
                overridePendingTransition(R.anim.unzoom_in, R.anim.unzoom_out);
                break;
            }
            case TRANSITION_PULL_RIGHT_PUSH_LEFT: {
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
            }
            case TRANSITION_PULL_LEFT_PUSH_RIGHT: {
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            }
            case TRANSITION_NONE: {
                overridePendingTransition(0, 0);
                break;
            }
            default: {
                break;
            }
        }
    }

    public void startActivityTransitionForResult(Context context, Class clazz, int transitionId, int requestCode) {
        startActivityTransitionForResult(new Intent(context, clazz), transitionId, requestCode);
    }

    public void startActivityTransitionForResult(Intent intent, int transitionId, int requestCode) {
        intent.putExtra(TRANSITION_TYPE, transitionId);
        startActivityForResult(intent, requestCode);

        switch (transitionId) {
            case TRANSITION_TV_OFF: {
                overridePendingTransition(R.anim.tv_off, R.anim.tv_off);
                break;
            }
            case TRANSITION_HEAD: {
                overridePendingTransition(R.anim.head_in, 0);
                break;
            }
            case TRANSITION_SLIDE_BOTTOM_IN: {
                overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
                break;
            }
            case TRANSITION_SLIDE_IN_BOTTOM: {
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                break;
            }
            case TRANSITION_SLIDE_IN_LEFT: {
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            }
            case TRANSITION_SLIDE_IN_RIGHT: {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
            case TRANSITION_SLIDE_IN_TOP: {
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
                break;
            }
            case TRANSITION_BACKOFF: {
                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                break;
            }
            case TRANSITION_FLIP_HORIZONTAL: {
                overridePendingTransition(R.anim.flip_horizontal_in, R.anim.flip_horizontal_out);
                break;
            }
            case TRANSITION_FLIP_VERTICAL: {
                overridePendingTransition(R.anim.flip_vertical_in, R.anim.flip_vertical_out);
                break;
            }
            case TRANSITION_FAST_FADE: {
                //overridePendingTransition(R.anim.fast_fade_in, R.anim.fade_out);
                overridePendingTransition(0, 0);
                break;
            }
            case TRANSITION_FADE: {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            }
            case TRANSITION_DISAPPEAR_TOP_LEFT: {
                overridePendingTransition(R.anim.disappear_top_left_in, R.anim.disappear_top_left_out);
                break;
            }
            case TRANSITION_APPEAR_TOP_LEFT: {
                overridePendingTransition(R.anim.appear_top_left_in, R.anim.appear_top_left_out);
                break;
            }
            case TRANSITION_DISAPPEAR_BOTTOM_RIGHT: {
                overridePendingTransition(R.anim.disappear_bottom_right_in, R.anim.disappear_bottom_right_out);
                break;
            }
            case TRANSITION_APPEAR_BOTTOM_RIGHT: {
                overridePendingTransition(R.anim.appear_bottom_right_in, R.anim.appear_bottom_right_out);
                break;
            }
            case TRANSITION_UNZOOM: {
                overridePendingTransition(R.anim.unzoom_in, R.anim.unzoom_out);
                break;
            }
            case TRANSITION_PULL_RIGHT_PUSH_LEFT: {
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
            }
            case TRANSITION_PULL_LEFT_PUSH_RIGHT: {
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            }
            case TRANSITION_NONE: {
                overridePendingTransition(0, 0);
                break;
            }
            default: {
                break;
            }
        }
    }

}
