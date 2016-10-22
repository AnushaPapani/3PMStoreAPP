package com.three.pmstore.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by satyanarayana pdv on 10/11/2016.
 */

public class CancelFormFragment extends Fragment {

    public static final String TAG = "CancelFormFragment";
    private View rootView;
    Uri uri;
    Button btn_submit;
    String orderid, cartProdId, orderpcost, orderpimage, pname, pcost, orderStatus, orderdate, cancelReason,
            comments, Uname, U_id, cartId;
    int pos;
    ArrayList<String> m;
    Spinner issueSpinner;
    ArrayList<String> spinnerdataList;
    EditText cancelComments;
    TextView issueAlert,commentAlert,odrid,name,cost,ostatus,odate;
    private HomeActivity mParent;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();
        if (getArguments() != null) {

            orderid = getArguments().getString("orderID");
            cartId = getArguments().getString("cartID");
            cartProdId = getArguments().getString("CartPID");
            orderpimage = getArguments().getString("Pimage");
            pname = getArguments().getString("Pname");
            pcost = getArguments().getString("Pcost");
            orderStatus = getArguments().getString("Orderstatus");
            orderdate = getArguments().getString("Orderdate");
            Uname = getArguments().getString("USername");
            U_id = getArguments().getString("Uid");

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.cancelform, container, false);
//        orderid = MyOrderFragment.orderID;
//        cartId = MyOrderFragment.cartID;
//        cartProdId = MyOrderFragment.CartPID;
//        orderpimage = MyOrderFragment.Pimage;
//        pname = MyOrderFragment.Pname;
//        pcost = MyOrderFragment.Pcost;
//        orderStatus = MyOrderFragment.Orderstatus;
//        orderdate = MyOrderFragment.Orderdate;
//        Uname = MyOrderFragment.USername;
//        U_id = MyOrderFragment.Uid;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        spinnerdataList = new ArrayList<String>();
        m = new ArrayList<String>();

        btn_submit = (Button) rootView.findViewById(R.id.cancelProduct);
        issueSpinner = (Spinner) rootView.findViewById(R.id.issueSpinner);
        cancelComments = (EditText)rootView.findViewById(R.id.cancelCommentsET);
        issueAlert =(TextView) rootView.findViewById(R.id.issueAlert);
        commentAlert =(TextView) rootView.findViewById(R.id.commentAlert);
        odrid = (TextView)rootView.findViewById(R.id.order);
        name =(TextView)rootView.findViewById(R.id.pname);
        cost =(TextView)rootView.findViewById(R.id.total);
        ostatus = (TextView)rootView.findViewById(R.id.status);
        odate = (TextView)rootView.findViewById(R.id.placedOn);
        ImageView productimage = (ImageView)rootView.findViewById(R.id.orderImage);

        odrid.setText(orderid);
        name.setText(pname);
        cost.setText(pcost);
        ostatus.setText(orderStatus);
        odate.setText(orderdate);
        uri = Uri.parse(orderpimage);
        Picasso.with(getActivity()).load(orderpimage.replace(" ", "%20")).into(productimage);

        new GetStatus().execute();
        initUI();
        return rootView;
    }

    private void initUI() {

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos=issueSpinner.getSelectedItemPosition();
                cancelReason =issueSpinner.getSelectedItem().toString();
                comments = cancelComments.getText().toString();

                if(pos == 0&comments.length()<=3)
                {
                            //        		Toast.makeText(getApplicationContext(), "Select Reason for Cancellation", Toast.LENGTH_SHORT).show();
                            issueAlert.setVisibility(View.VISIBLE);
                            commentAlert.setVisibility(View.VISIBLE);
                }
                else
                if(pos == 0)
                {
                            issueAlert.setVisibility(View.VISIBLE);
                            commentAlert.setVisibility(View.GONE);
                }
                else if(comments.length()<=3)
                {
                            issueAlert.setVisibility(View.GONE);
                            commentAlert.setVisibility(View.VISIBLE);
                }
                else {
                    commentAlert.setVisibility(View.GONE);
                    new CancelFormAsyncTask().execute();
                }
            }
        });
    }

    class CancelFormAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public CancelFormAsyncTask() {
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
                paramsList.put("name", Utility.getSharedPrefStringData(getActivity(), Constants.USER_NAME)) ;
                paramsList.put("id", Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID)) ;
                paramsList.put("cancelReason", cancelReason);
                paramsList.put("comments", comments);
                paramsList.put("StatusType", "Cancel");
                paramsList.put("cartId", cartId);
                paramsList.put("Orderid", orderid);
                paramsList.put("cartProdId", cartProdId);

                result = Utility.httpPostRequestToServer(ApiConstants.FORMS_SUBMIT, Utility.getParams(paramsList));


            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                System.out.println("response    " +response);
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject != null) {
//                        JSONObject jObj = new JSONObject(response);
                        String success = jsonobject.getString("success");
                        String message = jsonobject.getString("message");
                        System.out.println("Cancel form Details " + success + " " + message);
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


    class GetStatus extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetStatus() {
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
                Utility.showLog("data", "datadata" + paramsList.toString());
                result = Utility.httpGetRequestToServer(ApiConstants.CANCEL_RESONS);
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
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject != null) {
                        JSONArray jsonArrayReasons = jsonObject.getJSONArray("tbl_cancelReasons");
                        m.add(0, "Select Reason for Cancellation");
                        for (int i = 0; i < jsonArrayReasons.length(); i++) {
                            JSONObject json2 = jsonArrayReasons.getJSONObject(i);
                            String reason_name = json2.getString("Status");
                            spinnerdataList.add(json2.getString("Status"));
                            System.out.println("reason_name" + reason_name);
                            m.add(reason_name);
                        }
                    }
                    issueSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, m));
                    mCustomProgressDialog.dismissProgress();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
