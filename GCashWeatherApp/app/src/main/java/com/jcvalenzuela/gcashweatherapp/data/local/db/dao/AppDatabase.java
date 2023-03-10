package com.jcvalenzuela.gcashweatherapp.data.local.db.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jcvalenzuela.gcashweatherapp.data.local.db.dao.LoginDao;
import com.jcvalenzuela.gcashweatherapp.data.model.entity.LoginEntity;

@Database(entities = {LoginEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LoginDao getLoginDao();
}
