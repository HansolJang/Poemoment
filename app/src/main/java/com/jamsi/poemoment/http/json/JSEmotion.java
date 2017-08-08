package com.jamsi.poemoment.http.json;

/**
 * Created by zipdoc on 2017. 5. 21..
 */

public class JSEmotion extends JSBased {

    protected String emotion_code;
    protected String emotion_name;
    protected Integer emotion_depth;
    protected Integer emotion_above;

    public JSEmotion(String emotion_code, String emotion_name, int emotion_depth, int emotion_above) {
        this.emotion_code = emotion_code;
        this.emotion_name = emotion_name;
        this.emotion_depth = emotion_depth;
        this.emotion_above = emotion_above;
    }

    public String getEmotion_code() {
        return emotion_code;
    }

    public String getEmotion_name() {
        return emotion_name;
    }

    public Integer getEmotion_depth() {
        return emotion_depth;
    }

    public Integer getEmotion_above() {
        return emotion_above;
    }

    public boolean isAbove() {
        return emotion_above != null && emotion_above == 1;
    }
}
