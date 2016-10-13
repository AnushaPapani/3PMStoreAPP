package com.store.storeapps.fragments;


import android.app.DatePickerDialog.OnDateSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.DatePickerFragment;
import com.store.storeapps.utility.Utility;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.LinkedHashMap;

import static java.util.Calendar.DAY_OF_MONTH;


/**
 * Created by Shankar.
 */
public class AddAddressFragment extends Fragment {

    public static final String TAG = "RegistrationFragment";
    private HomeActivity mParent;
    private View rootView;
    EditText inputAddName;
    EditText inputAddMobile;
    EditText inputAddAddress1;
    EditText inputAddAddress2;
    EditText inputAddCity;
    EditText inputAddState;
    EditText inputAddPincode;
    private String mFrom = "";
    private TextView custom_toast;
    private View toastRoot;
    private View toastRoot2;
    private Toast toast;
    Button btnAddAddress;

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
        rootView = inflater.inflate(R.layout.fragment_addaddress, container, false);
        // Call toast.xml file for toast layout
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        initUI();
        return rootView;
    }


    private void initUI() {
        inputAddName = (EditText) rootView.findViewById(R.id.addressname);
        inputAddMobile = (EditText) rootView.findViewById(R.id.addressmobile);
        inputAddAddress1 = (EditText) rootView.findViewById(R.id.address1);
        inputAddAddress2 = (EditText) rootView.findViewById(R.id.address2);
        inputAddCity = (EditText) rootView.findViewById(R.id.addresscity);
        inputAddState = (EditText) rootView.findViewById(R.id.addressstate);
        inputAddPincode = (EditText) rootView.findViewById(R.id.addresspincode);
        btnAddAddress = (Button) rootView.findViewById(R.id.addressbutton);

        btnAddAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //new CreateNewAddress().execute();
                if (isNotValidFields()) {
                    isNotValidFields();
                }else {
                    CreateNewAddress(inputAddName.getText().toString(),inputAddMobile.getText().toString(),inputAddAddress1.getText().toString(),
                            inputAddAddress2.getText().toString(),inputAddCity.getText().toString(),inputAddState.getText().toString(),inputAddPincode.getText().toString()
                            );
                }


            }

        });
    }

    private void CreateNewAddress(String addrname, String addrmobile, String addr1, String addr2, String addrcity, String addrstate, String addrpincode) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new PostRegisterAddressAsyncTask(addrname, addrmobile, addr1, addr2, addrcity,addrstate,addrpincode).execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }


    class PostRegisterAddressAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String addrname;
        private String addrmobile;
        private String addr1;
        private String addr2;
        private String addrcity;
        private String addrstate;
        private String addrpincode;
        RadioGroup gender;
        String genderString;

        public PostRegisterAddressAsyncTask(String addrname, String addrmobile, String addr1, String addr2, String addrcity, String addrstate, String addrpincode) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.addrname = addrname;
            this.addrmobile = addrmobile;
            this.addr1 = addr1;
            this.addr2 =addr2;
            this.addrcity=addrcity;
            this.addrstate=addrstate;
            this.addrpincode=addrpincode;


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
                paramsList.put("User_ID", Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID));
                paramsList.put("username", addrname);
                paramsList.put("bmobile", addrmobile);
                paramsList.put("bline", addr1+addr2);
                paramsList.put("bcity", addrcity);
                paramsList.put("bstate", addrstate);
                paramsList.put("bpincode", addrpincode);
                result = Utility.httpPostRequestToServer(ApiConstants.ADD_ADDRESS, Utility.getParams(paramsList));
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    Log.d("Create Response", jsonobject.toString());
//                    if (jsonobject.optString("success").equalsIgnoreCase("1")) {
//                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_ID, jsonobject.optString("ID"));
//                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_LOGIN_COUNT, jsonobject.optString("count"));
//                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_CASH, jsonobject.optString("cash"));
//                        JSONObject userjsonobject = jsonobject.optJSONObject("user");
//                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_NAME, userjsonobject.optString("fullname"));
//                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID, userjsonobject.optString("email"));
//                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_FB_ID, userjsonobject.optString("fb_ID"));
//                        HomeActivity.txt_email.setText(userjsonobject.optString("email"));
//                        HomeActivity.txt_user_name.setText(userjsonobject.optString("fullname"));
//                        if (!mFrom.equalsIgnoreCase("cart")) {
//                            mParent.onBackPressed();
//                        }
//                    } else {
//                        Utility.showToastMessage(getActivity(), jsonobject.optString("message"));
//                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isNotValidFields() {
        boolean isValidate = false;
        if ((inputAddMobile.getText().toString().equals("")) &&
                (inputAddAddress1.getText().toString().equals(""))
                && (inputAddCity.getText().toString().equals("")) &&
                (inputAddState.getText().toString().equals("")) &&
                (inputAddPincode.getText().toString().equals(""))) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter the fields");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        } else if (inputAddMobile.getText().toString().length() < 1) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter Mobile");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        } else if (inputAddAddress1.getText().toString().length() < 1) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter Address");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        } else if (inputAddCity.getText().toString().length() < 1) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter City");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        } else if (inputAddState.getText().toString().length() < 1) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter State");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        } else if (inputAddPincode.getText().toString().length() < 1) {
            //						inputAddPincode.setError( "pincode is required!" );
            //						Toast.makeText(getApplicationContext(),
            //								"Please enter Pincode", Toast.LENGTH_SHORT).show();
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter Pincode");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        } else {

            //						Toast.makeText(getApplicationContext(),
            //								"Please Fill out the details", Toast.LENGTH_SHORT).show();
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please Fill out the details");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        }

        return isValidate;
    }


}
