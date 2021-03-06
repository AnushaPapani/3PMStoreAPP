package com.three.pmstore.fragments;

/**
 * Created by satyanarayana pdv on 10/11/2016.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.three.pmstore.R;
import com.three.pmstore.activities.HomeActivity;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;


/**
 * Created by satyanarayana pdv on 10/11/2016.
 */

public class ReviewFormFragment extends Fragment implements RatingBar.OnRatingBarChangeListener {
    RatingBar productRatingBar,customerRatingBar;

    public static final String TAG = "ReviewFormFragment";
    private View rootView;
    TextView countText, titleAlert, productAlert, customerAlert,head1,head2;
    EditText reviewTitle,productReview,customerExperience,extraComments;
    Button submit;
    String orderid, cartProdId, title, productreview, customerexperience, extraComment, pcost,
            Uname , U_id, ProductRating, CustomerRating,cartId;
    String review ="Order Review form";
    int count;
    float curRate;
    int rateFirst,rateSecond;
    private HomeActivity mParent;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();
        if (getArguments() != null) {
            orderid = getArguments().getString("orderID");
            cartProdId = getArguments().getString("CartPID");
            cartId = getArguments().getString("cartID");

            pcost = getArguments().getString("Pcost");
            Uname = getArguments().getString("USername");
            U_id = getArguments().getString("Uid");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.reviewform, container, false);

//        orderid = MyOrderFragment.orderID;
//        cartProdId = MyOrderFragment.CartPID;
//        cartId = MyOrderFragment.cartID;
//        pcost = MyOrderFragment.Pcost;
//        Uname = MyOrderFragment.USername;
//        U_id = MyOrderFragment.Uid;

        reviewTitle= (EditText)rootView.findViewById(R.id.reviewTitle);
        head2=(TextView) rootView.findViewById(R.id.t2);
        head1 =(TextView) rootView.findViewById(R.id.t1);
        productReview= (EditText) rootView.findViewById(R.id.productReview);
        customerExperience= (EditText) rootView.findViewById(R.id.customerExperience);
        extraComments = (EditText) rootView.findViewById(R.id.extraComments);
        submit =(Button) rootView.findViewById(R.id.submit);
        TextView textreview =(TextView) rootView.findViewById(R.id.textView1);
        SpannableString content2 = new  SpannableString(review);
        content2.setSpan(new UnderlineSpan(), 0, review.length(), 0);
        textreview.setText(content2);
        titleAlert = (TextView) rootView.findViewById(R.id.textView3);
        productAlert = (TextView) rootView.findViewById(R.id.textView4);
        customerAlert = (TextView) rootView.findViewById(R.id.textView5);

        TextView odrid = (TextView) rootView.findViewById(R.id.order);
        TextView cost =(TextView) rootView.findViewById(R.id.total);
        odrid.setText(orderid);
        cost.setText(pcost);

        productRatingBar = (RatingBar) rootView.findViewById(R.id.getRating);
        customerRatingBar = (RatingBar) rootView.findViewById(R.id.getRating2);
        productRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                curRate = Float.valueOf(decimalFormat.format(rating));
                String currate = String.valueOf(rating);
                System.out.println("currate"+currate);
                rateFirst = (int) curRate;
                System.out.println("currate"+rateFirst);

                if (rateFirst <= 3)
                {
                            productReview.setVisibility(View.VISIBLE);
                            head1.setVisibility(View.VISIBLE);
                }
                else
                {
                            productReview.setVisibility(View.GONE);
                            head1.setVisibility(View.GONE);
                }
            }
        });
        customerRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                curRate = Float.valueOf(decimalFormat.format(rating));
                String currating = String.valueOf(rating);
                rateSecond = (int) curRate;
                if (rateSecond <= 3)
                {
                            customerExperience.setVisibility(View.VISIBLE);
                            head2.setVisibility(View.VISIBLE);
                }
                else
                {
                            customerExperience.setVisibility(View.GONE);
                            head2.setVisibility(View.GONE);
                }
            }
        });
        initUI();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return rootView;
    }

    private void initUI() {
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ProductRating = String.valueOf(rateFirst);
                CustomerRating = String.valueOf(rateSecond);
                System.out.println(" Product  " + ProductRating + " Customer  " + CustomerRating);

                title = reviewTitle.getText().toString();
                productreview = productReview.getText().toString();
                customerexperience = customerExperience.getText().toString();
                extraComment = extraComments.getText().toString();

                if (title.equals("") && ProductRating.equals("0") && CustomerRating.equals("0")) {

                    titleAlert.setVisibility(View.VISIBLE);
                    productAlert.setVisibility(View.VISIBLE);
                    customerAlert.setVisibility(View.VISIBLE);
                } else if (title.equals("")) {
                    titleAlert.setVisibility(View.VISIBLE);
                    productAlert.setVisibility(View.GONE);
                    customerAlert.setVisibility(View.GONE);

                } else if (ProductRating.equals("0")) {
                    titleAlert.setVisibility(View.GONE);
                    productAlert.setVisibility(View.VISIBLE);
                    customerAlert.setVisibility(View.GONE);
                }
                else if (CustomerRating.equals("0")) {
                    titleAlert.setVisibility(View.GONE);
                    productAlert.setVisibility(View.GONE);
                    customerAlert.setVisibility(View.VISIBLE);
                } else
                {
                new ReviewFormsyncTask().execute();
            }
            }
        });

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

    }

    class ReviewFormsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public ReviewFormsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
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

                paramsList.put("reviewtitle", title);
                paramsList.put("reviewprodcomment", productreview);
                paramsList.put("reviewcustexpcomment", customerexperience);
                paramsList.put("reviewcomments", extraComment);
                paramsList.put("reviewprodrating", ProductRating);
                paramsList.put("reviewcustexprating", CustomerRating);
                paramsList.put("name", Utility.getSharedPrefStringData(getActivity(), Constants.USER_NAME)) ;
                paramsList.put("id", Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID)) ;
//                paramsList.put("name", Uname);
//                paramsList.put("id", U_id);
                paramsList.put("reviewOrderid", orderid);
                paramsList.put("StatusType", "Review");
                paramsList.put("cartProdId", cartProdId);
                paramsList.put("cartId", cartId);
                result = Utility.httpPostRequestToServer(ApiConstants.FORMS_SUBMIT, Utility.getParams(paramsList));

            }catch (Exception exception) {
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
                        JSONObject jObj = new JSONObject(response);
                        String success = jObj.getString("success");
                        String message = jObj.getString("message");
                        System.out.println("cartProdId   " +cartProdId );
                        System.out.println("cartId       " +cartId );
                        System.out.println("Call me form Details "+success+ " " +message);
                        if(success.equals("1"))
                        {
                            Utility.navigateDashBoardFragment(new MyOrderFragment(), MyOrderFragment.TAG, null, getActivity());
                        }
                        else
                        {
                            Utility.showToastMessage(getActivity(), "form details not inserted");
                        }
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
