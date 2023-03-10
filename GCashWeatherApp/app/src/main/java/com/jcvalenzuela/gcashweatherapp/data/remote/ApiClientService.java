package com.jcvalenzuela.gcashweatherapp.data.remote;

import com.jcvalenzuela.gcashweatherapp.data.model.response.current_weather.CurrentWeatherResponse;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.ForecastWeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClientService {

    @GET("weather")
    Observable<CurrentWeatherResponse> getCurWeather(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appId);

    @GET("forecast")
    Observable<ForecastWeatherResponse> getForecastWeather(
            @Query("id") int id,
            @Query("appid") String appId);

    @GET("weather")
    Observable<CurrentWeatherResponse> getCurWeatherByPlace(
            @Query("q") String queryPlace,
            @Query("appid") String apiKey);




}
