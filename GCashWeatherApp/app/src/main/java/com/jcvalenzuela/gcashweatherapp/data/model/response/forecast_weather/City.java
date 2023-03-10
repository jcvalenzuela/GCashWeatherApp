package com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class City implements Serializable {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("coord")
    private Coord coord;
    @Expose
    @SerializedName("country")
    private String country;
    @Expose
    @SerializedName("population")
    private int population;
    @Expose
    @SerializedName("timezone")
    private int timeZone;
    @Expose
    @SerializedName("sunrise")
    private int sunrise;
    @Expose
    @SerializedName("sunset")
    private int sunset;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }

    public int getPopulation() {
        return population;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }
}
