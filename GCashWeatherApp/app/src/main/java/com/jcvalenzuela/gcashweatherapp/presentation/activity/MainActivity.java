package com.jcvalenzuela.gcashweatherapp.presentation.activity;

import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.dispose;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.disposeComposite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding3.view.RxView;
import com.jcvalenzuela.gcashweatherapp.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogin;
    private Button buttonRegister;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Disposable disposableTimer;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        buttonLogin = findViewById(R.id.btnLogin);
//        buttonRegister = findViewById(R.id.btnSignUp);
//
//        context = this;
//
//
//        compositeDisposable.add(RxView.clicks(buttonLogin)
//                .throttleFirst(500, TimeUnit.MILLISECONDS)
//                .subscribe(unit -> {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                }, throwable -> Log.e("TAG", throwable.getMessage())));
//
//        compositeDisposable.add(RxView.clicks(buttonRegister)
//                .throttleFirst(500, TimeUnit.MILLISECONDS)
//                .subscribe(unit -> {
//                    Intent intent = new Intent(context, RegistrationActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                }, throwable -> Log.e("TAG", throwable.getMessage())));

        disposableTimer = Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        dispose(disposableTimer);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose(disposableTimer);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}