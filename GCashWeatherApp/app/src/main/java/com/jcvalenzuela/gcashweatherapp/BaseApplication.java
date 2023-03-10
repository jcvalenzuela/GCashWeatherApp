package com.jcvalenzuela.gcashweatherapp;

import com.jcvalenzuela.gcashweatherapp.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    @Override
    protected AndroidInjector< ? extends DaggerApplication > applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }
}
