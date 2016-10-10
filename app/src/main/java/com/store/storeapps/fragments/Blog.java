package com.store.storeapps.fragments;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.store.storeapps.R;


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

		webView = (WebView)rootView.findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://blog.3pmstore.com/");
	}





}
