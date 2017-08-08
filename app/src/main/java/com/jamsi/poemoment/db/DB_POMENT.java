package com.jamsi.poemoment.db;

import android.content.Context;

import kr.co.zipdoc.zddb.db.DATABASE;

/**
 * Created by zipdoc on 2017. 5. 14..
 */

public class DB_POMENT extends DATABASE {

    private static final String DATABASE_NAME = "poemoment.db";
    private static final int DATABASE_VERSION = 1;

    private static DB_POMENT _this;

    public DB_POMENT(Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
        _this = this;
    }

    public static DB_POMENT __INSTANCE__() {
        return _this;
    }
}
