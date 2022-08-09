package technologies.akkas.ageguess.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class Preferences {

    public static final String APP_PREF = "AgeGuessingPreferences";
    public static SharedPreferences sp;
    public static String KEY_USER_ID = "key_user_id";
    public static String KEY_SHOW_ADS = "key_show_ads";

    public static void save(Context context, String key, String value) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String get(Context context , String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        String userId = sp.getString(key, "0");
        return userId;
    }

    public static String getString(Context context , String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        String userId = sp.getString(key, "");
        return userId;
    }

    public static void saveInt(Context context, String key, int value) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int getInt(Context context , String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        int userId = sp.getInt(key,0);
        return userId;
    }


    public static void saveBool(Context context, String key, Boolean value) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static Boolean getBool(Context context , String key) {
        sp = context.getSharedPreferences(APP_PREF, 0);
        return sp.getBoolean(key,false);
    }

    public static void clearPreference(Context context) {
        sp = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
    }


}
