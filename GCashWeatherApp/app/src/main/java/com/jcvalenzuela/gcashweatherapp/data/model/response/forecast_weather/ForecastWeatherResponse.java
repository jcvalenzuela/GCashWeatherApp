package com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ForecastWeatherResponse implements Serializable {
    @Expose
    @SerializedName("cod")
    private int cod;
    @Expose
    @SerializedName("message")
    private int message;
    @Expose
    @SerializedName("cnt")
    private int cnt;
    @Expose
    @SerializedName("list")
    private java.util.List<List> list;
    @Expose
    @SerializedName("city")
    private City city;

    public int getCod() {
        return cod;
    }

    public int getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public City getCity() {
        return city;
    }
}
