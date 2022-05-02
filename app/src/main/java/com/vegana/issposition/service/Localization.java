package com.vegana.issposition.service;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.vegana.issposition.Constants;
import com.vegana.issposition.presenter.ISSPresenter;
import com.vegana.issposition.view.ISSView;

import java.io.IOException;
import java.util.List;

public class Localization implements LocationListener {
    Activity activity;

    public Localization(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Constants.latitude = String.valueOf(location.getLatitude());
        Constants.longitude = String.valueOf(location.getLongitude());
        Constants.altitude = String.valueOf(location.getLatitude());

        Geocoder geocoder = new Geocoder(activity.getApplicationContext());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address address = addresses.get(0);
            Constants.address = String.format("Estás en: %s", address.getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ISSPresenter presenter = new ISSPresenter((ISSView) activity);
        presenter.getNextTenPosition();
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Log.i("Localization", "GPS Enabled");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Context context = activity.getApplicationContext();
        CharSequence text = "GPS Disabled!!, Please turn on GPS";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
