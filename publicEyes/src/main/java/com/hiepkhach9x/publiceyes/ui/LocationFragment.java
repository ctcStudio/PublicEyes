package com.hiepkhach9x.publiceyes.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.GetGPSLocation.FusedLocationReceiver;
import com.hiepkhach9x.base.GetGPSLocation.FusedLocationService;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.base.toolbox.PermissionGrant;
import com.hiepkhach9x.publiceyes.Constants;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.api.request.UpdateReportRequest;
import com.hiepkhach9x.publiceyes.api.response.UpdateReportResponse;
import com.hiepkhach9x.publiceyes.entities.Complaint;
import com.hiepkhach9x.publiceyes.task.FetchAddressIntentService;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;
import com.hiepkhach9x.publiceyes.ui.dialog.PostSuccessDialog;
import com.hiepkhach9x.publiceyes.view.UnderLineEditText;

import co.utilities.KeyboardUtils;

/**
 * Created by hungh on 3/4/2017.
 */

public class LocationFragment extends BaseAppFragment implements ActionbarInfo, View.OnClickListener, ResponseListener {
    private static final String TAG = "LocationFragment";
    private static final int REQUEST_POST_DIALOG = 100;
    private static final String ARGS_COMPLAINT = "args.complaint";
    private static final int REQUEST_UPDATE_REPORT = 105;

    @Override
    public String getActionbarTitle() {
        return getString(R.string.set_location);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_location;
    }

    public static LocationFragment newInstance(Complaint complaint) {

        Bundle args = new Bundle();
        args.putParcelable(ARGS_COMPLAINT, complaint);
        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView ivGetLocation;
    private UnderLineEditText etYourLocation;
    private FusedLocationService locationService;
    private AddressResultReceiver mResultReceiver;
    private Complaint complaint;
    private String district, province;
    private String location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            complaint = savedInstanceState.getParcelable(ARGS_COMPLAINT);
        } else if (getArguments() != null) {
            complaint = getArguments().getParcelable(ARGS_COMPLAINT);
        }
        mResultReceiver = new AddressResultReceiver();
        if (PermissionGrant.verify(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_PERMISSION_LOCATION)) {
            initGetLocation();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARGS_COMPLAINT, complaint);
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
                Location currentLocation = locationService.getLocation();
                location = String.valueOf(currentLocation.getLatitude() + "," + currentLocation.getLongitude());
                FetchAddressIntentService.startFetchAddressService(getContext(), currentLocation, mResultReceiver);
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
    public void onDialogResult(int requestCode, int action, Intent extraData) {
        super.onDialogResult(requestCode, action, extraData);
        if (requestCode == REQUEST_POST_DIALOG) {
            switch (action) {
                case PostSuccessDialog.ACTION_CLICK_POST_OTHER:
                    if (mNavigationManager != null) {
                        mNavigationManager.replaceAll(new HomeFragment());
                    }
                    break;
                case PostSuccessDialog.ACTION_CLICK_VIEW_IN:
                    if (mNavigationManager != null) {
                        mNavigationManager.replaceAll(MyComplaintsFragment.newInstance());
                    }
                    break;
            }
        }
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
                updateReport();
                break;
            case R.id.layout:
                if (isAdded()) {
                    KeyboardUtils.hideSoftKeyboard(getActivity());
                }
                break;
        }
    }

    private void updateReport() {
        String address = etYourLocation.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            AlertDialog alertDialog = AppAlertDialog.alertDialogOk(getContext(), "", getString(R.string.input_data_error)
                    , true, null);
            alertDialog.show();
            return;
        }
        UpdateReportRequest updateReportRequest = new UpdateReportRequest();
        complaint.setAddress(address);
        complaint.setLocation(location);
        complaint.setDistrict(district);
        complaint.setProvince(province);
        updateReportRequest.setComplaint(complaint);
        showApiLoading();
        mApi.restartRequest(REQUEST_UPDATE_REPORT, updateReportRequest, this);
    }

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        if (requestId == REQUEST_UPDATE_REPORT) {
            return new UpdateReportResponse(response);
        }
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {
        dismissApiLoading();
        if (requestId == REQUEST_UPDATE_REPORT) {
            UpdateReportResponse updateReportResponse = (UpdateReportResponse) response;
            PostSuccessDialog postSuccessDialog = PostSuccessDialog.newInstance(REQUEST_POST_DIALOG);
            postSuccessDialog.show(getChildFragmentManager(), "SuccessDialog");
        }
    }

    @Override
    public void onError(int requestId, Exception e) {
        dismissApiLoading();
        AppAlertDialog.errorApiAlertDialogOk(getContext(), e, null);
    }

    private class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver() {
            super(new Handler());
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string
            // or an error message sent from the intent service.
            if (resultCode == Constants.SUCCESS_RESULT) {
                Address address = resultData.getParcelable(Constants.RESULT_DATA_ADDRESS);
                if (address != null) {
                    province = address.getAdminArea();
                    district = address.getSubAdminArea();
                }
                String addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
                Log.d(TAG, "Address: " + addressOutput);
                etYourLocation.setText(addressOutput);
            }
        }
    }
}
