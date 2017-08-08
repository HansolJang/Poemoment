package common.ui.draglayout;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by zipdoc on 2017. 5. 29..
 */

public class DragVerticalLayout extends LinearLayout {

    private static final String tag = DragVerticalLayout.class.getSimpleName();

    public interface IDragListener {
        void onDrag(int top);
        void onIdle();
    }

    protected ViewDragHelper dragHelper = null;
    protected DragHelperCallback dragCallback = null;
    protected View dragView = null;
    protected IDragListener listener = null;

    public DragVerticalLayout(Context context) {
        super(context);
        init();
    }

    public DragVerticalLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragVerticalLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setDragView(View dragView) {
        this.dragView = dragView;
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, 1.0f, dragCallback = new DragHelperCallback());
    }

    public void setOnIDragListener(IDragListener listener) {
        this.listener = listener;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            dragHelper.cancel();
            return false;
        }
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    protected class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - dragView.getHeight();

            final int newTop = Math.min(Math.max(top, topBound), bottomBound);

            //dy: 이전 top과 현재top의 차이
//            Log.d(tag, "top: " + top + " dy : " + dy + " newtop : " + newTop);

            if(listener != null) {
                listener.onDrag(newTop);
            }

            return newTop;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);

            if(state == ViewDragHelper.STATE_IDLE) {
                if(listener != null) {
                    listener.onIdle();
                }
            }
        }
    }
}
