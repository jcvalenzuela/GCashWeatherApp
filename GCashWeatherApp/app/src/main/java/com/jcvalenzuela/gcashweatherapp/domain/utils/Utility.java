package com.jcvalenzuela.gcashweatherapp.domain.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
}
