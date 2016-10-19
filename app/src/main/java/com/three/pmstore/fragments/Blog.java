package com.three.pmstore.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.three.pmstore.R;


public class Blog extends Fragment {
	WebView webView;
	public static final String TAG = "BlogFragment";
	private View rootView;
	String Aboutus="About Us";
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.blog, container, false);
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
		webView = (WebView)rootView.findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://blog.3pmstore.com/");
	}





}
