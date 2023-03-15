package com.jcvalenzuela.gcashweatherapp.data.local.db.dao;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.jcvalenzuela.gcashweatherapp.data.model.entity.LoginEntity;
import com.jcvalenzuela.gcashweatherapp.di.component.ApplicationComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

public class LoginDaoTest {
    private AppDatabase appDatabase;

    private LoginDao loginDao;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(
                context,
                AppDatabase.class).build();

        loginDao = appDatabase.getLoginDao();
    }

    @After
    public void tearDown() throws Exception {

        appDatabase.close();
    }

    @Test
    public void loginLiveData() {
        LoginEntity loginEntity = new LoginEntity(
                0,
                "test",
                "test@gmail.com",
                "test123",
                "test123"
                );
        LiveData<LoginEntity> entities = loginDao.loginLiveData("test", "test123");
        loginDao.insertLogin(loginEntity);
        assertThat(entities, equalTo(loginEntity));
    }

    @Test
    public void isUserLogin_TC01() {
        LoginEntity loginEntity = new LoginEntity(
                0,
                "test",
                "test@gmail.com",
                "test123",
                "test123"
        );
        loginDao.insertLogin(loginEntity);

        boolean test = loginDao.isUserLogin("test", "test123");
        assertTrue(test);
    }

    @Test
    public void isUserLogin_TC02() {
        LoginEntity loginEntity = new LoginEntity(
                0,
                "test",
                "test@gmail.com",
                "test123",
                "test123"
        );
        loginDao.insertLogin(loginEntity);
        boolean isUserExists = loginDao.isUserLogin("test123", "test123");
        assertFalse(isUserExists);
    }


    @Test
    public void isUserAccountExists_TC01() {
        LoginEntity loginEntity = new LoginEntity(
                0,
                "test",
                "test@gmail.com",
                "test123",
                "test123"
        );
        loginDao.insertLogin(loginEntity);

        boolean isUserAccountExists = loginDao.isUserAccountExists("test@gmail.com");
        assertTrue(isUserAccountExists);
    }

    @Test
    public void isUserAccountExists_TC02() {
        LoginEntity loginEntity = new LoginEntity(
                0,
                "test",
                "test@gmail.com",
                "test123",
                "test123"
        );
        loginDao.insertLogin(loginEntity);

        boolean isUserAccountExists = loginDao.isUserAccountExists("test123@gmail.com");
        assertFalse(isUserAccountExists);
    }
}