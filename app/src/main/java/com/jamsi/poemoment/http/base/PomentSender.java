package com.jamsi.poemoment.http.base;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zipdoc on 2017. 5. 21..
 */

public class PomentSender {

    public static PomentReq getSender(Context context, int server_id) {
        return new Retrofit.Builder()
                .baseUrl(context.getString(server_id))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PomentReq.class);
    }
}
