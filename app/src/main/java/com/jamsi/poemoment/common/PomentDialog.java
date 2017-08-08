package com.jamsi.poemoment.common;

import android.content.Context;
import android.os.Bundle;

import com.jamsi.poemoment.ui.LoadingDialog;

import common.ui.Dialog_;

/**
 * Created by zipdoc on 2017. 4. 21..
 */

public class PomentDialog extends Dialog_ {

    private static final String tag = PomentDialog.class.getSimpleName();

    private LoadingDialog loading = null;

    public PomentDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setWindowAnimations(Dialog_.TRANSITION_FADE);
        super.onCreate(savedInstanceState);
        initLoading();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        // TODO: 2017. 4. 21.  set font
//        FontUtil.setGlobalFont();
    }

    private void initLoading() {
        loading = new LoadingDialog(context);
    }
    public void showLoading() {
        if (loading == null) {
            loading = new LoadingDialog(context);
        }
        loading.show();
    }
    public void hideLoading() {
        if (loading != null) {
            loading.dismiss();
        }
    }
}
