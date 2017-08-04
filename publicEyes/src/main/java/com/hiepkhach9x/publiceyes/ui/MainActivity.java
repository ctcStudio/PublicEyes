package com.hiepkhach9x.publiceyes.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hiepkhach9x.base.BaseSlidingActivity;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.base.menu.CustomSlidingMenu;
import com.hiepkhach9x.base.toolbox.PermissionGrant;
import com.hiepkhach9x.publiceyes.Config;
import com.hiepkhach9x.publiceyes.Constants;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.api.ApiConfig;
import com.hiepkhach9x.publiceyes.api.ResponseCode;
import com.hiepkhach9x.publiceyes.api.request.CreateOderGCoinRequest;
import com.hiepkhach9x.publiceyes.api.request.GetUserRequest;
import com.hiepkhach9x.publiceyes.api.response.CreateOderGCoinResponse;
import com.hiepkhach9x.publiceyes.api.response.GetUserResponse;
import com.hiepkhach9x.publiceyes.entities.OrderGCoin;
import com.hiepkhach9x.publiceyes.store.AppPref;
import com.hiepkhach9x.publiceyes.store.UserPref;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;
import com.hiepkhach9x.publiceyes.ui.dialog.ConvertPointDialog;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import co.core.dialog.OnActionInDialogListener;

public class MainActivity extends BaseSlidingActivity implements CustomSlidingMenu, View.OnClickListener,
        ResponseListener, OnActionInDialogListener {

    private static final int REQUEST_GET_USER = 101;
    private static final int REQUEST_POINT_DIALOG = 102;
    private static final int REQUEST_CONVERT_POINT = 1001;
    private SlidingMenu mSlidingMenu;
    TextView name, point;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int code = intent.getIntExtra(Constants.EXTRA_CODE, ResponseCode.UNKNOWN_ERROR);
                if (code == ResponseCode.UNAUTHORIZED) {
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    finish();
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.activity_left_menu);

        initSlidingMenu();

        if (mNavigationManager != null
                && mNavigationManager.getActivePage() == null) {
            if (AppPref.get().isFirstLogin()) {
                mNavigationManager.swapPage(new SliderFragment());
            } else {
                mNavigationManager.swapPage(new HomeFragment());
            }
        }

        PermissionGrant.verify(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_PERMISSION_LOCATION);

        registerReceiver(broadcastReceiver,new IntentFilter(Constants.EXTRA_FILTER_ERROR_REQUEST));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void getUserInfo() {
        GetUserRequest getUserRequest = new GetUserRequest();
        mApi.restartRequest(REQUEST_GET_USER,getUserRequest,this);
    }

    private void initSlidingMenu() {
        // customize the SlidingMenu
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setSlidingEnabled(false);

        findViewById(R.id.layout_complaints).setOnClickListener(this);
        findViewById(R.id.layout_faq).setOnClickListener(this);
        findViewById(R.id.layout_rate).setOnClickListener(this);
        findViewById(R.id.layout_how_to).setOnClickListener(this);
        findViewById(R.id.layout_compaign).setOnClickListener(this);
        findViewById(R.id.layout_logout).setOnClickListener(this);
        findViewById(R.id.change_money).setOnClickListener(this);

        name = (TextView) findViewById(R.id.tv_name);
        point = (TextView) findViewById(R.id.tv_point);

        name.setText(UserPref.get().getFullName());
        point.setText(String.valueOf(UserPref.get().getPoint()));

        findViewById(R.id.change_money).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserPref.get().getPoint() > Config.NUMBER_POINT_THRESHOLD) {
                    ConvertPointDialog convertPointDialog = ConvertPointDialog.newInstance(REQUEST_POINT_DIALOG);
                    convertPointDialog.show(getSupportFragmentManager(), "SuccessDialog");
                }
            }
        });
    }

    @Override
    public void onDialogResult(int requestCode, int action, Intent extraData) {
        if (requestCode == REQUEST_POINT_DIALOG) {
            switch (action) {
                case ConvertPointDialog.ACTION_CLICK_CONVERT_POINT:
                    String phone = extraData.getStringExtra(ConvertPointDialog.EXTRA_PHONE);
                    requestConvertPoint(phone);
                    break;
            }
        }
    }

    private void requestConvertPoint(String phone) {
        CreateOderGCoinRequest createOderGCoinRequest = new CreateOderGCoinRequest();
        createOderGCoinRequest.setTransRef(ApiConfig.getUnixTime());
        createOderGCoinRequest.setAmount(UserPref.get().getPoint());
        createOderGCoinRequest.setUserNophone(phone);
        createOderGCoinRequest.setCallbackData("convert_point");

        showApiLoading();
        mApi.restartRequest(REQUEST_CONVERT_POINT,createOderGCoinRequest, this);
    }

    @Override
    public int getContentFrame() {
        return R.id.content_layout;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void show() {
        if (mSlidingMenu != null) {
            mSlidingMenu.showContent(true);
        }
    }

    @Override
    public void hide() {
        if (mSlidingMenu != null) {
            mSlidingMenu.showContent(false);
        }
    }

    @Override
    public void toggle() {
        if (mSlidingMenu != null) {
            mSlidingMenu.toggle();
        }
    }

    @Override
    public void setEnableSliding(boolean enable) {
        if (mSlidingMenu != null) {
            mSlidingMenu.setSlidingEnabled(enable);
        }
    }


    @Override
    public void onClick(View view) {
        hide();
        switch (view.getId()) {
            case R.id.layout_complaints:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(MyComplaintsFragment.newInstance());
                }
                break;
            case R.id.layout_faq:
                break;
            case R.id.layout_rate:
                break;
            case R.id.layout_how_to:
                break;
            case R.id.layout_compaign:
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(CampaignFragment.newInstance());
                }
                break;
            case R.id.change_money:
                break;
            case R.id.layout_logout:
                UserPref.get().clear();
                AppPref.get().clear();
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
        }
    }

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        if (requestId == REQUEST_GET_USER) {
            return new GetUserResponse(response);
        } else if (requestId == REQUEST_CONVERT_POINT) {
            return new CreateOderGCoinResponse(response);
        }
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {
        if (requestId == REQUEST_GET_USER) {
            GetUserResponse getUserResponse = (GetUserResponse) response;
            UserPref.get().saveUserInfo(getUserResponse.getUser());

            name.setText(UserPref.get().getFullName());
            point.setText(String.valueOf(UserPref.get().getPoint()));
        } else if (requestId == REQUEST_CONVERT_POINT) {
            dismissApiLoading();
            CreateOderGCoinResponse gCoinResponse = (CreateOderGCoinResponse) response;
            OrderGCoin orderGCoin = gCoinResponse.getOrderGCoins();
            if (orderGCoin.getStatus() == ApiConfig.COIN_SUCCESS) {
                AlertDialog alertDialog = AppAlertDialog.alertDialogOk(this,"",getString(R.string.order_coin_success, orderGCoin.getAmount()),null);
                alertDialog.show();
            }
        }
    }

    @Override
    public void onError(int requestId, Exception e) {
        dismissApiLoading();
        if(requestId == REQUEST_CONVERT_POINT) {
            AlertDialog dialog = AppAlertDialog.errorApiCoinAlertDialogOk(this, e, null);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            AlertDialog dialog = AppAlertDialog.errorApiAlertDialogOk(this, e, null);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }
}
