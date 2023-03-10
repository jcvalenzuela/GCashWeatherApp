package com.jcvalenzuela.gcashweatherapp.data.repository.remote;

import com.jcvalenzuela.gcashweatherapp.data.model.response.current_weather.CurrentWeatherResponse;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.ForecastWeatherResponse;

import io.reactivex.Observable;

public interface RemoteWeatherDataRepository {

    Observable<CurrentWeatherResponse> getCurWeather(
            String lat,
            String lon,
           String appId
    );


    Observable<ForecastWeatherResponse> getForecastWeather(
           int id,
           String appId
    );


    Observable<CurrentWeatherResponse> getCurWeatherByPlace(
            String queryPlace,
            String apiKey
    );
}
