package com.agsa.ruwfl.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.agsa.ruwfl.callback.SuccessObject;
import com.agsa.ruwfl.model.FirmaModel;
import com.agsa.ruwfl.model.PolizaModel;
import com.agsa.ruwfl.service.ApiManager;
import com.agsa.ruwfl.service.RequestQueueSingleton;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Evillatoro on 22/11/2016.
 */

public class RecieveController {

    public static void ProcesarFirma(FirmaModel model, List<PolizaModel> polizas, final Context context, final SuccessObject callback) {
        final ApiManager webApi = new ApiManager(context);
        String url = webApi.getUrlWS() + "Recibido/ProcesarFirma";
        try {
            ObjectMapper mapper = new ObjectMapper();
            final JSONObject newContract = new JSONObject(mapper.writeValueAsString(model));
            final JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < polizas.size(); i++) {
                jsonArray.put(new JSONObject(mapper.writeValueAsString(polizas.get(i))));
            }
            newContract.accumulate("polizas", jsonArray);
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                callback.onSuccess(response.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            callback.onError(webApi.parseNetworkError(error));
                        }
                    }) {
                @Override
                public byte[] getBody() {
                    try {
                        return newContract == null ? null : newContract.toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", newContract, "utf-8");
                        return null;
                    }
                }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(webApi.SOCKET_REQUEST_TIMEOUT,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueueSingleton.getInstance(context).addToRequestQueue(postRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return encodedImage;
    }

    public static byte[] getBytesImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
