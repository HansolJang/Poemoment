package com.jamsi.poemoment.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.jamsi.poemoment.ui.LoadingDialog;


/**
 * Created by zipdoc on 2017. 4. 21..
 */

public abstract class PomentView extends LinearLayout {

    protected Context context = null;

    private LoadingDialog loading = null;

    public PomentView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        initLoading();
        onInitView();
    }

    public PomentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initLoading();
        onInitView();
    }

    public PomentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initLoading();
        onInitView();
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

    public abstract void onReleaseView();
    protected abstract void onInitView();
    public abstract void onUpdateView(Object object);


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (loading != null) {
            loading.dismiss();
            loading = null;
        }
    }

    protected void setGlobalFont(View view) {
//        FontUtil.setGlobalFont(TYPEDEF.ZIPDOC_MAIN_FONT, view);
    }
}
