package com.agsa.ruwfl.controller;

import android.content.Context;

import com.agsa.ruwfl.callback.SuccessArray;
import com.agsa.ruwfl.model.PolizaModel;
import com.agsa.ruwfl.seguridad.Token;
import com.agsa.ruwfl.service.ApiManager;
import com.agsa.ruwfl.service.JsonParser;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Evillatoro on 25/11/2016.
 */

public class PolizaController {

    public static void buscarPolizasPendientes(String agente, final Context context, final SuccessArray callback) {
        ApiManager webApi = new ApiManager(context);
        webApi.getJSONArray("Poliza/PolizasPendientes", "token=" + Token.getToken(context) + "&agente=" + agente, new ApiManager.SuccessJsonArrayRequest() {
            @Override
            public void OnSuccess(JSONArray response) {
                List<PolizaModel> agenteModelList = JsonParser.getParsedArray(response, PolizaModel.class);
                callback.onSuccess(agenteModelList);
            }

            @Override
            public void OnError(VolleyError error) {
                callback.onError(error.getMessage());
            }
        });
    }
}
