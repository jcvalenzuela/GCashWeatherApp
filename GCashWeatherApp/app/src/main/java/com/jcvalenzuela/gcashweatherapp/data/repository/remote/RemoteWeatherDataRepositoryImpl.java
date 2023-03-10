package com.jcvalenzuela.gcashweatherapp.data.repository.remote;

import com.jcvalenzuela.gcashweatherapp.data.model.response.current_weather.CurrentWeatherResponse;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.ForecastWeatherResponse;
import com.jcvalenzuela.gcashweatherapp.data.remote.ApiClientService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class RemoteWeatherDataRepositoryImpl implements RemoteWeatherDataRepository {

    private final ApiClientService apiClientService;

    @Inject
    public RemoteWeatherDataRepositoryImpl(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    @Override
    public Observable<CurrentWeatherResponse> getCurWeather(String lat, String lon, String appId) {
        return apiClientService.getCurWeather(lat, lon, appId);
    }

    @Override
    public Observable<ForecastWeatherResponse> getForecastWeather(int id, String appId) {
        return apiClientService.getForecastWeather(id, appId);
    }

    @Override
    public Observable<CurrentWeatherResponse> getCurWeatherByPlace(String queryPlace, String apiKey) {
        return apiClientService.getCurWeatherByPlace(queryPlace, apiKey);
    }
}
