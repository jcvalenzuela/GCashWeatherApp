package com.jcvalenzuela.gcashweatherapp.presentation.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment  <T extends ViewDataBinding, V extends ViewModel> extends DaggerFragment {

    private Context context;
    private View rootView;
    private V viewModel;
    private T viewDataBinding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        rootView = viewDataBinding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getViewDataBinding().setVariable(getBindingVariables(), getViewModel());
        getViewDataBinding().setLifecycleOwner(this);
        getViewDataBinding().executePendingBindings();
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    public View getRootView() {
        return rootView;
    }

    public V getViewModel() {
        return viewModel;
    }

    public abstract int getBindingVariables();

    public abstract V initViewModel();

    public abstract int getLayoutId();

    public T getViewDataBinding() {
        return viewDataBinding;
    }
}
