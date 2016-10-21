package com.three.pmstore.fragments;


import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.customviews.DialogClass;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.DatePickerFragment;
import com.three.pmstore.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.LinkedHashMap;

import static com.three.pmstore.activities.HomeActivity.mCartId;
import static java.util.Calendar.DAY_OF_MONTH;


/**
 * Created by Shankar.
 */
public class RegistrationFragment extends Fragment {

    public static final String TAG = "RegistrationFragment";
    private HomeActivity mParent;
    private View rootView;
    private EditText inputName;
    private RadioButton inputMale;
    private RadioButton inputFemale;
    private EditText inputEmail;
    private EditText inputPassword;
    private RadioGroup gender;
    private EditText et;
    private Button btnRegisterUser;
    private Button btn_sign_up;
    Calendar cal;
    private int day;
    private int month;
    private int year;
    private String mFrom = "";
    private TextView custom_toast;
    private View toastRoot;
    private View toastRoot2;
    private Toast toast;
    RadioButton selectRadio;
//    String getGender;
    String get_gender="";
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
        rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        // Call toast.xml file for toast layout
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        toast = new Toast(getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initUI();
        return rootView;
    }


    private void initUI() {
        inputName = (EditText) rootView.findViewById(R.id.inputName);
        inputEmail = (EditText) rootView.findViewById(R.id.inputEmail);
        inputPassword = (EditText) rootView.findViewById(R.id.inputPassword);
        inputMale = (RadioButton) rootView.findViewById(R.id.inputMale);
        inputFemale = (RadioButton) rootView.findViewById(R.id.inputFemale);
        gender = (RadioGroup) rootView.findViewById(R.id.gender);
        cal = Calendar.getInstance();
        day = cal.get(DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText) rootView.findViewById(R.id.editText);
        btnRegisterUser = (Button) rootView.findViewById(R.id.btnRegisterUser);
        custom_toast = (TextView) toastRoot.findViewById(R.id.errortoast);

        selectRadio = (RadioButton) rootView.findViewById(gender
                .getCheckedRadioButtonId());
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();

                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);


                    if(btn.getId()==R.id.inputMale){
                        btn.setText("Male");
                    }else{
                        btn.setText("Female");
                    }
                    if (btn.getId() == checkedId) {

                        get_gender=btn.getText().toString();// here gender will contain M or F.

                    }

                }

                Log.e("Gender",get_gender);
            }
        });

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidFields()) {
                    CreateNewUser(inputName.getText().toString(), inputEmail.getText().toString(), inputPassword.getText().toString()
                    );
                }
            }
        });

    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);

        date.show(getActivity().getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            et.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / "
                    + year);
        }
    };


    private void CreateNewUser(String name, String emailid, String password) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new PostRegisternAsyncTask(name, emailid, password).execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }


    class PostRegisternAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String name;
        private String password;
        private String emailid;
        RadioGroup gender;
        String genderString;

        public PostRegisternAsyncTask(String name, String emailid, String password) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.name = name;
            this.emailid = emailid;
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
                paramsList.put("email", emailid);
                paramsList.put("fullname", name);
                paramsList.put("password", password);
                paramsList.put("gender", get_gender);
                paramsList.put("cartId", mCartId);
                result = Utility.httpPostRequestToServer(ApiConstants.REGISTER, Utility.getParams(paramsList));
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
                    if (jsonobject.optString("success").equalsIgnoreCase("1")) {
                        Utility.setSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT, jsonobject.optString("count"));
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_ID, jsonobject.optString("ID"));
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_LOGIN_COUNT, jsonobject.optString("count"));
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_CASH, jsonobject.optString("cash"));
                        JSONObject userjsonobject = jsonobject.optJSONObject("user");
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_NAME, userjsonobject.optString("fullname"));
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID, userjsonobject.optString("email"));
                        Utility.setSharedPrefStringData(getActivity(), Constants.USER_FB_ID, userjsonobject.optString("fb_ID"));
                        TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
                        t.setText("Registration Successfull");
                        toast.setView(toastRoot2);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                        toast.setDuration(20000);
                        toast.show();
                        Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, mParent);

                        if (Utility.isValueNullOrEmpty(mCartId)) {
                            Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, mParent);
                        } else {
//                            Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());
                            Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, mParent);
                        }

                    }
                    else if (jsonobject.optString("success").equalsIgnoreCase("0")) {
                        //				Toast.makeText(getApplicationContext(), "Email ID Already Exists", 9000).show();
                        TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
                        t.setText("Email ID Already Exists");
                        toast.setView(toastRoot);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                        toast.setDuration(20000);
                        toast.show();
                    } else {
                        Utility.navigateDashBoardFragment(new RegistrationFragment(), RegistrationFragment.TAG, null, getActivity());
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
        if ((inputEmail.getText().toString().length() < 1) &&
                (inputName.getText().toString().length() < 1) &&
                (inputPassword.getText().toString().length() < 8) &&
                (gender.getCheckedRadioButtonId() == -1)) {
            custom_toast.setText("Please enter fields");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else if (inputName.getText().toString().length() < 1) {
            custom_toast.setText("Please enter Name");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else if (gender.getCheckedRadioButtonId() == -1) {
            custom_toast.setText("Please select gender");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else if ((inputEmail.getText().toString().length() < 1)) {
            custom_toast.setText("Please enter Email id");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else if (!inputEmail.getText().toString().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$")) {
            custom_toast.setText("Please Enter Valid Email Id");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else if (inputPassword.getText().toString().length() < 1) {
            custom_toast.setText("Please enter password");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else if (inputPassword.getText().toString().length() < 8) {
            custom_toast.setText("Password must be 8 characters");
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return isValidate;
        } else {
            isValidate =true;
        }


        return isValidate;
    }


}
