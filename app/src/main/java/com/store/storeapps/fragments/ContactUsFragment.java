package com.store.storeapps.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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

import java.util.LinkedHashMap;

import static com.store.storeapps.R.id.contactus;
import static com.store.storeapps.R.id.view;

/**
 * Created by Shankar.
 */
public class ContactUsFragment extends Fragment {

    public static final String TAG = "RegistrationFragment";
    private HomeActivity mParent;
    private View rootView;
    EditText Name;
    EditText EmailText;
    EditText Message;
    EditText Subject;
    EditText inputAddCity;
    EditText inputAddState;
    EditText inputAddPincode;
    private String mFrom = "";
    private TextView custom_toast;
    TextView success;
    private View toastRoot;
    private View toastRoot2;
    private Toast toast;
    private Button Contactus;
    TextView alert;
    Button btnAddAddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mParent = (HomeActivity) getActivity();
        if (getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.contactus, container, false);
        // Call toast.xml file for toast layout
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        initUI();
        return rootView;
    }


    private void initUI() {
        toast = new Toast(getActivity());
        Contactus = (Button) rootView.findViewById(R.id.submitcontact);
        Name = (EditText) rootView.findViewById(R.id.name);
        EmailText = (EditText) rootView.findViewById(R.id.emailET);
        Subject = (EditText) rootView.findViewById(R.id.subject);
        Message = (EditText) rootView.findViewById(R.id.message);
        alert = (TextView) rootView.findViewById(R.id.alert);
        success =(TextView)toastRoot2.findViewById(R.id.validtoast) ;
        custom_toast = (TextView) toastRoot.findViewById(R.id.errortoast);
        Contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidFields()){
                    ContactUS(Name.getText().toString(), EmailText.getText().toString(), Subject.getText().toString(),
                            Message.getText().toString());
                }
                Toast.makeText(getActivity(), "Tested", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void ContactUS(String Name, String Emailid, String Subject, String Message) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new PostContactUsAsyncTask(Name, Emailid, Subject, Message).execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }


    class PostContactUsAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String Name;
        private String Emailid;
        private String Subject;
        private String Message;


        public PostContactUsAsyncTask(String Name, String Emailid, String Subject, String Message) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.Name = Name;
            this.Emailid = Emailid;
            this.Subject = Subject;
            this.Message = Message;
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
                paramsList.put("tag1", "contactus");
                paramsList.put("name", Name);
                paramsList.put("email", Emailid);
                paramsList.put("subject", Subject);
                paramsList.put("message", Message);
                result = Utility.httpPostRequestToServer(ApiConstants.CONTACT_US, Utility.getParams(paramsList));
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
                        Log.d("Create Response", jsonobject.toString());
                        String message = jsonobject.getString("message");
                        Utility.navigateDashBoardFragment(new ContactUsFragment(), ContactUsFragment.TAG, null, getActivity());
                        success.setText(message);
                        toast.setView(toastRoot);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                    }
                    mCustomProgressDialog.dismissProgress();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private boolean isValidFields() {
        boolean isValidate = false;
        if ((EmailText.getText().toString().length() < 1) &&
                (Name.getText().toString().length() < 1) &&
                (Subject.getText().toString().length() < 1) &&
                (Message.getText().toString().length() < 1)) {
            custom_toast.setText("Please enter fields");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else if (Name.getText().toString().length() < 1) {
            custom_toast.setText("Please enter Name");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else if (Message.getText().toString().length() < 1) {
            custom_toast.setText("Please select gender");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else if ((EmailText.getText().toString().length() < 1)) {
            custom_toast.setText("Please enter Email id");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else if (!EmailText.getText().toString().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$")) {
            custom_toast.setText("Please Enter Valid Email Id");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else {
            ContactUS(Name.getText().toString(), EmailText.getText().toString(), Subject.getText().toString(),
                    Message.getText().toString());
        }


        return isValidate;
    }


}
