package com.store.storeapps.fragments;

import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.store.storeapps.R;
import com.store.storeapps.adapters.ReviewOrderAdapter;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.models.ReviewOrderModel;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Shankar.
 */
public class ReviewOrderFragment extends Fragment {
    public static final String TAG = "ReviewOrderFragment";
    private View rootView;
    private ListView listView_selected_orders;
    private RelativeLayout ll_header;
    private TextView txt_review_your_order;
    private ArrayList<ReviewOrderModel> reviewOrderModels;
    private ReviewOrderAdapter reviewOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_review_order, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        listView_selected_orders = (ListView) rootView.findViewById(R.id.listView_selected_orders);
        ll_header = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.
                review_order_header, null);
        txt_review_your_order = (TextView) ll_header.findViewById(R.id.txt_review_your_order);
        txt_review_your_order.setPaintFlags(txt_review_your_order.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        getReviewOrderDetails();
    }

    private void getReviewOrderDetails() {
        if (Utility.isNetworkAvailable(getActivity())) {
            new GetReviewOrderAsyncTask().execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }

    class GetReviewOrderAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetReviewOrderAsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
            reviewOrderModels = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                Utility.showLog("data", "datadata" + paramsList.toString());
                result = Utility.httpGetRequestToServer(ApiConstants.CHECKOUT_NEW+"?cartId=CT152525");
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
                        JSONArray reviewOrdersArray = jsonobject.optJSONArray("tbl_cart");
                        for (int i = 0; i < reviewOrdersArray.length(); i++) {
                            JSONObject jsonObject = reviewOrdersArray.getJSONObject(i);
                            ReviewOrderModel reviewOrderModel = new ReviewOrderModel();
                            reviewOrderModel.setP_ID(jsonObject.getString("P_ID"));
                            reviewOrderModel.setMax_Quantity(jsonObject.optInt("Max_Quantity"));
                            reviewOrderModel.setP_Cost(jsonObject.optInt("P_Cost"));
                            reviewOrderModel.setP_Qty(jsonObject.optInt("P_Qty"));
                            reviewOrderModel.setP_Name(jsonObject.getString("P_Name"));
                            reviewOrderModel.setP_Image(jsonObject.getString("P_Image"));

                            JSONArray attrType = jsonObject.optJSONArray("Attribute_Type");
                            ArrayList<String> attrValuesArray = new ArrayList<>();
                            if (attrType != null) {
                                for (int j = 0; j < attrType.length(); j++) {
                                    attrValuesArray.add(attrType.optString(j));
                                }
                            }
                            reviewOrderModel.setAttribute_Type(attrValuesArray);

                            JSONArray attrValue = jsonObject.optJSONArray("Attribute_Value");
                            ArrayList<String> attrValueArray = new ArrayList<>();
                            if (attrType != null) {
                                for (int j = 0; j < attrValue.length(); j++) {
                                    attrValueArray.add(attrValue.optString(j));
                                }
                            }
                            reviewOrderModel.setAttribute_Value(attrValueArray);

                            reviewOrderModels.add(reviewOrderModel);
                        }
                        reviewOrderAdapter = new ReviewOrderAdapter(getActivity(), reviewOrderModels);
                        listView_selected_orders.setAdapter(reviewOrderAdapter);
                        listView_selected_orders.addHeaderView(ll_header);
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}