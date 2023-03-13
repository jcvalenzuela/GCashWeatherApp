package com.jcvalenzuela.gcashweatherapp.di.component;

import android.app.Application;

import com.jcvalenzuela.gcashweatherapp.BaseApplication;
import com.jcvalenzuela.gcashweatherapp.di.builder.ActivityBuilder;
import com.jcvalenzuela.gcashweatherapp.di.module.AppModule;
import com.jcvalenzuela.gcashweatherapp.presentation.MainActivityModule;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class
        , AppModule.class
        , MainActivityModule.class
        , ActivityBuilder.class
})
public interface ApplicationComponent extends AndroidInjector<BaseApplication>{

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
