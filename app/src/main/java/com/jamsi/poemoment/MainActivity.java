package com.jamsi.poemoment;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jamsi.poemoment.common.PomentActivity;
import com.jamsi.poemoment.http.json.JSEmotion;
import com.jamsi.poemoment.ui.common.ViewMaker;

import java.util.ArrayList;
import java.util.List;

import common.libraries.DisplayUtil;
import common.ui.draglayout.DragVerticalLayout;

public class MainActivity extends PomentActivity {

    private static final String tag = MainActivity.class.getSimpleName();

    private static final int WAVE_HEIGHT = 80; //dp
    private static final int BOTTLE_HEIGHT = 100; //px

    private DrawerLayout drawerContainer = null;
    private FrameLayout drawer = null;

    private Handler handler = null;
    private DragVerticalLayout dlContainer = null;
    private ImageView ivBottle = null;
    private ImageView ivWave = null;
    private ImageView ivBackgrnd = null;
    private Button btGnb = null;

    private LinearLayout llEmotionContainer = null;
    private List<JSEmotion> emotions = null;
    private List<Integer> tops = null;

    private int displayHeight = 0;
    private int bottomHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBackPressedToast(R.string.toast_backpressed);

        handler = new Handler();

        displayHeight = DisplayUtil.getDisplayHeight(this);

        drawerContainer = (DrawerLayout) findViewById(R.id.drawerContainer);
        drawer = (FrameLayout) findViewById(R.id.drawer);

        ivBottle = (ImageView) findViewById(R.id.ivBottle);
        ivWave = (ImageView) findViewById(R.id.ivWave);
        ivBackgrnd = (ImageView) findViewById(R.id.ivBackgrnd);
        btGnb = (Button) findViewById(R.id.btGnb);
        btGnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerContainer.openDrawer(drawer);
            }
        });

        ((TextView) findViewById(R.id.tvTitle)).setText("지금, 당신의 감정은 어디에 있나요?");

        dlContainer = (DragVerticalLayout) findViewById(R.id.dlContainer);
        dlContainer.setDragView(ivBottle);
        dlContainer.setOnIDragListener(dragListener);

        setEmotions();
    }

    @Override
    public void onBackPressed() {
        if (drawerContainer.isDrawerOpen(GravityCompat.START)) {
            drawerContainer.closeDrawer(drawer);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        int c = llEmotionContainer.getChildCount();
        int rowHeight = llEmotionContainer.getChildAt(0).getHeight();
        tops = new ArrayList<>();
        for (int i = 0; i < c; i++) {
            //top margin = 100, row hight = 70(top)
            tops.add((rowHeight * i) + DisplayUtil.DPToPixel(MainActivity.this, 100));
        }

        bottomHeight = Math.max(0, displayHeight - tops.get(c - 1) - DisplayUtil.DPToPixel(MainActivity.this, 70 + WAVE_HEIGHT));
        RelativeLayout.LayoutParams tparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, bottomHeight);
        tparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        ivBottom.setLayoutParams(tparams);

        DrawerLayout.LayoutParams dparams = new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dparams.setMargins(0, DisplayUtil.DPToPixel(MainActivity.this, 80), 0, bottomHeight);
        dlContainer.setLayoutParams(dparams);

        //수면으로 초기화
        // TODO: 2017. 7. 12. 초기화할 감정과 위에 띄울 문구를 서버에서 받기
        if (hasFocus) {

            c = emotions.size();
            for (int i = 0; i < c; i++) {
                if (emotions.get(i).getEmotion_depth() == 0) {

                    updateView(i);
                    ivBottle.animate().rotation(15f).withLayer();
                    break;
                }
            }
        }
    }

    private DragVerticalLayout.IDragListener dragListener = new DragVerticalLayout.IDragListener() {

        @Override
        public void onDrag(int top) {

            updateWaveViewColor(displayHeight - top);

            setImageViewLayoutParams(ivWave, 0, displayHeight - (top + DisplayUtil.DPToPixel(MainActivity.this, WAVE_HEIGHT)), 0, 0);
            setImageViewLayoutParams(ivBackgrnd, 0, displayHeight - top, 0, 0);
        }

        private int dest = 0;

        @Override
        public void onIdle() {

            int top = ivBottle.getTop();
            int minIndex = 0;
            int minDiff = Integer.MAX_VALUE;
            int middle = top + BOTTLE_HEIGHT / 2;
            int waveMiddle = 0;

            for (int i = 0; i < tops.size(); i++) {
                waveMiddle = tops.get(i);
                if (Math.abs(waveMiddle - middle) < Math.abs(minDiff)) {
                    minDiff = waveMiddle - middle;
                    minIndex = i;
                }
            }

            updateView(minIndex);
        }

        private Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                Log.d(tag, "dest : " + dest);
                setImageViewLayoutParams(ivBottle, 0, dest, 0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    };

    private void setImageViewLayoutParams(ImageView view, int left, int top, int right, int bottom) {
        if (view.equals(ivBackgrnd)) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(left, top, right, bottom);
            view.setLayoutParams(params);
        } else if (view.equals(ivWave)) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(left, Math.min(top, displayHeight - DisplayUtil.DPToPixel(MainActivity.this, 100)), right, bottom); //min height : 100dp
            view.setLayoutParams(params);
        } else if (view.equals(ivBottle)) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(left, top > 0 ? top - DisplayUtil.DPToPixel(MainActivity.this, 80) : top, right, bottom);
            view.setLayoutParams(params);
        }
    }

    private void updateView(int index) {

        //set bottle's position
        int top = tops.get(index) + BOTTLE_HEIGHT + DisplayUtil.DPToPixel(MainActivity.this, 10);
        setImageViewLayoutParams(ivBottle, 0, top, 0, 0);

        //set wave's position
        int waveDestTop = tops.get(tops.size() - index - 1) + DisplayUtil.DPToPixel(MainActivity.this, WAVE_HEIGHT) - DisplayUtil.DPToPixel(MainActivity.this, 10);
        setImageViewLayoutParams(ivBackgrnd, 0, waveDestTop + DisplayUtil.DPToPixel(MainActivity.this, WAVE_HEIGHT), 0, 0);
        setImageViewLayoutParams(ivWave, 0, waveDestTop, 0, 0);

        //set color
        updateBottleColor(top);
        updateWaveViewColor(waveDestTop);

        //set emotion
        setEmotionSelected(index);
    }

    private void updateWaveViewColor(int top) {

        float rate = (float) top / (float) displayHeight;

        int greyScale = Math.min(255, (int) (255 * (rate - 0.1f)));
        greyScale = Math.max(0, greyScale);

        ivBackgrnd.setBackgroundColor(Color.argb(255, greyScale, greyScale, greyScale));
//        ivBottom.setBackgroundColor(Color.argb(255, greyScale, greyScale, greyScale));
        ivWave.setColorFilter(Color.argb(255, greyScale, greyScale, greyScale), PorterDuff.Mode.SRC_IN);
    }

    private void updateBottleColor(int top) {

        float rate = (float) top / (float) displayHeight;

        int greyScale = Math.min(255, (int) (255 * (rate + 0.1f)));
        greyScale = Math.max(0, greyScale);

        ivBottle.setColorFilter(Color.argb(255, greyScale, greyScale, greyScale), PorterDuff.Mode.SRC_IN);
    }

    private void setEmotionSelected(int index) {
        int c = llEmotionContainer.getChildCount();
        for (int i = 0; i < c; i++) {
            llEmotionContainer.getChildAt(i).setSelected(index == i);
        }
    }

    public void setEmotions() {

        try {
            emotions = Storage.getInstance(Storage.class).getEmotions();

            if (emotions == null || emotions.size() == 0) {

                emotions = new ArrayList<>();
                emotions.add(new JSEmotion("1001", "눈부신", 50, 1));
                emotions.add(new JSEmotion("1002", "청량한", 20, 1));
                emotions.add(new JSEmotion("1003", "살랑이는", 5, 1));
                emotions.add(new JSEmotion("1004", "잔잔한", 0, 1));
                emotions.add(new JSEmotion("1005", "어스름한", 5, 0));
                emotions.add(new JSEmotion("1006", "캄캄한", 20, 0));
                emotions.add(new JSEmotion("1007", "숨막히는", 50, 0));

//                return;
            }

            llEmotionContainer = (LinearLayout) findViewById(R.id.llEmotionContainer);
            for (JSEmotion emotion : emotions) {
                View view = ViewMaker.getPopWinRowView(MainActivity.this, emotion);
                llEmotionContainer.addView(view);
            }


        } catch (Exception e) {

        }
    }
}
