package com.store.storeapps.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.activities.StatusActivity;
import com.store.storeapps.utility.Utility;

import org.json.JSONObject;

import static com.store.storeapps.R.id.view;

/**
 * Created by satyanarayana pdv on 10/15/2016.
 */

public class PaymentActivityFragment extends Fragment implements PaymentResultListener {
//    private static final String TAG = PaymentActivity.class.getSimpleName();
public static final String TAG = "PaymentActivityFragment";
    private HomeActivity mParent;
    private View rootView;
    String Orderid, Name, Email, TotalCost, Mobile, UID, cartId;
    String spinner_item, TotalAmount, otpmob, pmcash, amount, Codcash, coddisable, amountPayable, productname;
    String FinalPriceToSend;
    TextView orderid, pname, cost, product;
    String rs = "INR ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();
        if (getArguments() != null) {
            spinner_item = getArguments().getString("spiner");
            Orderid = getArguments().getString("orderID");
            TotalAmount = getArguments().getString("cost");
            TotalCost= getArguments().getString("cost");
            otpmob = getArguments().getString("otpmob");
            pmcash = getArguments().getString("Pmprice");
            amount = getArguments().getString("Amount");
            Codcash = getArguments().getString("CodCash");
            Mobile = getArguments().getString("otpmob");
            Email = getArguments().getString("email");
            cartId = getArguments().getString("cartId");
            amountPayable = getArguments().getString("Promo");
            Utility.showLog("position", "position" + spinner_item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.razorpay, container, false);
        int costValue = Integer.parseInt(TotalCost);
        int FinalPrice = costValue * 100;
        FinalPriceToSend = Integer.toString(FinalPrice);
        startPayment();
        initUI();

        return rootView;
    }

    private void initUI() {
        orderid = (TextView) rootView.findViewById(R.id.orderid);
        pname = (TextView) rootView.findViewById(R.id.pname);
        cost = (TextView) rootView.findViewById(R.id.cost);
        product = (TextView) rootView.findViewById(R.id.pname);
        try {


            orderid.setText(Orderid);
            cost.setText(rs + TotalCost);
            pname.setText(productname);

        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "3PMstore");
            options.put("description", productname);
            options.put("currency", "INR");
            options.put("amount", FinalPriceToSend);
            JSONObject preFill = new JSONObject();
            preFill.put("email",Email );
            preFill.put("contact", otpmob);
            options.put("prefill", preFill);

            co.setPublicKey("rzp_live_bKbvbtZ8byoBY9");
            co.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Utility.navigateDashBoardFragment(new PaymentOptionNewFrgament(), PaymentOptionNewFrgament.TAG, null, mParent);

            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
//            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            Log.d("LOG", "Payment Transaction is successful " + razorpayPaymentID);
            String status = "RazorPay Transaction Successful!";
            String P_Type = "RazorPay";
            Intent intent = new Intent(getActivity(), StatusActivity.class);
            intent.putExtra("transStatus", status);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
//            Toast.makeText(this, response+"Error on payment", Toast.LENGTH_SHORT).show();
            Utility.navigateDashBoardFragment(new PaymentOptionNewFrgament(), PaymentOptionNewFrgament.TAG, null, mParent);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
//
//        Utility.navigateDashBoardFragment(new PaymentOptionNewFrgament(), PaymentOptionNewFrgament.TAG, null, PaymentActivity.this);
//
//    }
}

