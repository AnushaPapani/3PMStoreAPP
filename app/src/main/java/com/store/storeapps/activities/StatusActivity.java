package com.store.storeapps.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.store.storeapps.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import android.support.v7.app.AppCompatActivity;

import com.store.storeapps.fragments.PaymentOptionNewFrgament;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.AppController;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

public class StatusActivity extends AppCompatActivity {
	private ProgressDialog pDialog;
	private WebView mWebView;
	AppController globalVariable;
	private static final String TAG_SUCCESS = "success";
	String totalcod,UID,Ordders,name,EmailID,ptype;
	String hurray ="http://www.3pmstore.com/android/android_connect/3pminvoicetoemail/hurraynotification.php";
	String status;
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_status);
		globalVariable =(AppController)getApplicationContext();
		status =getIntent().getStringExtra("transStatus");
		new Hurray().execute();
		
	}
	/**
	 * Background Async Task to Create new product
	 * */
	class Hurray extends AsyncTask<String, String, String> {
		SharedPreferences store;
		SharedPreferences.Editor editor;

		/**
		 * Before starting background thread Show Progress Dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(StatusActivity.this);
			pDialog.setMessage("Please Wait Status is loading..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		/**
		 * Creating product
		 */
		protected String doInBackground(String... args) {

			String result = null;
			UID = Utility.getSharedPrefStringData(StatusActivity.this, Constants.USER_ID);
			System.out.println("hurray USER ID  " + UID);
			Ordders = getIntent().getStringExtra("Orderid");
			System.out.println("hurray order id " + Ordders);
			name = getIntent().getStringExtra("name");
			System.out.println("hurray name " + name);

			ptype = getIntent().getStringExtra("P_Type");
			System.out.println("hurray ptype " + ptype);

			EmailID = getIntent().getStringExtra("email");
			System.out.println("hurray email " + EmailID);

			//int pmcash = globalVariable.getCashused();
			String cashused = getIntent().getStringExtra("3pmcashused");
			System.out.println("c" + cashused);

			LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
			paramsList.put("Orderid", PaymentOptionNewFrgament.finalOrderid);
			paramsList.put("U_id", UID);
			paramsList.put("3pmcashused", cashused);
			paramsList.put("P_Type", ptype);
			paramsList.put("EmailID", PaymentOptionNewFrgament.finalemail);
			paramsList.put("name", PaymentOptionNewFrgament.finalname);
			paramsList.put("cartId", HomeActivity.mCartId);
			result = Utility.httpPostRequestToServer(ApiConstants.HURRAY_NOTIFICATION, Utility.getParams(paramsList));

			return result;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 **/
		protected void onPostExecute(String response) {
			// dismiss the dialog once done

			try {
				if (response != null) {
					JSONObject jsonobject = new JSONObject(response);

					if (jsonobject != null) {
						JSONObject jObj = new JSONObject(response);
						int s = jObj.getInt("success");
						String success = jObj.getString("success");
						if (success.equals("1")) {
							if (status == "Paytm Transaction Successful!") {
								Intent i = new Intent(getApplicationContext(), SuccessActivity.class);
								i.putExtra("P_Type", "PayTM");
								i.putExtra("Orderid",Ordders);
								i.putExtra("name",name);
								i.putExtra("EmailID",EmailID);

								startActivity(i);
								finish();
							} else {
								globalVariable.setPaytype("CCAvenue");
								Intent i = new Intent(getApplicationContext(), SuccessActivity.class);
								i.putExtra("P_Type", "PayTM");
								i.putExtra("Orderid",Ordders);
								i.putExtra("name",name);
								i.putExtra("EmailID",EmailID);
								startActivity(i);
								finish();
							}
						} else {
							Intent i = new Intent(getApplicationContext(), FailureActivity.class);
							i.putExtra("P_Type", "PayTM");
							i.putExtra("Orderid",Ordders);
							i.putExtra("name",name);
							i.putExtra("EmailID",EmailID);
							startActivity(i);
							finish();
						}
					}
				}
				pDialog.dismiss();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onBackPressed() {

		Utility.navigateDashBoardFragment(new PaymentOptionNewFrgament(), PaymentOptionNewFrgament.TAG, null, StatusActivity.this);
		finish();
		//		this.finishAffinity();
	}
} 