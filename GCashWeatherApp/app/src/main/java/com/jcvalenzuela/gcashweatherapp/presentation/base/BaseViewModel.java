package com.jcvalenzuela.gcashweatherapp.presentation.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jcvalenzuela.gcashweatherapp.data.MainDataRepositoryImpl;


public abstract class BaseViewModel extends ViewModel {

    private MainDataRepositoryImpl mainDataRepository;

    private MutableLiveData<Boolean> isLoading;

    public BaseViewModel(MainDataRepositoryImpl mainDataRepository) {
        this.mainDataRepository = mainDataRepository;
        this.isLoading = new MutableLiveData<>(true);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(MutableLiveData<Boolean> isLoading) {
        this.isLoading = isLoading;
    }

    public MainDataRepositoryImpl getMainDataRepository() {
        return mainDataRepository;
    }
}
