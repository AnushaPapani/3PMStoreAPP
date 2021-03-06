package com.three.pmstore.fragments;

import android.content.Intent;
import android.graphics.Paint;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.three.pmstore.R;
import com.three.pmstore.activities.Facebook_Activity;
import com.three.pmstore.activities.HomeActivity;
import com.three.pmstore.adapters.NoOrderFoundAdapter;
import com.three.pmstore.adapters.ReviewOrderAdapter;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.customviews.DialogClass;
import com.three.pmstore.models.AddressesModel;
import com.three.pmstore.models.ReviewOrderModel;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.AppController;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

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
    public static String total_cartvalue;
    public static ListView listView_selected_orders;
    private RelativeLayout ll_header;
    private LinearLayout ll_fottor;
    private TextView txt_review_your_order;
    public static ArrayList<ReviewOrderModel> reviewOrderModels;
    private static ArrayList<AddressesModel> addressesModels;
    private ReviewOrderAdapter reviewOrderAdapter;
    public static Button proceedtopay;
    public static TextView Grand_total;
    public static boolean isPromoCodeApplied = false;
    private EditText Promocode;
    private TextView promotext;
    private TextView applypromocode;
    View toastRoot;
    View toastRoot2;
    Toast toast;
    String cart_id;
    public LinearLayout ll_address_layout;
    public static TextView txt_name;
    public static TextView txt_address_line;
    public static TextView txt_city;
    public static TextView txt_address_state;
    public static TextView txt_pin_code;
    public static TextView txt_mobile;
    public static String addressId;
    public static String Orderid = "";
    public static String CP_ID = "";
    public static String P_ID = "";
    public TextView txt_choose_another;
    private HomeActivity mParent;
    ReviewOrderModel reviewOrderModel;
    public static String coddisablepromo = "0";
    private Facebook_Activity mParent2;
    TextView DiscountText;
    TextView DiscountValue;
    TextView GrandText;
    TextView GrandValue;
    Boolean isDiscount = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_review_order, container, false);
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        toast = new Toast(getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
        proceedtopay = (Button) ll_fottor.findViewById(R.id.proceedtopay);
        Grand_total = (TextView) ll_fottor.findViewById(R.id.grandtotal);
        promotext = (TextView) ll_fottor.findViewById(R.id.promotext);
        ll_address_layout = (LinearLayout) ll_fottor.findViewById(R.id.ll_address_layout);
        txt_name = (TextView) ll_fottor.findViewById(R.id.txt_name);
        txt_address_line = (TextView) ll_fottor.findViewById(R.id.txt_address_line);
        txt_city = (TextView) ll_fottor.findViewById(R.id.txt_city);
        txt_address_state = (TextView) ll_fottor.findViewById(R.id.txt_address_state);
        txt_pin_code = (TextView) ll_fottor.findViewById(R.id.txt_pin_code);
        txt_mobile = (TextView) ll_fottor.findViewById(R.id.txt_mobile);
        txt_choose_another = (TextView) ll_fottor.findViewById(R.id.txt_choose_another);
        getAddress();
//        if (Integer.parseInt(Utility.getSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT)) > 0) {
//            ll_address_layout.setVisibility(View.VISIBLE);
//        } else {
//            ll_address_layout.setVisibility(View.GONE);
//        }

        if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID))) {

        } else {
//            Bundle bundle = new Bundle();
//            bundle.putString("from", "cart");
//            Utility.navigateDashBoardFragment(new LoginFragment(), LoginFragment.TAG, bundle, HomeActivity.this);
        }
        txt_review_your_order = (TextView) ll_header.findViewById(R.id.txt_review_your_order);
        txt_review_your_order.setPaintFlags(txt_review_your_order.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        getReviewOrderDetails();

        if (applypromocode.getText().toString() == "Cancel") {
            promotext.setText(Utility.getSharedPrefStringData(getActivity(), Constants.PROMOCODE));
        } else if (isDiscount = false) {
            promotext.setText(Utility.getSharedPrefStringData(getActivity(), Constants.PROMOCODE));
            applypromocode.setText("Cancel");
        }

        applypromocode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (applypromocode.getText().equals("Apply") && Promocode.getText().toString().length() > 1) {
                    applypromocode.setText("Cancel");
                    promotext.setVisibility(View.VISIBLE);
//                    cart_id =HomeActivity.mCartId.toString();
                    Promocode.setEnabled(false);
                    promocodeToServer(Promocode.getText().toString(), String.valueOf(HomeActivity.mCartTotal), HomeActivity.mCartId.toString());

                } else if (applypromocode.getText().equals("Cancel")) {
                    applypromocode.setText("Apply");
                    cancelpromocode(HomeActivity.mCartId.toString(), String.valueOf(HomeActivity.mCartTotal), Promocode.getText().toString());
                    Promocode.setText("");
                    promotext.setVisibility(View.GONE);
                    Promocode.setEnabled(true);
                    Promocode.setText("");
                    isPromoCodeApplied = false;
                    reviewOrderAdapter.notifyDataSetChanged();
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
        proceedtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insert_reviewOrder(txt_name.getText().toString(), txt_address_line.getText().toString(), txt_city.getText().toString(),
                        txt_address_state.getText().toString(), txt_pin_code.getText().toString(), txt_mobile.getText().toString(),
                        addressId, HomeActivity.mCartId.toString(), String.valueOf(HomeActivity.mCartTotal),
                        Promocode.getText().toString());
            }
        });
    }

    private void Insert_reviewOrder(String name, String addressline, String city, String state, String pincode, String mobile,
                                    String addressId, String cartId, String Grandtotal, String promocode) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new PostReviewOrderAsyncTask(name, addressline, city, state, pincode, mobile, addressId, cartId, Grandtotal, promocode).execute();

        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }

    class PostReviewOrderAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String name;
        private String addressline;
        private String city;
        private String state;
        private String pincode;
        private String mobile;
        private String addressId;
        private String cartId;
        private String Grandtotal;
        private String promocode;

        public PostReviewOrderAsyncTask(String name, String addressline, String city, String state, String pincode, String mobile,
                                        String addressId, String cartId, String Grandtotal, String promocode) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.name = name;
            this.addressline = addressline;
            this.city = city;
            this.state = state;
            this.pincode = pincode;
            this.mobile = mobile;
            this.addressId = addressId;
            this.cartId = cartId;
            this.Grandtotal = Grandtotal;
            this.promocode = promocode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            String pcost = Integer.toString(reviewOrderModel.getP_Cost());
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("addressID", addressId);
                paramsList.put("U_ID", Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID));
                paramsList.put("P_Name", reviewOrderModel.getP_Name().toString());
                paramsList.put("cartValue", Grandtotal);
                paramsList.put("fname", name);
                paramsList.put("cartId", HomeActivity.mCartId);
                paramsList.put("cartValue", String.valueOf(HomeActivity.mCartTotal));
                paramsList.put("TotalCost", String.valueOf(HomeActivity.mCartTotal));
                paramsList.put("bline", addressline);
                paramsList.put("bcity", city);
                paramsList.put("bstate", state);
                paramsList.put("bpincode", pincode);
                paramsList.put("bmobile", mobile);
                paramsList.put("Orderid", Orderid);


                System.out.println("userid " + Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID));
                System.out.println("addresid " + addressId);
                System.out.println("cartid " + cartId);
                System.out.println("grand " + Grandtotal);
                System.out.println("pincode " + pincode);
                System.out.println("adres " + addressline);


                result = Utility.httpPostRequestToServer(ApiConstants.INSERT_REVIEWORDER, Utility.getParams(paramsList));
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

                        Bundle b = new Bundle();
                        String pincode = jsonobject.optString("pincode");
                        String coddisable = jsonobject.optString("coddisable");
                        b.putString("pincode", pincode);
                        b.putString("coddisable", coddisable);
                        JSONArray tbl_delivery = jsonobject.getJSONArray("tbl_delivery");
                        for (int i = 0; i < tbl_delivery.length(); i++) {
                            String TotalCost = tbl_delivery.getJSONObject(i).getString("TotalCost").toString();
                            String ADMIN_COD = tbl_delivery.getJSONObject(i).getString("ADMIN_COD").toString();
                            String pm_Cash = tbl_delivery.getJSONObject(i).getString("3pm_Cash").toString();
                            String bmobile = tbl_delivery.getJSONObject(i).getString("bmobile").toString();
                            String ID = tbl_delivery.getJSONObject(i).getString("ID").toString();
                            Orderid = tbl_delivery.getJSONObject(i).getString("ID").toString();
                            String U_ID = tbl_delivery.getJSONObject(i).getString("U_ID").toString();
                            String amountpayable = tbl_delivery.getJSONObject(i).getString("amountpayable").toString();

                            String name = txt_name.getText().toString();
                            b.putString("TotalCost", TotalCost);
                            b.putString("ADMIN_COD", ADMIN_COD);
                            b.putString("pm_Cash", pm_Cash);
                            b.putString("OrderID", ID);
                            b.putString("bmobile", bmobile);
                            b.putString("U_ID", U_ID);
                            b.putString("amountpayable", amountpayable);
                            b.putString("name", name);
                            System.out.println("TotalCost  " + TotalCost);

                            System.out.println("pm_Cash  " + pm_Cash);
                        }
                        Utility.navigateDashBoardFragment(new PaymentOptionNewFrgament(), PaymentOptionNewFrgament.TAG, b, getActivity());
                    }
                    if (jsonobject.optString("success").equalsIgnoreCase("0")) {

                    }
//                    else {
//
//                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*Promocode Check*/
    private void promocodeToServer(String couponcode, String grandtotal, String cartid) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new PromocodeCheck(couponcode, grandtotal, cartid).execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }

    class PromocodeCheck extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String couponcode;
        private String grandtotal;
        private String cartid;


        public PromocodeCheck(String couponcode, String grandtotal, String cartid) {
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
                HomeActivity.mCartTotal = Integer.parseInt(grandtotal.toString());


            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                DiscountText = (TextView) rootView.findViewById(R.id.DiscountText);
                DiscountValue = (TextView) rootView.findViewById(R.id.DiscountValue);
                GrandText = (TextView) rootView.findViewById(R.id.GrandText);
                GrandValue = (TextView) rootView.findViewById(R.id.GrandValue);
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    promotext.setText("");
                    if (jsonobject.optString("success").equalsIgnoreCase("1")) {
                        Utility.setSharedPrefStringData(getActivity(), Constants.PROMOCODE, couponcode);
                        isDiscount = false;
                        String PromoType = jsonobject.optString("type");
                        if (PromoType.equals("BUY2GET1FREE")) {
                            coddisablepromo = jsonobject.optString("coddisablepromo");
                            //{"success":"1","price":748,"type":"BUY2GET1FREE","P_ID":"PM010247","CP_ID":"CP153843","remAmount":0,"minAmount":"199"}
                            int price = jsonobject.getInt("price");
                            String P_ID_DB = jsonobject.optString("P_ID");
                            String CP_ID_DB = jsonobject.optString("CP_ID");
                            int remAmount = jsonobject.getInt("remAmount");

                            int minAmount = jsonobject.getInt("minAmount");
                            String ammt = Integer.toString(minAmount);
                            DiscountText.setVisibility(View.VISIBLE);
                            DiscountValue.setVisibility(View.VISIBLE);
                            GrandText.setVisibility(View.VISIBLE);
                            GrandValue.setVisibility(View.VISIBLE);

                            int amt = price + minAmount;
                            DiscountValue.setText("- " + minAmount);
                            GrandValue.setText("" + price);
                            Grand_total.setText("" + amt);
                            HomeActivity.mCartTotal = price;
                            promotext.setText(jsonobject.getString("status"));
                        } else {
                            coddisablepromo = "0";
                            DiscountText.setVisibility(View.GONE);
                            DiscountValue.setVisibility(View.GONE);
                            GrandText.setVisibility(View.GONE);
                            GrandValue.setVisibility(View.GONE);
                            TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
                            t.setText(jsonobject.getString("status"));
                            toast.setView(toastRoot2);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.show();
                            String discount_price = jsonobject.getString("price");
                            Grand_total.setText(discount_price);
                            HomeActivity.mCartTotal = Integer.parseInt(discount_price);
                            promotext.setText(jsonobject.getString("status"));
                        }
                        isPromoCodeApplied = true;
                        reviewOrderAdapter.notifyDataSetChanged();
                    } else if (jsonobject.optString("success").equalsIgnoreCase("2")) {
                        coddisablepromo = "0";
                        TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
                        t.setText(jsonobject.getString("status"));
                        toast.setView(toastRoot2);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                        isPromoCodeApplied = false;
                        promotext.setText(jsonobject.getString("status"));
                        reviewOrderAdapter.notifyDataSetChanged();
                    } else {
                        coddisablepromo = "0";
                        TextView t = (TextView) toastRoot.findViewById(R.id.errortoast);
                        t.setText(jsonobject.getString("status"));
                        toast.setView(toastRoot);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                        isPromoCodeApplied = false;
                        promotext.setText(jsonobject.getString("status"));
                        reviewOrderAdapter.notifyDataSetChanged();
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
    private void cancelpromocode(String cartid, String cartvalue, String promoCode) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new CancelPromocode(cartid, cartvalue, promoCode).execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }

    class CancelPromocode extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String cartid;
        private String cartvalue;
        private String promoCode;


        public CancelPromocode(String cartid, String cartvalue, String promoCode) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.cartid = cartid;
            this.cartvalue = cartvalue;
            this.promoCode = promoCode;
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
                paramsList.put("promo", promoCode);
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
                        Grand_total.setText(jsonobject.optString("cartValue"));
                        HomeActivity.mCartTotal = Integer.parseInt(jsonobject.optString("cartValue"));
                        promotext.setText("");
                        DiscountText.setVisibility(View.GONE);
                        DiscountValue.setVisibility(View.GONE);
                        GrandText.setVisibility(View.GONE);
                        GrandValue.setVisibility(View.GONE);
                        coddisablepromo = "0";

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
                //result = Utility.httpGetRequestToServer(ApiConstants.CHECKOUT_NEW + "?cartId=CT153289");
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
                            reviewOrderModel = new ReviewOrderModel();
                            reviewOrderModel.setP_ID(jsonObject.getString("P_ID"));
                            reviewOrderModel.setMax_Quantity(jsonObject.optInt("Max_Quantity"));
                            reviewOrderModel.setP_Cost(jsonObject.optInt("P_Cost"));
                            reviewOrderModel.setP_Qty(jsonObject.optInt("P_Qty"));
                            reviewOrderModel.setP_Name(jsonObject.getString("P_Name"));
                            reviewOrderModel.setP_Image(jsonObject.getString("P_Image"));
                            reviewOrderModel.setCart_Prod_ID(jsonObject.getString("Cart_Prod_ID"));
//                            reviewOrderModel.setPR_ID(jsonObject.getString("Cart_Prod_ID"));
//                            reviewOrderModel.setCP_ID(jsonObject.getString("Cart_Prod_ID"));
                            total_cartvalue = jsonobject.getString("cartValue");
                            HomeActivity.mCartTotal = jsonobject.getInt("cartValue");

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
                        System.out.println("cartvalue " + total_cartvalue);
                        Grand_total.setText(total_cartvalue);
                        HomeActivity.mCartTotal = Integer.parseInt(total_cartvalue);
                        if (reviewOrderModels.size() > 0) {
                            reviewOrderAdapter = new ReviewOrderAdapter(getActivity(), reviewOrderModels, mParent);
                            listView_selected_orders.setAdapter(reviewOrderAdapter);
                            listView_selected_orders.addHeaderView(ll_header);

                        } else {
                            listView_selected_orders.setAdapter(new NoOrderFoundAdapter(mParent));
                            listView_selected_orders.addHeaderView(ll_header);
                            HomeActivity.mCartValue = 0;
                        }
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getAddress() {
        if (Utility.isNetworkAvailable(getActivity())) {
            new GetCheckout_AddressAsyncTask().execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }


    class GetCheckout_AddressAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetCheckout_AddressAsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            addressesModels = new ArrayList<>();
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
                result = Utility.httpGetRequestToServer(ApiConstants.CHECKOUT_ADDRESS + "?U_id=" + Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID));
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
//                            mAddressesModel.setCP_ID("");
                            addressesModels.add(mAddressesModel);
                        }
//                        HomeActivity.global.getBaddress() ==
//                        if ((HomeActivity.global.getBaddress()== null)) {
                        setAddressData();
//                        } else {
//                            txt_name.setText(Utility.capitalizeFirstLetter(Constants.USER_NAME));
//                            txt_address_line.setText(Utility.capitalizeFirstLetter(HomeActivity.global.getBaddress()).toString());
//                            txt_city.setText(Utility.capitalizeFirstLetter(HomeActivity.global.getBcity()).toString());
//                            txt_address_state.setText(Utility.capitalizeFirstLetter(HomeActivity.global.getBstate()).toString());
//                            txt_pin_code.setText(HomeActivity.global.getBpincode().toString());
//                            txt_mobile.setText(HomeActivity.global.getBmobile().toString());
//                            addressId = HomeActivity.global.getAddressID().toString();
//                        }
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

    private void setAddressData() {

        txt_name.setText(Utility.capitalizeFirstLetter(addressesModels.get(0).getUsername()));
        txt_address_line.setText(Utility.capitalizeFirstLetter(addressesModels.get(0).getBline()));
        txt_city.setText(Utility.capitalizeFirstLetter(addressesModels.get(0).getBcity()));
        txt_address_state.setText(Utility.capitalizeFirstLetter(addressesModels.get(0).getBstate()));
        txt_pin_code.setText(addressesModels.get(0).getBpincode());
        txt_mobile.setText(addressesModels.get(0).getBmobile());
        addressId = addressesModels.get(0).getID();

        txt_choose_another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("address_id", "" + addressesModels.get(0).getID());
                Utility.navigateDashBoardFragment(new MyAddressFragment(), MyAddressFragment.TAG, bundle, getActivity());
            }
        });
        listView_selected_orders.addFooterView(ll_fottor);
    }


}
