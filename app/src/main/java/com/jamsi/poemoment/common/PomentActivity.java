package com.jamsi.poemoment.common;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jamsi.poemoment.R;
import com.jamsi.poemoment.TYPEDEF;
import com.jamsi.poemoment.ui.LoadingDialog;

import common.Activity_;
import common.ui.common.FontUtil;

/**
 * Created by zipdoc on 2017. 4. 21..
 */

public class PomentActivity extends Activity_ {

    private static final String tag = PomentActivity.class.getSimpleName();

    private LoadingDialog loading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = new LoadingDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loading != null) {
            loading.dismiss();
            loading = null;
        }
    }

    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading != null) {
                    loading.show();
                }
            }
        });
    }
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading != null) {
                    loading.dismiss();
                }
            }
        });
    }

    private Typeface typeface = null;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (typeface == null) {
            typeface = TYPEDEF.POMENT_MAIN_FONT;
        }
        FontUtil.setGlobalFont(typeface, getWindow().getDecorView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // TODO: 2017. 5. 7. firebase 이벤트로 바꿀것 
//        if (gtracker != null) {
//            gtracker.start();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (gtracker != null) {
//            gtracker.stop();
//        }
    }

    protected void initGoogleTracker(int res_id) {
//        gtracker = new GoogleTracker(this, getString(R.string.property_id), R.xml.global_tracker, R.xml.ecommerse_tracker, res_id);

    }
}
