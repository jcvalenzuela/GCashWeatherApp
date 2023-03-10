package com.jcvalenzuela.gcashweatherapp.data.repository.local;

import androidx.lifecycle.LiveData;

import com.jcvalenzuela.gcashweatherapp.data.model.entity.LoginEntity;

public interface LocalDataRepository {

    void insertLogin(LoginEntity loginEntity);

    LiveData<LoginEntity> loginLiveData(String email, String password);

    boolean isAlreadyRegistered(String email, String user);

    boolean isUserAccountExists(String user);
}
