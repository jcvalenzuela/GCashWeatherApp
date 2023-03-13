package com.jcvalenzuela.gcashweatherapp.di.builder;

import com.jcvalenzuela.gcashweatherapp.presentation.activity.CurrentWeatherActivity;
import com.jcvalenzuela.gcashweatherapp.presentation.activity.LoginActivity;
import com.jcvalenzuela.gcashweatherapp.presentation.activity.MainActivity;
import com.jcvalenzuela.gcashweatherapp.presentation.activity.RegistrationActivity;
import com.jcvalenzuela.gcashweatherapp.presentation.fragment.current_weather.CurrentWeatherFragmentProvider;
import com.jcvalenzuela.gcashweatherapp.presentation.fragment.fetch_weather.FetchWeatherFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    public abstract RegistrationActivity contributeRegistrationActivity();

    @ContributesAndroidInjector
    public abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    public abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = {
            CurrentWeatherFragmentProvider.class,
            FetchWeatherFragmentProvider.class
    })
    public abstract CurrentWeatherActivity contributeCurrentWeatherActivity();


}
