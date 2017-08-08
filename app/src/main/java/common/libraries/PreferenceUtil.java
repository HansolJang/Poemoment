package common.libraries;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bong on 16. 3. 3..
 */
public class PreferenceUtil {

    private static SharedPreferences object(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getPreferencesEditor(Context context) {
        return object(context).edit();
    }

    public static void set(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPreferencesEditor(context);
        editor.putString(key, value);
        editor.commit();
    }

    public static void set(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getPreferencesEditor(context);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void set(Context context, String key, long value) {
        SharedPreferences.Editor editor = getPreferencesEditor(context);
        editor.putLong(key, value);
        editor.commit();
    }

    public static void set(Context context, String key, int value) {
        SharedPreferences.Editor editor = getPreferencesEditor(context);
        editor.putInt(key, value);
        editor.commit();
    }

    public static void set(Context context, String key, float value) {
        SharedPreferences.Editor editor = getPreferencesEditor(context);
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void delete(Context context, String key) {
        SharedPreferences.Editor editor = getPreferencesEditor(context);
        editor.remove(key);
        editor.commit();
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = getPreferencesEditor(context);
        editor.clear();
        editor.commit();
    }

    public static String get(Context context, String key) {
        return getString(context, key);
    }

    public static String getString(Context context, String key) {
        return object(context).getString(key, "");
    }

    public static boolean getBoolean(Context context, String key) {
        return object(context).getBoolean(key, false);
    }

    public static long getLong(Context context, String key) {
        return object(context).getLong(key, 0);
    }

    public static int getInt(Context context, String key) {
        return object(context).getInt(key, 0);
    }

    public static float getFloat(Context context, String key) {
        return object(context).getFloat(key, 0.0f);
    }
}
