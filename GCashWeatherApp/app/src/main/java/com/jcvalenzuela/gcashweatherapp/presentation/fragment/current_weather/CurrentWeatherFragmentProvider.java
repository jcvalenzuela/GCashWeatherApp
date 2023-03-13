package com.jcvalenzuela.gcashweatherapp.presentation.fragment.current_weather;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CurrentWeatherFragmentProvider {

    @ContributesAndroidInjector(modules = CurrentWeatherFragmentModule.class)
    public abstract CurrentWeatherFragment contributeCurrentWeatherFragment();
}
