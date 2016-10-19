package com.three.pmstore.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.customviews.DialogClass;
import com.three.pmstore.fragments.ReviewOrderFragment_Before_Login;
import com.three.pmstore.models.ReviewOrderModel_Before_Login;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * Created by shankar on 10/1/2016.
 */

public class ReviewOrderAdapter_Before_Login extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ReviewOrderModel_Before_Login> mReviewOrderModels;
    private HomeActivity homeActivity;

    public ReviewOrderAdapter_Before_Login(Context context, ArrayList<ReviewOrderModel_Before_Login> mReviewOrderModels, HomeActivity homeActivity)
    {
        this.homeActivity = homeActivity;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mReviewOrderModels = mReviewOrderModels;
    }

    @Override
    public int getCount() {
        return mReviewOrderModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mReviewOrderModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ReviewOrderItemHolder mReviewOrderItemHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.review_order_item,
                    null);
            mReviewOrderItemHolder = new ReviewOrderItemHolder();
            mReviewOrderItemHolder.img_order = (ImageView) convertView.findViewById(R.id.img_order);
            mReviewOrderItemHolder.txt_product_name = (TextView) convertView.findViewById(R.id.txt_product_name);
            mReviewOrderItemHolder.spin_qty = (Spinner) convertView.findViewById(R.id.spin_qty);
            mReviewOrderItemHolder.txt_unitPrice = (TextView) convertView.findViewById(R.id.txt_unitPrice);
            mReviewOrderItemHolder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
            mReviewOrderItemHolder.txt_subtotal = (TextView) convertView.findViewById(R.id.txt_subtotal);
            mReviewOrderItemHolder.txt_price_two = (TextView) convertView.findViewById(R.id.txt_price_two);
            mReviewOrderItemHolder.img_remove = (ImageView) convertView.findViewById(R.id.img_remove);

            convertView.setTag(mReviewOrderItemHolder);
        } else {
            mReviewOrderItemHolder = (ReviewOrderItemHolder) convertView.getTag();
        }

        final ReviewOrderModel_Before_Login reviewOrderModel = (ReviewOrderModel_Before_Login) getItem(position);

        Picasso.with(mContext).load(getFullFilledImage(reviewOrderModel.getP_Image())).placeholder(Utility.getDrawable(mContext, R.drawable.refresh))
                .into(mReviewOrderItemHolder.img_order);
        mReviewOrderItemHolder.txt_product_name.setText(reviewOrderModel.getP_Name());
        mReviewOrderItemHolder.txt_price.setText("" + reviewOrderModel.getP_Cost());

        ArrayList<String> spinnerArray = new ArrayList<>();
        for (int i = 0; i < reviewOrderModel.getMax_Quantity(); i++) {
            spinnerArray.add("" + (i + 1));
        }
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(mContext,
                R.layout.spinner_item,
                spinnerArray);
        mReviewOrderItemHolder.spin_qty.setAdapter(spinnerArrayAdapter);
        mReviewOrderItemHolder.spin_qty.setSelection(reviewOrderModel.getP_Qty() - 1);
        mReviewOrderItemHolder.txt_price_two.setText("" + (reviewOrderModel.getP_Qty() * reviewOrderModel.getP_Cost()));

        mReviewOrderItemHolder.spin_qty.setTag(position);
        mReviewOrderItemHolder.spin_qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = (int) adapterView.getTag();
                if (reviewOrderModel.getP_Qty() != Integer.parseInt(adapterView.getItemAtPosition(i).toString())) {
                    updateAPI(mReviewOrderModels.get(position).getP_Name(), HomeActivity.mCartId, adapterView.getItemAtPosition(i).toString(),
                            String.valueOf(HomeActivity.mCartTotal), mReviewOrderModels.get(position).getP_ID(), mReviewOrderModels.get(position).getCart_Prod_ID(), position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mReviewOrderItemHolder.img_remove.setId(position);
        mReviewOrderItemHolder.img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = view.getId();
                //removeReviewOrderDetails("CT152622", "2000", mReviewOrderModels.get(position).getP_ID(), mReviewOrderModels.get(position).getCart_Prod_ID(), position);
                removeReviewOrderDetails(HomeActivity.mCartId, String.valueOf(HomeActivity.mCartTotal), mReviewOrderModels.get(position).getP_ID(), mReviewOrderModels.get(position).getCart_Prod_ID(), position);
            }
        });

        return convertView;
    }

    private void updateAPI(String name, String cartId, String update_quantity, String cartValue, String pid, String CartProdId, int position) {
        if (Utility.isNetworkAvailable(mContext)) {
            new UpdateCartAsyncTask(name, cartId, update_quantity, cartValue, pid, CartProdId, position).execute();
        } else {
            DialogClass.createDAlertDialog(mContext, "The Internet connection appears to be offline.");
        }
    }

    private void removeReviewOrderDetails(String cartId, String cartValue, String pid, String CartProdId, int position) {
        if (Utility.isNetworkAvailable(mContext)) {
            new DeleteCartAsyncTask(cartId, cartValue, pid, CartProdId, position).execute();
        } else {
            DialogClass.createDAlertDialog(mContext, "The Internet connection appears to be offline.");
        }
    }

    private String getFullFilledImage(String image) {
        return image.replaceAll(" ", "%20");
    }


    private class ReviewOrderItemHolder {
        private ImageView img_order;
        private TextView txt_product_name;
        private Spinner spin_qty;
        private TextView txt_unitPrice;
        private TextView txt_price;
        private TextView txt_subtotal;
        private TextView txt_price_two;
        private ImageView img_remove;
    }

    class DeleteCartAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String cartId;
        private String cartValue;
        private String pid;
        private String CartProdId;
        private int position;

        public DeleteCartAsyncTask(String cartId, String cartValue, String pid, String CartProdId, int position) {
            mCustomProgressDialog = new CustomProgressDialog(mContext);
            this.cartId = cartId;
            this.cartValue = cartValue;
            this.pid = pid;
            this.CartProdId = CartProdId;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(mContext, R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {

            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("cartId", cartId);
                paramsList.put("cartValue", cartValue);
                paramsList.put("pid ", pid);
                paramsList.put("CartProdId", CartProdId);
                result = Utility.httpPostRequestToServer(ApiConstants.DELETE_FROM_CART, Utility.getParams(paramsList));
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
                        Utility.showToastMessage(mContext, "Successfully Deleted");
                        mReviewOrderModels.remove(position);
                        ReviewOrderFragment_Before_Login.Grand_total.setText(jsonobject.getString("cartValue"));
                        HomeActivity.mCartTotal = jsonobject.getInt("cartValue");
                        if (!Utility.isValueNullOrEmpty(jsonobject.optString("cartCount"))) {
                            HomeActivity.cart_layout_button_set_text.setText(jsonobject.optString("cartCount"));
                        } else {
                            HomeActivity.cart_layout_button_set_text.setText("0");
                            ReviewOrderFragment_Before_Login.listView_selected_orders.setAdapter(new NoOrderFoundAdapter(homeActivity));
                            ReviewOrderFragment_Before_Login.Grand_total.setText("0");
                            ReviewOrderFragment_Before_Login.Checkout.setEnabled(false);
                            ReviewOrderFragment_Before_Login.Checkout.setText("Oops Cart Empty");
                        }
                        notifyDataSetChanged();
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class UpdateCartAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String update_name;
        private String update_cartId;
        private String update_quantity;
        private String update_cartValue;
        private String update_pid;
        private String update_CartProdId;
        private int position;

        public UpdateCartAsyncTask(String name, String cartId, String update_quantity, String cartValue, String pid, String CartProdId, int position) {
            mCustomProgressDialog = new CustomProgressDialog(mContext);
            this.update_name = name;
            this.update_cartId = cartId;
            this.update_quantity = update_quantity;
            this.update_cartValue = cartValue;
            this.update_pid = pid;
            this.update_CartProdId = CartProdId;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(mContext, R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {

            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("name", update_name);
                paramsList.put("cartId", update_cartId);
                paramsList.put("quantity", update_quantity);
                paramsList.put("cost", update_cartValue);
                paramsList.put("pid", update_pid);
                paramsList.put("cartValue", String.valueOf(HomeActivity.mCartTotal));
                paramsList.put("CartProdId", update_CartProdId);
                result = Utility.httpPostRequestToServer(ApiConstants.UPDATE_QTY, Utility.getParams(paramsList));
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
                        for (int i = 0; i < mReviewOrderModels.size(); i++) {
                            if (mReviewOrderModels.get(i).getCart_Prod_ID().equalsIgnoreCase(jsonobject.optString("CartProdId"))){
                                ReviewOrderModel_Before_Login reviewOrderModel = mReviewOrderModels.get(i);
                                reviewOrderModel.setP_Qty(jsonobject.optInt("quantity"));
                                HomeActivity.mCartTotal = jsonobject.optInt("cartValue");
                                ReviewOrderFragment_Before_Login.Grand_total.setText(String.valueOf(HomeActivity.mCartTotal));
                                mReviewOrderModels.set(i, reviewOrderModel);
                                ReviewOrderFragment_Before_Login.reviewOrderModels.set(i, reviewOrderModel);
                            }
                        }
                        notifyDataSetChanged();
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
