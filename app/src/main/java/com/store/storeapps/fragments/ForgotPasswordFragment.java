package com.store.storeapps.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.LinkedHashMap;


public class ForgotPasswordFragment extends Fragment {
    public static final String TAG = "ForgotPasswordFragment";
    View rootView;
    View toastRoot;
    View toastRoot2;
    Toast toast;
    private EditText email;
    private TextView alert;
    private Button resetpass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.forgotpassword, container, false);
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        initUI();
        return rootView;
    }

    private void initUI() {
        toast = new Toast(getActivity());

        email = (EditText) rootView.findViewById(R.id.forpas);
        alert = (TextView) rootView.findViewById(R.id.alert);
        resetpass = (Button) rootView.findViewById(R.id.respass);
        TextView login = (TextView) rootView.findViewById(R.id.bktolog);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Utility.navigateDashBoardFragment(new LoginFragment(), LoginFragment.TAG, null, getActivity());
            }

        });

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidFields()) {
                    Recoverpassword(email.getText().toString());

                }
            }

        });

    }
    private void Recoverpassword(String emailid) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new RecoverpasswordAsynctask(emailid).execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }

    class RecoverpasswordAsynctask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String emailid;

        public RecoverpasswordAsynctask(String emailid) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.emailid = emailid;
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
                paramsList.put("tag", "forpass");
                paramsList.put("forgotpassword", emailid);
                result = Utility.httpPostRequestToServer(ApiConstants.FORGOT_PASSWORD, Utility.getParams(paramsList));
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
                        String res = jsonobject.getString("success");
                        String red = jsonobject.getString("error");

                        if(Integer.parseInt(res) == 1){
                            TextView t =(TextView)toastRoot2.findViewById(R.id.validtoast);
                            t.setText("A recovery mail has been sent to your email id");
                            toast.setView(toastRoot2);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
                            toast.setDuration(20000);
                            toast.show();
                            //                        alert.setText("A recovery email is sent to you, see it for more details.");
                        }
                        else if (Integer.parseInt(red) == 2)
                        {
                            TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
                            t.setText("Your email id does not exist in our database.");
                            toast.setView(toastRoot);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
                            toast.setDuration(20000);
                            toast.show();
                            //                        alert.setText("Your email does not exist in our database.");
                        }
                        else {
                            TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
                            t.setText("Error occured in changing Password");
                            toast.setView(toastRoot);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
                            toast.setDuration(20000);
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
        if (email.getText().toString().equals("")) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter your Email Id");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(20000);
            toast.show();
            return isValidate;
        } else if (!email.getText().toString().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$")) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter Valid Email Id");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(20000);
            toast.show();
            return isValidate;

        } else {
            return true;
        }
    }


}




