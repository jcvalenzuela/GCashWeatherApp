package com.jcvalenzuela.gcashweatherapp.presentation.activity;

import static com.jcvalenzuela.gcashweatherapp.helper.ConstantDeclarations.PERMISSION_REQUEST_LOCATION;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertDblToStr;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertToDate;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertToTime;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jcvalenzuela.gcashweatherapp.BR;
import com.jcvalenzuela.gcashweatherapp.presentation.fragment.current_weather.CurrentWeatherFragment;
import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.databinding.ActivityCurrentWeatherBinding;
import com.jcvalenzuela.gcashweatherapp.helper.provider.GeoLocationService;
import com.jcvalenzuela.gcashweatherapp.presentation.adapter.ViewPagerAdapter;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseActivity;
import com.jcvalenzuela.gcashweatherapp.presentation.fragment.fetch_weather.FetchWeatherFragment;
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
        } else {
            //Add error dialog
        }

    }

    private void initDesign() {

        viewPager = getViewDataBinding().viewPager;

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                getLifecycle()
        );

        viewPagerAdapter.addFragment(new CurrentWeatherFragment());
        viewPagerAdapter.addFragment(new FetchWeatherFragment());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = getViewDataBinding().tabLayout;
        String[] stringArrayTabItems = {getString(R.string.cur_weather), getString(R.string.fetch_weather)};
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.cur_weather)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.fetch_weather)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tabLayout.addTab(tabLayout.newTab().setText(stringArrayTabItems[position]));
        }).attach();

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
            getViewDataBinding().textViewSunrise.setText(convertToTime(currentWeatherResponse.getSys().getSunrise()));
            getViewDataBinding().textViewSunset.setText(convertToTime(currentWeatherResponse.getSys().getSunset()));

            getViewModel().onGetForecastWeather(currentWeatherResponse.getId());

            getViewModel().setWeatherId(currentWeatherResponse.getId());

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