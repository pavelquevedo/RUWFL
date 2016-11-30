package com.agsa.ruwfl.callback;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Evillatoro on 14/11/2016.
 */

public interface SuccessJSONObject {
    void onSucces(JSONObject jsonObject);

    void onError(VolleyError error);
}
