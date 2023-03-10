package com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Clouds implements Serializable {
    @Expose
    @SerializedName("all")
    private int all;
}
