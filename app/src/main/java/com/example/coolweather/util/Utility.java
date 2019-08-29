package com.example.coolweather.util;

// 服务器上的GSON数据：http://guolin.tech/api/china/

import android.text.TextUtils;

import com.example.coolweather.db.City;
import com.example.coolweather.db.Country;
import com.example.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    // 服务器地址：和风天气
    // 数据接口： http://guolin.tech/api/china

    /*
    **  解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                /*
                **  将服务器返回的数据转换为JSONArray的方式，服务器数据格式如下：
                **  [{"id":1,"name":"北京"},{"id":2,"name":"上海"}, ... ]
                */
                JSONArray allProvince = new JSONArray(response);
                for (int i = 0 ; i < allProvince.length() ; i++) {
                    // 解析为JSONObject的方式
                    JSONObject provinceObject = allProvince.getJSONObject(i);
                    // 保存在实体类对象中
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();   //保存在数据库当中
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
     **  解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                /*
                 **  将服务器返回的数据转换为JSONArray的方式，服务器数据格式如下：
                 **  [{"id":113,"name":"南京"},{"id":114,"name":"无锡"}, ... ]
                 */
                JSONArray allCity = new JSONArray(response);
                for (int i = 0; i < allCity.length(); i++) {
                    // 解析为JSONObject的方式
                    JSONObject cityObject = allCity.getJSONObject(i);
                    // 保存在实体类对象中
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();   //保存在数据库当中
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
     **  解析和处理服务器返回的县级数据
     */
    public static boolean handleCountryResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                /*
                 **  将服务器返回的数据转换为JSONArray的方式，服务器数据格式如下：
                 **  [{"id":921,"name":"南京","weather_id":"CN101190101"},
                 *    {"id":922,"name":"溧水","weather_id":"CN101190102"},, ... ]
                 */
                JSONArray allCountry = new JSONArray(response);
                for (int i = 0; i < allCountry.length(); i++) {
                    // 解析为JSONObject的方式
                    JSONObject countryObject = allCountry.getJSONObject(i);
                    // 保存在实体类对象中
                    Country country = new Country();
                    country.setCountryName(countryObject.getString("name"));
                    country.setWeatherId(countryObject.getString("weather_id"));
                    country.setCityId(cityId);
                    country.save();   //保存在数据库当中
                }
                return true;
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
