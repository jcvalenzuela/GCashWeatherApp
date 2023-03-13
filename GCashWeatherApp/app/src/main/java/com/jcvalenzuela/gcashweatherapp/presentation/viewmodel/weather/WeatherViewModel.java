package com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.weather;

import static com.jcvalenzuela.gcashweatherapp.data.remote.client.ApiClient.API_KEY_STRING;
import static com.jcvalenzuela.gcashweatherapp.helper.ConstantDeclarations.SHARED_PREF_WEATHER_ID_KEY;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.jcvalenzuela.gcashweatherapp.data.MainDataRepositoryImpl;
import com.jcvalenzuela.gcashweatherapp.data.model.response.current_weather.CurrentWeatherResponse;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.ForecastWeatherResponse;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class WeatherViewModel extends BaseViewModel implements WeatherViewModelHelper {

    private static final String TAG = WeatherViewModel.class.getSimpleName();

    private MutableLiveData<CurrentWeatherResponse> currentWeatherResponseMutableLiveData;

    public LiveData<CurrentWeatherResponse> getCurrentWeatherLiveData() {
        return currentWeatherResponseMutableLiveData;
    }

    private MutableLiveData<ForecastWeatherResponse> forecastWeatherResponseMutableLiveData;

    public LiveData<ForecastWeatherResponse> getForecastWeatherLiveData() {
        return forecastWeatherResponseMutableLiveData;
    }



    @Inject
    public WeatherViewModel(MainDataRepositoryImpl mainDataRepository) {
        super(mainDataRepository);
        currentWeatherResponseMutableLiveData = new MutableLiveData<>();
        forecastWeatherResponseMutableLiveData = new MutableLiveData<>();
    }


    @Override
    public void onGetCurrentWeather(String latitude, String longitude) {
        Log.e(TAG, "onGetCurrentWeather: " + "--Latitude: " + latitude +"--Longitude: " + longitude);
        setIsLoading(true);
        getMainDataRepository().getRemoteWeatherDataRepository().getCurWeather(
                        latitude,
                        longitude,
                        API_KEY_STRING
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentWeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CurrentWeatherResponse currentWeatherResponse) {
                        Log.e(TAG, "onNext CurrentWeather: " + new Gson().toJson(currentWeatherResponse));
                        currentWeatherResponseMutableLiveData.setValue(currentWeatherResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError CurrentWeather: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete CurrentWeather" );
                        setIsLoading(false);
                    }
                });
    }

    @Override
    public void onGetForecastWeather(int id) {
        setIsLoading(true);
        getMainDataRepository().getRemoteWeatherDataRepository().getForecastWeather(
                        id,
                        API_KEY_STRING
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForecastWeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ForecastWeatherResponse forecastWeatherResponse) {
                        Log.e(TAG, "onNext onGetForecastWeather: " + new Gson().toJson(forecastWeatherResponse) );
                        forecastWeatherResponseMutableLiveData.setValue(forecastWeatherResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.tag(TAG).e(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete onGetForecastWeather" );
                    }
                });
    }



    public void setWeatherId(int id) {
        getMainDataRepository().getPrefSharedCacheData().setId(id);
    }

    public int getWeatherId() {
        return getMainDataRepository().getPrefSharedCacheData().getId();
    }

    public void deleteWeatherId() {
        getMainDataRepository().getPrefSharedCacheData().clear(SHARED_PREF_WEATHER_ID_KEY);
    }
}
