package com.jcvalenzuela.gcashweatherapp.presentation.fragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CurrentWeatherFragmentProvider {

    @ContributesAndroidInjector(modules = CurrentWeatherFragmentModule.class)
    public abstract CurrentWeatherFragment contributeCurrentWeatherFragment();
}
