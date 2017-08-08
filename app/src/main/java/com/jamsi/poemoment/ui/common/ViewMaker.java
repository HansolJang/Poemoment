package com.jamsi.poemoment.ui.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jamsi.poemoment.R;
import com.jamsi.poemoment.TYPEDEF;
import com.jamsi.poemoment.http.json.JSEmotion;

import common.libraries.DisplayUtil;
import common.ui.common.FontUtil;

/**
 * Created by zipdoc on 2017. 7. 7..
 */

public class ViewMaker {

    public static View getPopWinRowView(Context context, JSEmotion emotion) {

        if(emotion == null) {
            return null;
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.include_emotion_row, null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(param);
        FontUtil.setGlobalFont(TYPEDEF.POMENT_MAIN_FONT, view);
        view.setTag(emotion);

        ((TextView) view.findViewById(R.id.tvTitle)).setText(emotion.getEmotion_name());
        String depth = null;
        if(emotion.getEmotion_depth() == 0) {
            depth = context.getString(R.string.water_surface);
        } else {
            depth = emotion.isAbove() ? context.getString(R.string.above) : context.getString(R.string.under);
            depth += " ";
            depth += String.format(context.getString(R.string.depth), emotion.getEmotion_depth());

            ((TextView) view.findViewById(R.id.tvDepth)).setTextColor(context.getResources().getColorStateList(
                    emotion.isAbove() ? R.color.selector_emotion_black : R.color.selector_emotion_white));
            ((TextView) view.findViewById(R.id.tvTitle)).setTextColor(context.getResources().getColorStateList(
                    emotion.isAbove() ? R.color.selector_emotion_desc_black : R.color.selector_emotion_desc_white));

        }
        ((TextView) view.findViewById(R.id.tvDepth)).setText(depth);

        return view;
    }
}
