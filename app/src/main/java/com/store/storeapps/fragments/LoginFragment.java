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

    public static final String TAG = "HomeFragment";
    private View rootView;

    private TextView txt_login;
    private TextView txt_email_id;
    private TextView txt_password;
    private TextView txt_register_link;

    private EditText edt_email;
    private EditText edt_password;

    private Button btn_login;
    private Button btn_sign_up;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        txt_login = (TextView) rootView.findViewById(R.id.txt_login);
        txt_email_id = (TextView) rootView.findViewById(R.id.txt_email_id);
        txt_password = (TextView) rootView.findViewById(R.id.txt_password);
        txt_register_link = (TextView) rootView.findViewById(R.id.txt_register_link);
        edt_email = (EditText) rootView.findViewById(R.id.edt_email);
        edt_password = (EditText) rootView.findViewById(R.id.edt_password);
        btn_login = (Button) rootView.findViewById(R.id.btn_login);
        btn_sign_up = (Button) rootView.findViewById(R.id.btn_sign_up);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (isValidFields()) {
                    loginDataToServer();

                }
                break;
            case R.id.btn_sign_up:

                break;
        }
    }

    private void loginDataToServer() {
            new PostLoginData().execute();
    }

    private class PostLoginData extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String cartid;
        public PostLoginData() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
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
                paramsList.put("email",edt_email.getText().toString());
                paramsList.put("password",edt_password.getText().toString());
                paramsList.put("cartId",HomeActivity.mCartId);

                result = Utility.httpPostRequestToServer(ApiConstants.INSERT_CHECK_PRODUCTS,  Utility.getParams(paramsList));
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
                    HomeActivity.mCartId = jsonobject.optString("cartId");
                    HomeActivity.mCartValue = jsonobject.optInt("cartCount");
                    HomeActivity.cart_layout_button_set_text.setText(""+HomeActivity.mCartValue);
                    Utility.showToastMessage(getActivity(), "Login done Successfully");
                    cartid =HomeActivity.mCartId;

                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
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
