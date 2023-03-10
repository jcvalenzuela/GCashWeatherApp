package com.jcvalenzuela.gcashweatherapp.data;

import com.jcvalenzuela.gcashweatherapp.data.repository.local.LocalDataRepositoryImpl;
import com.jcvalenzuela.gcashweatherapp.data.repository.remote.RemoteWeatherDataRepository;
import com.jcvalenzuela.gcashweatherapp.data.repository.remote.RemoteWeatherDataRepositoryImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainDataRepositoryImpl implements MainDataRepository{

    private RemoteWeatherDataRepositoryImpl remoteWeatherDataRepository;

    private LocalDataRepositoryImpl localDataRepository;

    @Inject
    public MainDataRepositoryImpl(RemoteWeatherDataRepositoryImpl remoteWeatherDataRepository, LocalDataRepositoryImpl localDataRepository) {
        this.remoteWeatherDataRepository = remoteWeatherDataRepository;
        this.localDataRepository = localDataRepository;
    }

    public RemoteWeatherDataRepositoryImpl getRemoteWeatherDataRepository() {
        return remoteWeatherDataRepository;
    }

    public LocalDataRepositoryImpl getLocalDataRepository() {
        return localDataRepository;
    }
}
