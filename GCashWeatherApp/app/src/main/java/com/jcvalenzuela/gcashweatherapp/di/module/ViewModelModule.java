package com.jcvalenzuela.gcashweatherapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jcvalenzuela.gcashweatherapp.ViewModelProviderFactory;
import com.jcvalenzuela.gcashweatherapp.di.ViewModelKey;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(
            ViewModelProviderFactory viewModelProvideFactory
    );

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);
}
