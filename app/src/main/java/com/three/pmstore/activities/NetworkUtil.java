package com.three.pmstore.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by home on 10/28/2016.
 */

public class NetworkUtil {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    public static Date time;
    public static Date settime;
    public static Date time1;
    Context mContext;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }public static String emptyvalues(Context context){
        String values = null;
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            String currenttime = new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime());
//					System.out.println("CURRENT TIME FOR TIMER" +currenttime);

            calendar.add(Calendar.DAY_OF_YEAR, 1);

            Date time = dateFormat1.parse("15:00:00");
            Date settime = dateFormat1.parse("15:00:00");
            Date time1 = dateFormat1.parse(currenttime);
//            String t = time.toString();
//            String t1 = time1.toString();
//            if (t.equals(t1)) {
//
//            }
//            if (t.length() > t1.length()){
//                Utility.setSharedPrefStringData(getApplicationContext(),Constants.CARTCOUNT,"0");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    return values;
    }

}
