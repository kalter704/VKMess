package com.example.vasiliy.vkmess.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by vasiliy on 01.01.16.
 *
 * Singleton
 *
 */
public class UserToken {

    private static final String ACCESS_TOKKEN = "vkmess_access_token";
    private static final String EXPIRIS_IN = "vkmess_expires_in";
    private static final String USER_ID = "vkmess_user_id";
    private static final String CREATED = "vkmess_created";

    private UserToken() {}

    private static class UserTokenHolder {
        private final static UserToken userToken = new UserToken();
    }

    public static UserToken getInstance() {
        return UserTokenHolder.userToken;
    }

    public String accessToken = null;
    public int expiresIn = 0;
    public String userId = null;
    public long created = 0;
    public boolean isReady = false;

    public void saveUserTokenFromFile(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(ACCESS_TOKKEN, accessToken);
        edit.putInt(EXPIRIS_IN, expiresIn);
        edit.putString(USER_ID, userId);
        edit.putLong(CREATED, created);
        edit.commit();
    }

    public void loadUserTokenFile(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        accessToken = prefs.getString(ACCESS_TOKKEN, null);
        expiresIn = prefs.getInt(EXPIRIS_IN, 0);
        userId = prefs.getString(USER_ID, null);
        created = prefs.getLong(CREATED, 0);
        if(accessToken != null && expiresIn != 0 && userId != null && created != 0) {
            isReady = true;
        } else {
            isReady = false;
        }
    }

    public boolean isExpired() {
        return expiresIn > 0 && expiresIn * 1000 + created + 600000 < System.currentTimeMillis();
    }

}
