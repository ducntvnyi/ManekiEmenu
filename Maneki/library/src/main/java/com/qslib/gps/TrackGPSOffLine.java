package com.qslib.gps;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java8.util.function.Consumer;

public class TrackGPSOffLine extends Service implements LocationListener {
    private final Context mContext;
    boolean checkGPS = false;
    boolean canGetLocation = false;
    Location loc;
    double latitude;
    double longitude;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0.1f; // meter
    private static final long MIN_TIME_BW_UPDATES = 1000 * 2; // 2s
    protected LocationManager locationManager;

    private Consumer<Location> locationConsumer;

    public TrackGPSOffLine(Context mContext, Consumer<Location> locationConsumer) {
        this.mContext = mContext;
        this.locationConsumer = locationConsumer;
        getLocation();
    }

    private Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (checkGPS) {
                this.canGetLocation = true;
                if (loc == null) {
                    try {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (loc != null) {
                                latitude = loc.getLatitude();
                                longitude = loc.getLongitude();
                                if (locationConsumer != null)
                                    locationConsumer.accept(loc);
                            }
                        }
                    } catch (SecurityException e) {
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return loc;
    }

    public void stopUsingGPS() {
        try {
            if (locationManager != null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.removeUpdates(TrackGPSOffLine.this);
            }
        } catch (Exception e) {
            Log.e("", "==> Exception:: " + e.getMessage());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            loc = location;
            if (locationConsumer != null)
                locationConsumer.accept(location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
