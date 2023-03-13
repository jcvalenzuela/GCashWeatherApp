package com.jcvalenzuela.gcashweatherapp.data.local.cache;

public interface PrefSharedHelper {

    void setIsLoggedIn(boolean bool);

    boolean isLoggedIn();

    void setId(int id);

    int getId();

    void  clear(String key);
}
