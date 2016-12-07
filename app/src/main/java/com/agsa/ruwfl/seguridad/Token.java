package com.agsa.ruwfl.seguridad;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Evillatoro on 14/11/2016.
 */

public class Token {
    private static final String RUWFL_PREFS = "AGSATOKEN";
    private static final String WSRUWFL_PREFS = "AGSAWSRUWFL";
    private static final String RUWFLADMIN_PREFS = "AGSAESADMIN";

    public static void setToken(Context context, String token) {
        SharedPreferences settings = context.getSharedPreferences(RUWFL_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(RUWFL_PREFS, Context.MODE_PRIVATE);
        return settings.getString("token", "");
    }

    public static void setURL(Context context, String url) {
        SharedPreferences settings = context.getSharedPreferences(WSRUWFL_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("URL", url);
        editor.commit();
    }

    public static String getURL(Context context) {
        SharedPreferences settings = context.getSharedPreferences(WSRUWFL_PREFS, Context.MODE_PRIVATE);
        return settings.getString("URL", "");
    }

    public static boolean getEsAdmin(Context context) {
        SharedPreferences settings = context.getSharedPreferences(RUWFLADMIN_PREFS, Context.MODE_PRIVATE);
        return Boolean.valueOf(settings.getString("ADMIN", ""));
    }

    public static void setEsAdmin(Context context, boolean esAdmin) {
        SharedPreferences settings = context.getSharedPreferences(RUWFLADMIN_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ADMIN",String.valueOf(esAdmin));
        editor.commit();
    }
}
