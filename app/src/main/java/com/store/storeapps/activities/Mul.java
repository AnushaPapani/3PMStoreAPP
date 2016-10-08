package com.store.storeapps.activities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.store.storeapps.R;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.models.ModelArray;
import com.store.storeapps.models.Movie;
import com.store.storeapps.models.MyOrdersModel;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by satyanarayana pdv on 10/4/2016.
 */

public class Mul extends Activity {

    private LinearLayout myOrderslinearLayout;
    private MyOrdersModel myOrdersModel;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ll);
        initUI();
    }

    private void initUI() {
        myOrderslinearLayout = (LinearLayout) findViewById(R.id.ll);
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getMyOrdersData();
    }

    private void getMyOrdersData() {
        if (Utility.isNetworkAvailable(this)) {
            new GetMyOrdersAsyncTask().execute();
        } else {
            DialogClass.createDAlertDialog(this, "The Internet connection appears to be offline.");
        }
    }

    class GetMyOrdersAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetMyOrdersAsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(Mul.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(Mul.this, R.string.please_wait));
            myOrdersModel = new MyOrdersModel();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                Utility.showLog("data", "datadata" + paramsList.toString());
                result = Utility.httpGetRequestToServer(ApiConstants.MY_ORDERS);
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
                        JSONArray jsonArray = jsonobject.getJSONArray("orderIds");
                        JSONObject jsonObject_tbl_order = jsonobject.getJSONObject("tbl_order");
                        ArrayList<String> orderIds = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String s = jsonArray.getString(i);
                            orderIds.add(s);
                        }
                        myOrdersModel.setOrderIds(orderIds);

                        ArrayList<ModelArray> mModelArray = new ArrayList<>();
                        for (int j = 0; j < orderIds.size(); j++) {
                            ModelArray modelArray = new ModelArray();
                            String key = orderIds.get(j);
                            JSONArray jsonArrayItem = jsonObject_tbl_order.getJSONArray(key);
                            ArrayList<Movie> mMovie = new ArrayList<>();
                            for (int k = 0; k < jsonArrayItem.length(); k++) {
                                JSONObject jsonObjectMovie = jsonArrayItem.getJSONObject(k);
                                Movie movie = new Movie();
                                movie.setOrder_Date(jsonObjectMovie.optString("Order_Date"));
                                mMovie.add(movie);
                            }
                            modelArray.setMovies(mMovie);
                            mModelArray.add(modelArray);
                            modelArray.setOrderId(orderIds.get(j));
                        }
                        myOrdersModel.setMovies(mModelArray);

                        setDataTotheLayout();
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setDataTotheLayout() {
        if (myOrdersModel.getMovies() != null) {
            for (int l = 0; l < myOrdersModel.getMovies().size(); l++) {
                ModelArray modelArray = myOrdersModel.getMovies().get(l);
                View layoutView = mInflater.inflate(R.layout.myorders_main_item, null);
                TextView textView = (TextView) layoutView.findViewById(R.id.order_id);
                LinearLayout ll_items = (LinearLayout) layoutView.findViewById(R.id.ll_items);
                textView.setText("" + modelArray.getOrderId());

                for (int m = 0; m < modelArray.getMovies().size(); m++) {
                    Movie mMovie = myOrdersModel.getMovies().get(l).getMovies().get(m);
                    View inneritem = mInflater.inflate(R.layout.layout_item_inner, null);
                    TextView txt_item_name = (TextView) inneritem.findViewById(R.id.txt_item_name);
                    txt_item_name.setText("" + mMovie.getP_Name());
                    ll_items.addView(inneritem);
                }

                myOrderslinearLayout.addView(layoutView);
            }
        }
    }
}