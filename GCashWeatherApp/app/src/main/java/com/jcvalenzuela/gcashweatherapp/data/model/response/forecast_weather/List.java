package com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class List implements Serializable {
    @Expose
    @SerializedName("dt")
    private int dt;
    @Expose
    @SerializedName("main")
    private Main main;
    @Expose
    @SerializedName("weather")
    private java.util.List<Weather> weatherList = new ArrayList<>();
    @Expose
    @SerializedName("clouds")
    private Clouds clouds;
    @Expose
    @SerializedName("wind")
    private Wind wind;
    @Expose
    @SerializedName("visibility")
    private int visibility;
    @Expose
    @SerializedName("pop")
    private double pop;
    @Expose
    @SerializedName("rain")
    private Rain rain;
    @Expose
    @SerializedName("sys")
    private Sys sys;
    @Expose
    @SerializedName("dt_txt")
    private String dtTxt;

    public int getDt() {
        return dt;
    }

    public Main getMain() {
        return main;
    }

    public java.util.List<Weather> getWeatherList() {
        return weatherList;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public int getVisibility() {
        return visibility;
    }

    public double getPop() {
        return pop;
    }

    public Sys getSys() {
        return sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public Rain getRain() {
        return rain;
    }
}
