package com.agsa.ruwfl.service;

import android.content.Context;

import com.agsa.ruwfl.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Evillatoro on 14/11/2016.
 */

public class ApiManager {
    public static final int SOCKET_REQUEST_TIMEOUT = 3000000;
    private String urlWS = null;
    private Context context;

    public String getUrlWS() {
        return urlWS;
    }

    public ApiManager(Context context) {
        this.context = context;
        urlWS = this.context.getResources().getString(R.string.urlWSRUWFL);
    }

    public VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null && volleyError.networkResponse.statusCode == 409) {
            VolleyError error = null;
            try {
                JSONObject object = new JSONObject(new String(volleyError.networkResponse.data));
                error = new VolleyError(object.getString("Message"));
            } catch (JSONException e) {
                e.printStackTrace();
                error = new VolleyError("Error al obtener mensaje de respuesta del servidor");
            }
            volleyError = error;
        }
        return volleyError;
    }

    public String getFormatedParameters(String queryParemters) {
        String newQueryParameter = "";
        if (queryParemters.contains("&")) {
            String[] params = queryParemters.split("&");
            for (int i = 0; i < params.length; i++) {
                try {
                    newQueryParameter = newQueryParameter + params[i].substring(0, params[i].indexOf("=", 0) + 1) +
                            URLEncoder.encode(params[i].substring(params[i].indexOf("=", 0) + 1), "UTF-8") + "&";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            newQueryParameter = newQueryParameter.substring(0, newQueryParameter.length() - 1);
        } else {
            try {
                newQueryParameter = newQueryParameter + queryParemters.substring(0, queryParemters.indexOf("=", 0) + 1) +
                        URLEncoder.encode(queryParemters.substring(queryParemters.indexOf("=", 0) + 1), "UTF-8") + "&";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            newQueryParameter = newQueryParameter.substring(0, newQueryParameter.length() - 1);
        }
        return newQueryParameter;
    }

    public void getJSONArray(String url, String content, final SuccessJsonArrayRequest listener) {
        String urlCompleta = null;
        urlCompleta = urlWS + url + "?" + getFormatedParameters(content);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlCompleta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listener.OnSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.OnError(parseNetworkError(error));
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(SOCKET_REQUEST_TIMEOUT,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueueSingleton.getInstance(this.context).addToRequestQueue(request);
    }

    public void getJSONObject(String url, String content, final SuccessJsonObjectRequest listener) {
        String urlCompleta = urlWS + url + "?" + getFormatedParameters(content);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlCompleta, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.OnSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.OnError(parseNetworkError(error));
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(SOCKET_REQUEST_TIMEOUT,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueueSingleton.getInstance(this.context).addToRequestQueue(request);
    }


    public interface SuccessJsonArrayRequest {
        void OnSuccess(JSONArray response);

        void OnError(VolleyError error);
    }

    public interface SuccessJsonObjectRequest {
        void OnSuccess(JSONObject response);

        void OnError(VolleyError error);
    }
}
