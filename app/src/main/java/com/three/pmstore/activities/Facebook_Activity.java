package com.three.pmstore.activities;

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
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.three.pmstore.R;
import com.three.pmstore.fragments.HomeFragment;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class Facebook_Activity extends AppCompatActivity {
    //	String TAG = "Second";
    private DrawerLayout mDrawerLayout;
    //	private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    Button login;
    // Your Facebook APP ID
    private static String APP_ID = "1667274583560911"; // Replace with your App ID

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
    private static final String url_login_user = "http://www.3pmstore.com/android/android_connect/loginuser.php";
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
//    AppController globalVariable;
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
//    String fb_name;
    Boolean Connectiontimeout = true;
    //	private LoginButton loginBtn;
    String access_token;
    int position =5;
    Toast toast;
    View toastRoot;
    View toastRoot2;
    private TextView username;
    //	private UiLifecycleHelper uiHelper;
    Context context = Facebook_Activity.this;
    private HomeActivity mParent;
    //	ProgressDialog prgDialog;

    RequestParams params = new RequestParams();
    GoogleCloudMessaging gcmObj;
    String regId = "";
    Context applicationContext;
    String fb_email;
    String fb_name;

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
//        mParent = (HomeActivity) getApplicationContext();
        //mParent = (HomeActivity) getActivity();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        //		setContentView(R.layout.secs);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        AppEventsLogger logger = AppEventsLogger.newLogger(Facebook_Activity.this);
        logger.logEvent("pageview");


        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.fragment_login);


//		textCounter = (TextView)findViewById(R.id.textView3);
        tl1=(TextView)findViewById(R.id.loginNameTextViews);
        tl2=(TextView)findViewById(R.id.emails);


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

//        if (!TextUtils.isEmpty(registrationId)) {
//            Intent i = new Intent(applicationContext, ProductsPage.class);
//            i.putExtra("regId", registrationId);
//            //			startActivity(i);
//            //			finish();
//        }


        mfacebook =(Button)findViewById(R.id.btn_sign_up);
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
//                    Toast.makeText(Login.this, "No Network Connection",
//                            Toast.LENGTH_SHORT).show();
                }
                //				loginToFacebook();

                //				new Fblogin().execute();
                //				Intent i=new Intent(Second.this,Facebookss.class);
                //				startActivity(i);
                //								Intent i=new Intent(Second.this,Example.class);
                //								startActivity(i);
            }
        });
//        try {
//            if (globalVariable.getUserid().toString() ==null) {
//                //				Toast.makeText(getApplicationContext(), "No Userid", 9000).show();
//            }else {
//                userid =globalVariable.getUserid().toString();
//            }
//        } catch (NullPointerException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


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

//        inputpasswd=(EditText)findViewById(R.id.psds);
//
//        Register=(TextView)findViewById(R.id.registerlink);
//        SpannableString textcontent = new SpannableString(mystring);
//        textcontent.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
//        Register.setText(textcontent);
//        Register.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent i= new Intent(getApplicationContext(),NewUserActivity.class);
//                startActivity(i);
//            }
//        });
//
//        login=(Button)findViewById(R.id.logins);
//        forgotpass=(TextView)findViewById(R.id.forgotpasswordlink);
//        forgotpass.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent i= new Intent(getApplicationContext(),ForgotPassword.class);
//                startActivity(i);
//
//
//            }
//        });

        //		inputmail.setText(duser);
        //		inputpasswd.setText(dpasswd);
//        login.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                if (((inputmail.getText().toString().length()<1)) && (inputpasswd.getText().toString().length()<1) )
//                {
//                    TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
//                    t.setText("Please enter fields");
//                    toast.setView(toastRoot);
//                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
//                    toast.setDuration(20000);
//                    toast.show();
//                }
//                else if (((inputmail.getText().toString().length()<1))){
//                    TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
//                    t.setText("Please enter Email ID");
//                    toast.setView(toastRoot);
//                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
//                    toast.setDuration(20000);
//                    toast.show();
//                }else if (!inputmail.getText().toString().matches(emailPattern)) {
//                    TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
//                    t.setText("Please enter Valid Email ID");
//                    toast.setView(toastRoot);
//                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
//                    toast.setDuration(20000);
//                    toast.show();
//                }
//                else if (((inputpasswd.getText().toString().length()<1))){
//                    TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
//                    t.setText("Please enter Password");
//                    toast.setView(toastRoot);
//                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
//                    toast.setDuration(20000);
//                    toast.show();
//                }
//                else
//                {
//
//                    new loginUser().execute();
//
//                }
//
//            }
//        });

        loadDrawer(mDrawerToggle, mDrawerLayout);

//        setTimer();
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
            pd = ProgressDialog.show(Facebook_Activity.this, "Please wait",
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
//                                            globalVariable.setFbemail(""+get_old_email);
//                                            //							globalVariable.setFb
//                                            globalVariable.setFbgender(""+gender);
//                                            globalVariable.setFbname(""+name);
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
//                                            globalVariable.setFbemail(""+get_email);
//                                            globalVariable.setFbgender(""+gender);
//                                            globalVariable.setFbname(""+name);
                                            new Fblogin().execute();
                                        }
                                    });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }
                else {

                }
            } else {
                TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
                t.setText("Connection Time out");
                toast.setView(toastRoot);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
                toast.setDuration(20000);
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
            Toast.makeText(getApplicationContext(), ""+gender, 9000).show();

        } catch (Exception e) {
            Connectiontimeout = true;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.authorizeCallback(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), ""+gender, 9000).show();
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
                    fb_name = profile.getString("name");
                    // getting email of the user
                    fb_email = profile.getString("email");

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
            pDialog3 = new ProgressDialog(Facebook_Activity.this);
            pDialog3.setMessage("Attempting for facebook login...");
            pDialog3.show();
        }

        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub

            int success;

            String res = null;

            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("fbname", name));
                params.add(new BasicNameValuePair("fbemail", names));
                params.add(new BasicNameValuePair("fbid", id));
                params.add(new BasicNameValuePair("fbgender", gender));
                params.add(new BasicNameValuePair("cartId", HomeActivity.mCartId));
                Log.d("request!", "starting");
                json = jsonParser.makeHttpRequest(ApiConstants.LOGIN, "POST", params);
                Log.d("Login attempt", json.toString());
                String suces = json.toString();
                System.out.println("**********"+suces);
                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                if (json.optString("success").equalsIgnoreCase("1")) {
                    Utility.setSharedPrefStringData(context, Constants.ADDRESS_COUNT, json.optString("count"));
                    Utility.setSharedPrefStringData(context, Constants.USER_ID, json.optString("ID"));
                    Utility.setSharedPrefStringData(context, Constants.USER_LOGIN_COUNT, json.optString("count"));
                    Utility.setSharedPrefStringData(context, Constants.USER_CASH, json.optString("cash"));
                    JSONObject userjsonobject = json.optJSONObject("user");
                    Utility.setSharedPrefStringData(context, Constants.USER_NAME, userjsonobject.optString("fullname"));
                    Utility.setSharedPrefStringData(context, Constants.USER_EMAIL_ID, userjsonobject.optString("email"));
                    Utility.setSharedPrefStringData(context, Constants.USER_FB_ID, userjsonobject.optString("fb_ID"));
//                    HomeActivity.txt_email.setText(userjsonobject.optString("email"));
//                    HomeActivity.txt_user_name.setText(userjsonobject.optString("fullname"));
                    HomeActivity.mCartId = json.optString("cartId");
                    HomeActivity.mCartTotal = json.optInt("cartValue");
                        /*GCM*/
//                    if (!TextUtils.isEmpty(Utility.getSharedPrefStringData(getApplicationContext(), Constants.USER_EMAIL_ID))
//                            && GCMUtility.validate(Utility.getSharedPrefStringData(getApplicationContext(), Constants.USER_EMAIL_ID))) {
//                        // Check if Google Play Service is installed in Device
//                        // Play services is needed to handle GCM stuffs
//                        if (checkPlayServices()) {
//                            // Register Device in GCM Server
//                            registerInBackground(Utility.getSharedPrefStringData(getApplicationContext(), Constants.USER_EMAIL_ID));
//                        }
//                    }
                    Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, Facebook_Activity.this);
                        finish();
//                    if (!mFrom.equalsIgnoreCase("cart")) {
//                        mParent.onBackPressed();
//                    }

//                    if ((Utility.isValueNullOrEmpty(json.optString("count")))) {
//                        Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, Facebook_Activity.this);
//                    } else if (Utility.isValueNullOrEmpty(HomeActivity.mCartId)) {
//                        HomeActivity.updateNavigationDrawer(context);
//                        Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, Facebook_Activity.this);
//                    } else {
//                        Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, Facebook_Activity.this);
//                    }

//                        }else if ((!Utility.isValueNullOrEmpty(HomeActivity.mCartId)&&
//                                (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT))))){
//                            Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());
//                        }

                        /*If Address is Zero*//*
                        if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT))) {
                            Utility.navigateDashBoardFragment(new MyAddressFragment(), MyAddressFragment.TAG, null, getActivity());
                        } else {
                            Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());
                        }*/


                } else {
                    TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
                    t.setText("Incorrect Credentials");
                    toast.setView(toastRoot);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
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

            }

        }
    }




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
