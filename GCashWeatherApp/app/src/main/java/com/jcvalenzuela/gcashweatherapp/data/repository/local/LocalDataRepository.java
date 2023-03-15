package com.jcvalenzuela.gcashweatherapp.data.repository.local;

import androidx.lifecycle.LiveData;

import com.jcvalenzuela.gcashweatherapp.data.model.entity.LoginEntity;

public interface LocalDataRepository {

    void insertLogin(LoginEntity loginEntity);

    boolean isUserLogin(String email, String password);

    boolean isUserAccountExists(String user);
}
