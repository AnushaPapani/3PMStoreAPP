package com.three.pmstore.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;
import com.three.pmstore.adapters.NoOrderFoundAdapter;
import com.three.pmstore.adapters.ReviewOrderAdapter;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.customviews.DialogClass;
import com.three.pmstore.models.AddressesModel;
import com.three.pmstore.models.ModelArray;
import com.three.pmstore.models.Movie;
import com.three.pmstore.models.MyOrdersModel;
import com.three.pmstore.models.ReviewOrderModel;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.three.pmstore.fragments.MyAddressFragment.addressesModels;

/**
 * Created by satyanarayana pdv on 11/4/2016.
 */

public class PaymentCartFragment extends Fragment{
    public static final String TAG = "PaymentCartFragment";
    private View rootView;
    private LayoutInflater mInflater;
    private HomeActivity mParent;
    public static TextView txt_email;
    public static TextView txt_name;
    public static TextView txt_address_line;
    public static TextView txt_city;
    public static TextView txt_address_state;
    public static TextView txt_pin_code;
    public static TextView txt_mobile;
    public static TextView txt_choose_another;
    public static String addressId;
    public static TextView totalprice,pmstorecash, pmcashcurrentbal, codchargesValue, codchargesHead, codchargesQuote, amounttoPAY;
    CheckBox pmcheckbutton;
    String   codcharge, amountPayable, Promocode, fname, bline, bcity, bstate, bpincode, bmobile, email, cartId, pmcash, coddisable;
    private boolean fromCOD;
    String orderid, CartProductId, U_id;
    Button confirmorder;
    ReviewOrderModel reviewOrderModel;
    private RelativeLayout layout_summary, divider3,divider4,divider5,layout_ordersummary, layout_cart_payment, layout_cart_paymentMethods,
            cart_ordersummarychild, layout_address;
    private LinearLayout cart_linearlayout;
    private MyOrdersModel cartModel;
    public static ArrayList<ReviewOrderModel> reviewOrderModels;
    public static int count;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();

        if (getArguments() != null) {
            orderid = getArguments().getString("OrderID");
            pmcash = getArguments().getString("pm_Cash");

            amountPayable = getArguments().getString("TotalCost");
            codcharge = getArguments().getString("ADMIN_COD");
            coddisable = getArguments().getString("coddisable");
            fname = getArguments().getString("name");
            email = getArguments().getString("email");
            U_id = getArguments().getString("U_ID");
            bpincode = getArguments().getString("pincode");
            bmobile = getArguments().getString("bmobile");

            System.out.println("disable promo " + ReviewOrderFragment.coddisablepromo);

            email = Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID);
            cartId = HomeActivity.mCartId.toString();

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.cart_page, container, false);

        txt_name = (TextView) rootView.findViewById(R.id.txt_name);
        txt_address_line = (TextView) rootView.findViewById(R.id.txt_address_line);
        txt_city = (TextView) rootView.findViewById(R.id.txt_city);
        txt_address_state = (TextView) rootView.findViewById(R.id.txt_address_state);
        txt_pin_code = (TextView) rootView.findViewById(R.id.txt_pin_code);
        txt_mobile = (TextView) rootView.findViewById(R.id.txt_mobile);
        txt_choose_another = (TextView) rootView.findViewById(R.id.txt_choose_another);
        txt_email = (TextView) rootView.findViewById(R.id.txt_email);

        txt_email.setText(email);
        amounttoPAY = (TextView) rootView.findViewById(R.id.amountpaycash);
        pmcashcurrentbal = (TextView) rootView.findViewById(R.id.currentcashbal);
        totalprice = (TextView) rootView.findViewById(R.id.totalprice);
        pmstorecash = (TextView) rootView.findViewById(R.id.pmstorecashamount);
        pmcheckbutton = (CheckBox) rootView.findViewById(R.id.pmcheck);
        codchargesValue = (TextView) rootView.findViewById(R.id.codchargesValue);
        codchargesHead = (TextView) rootView.findViewById(R.id.codchargesHead);
        codchargesQuote = (TextView) rootView.findViewById(R.id.codchargesQuote);
        confirmorder = (Button) rootView.findViewById(R.id.confirmorder);

        totalprice.setText(""+amountPayable);
        pmcashcurrentbal.setText(""+pmcash+")");

        layout_summary = (RelativeLayout) rootView.findViewById(R.id.layout_summary);
        divider3 = (RelativeLayout) rootView.findViewById(R.id.divider3);
        divider4 = (RelativeLayout) rootView.findViewById(R.id.divider4);
        layout_ordersummary = (RelativeLayout) rootView.findViewById(R.id.layout_ordersummary);
        cart_linearlayout = (LinearLayout) rootView.findViewById(R.id.cart_linearlayout);
        layout_cart_payment = (RelativeLayout) rootView.findViewById(R.id.layout_cart_payment);
        layout_cart_paymentMethods = (RelativeLayout) rootView.findViewById(R.id.layout_cart_paymentMethods);
        cart_ordersummarychild = (RelativeLayout) rootView.findViewById(R.id.cart_ordersummarychild);
        layout_address = (RelativeLayout) rootView.findViewById(R.id.layout_address);

        initUI();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootView;
    }
    private void initUI() {

        mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getAddress();
        getCartData();

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
                        setAddressData();
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
    }

    private void getCartData() {
        if (Utility.isNetworkAvailable(getActivity())) {
            new GetCartAsyncTask().execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }
    class GetCartAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetCartAsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
            cartModel = new MyOrdersModel();
            reviewOrderModels = new ArrayList<>();
        }

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
                        count = reviewOrdersArray.length();
                        for (int i = 0; i < reviewOrdersArray.length(); i++) {
                            JSONObject jsonObject = reviewOrdersArray.getJSONObject(i);
                            reviewOrderModel = new ReviewOrderModel();
//                            reviewOrderModel.setCart_ItemCount(reviewOrdersArray.length());
                            reviewOrderModel.setP_ID(jsonObject.getString("P_ID"));
                            reviewOrderModel.setMax_Quantity(jsonObject.optInt("Max_Quantity"));
                            reviewOrderModel.setP_Cost(jsonObject.optInt("P_Cost"));
                            reviewOrderModel.setP_Qty(jsonObject.optInt("P_Qty"));
                            reviewOrderModel.setP_Name(jsonObject.getString("P_Name"));
                            reviewOrderModel.setP_Image(jsonObject.getString("P_Image"));
                            reviewOrderModel.setCart_Prod_ID(jsonObject.getString("Cart_Prod_ID"));
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
                            setDataTotheLayout();
                        }
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setDataTotheLayout() {

        if(count >0)
            System.out.println("count "+PaymentCartFragment.count);
            {
                View inneritem = mInflater.inflate(R.layout.cart_inneritem, null);

                final ImageView img_order = (ImageView) inneritem.findViewById(R.id.img_order);
                final TextView txt_product_name = (TextView) inneritem.findViewById(R.id.txt_product_name);
                final Spinner spin_qty = (Spinner) inneritem.findViewById(R.id.spin_qty);
                final TextView txt_unitPrice = (TextView) inneritem.findViewById(R.id.txt_unitPrice);
                final TextView txt_price = (TextView) inneritem.findViewById(R.id.txt_price);
                final TextView txt_subtotal = (TextView) inneritem.findViewById(R.id.txt_subtotal);
                final TextView txt_price_two = (TextView) inneritem.findViewById(R.id.txt_price_two);
                final TextView txt_qty = (TextView) inneritem.findViewById(R.id.txt_qty);
                final TextView colorValue = (TextView) inneritem.findViewById(R.id.colorValue);
                final TextView customValue = (TextView) inneritem.findViewById(R.id.customValue);
                final TextView sizeValue = (TextView) inneritem.findViewById(R.id.sizeValue);
                final TextView genderValue = (TextView) inneritem.findViewById(R.id.genderValue);
                final TextView colorHead = (TextView) inneritem.findViewById(R.id.colorHead);
                final TextView customHead = (TextView) inneritem.findViewById(R.id.CustomHead);
                final TextView sizeHead = (TextView) inneritem.findViewById(R.id.sizeHead);
                final TextView genderHead = (TextView) inneritem.findViewById(R.id.genderHead);
                final TextView colorQuote = (TextView) inneritem.findViewById(R.id.colorQuote);
                final TextView genderQuote = (TextView) inneritem.findViewById(R.id.genderQuote);
                final TextView sizeQuote = (TextView) inneritem.findViewById(R.id.sizeQuote);

                Picasso.with(getActivity()).load(reviewOrderModel.getP_Image()).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
                        .into( img_order);
                 txt_product_name.setText(reviewOrderModel.getP_Name());
                 txt_price.setText("" + reviewOrderModel.getP_Cost());
                 txt_qty.setText(""+reviewOrderModel.getP_Qty());

                ArrayList<String> spinnerArray = new ArrayList<>();
                for (int i = 0; i < reviewOrderModel.getMax_Quantity(); i++) {
                    spinnerArray.add("" + (i + 1));
                }
                 txt_price_two.setText("" + (reviewOrderModel.getP_Qty() * reviewOrderModel.getP_Cost()));

                ArrayList<String> attributeType = reviewOrderModel.getAttribute_Type();
                ArrayList<String> attributeValue = reviewOrderModel.getAttribute_Value();
                System.out.println("attributeType   "+attributeType  +"     "+ attributeValue);

                 sizeHead.setVisibility(View.GONE);
                 sizeValue.setVisibility(View.GONE);

                 colorHead.setVisibility(View.GONE);
                 colorValue.setVisibility(View.GONE);

                 genderHead.setVisibility(View.GONE);
                 genderValue.setVisibility(View.GONE);

                 customHead.setVisibility(View.GONE);
                 customValue.setVisibility(View.GONE);

                 colorQuote.setVisibility(View.GONE);
                 genderQuote.setVisibility(View.GONE);
                sizeQuote.setVisibility(View.GONE);

                if (attributeType != null && attributeType.size() > 0) {
                    System.out.println(attributeType  +"     "+ attributeValue);
                    for (int attrCount = 0; attrCount < attributeType.size(); attrCount++) {
                        if (attributeType.get(attrCount).equalsIgnoreCase("Size")) {
                             sizeHead.setVisibility(View.VISIBLE);
                             sizeValue.setVisibility(View.VISIBLE);
                            sizeQuote.setVisibility(View.VISIBLE);
                            String s = attributeValue.get(attrCount);
                            String upToNCharacters = s.substring(0, Math.min(s.length(), 14));
                             sizeValue.setText(upToNCharacters+"....");
//                     sizeValue.setText(attributeValue.get(attrCount));
                        }
                        if (attributeType.get(attrCount).equalsIgnoreCase("Color")) {
                             colorHead.setVisibility(View.VISIBLE);
                             colorValue.setVisibility(View.VISIBLE);
                             colorQuote.setVisibility(View.VISIBLE);

                            System.out.println("color value"+attributeValue.get(attrCount));
                             colorValue.setText(attributeValue.get(attrCount));
                        }
                        if (attributeType.get(attrCount).equalsIgnoreCase("Gender")) {
                             genderHead.setVisibility(View.VISIBLE);
                             genderValue.setVisibility(View.VISIBLE);
                             genderQuote.setVisibility(View.VISIBLE);

                             genderValue.setText(attributeValue.get(attrCount));
                        }
                        if (attributeType.get(attrCount).equalsIgnoreCase("Custom")) {
                            System.out.println("Custom value"+attributeValue.get(attrCount));
                             customHead.setVisibility(View.VISIBLE);
                             customValue.setVisibility(View.VISIBLE);

                             customValue.setText(attributeValue.get(attrCount));
                        }
                    }
                }
                
                cart_linearlayout.addView(inneritem);
            }
    }

                    




}
