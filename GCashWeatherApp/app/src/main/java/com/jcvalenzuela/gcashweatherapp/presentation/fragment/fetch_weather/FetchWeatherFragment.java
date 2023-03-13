package com.jcvalenzuela.gcashweatherapp.presentation.fragment.fetch_weather;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcvalenzuela.gcashweatherapp.BR;
import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.List;
import com.jcvalenzuela.gcashweatherapp.databinding.FragmentFetchWeatherBinding;
import com.jcvalenzuela.gcashweatherapp.presentation.adapter.CurrentWeatherAdapter;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseFragment;
import com.jcvalenzuela.gcashweatherapp.presentation.fragment.current_weather.CurrentWeatherFragment;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.weather.WeatherViewModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class FetchWeatherFragment extends BaseFragment<FragmentFetchWeatherBinding, WeatherViewModel> implements CurrentWeatherAdapter.ItemAdapterListener {


    private static final String TAG = FetchWeatherFragment.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    CurrentWeatherAdapter currentWeatherAdapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Disposable disposable;

    public FetchWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentWeatherAdapter.setItemAdapterListener(this);
        initResult();
        fetchCacheData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
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
        return R.layout.fragment_fetch_weather;
    }

    @Override
    public void onItemClick(View view, List item) {

    }

    private void fetchCacheData() {
        disposable = Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> getViewModel().onGetForecastWeather(getViewModel().getWeatherId()), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                    }
                });
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        getViewDataBinding().fetchWeatherList.setLayoutManager(layoutManager);
        getViewDataBinding().fetchWeatherList.setAdapter(currentWeatherAdapter);
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