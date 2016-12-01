package com.agsa.ruwfl.controller;

import android.content.Context;

import com.agsa.ruwfl.callback.SuccessObject;
import com.agsa.ruwfl.seguridad.Token;
import com.agsa.ruwfl.service.ApiManager;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Evillatoro on 14/11/2016.
 */

public class AuthController {

    public static void Login(String usuario, String password, final Context context, final SuccessObject callback) {
        ApiManager webApi = new ApiManager(context);
        webApi.getJSONObject("Auth/Usuario", "strUsuario=" + usuario + "&strPassword=" + password, new ApiManager.SuccessJsonObjectRequest() {
            @Override
            public void OnSuccess(JSONObject response) {
                try {
                    Token.setToken(context, response.getString("token"));
                    callback.onSuccess(response.getString("token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(VolleyError error) {
                callback.onError(error.getMessage());
            }
        });

    }
}
