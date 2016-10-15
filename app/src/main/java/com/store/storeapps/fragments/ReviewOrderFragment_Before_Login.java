package com.store.storeapps.fragments;

import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.adapters.ReviewOrderAdapter;
import com.store.storeapps.adapters.ReviewOrderAdapter_Before_Login;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.models.ReviewOrderModel;
import com.store.storeapps.models.ReviewOrderModel_Before_Login;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Shankar.
 */
public class ReviewOrderFragment_Before_Login extends Fragment {
    public static final String TAG = "ReviewOrderFragment_Before_Login";
    private View rootView;
    private ListView listView_selected_orders;
    private RelativeLayout ll_header;
    private LinearLayout ll_fottor;
    private TextView txt_review_your_order;
    public static ArrayList<ReviewOrderModel_Before_Login> reviewOrderModels;
    private ReviewOrderAdapter_Before_Login reviewOrderAdapter;
    private Button Checkout;
    private EditText Promocode;
    private TextView promotext;
    private TextView applypromocode;
    View toastRoot;
    View toastRoot2;
    Toast toast;
    public static TextView Grand_total;
    private LinearLayout ll_address_layout;
    private TextView txt_name;
    private TextView txt_address_line;
    private TextView txt_city;
    private TextView txt_address_state;
    private TextView txt_pin_code;
    private TextView txt_mobile;
    private TextView txt_choose_another;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_review_order, container, false);
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        initUI();
        return rootView;
    }

    private void initUI() {
        listView_selected_orders = (ListView) rootView.findViewById(R.id.listView_selected_orders);
        ll_header = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.
                review_order_header, null);

        ll_fottor = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.
                footer_revieworder, null);
        Promocode = (EditText) ll_fottor.findViewById(R.id.editText1);
        applypromocode = (TextView) ll_fottor.findViewById(R.id.applypromo);
        Checkout = (Button) ll_fottor.findViewById(R.id.proceedtopay);
        Checkout.setText("Checkout");
        ll_address_layout = (LinearLayout) ll_fottor.findViewById(R.id.ll_address_layout);
        ll_address_layout.setVisibility(View.GONE);
        Grand_total =(TextView)ll_fottor.findViewById(R.id.grandtotal);
        txt_name = (TextView) ll_fottor.findViewById(R.id.txt_name);
        txt_address_line = (TextView) ll_fottor.findViewById(R.id.txt_address_line);
        txt_city = (TextView) ll_fottor.findViewById(R.id.txt_city);
        txt_address_state = (TextView) ll_fottor.findViewById(R.id.txt_address_state);
        txt_pin_code = (TextView) ll_fottor.findViewById(R.id.txt_pin_code);
        txt_mobile = (TextView) ll_fottor.findViewById(R.id.txt_mobile);
        txt_choose_another = (TextView) ll_fottor.findViewById(R.id.txt_choose_another);
        txt_review_your_order = (TextView) ll_header.findViewById(R.id.txt_review_your_order);
        txt_review_your_order.setPaintFlags(txt_review_your_order.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        getReviewOrderDetails();


        applypromocode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (applypromocode.getText().equals("Apply") && Promocode.getText().toString().length() > 1) {
                    applypromocode.setText("Cancel");
                    promotext.setVisibility(View.VISIBLE);
//                    cart_id =HomeActivity.mCartId.toString();
                    Promocode.setEnabled(false);
                    promocodeToServer(Promocode.getText().toString(),Grand_total.getText().toString(),HomeActivity.mCartId.toString());

                } else if (applypromocode.getText().equals("Cancel")) {
                    applypromocode.setText("Apply");
                    cancelpromocode(Grand_total.getText().toString(),HomeActivity.mCartId.toString());
                    Promocode.setText("");
                    promotext.setVisibility(View.GONE);
                    Promocode.setEnabled(true);
                    Promocode.setText("");
                } else {
                    TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
                    t.setText("Please enter Promo code");
                    toast.setView(toastRoot);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.navigateDashBoardFragment(new LoginFragment(), LoginFragment.TAG, null, getActivity());
            }
        });
    }


    /*Promocode Check*/
    private void promocodeToServer(String couponcode, String grandtotal,String cartid) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new PromocodeCheck(couponcode,grandtotal,cartid).execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }

    class PromocodeCheck extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String couponcode;
        private String grandtotal;
        private String cartid;


        public PromocodeCheck(String couponcode, String grandtotal,String cartid) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.couponcode = couponcode;
            this.grandtotal = grandtotal;
            this.cartid = cartid;
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
                paramsList.put("P_Code", couponcode);
                paramsList.put("cartValue", grandtotal);
                paramsList.put("cartId", cartid);
                result = Utility.httpPostRequestToServer(ApiConstants.PROMO_CHECK, Utility.getParams(paramsList));
                Utility.setSharedPrefStringData(getActivity(), Constants.GRANDTOTAL, grandtotal.toString());

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
                        String disount_price =jsonobject.getString("price");
                        Grand_total.setText(disount_price);
                        promotext.setText(jsonobject.getString("status"));
                    } else {
                        Utility.showToastMessage(getActivity(), jsonobject.optString("message"));
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    /*Delete Promocode*/
    private void cancelpromocode(String cartid, String cartvalue) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new CancelPromocode(cartid,cartvalue).execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }

    class CancelPromocode extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String cartid;
        private String cartvalue;



        public CancelPromocode(String cartid, String cartvalue) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.cartid = cartid;
            this.cartvalue = cartvalue;

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
                paramsList.put("cartValue", cartvalue);
                paramsList.put("cartId", cartid);
                result = Utility.httpPostRequestToServer(ApiConstants.DELETE_PROMOCODE, Utility.getParams(paramsList));
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
                        Grand_total.setText(Utility.getSharedPrefStringData(getActivity(), Constants.GRANDTOTAL));
                    } else {
                        Utility.showToastMessage(getActivity(), jsonobject.optString("message"));
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
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
                result = Utility.httpGetRequestToServer(ApiConstants.CHECKOUT_NEW + "?cartId=" + HomeActivity.mCartId);
                //result = Utility.httpGetRequestToServer(ApiConstants.CHECKOUT_NEW + "?cartId=CT152672");
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
                            ReviewOrderModel_Before_Login reviewOrderModel = new ReviewOrderModel_Before_Login();
                            reviewOrderModel.setP_ID(jsonObject.getString("P_ID"));
                            reviewOrderModel.setMax_Quantity(jsonObject.optInt("Max_Quantity"));
                            reviewOrderModel.setP_Cost(jsonObject.optInt("P_Cost"));
                            reviewOrderModel.setP_Qty(jsonObject.optInt("P_Qty"));
                            reviewOrderModel.setP_Name(jsonObject.getString("P_Name"));
                            reviewOrderModel.setP_Image(jsonObject.getString("P_Image"));
                            reviewOrderModel.setCart_Prod_ID(jsonObject.getString("Cart_Prod_ID"));

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
                        reviewOrderAdapter = new ReviewOrderAdapter_Before_Login(getActivity(), reviewOrderModels);
                        listView_selected_orders.setAdapter(reviewOrderAdapter);
                        listView_selected_orders.addHeaderView(ll_header);
                        listView_selected_orders.addFooterView(ll_fottor);
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
