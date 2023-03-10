package com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coord implements Serializable {
    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("lon")
    private double lon;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
