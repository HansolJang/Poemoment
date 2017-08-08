package com.jamsi.poemoment.db;

import kr.co.zipdoc.zddb.db.TABLE;

/**
 * Created by zipdoc on 2017. 5. 14..
 */

public class TBL_PROPERTIES extends TABLE<TBL_PROPERTIES> {

    @TABLE._Attribute(_field = false)
    private static final String tag = TBL_PROPERTIES.class.getSimpleName();

    @_Attribute(_field = false)
    public static final String IGNORE_FIELD = null;

    @_Attribute(_option = "PRIMARY KEY AUTOINCREMENT", _key = false)
    public int _id = 0;

    @_Attribute(_option = "UNIQUE ON CONFLICT REPLACE", _key = true)
    public String key = null;

    @_Attribute(_null = false)
    public String value = null;

    public TBL_PROPERTIES(Boolean arg0) {
        super(DB_POMENT.__INSTANCE__(), arg0);
        // TODO Auto-generated constructor stub
    }
}
