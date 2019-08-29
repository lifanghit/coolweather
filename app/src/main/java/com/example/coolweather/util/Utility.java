package com.example.coolweather.util;

// 服务器上的GSON数据：http://guolin.tech/api/china/

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    /*
    **  解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response) throws JSONException {
        if (!TextUtils.isEmpty(response)) {
            JSONArray allProvince = new JSONArray(response);
            for (int i = 0 ; i < allProvince.length() ; i++) {
                JSONObject provinceObject = allProvince.getJSONObject(i);

            }

        }
        return false;
    }

    /*
     **  解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response) throws JSONException {
        if (!TextUtils.isEmpty(response)) {
            JSONArray allCity = new JSONArray(response);
            for (int i = 0 ; i < allCity.length() ; i++) {
                JSONObject provinceObject = allCity.getJSONObject(i);

            }

        }
        return false;
    }

    /*
     **  解析和处理服务器返回的县级数据
     */
    public static boolean handleCountryResponse(String response) throws JSONException {
        if (!TextUtils.isEmpty(response)) {
            JSONArray allCountry = new JSONArray(response);
            for (int i = 0 ; i < allCountry.length() ; i++) {
                JSONObject provinceObject = allCountry.getJSONObject(i);

            }

        }
        return false;
    }
}
