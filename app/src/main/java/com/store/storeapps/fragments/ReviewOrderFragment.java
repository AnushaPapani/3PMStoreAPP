package com.store.storeapps.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.adapters.ReviewOrderAdapter;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.models.ReviewOrderModel;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Shankar.
 */
public class ReviewOrderFragment extends Fragment {
    public static final String TAG = "ReviewOrderFragment";
    private View rootView;
    private ListView listView_selected_orders;
    private RelativeLayout ll_header;
    private TextView txt_review_your_order;
    public static ArrayList<ReviewOrderModel> reviewOrderModels;
    private ReviewOrderAdapter reviewOrderAdapter;
    private Button proceedtopay;
    private EditText Promocode;
    private TextView promotext;
    private TextView applypromocode;
    View toastRoot;
    View toastRoot2;
    Toast toast;
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
        proceedtopay =(Button)rootView.findViewById(R.id.proceedtopay);
        listView_selected_orders = (ListView) rootView.findViewById(R.id.listView_selected_orders);
        Promocode =(EditText)rootView.findViewById(R.id.editText1);
        promotext =(TextView)rootView.findViewById(R.id.promotext);
        applypromocode =(TextView)rootView.findViewById(R.id.applypromo);
        ll_header = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.
                review_order_header, null);
        if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID))) {
            proceedtopay.setText("Checkout");
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
                if (applypromocode.getText().equals("Apply")&& Promocode.getText().toString().length()>1) {
                    applypromocode.setText("Cancel");
                    promotext.setVisibility(View.VISIBLE);
                    Promocode.setEnabled(false);
//                    new Promocode().execute();
                }else if(applypromocode.getText().equals("Cancel")){

//                    new Cancelpromocode().execute();
                    applypromocode.setText("Apply");
                    Promocode.setText("");
                    promotext.setVisibility(View.GONE);
                    Promocode.setEnabled(true);
                    Promocode.setText("");
                }
                else {
                    TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
                    t.setText("Please enter Promo code");
                    toast.setView(toastRoot);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
                    toast.setDuration(20000);
                    toast.show();

                }

            }
        });
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
                            ReviewOrderModel reviewOrderModel = new ReviewOrderModel();
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

    public class Promocode extends AsyncTask<String, String, String> {
        JSONObject jsonpromo;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(ReviewOrder.this);
//            pDialog.setMessage("promocode applying ..");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();

        }

        /**
         * Creating product
         * */

        protected String doInBackground(String... args) {
            //	String P_Name=pname.getText().toString();
            String promcode=Promocode.getText().toString();
//            String amountpromo=cost2.getText().toString();
//            cost1= (TextView)findViewById(R.id.textView5);
            //String discountpromo=discount.getText().toString();
            //	String quantitypromo=pquant.getText().toString();
            //	System.out.println("quantity output"+quantitypromo);
//            if (spinnerOsversions.getVisibility()!=View.GONE) {
//                item = (String) spinnerOsversions.getSelectedItem();
//            }else {
//                item =quatity.getText().toString();
//            }

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("OR_ID", O_ID));
//            params.add(new BasicNameValuePair("P_Code", promcode));
//            params.add(new BasicNameValuePair("amount", amountpromo));
//            params.add(new BasicNameValuePair("quantity", item));
//			params.add(new BasicNameValuePair("PromoType", PromoType));


            // getting JSON Object
            // Note that create product url accepts POST method
//            jsonpromo = jsonparser.makeHttpRequest(promo_Feed,
//                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", jsonpromo.toString());
            Log.d("Promocode Applied!", jsonpromo.toString());


//            try {
//                disprice =jsonpromo.getString("price");
//                Log.d("Discount Price", disprice);
////                spinnerOsversions.setEnabled(false);
//
//                Log.d("Output", out);
//            } catch (JSONException e1){
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }catch (RuntimeException e) {
//                // TODO: handle exception
//            }

            return null;
        }


        protected void onPostExecute(String file_url) {
            try {
                int success = jsonpromo.getInt("success");
                String PromoType = jsonpromo.getString("PromoType");
                System.out.println("PromoType "+PromoType);


                if (success == 1) {
                    // successfully created user
                    TextView t =(TextView)toastRoot2.findViewById(R.id.validtoast);
                    t.setText("Coupon Code Applied Successfully");
                    toast.setView(toastRoot2);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
                    toast.setDuration(20000);
                    toast.show();

                    applypromocode.setText("Cancel");
                    promotext.setVisibility(View.VISIBLE);
                    Promocode.setEnabled(false);
//                    cost2.setText(disprice);

                }
                else if (success == 2) {
                    TextView t =(TextView)toastRoot2.findViewById(R.id.validtoast);
                    t.setText("Coupon Code already applied.Please cancel to apply new Coupon Code");
                    toast.setView(toastRoot2);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
                    toast.setDuration(20000);
                    toast.show();

                    applypromocode.setText("Apply");
                    Promocode.setEnabled(true);

                    //					return jsonpromo.getString(TAG_MESSAGE);
                }
                else {
                    TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
                    t.setText("Invalid Promocode");
                    toast.setView(toastRoot);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
                    toast.setDuration(20000);
                    toast.show();

                    applypromocode.setText("Apply");
                    promotext.setVisibility(View.GONE);
                    Promocode.setEnabled(true);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch ( NullPointerException e) {
                // TODO: handle exception
            }



        }
    }
}
