package com.agsa.ruwfl.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evillatoro on 14/11/2016.
 */

public class JsonParser {
    public static JSONObject parseObject(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(objectMapper.writeValueAsString(object));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static <T> T getParsedObject(JSONObject jsonObject, Class<T> type) {
        T object = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            object = objectMapper.readValue(jsonObject.toString(), type);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return object;
    }

    public static <T> List<T> getParsedArray(JSONArray jsonArray, Class<T> type) {
        List<T> objectArrayList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                objectArrayList.add(objectMapper.readValue(jsonArray.get(i).toString(), type));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objectArrayList;
    }
}
