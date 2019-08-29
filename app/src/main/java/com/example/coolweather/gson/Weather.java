package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/***
 *  {"HeWeather":[
 *     {"basic":{},
 *     "update":{},
 *     "status":"ok",
 *     "now":{},
 *     "daily_forecast":[{},{},...]
 *     "aqi":{},
 *     "suggestion":{}
 */

public class Weather {

    public Basic basic;

    public Update update;

    public String status;

    public Now now;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

    public AQI aqi;

    public Suggestion suggestion;

}
