package com.jcvalenzuela.gcashweatherapp.data.repository.local;

import androidx.lifecycle.LiveData;

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
    public LiveData<LoginEntity> loginLiveData(String email, String password) {
        return appDatabase.getLoginDao().loginLiveData(email, password);
    }

    @Override
    public boolean isUserAccountExists(String user) {
        return appDatabase.getLoginDao().isUserAccountExists(user);
    }
}
