package com.qslib.gps;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

@SuppressWarnings("ALL")
@SuppressLint("Registered")
public class GPSTracker extends Service implements LocationListener {
    private static final String TAG = GPSTracker.class.getSimpleName();
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private final Context mContext;
    protected LocationManager locationManager = null;
    private boolean canGetLocation = false;
    private Location location = null;
    private double latitude = 0;
    private double longitude = 0;

    /**
     * @param context
     */
    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    /**
     * get location of device
     *
     * @return
     */
    public Location getLocation() {
        try {
            // check permission for location with android
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: show request permission (PermissionUtils.checkPermission())
                return null;
            }

            this.locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            // getting GPS status
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                System.out.println(TAG
                        + ":: getLocation:: No network provider is enabled!");
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    System.out.println("Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        System.out.println("GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(TAG + ":: " + e.getMessage());
        }

        return location;
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     */
    public void stopUsingGPS() {
        try {
            if (locationManager != null) {
                // check permission for location with android has version >= 23
                if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.removeUpdates(GPSTracker.this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) latitude = location.getLatitude();
        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) longitude = location.getLongitude();
        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will launch Settings Options
     *
     * @param title
     * @param message
     * @param messageOpenSettingGps
     * @param messageDontAllowSettingGps
     */
    public void showSettingsAlert(String title, String message,
                                  String messageOpenSettingGps, String messageDontAllowSettingGps) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(title);
            builder.setMessage(message);
            // On pressing Settings button
            builder.setPositiveButton(messageOpenSettingGps,
                    (dialog, which) -> {
                        try {
                            dialog.dismiss();
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            mContext.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            // on pressing cancel button
            builder.setNegativeButton(messageDontAllowSettingGps, (dialog, which) -> {
                try {
                    dialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // set cancelable
            builder.setCancelable(true);
            // Showing Alert Message
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            System.out.println("TAG:: " + "Location:: getLatitude:: "
                    + location.getLatitude() + "::getLongitude "
                    + location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
