package com.jcvalenzuela.gcashweatherapp.helper.utils;

import static org.junit.Assert.*;

import android.content.Context;

import org.junit.Test;

public class UtilityTest {


    @Test
    public void isValidMail() {
        boolean testTrue = Utility.isValidMail("test");
        assertTrue(testTrue);
    }

    @Test
    public void isStringEmpty_TC01() {
        boolean testTrue = Utility.isStringEmpty("");
        assertTrue(testTrue);
    }


    @Test
    public void isStringEmpty_TC02() {
        boolean testFalse = Utility.isStringEmpty("helloworld");
        assertFalse(testFalse);
    }


    @Test
    public void convertDblToStr_TC01() {
        Double dtTest = 10.20;
        String testTrue = Utility.convertDblToStr(dtTest);
    }

    @Test
    public void convertToDate() {
    }

    @Test
    public void convertToTime() {
    }

    @Test
    public void isSunset() {
    }

    @Test
    public void getCurrentWeatherStatus() {
    }

    @Test
    public void isDayTime() {
    }

    @Test
    public void getWeatherStatus() {
    }

    @Test
    public void setWeatherIconByTime() {
    }
}