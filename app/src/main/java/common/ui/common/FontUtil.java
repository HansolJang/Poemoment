package common.ui.common;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zipdoc on 2017. 4. 21..
 */

public class FontUtil {

    public static void setGlobalFont(Typeface typeface, View view) {

        if (view != null) {
            if(view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup)view;
                int vgCnt = vg.getChildCount();
                for(int i=0; i < vgCnt; i++){
                    View v = vg.getChildAt(i);
                    if(v instanceof TextView){
                        ((TextView) v).setTypeface(typeface);
                    }
                    setGlobalFont(typeface, v);
                }
            }
            else if (view instanceof TextView) {
                ((TextView)view).setTypeface(typeface);
            }
            else if (view instanceof EditText) {
                ((EditText)view).setTypeface(typeface);
            }
            else if(view instanceof Button) {
                ((Button)view).setTypeface(typeface);
            }
        }
    }
}
