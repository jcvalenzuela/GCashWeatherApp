package com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.weather;

public interface WeatherViewModelHelper {

    void onGetCurrentWeather(String latitude, String longitude);

    void onGetForecastWeather(int id);
}
