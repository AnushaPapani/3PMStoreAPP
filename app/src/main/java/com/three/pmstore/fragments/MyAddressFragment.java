package com.three.pmstore.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;
import com.three.pmstore.adapters.AddressAdapter;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.models.AddressesModel;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/*
Created by Shankar
 */
public class MyAddressFragment extends Fragment {
    public static final String TAG = "MyAddressFragment";
    private View rootView;
    ListView list;
    public static ArrayList<AddressesModel> addressesModels;
    private AddressAdapter addressAdapter;
    TextView addnew;
    View toastRoot;
    View toastRoot2;
    Toast toast;

    public static String edit_username ="";
    public static String edit_address ="";
    public static String edit_city ="";
    public static String edit_state ="";
    public static String edit_pincode ="";
    public static String edit_mobile ="";
    public static String addressid2 ="";

    private String mSelectedId;
    private HomeActivity mParent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();
        if (getArguments()!=null){
            mSelectedId = getArguments().getString("address_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_myaddress, container, false);
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        initUI();
        return rootView;
    }

    private void initUI() {
        list = (ListView) rootView.findViewById(R.id.list);
        addnew = (TextView) rootView.findViewById(R.id.addanother);
        new GetAddress(Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID)).execute();
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, getActivity());
            }
        });
    }

    class GetAddress extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String userid;
        JSONArray addresseArray = null;

        public GetAddress(String userid) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.userid = userid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
            addressesModels = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                result = Utility.httpPostRequestToServer(ApiConstants.MY_ADDRESS+"?U_id=" + userid, Utility.getParams(paramsList));
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
                        JSONArray tbl_addresses = jsonobject.optJSONArray("tbl_addresses");
                        for (int i = 0; i < tbl_addresses.length(); i++) {
                            JSONObject jsonTblAddresses = tbl_addresses.optJSONObject(i);
                            AddressesModel mAddressesModel = new AddressesModel();
                            mAddressesModel.setID(jsonTblAddresses.optString("ID"));
                            mAddressesModel.setUser_ID(jsonTblAddresses.optString("User_ID"));
                            mAddressesModel.setUsername(jsonTblAddresses.optString("username"));
                            mAddressesModel.setBline(jsonTblAddresses.optString("bline"));
                            mAddressesModel.setBcity(jsonTblAddresses.optString("bcity"));
                            mAddressesModel.setBstate(jsonTblAddresses.optString("bstate"));
                            mAddressesModel.setBpincode(jsonTblAddresses.optString("bpincode"));
                            mAddressesModel.setBmobile(jsonTblAddresses.optString("bmobile"));
                            addressesModels.add(mAddressesModel);
                        }
                        setAddressData();
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setAddressData() {
        addressAdapter = new AddressAdapter(getActivity(), addressesModels, mSelectedId, mParent);
        list.setAdapter(addressAdapter);
    }
}
