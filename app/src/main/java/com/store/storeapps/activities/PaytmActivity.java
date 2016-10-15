package com.store.storeapps.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.store.storeapps.R;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the sample app which will make use of the PG SDK. This activity will
 * show the usage of Paytm PG SDK API's.
 **/

public class PaytmActivity extends Activity {
	String Orderid,Name,Email,TotalCost,Mobile,UID;
	String spinner_item,Intentcost,otpmob,pmcash,amount,Codcash,coddisable,amountPayable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paytm);
//		spinner_item =getIntent().getStringExtra("spiner");
//		Intentcost =getIntent().getStringExtra("cost");
//		otpmob= getIntent().getStringExtra("otpmob");
//		pmcash =getIntent().getStringExtra("Pmprice");
//		amount=getIntent().getStringExtra("Amount");
//		Codcash =getIntent().getStringExtra("CodCash");
//		coddisable = getIntent().getStringExtra("coddisable");
//		amountPayable =getIntent().getStringExtra("Promo");

		try {
			Orderid  = "";
			Name = Utility.getSharedPrefStringData(this, Constants.USER_NAME);
			Email = "";
			TotalCost = "";
			Mobile = "";
			UID = "";
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onStartTransaction();
//		initOrderId();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	//This is to refresh the order id: Only for the Sample App's purpose.
	@Override
	protected void onStart(){
		super.onStart();
//		initOrderId();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}


	/*private void initOrderId() {
		//		Random r = new Random(System.currentTimeMillis());
		//		String orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
		//				+ r.nextInt(10000);
		//		EditText orderIdEditText = (EditText) findViewById(R.id.order_id);
		//		orderIdEditText.setText(orderId);
	}*/

	public void onStartTransaction() {
		PaytmPGService Service = PaytmPGService.getProductionService();
		//		getStagingService();
		Map<String, String> paramMap = new HashMap<String, String>();

		// these are mandatory parameters

		paramMap.put("ORDER_ID", "Test123");
		paramMap.put("MID", "Mersey83050553367323");
		paramMap.put("CUST_ID", "193");
		paramMap.put("CHANNEL_ID", "WEB");
		paramMap.put("INDUSTRY_TYPE_ID", "Retail120");
		paramMap.put("WEBSITE", "Merseywap");
		paramMap.put("TXN_AMOUNT", "5");
		//				TotalCost);
		paramMap.put("THEME", "merchant");
		paramMap.put("EMAIL", "papanianu@gmail.com");
		paramMap.put("MOBILE_NO", "9848852159");
		PaytmOrder Order = new PaytmOrder(paramMap);

		PaytmMerchant Merchant = new PaytmMerchant(
				"https://3pmstore.com/android/android_connect/paytm/generateChecksum.php",
				"https://3pmstore.com/android/android_connect/paytm/verifyChecksum.php");



		Service.initialize(Order, Merchant, null);

		Service.startPaymentTransaction(this, true, true,
				new PaytmPaymentTransactionCallback() {
			@Override
			public void someUIErrorOccurred(String inErrorMessage) {
				// Some UI Error Occurred in Payment Gateway Activity.
				// // This may be due to initialization of views in
				// Payment Gateway Activity or may be due to //
				// initialization of webview. // Error Message details
				// the error occurred.
			}

			@Override
			public void onTransactionSuccess(Bundle inResponse) {
				// After successful transaction this method gets called.
				// // Response bundle contains the merchant response
				// parameters.
				Log.d("LOG", "Payment Transaction is successful " + inResponse);
				String status = "Paytm Transaction Successful!";
				String P_Type = "PayTM";
				//globalVariable.setPaytype(P_Type);
				Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
				intent.putExtra("transStatus", status);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onTransactionFailure(String inErrorMessage,
					Bundle inResponse) {
				// This method gets called if transaction failed. //
				// Here in this case transaction is completed, but with
				// a failure. // Error Message describes the reason for
				// failure. // Response bundle contains the merchant
				// response parameters.
				Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
				Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
			}

			@Override
			public void networkNotAvailable() { // If network is not
				// available, then this
				// method gets called.
			}

			@Override
			public void clientAuthenticationFailed(String inErrorMessage) {
				// This method gets called if client authentication
				// failed. // Failure may be due to following reasons //
				// 1. Server error or downtime. // 2. Server unable to
				// generate checksum or checksum response is not in
				// proper format. // 3. Server failed to authenticate
				// that client. That is value of payt_STATUS is 2. //
				// Error Message describes the reason for failure.
			}

			@Override
			public void onErrorLoadingWebPage(int iniErrorCode,
					String inErrorMessage, String inFailingUrl) {

			}

			@Override
			public void onBackPressedCancelTransaction() {
				// TODO Auto-generated method stub
//				Intent i=new Intent(PaytmActivity.this,PaymentOption.class);
//				i.putExtra("spiner", spinner_item);
//				i.putExtra("Promo",amountPayable);
//				i.putExtra("Pmprice",pmcash );
//				i.putExtra("cost", Intentcost);
//				i.putExtra("coddisable", coddisable);
//				i.putExtra("otpmob",otpmob);
//				//		i.putExtra("BalCash", currentbalance);
//				i.putExtra("Amount",amount );
//				i.putExtra("CodCash",globalVariable.getAdminCod().toString());
//				startActivity(i);
//				finish();
			}

		});
	}
}
