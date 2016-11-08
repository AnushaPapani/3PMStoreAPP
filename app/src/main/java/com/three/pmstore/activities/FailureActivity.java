package com.three.pmstore.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.three.pmstore.R;


public class FailureActivity extends Activity {
	private WebView mWebView;
	String Paymenttype;
	String totalcod;
	Context f_context;
	String spinner_item,Intentcost,otpmob,pmcash,amount,Codcash,coddisable,amountPayable,Orderid,bmobile,Userid,Fname;
	//	AppController globalVariable;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.failure);
		if(!isNetworkAvailable1()){
			//getLayoutInflater().inflate(R.layout.nonetwork, frameLayout);
		}else {

			mWebView = (WebView) findViewById(R.id.failureactivity_main_webview);
			Button retry =(Button)findViewById(R.id.retry);
			spinner_item =getIntent().getStringExtra("spiner");
			Orderid =getIntent().getStringExtra("OrderID");
			Intentcost =getIntent().getStringExtra("TotalCost");
			otpmob= getIntent().getStringExtra("otpmob");
			pmcash =getIntent().getStringExtra("pm_Cash");
			bmobile =getIntent().getStringExtra("bmobile");
			Userid =getIntent().getStringExtra("U_ID");
			amount=getIntent().getStringExtra("Amount");
			Codcash =getIntent().getStringExtra("ADMIN_COD");
			coddisable = getIntent().getStringExtra("coddisable");	
			amountPayable =getIntent().getStringExtra("Promo");
			Fname =getIntent().getStringExtra("Name");
			// Enable Javascript
			WebSettings webSettings = mWebView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			mWebView.loadUrl("http://www.3pmstore.com/android/android_connect/transactionfailure.php");

			retry.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i=new Intent(FailureActivity.this, HomeActivity.class);
					i.putExtra("TotalCost",amountPayable);
					i.putExtra("pm_Cash",pmcash);
					i.putExtra("ADMIN_COD", Codcash);
					i.putExtra("OrderID", Orderid);
					i.putExtra("bmobile",bmobile);
					i.putExtra("U_ID",Userid);
					i.putExtra("CodCash",Codcash);
					i.putExtra("fname",Fname);
					i.putExtra("FailureActivity","FailureActivity");
					startActivity(i);
					finish();
				}
			});
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