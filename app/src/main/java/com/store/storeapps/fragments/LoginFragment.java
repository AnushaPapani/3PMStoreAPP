package com.store.storeapps.fragments;


import android.content.SharedPreferences;
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
import com.store.storeapps.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;


/**
 * Created by Shankar.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "HomeFragment";
    private View rootView;
    public static String UID;
    public static String USERNAME;
    public static String EMAILID;
    public static boolean IsUid;
    public static boolean IsUsername;
    public static boolean IsEmailid;
//    private TextView txt_login;
//    private TextView txt_email_id;
    private TextView txt_password;
    private TextView txt_register_link;
    public static String Emailid, Username;
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
//        txt_login = (TextView) rootView.findViewById(R.id.txt_login);
//        txt_email_id = (TextView) rootView.findViewById(R.id.txt_email_id);
        txt_password = (TextView) rootView.findViewById(R.id.forgotpasswordlink);
        txt_register_link = (TextView) rootView.findViewById(R.id.registerlink);
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
                    if (jsonobject != null) {
                        JSONObject jObj = new JSONObject(response);
                        String uids = jObj.getString("ID");
                        JSONObject user = jObj.getJSONObject("user");
                        Username = user.getString("fullname");
                        Emailid = user.getString("email");
                        String adcount =jObj.getString("count");
                        Utility.setSharedpreferences(getContext(),UID,IsUid);
                        Utility.setSharedpreferences(getContext(),USERNAME,IsUsername);
                        Utility.setSharedpreferences(getContext(),EMAILID,IsUid);

                        System.out.println("loguser "+Utility.getSharedPrefStringData(getContext(),USERNAME));

                    }
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
