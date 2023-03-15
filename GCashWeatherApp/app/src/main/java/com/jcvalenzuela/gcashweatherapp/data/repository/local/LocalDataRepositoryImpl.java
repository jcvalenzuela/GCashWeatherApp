package com.jcvalenzuela.gcashweatherapp.data.repository.local;


import com.jcvalenzuela.gcashweatherapp.data.local.db.dao.AppDatabase;
import com.jcvalenzuela.gcashweatherapp.data.model.entity.LoginEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalDataRepositoryImpl implements LocalDataRepository {

    private AppDatabase appDatabase;

    @Inject
    public LocalDataRepositoryImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void insertLogin(LoginEntity loginEntity) {
        appDatabase.getLoginDao().insertLogin(loginEntity);
    }

    @Override
    public boolean isUserLogin(String email, String password) {
        return appDatabase.getLoginDao().isUserLogin(email, password);
    }

    @Override
    public boolean isUserAccountExists(String user) {
        return appDatabase.getLoginDao().isUserAccountExists(user);
    }
}
