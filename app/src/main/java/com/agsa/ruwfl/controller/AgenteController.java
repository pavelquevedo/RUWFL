package com.agsa.ruwfl.controller;

import android.content.Context;

import com.agsa.ruwfl.callback.SuccessArray;
import com.agsa.ruwfl.model.AgenteModel;
import com.agsa.ruwfl.seguridad.Token;
import com.agsa.ruwfl.service.ApiManager;
import com.agsa.ruwfl.service.JsonParser;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Evillatoro on 15/11/2016.
 */

public class AgenteController {

    public static void buscarAgente(String patron, final Context context, final SuccessArray callback) {
        ApiManager webApi = new ApiManager(context);
        webApi.getJSONArray("Agente/Consultar", "token=" + Token.getToken(context) + "&patron=" + patron, new ApiManager.SuccessJsonArrayRequest() {
            @Override
            public void OnSuccess(JSONArray response) {
                List<AgenteModel> agenteModelList = JsonParser.getParsedArray(response, AgenteModel.class);
                callback.onSuccess(agenteModelList);
            }

            @Override
            public void OnError(VolleyError error) {
                callback.onError(error.getMessage());
            }
        });
    }
}
