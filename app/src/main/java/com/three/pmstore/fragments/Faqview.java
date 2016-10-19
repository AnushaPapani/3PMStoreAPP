package com.three.pmstore.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;

/**
 * Created by satyanarayana pdv on 10/19/2016.
 */

public class Faqview extends Fragment {


    WebView webView;
    public static final String TAG = "Faqview";
    private View rootView;
    String url;
    private HomeActivity mParent;

//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mParent = (HomeActivity) getActivity();
//        if (getArguments() != null) {
//            url = getArguments().getString("URL");
//        }
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.faqlayout, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {

        webView = (WebView) rootView.findViewById(R.id.activity_main_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.3pmstore.com/faqapp.php");

//        webView.setWebViewClient(new WebViewClient());
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.loadUrl(url);
    }
}


