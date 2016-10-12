package com.store.storeapps.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.store.storeapps.R;
import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Payumoney extends Activity {


	private static final String TAG = "MainActivity";
	WebView webviewPayment;
	//AppController globalVariable;
	String spinner_item,Intentcost,otpmob,pmcash,amount,Codcash,coddisable,amountPayable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//globalVariable = (AppController) getApplicationContext();
		//		getLayoutInflater().inflate(R.layout.payu, frameLayout);
		setContentView(R.layout.payu);
		if(!isNetworkAvailable1()){
			//setContentView(R.layout.nonetwork);
		}
//		spinner_item =getIntent().getStringExtra("spiner");
//		Intentcost =getIntent().getStringExtra("cost");
//		otpmob= getIntent().getStringExtra("otpmob");
//		pmcash =getIntent().getStringExtra("Pmprice");
//		amount=getIntent().getStringExtra("Amount");
//		Codcash =getIntent().getStringExtra("CodCash");
//		coddisable = getIntent().getStringExtra("coddisable");
		amountPayable =getIntent().getStringExtra("Promo");

		webviewPayment = (WebView) findViewById(R.id.webview);
		webviewPayment.getSettings().setJavaScriptEnabled(true);
		webviewPayment.getSettings().setDomStorageEnabled(true);
		webviewPayment.getSettings().setLoadWithOverviewMode(true);
		webviewPayment.getSettings().setUseWideViewPort(true);
		//webviewPayment.loadUrl("http://www.google.com");
		/*webviewPayment
				.loadUrl("128.199.193.113/rakhi/payment/endpoint?order_id=aAbBcC45&amount=10");*/

		//	webviewPayment.loadUrl("http://timesofindia.com/");
		StringBuilder url_s = new StringBuilder();
		//http://merirakhi.com/processor/payment/endpoint?order_id=aAbBcC&amount=10&currency=USD
		url_s.append("https://secure.payu.in/_payment");

		Log.e(TAG, "call url " + url_s);

		//webviewPayment.loadUrl(url_s.toString());
		//String postData = "username=my_username&password=my_password";
		webviewPayment.postUrl(url_s.toString(),EncodingUtils.getBytes(getPostString(), "utf-8"));

		//	webviewPayment.loadUrl("http://128.199.193.113/rakhi/payment/endpoint?order_id=aAbBcC45&amount=0.10&currency=USD");

		webviewPayment.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				if(url.indexOf("/payusuccess.php")!=-1){

					String status = "PayU Transaction Successful!";
					String P_Type = "PayU";
					//globalVariable.setPaytype(P_Type);
					Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
					intent.putExtra("transStatus", status);
					startActivity(intent);
					//webviewPayment.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
				}
			}

			@SuppressWarnings("unused")
			public void onReceivedSslError(WebView view) {
				Log.e("Error", "Exception caught!");
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}



		});


	}
	private boolean isNetworkAvailable1() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//		int id = item.getItemId();
		//		if (id == R.id.action_settings) {
		//			return true;
		//		}
		return super.onOptionsItemSelected(item);
	}

	private String getPostString()
	{
		String key  = "rX3zfA";
		String salt  = "f6kkMCOH";
//		String txnid = globalVariable.getOrderid().toString();
//		String amount = globalVariable.getAmount().toString();
//		String firstname = globalVariable.getName().toString();
//		String email = globalVariable.getEmailid().toString();
//		String phone = globalVariable.getBmobile().toString();
//		String productInfo = globalVariable.getProname().toString();
//		String UID = globalVariable.getUserid().toString();
//		String P_Type = "PayuMoney";
//		////		System.out.println("USER ID  "+UID);
//		String Ordders =globalVariable.getOrderid().toString();
//		String name =globalVariable.getName().toString();
		//		StringBuilder post = new StringBuilder();
		//		params.add(new BasicNameValuePair("Orderid", Ordders));
		//		params.add(new BasicNameValuePair("U_id", UID));
		//		params.add(new BasicNameValuePair("P_Type", "3PMstore Cash"));
		//		params.add(new BasicNameValuePair("3pmcashused", amountPayable));
		//		params.add(new BasicNameValuePair("name", name));
		//		System.out.println("Name "+name);
		//		System.out.println("3pmcashused "+pmcash);
		//		post.append("UID=");
		//		post.append(UID);
		//		post.append("Ordders=");
		//		post.append(Ordders);
		//		post.append("name=");
		//		post.append(name);
		//		post.append("P_Type=");
		//		post.append(P_Type);
		StringBuilder post = new StringBuilder();
		post.append("Ordders=");
//		post.append(Ordders);
//		post.append("&");
//		post.append("P_Type=");
//		post.append(P_Type);
//		post.append("&");
//		post.append("UID=");
//		post.append(UID);
//		post.append("&");
//		post.append("name=");
//		post.append(name);
//		post.append("&");
//		post.append("key=");
//		post.append(key);
//		post.append("&");
//		post.append("txnid=");
//		post.append(txnid);
//		post.append("&");
//		post.append("amount=");
//		post.append(amount);
//		post.append("&");
//		post.append("productinfo=");
//		post.append(productInfo);
//		post.append("&");
//		post.append("firstname=");
//		post.append(firstname);
//		post.append("&");
//		post.append("email=");
//		post.append(email);
//		post.append("&");
//		post.append("phone=");
//		post.append(phone);
		post.append("&");
		post.append("surl=");
		post.append("http://www.3pmstore.com/android/android_connect/payusuccess.php");
		post.append("&");
		post.append("furl=");
		post.append("https://www.3pmstore.com/transactionfailure.php");
		post.append("&");

		StringBuilder checkSumStr = new StringBuilder();
		/* =sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||salt) */
		MessageDigest digest=null;
		String hash;
		try {
			digest = MessageDigest.getInstance("SHA-512");// MessageDigest.getInstance("SHA-256");

			checkSumStr.append(key);
			checkSumStr.append("|");
//			checkSumStr.append(txnid);
//			checkSumStr.append("|");
//			checkSumStr.append(amount);
//			checkSumStr.append("|");
//			checkSumStr.append(productInfo);
//			checkSumStr.append("|");
//			checkSumStr.append(firstname);
//			checkSumStr.append("|");
//			checkSumStr.append(email);
			checkSumStr.append("|||||||||||");
			checkSumStr.append(salt);

			digest.update(checkSumStr.toString().getBytes());

			hash = bytesToHexString(digest.digest());
			post.append("hash=");
			post.append(hash);
			post.append("&");
			Log.i(TAG, "SHA result is " + hash);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		post.append("service_provider=");
		post.append("payu_paisa");
		return post.toString();	
	}

	private JSONObject getProductInfo()
	{
		try {
			//create payment part object
			JSONObject productInfo = new JSONObject();

			JSONObject jsonPaymentPart = new JSONObject();
			jsonPaymentPart.put("name", "TapFood");
			jsonPaymentPart.put("description", "Lunchcombo");
			jsonPaymentPart.put("value", "500");
			jsonPaymentPart.put("isRequired", "true");
			jsonPaymentPart.put("settlementEvent", "EmailConfirmation");

			//create payment part array
			JSONArray jsonPaymentPartsArr = new JSONArray();
			jsonPaymentPartsArr.put(jsonPaymentPart);

			//paymentIdentifiers
			JSONObject jsonPaymentIdent = new JSONObject();
			jsonPaymentIdent.put("field", "CompletionDate");
			jsonPaymentIdent.put("value", "31/10/2012");

			//create payment part array
			JSONArray jsonPaymentIdentArr = new JSONArray();
			jsonPaymentIdentArr.put(jsonPaymentIdent);

			productInfo.put("paymentParts", jsonPaymentPartsArr);
			productInfo.put("paymentIdentifiers", jsonPaymentIdentArr);

			Log.e(TAG, "product Info = " + productInfo.toString());
			return productInfo;


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private static String bytesToHexString(byte[] bytes) {
		// http://stackoverflow.com/questions/332079
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
	@Override
	public void onBackPressed() {

//		Intent i=new Intent(Payumoney.this,PaymentOption.class);
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
