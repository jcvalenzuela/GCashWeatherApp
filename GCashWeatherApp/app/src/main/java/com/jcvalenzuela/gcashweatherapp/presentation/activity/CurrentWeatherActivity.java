package com.jcvalenzuela.gcashweatherapp.presentation.activity;

import static com.jcvalenzuela.gcashweatherapp.helper.ConstantDeclarations.PERMISSION_REQUEST_LOCATION;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertDblToStr;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertToDate;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.convertToTime;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.dispose;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.disposeAlertDialog;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.disposeComposite;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.getCurrentWeatherStatus;
import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.isDayTime;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jcvalenzuela.gcashweatherapp.BR;
import com.jcvalenzuela.gcashweatherapp.helper.utils.CustomAlertDialogBuilder;
import com.jcvalenzuela.gcashweatherapp.presentation.fragment.current_weather.CurrentWeatherFragment;
import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.databinding.ActivityCurrentWeatherBinding;
import com.jcvalenzuela.gcashweatherapp.helper.provider.GeoLocationService;
import com.jcvalenzuela.gcashweatherapp.presentation.adapter.ViewPagerAdapter;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseActivity;
import com.jcvalenzuela.gcashweatherapp.presentation.fragment.fetch_weather.FetchWeatherFragment;
import com.jcvalenzuela.gcashweatherapp.presentation.viewmodel.weather.WeatherViewModel;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initDesign();
        initResult();
        requestLocationPermission();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose(disposable);
        disposeComposite(compositeDisposable);
        disposeAlertDialog(alertDialog);
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

             alertDialog = CustomAlertDialogBuilder.customDialogBox(
                    this,
                    R.drawable.error_outline,
                    "Permission Denied",
                    () -> {
                        Log.e(TAG, "Request Permission");
                        requestLocationPermission();
                    });
        }

    }

    @SuppressWarnings("deprecation")
    private void initDesign() {
        getViewDataBinding().constraintBackground.setBackground(getDrawable(isDayTime()));

        viewPager = getViewDataBinding().viewPager;

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                getLifecycle()
        );

        final DotsIndicator dotsIndicator = getViewDataBinding().dotsIndicator;


       viewPagerAdapter.addFragment(new CurrentWeatherFragment());
        viewPagerAdapter.addFragment(new FetchWeatherFragment());
        viewPager.setAdapter(viewPagerAdapter);
        dotsIndicator.setViewPager2(viewPager);

//        tabLayout = getViewDataBinding().tabLayout;
//        String[] stringArrayTabItems = {getString(R.string.cur_weather), getString(R.string.fetch_weather)};
//
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//
//        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
//            Log.e(TAG,"tabPosition: " + position);
//            tab.setText(stringArrayTabItems[position]);
//        }).attach();

    }

    private void initGeoLocation() {
        geoLocationService = new GeoLocationService(CurrentWeatherActivity.this);
        Log.e(TAG, "GeoLocation: " + geoLocationService.isNetworkOrLocationEnabled());
        if (geoLocationService.isNetworkOrLocationEnabled()) {
            getViewModel().onGetCurrentWeather(
                    convertDblToStr(geoLocationService.getLatitude()),
                    convertDblToStr(geoLocationService.getLongitude())
            );
        } else {
            alertDialog = CustomAlertDialogBuilder.customDialogBox(
                    this,
                    R.drawable.error_outline,
                    "Location/Wifi/Data is OFF",
                    () -> {
                        Log.e(TAG, "Request Permission");
                        requestLocationPermission();
                    });
        }
    }


    private void initResult() {
        getViewModel().getCurrentWeatherLiveData().observe(this, currentWeatherResponse -> {
            //GetForecastWeather
            getViewModel().onGetForecastWeather(currentWeatherResponse.getId());
            //SetWeatherId
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