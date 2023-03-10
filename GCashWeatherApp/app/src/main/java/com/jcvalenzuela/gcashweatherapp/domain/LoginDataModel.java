package com.jcvalenzuela.gcashweatherapp.domain;

public class LoginDataModel {

    private final String user;

    private final String password;

    private final String email;

    private final String confirmPassword;


    public LoginDataModel(String user, String password, String email, String confirmPassword) {
        this.user = user;
        this.password = password;
        this.email = email;
        this.confirmPassword = confirmPassword;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
