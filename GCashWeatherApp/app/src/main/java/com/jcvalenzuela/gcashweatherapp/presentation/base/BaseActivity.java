package com.jcvalenzuela.gcashweatherapp.presentation.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity <T extends ViewDataBinding, V extends BaseViewModel> extends DaggerAppCompatActivity {

    private T viewDataBinding;

    private V viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel();
        performDataBinding();
    }

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        viewDataBinding.setLifecycleOwner(this);
    }

    public abstract int getLayoutId();

    public abstract V initViewModel();

    public T getViewDataBinding() {
        return viewDataBinding;
    }

}
