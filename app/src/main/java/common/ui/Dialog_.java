package common.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.jamsi.poemoment.R;


/**
 * Created by zipdoc on 16. 3. 24..
 */
public class Dialog_ extends Dialog {

    private int windowAnimations = 0;

    public static final int TRANSITION_ZOOM = 1;
    public static final int TRANSITION_LEFT = 2;
    public static final int TRANSITION_TOP = 3;
    public static final int TRANSITION_BOTTOM = 4;
    public static final int TRANSITION_FLIP = 5;
    public static final int TRANSITION_FADE = 6;
    public static final int TRANSITION_BOUNCE = 7;
    public static final int TRANSITION_NONE = 100;

    protected final Context context;
    public Dialog_(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        if (windowAnimations != 0) {
            getWindow().getAttributes().windowAnimations = windowAnimations;
        }
    }

    public void setWindowAnimations(int windowAnimations) {
        switch (windowAnimations) {
            case TRANSITION_ZOOM: {
                this.windowAnimations = R.style.dialog_animation_zoom;
                break;
            }
            case TRANSITION_LEFT: {
                this.windowAnimations = R.style.dialog_animation_left;
                break;
            }
            case TRANSITION_BOTTOM: {
                this.windowAnimations = R.style.dialog_animation_bottom;
                break;
            }
            case TRANSITION_TOP: {
                this.windowAnimations = R.style.dialog_animation_top;
                break;
            }
            case TRANSITION_FLIP: {
                this.windowAnimations = R.style.dialog_animation_flip;
                break;
            }
            case TRANSITION_FADE: {
                this.windowAnimations = R.style.dialog_animation_fade;
                break;
            }
            case TRANSITION_BOUNCE: {
                this.windowAnimations = R.style.dialog_animation_bounce;
                break;
            }
            default: {
                break;
            }
        }

        //this.windowAnimations = windowAnimations;
    }

}
