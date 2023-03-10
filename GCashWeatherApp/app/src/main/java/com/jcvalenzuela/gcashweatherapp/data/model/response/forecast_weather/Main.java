package com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Main implements Serializable {
    @Expose
    @SerializedName("temp")
    private double temp;
    @Expose
    @SerializedName("feels_like")
    private double feelsLike;
    @Expose
    @SerializedName("temp_min")
    private double tempMin;
    @Expose
    @SerializedName("temp_max")
    private double tempMax;
    @Expose
    @SerializedName("pressure")
    private double pressure;
    @Expose
    @SerializedName("sea_level")
    private double seaLevel;
    @Expose
    @SerializedName("grnd_level")
    private double grndLevel;
    @Expose
    @SerializedName("humidity")
    private int humidity;
    @Expose
    @SerializedName("temp_kf")
    private float tempKf;

    public double getTemp() {
        return temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getPressure() {
        return pressure;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public double getGrndLevel() {
        return grndLevel;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getTempKf() {
        return tempKf;
    }
}
