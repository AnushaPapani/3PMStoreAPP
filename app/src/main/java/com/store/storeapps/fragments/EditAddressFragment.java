package com.store.storeapps.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;


/**
 * Created by BabuRao.
 */
public class EditAddressFragment extends Fragment {

    public static final String TAG = "EditAddressFragment";
    private HomeActivity mParent;
    private View rootView;
    private TextView addname;
    private EditText inputAddName;
    private EditText inputAddMobile;
    private EditText inputAddAddress1;
    private EditText inputAddAddress2;
    private EditText inputAddCity;
    private EditText inputAddState;
    private EditText inputAddPincode;
    Button btnAddAddress;
    private String mFrom = "";
    View toastRoot;
    View toastRoot2;
    Toast toast;
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
        rootView = inflater.inflate(R.layout.fragment_editaddress, container, false);
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        initUI();
        return rootView;
    }

    private void initUI() {
        addname = (TextView)rootView.findViewById(R.id.addname);

        toast = new Toast(getActivity());
        // Edit Text
        inputAddName = (EditText)rootView.findViewById(R.id.addressname);
        inputAddMobile = (EditText) rootView.findViewById(R.id.addressmobile);
        inputAddAddress1 = (EditText) rootView.findViewById(R.id.address1);
        inputAddAddress2 = (EditText) rootView.findViewById(R.id.address2);
        inputAddCity = (EditText) rootView.findViewById(R.id.addresscity);
        inputAddState = (EditText) rootView.findViewById(R.id.addressstate);
        inputAddPincode = (EditText)rootView.findViewById(R.id.addresspincode);
        btnAddAddress = (Button)rootView.findViewById(R.id.save);
        String name =MyAddressFragment.edit_address.toString();
        System.out.println("NAME "+name);
        inputAddName.setText(MyAddressFragment.edit_username);
        inputAddMobile.setText(MyAddressFragment.edit_mobile);
        inputAddAddress1.setText(MyAddressFragment.edit_address);
        inputAddCity.setText(MyAddressFragment.edit_city);
        inputAddState.setText(MyAddressFragment.edit_state);
        inputAddPincode.setText(MyAddressFragment.edit_pincode);




        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidFields()) {
                    isValidFields();
                }else {
                    new SaveAddressDetails().execute();

                }

            }
        });
    }



    class SaveAddressDetails extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;


        public SaveAddressDetails() {
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
            String getMobile =inputAddMobile.getText().toString();
            String getAddress1 =inputAddAddress1.getText().toString();
            String getAddress2 =inputAddAddress2.getText().toString();
            String getCity =inputAddCity.getText().toString();
            String getState =inputAddState.getText().toString();
            String getPincode =inputAddPincode.getText().toString();

            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("ID", MyAddressFragment.addressid2);
                paramsList.put("emobile", getMobile);
                paramsList.put("eline1", getAddress1);
                paramsList.put("eline2", getAddress2);
                paramsList.put("ecity",getCity);
                paramsList.put("estate", getState);
                paramsList.put("epincode", getPincode);
                result = Utility.httpPostRequestToServer(ApiConstants.UPDATE_ADDRESS, Utility.getParams(paramsList));
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

                    } else {
                        Utility.showToastMessage(getActivity(), jsonobject.optString("message"));
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    private boolean isValidFields() {
        boolean isValidate = false;
        if ((inputAddName.getText().toString().equals(""))
                && (inputAddMobile.getText().toString().equals("")) &&
                (inputAddAddress1.getText().toString().equals(""))
                && (inputAddCity.getText().toString().equals("")) &&
                (inputAddState.getText().toString().equals("")) &&
                (inputAddPincode.getText().toString().equals(""))) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter the fields");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(20000);
            toast.show();
            return isValidate;
        } else if (inputAddName.getText().toString().length() < 1) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter name");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(20000);
            toast.show();
            return isValidate;
        } else if (inputAddMobile.getText().toString().length() < 1) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter Mobile");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(20000);
            toast.show();
            return isValidate;
        } else if (inputAddAddress1.getText().toString().length() < 1) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter Address");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(20000);
            toast.show();
            return isValidate;
        } else if (inputAddCity.getText().toString().length() < 1) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter City");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(20000);
            toast.show();
            return isValidate;
        } else if (inputAddState.getText().toString().length() < 1) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter State");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(20000);
            toast.show();
            return isValidate;
        } else if (inputAddPincode.getText().toString().length() < 1) {
            TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
            t.setText("Please enter Pincode");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(20000);
            toast.show();
            return isValidate;
        }
        return isValidate;
    }


}
