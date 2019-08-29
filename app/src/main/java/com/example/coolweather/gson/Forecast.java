package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/***
 *  "daily_forecast":[
 *         {   "date":"2019-08-30",
 *             "cond":{"txt_d":"多云"},
 *             "tmp":{
 *                 "max":"24","min":"12"}},
 *         {"date":"2019-08-31","cond":{"txt_d":"多云"},"tmp":{"max":"25","min":"11"}},
 *         {"date":"2019-09-01","cond":{"txt_d":"阵雨"},"tmp":{"max":"15","min":"11"}},
 *         {"date":"2019-09-02","cond":{"txt_d":"阴"},"tmp":{"max":"21","min":"12"}},
 *         {"date":"2019-09-03","cond":{"txt_d":"多云"},"tmp":{"max":"26","min":"13"}},
 *         {"date":"2019-09-04","cond":{"txt_d":"阵雨"},"tmp":{"max":"15","min":"9"}}]
 */
public class Forecast {

    public String date;

    @SerializedName("cond")
    public Condition condition;

    @SerializedName("tmp")
    public Temperature temperature;

    public class Condition {
        @SerializedName("txt_d")
        public String info;
    }

    public class Temperature {
        public String max;
        public String min;
    }
}
