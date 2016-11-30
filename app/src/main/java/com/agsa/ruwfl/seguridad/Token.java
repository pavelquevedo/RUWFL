package com.agsa.ruwfl.seguridad;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Evillatoro on 14/11/2016.
 */

public class Token {
    private static final String RUWFL_PREFS = "AGSATOKEN";

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
}
