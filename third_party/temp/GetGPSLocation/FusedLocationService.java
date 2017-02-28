package co.ntq.dispatch.center.location;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import co.ntq.dispatch.center.AppLog;

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
    Activity locationActivity;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location location;
    private boolean isHasGoogleApiClientConnected = false;
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;

    FusedLocationReceiver locationReceiver = null;

    public FusedLocationService(Activity locationActivity, FusedLocationReceiver locationReceiver) {
        this.locationReceiver = locationReceiver;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        this.locationActivity = locationActivity;

        googleApiClient = new GoogleApiClient.Builder(locationActivity)
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

        if (fusedLocationProviderApi != null) {
            if (isHasGoogleApiClientConnected)
                fusedLocationProviderApi.removeLocationUpdates(googleApiClient,
                        this);
        }

        locationActivity = null;
        locationRequest = null;
        googleApiClient = null;
        location = null;
        fusedLocationProviderApi = null;
        locationReceiver = null;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        AppLog.i("Google API client connected now");
        Location currentLocation = fusedLocationProviderApi.getLastLocation(googleApiClient);
        if (currentLocation != null)
            AppLog.d("location trong fused = " + currentLocation.getLatitude() + " " + currentLocation.getLongitude());

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

    @Override
    public void onLocationChanged(Location location) {
        AppLog.i("Location is changed!");
        //if the existing location is empty or 
        //the current location accuracy is greater than existing accuracy 
        //then store the current location 
        if (null == this.location || location.getAccuracy() < this.location.getAccuracy()) {
            this.location = location;
// let's inform my client class through the receiver
            if (locationReceiver != null)
                locationReceiver.onLocationChanged();
            //if the accuracy is not better, remove all location updates for this listener 
            if (this.location.getAccuracy() < MINIMUM_ACCURACY) {
                fusedLocationProviderApi.removeLocationUpdates(googleApiClient, this);
                isHasGoogleApiClientConnected = false;
            }
        }
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