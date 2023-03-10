package com.jcvalenzuela.gcashweatherapp.domain;

public class LiveLoginData {

    private final LoginEnumState loginEnumState;

    private final String message;

    public LiveLoginData(LoginEnumState loginEnumState, String message) {
        this.loginEnumState = loginEnumState;
        this.message = message;
    }

    public LoginEnumState getLoginEnumState() {
        return loginEnumState;
    }

    public String getMessage() {
        return message;
    }
}
