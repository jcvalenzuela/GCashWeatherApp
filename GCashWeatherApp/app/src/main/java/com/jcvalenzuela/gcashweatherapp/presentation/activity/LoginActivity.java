package com.jcvalenzuela.gcashweatherapp.presentation.activity;

import static com.jcvalenzuela.gcashweatherapp.helper.utils.CustomAlertDialogBuilder.disposableAlert;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.dispose;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.disposeAlertDialog;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.disposeComposite;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.isDayTime;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.jakewharton.rxbinding3.view.RxView;
import com.jcvalenzuela.gcashweatherapp.BR;
import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.databinding.ActivityLoginBinding;
import com.jcvalenzuela.gcashweatherapp.domain.LoginEnumState;
import com.jcvalenzuela.gcashweatherapp.helper.utils.CustomAlertDialogBuilder;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseActivity;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.login.LoginViewModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    Context context;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getViewDataBinding().setLoginViewModel(initViewModel());
        getViewDataBinding().constraintBackground.setBackground(getDrawable(isDayTime()));
        initButton();
        initResult();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel initViewModel() {
        return new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);
    }

    @Override
    public int getBindingVariables() {
        return BR.loginViewModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposeComposite(compositeDisposable);
        disposeAlertDialog(alertDialog);
        dispose(disposableAlert);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initButton() {
        compositeDisposable.add(RxView.clicks(getViewDataBinding().btnLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(unit -> {
                    //Execute Login
                    getViewModel().onUserLogin();
                }, throwable -> Log.e(TAG, throwable.getMessage())));

        compositeDisposable.add(RxView.clicks(getViewDataBinding().textViewSignup)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(unit -> {
                    Intent intent = new Intent(context, RegistrationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }, throwable -> Log.e(TAG, throwable.getMessage())));
    }

    private void initResult() {
        Log.e(TAG, "InitResult");
        getViewModel()
                .getLiveLoginDataLiveData()
                .observe(this,
                        liveLoginData -> {
                            if (liveLoginData.getLoginEnumState() == LoginEnumState.LOGIN_SUCCESSFUL) {
                                Intent intent = new Intent(context, CurrentWeatherActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                alertDialog = CustomAlertDialogBuilder.customDialogBox(
                                        this,
                                        R.drawable.error_outline,
                                        liveLoginData.getMessage(),
                                        () -> Log.e("TAG: ", "No Action")
                                );
                            }

                        });
    }
}