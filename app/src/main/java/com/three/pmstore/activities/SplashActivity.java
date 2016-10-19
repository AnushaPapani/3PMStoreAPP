package com.three.pmstore.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import com.three.pmstore.R;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.customviews.DialogClass;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class SplashActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        Handler mSplashHandler = new Handler();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                PackageInfo pInfo = null;
                try {
                    pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    String versionName = pInfo.versionName;
                    int versionCode = pInfo.versionCode;
                    String android_id = Settings.Secure.getString(context.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    if (!Utility.getSharedPrefStringData(context, Constants.VERSION_CODE).equalsIgnoreCase(String.valueOf(versionCode))) {
                        updateVersion(versionName, String.valueOf(versionCode), android_id);
                    } else {
                        Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        mSplashHandler.postDelayed(action, Constants.SPLASH_TIME_OUT);
    }

    private void updateVersion(String mVersionName, String mVersionCode, String mDeviceId) {
        if (Utility.isNetworkAvailable(this)) {
            new UpdateVersiontAsyncTask(mVersionName, mVersionCode, mDeviceId).execute();
        } else {
            DialogClass.createDAlertDialog(this, "The Internet connection appears to be offline.");
        }
    }


    class UpdateVersiontAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String mVersionName;
        private String mVersionCode;
        private String mDeviceId;

        public UpdateVersiontAsyncTask(String mVersionName, String mVersionCode, String mDeviceId) {
            mCustomProgressDialog = new CustomProgressDialog(SplashActivity.this);
            this.mVersionName = mVersionName;
            this.mVersionCode = mVersionCode;
            this.mDeviceId = mDeviceId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(SplashActivity.this, R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("appVersionName", mVersionName);
                paramsList.put("appVersion", mVersionCode);
                paramsList.put("DeviceID", mDeviceId);
                result = Utility.httpPostRequestToServer(ApiConstants.CAPTURE_USER_APP_VERSION, Utility.getParams(paramsList));
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject != null) {
                        Utility.setSharedPrefStringData(context, Constants.VERSION_CODE, mVersionCode);
                        Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
