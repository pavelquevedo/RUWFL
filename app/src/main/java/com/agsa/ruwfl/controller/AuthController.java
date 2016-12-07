package com.agsa.ruwfl.controller;

import android.content.Context;

import com.agsa.ruwfl.callback.SuccessArray;
import com.agsa.ruwfl.callback.SuccessObject;
import com.agsa.ruwfl.model.ClsConexiones;
import com.agsa.ruwfl.seguridad.Token;
import com.agsa.ruwfl.service.ApiManager;
import com.agsa.ruwfl.service.JsonParser;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    public static void obtenerConexions(final Context context, final SuccessArray callback) {
        ApiManager webApi = new ApiManager(context);
        webApi.getJSONArray("Auth/ObtenerConexiones", "token=" + Token.getToken(context), new ApiManager.SuccessJsonArrayRequest() {
            @Override
            public void OnSuccess(JSONArray response) {
                List<ClsConexiones> conexionesModelList = JsonParser.getParsedArray(response, ClsConexiones.class);
                callback.onSuccess(conexionesModelList);
            }

            @Override
            public void OnError(VolleyError error) {
                callback.onError(error.getMessage());
            }
        });
    }

    public static void ObtenerToken(String codConexion, final Context context, final SuccessObject callback) {
        ApiManager webApi = new ApiManager(context);
        webApi.getJSONObject("Auth/GenerarToken", "token=" + Token.getToken(context) + "&codConexion=" + codConexion, new ApiManager.SuccessJsonObjectRequest() {
            @Override
            public void OnSuccess(JSONObject response) {
                try {
                    Token.setToken(context, response.getString("token"));
                    Token.setEsAdmin(context, Boolean.valueOf(response.getString("isAdmin")));
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
