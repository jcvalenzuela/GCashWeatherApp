package com.jcvalenzuela.gcashweatherapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jcvalenzuela.gcashweatherapp.ViewModelProviderFactory;
import com.jcvalenzuela.gcashweatherapp.di.ViewModelKey;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.login.LoginViewModel;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.weather.WeatherViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    public abstract ViewModel bindCurrentWeatherViewModel(WeatherViewModel weatherViewModel);


    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(
            ViewModelProviderFactory viewModelProvideFactory
    );


}
