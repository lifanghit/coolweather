package com.example.coolweather.gson;

/*
**  "now":{
        "cloud":"100",
        "cond_code":"101",
        "cond_txt":"多云",
        "fl":"20",
        "hum":"78",
        "pcpn":"0.0",
        "pres":"1013",
        "tmp":"19",
        "vis":"2",
        "wind_deg":"261",
        "wind_dir":"西风",
        "wind_sc":"1",
        "wind_spd":"2",
        "cond":{
            "code":"101",
            "txt":"多云"}}
 */

import com.google.gson.annotations.SerializedName;

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public Condition condition;

    public class Condition {

        public String code;

        @SerializedName("txt")
        public String info;
    }
}
