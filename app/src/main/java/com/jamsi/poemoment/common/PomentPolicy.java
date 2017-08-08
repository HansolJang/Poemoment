package com.jamsi.poemoment.common;

import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by zipdoc on 2017. 5. 14..
 */

public class PomentPolicy {

    public static Spanned getBoldSpanned(String str) {
        return Html.fromHtml("<b>" + str + "</b>");
    }

    public static int getAlphaColor(int color, float factor) {
        if(factor > 1) {
            factor = 1f;
        }
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
