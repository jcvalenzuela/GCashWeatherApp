package com.jcvalenzuela.gcashweatherapp.presentation.fragment.fetch_weather;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FetchWeatherFragmentProvider {

    @ContributesAndroidInjector(modules = FetchWeatherFragmentModule.class)
    public  abstract FetchWeatherFragment contributeFetchWeatherFragment();
}
