package com.store.storeapps.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.store.storeapps.R;


public class FailureActivity extends AppCompatActivity {
	private WebView mWebView;
	String Paymenttype;
	String totalcod;
	String spinner_item,Intentcost,otpmob,pmcash,amount,Codcash,coddisable,amountPayable;
	//	AppController globalVariable;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.failure);
		if(!isNetworkAvailable1()){
			//getLayoutInflater().inflate(R.layout.nonetwork, frameLayout);
		}else {

			mWebView = (WebView) findViewById(R.id.failureactivity_main_webview);
			spinner_item =getIntent().getStringExtra("spiner");
			Intentcost =getIntent().getStringExtra("cost");
			otpmob= getIntent().getStringExtra("otpmob");
			pmcash =getIntent().getStringExtra("Pmprice");
			amount=getIntent().getStringExtra("Amount");
			Codcash =getIntent().getStringExtra("CodCash");
			coddisable = getIntent().getStringExtra("coddisable");	
			amountPayable =getIntent().getStringExtra("Promo");
			// Enable Javascript
			WebSettings webSettings = mWebView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			mWebView.loadUrl("http://www.3pmstore.com/android/android_connect/transactionfailure.php");
		}
	}
	private boolean isNetworkAvailable1() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public void onBackPressed() {

//		Intent i=new Intent(FailureActivity.this,PaymentOption.class);
//		i.putExtra("spiner", spinner_item);
//		i.putExtra("Promo",amountPayable);
//		i.putExtra("Pmprice",pmcash );
//		i.putExtra("cost", Intentcost);
//		i.putExtra("coddisable", coddisable);
//		i.putExtra("otpmob",otpmob);
//		//		i.putExtra("BalCash", currentbalance);
//		i.putExtra("Amount",amount );
//		i.putExtra("CodCash",globalVariable.getAdminCod().toString());
//		startActivity(i);
//		finish();

	}
}