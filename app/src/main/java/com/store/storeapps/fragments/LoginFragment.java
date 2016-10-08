package com.store.storeapps.fragments;


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
import com.store.storeapps.utility.Utility;


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
