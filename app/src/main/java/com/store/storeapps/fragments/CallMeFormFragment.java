package com.store.storeapps.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
 * Created by satyanarayana pdv on 10/11/2016.
 */

public class CallMeFormFragment extends Fragment {

    public static final String TAG = "CallMeFormFragment";
    private View rootView;
    Button btn_submit;
    String orderid, cartProdId , primary,secondary, orderpcost, cartId;
    EditText edtxt_primaryMobile, edtxt_secondaryMobile;
    TextView txt_primarytext, txt_secondarytext, txt_orderid, txt_pcost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.callmeform, container, false);
        orderid = MyOrderFragment.orderID;
        cartProdId = MyOrderFragment.CartPID;
        cartId = MyOrderFragment.cartID;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initUI();
        return rootView;
    }

    private void initUI() {
        txt_primarytext = (TextView) rootView.findViewById(R.id.primaryText);
        txt_secondarytext = (TextView) rootView.findViewById(R.id.secondaryText);
        txt_orderid = (TextView) rootView.findViewById(R.id.secondaryText);
        txt_pcost = (TextView) rootView.findViewById(R.id.secondaryText);
        btn_submit = (Button) rootView.findViewById(R.id.submit);

        edtxt_primaryMobile = (EditText) rootView.findViewById(R.id.primaryMobile);
        edtxt_secondaryMobile = (EditText) rootView.findViewById(R.id.secondaryMobile);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primary = edtxt_primaryMobile.getText().toString();
                secondary = edtxt_secondaryMobile.getText().toString();

                if (primary.length() < 6 & secondary.length() < 6) {
                    txt_primarytext.setVisibility(View.VISIBLE);
                    txt_secondarytext.setVisibility(View.VISIBLE);

                } else if (primary.length() < 6) {
                    txt_primarytext.setVisibility(View.VISIBLE);
                    txt_secondarytext.setVisibility(View.GONE);

                } else if (secondary.length() < 6) {
                    txt_secondarytext.setVisibility(View.VISIBLE);
                    txt_primarytext.setVisibility(View.GONE);
                } else {

                    new CallmeFormAsyncTask().execute();
                }
            }
        });
    }

    class CallmeFormAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public CallmeFormAsyncTask() {
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
                    paramsList.put("callmeOrderid", orderid);
                    paramsList.put("existcontact", primary);
                    paramsList.put("altcontact", secondary);
                    paramsList.put("StatusType", "Callme");
                    paramsList.put("cartId", cartId);
                    paramsList.put("cartProdId", cartProdId);

                    paramsList.put("name", Utility.getSharedPrefStringData(getActivity(), Constants.USER_NAME)) ;
                    paramsList.put("id", Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID)) ;

//                ("name", name));
//                ("id", uid));
                    result = Utility.httpPostRequestToServer(ApiConstants.FORMS_SUBMIT, Utility.getParams(paramsList));

            }catch (Exception exception) {
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
                        String success = jObj.getString("success");
                        String message = jObj.getString("message");
                        System.out.println("Call me form Details "+success+ " " +message);
                        if(success.equals("1"))
                        {
                            Utility.navigateDashBoardFragment(new MyOrderFragment(), MyOrderFragment.TAG, null, getActivity());
                        }
                        else
                        {
                            Utility.showToastMessage(getActivity(), "form details not inserted");
                        }
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
