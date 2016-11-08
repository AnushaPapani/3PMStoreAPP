package com.three.pmstore.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static com.three.pmstore.activities.NetworkUtil.time;
import static com.three.pmstore.activities.NetworkUtil.time1;

/**
 * Created by home on 10/28/2016.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);

        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        if (status.equals("Wifi enabled")){

        }
        String t = time.toString();
        String t1 = time1.toString();
        if (t.equals(t1)) {
            Toast.makeText(context, "RESET::::::::::::::", Toast.LENGTH_LONG).show();
        }
//        if (t.length() > t1.length()){
//            Utility.setSharedPrefStringData(getApplicationContext(), Constants.CARTCOUNT,"0");
//        }
    }
}
