package com.jcvalenzuela.gcashweatherapp.presentation.activity;

import static com.jcvalenzuela.gcashweatherapp.domain.utils.CustomAlertDialogBuilder.disposableAlert;
import static com.jcvalenzuela.gcashweatherapp.domain.utils.Utility.dispose;
import static com.jcvalenzuela.gcashweatherapp.domain.utils.Utility.disposeAlertDialog;
import static com.jcvalenzuela.gcashweatherapp.domain.utils.Utility.disposeComposite;

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
import com.jcvalenzuela.gcashweatherapp.databinding.ActivityRegistrationBinding;
import com.jcvalenzuela.gcashweatherapp.domain.LoginEnumState;
import com.jcvalenzuela.gcashweatherapp.domain.utils.CustomAlertDialogBuilder;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseActivity;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.LoginViewModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

public class RegistrationActivity extends BaseActivity<ActivityRegistrationBinding, LoginViewModel> {

    private static final String TAG = RegistrationActivity.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    Context context;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getViewDataBinding().setLoginViewModel(initViewModel());
        initButton();
        initResult();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initButton() {
        //Button Register
        compositeDisposable.add(RxView.clicks(getViewDataBinding().btnRegister)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(unit -> {
                    //Execute Registration
                    getViewModel().onUserRegistration();
                }, throwable -> Log.e(TAG, throwable.getMessage())));

        //Button Back
        compositeDisposable.add(RxView.clicks(getViewDataBinding().imgBackButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(unit -> {
                    Intent intent = new Intent(context, LoginActivity.class);
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
                            if (liveLoginData.getLoginEnumState() == LoginEnumState.REGISTRATION_SUCCESS) {
                                Intent intent = new Intent(context, LoginActivity.class);
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