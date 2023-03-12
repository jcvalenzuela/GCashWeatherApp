package com.jcvalenzuela.gcashweatherapp.helper.provider;

import static com.jcvalenzuela.gcashweatherapp.helper.ConstantDeclarations.PERMISSION_REQUEST_LOCATION;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import timber.log.Timber;

public class GeoLocationService extends Service implements LocationListener {

    private static final String TAG = GeoLocationService.class.getSimpleName();
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    protected LocationManager locationManager;
    private Location location;
    private double latitude;
    private double longitude;
    private boolean isGpsEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean isNetworkOrLocationEnabled = false;

    public GeoLocationService(Context context) {
        initLocation(context);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    public Location initLocation(Context context) {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            Log.e(TAG, "Gps Enabled: " + isGpsEnabled + " Network Enabled: " + isNetworkEnabled);

            isNetworkOrLocationEnabled = isNetworkEnabled && isGpsEnabled;


            if (isNetworkOrLocationEnabled) {
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(context,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_LOCATION);

                    }

                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            this
                    );

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    } else {
                        Log.e(TAG, "Location Manager Null");
                    }
                }

                if (isGpsEnabled) {
                    if (ActivityCompat.checkSelfPermission(
                            context, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(
                            context, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{
                                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_LOCATION);
                    }


                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                    } else {
                        Log.e(TAG, "Location Manager Null");
                    }
                }
            }

        } catch (Exception e) {
            Timber.e(e);
        }
        return location;
    }

    public void stop() {
        if (locationManager != null) {
            locationManager.removeUpdates(GeoLocationService.this);
        }
    }

    public double getLatitude() {
        return location != null ? latitude = location.getLatitude() : latitude;
    }

    public double getLongitude() {
        return location != null ? longitude = location.getLongitude() : longitude;
    }

    public boolean isNetworkOrLocationEnabled() {
        return isNetworkOrLocationEnabled;
    }
}
