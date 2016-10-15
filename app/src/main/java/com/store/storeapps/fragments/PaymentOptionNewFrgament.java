package com.store.storeapps.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.store.storeapps.R;
import com.store.storeapps.activities.PaymentActivity;
import com.store.storeapps.activities.StatusActivity;
import com.store.storeapps.utility.Utility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shankar on 10/15/2016.
 */

public class PaymentOptionNewFrgament extends Fragment implements View.OnClickListener , PaymentResultListener {

    public static final String TAG = "PaymentOptionNewFrgament";
    private View rootView;
    private LayoutInflater mInflater;
//    public static String orderID , CartPID, Pimage, Pname, Pcost, Orderstatus, Orderdate ,USername ,Uid ,PaymentType,PQuantity;
    CheckBox pmcheckbutton;
    TextView codchargesHead, codchargesValue, codchargesQuote, amounttotal, cashtext, pmamount, price;
    private RelativeLayout expand1, expand2, expand3, expand4, expand5,
            childexpand1, childexpand2, childexpand3, childexpand4, childexpand5;
    Button childpaysecurely1, childpaysecurely2, childpaysecurely3, childpaysecurely4,
            sendOTP, confirmorder, confirmOTP;
    String FinalPriceToSend;
    String updatedValue, updatedValue2;
    String orderid, CartProductId, U_id;
    String P_Cost, Quantity;
    String ProductId, P_Name, codcharge, amountPayable, Promocode, fname, bline, bcity, bstate, bpincode, bmobile, email, cartId, pmcash;
    private boolean fromCOD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.payment_option, container, false);
        initUI();
        return rootView;
    }

    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "3PMstore");
            options.put("description", P_Name);
            options.put("currency", "INR");
            options.put("amount", FinalPriceToSend);
            JSONObject preFill = new JSONObject();
            preFill.put("email",email );
            preFill.put("contact", bmobile);
            options.put("prefill", preFill);

            co.setPublicKey("rzp_live_bKbvbtZ8byoBY9");
            co.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(getActivity(), "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), response+"Error on payment", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    public void onStartTransaction() {
        PaytmPGService Service = PaytmPGService.getProductionService();
        //		getStagingService();
        Map<String, String> paramMap = new HashMap<String, String>();

        // these are mandatory parameters
        paramMap.put("ORDER_ID", orderid);
        paramMap.put("MID", "Mersey83050553367323");
        paramMap.put("CUST_ID", U_id);
        paramMap.put("CHANNEL_ID", "WEB");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail120");
        paramMap.put("WEBSITE", "Merseywap");
        paramMap.put("TXN_AMOUNT", amounttotal.getText().toString());
        paramMap.put("THEME", "merchant");
        paramMap.put("EMAIL", email);
        paramMap.put("MOBILE_NO", bmobile);
        PaytmOrder Order = new PaytmOrder(paramMap);

        PaytmMerchant Merchant = new PaytmMerchant(
                "https://3pmstore.com/android/android_connect/paytm/generateChecksum.php",
                "https://3pmstore.com/android/android_connect/paytm/verifyChecksum.php");
        Service.initialize(Order, Merchant, null);

        Service.startPaymentTransaction(getActivity(), true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                    }

                    @Override
                    public void onTransactionSuccess(Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction is successful " + inResponse);
                        String status = "Paytm Transaction Successful!";
                        String P_Type = "PayTM";
                        Intent intent = new Intent(getActivity(),StatusActivity.class);
                        intent.putExtra("transStatus", status);
                        startActivity(intent);
                        Toast.makeText(getActivity(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionFailure(String inErrorMessage,
                                                     Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getActivity(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() {
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {

                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                    }

                    @Override
                    public void onBackPressedCancelTransaction() {
                        // TODO Auto-generated method stub
//                        Intent i=new Intent(PaytmActivity.this,PaymentOption.class);
//                        i.putExtra("spiner", spinner_item);
//                        i.putExtra("Promo",amountPayable);
//                        i.putExtra("Pmprice",pmcash );
//                        i.putExtra("cost", Intentcost);
//                        i.putExtra("coddisable", coddisable);
//                        i.putExtra("otpmob",otpmob);
//                        //		i.putExtra("BalCash", currentbalance);
//                        i.putExtra("Amount",amount );
//                        i.putExtra("CodCash",globalVariable.getAdminCod().toString());
//                        startActivity(i);
//                        finish();
                    }

                });
    }

    private void initUI() {
        cartId = "CT152632";
        U_id = "193";
        email = "papanianu@gmail.com";
        P_Cost = "599";
        amountPayable = "999";
        ProductId = "PM010247";
        P_Name = "Ghost Busters Keychain";
        codcharge = "75";
        Quantity = "3";
        fname = "Anusha Papani";
        bline = "hyd";
        bstate = "hyd";
        bpincode = "hyd";
        bmobile = "7416393994";
        orderid = "3PMPM100200400";
        pmcash = "900";


        expand1 = (RelativeLayout) rootView.findViewById(R.id.expand1);
        expand2 = (RelativeLayout) rootView.findViewById(R.id.expand2);
        expand3 = (RelativeLayout) rootView.findViewById(R.id.expand3);
        expand4 = (RelativeLayout) rootView.findViewById(R.id.expand4);
        expand5 = (RelativeLayout) rootView.findViewById(R.id.expand5);

        childexpand1 = (RelativeLayout) rootView.findViewById(R.id.childexpand1);
        childexpand2 = (RelativeLayout) rootView.findViewById(R.id.childexpand2);
        childexpand3 = (RelativeLayout) rootView.findViewById(R.id.childexpand3);
        childexpand4 = (RelativeLayout) rootView.findViewById(R.id.childexpand4);
        childexpand5 = (RelativeLayout) rootView.findViewById(R.id.childexpand5);

        childpaysecurely1 = (Button) rootView.findViewById(R.id.childpaysecurely1);
        childpaysecurely2 = (Button) rootView.findViewById(R.id.childpaysecurely2);
        childpaysecurely3 = (Button) rootView.findViewById(R.id.childpaysecurely3);
        childpaysecurely4 = (Button) rootView.findViewById(R.id.childpaysecurely4);
        sendOTP = (Button) rootView.findViewById(R.id.sendOTP);
        confirmorder = (Button) rootView.findViewById(R.id.confirmorder);
        //        confirmOTP =(Button) rootView.findViewById(R.id.confirmOTP);

        codchargesHead = (TextView) rootView.findViewById(R.id.codchargesHead);
        codchargesQuote = (TextView) rootView.findViewById(R.id.codchargesQuote);
        codchargesValue = (TextView) rootView.findViewById(R.id.codchargesValue);

        childpaysecurely1.setText("Pay Securely");
        childpaysecurely2.setText("Pay Securely");
        childpaysecurely3.setText("Pay Securely");
        childpaysecurely4.setText("Pay Securely");
        sendOTP.setText("Send OTP");



        amounttotal = (TextView) rootView.findViewById(R.id.amountpaycash);
        cashtext = (TextView) rootView.findViewById(R.id.currentcashbal);
        price = (TextView) rootView.findViewById(R.id.totalprice);
        pmamount = (TextView) rootView.findViewById(R.id.pmstorecashamount);
        pmcheckbutton = (CheckBox) rootView.findViewById(R.id.pmcheck);
        codchargesValue = (TextView) rootView.findViewById(R.id.codchargesValue);
        fromCOD = false;
        price.setText("" + amountPayable);

        codchargesHead.setVisibility(View.GONE);
        codchargesQuote.setVisibility(View.GONE);
        codchargesValue.setVisibility(View.GONE);
        pmcheckbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (fromCOD) {
                    calculateTotalFare("COD", 75);
                } else {
                    calculateTotalFare("Default", 0);
                }
            }
        });
        pmcheckbutton.setChecked(true);

        expand1.setOnClickListener(this);
        expand5.setOnClickListener(this);
        expand4.setOnClickListener(this);
        expand3.setOnClickListener(this);
        expand2.setOnClickListener(this);


        childpaysecurely1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int costValue = Integer.parseInt(amounttotal.getText().toString());
                int FinalPrice = costValue * 100;
                FinalPriceToSend = Integer.toString(FinalPrice);
                startPayment();

            }
        });
        childpaysecurely2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onStartTransaction();

            }
        });
        childpaysecurely3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        childpaysecurely4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString("Quantity",Quantity);
                b.putString("orderid",orderid);
//               Intent i =new Intent(PaymentOption.this,Payumoney.class);
                b.putString("Promo",amounttotal.getText().toString());
                b.putString("Pmprice",pmcash );
                b.putString("cost", amounttotal.getText().toString());
                b.putString("coddisable", "");
                b.putString("otpmob",bmobile);
                b.putString("email",email);
                b.putString("Amount",amounttotal.getText().toString() );
                b.putString("CodCash",codcharge);
                b.putString("fname",fname);
                b.putString("pname",P_Name);
                b.putString("UID",U_id);
                b.putString("Pid",ProductId);
//                Intent i = new Intent(getActivity(), PayUMoneyFragment.class);
//                startActivity(i);

                Utility.navigateDashBoardFragment(new PayUMoneyFragment(), PayUMoneyFragment.TAG, b, getActivity());
//                b.putString("Ptype",Ptype);
            }
        });

    }


    private void calculateTotalFare(String from, int number) {
        int total = Integer.parseInt(amountPayable);
        if (pmcheckbutton.isChecked()) {
            int remaningAmount = Integer.parseInt(pmcash) - Integer.parseInt(amountPayable);
            if (Integer.parseInt(pmcash) > Integer.parseInt(amountPayable)) {
                pmamount.setText("" + Integer.parseInt(amountPayable));
                amounttotal.setText("0");
                cashtext.setText("" + remaningAmount);

                expand1.setEnabled(false);
                expand2.setEnabled(false);
                expand3.setEnabled(false);
                expand4.setEnabled(false);
                expand5.setEnabled(false);

                childexpand1.setVisibility(View.GONE);
                childexpand2.setVisibility(View.GONE);
                childexpand3.setVisibility(View.GONE);
                childexpand4.setVisibility(View.GONE);
                childexpand5.setVisibility(View.GONE);

                codchargesHead.setVisibility(View.GONE);
                codchargesQuote.setVisibility(View.GONE);
                codchargesValue.setVisibility(View.GONE);
                confirmorder.setVisibility(View.GONE);

                expand1.setBackgroundResource(R.drawable.border);
                expand2.setBackgroundResource(R.drawable.border);
                expand3.setBackgroundResource(R.drawable.border);
                expand4.setBackgroundResource(R.drawable.border);
                expand5.setBackgroundResource(R.drawable.border);
            } else {
                expand1.setEnabled(true);
                expand2.setEnabled(true);
                expand3.setEnabled(true);
                expand4.setEnabled(true);
                expand5.setEnabled(true);
                if (from.equalsIgnoreCase("COD")) {
                    total = total - Integer.parseInt(pmcash) + number;
                    pmamount.setText("" + Integer.parseInt(pmcash));
                    amounttotal.setText("" + total);
                    cashtext.setText("0");
                    codchargesValue.setText("" + number);
                } else {
                    total = total - Integer.parseInt(pmcash);
                    pmamount.setText("" + Integer.parseInt(pmcash));
                    amounttotal.setText("" + total);
                    cashtext.setText("0");
                }
            }
        } else {
            expand1.setEnabled(true);
            expand2.setEnabled(true);
            expand3.setEnabled(true);
            expand4.setEnabled(true);
            expand5.setEnabled(true);
            if (from.equalsIgnoreCase("COD")) {
                total = total + number;
                cashtext.setText("" + Integer.parseInt(pmcash));
                amounttotal.setText("" + total);
                pmamount.setText("0");
                codchargesValue.setText("" + number);
            } else {
                cashtext.setText("" + Integer.parseInt(pmcash));
                amounttotal.setText("" + total);
                pmamount.setText("0");
                codchargesValue.setText("0");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expand1:
                visablityHanlde(childexpand1, 1);
                break;
            case R.id.expand2:
                visablityHanlde(childexpand2, 2);
                break;
            case R.id.expand3:
                visablityHanlde(childexpand3, 3);
                break;
            case R.id.expand4:
                visablityHanlde(childexpand4, 4);
                break;
            case R.id.expand5:
                visablityHanlde(childexpand5, 5);
                break;
        }
    }

    private void visablityHanlde(View child, int position) {
        fromCOD = false;
        if (child.getVisibility() == View.VISIBLE) {
            childexpand1.setVisibility(View.GONE);
            childexpand2.setVisibility(View.GONE);
            childexpand3.setVisibility(View.GONE);
            childexpand4.setVisibility(View.GONE);
            childexpand5.setVisibility(View.GONE);

            codchargesHead.setVisibility(View.GONE);
            codchargesQuote.setVisibility(View.GONE);
            codchargesValue.setVisibility(View.GONE);
            confirmorder.setVisibility(View.GONE);

            expand1.setBackgroundResource(R.drawable.border);
            expand2.setBackgroundResource(R.drawable.border);
            expand3.setBackgroundResource(R.drawable.border);
            expand4.setBackgroundResource(R.drawable.border);
            expand5.setBackgroundResource(R.drawable.border);
            calculateTotalFare("Default", 0);
        } else {
            if (position == 1) {
                childexpand1.setVisibility(View.VISIBLE);
                childexpand2.setVisibility(View.GONE);
                childexpand3.setVisibility(View.GONE);
                childexpand4.setVisibility(View.GONE);
                childexpand5.setVisibility(View.GONE);

                codchargesHead.setVisibility(View.GONE);
                codchargesQuote.setVisibility(View.GONE);
                codchargesValue.setVisibility(View.GONE);
                confirmorder.setVisibility(View.GONE);

                expand1.setBackgroundResource(R.drawable.paymentparentbackground);
                expand2.setBackgroundResource(R.drawable.border);
                expand3.setBackgroundResource(R.drawable.border);
                expand4.setBackgroundResource(R.drawable.border);
                expand5.setBackgroundResource(R.drawable.border);
                calculateTotalFare("DEFULT", 0);
            } else if (position == 2) {
                childexpand1.setVisibility(View.GONE);
                childexpand2.setVisibility(View.VISIBLE);
                childexpand3.setVisibility(View.GONE);
                childexpand4.setVisibility(View.GONE);
                childexpand5.setVisibility(View.GONE);

                codchargesHead.setVisibility(View.GONE);
                codchargesQuote.setVisibility(View.GONE);
                codchargesValue.setVisibility(View.GONE);
                confirmorder.setVisibility(View.GONE);

                expand1.setBackgroundResource(R.drawable.border);
                expand2.setBackgroundResource(R.drawable.paymentparentbackground);
                expand3.setBackgroundResource(R.drawable.border);
                expand4.setBackgroundResource(R.drawable.border);
                expand5.setBackgroundResource(R.drawable.border);
                calculateTotalFare("DEFULT", 0);
            } else if (position == 3) {
                childexpand1.setVisibility(View.GONE);
                childexpand2.setVisibility(View.GONE);
                childexpand3.setVisibility(View.VISIBLE);
                childexpand4.setVisibility(View.GONE);
                childexpand5.setVisibility(View.GONE);

                codchargesHead.setVisibility(View.GONE);
                codchargesQuote.setVisibility(View.GONE);
                codchargesValue.setVisibility(View.GONE);
                confirmorder.setVisibility(View.GONE);

                expand1.setBackgroundResource(R.drawable.border);
                expand2.setBackgroundResource(R.drawable.border);
                expand3.setBackgroundResource(R.drawable.paymentparentbackground);
                expand4.setBackgroundResource(R.drawable.border);
                expand5.setBackgroundResource(R.drawable.border);
                calculateTotalFare("DEFULT", 0);
            } else if (position == 4) {
                childexpand1.setVisibility(View.GONE);
                childexpand2.setVisibility(View.GONE);
                childexpand3.setVisibility(View.GONE);
                childexpand4.setVisibility(View.VISIBLE);
                childexpand5.setVisibility(View.GONE);

                codchargesHead.setVisibility(View.GONE);
                codchargesQuote.setVisibility(View.GONE);
                codchargesValue.setVisibility(View.GONE);
                confirmorder.setVisibility(View.GONE);

                expand1.setBackgroundResource(R.drawable.border);
                expand2.setBackgroundResource(R.drawable.border);
                expand3.setBackgroundResource(R.drawable.border);
                expand4.setBackgroundResource(R.drawable.paymentparentbackground);
                expand5.setBackgroundResource(R.drawable.border);
                calculateTotalFare("DEFULT", 0);

            } else if (position == 5) {
                fromCOD = true;
                childexpand1.setVisibility(View.GONE);
                childexpand2.setVisibility(View.GONE);
                childexpand3.setVisibility(View.GONE);
                childexpand4.setVisibility(View.GONE);
                childexpand5.setVisibility(View.VISIBLE);

                codchargesHead.setVisibility(View.VISIBLE);
                codchargesQuote.setVisibility(View.VISIBLE);
                codchargesValue.setVisibility(View.VISIBLE);
                confirmorder.setVisibility(View.GONE);

                expand1.setBackgroundResource(R.drawable.border);
                expand2.setBackgroundResource(R.drawable.border);
                expand3.setBackgroundResource(R.drawable.border);
                expand4.setBackgroundResource(R.drawable.border);
                expand5.setBackgroundResource(R.drawable.paymentparentbackground);
                calculateTotalFare("COD", 75);
            }
        }
    }


}
