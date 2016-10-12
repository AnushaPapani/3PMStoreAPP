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
import java.util.List;
import android.support.v7.app.AppCompatActivity;
import com.store.storeapps.utility.AppController;

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
		 * */
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
		 * */
		protected String doInBackground(String... args) {

			
			UID = globalVariable.getUserid().toString();
			System.out.println("hurray USER ID  "+UID);
			Ordders =globalVariable.getOrderid().toString();
			System.out.println("hurray order id "+Ordders);
			name =globalVariable.getName().toString();
			System.out.println("hurray name "+name);

			ptype = globalVariable.getPaytype().toString();
			System.out.println("hurray ptype "+ptype);

			EmailID =globalVariable.getEmailid().toString();
			System.out.println("hurray email "+EmailID);

			int pmcash =globalVariable.getCashused();
			String cashused = Integer.toString(pmcash);
			System.out.println("c"+cashused);
			

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("Orderid", Ordders));
			params.add(new BasicNameValuePair("U_id", UID));
			params.add(new BasicNameValuePair("P_Type", ptype));
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("3pmcashused", cashused));
			params.add(new BasicNameValuePair("EmailID", EmailID));
			System.out.println("hurrayorder"+Ordders);
			System.out.println("hurrayorder"+UID);
			System.out.println("hurrayorder"+ptype);
			System.out.println("hurrayorder"+name);
			System.out.println("hurrayorder"+cashused);
			System.out.println("hurrayorder"+EmailID);
			
			System.out.println("EMAIL FOR PAYMENT "+EmailID);
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONParser jsonpars =new JSONParser();
			JSONObject json = jsonpars.makeHttpRequest(hurray,
					"POST", params);

			// check log cat fro response
//			try {
//				Log.d("Create Response", json.toString());
//			} catch (NullPointerException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			//System.out.println("ORDER ID"+json);	
			// Writing data to SharedPreferences

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success != 1) {
					Intent i = new Intent(getApplicationContext(), FailureActivity.class);
					startActivity(i);
					finish();
				}
				else if(success == 1)
				{
					if (status == "Paytm Transaction Successful!") {
						globalVariable.setPaytype("Paytm");
						Intent i = new Intent(getApplicationContext(), SuccessActivity.class);
						startActivity(i);
						finish();
					}else {
						globalVariable.setPaytype("CCAvenue");
						Intent i = new Intent(getApplicationContext(), SuccessActivity.class);
						startActivity(i);
						finish();
					}
					
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			//			String responseBody = EntityUtils.toString(js.getEntity());

			pDialog.dismiss();
		}


	}

	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
	}
	@Override
	public void onBackPressed() {

//		Intent i=new Intent(StatusActivity.this,PaymentOption.class);
//		startActivity(i);
//		finish();
		//		this.finishAffinity();

	}
} 