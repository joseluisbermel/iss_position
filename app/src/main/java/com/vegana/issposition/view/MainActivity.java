package com.vegana.issposition.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vegana.issposition.Constants;
import com.vegana.issposition.R;
import com.vegana.issposition.databinding.ActivityMainBinding;
import com.vegana.issposition.model.ResponseISS;
import com.vegana.issposition.presenter.ISSPresenter;
import com.vegana.issposition.service.Localization;
import com.vegana.issposition.view.ISSView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ISSView, PositionAdapter.ItemClickListener {
    private static final int requestCode = 1000;
    private ActivityMainBinding binding;
    private PositionAdapter adapter;
    private LocationManager locationManager;
    private Localization Local;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, requestCode);
        } else {
            locationStart();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e("MainActivity", "requestCode: " + requestCode);
        if(requestCode == MainActivity.requestCode && grantResults[0] != PackageManager.PERMISSION_GRANTED){
            showNeedPermission();
        }
    }

    @Override
    public void getNextPositions(ArrayList<ResponseISS> response) {
        binding.progressBar.setVisibility(View.GONE);
        binding.ListPositions.setVisibility(View.VISIBLE);
        binding.textAddress.setText(Constants.address);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.ListPositions.setLayoutManager(mLayoutManager);

        adapter = new PositionAdapter(response);
        adapter.setClickListener(MainActivity.this);
        binding.ListPositions.setAdapter(adapter);
        locationManager.removeUpdates(Local);
    }

    private void locationStart() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Local = new Localization(this);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.e("MainActivity", adapter.getItem(position).getDuration().toString());
        Constants.selected = adapter.getItem(position);
        Intent intent = new Intent(this, PositionActivity.class);
        startActivity(intent);
    }

    private void showNeedPermission() {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, viewGroup, false);
        Button ok = dialogView.findViewById(R.id.btn_dialog);
        ok.setOnClickListener(v -> {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, MainActivity.requestCode);
            close();
        });
        builder.setView(dialogView);

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void close() {
        alertDialog.dismiss();
    }
}