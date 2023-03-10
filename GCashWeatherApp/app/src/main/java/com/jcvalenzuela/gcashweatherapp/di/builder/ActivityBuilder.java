package com.jcvalenzuela.gcashweatherapp.di.builder;

import com.jcvalenzuela.gcashweatherapp.presentation.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    public abstract MainActivity contributeMainActivity();
}
