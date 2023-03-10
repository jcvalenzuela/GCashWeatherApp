package com.jcvalenzuela.gcashweatherapp.presentation.activity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.databinding.ActivityRegistrationBinding;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseActivity;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.LoginViewModel;

import javax.inject.Inject;

import timber.log.Timber;

public class RegistrationActivity extends BaseActivity<ActivityRegistrationBinding, LoginViewModel> {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initResult() {
        getViewModel()
                .getLiveLoginDataLiveData()
                .observe(this,
                        liveLoginData -> {
                            switch (liveLoginData.getLoginEnumState()) {
                                case MISSING_FIELDS:
                                    Log.e("TAG: " , liveLoginData.getMessage());
                                    break;
                                default:
                                    break;
                            }
                        });
    }

}