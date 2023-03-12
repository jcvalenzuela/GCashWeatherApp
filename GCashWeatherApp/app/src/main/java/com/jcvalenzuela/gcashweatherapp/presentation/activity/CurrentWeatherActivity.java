package com.jcvalenzuela.gcashweatherapp.presentation.activity;

import static com.jcvalenzuela.gcashweatherapp.helper.ConstantDeclarations.PERMISSION_REQUEST_LOCATION;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertDblToStr;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertToDate;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertToTime;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.jcvalenzuela.gcashweatherapp.BR;
import com.jcvalenzuela.gcashweatherapp.presentation.fragment.CurrentWeatherFragment;
import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.ForecastWeatherResponse;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.List;
import com.jcvalenzuela.gcashweatherapp.databinding.ActivityCurrentWeatherBinding;
import com.jcvalenzuela.gcashweatherapp.helper.provider.GeoLocationService;
import com.jcvalenzuela.gcashweatherapp.presentation.adapter.CurrentWeatherAdapter;
import com.jcvalenzuela.gcashweatherapp.presentation.adapter.MainWeatherAdapter;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseActivity;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.weather.WeatherViewModel;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CurrentWeatherActivity extends BaseActivity<ActivityCurrentWeatherBinding, WeatherViewModel> {

    private final String TAG = CurrentWeatherActivity.class.getSimpleName();
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    Context context;


    private AlertDialog alertDialog;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private Disposable disposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private GeoLocationService geoLocationService;
    private boolean isPermissionDenied = false;

    private CurrentWeatherFragment currentWeatherFragment = new CurrentWeatherFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDesign();
        initResult();
        requestLocationPermission();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_current_weather;
    }

    @Override
    public WeatherViewModel initViewModel() {
        return new ViewModelProvider(this, viewModelFactory).get(WeatherViewModel.class);
    }

    @Override
    public int getBindingVariables() {
        return BR.weatherViewModel;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, "grantResults: " + grantResults.length);
        Log.e(TAG, "grantResults: " + grantResults[0] + " " + grantResults[1]);

        boolean isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED;

        if (requestCode == PERMISSION_REQUEST_LOCATION && isGranted) {
            initGeoLocation();
        }

    }

    private void initDesign() {

        viewPager = getViewDataBinding().viewPager;

        final MainWeatherAdapter mainWeatherAdapter = new MainWeatherAdapter(
                getSupportFragmentManager(),
                getLifecycle()
        );

        mainWeatherAdapter.addFragment(currentWeatherFragment);
        viewPager.setAdapter(mainWeatherAdapter);

        tabLayout = getViewDataBinding().tabLayout;
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.cur_weather)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.fetch_weather)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initGeoLocation() {
        geoLocationService = new GeoLocationService(CurrentWeatherActivity.this);
        Log.e(TAG, "GeoLocation: " + geoLocationService.isNetworkOrLocationEnabled());
        if (geoLocationService.isNetworkOrLocationEnabled()) {
            getViewModel().onGetCurrentWeather(
                    convertDblToStr(geoLocationService.getLatitude()),
                    convertDblToStr(geoLocationService.getLongitude())
            );
        }
    }


    private void initResult() {
        getViewModel().getCurrentWeatherLiveData().observe(this, currentWeatherResponse -> {
            double celsius = Math.round(currentWeatherResponse.getMain().getTemp() - 273.15);
            String temp = String.format(Locale.getDefault(), "%.0f", celsius);
            String wind = "Wind\n{spd: " + currentWeatherResponse.getWind().getSpeed() + "km/hr | deg: " + currentWeatherResponse.getWind().getDeg() + "% }";
            String humidity = "Humidity: " + currentWeatherResponse.getMain().getHumidity() + "%";
            String sunInfo = "Sunrise: " + convertToTime(currentWeatherResponse.getSys().getSunrise()) + "\n\nSunset: " + convertToTime(currentWeatherResponse.getSys().getSunset());
            getViewDataBinding().textViewTemperature.setText(temp + "°C");
            getViewDataBinding().textViewCountry.setText(currentWeatherResponse.getSys().getCountry());
            getViewDataBinding().textViewCity.setText(currentWeatherResponse.getName());
            getViewDataBinding().textViewCurrentDate.setText(convertToDate(currentWeatherResponse.getDt()));
            getViewDataBinding().textViewSunInfo.setText(sunInfo);
//                    getDataBinding().textTemperature.setText(temp + "°C");
//                    getDataBinding().textCountry.setText(currentWeatherResponse.getSys().getCountry());
//                    getDataBinding().textName.setText(currentWeatherResponse.getName());
//                    getDataBinding().textHumidity.setText(humidity);
//                    getDataBinding().textRiseSet.setText(sunRiseSunSet);
//                    getDataBinding().textWindVelocity.setText(wind);
//                    getDataBinding().textDate.setText(Utils.getDate(currentWeatherResponse.getDt()));
//                    getDataBinding().textDescription.setText(currentWeatherResponse.getWeatherList().get(0).getMain());
//                    getDataBinding().iconTemp.setAnimation(Utils.getCurrentWeatherStatus(currentWeatherResponse.getWeatherList().get(0).getId(),
//                            currentWeatherResponse.getSys().getSunset()));
//                    getDataBinding().iconTemp.playAnimation();
//
//                    // set weather id
//                    getViewModel().setWeatherId(currentWeatherResponse.getId());
//
//                    // call weather forecast
            //getViewModel().onGetForecastWeather(currentWeatherResponse.getId());

            currentWeatherFragment.setWeatherForecast(currentWeatherResponse.getId());


        });

    }


    private void requestLocationPermission() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                                initGeoLocation();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                                initGeoLocation();
                            } else {
                                // No location access granted.
                            }
                        }
                );

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }


}