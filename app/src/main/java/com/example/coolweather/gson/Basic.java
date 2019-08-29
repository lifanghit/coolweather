package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/*
    从和风天气网实际返回的数据：http://guolin.tech/api/weather?cityid=CN101210801
    "basic":{
        "cid":"CN101210801",
        "location":"丽水",
        "parent_city":"丽水",
        "admin_area":"浙江",
        "cnty":"中国",
        "lat":"28.19408989",
        "lon":"112.98227692",
        "tz":"+8.00",
        "city":"丽水",
        "id":"CN101210801",
        "update":{
            "loc":"2019-08-29 16:35",
            "utc":"2019-08-29 08:35"}},
 */

public class Basic {

    /*
    **  JSON中的一些字段不太适合直接作为Java字段来命名
    *     因此使用了@SerializedName的注解方式来让JSON字段和Java字段之间建立映射关系
     */

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
