package com.hiepkhach9x.base.GetGPSLocation;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.hiepkhach9x.base.toolbox.PermissionGrant;

public class FusedLocationService implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "FusedLocationService";

    private static final long INTERVAL = 1000 * 30;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final long ONE_MIN = 1000 * 60;
    private static final long REFRESH_TIME = ONE_MIN * 5;
    private static final float MINIMUM_ACCURACY = 50.0f;
    Context mContext;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location location;
    private boolean isHasGoogleApiClientConnected = false;
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;

    FusedLocationReceiver locationReceiver = null;

    public FusedLocationService(Context context, FusedLocationReceiver locationReceiver) {
        this.locationReceiver = locationReceiver;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        this.mContext = context;

        googleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();
    }

    public void destroy() {
        if (googleApiClient != null) {
            googleApiClient.unregisterConnectionCallbacks(this);
            googleApiClient.unregisterConnectionFailedListener(this);
        }

        stopLocationUpdates();

        mContext = null;
        locationRequest = null;
        googleApiClient = null;
        location = null;
        fusedLocationProviderApi = null;
        locationReceiver = null;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Google API client connected now");
        if (!PermissionGrant.checkSelfPermission(mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION})) {
            return;
        }
        Location currentLocation = fusedLocationProviderApi.getLastLocation(googleApiClient);
        if (currentLocation != null)
            Log.d(TAG, "location trong fused = " + currentLocation.getLatitude() + " " + currentLocation.getLongitude());

        if (currentLocation != null && currentLocation.getTime() > REFRESH_TIME) {
            location = currentLocation;
            if (locationReceiver != null)
                locationReceiver.onConnected();
        } else {
            PendingResult<Status> result = fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            isHasGoogleApiClientConnected = true;
            // Schedule a Thread to unregister location listeners
//            Executors.newScheduledThreadPool(1).schedule(new Runnable() {
//                @Override
//                public void run() {
//                    fusedLocationProviderApi.removeLocationUpdates(googleApiClient,
//                            FusedLocationService.this);
//                }
//            }, ONE_MIN, TimeUnit.MILLISECONDS);
        }
    }

    public void startLocationUpdates() {
        if (!PermissionGrant.checkSelfPermission(mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION})) {
            return;
        }
        if (!isHasGoogleApiClientConnected) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, this);
            isHasGoogleApiClientConnected = true;
        }
    }

    public void stopLocationUpdates() {
        if (fusedLocationProviderApi != null) {
            if (isHasGoogleApiClientConnected)
                fusedLocationProviderApi.removeLocationUpdates(googleApiClient,
                        this);
            isHasGoogleApiClientConnected = false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Location is changed!");
        //if the existing location is empty or 
        //the current location accuracy is greater than existing accuracy 
        //then store the current location 
        if (null == this.location || location.getAccuracy() < this.location.getAccuracy()) {
            this.location = location;
            //if the accuracy is not better, remove all location updates for this listener 
            if (this.location.getAccuracy() < MINIMUM_ACCURACY) {
                fusedLocationProviderApi.removeLocationUpdates(googleApiClient, this);
                isHasGoogleApiClientConnected = false;
            }
        }
        // let's inform my client class through the receiver
        if (locationReceiver != null)
            locationReceiver.onLocationChanged();
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

} 