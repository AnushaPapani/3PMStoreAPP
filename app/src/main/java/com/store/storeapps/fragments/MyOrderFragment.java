package com.store.storeapps.fragments;

import android.app.Activity;
//import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.store.storeapps.R;

import com.store.storeapps.activities.ReturnFormNew;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by satyanarayana pdv on 10/4/2016.
 */

public class MyOrderFragment extends Fragment {

    public static final String TAG = "MyOrderFragment";

    private LinearLayout myOrderslinearLayout;
    private MyOrdersModel myOrdersModel;
    private LayoutInflater mInflater;
    public static String orderID , CartPID, Pimage, Pname, Pcost, Orderstatus, Orderdate ,USername ,Uid ,PaymentType;
    private View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ll, container, false);
        initUI();
        return rootView;
    }
    private void initUI() {
        myOrderslinearLayout = (LinearLayout) rootView.findViewById(R.id.ll);
        mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getMyOrdersData();
    }

    private void getMyOrdersData() {
        if (Utility.isNetworkAvailable(getActivity())) {
            new GetMyOrdersAsyncTask().execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }

    class GetMyOrdersAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetMyOrdersAsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
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
                                movie.setCart_Prod_ID(jsonObjectMovie.optString("Cart_Prod_ID"));
                                movie.setP_Name(jsonObjectMovie.optString("P_Name"));
                                movie.setStatus(jsonObjectMovie.optString("Status"));
                                movie.setOrder_Date(jsonObjectMovie.optString("Order_Date"));
                                movie.setP_Image(jsonObjectMovie.optString("P_Image"));
                                movie.setCustomerName(jsonObjectMovie.optString("CustomerName"));
                                movie.setU_ID(jsonObjectMovie.optString("U_ID"));
                                movie.setP_Cost(jsonObjectMovie.optString("P_Cost"));
                                movie.setPayment_Type(jsonObjectMovie.optString("Payment_Type"));
                                movie.setReturn_RefundType(jsonObjectMovie.optString("Return_RefundType"));
                                movie.setReturn_ExchangeType(jsonObjectMovie.optString("Return_ExchangeType"));

                                movie.setCallmeback_IsSubmit(jsonObjectMovie.optString("Callmeback_IsSubmit"));
                                movie.setCancelStatus(jsonObjectMovie.optString("CancelStatus"));
                                movie.setCancel_Issubmit(jsonObjectMovie.optString("Cancel_Issubmit"));
                                movie.setReview_Issubmit(jsonObjectMovie.optString("Review_Issubmit"));
                                movie.setRefund_IsSubmit(jsonObjectMovie.optString("Refund_IsSubmit"));
                                movie.setExchange_IsSubmit(jsonObjectMovie.optString("Exchange_IsSubmit"));
                                movie.setTrackenabledate(jsonObjectMovie.optString("trackenabledate"));
                                movie.setReturndisabledate(jsonObjectMovie.optString("returndisabledate"));



                                movie.setTotalCost(jsonObjectMovie.optString("SubTotal"));
                                movie.setPMCashUsed(jsonObjectMovie.optString("3PMCashUsed"));
                                movie.setDiscount(jsonObjectMovie.optString("Discount"));
                                movie.setCOD_Charges(jsonObjectMovie.optString("COD_Charges"));
                                movie.setGrandTotal(jsonObjectMovie.optString("Cart_Value"));

//                                movie.setAttribute_Type(jsonObjectMovie.optString("Attribute_Type"));
//                                movie.setAttribute_Value(jsonObjectMovie.optString("Attribute_Value"));
                                JSONArray attrTypes = jsonObjectMovie.optJSONArray("Attribute_Type");
                                ArrayList<String> attrTypesArray = new ArrayList<>();
                                if (attrTypes != null) {
                                    for (int i1 = 0; i1 < attrTypes.length(); i1++) {
                                        attrTypesArray.add(attrTypes.optString(i1));
                                    }
                                }
                                movie.setAttribute_Type(attrTypesArray);

                                JSONArray attrNames = jsonObjectMovie.optJSONArray("Attribute_Value");
                                ArrayList<String> attrNamesArray = new ArrayList<>();
                                if (attrNames != null) {
                                    for (int j1 = 0; j1 < attrNames.length(); j1++) {
                                        attrNamesArray.add(attrNames.optString(j1));
                                    }
                                }
                                movie.setAttribute_Value(attrNamesArray);

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
                final ModelArray modelArray = myOrdersModel.getMovies().get(l);
                View layoutView = mInflater.inflate(R.layout.myorders_main_item, null);
                TextView textView = (TextView) layoutView.findViewById(R.id.order_id);
                LinearLayout ll_items = (LinearLayout) layoutView.findViewById(R.id.ll_items);
                TextView totalcost = (TextView) layoutView.findViewById(R.id.totalcost);
                TextView pmcashused = (TextView) layoutView.findViewById(R.id.pmcashused);
                TextView discount = (TextView) layoutView.findViewById(R.id.discount);
                TextView codcharges = (TextView) layoutView.findViewById(R.id.codcharges);
                TextView grandtotal = (TextView) layoutView.findViewById(R.id.grandtotal);

                textView.setText("" + modelArray.getOrderId());

                for (int m = 0; m < modelArray.getMovies().size(); m++) {
                    final Movie mMovie = myOrdersModel.getMovies().get(l).getMovies().get(m);
                    View inneritem = mInflater.inflate(R.layout.layout_item_inner, null);
//                    TextView txt_item_name = (TextView) inneritem.findViewById(R.id.txt_item_name);
                    final TextView txt_product_name = (TextView) inneritem.findViewById(R.id.proname1);
                    TextView txt_status = (TextView) inneritem.findViewById(R.id.pstatus1);
                    TextView txt_date = (TextView) inneritem.findViewById(R.id.pdate1);
                    ImageView img_P_Image = (ImageView) inneritem.findViewById(R.id.pimage1);

                    final TextView txt_approved1 = (TextView) inneritem.findViewById(R.id.approved1);
                    final TextView txt_packed1 = (TextView) inneritem.findViewById(R.id.Packed1);
                    final TextView txt_cancelled1 = (TextView) inneritem.findViewById(R.id.Canceled1);
                    final TextView txt_exstatus1 = (TextView) inneritem.findViewById(R.id.exstatus1);
                    final TextView txt_delivered1 = (TextView) inneritem.findViewById(R.id.delivered1);
                    final ImageView img_statusnode1 = (ImageView) inneritem.findViewById(R.id.statusnode1);
                    final ImageView img_statusnode2 = (ImageView) inneritem.findViewById(R.id.statusnode2);
                    final ImageView img_statusnode3 = (ImageView) inneritem.findViewById(R.id.statusnode3);
                    final ImageView img_statusnode4 = (ImageView) inneritem.findViewById(R.id.statusnode4);
                    final ImageView img_statusnode5 = (ImageView) inneritem.findViewById(R.id.statusnode5);
                    final View statusBar11 = (View) inneritem.findViewById(R.id.statusbar11);
                    final View statusBar12 = (View) inneritem.findViewById(R.id.statusbar12);
                    final View statusBar13 = (View) inneritem.findViewById(R.id.statusbar13);
                    final View statusBar14 = (View) inneritem.findViewById(R.id.statusbar14);
                    Button btn_cancel1 = (Button) inneritem.findViewById(R.id.ordercancel1);
                    Button btn_track1 = (Button) inneritem.findViewById(R.id.ordertrack1);
                    Button btn_callme1 = (Button) inneritem.findViewById(R.id.ordercallme1);
                    Button btn_review1 = (Button) inneritem.findViewById(R.id.orderreview1);
                    Button btn_return1 = (Button) inneritem.findViewById(R.id.orderreturn1);

                    TextView order_details = (TextView) inneritem.findViewById(R.id.detials);
                    String OrderDetails = "Order Details";
                    SpannableString content = new SpannableString(OrderDetails);
                    content.setSpan(new UnderlineSpan(), 0, OrderDetails.length(), 0);
                    order_details.setText(content);

                    final TextView textViewSize= (TextView) inneritem.findViewById(R.id.textViewSize);
                    final TextView sizeQuote= (TextView) inneritem.findViewById(R.id.sizeQuote);
                    final TextView sizeValue= (TextView) inneritem.findViewById(R.id.sizeValue);

                    final TextView textViewColor= (TextView) inneritem.findViewById(R.id.textViewColor);
                    final TextView colorQuote= (TextView) inneritem.findViewById(R.id.colorQuote);
                    final TextView colorValue= (TextView) inneritem.findViewById(R.id.colorValue);

                    final TextView textViewCustom= (TextView) inneritem.findViewById(R.id.textViewCustom);
                    final TextView typeQuote= (TextView) inneritem.findViewById(R.id.typeQuote);
                    final TextView typeValue= (TextView) inneritem.findViewById(R.id.typeValue);

                    final TextView textViewGender= (TextView) inneritem.findViewById(R.id.textViewGender);
                    final TextView genderQuote= (TextView) inneritem.findViewById(R.id.genderQuote);
                    final TextView genderValue= (TextView) inneritem.findViewById(R.id.genderValue);

                    ArrayList<String> attributeType = mMovie.getAttribute_Type();
                    ArrayList<String> attributeValue = mMovie.getAttribute_Value();

                    textViewSize.setVisibility(View.GONE);
                    sizeQuote.setVisibility(View.GONE);
                    sizeValue.setVisibility(View.GONE);

                    textViewColor.setVisibility(View.GONE);
                    colorQuote.setVisibility(View.GONE);
                    colorValue.setVisibility(View.GONE);

                    textViewCustom.setVisibility(View.GONE);
                    typeQuote.setVisibility(View.GONE);
                    typeValue.setVisibility(View.GONE);

                    textViewGender.setVisibility(View.GONE);
                    genderQuote.setVisibility(View.GONE);
                    genderValue.setVisibility(View.GONE);

                    for(int attrCount = 0; attrCount< attributeType.size(); attrCount++)
                    {
                        if(attributeType.get(attrCount) == "Size")
                        {
                            textViewSize.setVisibility(View.VISIBLE);
                            sizeQuote.setVisibility(View.VISIBLE);
                            sizeValue.setVisibility(View.VISIBLE);
                            sizeValue.setText(attributeValue.get(attrCount));
                        }
                        if(attributeType.get(attrCount) == "Color")
                        {
                            textViewColor.setVisibility(View.VISIBLE);
                        colorQuote.setVisibility(View.VISIBLE);
                        colorValue.setVisibility(View.VISIBLE);

                        colorValue.setText(attributeValue.get(attrCount));
                        }
                        if(attributeType.get(attrCount) == "Gender")
                        {
                            textViewGender.setVisibility(View.VISIBLE);
                        genderQuote.setVisibility(View.VISIBLE);
                        genderValue.setVisibility(View.VISIBLE);

                        genderValue.setText(attributeValue.get(attrCount));
                        }
                        if(attributeType.get(attrCount) == "Custom")
                        {
                            textViewCustom.setVisibility(View.VISIBLE);
                        typeQuote.setVisibility(View.VISIBLE);
                        typeValue.setVisibility(View.VISIBLE);

                        typeValue.setText(attributeValue.get(attrCount));
                        }
                    }

                    String orderstatus = mMovie.getStatus().toString();
                    String Return_ExchangeTypes = mMovie.getReturn_ExchangeType().toString();
                    String Return_RefundTypes = mMovie.getReturn_RefundType().toString();

                    String Callme_Issubmit = mMovie.getCallmeback_IsSubmit().toString();
                    String CancelStatus = mMovie.getCancelStatus().toString();
                    String Cancel_Issubmit = mMovie.getCancel_Issubmit().toString();
                    String Review_Issubmit = mMovie.getReview_Issubmit().toString();
                    String Refund_Issubmit = mMovie.getRefund_IsSubmit().toString();
                    String Exchange_Issubmit = mMovie.getExchange_IsSubmit().toString();
                    String trackenabledate = mMovie.getTrackenabledate().toString();
                    String returndisabledate = mMovie.getReturndisabledate().toString();

                    String TotalCost= mMovie.getTotalCost().toString();
                    String PMCashUsed = mMovie.getPMCashUsed().toString();
                    String Discount = mMovie.getDiscount().toString();
                    String COD_Charges = mMovie.getCOD_Charges().toString();
                    String GrandTotal = mMovie.getGrandTotal().toString();

                    totalcost.setText(TotalCost);
                    pmcashused.setText(PMCashUsed);
                    discount.setText(Discount);
                    codcharges.setText(COD_Charges);
                    grandtotal.setText(GrandTotal);

                    if(orderstatus.equals("Order Placed") || orderstatus.equals("Preparation in progress") ||
                            orderstatus.equals("Packed & Ready")||orderstatus.equals("Dispatched") ||
                            orderstatus.equals("Canceled")|| orderstatus.equals("Pre-dispatch Cancellation")
                            ||orderstatus.equals("Post-dispatch Cancellation"))
                    {
                        btn_cancel1.setVisibility(View.VISIBLE);
                        btn_track1.setVisibility(View.VISIBLE);
                        btn_callme1.setVisibility(View.GONE);
                        btn_return1.setVisibility(View.GONE);
                        btn_review1.setVisibility(View.GONE);
                    }
                    else if(orderstatus.equals("Order Held"))
                    {
                        btn_callme1.setVisibility(View.VISIBLE);
                        btn_cancel1.setVisibility(View.GONE);
                        btn_track1.setVisibility(View.GONE);
                        btn_return1.setVisibility(View.GONE);
                        btn_review1.setVisibility(View.GONE);
                    }
                    else if(orderstatus.equals("Delivered") || orderstatus.equals("Return Request Received") ||
                            orderstatus.equals("Return process initiated") || orderstatus.equals("Reverse pick-up done") ||
                            orderstatus.equals("Refund done Successfully") || orderstatus.equals("Next product dispatched") ||
                            orderstatus.equals("Exchange done Successfully"))
                    {
                        btn_return1.setVisibility(View.VISIBLE);
                        btn_review1.setVisibility(View.VISIBLE);
                        btn_cancel1.setVisibility(View.GONE);
                        btn_track1.setVisibility(View.GONE);
                        btn_callme1.setVisibility(View.GONE);
                    }

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    String date1= dateFormat.format(date);

                    try {
                        Date returnDisableDate = dateFormat.parse(returndisabledate);
                        if(Refund_Issubmit.equals("1") ||Refund_Issubmit.equals("0")|| Exchange_Issubmit.equals("1")
                                ||Exchange_Issubmit.equals("0")|| date.compareTo(returnDisableDate)>0)
                        {
                            btn_return1.setEnabled(false);
                            btn_return1.setBackgroundResource(R.drawable.order_buttongrey);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Date trackDate = dateFormat.parse(trackenabledate);
                        Date currentDate = dateFormat.parse(date1);

                        System.out.println("trackenable date String --"+trackDate);
                        if(orderstatus.equals("Dispatched") && currentDate.compareTo(trackDate)>0 )
                        {
                            btn_track1.setEnabled(true);
                        }
                        else if(orderstatus.equals("Dispatched") && currentDate.compareTo(trackDate)<0 )
                        {
                            btn_track1.setEnabled(false);
                            btn_track1.setBackgroundResource(R.drawable.order_buttongrey);
                        }
                        else
                        {
                            btn_track1.setEnabled(false);
                            btn_track1.setBackgroundResource(R.drawable.order_buttongrey);
                        }
                    }
                    catch (ParseException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    if(Cancel_Issubmit.equals("1") || Cancel_Issubmit.equals("0"))
                    {
                        btn_cancel1.setEnabled(false);
                        btn_cancel1.setBackgroundResource(R.drawable.order_buttongrey);
                    }
                    else  if(CancelStatus.equals("Canceled") || CancelStatus.equals("Pre-dispatch Cancellation") ||
                            CancelStatus.equals("Post-dispatch Cancellation") || Cancel_Issubmit.equals("0"))
                    {
                        btn_cancel1.setVisibility(View.GONE);
                    }
                    else
                    {
                        btn_cancel1.setEnabled(true);
                    }

                    if(!(orderstatus.equals("Order Held")) || Callme_Issubmit.equals("1")|| Cancel_Issubmit.equals("0"))
                    {
                        btn_callme1.setEnabled(false);
                        btn_callme1.setBackgroundResource(R.drawable.order_buttongrey);
                    } else
                    {
                        btn_callme1.setEnabled(true);
                    }

                    if(Review_Issubmit.equals("1")||Review_Issubmit.equals("0"))
                    {
                        btn_review1.setEnabled(false);
                        btn_review1.setBackgroundResource(R.drawable.order_buttongrey);
                    }

                    if (orderstatus.contentEquals("Order Held")) {
                        txt_packed1.setText("Order Held");
                        txt_cancelled1.setVisibility(View.INVISIBLE);
                        txt_delivered1.setVisibility(View.INVISIBLE);
                        img_statusnode2.setVisibility(View.VISIBLE);
                        img_statusnode3.setVisibility(View.INVISIBLE);
                        img_statusnode4.setVisibility(View.INVISIBLE);
                        statusBar11.setVisibility(View.VISIBLE);
                        statusBar12.setVisibility(View.INVISIBLE);
                        statusBar13.setVisibility(View.INVISIBLE);
                        statusBar14.setVisibility(View.INVISIBLE);
                    }else if (orderstatus.contentEquals("Preparation in progress")) {
                        img_statusnode2.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode3.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar12.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        statusBar13.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    }else if (orderstatus.contentEquals("Packed & Ready")) {
                        img_statusnode3.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar11.setVisibility(View.VISIBLE);
                        statusBar12.setVisibility(View.VISIBLE);
                        statusBar13.setVisibility(View.VISIBLE);
                    }else if (orderstatus.contentEquals("Dispatched")) {
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar11.setVisibility(View.VISIBLE);
                        statusBar12.setVisibility(View.VISIBLE);
                        statusBar13.setVisibility(View.VISIBLE);
                    }
                    else if (orderstatus.contains("Delivered")) {
                        img_statusnode2.setVisibility(View.VISIBLE);
                        img_statusnode3.setVisibility(View.VISIBLE);
                        img_statusnode4.setVisibility(View.VISIBLE);
                        statusBar11.setVisibility(View.VISIBLE);
                        statusBar12.setVisibility(View.VISIBLE);
                        statusBar13.setVisibility(View.VISIBLE);

                    }else if (orderstatus.contains("Pre-dispatch Cancellation")) {
                        txt_cancelled1.setText("Canceled");
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar13.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    }else if (orderstatus.contains("Canceled")) {
                        txt_cancelled1.setText("Canceled");
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar13.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    }else if (orderstatus.contains("Post-dispatch Cancellation")) {
                        txt_delivered1.setText("Canceled");
                    }else if (orderstatus.contains("Return Request Received")&&(Return_ExchangeTypes.contentEquals("A New Product"))) {

                        txt_approved1.setText("Return \nRequest");
                        txt_packed1.setText("Return \nprocess");
                        txt_cancelled1.setText("Reverse \npick-up");
                        txt_delivered1.setText("Next product \ndispatched");
                        img_statusnode5.setVisibility(View.VISIBLE);
                        statusBar14.setVisibility(View.VISIBLE);
                        txt_exstatus1.setVisibility(View.VISIBLE);
                        txt_exstatus1.setText("Exchange done \nSuccessfully");
                        img_statusnode2.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode3.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode5.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar12.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        statusBar13.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        statusBar14.setBackgroundColor(Color.parseColor("#D3D3D3"));

                    }else if (orderstatus.contains("Return Request Received")&&(Return_RefundTypes.contentEquals("Refund"))) {

                        txt_approved1.setText("Return \nRequest");
                        txt_packed1.setText("Return \nprocess");
                        txt_cancelled1.setText("Reverse \npick-up");
                        txt_delivered1.setText("Refund done \nSuccessfully");
                        img_statusnode5.setVisibility(View.INVISIBLE);
                        statusBar14.setVisibility(View.INVISIBLE);
                        txt_exstatus1.setVisibility(View.INVISIBLE);
                        img_statusnode2.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode3.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar12.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        statusBar13.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    }

                    else if (orderstatus.contains("Return process initiated")&&(Return_RefundTypes.contentEquals("Refund"))) {

                        txt_approved1.setText("Return \nRequest");
                        txt_packed1.setText("Return \nprocess");
                        txt_cancelled1.setText("Reverse \npick-up");
                        txt_delivered1.setText("Refund done \nSuccessfully");
                        img_statusnode5.setVisibility(View.GONE);
                        statusBar14.setVisibility(View.GONE);
                        txt_exstatus1.setVisibility(View.GONE);
                        img_statusnode3.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar13.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    }
                    else if (orderstatus.contains("Return process initiated")&&(Return_ExchangeTypes.contentEquals("A New Product"))) {

                        txt_approved1.setText("Return \nRequest");
                        txt_packed1.setText("Return \nprocess");
                        txt_cancelled1.setText("Reverse \npick-up");
                        txt_delivered1.setText("Refund done \nSuccessfully");
                        txt_delivered1.setText("Next product \ndispatched");
                        img_statusnode5.setVisibility(View.VISIBLE);
                        statusBar14.setVisibility(View.VISIBLE);
                        txt_exstatus1.setVisibility(View.VISIBLE);
                        txt_exstatus1.setText("Refund done \nSuccessfully");
                        img_statusnode3.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode5.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar13.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        statusBar14.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    }
                    else if (orderstatus.contains("Exchange done Successfully")&&(Return_ExchangeTypes.contentEquals("A New Product"))) {
                        txt_approved1.setText("Return \nRequest");
                        txt_packed1.setText("Return \nprocess");
                        txt_cancelled1.setText("Reverse \npick-up");
                        txt_delivered1.setText("Next product \ndispatched");
                        img_statusnode5.setVisibility(View.VISIBLE);
                        statusBar14.setVisibility(View.VISIBLE);
                        txt_exstatus1.setVisibility(View.VISIBLE);
                        txt_exstatus1.setText("Exchange done \nSuccessfully");
                    }
                    else if (orderstatus.contains("Reverse pick-up done")&&((Return_RefundTypes.contentEquals("Refund")))) {
                        txt_approved1.setText("Return \nRequest");
                        txt_packed1.setText("Return \nprocess");
                        txt_cancelled1.setText("Reverse \npick-up");
                        txt_delivered1.setText("Refund done \nSuccessfully");
                        img_statusnode5.setVisibility(View.INVISIBLE);
                        statusBar14.setVisibility(View.INVISIBLE);
                        txt_exstatus1.setVisibility(View.INVISIBLE);
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode5.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar14.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    }
                    else if (orderstatus.contains("Reverse pick-up done")&&(Return_ExchangeTypes.contentEquals("A New Product"))) {
                        txt_approved1.setText("Return \nRequest");
                        txt_packed1.setText("Return \nprocess");
                        txt_cancelled1.setText("Reverse \npick-up");
                        txt_delivered1.setText("Next product \ndispatched");
                        txt_exstatus1.setText("Exchange done \nSuccessfully");
                        img_statusnode5.setVisibility(View.VISIBLE);
                        statusBar14.setVisibility(View.VISIBLE);
                        txt_exstatus1.setVisibility(View.VISIBLE);
                        img_statusnode4.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        img_statusnode5.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar14.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    }
                    else if (orderstatus.contains("Next product dispatched")&&(Return_ExchangeTypes.contentEquals("A New Product"))) {
                        txt_approved1.setText("Return \nRequest");
                        txt_packed1.setText("Return \nprocess");
                        txt_cancelled1.setText("Reverse \npick-up");
                        txt_delivered1.setText("Next product \ndispatched");
                        img_statusnode5.setVisibility(View.VISIBLE);
                        img_statusnode5.setImageDrawable(getResources().getDrawable(R.drawable.circle_change));
                        statusBar14.setBackgroundColor(Color.parseColor("#D3D3D3"));
                        statusBar14.setVisibility(View.VISIBLE);
                        txt_exstatus1.setVisibility(View.VISIBLE);
                        txt_exstatus1.setText("Exchange done \nSuccessfully");
                    }
                    else if ((orderstatus.contains("Refund done Successfully")&&(Return_RefundTypes.contentEquals("Refund")))) {
                        txt_approved1.setText("Return \nRequest");
                        txt_packed1.setText("Return \nprocess");
                        txt_cancelled1.setText("Reverse \npick-up");
                        txt_delivered1.setText("Refund done \nSuccessfully");
                        txt_exstatus1.setVisibility(View.INVISIBLE);
                        txt_exstatus1.setText("Exchange done \nSuccessfully");
                    }

                    order_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderID= modelArray.getOrderId().toString();
                            CartPID= mMovie.getCart_Prod_ID().toString();

                            Utility.navigateDashBoardFragment(new OrderDetailsFragment(), OrderDetailsFragment.TAG, null,getActivity());
                        }
                    });
                    btn_cancel1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderID= modelArray.getOrderId().toString();
                            CartPID= mMovie.getCart_Prod_ID().toString();
                            Pimage = mMovie.getP_Image().toString();
                            Pname = mMovie.getP_Name().toString();
                            Pcost = mMovie.getP_Cost().toString();
                            Orderstatus = mMovie.getStatus().toString();
                            Orderdate = mMovie.getOrder_Date().toString();
                            USername = mMovie.getCustomerName().toString();
                            Uid = mMovie.getU_ID().toString();

                            Utility.navigateDashBoardFragment(new CancelFormFragment(), CancelFormFragment.TAG, null,getActivity());
                        }
                    });
                    btn_review1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderID= modelArray.getOrderId().toString();
                            CartPID= mMovie.getCart_Prod_ID().toString();
                            Pcost = mMovie.getP_Cost().toString();
                            USername = mMovie.getCustomerName().toString();
                            Uid = mMovie.getU_ID().toString();

//                            Intent i = new Intent(getActivity(), ReturnFormNew.class);
//                            startActivity(i);
                            Utility.navigateDashBoardFragment(new ReviewFormFragment(), ReviewFormFragment.TAG, null,getActivity());
                        }
                    });
                    btn_return1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderID= modelArray.getOrderId().toString();
                            CartPID= mMovie.getCart_Prod_ID().toString();
                            USername = mMovie.getCustomerName().toString();
                            Uid = mMovie.getU_ID().toString();
                            PaymentType = mMovie.getPayment_Type().toString();

                            Intent i = new Intent(getActivity(), ReturnFormNew.class);
                            startActivity(i);
//                            Utility.navigateDashBoardFragment(new ReturnFormFragment(), ReturnFormFragment.TAG, null,getActivity());
                        }
                    });


                    btn_callme1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderID= modelArray.getOrderId().toString();
                            CartPID= mMovie.getCart_Prod_ID().toString();

                            Utility.navigateDashBoardFragment(new CallMeFormFragment(), CallMeFormFragment.TAG, null,getActivity());
                        }
                    });


                    txt_product_name.setText("" + mMovie.getP_Name());
                    txt_status.setText("" + mMovie.getStatus());
                    txt_date.setText("" + mMovie.getOrder_Date());
                    String imgStr = mMovie.getP_Image().toString().replace(" ", "%20");
                    System.out.println("imgString " + imgStr);
                    Picasso.with(getActivity()).load(imgStr).into(img_P_Image);


                    ll_items.addView(inneritem);
                }

                myOrderslinearLayout.addView(layoutView);
            }
        }
    }
}