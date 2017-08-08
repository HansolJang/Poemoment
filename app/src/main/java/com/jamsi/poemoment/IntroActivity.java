package com.jamsi.poemoment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.jamsi.poemoment.common.PomentActivity;
import com.jamsi.poemoment.fcm.FPushManager;
import com.jamsi.poemoment.http.base.PomentReq;
import com.jamsi.poemoment.http.base.PomentSender;
import com.jamsi.poemoment.http.json.JSEmotionList;

import common.Network_;
import common.libraries.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zipdoc on 2017. 5. 7..
 */

public class IntroActivity extends PomentActivity {

    private static final String tag = IntroActivity.class.getSimpleName();

    private Handler handler = null;
    private boolean backKeyPressed = false;

    // TODO: 2017. 5. 7. 스플래시 만들고 fpushmanager init해서 토큰 받는지 확인하기!!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        registerBroadcastReceiver();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!Network_.isNetworkConnected(IntroActivity.this)) {
                            Toast.show("연결된 네트워크가 없습니다");
                            return;
                        }
                        startPoment();
                    }
                }, 1800);
            }
        }, 100);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backKeyPressed = true;
    }

    private void startPoment() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if(backKeyPressed) {
                    finish();
                } else {

                    DeviceUuidUtil.init(IntroActivity.this);
                    Log.d(tag, "uuid : " + DeviceUuidUtil.getDeviceUuid().toString());

                    if(FPushManager.init(IntroActivity.this)) {
                        FPushManager.update(IntroActivity.this);
                    }
//
                    Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                    finish_fade(intent);

//                    doEmotionList();

                }
            }
        });

    }

    private void doEmotionList() {
        PomentReq sender = PomentSender.getSender(IntroActivity.this, R.string.server_url);
        sender.getEmotionList().enqueue(new Callback<JSEmotionList>() {
            @Override
            public void onResponse(Call<JSEmotionList> call, Response<JSEmotionList> response) {
                if(response.isSuccessful()) {
                    Log.d(tag, response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<JSEmotionList> call, Throwable t) {
                Log.d(tag, t.getMessage());
            }
        });
    }
}
