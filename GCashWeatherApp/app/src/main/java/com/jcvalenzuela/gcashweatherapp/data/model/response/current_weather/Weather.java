package com.jcvalenzuela.gcashweatherapp.data.model.response.current_weather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Weather implements Serializable {
    @SerializedName("icon")
    private String icon;
    @SerializedName("description")
    private String description;
    @SerializedName("main")
    private String main;
    @SerializedName("id")
    private int id;

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getMain() {
        return main;
    }

    public int getId() {
        return id;
    }
}
