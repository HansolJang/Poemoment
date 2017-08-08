package com.jamsi.poemoment.common;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.jamsi.poemoment.ui.LoadingDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import common.ui.common.FontUtil;

/**
 * Created by zipdoc on 2017. 4. 21..
 */

public abstract class PomentFragment extends Fragment {

    private Handler handler = null;
    private LoadingDialog loading = null;

    public PomentFragment() {

    }

    public abstract void onInitView(Object object);
    public abstract void onUpdateView(Object object);
    public abstract void onData(Object object);



    public void onAfterInflate(View v) {
        // TODO: 2017. 4. 21. set font
//        FontUtil.setGlobalFont();
        handler = new Handler();
        loading = new LoadingDialog(getActivity());
    }
    public void onScrollUp() {}

    public interface IFragmentAfterOnCreateListener {
        void onAfter(PomentFragment fragment);
    }
    protected IFragmentAfterOnCreateListener listener = null;
    public void setFragmentAfterOnCreateListener(IFragmentAfterOnCreateListener listener) {
        this.listener = listener;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        invokeFragmentManagerNoteStateNotSaved();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void invokeFragmentManagerNoteStateNotSaved() {
        /**
         * For post-Honeycomb devices
         */
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        try {
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!"Activity".equals(cls.getSimpleName()));
            Field fragmentMgrField = cls.getDeclaredField("mFragments");
            fragmentMgrField.setAccessible(true);

            Object fragmentMgr = fragmentMgrField.get(this);
            cls = fragmentMgr.getClass();

            Method noteStateNotSavedMethod = cls.getDeclaredMethod("noteStateNotSaved");
            noteStateNotSavedMethod.invoke(fragmentMgr);

        } catch (Exception ex) {

        }
    }

    public void showLoading() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                loading.show();
            }
        });
    }
    public void hideLoading() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (loading != null) {
                    loading.dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loading != null) {
            loading.dismiss();
            loading = null;
        }
    }
}
