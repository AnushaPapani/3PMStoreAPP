package com.three.pmstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.three.pmstore.R;

/**
 * Created by satyanarayana pdv on 10/20/2016.
 */

public class NoPrevious extends AppCompatActivity {
    WebView webView;
    public static final String TAG = "NoPreviousFragment";
    String url;
    private View rootView;
    //	private HomeActivity mParent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackorder);
        url = getIntent().getStringExtra("URL");

        webView = (WebView) findViewById(R.id.activity_main_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }
    @Override
    public void onBackPressed() {

        Intent i=new Intent(NoPrevious.this, HomeActivity.class);
        startActivity(i);
        finish();

    }


}
