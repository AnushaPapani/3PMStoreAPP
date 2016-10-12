package com.store.storeapps.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.store.storeapps.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;


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
	String spinner_item,Intentcost,otpmob,pmcash,amount,Codcash,coddisable,amountPayable;
	//	AppController globalVariable;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.success);

		//		getLayoutInflater().inflate(R.layout.success, frameLayout);
		if(!isNetworkAvailable1()){
			//getLayoutInflater().inflate(R.layout.nonetwork, frameLayout);
		}else {

//			mWebView = (WebView) findViewById(R.id.successactivity_main_webview);
			
			spinner_item =getIntent().getStringExtra("spiner");
			Intentcost =getIntent().getStringExtra("cost");
			otpmob= getIntent().getStringExtra("otpmob");
			pmcash =getIntent().getStringExtra("Pmprice");
			amount=getIntent().getStringExtra("Amount");
			Codcash =getIntent().getStringExtra("CodCash");
			coddisable = getIntent().getStringExtra("coddisable");	
			amountPayable =getIntent().getStringExtra("Promo");
			
			// Enable Javascript
//			WebSettings webSettings = mWebView.getSettings();
//			webSettings.setJavaScriptEnabled(true);
			try {
				//Paymenttype = globalVariable.getPaytype();
				//totalcod = globalVariable.getFinalcodamounttotal();

				//UID = globalVariable.getUserid().toString();
				System.out.println("USER ID  "+UID);
				//Ordders =globalVariable.getOrderid().toString();
				//name =globalVariable.getName().toString();
				//EmailID  =globalVariable.getEmailid().toString();
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
				new CODSuccess().execute(totalcod,Ordders,name,EmailID);
//				mWebView.loadUrl("http://www.3pmstore.com/android/android_connect/3pminvoicetoemail/viewusers.php?totalcod="+totalcod+"&Ordders="+Ordders+"&name="+name+"&EmailID="+EmailID+"");
				//gcmnames =globalVariable.getEmailid().toString();
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
				new CashSuccess().execute(Ordders,name,EmailID);
//				mWebView.loadUrl("http://www.3pmstore.com/android/android_connect/3pminvoicetoemail/hurraynotification.php?Ordders="+Ordders+"&name="+name+"&EmailID="+EmailID+"");
				//gcmnames =globalVariable.getEmailid().toString();
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
	}

	class CashSuccess extends AsyncTask<String, Integer, Double> {
		HttpEntity entity;
		HttpResponse responses;
		String baseurl;
		@Override
		protected Double doInBackground(String... params) throws ArrayIndexOutOfBoundsException{
			// TODO Auto-generated method stub
			postData(params[0],params[1],params[2]);

			return null;
		}

		protected void onPostExecute(Double result) {
			//            pb.setVisibility(View.GONE);
			//            Toast.makeText()
		}

		protected void onProgressUpdate(Integer... progress) {
			//            pb.setProgress(progress[0]);
		}


		public void postData(String Orders, String Name, String Emailid) {
			// Create a new HttpClient and Post Header

			try {
				baseurl = "http://www.3pmstore.com/android/android_connect/3pminvoicetoemail/hurraynotification.php?";
//				baseurl ="http://3pmstore.com/android/android_connect/get_returntype.php";
				baseurl+= "&Ordders="+Orders+"&name="+Name+"&EmailID="+Emailid+"";

				System.out.println("cashsucess" + baseurl);
				//                uri = Uri.parse(out);

				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(baseurl);
				responses = httpClient.execute(httpGet);
				entity = responses.getEntity();
				//                String responseStr = EntityUtils.toString(entity);
				String content = EntityUtils.toString(entity);
				System.out.println("cashresponse"+ content);
//				AppEventsLogger logger = AppEventsLogger.newLogger(SuccessActivity.this);
//				Long changetotal = Long.parseLong(codtotal);
//				logger.logPurchase(BigDecimal.valueOf(changetotal), Currency.getInstance("INR"));



//				JSONObject myObject = new JSONObject(content);
//				JSONArray articles = myObject.getJSONArray("tbl_returntype");
//				String data =articles.getJSONObject(0).getString("Status");
//				TextView textView34 =(TextView)findViewById(R.id.textView34);
//				textView34.setText(data);
//				String cost =myObject.getString("Status");
//				System.out.println("Status"+data);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e){
				e.printStackTrace();
			} catch (RuntimeException e){
				e.printStackTrace();
			}


		}
	}

	class CODSuccess extends AsyncTask<String, Integer, Double> {
		HttpEntity entity;
		HttpResponse responses;
		String baseurl;
		@Override
		protected Double doInBackground(String... params) throws ArrayIndexOutOfBoundsException{
			// TODO Auto-generated method stub
			postData(params[0],params[1],params[2],params[3]);
			return null;
		}

		protected void onPostExecute(Double result) {
			//            pb.setVisibility(View.GONE);
			//            Toast.makeText()
		}

		protected void onProgressUpdate(Integer... progress) {
			//            pb.setProgress(progress[0]);
		}


		public void postData(String Totalcod, String Orders, String Name, String Emailid) {
			// Create a new HttpClient and Post Header


			//            String baseurl="http://166.62.81.118:18080/SpringRestCrud/signup/createuser/"+fname+;
			String exurl="http://166.62.81.118:18080/SpringRestCrud/signup/createuser/Baburao/12345/Baburao/rao/M/Android%20developer/ACTIVE/baburao4790@gmail.com/7799591404/hi/btech/india/hyd";
			System.out.println("*********"+exurl);
			try {
				baseurl = "http://www.3pmstore.com/android/android_connect/3pminvoicetoemail/hurraynotification.php?";
				baseurl+= "totalcod="+Totalcod+"&Ordders="+Orders+"&name="+Name+"&EmailID="+Emailid+"";

				System.out.println("cod" + baseurl);
				//                uri = Uri.parse(out);

				System.out.println("codsucess" + baseurl);
				//                uri = Uri.parse(out);
				/*RESPONSE*/
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(baseurl);
				responses = httpClient.execute(httpGet);
				entity = responses.getEntity();
				String content = EntityUtils.toString(entity);
				System.out.println("sucesresponse"+ content);
				JSONObject myObject = new JSONObject(content);
				String codtotal=myObject.getString("grandtotal");
				System.out.println("totalcost"+ codtotal);
				AppEventsLogger logger = AppEventsLogger.newLogger(SuccessActivity.this);
				int changetotal = Integer.parseInt(codtotal);
				logger.logPurchase(BigDecimal.valueOf(changetotal), Currency.getInstance("INR"));

				//TextView cost =(TextView)findViewById(R.id.textView37);
				//RelativeLayout codlayout = (RelativeLayout) findViewById(R.id.codlayout);
				//if (codlayout.getVisibility() == View.GONE){
					//codlayout.setVisibility(View.VISIBLE);
					//cost.setText(codtotal);

			//	}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e){
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (RuntimeException e){
			e.printStackTrace();
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

		//Intent i=new Intent(SuccessActivity.this,MyOrder.class);
		//startActivity(i);
		//finish();

	}


	
}