package com.jcvalenzuela.gcashweatherapp.presentation.activity;

import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.dispose;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.disposeComposite;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.isDayTime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.jakewharton.rxbinding3.view.RxView;
import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.databinding.ActivityMainBinding;

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


    private ActivityMainBinding activityMainBinding;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Disposable disposableTimer;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        activityMainBinding.constraintMain.setBackground(getDrawable(isDayTime()));


        disposableTimer = Observable.timer(2, TimeUnit.SECONDS)
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