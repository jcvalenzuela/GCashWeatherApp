package com.jcvalenzuela.gcashweatherapp.presentation.fragment.current_weather;

import static androidx.core.content.ContextCompat.getDrawable;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertToDate;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertToTime;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.getCurrentWeatherStatus;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.isDayTime;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.setWeatherIconByTime;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jcvalenzuela.gcashweatherapp.BR;
import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.ForecastWeatherResponse;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.List;
import com.jcvalenzuela.gcashweatherapp.databinding.FragmentCurrentWeatherBinding;
import com.jcvalenzuela.gcashweatherapp.presentation.adapter.CurrentWeatherAdapter;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseFragment;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.weather.WeatherViewModel;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CurrentWeatherFragment extends BaseFragment<FragmentCurrentWeatherBinding, WeatherViewModel> implements CurrentWeatherAdapter.ItemAdapterListener{

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
        initDesign();
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
        Log.e(TAG, "onAttach");
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

    private void initDesign() {
        currentWeatherAdapter.setItemAdapterListener(this);
        getViewDataBinding().frameCurrentWeather.setBackground(getContext().getDrawable(isDayTime()));
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
        getViewModel().getCurrentWeatherLiveData().observe(getViewLifecycleOwner(), currentWeatherResponse -> {
            double celsius = Math.round(currentWeatherResponse.getMain().getTemp() - 273.15);
            String temp = String.format(Locale.getDefault(), "%.0f", celsius) + "°C";
            String wind = "Wind {spd: " + currentWeatherResponse.getWind().getSpeed() + "km/hr | deg: " + currentWeatherResponse.getWind().getDeg() + "% }";
            String humidity = "Humidity: " + currentWeatherResponse.getMain().getHumidity() + getString(R.string.percentage);
            getViewDataBinding().imgIcon.setImageDrawable(getActivity().getDrawable(setWeatherIconByTime()
            ));
            getViewDataBinding().textViewTemperature.setText(temp);
            getViewDataBinding().textViewDescription.setText(currentWeatherResponse.getWeatherList().get(0).getDescription());
            getViewDataBinding().textViewCurrentDate.setText(convertToDate(currentWeatherResponse.getDt()));
            getViewDataBinding().textViewCountry.setText(currentWeatherResponse.getSys().getCountry());
            getViewDataBinding().textViewCity.setText(currentWeatherResponse.getName());
            getViewDataBinding().textViewSunrise.setText(convertToTime(currentWeatherResponse.getSys().getSunrise()));
            getViewDataBinding().textViewSunset.setText(convertToTime(currentWeatherResponse.getSys().getSunset()));
            getViewDataBinding().textViewWindVelocity.setText(wind);
            getViewDataBinding().textViewHumidity.setText(humidity);

        });

        getViewModel().getForecastWeatherLiveData().observe(getViewLifecycleOwner(), forecastWeatherResponse -> {
            currentWeatherAdapter.add(forecastWeatherResponse.getList());
            initRecyclerView();
        });
    }

    @Override
    public void onItemClick(View view, List item) {

    }
}