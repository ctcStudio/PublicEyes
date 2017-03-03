package co.ntq.dispatch.center.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import co.ntq.dispatch.center.AppLog;

/**
 * Created by HungHN on 11/11/15.
 */
public class GPSStatusReceiver extends BroadcastReceiver {

    private static final String GPS_CHANGED = "gps_changed";

    @Override
    public void onReceive(Context context, Intent intent) {
        AppLog.d("on Receive GPS");
        Intent sendToActivity = new Intent(GPS_CHANGED);
        context.sendBroadcast(sendToActivity);
    }

    public static IntentFilter getGPSIntentFilter() {
        return new IntentFilter(GPS_CHANGED);
    }
}
