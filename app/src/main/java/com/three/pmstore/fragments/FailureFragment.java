package com.three.pmstore.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;


/*
Created by Anusha
 */
public class FailureFragment extends Fragment {
    public static final String TAG = "FailureFragment";
    private View rootView;
    Button retry;
    WebView webView;
    private HomeActivity mParent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent = (HomeActivity) getActivity();
        rootView = inflater.inflate(R.layout.failure, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        FacebookSdk.sdkInitialize(getActivity());
        AppEventsLogger.activateApp(getActivity());
        //		setContentView(R.layout.secs);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        AppEventsLogger logger = AppEventsLogger.newLogger(getActivity());
        logger.logEvent("pageview");
//        webView = (WebView)rootView.findViewById(R.id.failureactivity_main_webview);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("http://www.3pmstore.com/android/android_connect/transactionfailure.php");
        retry =(Button)rootView.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParent.onBackPressed();
            }
        });
    }

}
