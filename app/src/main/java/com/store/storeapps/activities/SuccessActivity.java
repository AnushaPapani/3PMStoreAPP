package com.store.storeapps.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.store.storeapps.R;
import com.store.storeapps.fragments.MyOrderFragment;
import com.store.storeapps.fragments.PaymentOptionNewFrgament;
import com.store.storeapps.fragments.Previous_ProductsFragment;
import com.store.storeapps.fragments.ReviewOrderFragment;

import org.json.JSONArray;

import java.io.IOException;


/**
 * Created by varma on 9/21/2015.
 */
public class SuccessActivity extends AppCompatActivity {

	private WebView mWebView;
	RequestParams params = new RequestParams();
	String gcmnames;
	GoogleCloudMessaging gcmObj;
	Context applicationContext;
	JSONArray jsonarray;
	String regId = "";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	AsyncTask<Void, Void, String> createRegIdTask;
	public static final String REG_ID = "regId";
	String Paymenttype;
	String totalcod,UID,Ordders,name,EmailID;
	TextView cost;
	RelativeLayout codlayout;
	String spinner_item,Intentcost,otpmob,pmcash,amount,Codcash,coddisable,amountPayable;
	//	AppController globalVariable;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.success);

		mWebView = (WebView) findViewById(R.id.successactivity_main_webview);
		spinner_item =getIntent().getStringExtra("spiner");
		Intentcost =getIntent().getStringExtra("amounttotal");
		otpmob= getIntent().getStringExtra("bmobile");
		pmcash =getIntent().getStringExtra("Pmprice");
		amount=getIntent().getStringExtra("Amount");
		Codcash =getIntent().getStringExtra("CodCash");
		coddisable = getIntent().getStringExtra("coddisable");
		amountPayable =getIntent().getStringExtra("Promo");

		Paymenttype = getIntent().getStringExtra("P_Type");
		totalcod = getIntent().getStringExtra("CodCash");
		UID= getIntent().getStringExtra("U_id");
		name    =getIntent().getStringExtra("name");
		EmailID  = PaymentOptionNewFrgament.finalemail;
		name = PaymentOptionNewFrgament.finalname;
		Ordders= PaymentOptionNewFrgament.finalOrderid;

		System.out.println("spinner_item  check"+spinner_item+ "   " +pmcash);

		// Enable Javascript
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		try {

//                    Paymenttype = globalVariable.getPaytype();
//                    totalcod = globalVariable.getFinalcodamounttotal();
//                    UID = globalVariable.getUserid().toString();
//                    Ordders =globalVariable.getOrderid().toString();
//                    name    =globalVariable.getName().toString();
//                    EmailID  =globalVariable.getEmailid().toString();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("COD GET Name "+name);
		System.out.println("COD GET ORDERID "+Ordders);
		System.out.println("COD GET EMAIL "+EmailID);
		System.out.println("COD GET Payment "+Paymenttype);
		if(Paymenttype.equals("COD"))
		{
            HomeActivity.mCartTotal = 0;
			HomeActivity.mCartId = "";
			HomeActivity.mCartItemsList = null;
			ReviewOrderFragment.isPromoCodeApplied = false;
			String newUrl = ("http://8daysaweek.in/3productsaday/3PMstoreApp/3PMstore5189062/3pminvoicetoemail/codgcm.php?" +
					"totalcod="+totalcod+"&Ordders="+Ordders+"&name="+name+"&EmailID="+EmailID+"").replace(" ","%20");
			mWebView.loadUrl(newUrl);
//                    gcmnames =globalVariable.getEmailid().toString();
			if (!TextUtils.isEmpty(gcmnames) && GCMUtility.validate(gcmnames)) {
				// Check if Google Play Service is installed in Device
				// Play services is needed to handle GCM stuffs
				if (checkPlayServices()) {

					// Register Device in GCM Server
					registerInBackground(gcmnames);
				}
			}
		}
		else
		{
			HomeActivity.mCartTotal = 0;
			HomeActivity.mCartId = "";
			HomeActivity.mCartItemsList = null;
			ReviewOrderFragment.isPromoCodeApplied = false;
			String url= ("http://8daysaweek.in/3productsaday/3PMstoreApp/3PMstore5189062/3pminvoicetoemail/hurraygcm.php?" +
					"Ordders="+Ordders+"&name="+name+"&EmailID="+EmailID+"").replace(" ","%20");
			mWebView.loadUrl(url);
//                    gcmnames =globalVariable.getEmailid().toString();
			if (!TextUtils.isEmpty(gcmnames) && GCMUtility.validate(gcmnames)) {
				// Check if Google Play Service is installed in Device
				// Play services is needed to handle GCM stuffs
				if (checkPlayServices()) {

					// Register Device in GCM Server
					registerInBackground(gcmnames);
				}
			}
		}
	}

	private boolean isNetworkAvailable1() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/*GCM REGISTER*/
	// AsyncTask to register Device in GCM Server
	private void registerInBackground(final String emailID) {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcmObj == null) {
						gcmObj = GoogleCloudMessaging.getInstance(applicationContext);
					}
					regId = gcmObj
							.register(GCMApplicationConstants.GOOGLE_PROJ_ID);
					msg = "Registration ID :" + regId;

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}catch (NullPointerException e) {
					msg = "Error :" + e.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				if (!TextUtils.isEmpty(regId)) {
					storeRegIdinSharedPref(applicationContext, regId, emailID);
					//					Toast.makeText(
					//							applicationContext,
					//							"Registered with GCM Server successfully.\n\n"
					//									+ msg, Toast.LENGTH_SHORT).show();
				} else {
					//					Toast.makeText(
					//							applicationContext,
					//							"Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
					//									+ msg, Toast.LENGTH_LONG).show();
				}
			}
		}.execute(null, null, null);
	}

	// Store RegId and Email entered by User in SharedPref
	private void storeRegIdinSharedPref(Context context, String regId,
										String gcmname) {
		SharedPreferences prefs = getSharedPreferences("UserDetails",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(REG_ID, regId);
		editor.putString(gcmnames, gcmname);
		editor.commit();
		storeRegIdinServer(regId, gcmname);

	}

	// Share RegID and Email ID with GCM Server Application (Php)
	private void storeRegIdinServer(String regId2, String gcmname) {
		//		prgDialog.show();
		params.put("emailId", gcmname);
		params.put("regId", regId);
		System.out.println("Email id = " + gcmname + " Reg Id = " + regId);
		// Make RESTful webservice call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(GCMApplicationConstants.APP_SERVER_URL, params,
				new AsyncHttpResponseHandler(){
					// When the response returned by REST has Http
					// response code '200'
					@Override
					public void onSuccess(String response) {
						// Hide Progress Dialog
						//				prgDialog.hide();
						//				if (prgDialog != null) {
						//					prgDialog.dismiss();
						//				}
						//				Toast.makeText(applicationContext,
						//						"Reg Id shared successfully with Web App ",
						//						Toast.LENGTH_LONG).show();
						//				Intent i = new Intent(applicationContext,
						//						GreetingActivity.class);
						//				i.putExtra("regId", regId);
						//				startActivity(i);
						//				finish();
					}

					// When the response returned by REST has Http
					// response code other than '200' such as '404',
					// '500' or '403' etc
					@Override
					public void onFailure(int statusCode, Throwable error,
										  String content) {
						// Hide Progress Dialog
						//				prgDialog.hide();
						//				if (prgDialog != null) {
						//					prgDialog.dismiss();
						//				}
						// When Http response code is '404'
						if (statusCode == 404) {
							//					Toast.makeText(applicationContext,
							//							"Requested resource not found",
							//							Toast.LENGTH_LONG).show();
						}
						// When Http response code is '500'
						else if (statusCode == 500) {
							//					Toast.makeText(applicationContext,
							//							"Something went wrong at server end",
							//							Toast.LENGTH_LONG).show();
						}
						// When Http response code other than 404, 500
						else {
							//					Toast.makeText(
							//							applicationContext,
							//							"Unexpected Error occcured! [Most common Error: Device might "
							//									+ "not be connected to Internet or remote server is not up and running], check for other errors as well",
							//									Toast.LENGTH_LONG).show();
						}
					}
				});
	}

	// Check if Google Playservices is installed in Device or not
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		// When Play services not found in device
		try {
			if (resultCode != ConnectionResult.SUCCESS) {
				if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
					// Show Error dialog to install Play services
					GooglePlayServicesUtil.getErrorDialog(resultCode, this,
							PLAY_SERVICES_RESOLUTION_REQUEST).show();
				} else {
					//					Toast.makeText(
					//							applicationContext,
					//							"This device doesn't support Play services, App will not work normally",
					//							Toast.LENGTH_LONG).show();
					finish();
				}
				return false;
			} else {
				//				Toast.makeText(
				//						applicationContext,
				//						"This device supports Play services, App will work normally",
				//						Toast.LENGTH_LONG).show();
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// TODO: handle exception
		}catch (RuntimeException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return true;
	}

	// When Application is resumed, check for Play services support to make sure
	// app will be running normally
	@Override
	protected void onResume() {
		super.onResume();
		checkPlayServices();
	}

	@Override
	public void onBackPressed() {

		Intent i=new Intent(SuccessActivity.this, HomeActivity.class);
		i.putExtra("MyOrderFragment","MyOrderFragment");
		startActivity(i);
		finish();

	}

//	@Override
//	public void onBackPressed() {
//		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//			FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
//					.getBackStackEntryAt(
//							getSupportFragmentManager()
//									.getBackStackEntryCount() - 1);
//			String tagName = backEntry.getName();
//			if (tagName.equals(MyOrderFragment.TAG)) {
//				finishAffinity();
//			}
//		}
//	}


}

