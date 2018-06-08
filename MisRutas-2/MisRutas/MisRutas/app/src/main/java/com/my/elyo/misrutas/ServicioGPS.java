package com.my.elyo.misrutas;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by elyo_ on 13/11/2016.
 */

public class ServicioGPS extends Service implements LocationListener {
    private final Context c;
    double lat, lng;
    Location l;
    boolean gps = false;
    B b;
    protected LocationManager locationManager;

    public ServicioGPS(Context context) {
        super();
        c = context;
        try {
            locationManager = (LocationManager) this.c.getSystemService(LOCATION_SERVICE);
            gps = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
            b = new B(context, "gps");
        } catch (Exception e) {
            MainActivity.t.setText(e.getMessage());
        }
        ubicar();
    }

    private void ubicar() {
        try {
            if (gps) {

                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 5000, 5f, this);
                locationManager = (LocationManager) this.c.getSystemService(LOCATION_SERVICE);
                if (locationManager != null) {
                    l = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                    if (l != null) {
                            if (lat != l.getLatitude() && lng != l.getLongitude()) {
                                lat = l.getLatitude();
                                lng = l.getLongitude();
                                String[] s = {String.valueOf(lat), String.valueOf(lng)};
                                b.insertRow(b.getWritableDatabase(), s, "lugares");
                            }
                    }
                }
            } else {
                MainActivity.t.setText("El GPS se encuentra desactivado");
            }
        } catch (Exception e) {
            MainActivity.t.setText(e.getMessage());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent i, int f, int arr) {

        return START_STICKY;
    }


    @Override
    public void onLocationChanged(Location location) {

        l = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        if(l!=null)
        {

            if(lat!=l.getLatitude() && lng!=l.getLongitude()) {
                lat = l.getLatitude();
                lng = l.getLongitude();
                String[] s = {String.valueOf(lat), String.valueOf(lng)};
                b.insertRow(b.getWritableDatabase(), s, "lugares");
            }

        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
