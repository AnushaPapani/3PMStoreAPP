package com.store.storeapps.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

/**
 * Created by satyanarayana pdv on 10/18/2016.
 */

public class TrackWebView extends Fragment{
    WebView webView;
    public static final String TAG = "TrackWebView";
    private View rootView;
    String url;
    private HomeActivity mParent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();
        if (getArguments() != null) {
            url = getArguments().getString("URL");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.trackorder, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {

        webView = (WebView) rootView.findViewById(R.id.activity_main_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

//        webView.setWebViewClient(new WebViewClient());
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.loadUrl(url);
    }
}
