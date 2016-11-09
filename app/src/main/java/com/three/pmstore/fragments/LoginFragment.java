package com.three.pmstore.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.three.pmstore.R;
import com.three.pmstore.activities.Facebook_Activity;
import com.three.pmstore.activities.GCMApplicationConstants;
import com.three.pmstore.activities.GCMUtility;
import com.three.pmstore.activities.HomeActivity;
import com.three.pmstore.activities.JSONParser;
import com.three.pmstore.activities.Previous_ProductsActivity;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.customviews.DialogClass;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;

import static com.three.pmstore.activities.GCMMainActivity.EMAIL_ID;
import static com.three.pmstore.activities.GCMMainActivity.REG_ID;

//import com.three.pmstore.activities.GooglePlus_Activity;


/**
 * Created by Shankar.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "LoginFragment";
    Activity Acontext;

    private HomeActivity mParent;
    private Previous_ProductsActivity mParent2;
    private View rootView;
    // Instance of Facebook_Activity Class
    private TextView txt_password;
    private TextView txt_register_link;
    public static String Emailid, Username;
    private EditText edt_email;
    private EditText edt_password;
    String access_token;
    private Button btn_login;
    private Button btn_sign_up;
    private SignInButton gPlus_sign_up;
    private SharedPreferences mPrefs;
    private String mFrom = "";
    View toastRoot;
    View toastRoot2;
    Toast toast;
    String get_old_email;
    EditText userInput;
    long FBID;
    private Context mContext;
    Context context;
    Boolean Connectiontimeout = true;
    AlertDialog.Builder alertDialogBuilder;
    // Your Facebook_Activity APP ID
    private static String APP_ID = "1667274583560911"; // Replace with your App ID
    JSONObject json;
    // Instance of Facebook_Activity Class
    private Facebook facebook = new Facebook(APP_ID);
    private AsyncFacebookRunner mAsyncRunner;
    String name = "";
    String names = "";
    String gender = "";
    GoogleCloudMessaging gcmObj;
    String regId = "";
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_MESSAGE = "message";
    RequestParams params = new RequestParams();
    String signup = "Sign up with 3PMstore";
    String login = "Login";
    CallbackManager callbackManager;
    Bundle bundle;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private View otherView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        try {
            if (bundle.containsKey("previouslogin")) {
//            Previous_ProductsActivity mParent;
                mParent2 = (Previous_ProductsActivity) getActivity();
            } else {
//            HomeActivity mParent;
                mParent = (HomeActivity) getActivity();
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        if (getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        this.mContext = context;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initUI();
        return rootView;
    }

    private void initUI() {
        FacebookSdk.sdkInitialize(getActivity());
        AppEventsLogger.activateApp(getActivity());
        //		setContentView(R.layout.secs);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        AppEventsLogger logger = AppEventsLogger.newLogger(getActivity());
        logger.logEvent("pageview");
        TextView logiheading = (TextView) rootView.findViewById(R.id.txt_login);
        SpannableString content2 = new SpannableString(login);
        content2.setSpan(new UnderlineSpan(), 0, login.length(), 0);
        logiheading.setText(content2);

        toast = new Toast(getActivity());
        txt_password = (TextView) rootView.findViewById(R.id.txt_forgot_password);
        txt_register_link = (TextView) rootView.findViewById(R.id.register_link);
        SpannableString content = new SpannableString(signup);
        content.setSpan(new UnderlineSpan(), 0, signup.length(), 0);
        txt_register_link.setText(content);

        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo("com.store.storeapps", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        edt_email = (EditText) rootView.findViewById(R.id.edt_email);
        edt_password = (EditText) rootView.findViewById(R.id.edt_password);
        btn_login = (Button) rootView.findViewById(R.id.btn_login);
        gPlus_sign_up = (SignInButton) rootView.findViewById(R.id.sign_in_button);
        btn_sign_up = (Button) rootView.findViewById(R.id.btn_sign_up);
        btn_login.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
        gPlus_sign_up.setOnClickListener(this);
        txt_register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.navigateDashBoardFragment(new RegistrationFragment(), RegistrationFragment.TAG, null, getActivity());
            }
        });
        txt_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.navigateDashBoardFragment(new ForgotPasswordFragment(), ForgotPasswordFragment.TAG, null, getActivity());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (isValidFields()) {
                    loginDataToServer(edt_email.getText().toString(), edt_password.getText().toString());
                }
                break;
            case R.id.btn_sign_up:
                if (isNetworkAvailable(getActivity()) == true) {
                    Intent i = new Intent(getActivity(), Facebook_Activity.class);
                    startActivity(i);
//                    printKeyHash(getActivity());
                } else {
                    Toast.makeText(getActivity(), "No Network Connection",
                            Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.sign_in_button:
                if (isNetworkAvailable(getActivity()) == true) {
//                    Intent i = new Intent(getActivity(), GooglePlus_Activity.class);
//                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "No Network Connection",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
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

    // AsyncTask to register Device in GCM Server
    private void registerInBackground(final String emailID) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(getActivity());
                    }
                    regId = gcmObj
                            .register(GCMApplicationConstants.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;
                    storeRegIdinServer(regId, emailID);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                } catch (NullPointerException e) {
                    // TODO: handle exception
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
//                    storeRegIdinSharedPref(getActivity(), regId, emailID);
//                    Utility.setSharedPrefStringData(getActivity(), Constants.GCM_REGID, regId);
//                    Utility.setSharedPrefStringData(getActivity(), Constants.GCM_REGEMAILID, emailID);
//                    storeRegIdinServer(Utility.getSharedPrefStringData(getActivity(),Constants.GCM_REGID),
//                            Utility.getSharedPrefStringData(getActivity(),Constants.GCM_REGEMAILID));
//                    					Toast.makeText(
//                    							getActivity(),
//                    							"Registered with GCM Server successfully.\n\n"
//                    									+ msg, Toast.LENGTH_SHORT).show();
                } else {
//                    					Toast.makeText(
//                    							getActivity(),
//                    							"Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
//                    									+ msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }

    // Store RegId and Email entered by User in SharedPref
    private void storeRegIdinSharedPref(Context context, String regId, String emailID) {
        SharedPreferences prefs = getActivity().getSharedPreferences("UserDetails",
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
        params.put("emailId", HomeActivity.txt_email.getText().toString());
        params.put("regId", regId2);
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
                .isGooglePlayServicesAvailable(getActivity());
        // When Play services not found in device
        try {
            if (resultCode != ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    // Show Error dialog to install Play services
                    GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                            PLAY_SERVICES_RESOLUTION_REQUEST).show();
                } else {

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
        } catch (RuntimeException e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return true;
    }

    // When Application is resumed, check for Play services support to make sure
    // app will be running normally
    @Override
    public void onResume() {
        super.onResume();

        checkPlayServices();
    }

    private void loginDataToServer(String username, String password) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new PostLoginAsyncTask(username, password).execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }

    class PostLoginAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String username;
        private String password;

        public PostLoginAsyncTask(String username, String password) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));

        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("email", username);
                paramsList.put("password", password);
                paramsList.put("cartId", HomeActivity.mCartId);
                result = Utility.httpPostRequestToServer(ApiConstants.LOGIN, Utility.getParams(paramsList));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            mCustomProgressDialog.dismissProgress();
            try {
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject.optString("success").equalsIgnoreCase("1")) {
                        Utility.setSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT, jsonobject.optString("count"));
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_ID, jsonobject.optString("ID"));
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_LOGIN_COUNT, jsonobject.optString("count"));
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_CASH, jsonobject.optString("cash"));
                        JSONObject userjsonobject = jsonobject.optJSONObject("user");
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_NAME, userjsonobject.optString("fullname"));
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID, userjsonobject.optString("email"));
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_FB_ID, userjsonobject.optString("fb_ID"));
                        HomeActivity.txt_email.setText(userjsonobject.optString("email"));
                        HomeActivity.txt_user_name.setText(userjsonobject.optString("fullname"));
                        HomeActivity.mCartId = jsonobject.optString("cartId");
                        HomeActivity.mCartTotal = jsonobject.optInt("cartValue");
                        HomeActivity.leftMenuAdapter.notifyDataSetChanged();
                        String count = Utility.getSharedPrefStringData(getActivity(), Constants.CARTCOUNT);
                        /*GCM*/
                        if (!TextUtils.isEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID))
                                && GCMUtility.validate(Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID))) {
                            // Check if Google Play Service is installed in Device
                            // Play services is needed to handle GCM stuffs
                            if (checkPlayServices()) {
                                // Register Device in GCM Server
                                registerInBackground(Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID));
                            }
                        }

                        if (!mFrom.equalsIgnoreCase("cart")) {
                            if (bundle.containsKey("previouslogin")) {
//                                Previous_ProductsActivity mParent;
                                mParent2 = (Previous_ProductsActivity) getActivity();
                                mParent2.onBackPressed();
                            } else {
//                                HomeActivity mParent;
                                mParent = (HomeActivity) getActivity();
                                mParent.onBackPressed();
                            }
                        }
//                        if (!(jsonobject.optString("cartId").equalsIgnoreCase("0")) && (!(jsonobject.optString("cartId").equalsIgnoreCase("")))){
//                            if (json.optString("count").equalsIgnoreCase("0")){
//                                Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, mParent);
//                            }else if ((!(jsonobject.optString("cartId").equalsIgnoreCase("0")) && (!(jsonobject.optString("cartId").equalsIgnoreCase(""))))){
//                                Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, mParent);
//                            }
//
//                        }else {
//                            Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, mParent);
//                        }
//                        if (bundle.containsKey("previouslogin")){
//                            Previous_ProductsActivity mParent;
//                            mParent = (Previous_ProductsActivity) getActivity();
//                        }else {
//                            HomeActivity mParent;
//                            mParent = (HomeActivity) getActivity();
//                        }
                        if ((Utility.isValueNullOrEmpty(jsonobject.optString("count")))) {
                            if (bundle.containsKey("previouslogin")) {
//                                Previous_ProductsActivity mParent;
                                mParent2 = (Previous_ProductsActivity) getActivity();
                                Previous_ProductsActivity.updateNavigationDrawer(mParent2);
                                Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, mParent2);
                            } else {
//                                HomeActivity mParent;
                                mParent = (HomeActivity) getActivity();
                                HomeActivity.updateNavigationDrawer(mParent);
                                Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, mParent);
                            }

                        } else if (Utility.isValueNullOrEmpty(HomeActivity.mCartId)) {
                            if (bundle.containsKey("previouslogin")) {
//                                Previous_ProductsActivity mParent;
                                mParent2 = (Previous_ProductsActivity) getActivity();
                                Previous_ProductsActivity.updateNavigationDrawer(mParent2);
                                Intent i = new Intent(getActivity(), HomeActivity.class);
                                startActivity(i);
                                Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, mParent2);
                            } else {
//                                HomeActivity mParent;
                                mParent = (HomeActivity) getActivity();
                                HomeActivity.updateNavigationDrawer(mParent);
                                Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, mParent);
                            }
                        } else {
                            if (bundle.containsKey("previouslogin")) {
//                                Previous_ProductsActivity mParent;
                                mParent2 = (Previous_ProductsActivity) getActivity();
                                Previous_ProductsActivity.updateNavigationDrawer(mParent2);
                                Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, mParent2);
                            } else {
//                                HomeActivity mParent;
                                mParent = (HomeActivity) getActivity();
                                HomeActivity.updateNavigationDrawer(mParent);
                                Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, mParent);
                            }
//                            HomeActivity.updateNavigationDrawer(mParent);
                        }

//                        if ((Utility.isValueNullOrEmpty(jsonobject.optString("count")))) {
//                            Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, mParent);
//                        } else if ((Utility.isValueNullOrEmpty(jsonobject.optString("cartId"))) && (jsonobject.getString("cartId").equals(""))) {
////                            HomeActivity.updateNavigationDrawer(mParent);
////                            Intent i=new Intent(mContext,HomeActivity.class);
////                            startActivity(i);
//                            Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, mParent);
////                            Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, mParent);
//                        } else if (count.equals("0")) {
//                            HomeActivity.updateNavigationDrawer(mParent);
//                            Intent i=new Intent(mContext,HomeActivity.class);
//                            startActivity(i);
////                            Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, mParent);
//                        } else if (!(count.equals("0")) && !(HomeActivity.mCartId.equals("0"))){
//                            Toast.makeText(mContext,"Revieworder",Toast.LENGTH_LONG).show();
////                            Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, mParent);
//                        }
                    } else {
                        TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
                        t.setText("Incorrect Credentials");
                        toast.setView(toastRoot);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidFields() {
        boolean isValidate = false;
        if (edt_email.getText().toString().equals("")) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter fields");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
            return isValidate;
        } else if (!edt_email.getText().toString().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$")) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter Valid Email ID");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
            return isValidate;
        } else if (edt_password.getText().toString().equals("")) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter Password");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
            return isValidate;
        } else {
            return true;
        }
    }


}
