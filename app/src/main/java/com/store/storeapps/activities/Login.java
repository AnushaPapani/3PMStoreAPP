package com.store.storeapps.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.store.storeapps.R;
import com.store.storeapps.fragments.ReviewOrderFragment;
import com.store.storeapps.utility.AppController;
import com.store.storeapps.utility.Utility;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;




public class Login extends AppCompatActivity {
	//	String TAG = "Second";
	private DrawerLayout mDrawerLayout;
	//	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	Button login;
	// Your Facebook APP ID
	private static String APP_ID = "1667274583560911"; // Replace with your App ID
	public static String mCartId = HomeActivity.mCartId;
	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
	private final int SPLASH_DISPLAY_LENGTH = 9000;
	EditText inputmail;
	EditText inputpasswd;
	int countadres;
	TextView forgotpass;
	private String[] menuItemTitles;
	ListView listView;
	private ArrayAdapter<String> listAdapter ;
	AlertDialog.Builder alertDialogBuilder;
	String uids;
	String adcount;
	JSONParser jsonParser = new JSONParser();
	private static final String url_login_user = "http://8daysaweek.in/3productsaday/3PMstoreApp/3PMstore5189062/loginuser.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	Timer timer;
	String get_old_email;
	private static final String FORMAT = "%02d:%02d:%02d";
	private CountDownTimer countDownTimer; // built in android class
	private long totalTimeCountInMilliseconds; // total count down time in
	// milliseconds
	private long timeBlinkInMilliseconds; // start time of start blinking
	private boolean blink; // controls the blinking .. on and off

	EditText userInput;

	String mystring ="Sign up with 3PMstore";
	TextView Register;
	JSONArray users = null;
	JSONObject json;
	TextView pmrp,pcost,shortdesc,head,thour,tvHour,tminutes,tvMinute,tvSecond,s,info,descrip;
	//	private SessionManager session;

	TextView h,minutes,m,seconds;
	String hour,minutess,secondss;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	String mailid;
	//	EditText email;
	TextView alert;
	AppController globalVariable;
	Button resetpass;
	TextView tl1,tl2;
	ImageView buy;
	Button mfacebook;
	String name = "";
	String names = "";
	String gender = "";
	String id;
	long FBID;
	String imageURL = "";
	Bitmap profilePic;
	ImageView userImage;
	String userid;
	JSONArray Testfacebook;
	String url_buynow = "http://www.3pmstore.com/android/android_connect/insert_check_products.php";
	JSONObject profile;
	//	FacebookDbModel facebookDbModel;
	String fb_name;
	Boolean Connectiontimeout = true;
	//	private LoginButton loginBtn;
	String access_token;
	int position =5;
	Toast toast;
	View toastRoot;
	View toastRoot2;
	private TextView username;
	//	private UiLifecycleHelper uiHelper;
	Context context = Login.this;

	//	ProgressDialog prgDialog;

	RequestParams params = new RequestParams();
	GoogleCloudMessaging gcmObj;
	Context applicationContext;
	String regId = "";

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	AsyncTask<Void, Void, String> createRegIdTask;

	public static final String REG_ID = "regId";
	public static final String EMAIL_ID = "eMailId";
	EditText emailET;
	String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	String logintext ="Login";
	@Override
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		//		setContentView(R.layout.secs);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();



		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.login);

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//		if(!isNetworkAvailable1()){
//			getLayoutInflater().inflate(R.layout.nonetwork, frameLayout);
//		}
		TextView textlogin =(TextView)findViewById(R.id.logg);
		SpannableString content = new SpannableString(logintext);
		content.setSpan(new UnderlineSpan(), 0, logintext.length(), 0);
		textlogin.setText(content);

//		textCounter = (TextView)findViewById(R.id.textView3);
		tl1=(TextView)findViewById(R.id.loginNameTextViews);
		tl2=(TextView)findViewById(R.id.emails);
		head=(TextView)findViewById(R.id.heading);
		globalVariable = (AppController) getApplicationContext();

		//Timerrr
		head=(TextView)findViewById(R.id.heading);
		//		lgender =(LinearLayout)findViewById(R.id.radio);
		head.setTypeface(Typeface.createFromAsset(getAssets(),"LED.ttf"));
		head.setTextSize(20);
		thour = (TextView)findViewById(R.id.hour);
		tvHour = (TextView)findViewById(R.id.th);
		tvMinute=(TextView)findViewById(R.id.tmin);
		tminutes=(TextView)findViewById(R.id.min);
		tvSecond=(TextView)findViewById(R.id.tsec);
		s=(TextView)findViewById(R.id.seco);
		//		textCounter.setTypeface(tf);
//		timer = new Timer();

		/*GCM Integration*/
		applicationContext = getApplicationContext();
		emailET = (EditText) findViewById(R.id.editText);

		//		prgDialog = new ProgressDialog(this);
		//		// Set Progress Dialog Text
		//		prgDialog.setMessage("Please wait...");
		//		// Set Cancelable as False
		//		prgDialog.setCancelable(false);

		SharedPreferences prefs = getSharedPreferences("UserDetails",
				Context.MODE_PRIVATE);
		String registrationId = prefs.getString(REG_ID, "");

		if (!TextUtils.isEmpty(registrationId)) {
			Intent i = new Intent(applicationContext, HomeActivity.class);
			i.putExtra("regId", registrationId);
			//			startActivity(i);
			//			finish();
		}


		mfacebook =(Button)findViewById(R.id.facebooks);
		final Context context = getApplicationContext();
		// Create layout inflator object to inflate toast.xml file
		final LayoutInflater inflater = getLayoutInflater();

		// Call toast.xml file for toast layout
		toastRoot = inflater.inflate(R.layout.toast, null);
		toastRoot2 =inflater.inflate(R.layout.error_toast, null);
		toast = new Toast(context);

		// Set layout to toast

		mfacebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isNetworkAvailable(context) == true) {
					accessTokenFacebook();

				} else {
					Toast.makeText(Login.this, "No Network Connection",
							Toast.LENGTH_SHORT).show();
				}
				//				loginToFacebook();

				//				new Fblogin().execute();
				//				Intent i=new Intent(Second.this,Facebookss.class);
				//				startActivity(i);
				//								Intent i=new Intent(Second.this,Example.class);
				//								startActivity(i);
			}
		});
		try {
			if (globalVariable.getUserid().toString() ==null) {
				//				Toast.makeText(getApplicationContext(), "No Userid", 9000).show();
			}else {
				userid =globalVariable.getUserid().toString();
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//        }
		/* to get the Hash key for Facebook*/
		PackageInfo info;
		try {
			info = getPackageManager().getPackageInfo("com.store.storeapps", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md;
				md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String something = new String(Base64.encode(md.digest(), 0));
				//String something = new String(Base64.encodeBytes(md.digest()));
				Log.e("hash key *******", something);
			}
		} catch (NameNotFoundException e1) {
			Log.e("name not found", e1.toString());
		} catch (NoSuchAlgorithmException e) {
			Log.e("no such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("exception", e.toString());
		}

		inputmail = (EditText)findViewById(R.id.editText);

		inputpasswd=(EditText)findViewById(R.id.psds);

		Register=(TextView)findViewById(R.id.registerlink);
		SpannableString textcontent = new SpannableString(mystring);
		textcontent.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
		Register.setText(textcontent);
		Register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i= new Intent(getApplicationContext(),NewUserActivity.class);
//				startActivity(i);
			}
		});

		login=(Button)findViewById(R.id.logins);
		forgotpass=(TextView)findViewById(R.id.forgotpasswordlink);
		forgotpass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i= new Intent(getApplicationContext(),ForgotPassword.class);
//				startActivity(i);


			}
		});

		//		inputmail.setText(duser);
		//		inputpasswd.setText(dpasswd);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (((inputmail.getText().toString().length()<1)) && (inputpasswd.getText().toString().length()<1) )
				{
					TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
					t.setText("Please enter fields");
					toast.setView(toastRoot);
					toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.show();
				}
				else if (((inputmail.getText().toString().length()<1))){
					TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
					t.setText("Please enter Email ID");
					toast.setView(toastRoot);
					toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.show();
				}else if (!inputmail.getText().toString().matches(emailPattern)) {
					TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
					t.setText("Please enter Valid Email ID");
					toast.setView(toastRoot);
					toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.show();
				}
				else if (((inputpasswd.getText().toString().length()<1))){
					TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
					t.setText("Please enter Password");
					toast.setView(toastRoot);
					toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.show();
				}
				else
				{

					new loginUser().execute();

				}

			}
		});

		loadDrawer(mDrawerToggle, mDrawerLayout);

		setTimer();
	}

	public static boolean isNetworkAvailable(Context mContext) {

		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			Log.e("Network Testing", "***Available***");
			return true;
		}
		Log.e("Network Testing", "***Not Available***");
		return false;
	}

	@SuppressWarnings("deprecation")
	public void accessTokenFacebook() {

		mPrefs = getPreferences(MODE_PRIVATE);
		access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		// Log.e("Facebook token", access_token);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}
		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		/**
		 * Only call authorize if the access_token has expired.
		 */
		if (!facebook.isSessionValid()) {

			facebook.authorize(this, new String[] {"email"}, new DialogListener() {
				public void onComplete(Bundle values) {
					try {
						JSONObject me = new JSONObject(facebook.request("me"));
						SharedPreferences.Editor editor = mPrefs.edit();
						String id = me.getString("id");
						System.out
								.println("******facebook.getAccessToken()****"
										+ facebook.getAccessToken());
						editor.putString("userid", id);
						editor.putString("access_token",
								facebook.getAccessToken());
						editor.putLong("access_expires",
								facebook.getAccessExpires());
						editor.commit();
						new getFacebookData().execute();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				public void onFacebookError(FacebookError error) {
				}

				public void onError(DialogError e) {
				}

				public void onCancel() {
				}
			});
		} else {
			new getFacebookData().execute();
		}

	}

	public class getFacebookData extends AsyncTask<String, Void, String> {

		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(Login.this, "Please wait",
					"Loading please wait..", true);
			pd.setCancelable(true);

		}

		@Override
		protected String doInBackground(String... params) {
			fbUserProfile();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();
			// get prompts.xml view
			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.fb_dialog, null);
			alertDialogBuilder = new AlertDialog.Builder(
					context);

			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			userInput = (EditText) promptsView
					.findViewById(R.id.editTextDialogUserInput);
			if (isNetworkAvailable(context) == true) {
				if (TextUtils.isEmpty(names)) {
					LayoutInflater li1 = LayoutInflater.from(context);
					View promptsView1 = li1.inflate(R.layout.fb_dialog, null);
					alertDialogBuilder = new AlertDialog.Builder(
							context);

					// set prompts.xml to alertdialog builder
					alertDialogBuilder.setView(promptsView1);

					userInput = (EditText) promptsView1
							.findViewById(R.id.editTextDialogUserInput);
					userInput.setText(names);

					alertDialogBuilder
							.setCancelable(false)
							.setPositiveButton("Submit",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,int id) {
											get_old_email =userInput.getText().toString();
											globalVariable.setFbemail(""+get_old_email);
											//							globalVariable.setFb
											globalVariable.setFbgender(""+gender);
											globalVariable.setFbname(""+name);
											new Fblogin().execute();
										}
									});


					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();

				}
				if (!TextUtils.isEmpty(names)) {
					userInput.setText(names);
					alertDialogBuilder
							.setCancelable(false)
							.setPositiveButton("Submit",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,int id) {
											String get_email =userInput.getText().toString();
											globalVariable.setFbemail(""+get_email);
											globalVariable.setFbgender(""+gender);
											globalVariable.setFbname(""+name);
											new Fblogin().execute();
										}
									});
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();

				}
				else {
					globalVariable.setFbemail(""+names);
					globalVariable.setFbgender(""+gender);
					globalVariable.setFbname(""+name);
				}
			} else {
				TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
				t.setText("Connection Time out");
				toast.setView(toastRoot);
				toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.show();

			}
		}

	}
	public void fbUserProfile() {

		try {
			access_token = mPrefs.getString("access_token", null);
			JSONObject jsonObj = null;
			JSONObject jsonObjData = null;
			JSONObject jsonObjUrl = null;
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 50000);
			HttpConnectionParams.setSoTimeout(httpParameters, 50000);

			HttpClient client = new DefaultHttpClient(httpParameters);

			String requestURL = "https://graph.facebook.com/me?fields=picture,id,name,gender,age_range,email&access_token="
					+ access_token;
			Log.i("Request URL:", "---" + requestURL);
			System.out.print("Request URL:"+requestURL);
			HttpGet request = new HttpGet(requestURL);

			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String webServiceInfo = "";

			while ((webServiceInfo = rd.readLine()) != null) {
				Log.i("Service Response:", "---" + webServiceInfo);
				jsonObj = new JSONObject(webServiceInfo);
				jsonObjData = jsonObj.getJSONObject("picture");
				jsonObjUrl = jsonObjData.getJSONObject("data");
				FBID =jsonObj.getLong("id");
				System.out.println("facebook user id "+FBID);
				name = jsonObj.getString("name");
				names = jsonObj.getString("email");
				gender = jsonObj.getString("gender");
				imageURL = jsonObjUrl.getString("url");

				System.out.print("Request URL:"+name);
				System.out.print("Request URL:"+names);
				System.out.print("Request URL:"+gender);
				System.out.print("Request URL:"+imageURL);
				new Handler().postDelayed(new Runnable(){
					@Override
					public void run() {
						/* Create an Intent that will start the Menu-Activity. */
					}
				}, SPLASH_DISPLAY_LENGTH);
			}
			Toast.makeText(getApplicationContext(), ""+gender, Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			Connectiontimeout = true;
		}


	}



	/**
	 * Function to login into facebook
	 * */


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
		Toast.makeText(getApplicationContext(), ""+gender, Toast.LENGTH_LONG).show();
	}

	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */



	public void getProfileInformation()  {
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				String json = response;
				try {
					JSONObject profile = new JSONObject(json);
					// getting name of the user
					final String name = profile.getString("name");
					// getting email of the user
					final String email = profile.getString("email");

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							//							Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
						}

					});

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
												Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
												Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

	private boolean isNetworkAvailable1() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private void setTimer() {
		long minutesLeft = 0;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("kk:mm:ss");
			Calendar calendar = Calendar.getInstance();
			String currenttime = new SimpleDateFormat("kk:mm:ss").format(Calendar.getInstance().getTime());
			System.out.println("CURRENT TIME FOR TIMER" +currenttime);

			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date tomorrow = calendar.getTime();
			String tomorrowAsString = dateFormat.format(tomorrow);

			System.out.println(tomorrowAsString);

			try {
				String dateStart = dateFormat.format(System.currentTimeMillis());
				Date date1 = dateFormat.parse(dateStart);

				Date date2 = dateFormat.parse(tomorrowAsString);
				Date time = dateFormat1.parse("15:00:00");
				Date time1 = dateFormat1.parse(currenttime);
				if(time1.getTime() > time.getTime() || time1.getTime() == time.getTime()){
					long different = (date2.getTime()+time.getTime()) - (date1.getTime()+time1.getTime());
					long seconds = different / 1000;
					minutesLeft = seconds / 60;
					//printDifference(date1, date2, time, time1);
				}
				else{
					long different = (date2.getTime()+time.getTime()) - (date1.getTime()+time1.getTime());
					long seconds = different / 1000;
					minutesLeft = seconds / 60;
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(minutesLeft > 1440)
		{
			minutesLeft = minutesLeft - 1440 ;
		}

		totalTimeCountInMilliseconds = 60 * minutesLeft * 1000;
		timeBlinkInMilliseconds = 30 * 1000;
		startTimer();
	}

	private void startTimer() {
		countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
			// 500 means, onTick function will be called at every 500
			// milliseconds

			@Override
			public void onTick(long leftTimeInMilliseconds) {
				long seconds = leftTimeInMilliseconds / 1000;

				/*set Timer and font type to Timer Texts*/
				thour.setTypeface(Typeface.createFromAsset(getAssets(),"LED.ttf"));
				tminutes.setTypeface(Typeface.createFromAsset(getAssets(),"LED.ttf"));
				s.setTypeface(Typeface.createFromAsset(getAssets(),"LED.ttf"));
				tvHour.setTypeface(Typeface.createFromAsset(getAssets(),"LED.ttf"));
				tvMinute.setTypeface(Typeface.createFromAsset(getAssets(),"LED.ttf"));
				tvSecond.setTypeface(Typeface.createFromAsset(getAssets(),"LED.ttf"));

				AppController app= (AppController)getApplicationContext();
				long hh=app.getHour();
				long mm=app.getMin();
				long ss=app.getSec();


				thour.setText(""+hh);
				tvHour.setText("H");
				tvMinute.setText(""+mm);
				tminutes.setText("M");
				tvSecond.setText(""+ss);
				s.setText("S");
				try {
					SimpleDateFormat dateFormat1 = new SimpleDateFormat("kk:mm:ss");
					Calendar calendar = Calendar.getInstance();
					String currenttime = new SimpleDateFormat("kk:mm:ss").format(Calendar.getInstance().getTime());
//					System.out.println("CURRENT TIME FOR TIMER" +currenttime);

					calendar.add(Calendar.DAY_OF_YEAR, 1);

					Date time = dateFormat1.parse("15:00:00");
					Date time1 = dateFormat1.parse(currenttime);


					String t= time.toString();
					String t1=time1.toString();
					if(t.equals(t1))
					{
						Intent i = new Intent(Login.this,HomeActivity.class);
						startActivity(i);
						finish();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				if(hh<10)
				{
					thour.setText(""+"0"+hh);
				}
				else
				{
					thour.setText(""+hh);

				}
				if(mm<10)
				{
					tvMinute.setText(""+"0"+mm);

				}
				else
				{
					tvMinute.setText(""+mm);

				}

				if(ss<10)
				{
					tvSecond.setText(""+"0"+ss);
				}
				else
				{
					tvSecond.setText(""+ss);
				}
				thour.setTextSize(30);
				tvMinute.setTextSize(30);
				tvSecond.setTextSize(30);
				// format the textview to show the easily readable format

			}

			@Override
			public void onFinish() {
			}

		}.start();

	}

	class loginUser extends AsyncTask<String, String, String>
	{
		ProgressDialog pDialog1;
		boolean failure = false;
		int success;
		String order;
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog1 = new ProgressDialog(Login.this);
			pDialog1.setMessage("Attempting for login...");
			pDialog1.setIndeterminate(false);
			pDialog1.setCancelable(true);
			pDialog1.show();
		}

		protected String doInBackground(String... args){
			// TODO Auto-generated method stub
			// here Check for success tag
			//        	JSONParser jParser = new JSONParser();
			String email = inputmail.getText().toString();
			String password = inputpasswd.getText().toString();
			if (globalVariable.getOrderid() !=null) {
				order =globalVariable.getOrderid().toString();
			}


			//			name = name.getText().toString();

			//			fb_name = fb_name.getString("name");
			//			final String name = mfacebook.getString("name");
			String res = null;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("email", email));
				params.add(new BasicNameValuePair("password", password));
				params.add(new BasicNameValuePair("ORDERIDLOGIN",order ));
				params.add(new BasicNameValuePair("fbname", fb_name));

				Log.d("request!", "starting");
				json = jsonParser.makeHttpRequest(url_login_user, "POST", params);
				// checking log for json response
				// Getting JSON from URL
				//            JSONObject json = jParser.getJSONFromUrl(url);
				//            return json;
				try {
					Log.d("Login attempt", json.toString());
				} catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String suces = json.toString();
				System.out.println("**********"+suces);
				//				Toast.makeText(getApplicationContext(), ""+name+ ""+email, 90000).show();
				// success tag for json
				success = json.getInt(TAG_SUCCESS);
				if (success == 1)
				{
					// Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
					Log.d("Successfully Login!", json.toString());

					try {
						JSONObject jObj = new JSONObject(suces);
						uids = jObj.getString("ID");
						JSONObject user = jObj.getJSONObject("user");
						String namess = user.getString("fullname");
						String emailsss = user.getString("email");
						adcount =jObj.getString("count");
						String Paswd =user.getString("password");
						countadres=Integer.parseInt(adcount);
						System.out.println("**********"+uids);
						System.out.println("**********"+namess);
						System.out.println("**********"+emailsss);

						globalVariable.setName(namess);
						globalVariable.setEmailid(emailsss);
						globalVariable.setUserid(uids);
						System.out.println("userName :"+globalVariable.getName());
						System.out.println("userEmail :"+globalVariable.getEmailid());
						System.out.println("Uid :"+globalVariable.getUserid());

						if (globalVariable.getOrderid()!=null) {
							String ORDERID =globalVariable.getOrderid().toString();
							System.out.println("ORDERS "+ORDERID);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return json.getString(TAG_MESSAGE);
				}
				else{

					return json.getString(TAG_MESSAGE);
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			return null;
		}


		protected void onPostExecute(String message) {
			pDialog1.dismiss();
			if (message.contentEquals("Login Successfull!!!") ){

//				if(globalVariable.getUserid() != null)
//				{

						TextView t =(TextView)toastRoot2.findViewById(R.id.validtoast);
						t.setText("Login Successfull!");
						toast.setView(toastRoot2);
						toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
						toast.setDuration(Toast.LENGTH_LONG);
						toast.show();
				if (!Utility.isValueNullOrEmpty(mCartId) && (globalVariable.getUserid() != null)) {
					Login.mCartId = mCartId;
					Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, Login.this);
//					Intent i=new Intent(Login.this,ReviewOrderFragment.class);
//					startActivity(i);
				}
//						new Postdaata().execute();
//				Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, Login.this);

//				}
//				else
//				//					(globalVariable.getOrderid() == null)
//				{
//					TextView t =(TextView)toastRoot2.findViewById(R.id.validtoast);
//					t.setText("Login Successfull!");
//					toast.setView(toastRoot2);
//					toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
//					toast.setDuration(Toast.LENGTH_LONG);
//					toast.show();
//
//					Intent i=new Intent(Login.this,HomeActivity.class);
//					i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//					startActivity(i);
//					finish();
//
//				}
			}
			//	{"message":"Incorrect Credentials! ","success":0}

			//if (message.contentEquals("Incorrect Credentials!"))
			else{
				TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
				t.setText("Incorrect Credentials");
				toast.setView(toastRoot);
				toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.show();

			}
		}
	}
	class Fblogin extends AsyncTask<String, String, String>
	{
		String ordersid;
		ProgressDialog pDialog3;
		/** * Before starting background thread Show Progress Dialog * */
		boolean failure = false;
		String password;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog3 = new ProgressDialog(Login.this);
			pDialog3.setMessage("Attempting for facebook login...");
			pDialog3.show();
		}

		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			int success;

			String res = null;
			String fbmail =globalVariable.getFbemail().toString();
			String fbgender =globalVariable.getFbgender().toString();
			String fbname =globalVariable.getFbname().toString();

			System.out.println("fbmail id1"+fbmail);
			//			String fbid =globalVariable.getFbid().toString();
			//			System.out.println("IDIDIDIss  "+fbid);
			if (globalVariable.getOrderid() !=null) {
				ordersid =globalVariable.getOrderid().toString();
			}

			System.out.println("emaild fb  "+fbmail);
			System.out.println("gender fb  "+fbgender);
			System.out.println("IDIDIDI  "+ordersid);
			System.out.println("FACEBOOKddd   "+FBID);
			String faceid =String.valueOf(FBID);

			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("fbname", fbname));
				params.add(new BasicNameValuePair("fbemail", fbmail));
				params.add(new BasicNameValuePair("fbid", faceid));
				params.add(new BasicNameValuePair("fbgender", fbgender));
				params.add(new BasicNameValuePair("ORDERIDLOGIN", ordersid));
				Log.d("request!", "starting");
				json = jsonParser.makeHttpRequest(url_login_user, "POST", params);
				Log.d("Login attempt", json.toString());
				String suces = json.toString();
				System.out.println("**********"+suces);
				// success tag for json
				success = json.getInt(TAG_SUCCESS);
				if (success == 1)
				{
					Log.d("Successfully Login!", json.toString());
					if (!TextUtils.isEmpty(fbmail) && GCMUtility.validate(fbmail)) {
						// Check if Google Play Service is installed in Device
						// Play services is needed to handle GCM stuffs
						if (checkPlayServices()) {

							// Register Device in GCM Server
							registerInBackground(fbmail);
						}
					}
					// When Email is invalid
					else {
						Toast.makeText(applicationContext, "Please enter valid email",
								Toast.LENGTH_LONG).show();
					}

					try {
						JSONObject jObj = new JSONObject(suces);
						//						Long uids = jObj.getLong("fb_ID");
						String userid =jObj.getString("ID");
						String Count =jObj.getString("count");
						JSONObject user = jObj.getJSONObject("user");
						Long uids = user.getLong("fb_ID");
						String namess = user.getString("fullname");
						String emailsss = user.getString("email");
						Log.d("LoginUserid", userid);
						System.out.println("**********   UIDFB"+userid);
						System.out.println("**********"+user);
						System.out.println("**********"+namess);
						System.out.println("**********"+emailsss);


						if (globalVariable.getOrderid()!=null) {
							String ORDERID =globalVariable.getOrderid().toString();
							System.out.println("ORDERS "+ORDERID);
						}

						Intent i=new Intent(Login.this,ReviewOrderFragment.class);
						startActivity(i);
						// Inserting row in users table

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch (NullPointerException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					return json.getString("message");
				}
				else{
					Intent i=new Intent(Login.this,Login.class);
					startActivity(i);
					return json.getString(TAG_MESSAGE);
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		/** * Once the background process is done we need to Dismiss the progress dialog asap * **/
		protected void onPostExecute(String message) {
			pDialog3.dismiss();

			if (message.contentEquals("Login Successfull!!!") ){
				String emailID = emailET.getText().toString();


				if (!TextUtils.isEmpty(emailID) && GCMUtility.validate(emailID)) {
					// Check if Google Play Service is installed in Device
					// Play services is needed to handle GCM stuffs
					if (checkPlayServices()) {


						// Register Device in GCM Server
						registerInBackground(emailID);
					}
				}
				if(globalVariable.getOrderid() != null)
				{
					if (countadres == 0) {
						TextView t =(TextView)toastRoot2.findViewById(R.id.validtoast);
						t.setText("Login Successfull!");
						toast.setView(toastRoot2);
						toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
						toast.setDuration(Toast.LENGTH_LONG);

						toast.show();

//						Intent i=new Intent(Login.this,AddAddressActivity.class);
//						i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//						startActivity(i);
//						finish();
						//					startActivity(i);

					}else {
						TextView t =(TextView)toastRoot2.findViewById(R.id.validtoast);
						t.setText("Login Successfull!");
						toast.setView(toastRoot2);
						toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
						toast.setDuration(Toast.LENGTH_LONG);
						toast.show();

//						new Postdaata().execute();
						Intent i=new Intent(Login.this,ReviewOrderFragment.class);
						i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						startActivity(i);
						finish();
					}
				}
				else
				//					(globalVariable.getOrderid() == null)
				{
					TextView t =(TextView)toastRoot2.findViewById(R.id.validtoast);
					t.setText("Login Successfull!");
					toast.setView(toastRoot2);
					toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.show();

					Intent i=new Intent(Login.this,HomeActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(i);
					finish();

				}
			}

		}
	}
	//
	class Postdaata extends AsyncTask<String, String, String> {
		SharedPreferences store;
		SharedPreferences.Editor editor;
		String Userid;
		String ordersid;
		ProgressDialog pDialog2;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog2 = new ProgressDialog(Login.this);
			pDialog2.setMessage("Please Wait ..");
			//			pDialog2.show();

		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {


			try {
				ordersid =globalVariable.getOrderid().toString();
				Userid = globalVariable.getUserid().toString();
			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<NameValuePair> params = new ArrayList<NameValuePair>();


			params.add(new BasicNameValuePair("id", Userid));
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONParser jsonpars =new JSONParser();
			JSONObject json = jsonpars.makeHttpRequest(url_buynow,
					"POST", params);

			// check log cat fro response
			Log.d("Create Response", json.toString());
			System.out.println("ORDER ID"+json);
			// Writing data to SharedPreferences

			//				globalVariable.setOrderid(OID);
			//			String uorderid =globalVariable.getOrderid().toString();
			//				String value = settings.getString("key", "");
			//			System.out.println("SharedOrderID"+ uorderid);

			if (globalVariable.getOrderid() !=null) {
				String uorderid =globalVariable.getOrderid().toString();
				System.out.println("SharedOrderID"+ uorderid);
			}


			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					Intent i = new Intent(Login.this, ReviewOrderFragment.class);
					//					Intent i = new Intent(getApplicationContext(), ReviewOrder.class);
					startActivity(i);
					finish();
					// failed to create product
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

			pDialog2.dismiss();
		}


	}


	//	/* The click listener for ListView in the navigation drawer */
	//	public class DrawerItemClickListener implements
	//	ListView.OnItemClickListener {
	//		@Override
	//		public void onItemClick(AdapterView<?> parent, View view, int position,
	//				long id) {
	//
	//			switch (position) {
	//			case 0: {
	//				Intent list = new Intent(getApplicationContext(),
	//						ListActivity.class);
	//				startActivity(list);
	//				finish();
	//				break;
	//			}
	//			case 1: {
	//				//				Intent main = new Intent(getApplicationContext(),
	//				//						MainActivity.class);
	//				//				startActivity(main);
	//				finish();
	//				break;
	//			}
	//			case 2: {
	//				Intent list = new Intent(getApplicationContext(),
	//						ListActivity.class);
	//				startActivity(list);
	//				finish();
	//				break;
	//			}
	//			case 3: {
	//
	//				break;
	//			}
	//
	//			default:
	//				break;
	//			}
	//		}
	//	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		//		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		//		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void loadDrawer(ActionBarDrawerToggle mDrawerToggle2,
						   DrawerLayout mDrawerLayout2) {
		//		mDrawerToggle = mDrawerToggle2;
		//		mDrawerLayout = mDrawerLayout2;
		//		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	/*GCM Integration*/
	// When Register Me button is clicked
	//	public void RegisterUser(View view) {
	//		String emailID = emailET.getText().toString();
	//
	//		if (!TextUtils.isEmpty(emailID) && GCMUtility.validate(emailID)) {
	//			// Check if Google Play Service is installed in Device
	//			// Play services is needed to handle GCM stuffs
	//			if (checkPlayServices()) {
	//
	//				// Register Device in GCM Server
	//				registerInBackground(emailID);
	//			}
	//		}
	//		// When Email is invalid
	//		else {
	//			Toast.makeText(applicationContext, "Please enter valid email",
	//					Toast.LENGTH_LONG).show();
	//		}
	//	}

	// AsyncTask to register Device in GCM Server
	private void registerInBackground(final String emailID) {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcmObj == null) {
						gcmObj = GoogleCloudMessaging
								.getInstance(applicationContext);
					}
					regId = gcmObj
							.register(GCMApplicationConstants.GOOGLE_PROJ_ID);
					msg = "Registration ID :" + regId;

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}catch (NullPointerException e) {
					// TODO: handle exception
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
										String emailID) {
		SharedPreferences prefs = getSharedPreferences("UserDetails",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(REG_ID, regId);
		editor.putString(EMAIL_ID, emailID);
		editor.commit();
		storeRegIdinServer(regId, emailID);

	}

	// Share RegID and Email ID with GCM Server Application (Php)
	private void storeRegIdinServer(String regId2, String emailID) {
		//		prgDialog.show();
		params.put("emailId", emailID);
		params.put("regId", regId);
		System.out.println("Email id = " + emailID + " Reg Id = " + regId);
		// Make RESTful webservice call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(GCMApplicationConstants.APP_SERVER_URL, params,
				new AsyncHttpResponseHandler() {
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

	//	@Override
	//	public boolean onKeyDown(int keyCode, KeyEvent event) {
	//		// cleanup app, save preferences, etc.
	//		//	    android.os.Process.killProcess(android.os.Process.myPid());
	//		this.finishAffinity();
	//		onDestroy();
	//		finish();
	//		// finish(); // not working properly, especially not with asynchronous tasks running
	//		// return moveTaskToBack(true); 
	//		return super.onKeyDown(keyCode, event);
	//	}
}
