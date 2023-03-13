package com.jcvalenzuela.gcashweatherapp.data.local.cache;

import static com.jcvalenzuela.gcashweatherapp.helper.ConstantDeclarations.SHARED_PREF_LOGIN_KEY;
import static com.jcvalenzuela.gcashweatherapp.helper.ConstantDeclarations.SHARED_PREF_WEATHER_ID_KEY;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefSharedCacheData implements PrefSharedHelper{

    private SharedPreferences sharedPreferences;

    @Inject
    public PrefSharedCacheData(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void setIsLoggedIn(boolean bool) {
        sharedPreferences.edit().putBoolean(SHARED_PREF_LOGIN_KEY, bool).commit();
    }

    @Override
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(SHARED_PREF_LOGIN_KEY, false);
    }

    @Override
    public void setId(int id) {
        sharedPreferences.edit().putInt(SHARED_PREF_WEATHER_ID_KEY, id).commit();
    }

    @Override
    public int getId() {
        return sharedPreferences.getInt(SHARED_PREF_WEATHER_ID_KEY, 0);
    }

    @Override
    public void clear(String key) {
        sharedPreferences.edit().remove(key).commit();
    }
}
