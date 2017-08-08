package com.jamsi.poemoment;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import common.libraries.PreferenceUtil;

/**
 * Created by zipdoc on 2017. 5. 14..
 */

public class DeviceUuidUtil {

    private static final String tag = DeviceUuidUtil.class.getSimpleName();

    protected volatile static UUID uuid;

    public static void init(Context context) {
        if(uuid == null) {
            synchronized (DeviceUuidUtil.class) {
                if(uuid == null) {
                    String id = PreferenceUtil.get(context, TYPEDEF.POMENT_UUID);
                    if(!TextUtils.isEmpty(id)) {
                        uuid = UUID.fromString(id);
                    } else {
                        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

                        try {
                            //몇몇 디바이스에서 동일하게 추출되는 androidId
                            if(!"9774d56d682e549c".equals(androidId)) {
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {
                                final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                            }
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    PreferenceUtil.set(context, TYPEDEF.POMENT_UUID, uuid.toString());
                }
            }
        }
    }

    public static UUID getDeviceUuid() {
        return uuid;
    }
}
