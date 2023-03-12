package com.jcvalenzuela.gcashweatherapp.domain.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;
import com.jcvalenzuela.gcashweatherapp.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;
import timber.log.Timber;

public class CustomAlertDialogBuilder {

    public static Disposable disposableAlert;

    public static AlertDialog customDialogBox(final Context context, final int imageResource,
                                              final String alertMessage, final Runnable runnable) {

        LayoutInflater factory = LayoutInflater.from(context);
        final View alertDialogView = factory.inflate(R.layout.custom_dialog, null);

        ImageView resource = alertDialogView.findViewById(R.id.image_alert);
        resource.setImageDrawable(context.getDrawable(imageResource));
        TextView txtMessage = alertDialogView.findViewById(R.id.text_message);
        txtMessage.setText(alertMessage);
        Button btnClose = alertDialogView.findViewById(R.id.button_close);

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setView(alertDialogView);

        disposableAlert = (RxView.clicks(btnClose)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Exception {
                        if (runnable != null) {
                            runnable.run();
                        }
                        Utility.disposeAlertDialog(alertDialog);
                        Utility.dispose(disposableAlert);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                }));




        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
        return alertDialog;
    }
}
