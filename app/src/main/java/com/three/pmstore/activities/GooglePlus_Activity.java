package com.three.pmstore.activities;//package com.three.pmstore.activities;
//
//import android.app.ProgressDialog;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentSender.SendIntentException;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.common.api.CommonStatusCodes;
//import com.google.android.gms.common.api.Scope;
//import com.google.android.gms.gcm.GoogleCloudMessaging;
//import com.google.android.gms.plus.People;
//import com.google.android.gms.plus.People.LoadPeopleResult;
//import com.google.android.gms.plus.Plus;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
//import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.plus.PlusShare;
//import com.google.android.gms.plus.model.people.Person;
//import com.google.android.gms.plus.model.people.PersonBuffer;
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//
//import com.three.pmstore.R;
//import com.three.pmstore.utility.ApiConstants;
//import com.three.pmstore.utility.Constants;
//import com.three.pmstore.utility.Utility;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GooglePlus_Activity extends AppCompatActivity implements OnConnectionFailedListener, View.OnClickListener, ConnectionCallbacks, ResultCallback<LoadPeopleResult> {
//    GoogleApiClient google_api_client;
//    GoogleApiAvailability google_api_availability;
////    SignInButton signIn_btn;
//    private static final int SIGN_IN_CODE = 0;
//    private static final int PROFILE_PIC_SIZE = 120;
//    private ConnectionResult connection_result;
//    private boolean is_intent_inprogress;
//    private boolean is_signInBtn_clicked;
//    private int request_code;
//    ProgressDialog progress_dialog;
//    JSONObject json;
//    JSONParser jsonParser = new JSONParser();
//    Context context = GooglePlus_Activity.this;
//    String Username;
//    String gemail;
//    RequestParams params = new RequestParams();
//    GoogleCloudMessaging gcmObj;
//    String regId = "";
//    Context applicationContext;
//    Toast toast;
//    View toastRoot;
//    View toastRoot2;
//    public static final String REG_ID = "regId";
//    public static final String EMAIL_ID = "eMailId";
//    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppTheme_NoActionBar);
//        buidNewGoogleApiClient();
//        setContentView(R.layout.toolbar);
//        //Customize sign-in button.a red button may be displayed when Google+ scopes are requested
//        custimizeSignBtn();
//        setBtnClickListeners();
//        progress_dialog = new ProgressDialog(this);
//        progress_dialog.setMessage("Signing in....");
//        final Context context = getApplicationContext();
//        // Create layout inflator object to inflate toast.xml file
//        final LayoutInflater inflater = getLayoutInflater();
//        // Call toast.xml file for toast layout
//        toastRoot = inflater.inflate(R.layout.toast, null);
//        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
//        toast = new Toast(context);
//        Toast.makeText(this, "start sign process", Toast.LENGTH_SHORT).show();
//        gPlusSignIn();
//    }
//
//    /*
//    create and  initialize GoogleApiClient object to use Google Plus Api.
//    While initializing the GoogleApiClient object, request the Plus.SCOPE_PLUS_LOGIN scope.
//    */
//
//
//    private void buidNewGoogleApiClient() {
//        google_api_client = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API, Plus.PlusOptions.builder().build())
//                .addScope(Plus.SCOPE_PLUS_LOGIN)
//                .addScope(Plus.SCOPE_PLUS_PROFILE)
//                .build();
//    }
//    /*
//      Customize sign-in button. The sign-in button can be displayed in
//      multiple sizes and color schemes. It can also be contextually
//      rendered based on the requested scopes. For example. a red button may
//      be displayed when Google+ scopes are requested, but a white button
//      may be displayed when only basic profile is requested. Try adding the
//      Plus.SCOPE_PLUS_LOGIN scope to see the  difference.
//    */
//
//    private void custimizeSignBtn() {
//
////        signIn_btn = (SignInButton) findViewById(R.id.sign_in_button);
////        signIn_btn.setSize(SignInButton.SIZE_STANDARD);
////        signIn_btn.setScopes(new Scope[]{Plus.SCOPE_PLUS_LOGIN});
//
//    }
//
//    /*
//      Set on click Listeners on the sign-in sign-out and disconnect buttons
//     */
//
//    private void setBtnClickListeners() {
//        // Button listeners
////        signIn_btn.setOnClickListener(this);
//
//    }
//
//    protected void onStart() {
//        super.onStart();
//        google_api_client.connect();
//    }
//
//    protected void onStop() {
//        super.onStop();
//        if (google_api_client.isConnected()) {
//            google_api_client.disconnect();
//        }
//    }
//
//    protected void onResume() {
//        super.onResume();
//        if (google_api_client.isConnected()) {
//            google_api_client.connect();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        if (!result.hasResolution()) {
//            google_api_availability.getErrorDialog(this, result.getErrorCode(), request_code).show();
//            return;
//        }
//
//        if (!is_intent_inprogress) {
//
//            connection_result = result;
//
//            if (is_signInBtn_clicked) {
//
//                resolveSignInError();
//            }
//        }
//
//    }
//
//    /*
//      Will receive the activity result and check which request we are responding to
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int responseCode,
//                                    Intent intent) {
//        // Check which request we're responding to
//        if (requestCode == SIGN_IN_CODE) {
//            request_code = requestCode;
//            if (responseCode != RESULT_OK) {
//                is_signInBtn_clicked = false;
//                progress_dialog.dismiss();
//
//            }
//
//            is_intent_inprogress = false;
//
//            if (!google_api_client.isConnecting()) {
//                google_api_client.connect();
//            }
//        }
//
//    }
//
//    @Override
//    public void onConnected(Bundle arg0) {
//        is_signInBtn_clicked = false;
//        // Get user's information and set it into the layout
//        getProfileInfo();
//
//        // Update the UI after signin
//        changeUI(true);
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int arg0) {
//        google_api_client.connect();
//        changeUI(false);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.sign_in_button:
//                Toast.makeText(this, "start sign process", Toast.LENGTH_SHORT).show();
//                gPlusSignIn();
//                break;
//
//
//        }
//    }
//
//
//
//    /*
//      Sign-in into the Google + account
//     */
//
//    private void gPlusSignIn() {
//        if (!google_api_client.isConnecting()) {
//            Log.d("user connected", "connected");
//            is_signInBtn_clicked = true;
//            progress_dialog.show();
//            resolveSignInError();
//
//        }
//    }
//
//    /*
//     Revoking access from Google+ account
//     */
//
//    private void gPlusRevokeAccess() {
//        if (google_api_client.isConnected()) {
//            Plus.AccountApi.clearDefaultAccount(google_api_client);
//            Plus.AccountApi.revokeAccessAndDisconnect(google_api_client)
//                    .setResultCallback(new ResultCallback<Status>() {
//                        @Override
//                        public void onResult(Status arg0) {
//                            Log.d("MainActivity", "User access revoked!");
//                            buidNewGoogleApiClient();
//                            google_api_client.connect();
//                            changeUI(false);
//                        }
//
//                    });
//        }
//    }
//
//
//    /*
//      Method to resolve any signin errors
//     */
//
//    private void resolveSignInError() {
//        if (connection_result.hasResolution()) {
//            try {
//                is_intent_inprogress = true;
//                connection_result.startResolutionForResult(this, SIGN_IN_CODE);
//                Log.d("resolve error", "sign in error resolved");
//            } catch (SendIntentException e) {
//                is_intent_inprogress = false;
//                google_api_client.connect();
//            }
//        }
//    }
//
//    /*
//      Sign-out from Google+ account
//     */
//
//    private void gPlusSignOut() {
//        if (google_api_client.isConnected()) {
//            Plus.AccountApi.clearDefaultAccount(google_api_client);
//            google_api_client.disconnect();
//            google_api_client.connect();
//            changeUI(false);
//        }
//    }
//
//
//
//    /*
//     get user's information name, email, profile pic,Date of birth,tag line and about me
//     */
//
//    private void getProfileInfo() {
//
//        try {
//
//            if (Plus.PeopleApi.getCurrentPerson(google_api_client) != null) {
//                Person currentPerson = Plus.PeopleApi.getCurrentPerson(google_api_client);
//                setPersonalInfo(currentPerson);
//
//            } else {
//                Toast.makeText(getApplicationContext(),
//                        "No Personal info mention", Toast.LENGTH_LONG).show();
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /*
//     set the User information into the views defined in the layout
//     */
//
//    private void setPersonalInfo(Person currentPerson) {
//
//        String personName = currentPerson.getDisplayName();
//        String personPhotoUrl = currentPerson.getImage().getUrl();
//        String email = Plus.AccountApi.getAccountName(google_api_client);
////        TextView   user_name = (TextView) findViewById(R.id.userName);
//        Username = personName;
////        user_name.setText("Name: "+personName);
////        TextView gemail_id = (TextView)findViewById(R.id.emailId);
//        gemail = email;
////        gemail_id.setText("Email Id: " +email);
//        setProfilePic(personPhotoUrl);
//        progress_dialog.dismiss();
//        Toast.makeText(this, "Person information is shown!", Toast.LENGTH_LONG).show();
//    }
//
//    /*
//     By default the profile pic url gives 50x50 px image.
//     If you need a bigger image we have to change the query parameter value from 50 to the size you want
//    */
//
//    private void setProfilePic(String profile_pic) {
//        profile_pic = profile_pic.substring(0,
//                profile_pic.length() - 2)
//                + PROFILE_PIC_SIZE;
////        ImageView    user_picture = (ImageView)findViewById(R.id.profile_pic);
////        new LoadProfilePic(user_picture).execute(profile_pic);
//    }
//
//    /*
//     Show and hide of the Views according to the user login status
//     */
//
//    private void changeUI(boolean signedIn) {
//        if (signedIn) {
//            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
////            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
//        } else {
//
//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
////            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void onResult(LoadPeopleResult peopleData) {
//        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
//            PersonBuffer personBuffer = peopleData.getPersonBuffer();
//            ArrayList<String> list = new ArrayList<String>();
//            ArrayList<String> img_list = new ArrayList<String>();
//            try {
//                int count = personBuffer.getCount();
//
//                for (int i = 0; i < count; i++) {
//                    list.add(personBuffer.get(i).getDisplayName());
//                    img_list.add(personBuffer.get(i).getImage().getUrl());
//                }
////                Intent intent = new Intent(MainActivity.this,FriendActivity.class);
////                intent.putStringArrayListExtra("friendsName",list);
////                intent.putStringArrayListExtra("friendsPic",img_list);
////                startActivity(intent);
//            } finally {
//                personBuffer.release();
//            }
//        } else {
//            Log.e("circle error", "Error requesting visible circles: " + peopleData.getStatus());
//        }
//    }
//
//
//   /*
//    Perform background operation asynchronously, to load user profile picture with new dimensions from the modified url
//    */
//
//    private class LoadProfilePic extends AsyncTask<String, Void, Bitmap> {
//        ImageView bitmap_img;
//
//        public LoadProfilePic(ImageView bitmap_img) {
//            this.bitmap_img = bitmap_img;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String url = urls[0];
//            Bitmap new_icon = null;
//            try {
//                InputStream in_stream = new java.net.URL(url).openStream();
//                new_icon = BitmapFactory.decodeStream(in_stream);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return new_icon;
//        }
//
//        protected void onPostExecute(Bitmap result_img) {
//
//            bitmap_img.setImageBitmap(result_img);
//        }
//    }
//
//    class Fblogin extends AsyncTask<String, String, String> {
//        String ordersid;
//        ProgressDialog pDialog3;
//        /**
//         * Before starting background thread Show Progress Dialog *
//         */
//        boolean failure = false;
//        String password;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog3 = new ProgressDialog(GooglePlus_Activity.this);
//            pDialog3.setMessage("Attempting for facebook login...");
//            pDialog3.show();
//        }
//
//        protected String doInBackground(String... args) {
//            // TODO Auto-generated method stub
//
//            int success;
//
//            String res = null;
//
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//            params.add(new BasicNameValuePair("fbname", Username));
//            params.add(new BasicNameValuePair("fbemail", gemail));
////                params.add(new BasicNameValuePair("fbid", ""));
////                params.add(new BasicNameValuePair("fbgender", ""));
//            params.add(new BasicNameValuePair("cartId", HomeActivity.mCartId));
//            Log.d("request!", "starting");
//            json = jsonParser.makeHttpRequest(ApiConstants.LOGIN, "POST", params);
//            Log.d("Login attempt", json.toString());
//            String suces = json.toString();
//            System.out.println("**********" + suces);
//            // success tag for json
//            if (json.optString("success").equalsIgnoreCase("1")) {
//                Utility.setSharedPrefStringData(context, Constants.ADDRESS_COUNT, json.optString("count"));
//                Utility.setSharedPrefStringData(context, Constants.USER_ID, json.optString("ID"));
//                Utility.setSharedPrefStringData(context, Constants.USER_LOGIN_COUNT, json.optString("count"));
//                Utility.setSharedPrefStringData(context, Constants.USER_CASH, json.optString("cash"));
//                JSONObject userjsonobject = json.optJSONObject("user");
//                Utility.setSharedPrefStringData(context, Constants.USER_NAME, userjsonobject.optString("fullname"));
//                Utility.setSharedPrefStringData(context, Constants.USER_EMAIL_ID, userjsonobject.optString("email"));
//                Utility.setSharedPrefStringData(context, Constants.USER_FB_ID, userjsonobject.optString("fb_ID"));
////                    HomeActivity.txt_email.setText(userjsonobject.optString("email"));
////                    HomeActivity.txt_user_name.setText(userjsonobject.optString("fullname"));
//                HomeActivity.mCartId = json.optString("cartId");
//                HomeActivity.mCartTotal = json.optInt("cartValue");
//                HomeActivity.leftMenuAdapter.notifyDataSetChanged();
////                    String emailID = emailET.getText().toString();
//
//                if (!TextUtils.isEmpty(gemail) && GCMUtility.validate(gemail)) {
//                    // Check if Google Play Service is installed in Device
//                    // Play services is needed to handle GCM stuffs
//                    if (checkPlayServices()) {
//                        // Register Device in GCM Server
//                        registerInBackground(gemail);
//                    }
//                }
//                if ((Utility.isValueNullOrEmpty(json.optString("count")))) {
//                    Intent i = new Intent(GooglePlus_Activity.this, HomeActivity.class);
//                    i.putExtra("Address", "Address");
//                    startActivity(i);
//                } else if (Utility.isValueNullOrEmpty(HomeActivity.mCartId)) {
//
//                    Intent i = new Intent(GooglePlus_Activity.this, HomeActivity.class);
//                    startActivity(i);
//                } else {
//                    Intent i = new Intent(GooglePlus_Activity.this, HomeActivity.class);
//                    i.putExtra("Facebook", "Facebook");
//                    startActivity(i);
//                }
//
////                    if (!(json.optString("cartId").equalsIgnoreCase("0"))) {
////                        if (json.optString("count").equalsIgnoreCase("0")) {
////                            Intent i = new Intent(Facebook_Activity.this, HomeActivity.class);
////                            i.putExtra("Address","Address");
////                            startActivity(i);
//////                        Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, Facebook_Activity.this);
////                        } else {
////                            Intent i = new Intent(Facebook_Activity.this, HomeActivity.class);
////                            i.putExtra("Facebook","Facebook");
////                            startActivity(i);
//////                        Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, Facebook_Activity.this);
////                        }
////                    } else if ((json.optString("cartId").equalsIgnoreCase("0")) && (json.optString("cartId").equalsIgnoreCase(""))){
////                        Intent i = new Intent(Facebook_Activity.this, HomeActivity.class);
////                        startActivity(i);
////                    }
////                    Intent i=new Intent(Facebook_Activity.this,HomeActivity.class);
////                    startActivity(i);
//
//                    /*GCM*/
////                    if (!TextUtils.isEmpty(Utility.getSharedPrefStringData(getApplicationContext(), Constants.USER_EMAIL_ID))
////                            && GCMUtility.validate(Utility.getSharedPrefStringData(getApplicationContext(), Constants.USER_EMAIL_ID))) {
////                        // Check if Google Play Service is installed in Device
////                        // Play services is needed to handle GCM stuffs
////                        if (checkPlayServices()) {
////                            // Register Device in GCM Server
////                            registerInBackground(Utility.getSharedPrefStringData(getApplicationContext(), Constants.USER_EMAIL_ID));
////                        }
////                    }
////                    Intent i=new Intent(Facebook_Activity.this,HomeActivity.class);
////                    startActivity(i);
////                    Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, Facebook_Activity.this);
////                        finish();
////                    if (!mFrom.equalsIgnoreCase("cart")) {
////                        mParent.onBackPressed();
////                    }
//
//
////                    if ((Utility.isValueNullOrEmpty(json.optString("count")))) {
////                        Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, Facebook_Activity.this);
////                    } else if (Utility.isValueNullOrEmpty(HomeActivity.mCartId)) {
////                        HomeActivity.updateNavigationDrawer(context);
////                        Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, Facebook_Activity.this);
////                    } else {
////                        Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, Facebook_Activity.this);
////                    }
//
////                        }else if ((!Utility.isValueNullOrEmpty(HomeActivity.mCartId)&&
////                                (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT))))){
////                            Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());
////                        }
//
//                    /*If Address is Zero*//*
//                    if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT))) {
//                        Utility.navigateDashBoardFragment(new MyAddressFragment(), MyAddressFragment.TAG, null, getActivity());
//                    } else {
//                        Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());
//                    }*/
//
//
//            } else {
//                TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
//                t.setText("Incorrect Credentials");
//                toast.setView(toastRoot);
//                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.show();
//            }
//
//            return null;
//        }
//
//        /**
//         * Once the background process is done we need to Dismiss the progress dialog asap *
//         **/
//        protected void onPostExecute(String message) {
//            pDialog3.dismiss();
//        }
//
//        // AsyncTask to register Device in GCM Server
//
//        // AsyncTask to register Device in GCM Server
//        private void registerInBackground(final String emailID) {
//            new AsyncTask<Void, Void, String>() {
//                @Override
//                protected String doInBackground(Void... params) {
//                    String msg = "";
//                    try {
//                        if (gcmObj == null) {
//                            gcmObj = GoogleCloudMessaging
//                                    .getInstance(applicationContext);
//                        }
//                        regId = gcmObj
//                                .register(GCMApplicationConstants.GOOGLE_PROJ_ID);
//                        msg = "Registration ID :" + regId;
//
//                    } catch (IOException ex) {
//                        msg = "Error :" + ex.getMessage();
//                    } catch (NullPointerException e) {
//                        // TODO: handle exception
//                    }
//                    return msg;
//                }
//
//                @Override
//                protected void onPostExecute(String msg) {
//                    if (!TextUtils.isEmpty(regId)) {
//                        storeRegIdinSharedPref(applicationContext, regId, emailID);
//                        //					Toast.makeText(
//                        //							applicationContext,
//                        //							"Registered with GCM Server successfully.\n\n"
//                        //									+ msg, Toast.LENGTH_SHORT).show();
//                    } else {
//                        //					Toast.makeText(
//                        //							applicationContext,
//                        //							"Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
//                        //									+ msg, Toast.LENGTH_LONG).show();
//                    }
//                }
//            }.execute(null, null, null);
//        }
//
//        // Store RegId and Email entered by User in SharedPref
//        private void storeRegIdinSharedPref(Context context, String regId,
//                                            String emailID) {
//            SharedPreferences prefs = getSharedPreferences("UserDetails",
//                    Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putString(REG_ID, regId);
//            editor.putString(EMAIL_ID, emailID);
//            editor.commit();
//            storeRegIdinServer(regId, emailID);
//
//        }
//
//        // Share RegID and Email ID with GCM Server Application (Php)
//        private void storeRegIdinServer(String regId2, String emailID) {
//            //		prgDialog.show();
//            params.put("emailId", emailID);
//            params.put("regId", regId);
//            System.out.println("Email id = " + emailID + " Reg Id = " + regId);
//            // Make RESTful webservice call using AsyncHttpClient object
//            AsyncHttpClient client = new AsyncHttpClient();
//            client.post(GCMApplicationConstants.APP_SERVER_URL, params,
//                    new AsyncHttpResponseHandler() {
//                        // When the response returned by REST has Http
//                        // response code '200'
//                        @Override
//                        public void onSuccess(String response) {
//                            // Hide Progress Dialog
//                            //				prgDialog.hide();
//                            //				if (prgDialog != null) {
//                            //					prgDialog.dismiss();
//                            //				}
//                            //				Toast.makeText(applicationContext,
//                            //						"Reg Id shared successfully with Web App ",
//                            //						Toast.LENGTH_LONG).show();
//                            //				Intent i = new Intent(applicationContext,
//                            //						GreetingActivity.class);
//                            //				i.putExtra("regId", regId);
//                            //				startActivity(i);
//                            //				finish();
//                        }
//
//                        // When the response returned by REST has Http
//                        // response code other than '200' such as '404',
//                        // '500' or '403' etc
//                        @Override
//                        public void onFailure(int statusCode, Throwable error,
//                                              String content) {
//                            // Hide Progress Dialog
//                            //				prgDialog.hide();
//                            //				if (prgDialog != null) {
//                            //					prgDialog.dismiss();
//                            //				}
//                            // When Http response code is '404'
//                            if (statusCode == 404) {
//                                //					Toast.makeText(applicationContext,
//                                //							"Requested resource not found",
//                                //							Toast.LENGTH_LONG).show();
//                            }
//                            // When Http response code is '500'
//                            else if (statusCode == 500) {
//                                //					Toast.makeText(applicationContext,
//                                //							"Something went wrong at server end",
//                                //							Toast.LENGTH_LONG).show();
//                            }
//                            // When Http response code other than 404, 500
//                            else {
//                                //					Toast.makeText(
//                                //							applicationContext,
//                                //							"Unexpected Error occcured! [Most common Error: Device might "
//                                //									+ "not be connected to Internet or remote server is not up and running], check for other errors as well",
//                                //									Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//        }
//
//        // Check if Google Playservices is installed in Device or not
//        private boolean checkPlayServices() {
//            int resultCode = GooglePlayServicesUtil
//                    .isGooglePlayServicesAvailable(GooglePlus_Activity.this);
//            // When Play services not found in device
//            try {
//                if (resultCode != ConnectionResult.SUCCESS) {
//                    if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                        // Show Error dialog to install Play services
//                        GooglePlayServicesUtil.getErrorDialog(resultCode, GooglePlus_Activity.this,
//                                PLAY_SERVICES_RESOLUTION_REQUEST).show();
//                    } else {
//                        //					Toast.makeText(
//                        //							applicationContext,
//                        //							"This device doesn't support Play services, App will not work normally",
//                        //							Toast.LENGTH_LONG).show();
//                        finish();
//                    }
//                    return false;
//                } else {
//                    //				Toast.makeText(
//                    //						applicationContext,
//                    //						"This device supports Play services, App will work normally",
//                    //						Toast.LENGTH_LONG).show();
//                }
//            } catch (NullPointerException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                // TODO: handle exception
//            } catch (RuntimeException e) {
//                e.printStackTrace();
//                // TODO: handle exception
//            }
//            return true;
//        }
//
////        @Override
//        public void onBackPressed() {
//
//            Intent i = new Intent(GooglePlus_Activity.this, HomeActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//            finish();
//
//
//        }
//    }
//}