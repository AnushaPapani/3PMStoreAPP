package com.store.storeapps.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;


/**
 * Created by Shankar.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "LoginFragment";
    private HomeActivity mParent;
    private View rootView;
    private TextView txt_password;
    private TextView txt_register_link;
    public static String Emailid, Username;
    private EditText edt_email;
    private EditText edt_password;

    private Button btn_login;
    private Button btn_sign_up;

    private String mFrom = "";

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
        initUI();
        return rootView;
    }

    private void initUI() {
        txt_password = (TextView) rootView.findViewById(R.id.txt_forgot_password);
        txt_register_link = (TextView) rootView.findViewById(R.id.register_link);
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

                break;
        }
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
                        if (!mFrom.equalsIgnoreCase("cart")) {
                            mParent.onBackPressed();
                        }
//                        Intent i=new Intent(getActivity(),HomeActivity.class);
//                        startActivity(i);
                        if ((Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT)))) {
                            Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, getActivity());
                        }
                        else if (Utility.isValueNullOrEmpty(HomeActivity.mCartId)){
                            Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, getActivity());
                        }
                        else {
                            Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());
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
                        Utility.showToastMessage(getActivity(), jsonobject.optString("message"));
                    }
                }
                mCustomProgressDialog.dismissProgress();
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
            Utility.showOKOnlyDialog(getActivity(), "Please Enter Email Id", Utility.getResourcesString(getActivity(), R.string.app_name));
            return isValidate;
        } else if (!edt_email.getText().toString().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$")) {
            Utility.showOKOnlyDialog(getActivity(), "Please Enter Valid Email Id", Utility.getResourcesString(getActivity(), R.string.app_name));
            return isValidate;
        } else if (edt_password.getText().toString().equals("")) {
            Utility.showOKOnlyDialog(getActivity(), "Please Enter Password", Utility.getResourcesString(getActivity(), R.string.app_name));
            return isValidate;
        } else {
            return true;
        }
    }


}
