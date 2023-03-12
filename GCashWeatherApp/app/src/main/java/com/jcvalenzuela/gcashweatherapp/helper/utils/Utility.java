package com.jcvalenzuela.gcashweatherapp.helper.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class Utility {

    public static boolean hasNetworkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isValidMail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isStringEmpty(final String str) {
        return (str == null || str.isEmpty());
    }


    public static void disposeComposite(CompositeDisposable compositeDisposable) {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public static void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public static void disposeAlertDialog(AlertDialog alertDialog) {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public static String dateAndTimeFormatter(String dateTime) {
        SimpleDateFormat getDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");
        Date date = null;
        try {
            date = getDateFormat.parse(dateTime);
        } catch (ParseException e) {
            Timber.e(e);
        }
        return newDateFormat.format(date);
    }

    public static String convertDblToStr(Double val) {
        return String.valueOf(val);
    }


    public static String convertToDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("MMMM dd, yyyy", cal).toString();
        return date;
    }

    public static String convertToTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("hh:mm a", cal).toString();
        return date;
    }

    public static boolean isSunset(long sunrise) {
        boolean isDetect = false;
        // sunset
        int sunset = 36000000; //default
        Calendar currentCal = Calendar.getInstance(Locale.getDefault());
        String currentTime = DateFormat.format("HH:mm:ss", currentCal).toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date currentDate = null;
        try {

            currentDate = dateFormat.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currTime = currentDate.getTime();

        if (currTime >= sunset) {
            Timber.i("Night at start 6pm...");
            isDetect = true;
        } else if (currTime <= sunrise) {
            Timber.e("Sunrise is starting...");
            isDetect = false;
        }
        return isDetect;
    }
}
