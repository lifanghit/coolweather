package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * "update":{
 *         "loc":"2019-08-29 16:35",
 *         "utc":"2019-08-29 08:35"}
 */

public class Update {

    @SerializedName("loc")
    public String location;

    public String utc;
}
