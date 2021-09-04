package com.baro.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class GetBool {

    public static boolean getBool(String toString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(toString);
            return jsonObject.getBoolean("result");
        } catch (JSONException e) {
            return false;
        }
    }

    public static String getMessage(String toString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(toString);
            return jsonObject.getString("message");
        } catch (JSONException e) {
            return "";
        }
    }
}
