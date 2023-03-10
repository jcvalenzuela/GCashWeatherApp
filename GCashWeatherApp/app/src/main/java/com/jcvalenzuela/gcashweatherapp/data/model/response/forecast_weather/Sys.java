package com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sys implements Serializable {
    @Expose
    @SerializedName("pod")
    private String pod;

    public String getPod() {
        return pod;
    }
}
