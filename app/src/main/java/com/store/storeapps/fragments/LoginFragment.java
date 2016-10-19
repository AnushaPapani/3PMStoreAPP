package com.store.storeapps.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.store.storeapps.R;

import com.store.storeapps.activities.GCMApplicationConstants;
import com.store.storeapps.activities.GCMUtility;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.activities.JSONParser;
import com.store.storeapps.activities.Previous_ProductsActivity;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.store.storeapps.activities.GCMMainActivity.EMAIL_ID;
import static com.store.storeapps.activities.GCMMainActivity.REG_ID;


/**
 * Created by Shankar.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "LoginFragment";

    private HomeActivity mParent;
    private View rootView;
    // Instance of Facebook Class
    private TextView txt_password;
    private TextView txt_register_link;
    public static String Emailid, Username;
    private EditText edt_email;
    private EditText edt_password;
    String access_token;
    private Button btn_login;
    private Button btn_sign_up;
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
    // Your Facebook APP ID
    private static String APP_ID = "1667274583560911"; // Replace with your App ID
    JSONObject json;
    // Instance of Facebook Class
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
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private View otherView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();

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
        btn_sign_up = (Button) rootView.findViewById(R.id.btn_sign_up);
        btn_login.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
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
                    setUpFacebookLogin();
//                    accessTokenFacebook();
//                    printKeyHash(getActivity());
                } else {
                    Toast.makeText(getActivity(), "No Network Connection",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setUpFacebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(getActivity(),
                Collections.singletonList("public_profile"));

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String name = object.getString("name");
                                    String mEmainId = object.getString("email");
                                    String mFaceBookUniqueId = object.getString("id");

                                    String token = loginResult.getAccessToken().getToken();
                                    System.out.println("fname"+name);
//                                    Utility.showLog("name", "name" + name);
//                                    Utility.showLog("token", "token" + token);

//                                    if (mFromLogin.equals("fromlogin")) {
//                                        signInforNextstar(mEmainId, mFaceBookUniqueId, "", "facebook");
//                                    } else if (mFromLogin.equals("fromsingup")) {
//                                        if (!Utility.isValueNullOrEmpty(mEmainId)) {
//                                            checkisUsernameAviableOrNot();
//                                        } else {
//                                            Bundle bundle = new Bundle();
//                                            bundle.putString("useremail", "");
//                                            bundle.putString("password", etAccountPassword.getText().toString());
//                                            bundle.putString("logintype", mLoginType);
//                                            bundle.putString("facebookuniqueid", mFaceBookUniqueId);
//                                        /*parent.tvTermsPrivacy.setVisibility(View.VISIBLE);*/
//                                            parent.llPrivacy.setVisibility(View.GONE);
//                                            Utility.navigateFragment(new RolesFragment(), RolesFragment.TAG, bundle, getActivity());
//                                        }
//                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }

        });
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

        mPrefs = getActivity().getPreferences(MODE_PRIVATE);
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

            facebook.authorize(getActivity(), new String[]{"email"}, new Facebook.DialogListener() {
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

    /*Facebook*/
    public class getFacebookData extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(getActivity(), "Please wait",
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
            LayoutInflater li = LayoutInflater.from(getActivity());
            View promptsView = li.inflate(R.layout.fb_dialog, null);
            alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);
            if (isNetworkAvailable(getActivity()) == true) {
                if (TextUtils.isEmpty(names)) {
                    LayoutInflater li1 = LayoutInflater.from(getActivity());
                    View promptsView1 = li1.inflate(R.layout.fb_dialog, null);
                    alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView1);

                    userInput = (EditText) promptsView1
                            .findViewById(R.id.editTextDialogUserInput);
                    userInput.setText(names);

                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Submit",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            get_old_email = userInput.getText().toString();

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
                                        public void onClick(DialogInterface dialog, int id) {
                                            String get_email = userInput.getText().toString();
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

                } else {
//                    globalVariable.setFbemail(""+names);
//                    globalVariable.setFbgender(""+gender);
//                    globalVariable.setFbname(""+name);
                }
            } else {
                TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
                t.setText("Connection Time out");
                toast.setView(toastRoot);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();

            }
        }

    }

    /*Facebook Userprofile*/
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
            System.out.print("Request URL:" + requestURL);
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
                FBID = jsonObj.getLong("id");
                System.out.println("facebook user id " + FBID);
                name = jsonObj.getString("name");
                names = jsonObj.getString("email");
                gender = jsonObj.getString("gender");
                Utility.setSharedPrefStringData(getActivity(), Constants.FB_GENDER, gender);
                String imageURL = jsonObjUrl.getString("url");

//                new Handler().postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//						/* Create an Intent that will start the Menu-Activity. */
//                    }
//                }, SPLASH_DISPLAY_LENGTH);
            }


        } catch (Exception e) {
            Connectiontimeout = true;
        }


    }

    /*Facebook Login*/
    class Fblogin extends AsyncTask<String, String, String> {

        ProgressDialog pDialog3;
        /**
         * Before starting background thread Show Progress Dialog *
         */
        boolean failure = false;
        String password;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog3 = new ProgressDialog(getActivity());
            pDialog3.setMessage("Attempting for facebook login...");
            pDialog3.show();
        }

        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub

            int success;

            String res = null;
//            String fbmail =globalVariable.getFbemail().toString();
//            String fbgender =globalVariable.getFbgender().toString();
//            String fbname =globalVariable.getFbname().toString();

//            System.out.println("fbmail id1"+fbmail);
            //			String fbid =globalVariable.getFbid().toString();
            //			System.out.println("IDIDIDIss  "+fbid);
            if (Utility.isValueNullOrEmpty(HomeActivity.mCartId)) {
                HomeActivity.mCartId = HomeActivity.mCartId.toString();
            }

//            System.out.println("emaild fb  "+fbmail);
//            System.out.println("gender fb  "+fbgender);
//            System.out.println("IDIDIDI  "+ordersid);
//            System.out.println("FACEBOOKddd   "+FBID);
            String faceid = String.valueOf(FBID);

            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("fbname", Utility.getSharedPrefStringData(getActivity(), Constants.FB_NAME)));
                params.add(new BasicNameValuePair("fbemail", Utility.getSharedPrefStringData(getActivity(), Constants.FB_EMAIL)));
                params.add(new BasicNameValuePair("fbid", faceid));
                params.add(new BasicNameValuePair("fbgender", Utility.getSharedPrefStringData(getActivity(), Constants.FB_GENDER)));
                params.add(new BasicNameValuePair("cartId", HomeActivity.mCartId));
                Log.d("request!", "starting");
                json = jsonParser.makeHttpRequest(ApiConstants.LOGIN, "POST", params);
                Log.d("Login attempt", json.toString());
                String suces = json.toString();
                System.out.println("**********" + suces);
                // success tag for json
                success = json.getInt("success");
                if (success == 1) {
                    Log.d("Successfully Login!", json.toString());
                    if (!TextUtils.isEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.FB_EMAIL))
                            && GCMUtility.validate(Utility.getSharedPrefStringData(getActivity(), Constants.FB_EMAIL))) {
                        // Check if Google Play Service is installed in Device
                        // Play services is needed to handle GCM stuffs
                        if (checkPlayServices()) {
                            // Register Device in GCM Server
                            registerInBackground(Utility.getSharedPrefStringData(getActivity(), Constants.FB_EMAIL));
                        }
                    }
                    // When Email is invalid
                    else {
                        Toast.makeText(getActivity(), "Please enter valid email",
                                Toast.LENGTH_LONG).show();
                    }
                    try {
                        JSONObject jObj = new JSONObject(suces);
                        //						Long uids = jObj.getLong("fb_ID");
                        String userid = jObj.getString("ID");
                        String Count = jObj.getString("count");
                        JSONObject user = jObj.getJSONObject("user");
                        Long uids = user.getLong("fb_ID");
                        String namess = user.getString("fullname");
                        String emailsss = user.getString("email");
                        Log.d("LoginUserid", userid);
                        System.out.println("**********   UIDFB" + userid);
                        System.out.println("**********" + user);
                        System.out.println("**********" + namess);
                        System.out.println("**********" + emailsss);
                        Utility.setSharedPrefStringData(getActivity(), Constants.FB_NAME, namess);
                        Utility.setSharedPrefStringData(getActivity(), Constants.FB_EMAIL, emailsss);

//                        String Name =globalVariable.getName().toString();
//                        System.out.println("UNAME "+Name);
//                        String UID =globalVariable.getUserid().toString();
//                        System.out.println("UNAMEID"+UID);
//                        String MAIL =globalVariable.getEmailid().toString();
//                        System.out.println("UNAMEMAIL "+MAIL);


                        //						System.out.println("Globalname"+globalVariable.getFbname());
                        //						System.out.println("Globalemail"+globalVariable.getFbemail());
                        //
                        //						String Name =globalVariable.getName().toString();
                        //						System.out.println("UNAME "+Name);
                        //						String UID =globalVariable.getUserid().toString();
                        //						System.out.println("UNAMEID"+UID);
                        //						String MAIL =globalVariable.getEmailid().toString();
                        //						System.out.println("UNAMEMAIL "+MAIL);
                        //						String ORDERID =globalVariable.getOrderid().toString();
                        //						System.out.println("ORDERS "+ORDERID);
                        Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());

                        // Inserting row in users table


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    return json.getString("message");
                } else {
                    Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Once the background process is done we need to Dismiss the progress dialog asap *
         **/
        protected void onPostExecute(String message) {
            pDialog3.dismiss();

            if (message.contentEquals("Login Successfull!!!")) {
//                String emailID = emailET.getText().toString();


                if (!TextUtils.isEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID))
                        && GCMUtility.validate(Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID))) {
                    // Check if Google Play Service is installed in Device
                    // Play services is needed to handle GCM stuffs
                    if (checkPlayServices()) {


                        // Register Device in GCM Server
                        registerInBackground(Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID));
                    }
                }
                if (HomeActivity.mCartId != null) {
                    if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT))) {
                        TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
                        t.setText("Login Successfull!");
                        toast.setView(toastRoot2);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                        toast.setDuration(Toast.LENGTH_SHORT);

                        toast.show();
                        Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, getActivity());


                    } else {
                        TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
                        t.setText("Login Successfull!");
                        toast.setView(toastRoot2);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();

                        Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());

//                        new Postdaata().execute();
//                        Intent i = new Intent(Login.this, ReviewOrder.class);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        startActivity(i);
//                        finish();
                    }
                } else {
                    TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
                    t.setText("Login Successfull!");
                    toast.setView(toastRoot2);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                    Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, getActivity());
                }
            }

        }
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
                            mParent.onBackPressed();
                        }

                        if ((Utility.isValueNullOrEmpty(jsonobject.optString("count")))) {
                            Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, mParent);
                        } else if (Utility.isValueNullOrEmpty(HomeActivity.mCartId)) {
                            HomeActivity.updateNavigationDrawer(mParent);
                            Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, mParent);
                        } else {
                            Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, mParent);
                        }

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
