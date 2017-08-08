package com.jamsi.poemoment.http.base;

import retrofit2.Call;
import retrofit2.http.GET;
import com.jamsi.poemoment.http.json.JSEmotionList;

/**
 * Created by zipdoc on 2017. 5. 21..
 */

public abstract interface PomentReq {

    @GET("emotion")
    public abstract Call<JSEmotionList> getEmotionList();

}
