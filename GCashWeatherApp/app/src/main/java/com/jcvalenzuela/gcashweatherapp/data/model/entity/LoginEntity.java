package com.jcvalenzuela.gcashweatherapp.data.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * DB Entity for Login.
 *
 * @author Julius Valenzuela
 * @version 1.0
 * @since 2023/03/10
 */

@Entity(tableName = "tblLogin")
public class LoginEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "user")
    private String user;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    @NonNull
    @ColumnInfo(name = "re_password")
    private String confirmPassword;


    public LoginEntity(@NonNull String user,
                       @NonNull String email,
                       @NonNull String password,
                       @NonNull String confirmPassword) {

        this.user = user;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }


    @Ignore
    public LoginEntity(int id,
                       @NonNull String user,
                       @NonNull String email,
                       @NonNull String password,
                       @NonNull String confirmPassword) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }


    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
