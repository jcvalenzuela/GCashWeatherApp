package com.jcvalenzuela.gcashweatherapp.presentation.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.LoginViewModel;

import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

public abstract class BaseActivity <T extends ViewDataBinding, V extends BaseViewModel> extends DaggerAppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private T viewDataBinding;

    private V viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        getViewDataBinding().setLifecycleOwner(this);
        getViewDataBinding().setVariable(getBindingVariables(), initViewModel());
        viewModel = initViewModel();

    }

    public abstract int getLayoutId();

    public abstract V initViewModel();

    public abstract int getBindingVariables();

    public V getViewModel() { return viewModel;}

    public T getViewDataBinding() {
        return viewDataBinding;
    }

}
