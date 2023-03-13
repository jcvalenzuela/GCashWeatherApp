package com.jcvalenzuela.gcashweatherapp.presentation.fragment.current_weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jcvalenzuela.gcashweatherapp.BR;
import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.List;
import com.jcvalenzuela.gcashweatherapp.databinding.FragmentCurrentWeatherBinding;
import com.jcvalenzuela.gcashweatherapp.presentation.adapter.CurrentWeatherAdapter;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseFragment;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.weather.WeatherViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CurrentWeatherFragment extends BaseFragment<FragmentCurrentWeatherBinding, WeatherViewModel> implements CurrentWeatherAdapter.ItemAdapterListener {

    private static final String TAG = CurrentWeatherFragment.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    CurrentWeatherAdapter currentWeatherAdapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Disposable disposable;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentWeatherAdapter.setItemAdapterListener(this);
        initResult();

        Log.e(TAG, "onViewCreated");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.e(TAG, "onCreateView");

        // Inflate the layout for this fragment
        if (getArguments() != null) {
            getViewDataBinding().setWeatherViewModel(getViewModel());
            Log.e(TAG, "onCreateView");
        }

        return getRootView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG,"onAttach");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public int getBindingVariables() {
        return BR.weatherViewModel;
    }

    @Override
    public WeatherViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), viewModelFactory).get(WeatherViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_current_weather;
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        getViewDataBinding().weatherList.setLayoutManager(layoutManager);
        getViewDataBinding().weatherList.setAdapter(currentWeatherAdapter);
    }

    @Override
    public void onItemClick(View view, List item) {

    }

    private void initResult() {
        Log.e(TAG, "initResult");
        getViewModel().getForecastWeatherLiveData().observe(getViewLifecycleOwner(), forecastWeatherResponse -> {
            Log.e(TAG, new Gson().toJson(forecastWeatherResponse));
            currentWeatherAdapter.add(forecastWeatherResponse.getList());
            initRecyclerView();

        });
    }
}