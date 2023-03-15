package com.jcvalenzuela.gcashweatherapp.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jcvalenzuela.gcashweatherapp.data.model.entity.LoginEntity;

@Dao
public interface LoginDao {

    @Insert
    void insertLogin(LoginEntity loginEntity);

    @Query("SELECT * FROM tblLogin WHERE user = :user AND password = :password")
    boolean isUserLogin(String user, String password);


    @Query("SELECT * FROM tblLogin WHERE user = :user")
    boolean isUserAccountExists(String user);

}
