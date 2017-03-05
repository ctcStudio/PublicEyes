package com.hiepkhach9x.publiceyes.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.GetGPSLocation.FusedLocationReceiver;
import com.hiepkhach9x.base.GetGPSLocation.FusedLocationService;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.toolbox.PermissionGrant;
import com.hiepkhach9x.publiceyes.Constants;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.task.FetchAddressIntentService;
import com.hiepkhach9x.publiceyes.view.UnderLineEditText;

import co.utilities.KeyboardUtils;

/**
 * Created by hungh on 3/4/2017.
 */

public class LocationFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener {
    private static final String TAG = "LocationFragment";

    @Override
    public String getActionbarTitle() {
        return "Set Location";
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_location;
    }

    private ImageView ivGetLocation;
    private UnderLineEditText etYourLocation;
    private FusedLocationService locationService;
    private AddressResultReceiver mResultReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResultReceiver = new AddressResultReceiver();
        if (PermissionGrant.verify(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_PERMISSION_LOCATION)) {
            initGetLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_PERMISSION_LOCATION) {
            boolean grant = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    grant = false;
                }
            }

            if (grant) {
                initGetLocation();
            }
        }
    }

    private void initGetLocation() {
        locationService = new FusedLocationService(getContext(), new FusedLocationReceiver() {
            @Override
            public void onConnected() {
                locationService.startLocationUpdates();
            }

            @Override
            public void onLocationChanged() {
                FetchAddressIntentService.startFetchAddressService(getContext(), locationService.getLocation(), mResultReceiver);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationService != null)
            locationService.stopLocationUpdates();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationService != null)
            locationService.destroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivGetLocation = (ImageView) view.findViewById(R.id.get_location);
        etYourLocation = (UnderLineEditText) view.findViewById(R.id.input_location);

        ivGetLocation.setOnClickListener(this);
        view.findViewById(R.id.btn_continue).setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_location:
                if (locationService != null) {
                    locationService.startLocationUpdates();
                } else {
                    if (PermissionGrant.verify(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_PERMISSION_LOCATION)) {
                        initGetLocation();
                    }
                }
                break;
            case R.id.btn_continue:
                if (mNavigationManager != null) {
                    mNavigationManager.replaceAll(new HomeFragment());
                }
                break;
            case R.id.layout:
                if (isAdded()) {
                    KeyboardUtils.hideSoftKeyboard(getActivity());
                }
                break;
        }
    }

    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver() {
            super(new Handler());
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string
            // or an error message sent from the intent service.
            if (resultCode == Constants.SUCCESS_RESULT) {
                String addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
                Log.d(TAG, "Address: " + addressOutput);
                etYourLocation.setText(addressOutput);
            }
        }
    }
}
