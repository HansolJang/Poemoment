package com.jamsi.poemoment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import com.jamsi.poemoment.http.json.JSEmotion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import common.libraries.Singleton;


/**
 * Created by zipdoc on 16. 3. 16..
 */
public class Storage extends Singleton {

    private static final String tag = Storage.class.getSimpleName();

    private Context context = null;
    private Handler handler = null;

    public Handler getHandler() {
        return handler;
    }
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    private List<JSEmotion> emotions = null;
    public void setEmotions(List<JSEmotion> emotions) {
        this.emotions = emotions;
    }
    public List<JSEmotion> getEmotions() {
        return this.emotions;
    }
}
