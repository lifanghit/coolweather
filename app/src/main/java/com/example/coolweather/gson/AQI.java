package com.example.coolweather.gson;

/*
 **  "aqi":{"city":{"aqi":"83","pm25":"61","qlty":"良"}}
 */

public class AQI {

    public AQICity city;

    public class AQICity {

        public String aqi;

        public String pm25;

        public String qlty;
    }
}
