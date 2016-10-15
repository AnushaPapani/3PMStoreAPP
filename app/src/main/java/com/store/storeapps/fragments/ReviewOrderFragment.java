package com.store.storeapps.fragments;

import android.graphics.Paint;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.adapters.NoOrderFoundAdapter;
import com.store.storeapps.adapters.ReviewOrderAdapter;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.models.AddressesModel;
import com.store.storeapps.models.ReviewOrderModel;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.apache.http.message.BasicNameValuePair;
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
    private Button proceedtopay;
    public static TextView Grand_total;
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
    public TextView txt_choose_another;
    private HomeActivity mParent;
    ReviewOrderModel reviewOrderModel;
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
        promotext =(TextView)ll_fottor.findViewById(R.id.promotext);
        ll_address_layout = (LinearLayout) ll_fottor.findViewById(R.id.ll_address_layout);
        txt_name = (TextView) ll_fottor.findViewById(R.id.txt_name);
        txt_address_line = (TextView) ll_fottor.findViewById(R.id.txt_address_line);
        txt_city = (TextView) ll_fottor.findViewById(R.id.txt_city);
        txt_address_state = (TextView) ll_fottor.findViewById(R.id.txt_address_state);
        txt_pin_code = (TextView) ll_fottor.findViewById(R.id.txt_pin_code);
        txt_mobile = (TextView) ll_fottor.findViewById(R.id.txt_mobile);
        txt_choose_another = (TextView) ll_fottor.findViewById(R.id.txt_choose_another);

        if (Integer.parseInt(Utility.getSharedPrefStringData(getActivity(), Constants.ADDRESS_COUNT)) > 0) {
            ll_address_layout.setVisibility(View.VISIBLE);
        } else {
            ll_address_layout.setVisibility(View.GONE);
        }

        if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID))) {

        } else {
//            Bundle bundle = new Bundle();
//            bundle.putString("from", "cart");
//            Utility.navigateDashBoardFragment(new LoginFragment(), LoginFragment.TAG, bundle, HomeActivity.this);
        }
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
                    promocodeToServer(Promocode.getText().toString(), Grand_total.getText().toString(), HomeActivity.mCartId.toString());

                } else if (applypromocode.getText().equals("Cancel")) {
                    applypromocode.setText("Apply");
                    cancelpromocode(Grand_total.getText().toString(), HomeActivity.mCartId.toString());
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
        proceedtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insert_reviewOrder(txt_name.getText().toString(), txt_address_line.getText().toString(), txt_city.getText().toString(),
                        txt_address_state.getText().toString(),txt_pin_code.getText().toString(),txt_mobile.getText().toString(),
                        addressesModels.get(0).getID().toString(),HomeActivity.mCartId.toString(),Grand_total.getText().toString(),
                        Promocode.getText().toString());
            }
        });
    }

    private void Insert_reviewOrder(String name, String addressline, String city, String state, String pincode, String mobile,
                                    String addressId, String cartId, String Grandtotal, String promocode ) {
        if (Utility.isNetworkAvailable(getActivity())) {
            new PostReviewOrderAsyncTask(name, addressline, city, state,pincode,mobile,addressId,cartId,Grandtotal,promocode).execute();

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
            this.state =state;
            this.pincode =pincode;
            this.mobile =mobile;
            this.addressId =addressId;
            this.cartId =cartId;
            this.Grandtotal =Grandtotal;
            this.promocode =promocode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            String pcost =Integer.toString(reviewOrderModel.getP_Cost());
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("addressID", addressId);
                paramsList.put("U_ID", Utility.getSharedPrefStringData(getActivity(),Constants.USER_ID));
                paramsList.put("P_Name",reviewOrderModel.getP_Name().toString() );
                paramsList.put("cartValue", Grandtotal);
                paramsList.put("fname", name);
                paramsList.put("cartId", HomeActivity.mCartId);
                paramsList.put("bline",addressline);
                paramsList.put("bcity",city);
                paramsList.put("bstate",state);
                paramsList.put("bpincode",pincode);
                paramsList.put("bmobile",mobile);


                System.out.println("userid "+Utility.getSharedPrefStringData(getActivity(),Constants.USER_ID));
                System.out.println("addresid "+addressId);
                System.out.println("cartid "+cartId);
                System.out.println("grand "+Grandtotal);
                System.out.println("name "+name);
                System.out.println("adres "+addressline);


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


                    }
                    if (jsonobject.optString("success").equalsIgnoreCase("0")) {

                    } else {
                        Utility.navigateDashBoardFragment(new PaymentOptionNewFrgament(), PaymentOptionNewFrgament.TAG, null, getActivity());
                    }
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
                        String disount_price = jsonobject.getString("price");
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
            new CancelPromocode(cartid, cartvalue).execute();
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
                //result = Utility.httpGetRequestToServer(ApiConstants.CHECKOUT_NEW + "?cartId=CT152665");
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
                            total_cartvalue = jsonobject.getString("cartValue");

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
                        if (reviewOrderModels.size() > 0) {
                            reviewOrderAdapter = new ReviewOrderAdapter(getActivity(), reviewOrderModels, mParent);
                            listView_selected_orders.setAdapter(reviewOrderAdapter);
                            listView_selected_orders.addHeaderView(ll_header);
                            //listView_selected_orders.addFooterView(ll_fottor);
                            getAddress();
                        } else {
                            listView_selected_orders.setAdapter(new NoOrderFoundAdapter(mParent));
                            listView_selected_orders.addHeaderView(ll_header);
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
        txt_name.setText(Utility.capitalizeFirstLetter(addressesModels.get(0).getUsername()));
        txt_address_line.setText(Utility.capitalizeFirstLetter(addressesModels.get(0).getBline()));
        txt_city.setText(Utility.capitalizeFirstLetter(addressesModels.get(0).getBcity()));
        txt_address_state.setText(Utility.capitalizeFirstLetter(addressesModels.get(0).getBstate()));
        txt_pin_code.setText(addressesModels.get(0).getBpincode());
        txt_mobile.setText(addressesModels.get(0).getBmobile());
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
