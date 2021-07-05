package com.softwise.tracewizs.utils;

import android.content.Context;

import com.softwise.tracewizs.activitys.LoginActivity;
import com.softwise.tracewizs.helper.HelperPreference;

public class SPTRaceWizsConstants {
    public static final String SP_LOGIN_STATUS = "login_status";
    public static final String SP_USER_ROLE = "user_role";
    public static final String SP_USER_MOBILE = "user_mobile";
    public static final String SP_USER_NAME = "user_name";
    public static final String SP_USER_ID = "user_id";
    public static final String SP_TOKEN = "token";
    public static final String SP_GATE_ENTRY_ID = "gate_entry_id";



    public static void saveLoginStatus(Context context, Boolean status) {
        HelperPreference.saveBoolean(context, SP_LOGIN_STATUS, status);
    }

    public static boolean getLoginStatus(Context context) {
        return HelperPreference.getBoolean(context, SP_LOGIN_STATUS);
    }

    public static void saveUserName(Context context, String userName) {
        HelperPreference.saveString(context, SP_USER_NAME, userName);
    }
    public static String getUserName(Context context) {
        return HelperPreference.getString(context, SP_USER_NAME);
    }
    public static void saveUserId(Context context,String userId) {
        HelperPreference.saveString(context,SP_USER_ID, userId);
    }
    public static String getUserId(Context context) {
        return HelperPreference.getString(context, SP_USER_ID);
    }
    public static void saveToken(Context context,String token) {
        HelperPreference.saveString(context,SP_TOKEN, token);
    }
    public static String getToken(Context context) {
        return HelperPreference.getString(context, SP_TOKEN);
    }
    public static void saveGateEntryId(Context context,String gateEntryId) {
        HelperPreference.saveString(context,SP_GATE_ENTRY_ID, gateEntryId);
    }
    public static String getGateEntryId(Context context) {
        return HelperPreference.getString(context, SP_GATE_ENTRY_ID);
    }
    public static void saveUserRole(Context context,String role) {
        HelperPreference.saveString(context,SP_USER_ROLE, role);
    }
    public static String getUserRole(Context context) {
        return HelperPreference.getString(context, SP_USER_ROLE);
    }
    public static void saveUserMobile(Context context,String mobile) {
        HelperPreference.saveString(context,SP_USER_MOBILE, mobile);
    }
    public static String getUserMobile(Context context) {
        return HelperPreference.getString(context, SP_USER_MOBILE);
    }
}
