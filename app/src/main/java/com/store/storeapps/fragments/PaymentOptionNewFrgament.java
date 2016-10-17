package com.store.storeapps.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.store.storeapps.activities.FailureActivity;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.activities.StatusActivity;
import com.store.storeapps.activities.SuccessActivity;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static com.store.storeapps.R.layout.toast;

/**
 * Created by shankar on 10/15/2016.
 */

public class PaymentOptionNewFrgament extends Fragment implements View.OnClickListener , PaymentResultListener {

    public static final String TAG = "PaymentOptionNewFrgament";
    private View rootView;
    private LayoutInflater mInflater;
    private HomeActivity mParent;
    CheckBox pmcheckbutton;
    TextView codchargesHead, codchargesValue, codchargesQuote, amounttotal, cashtext, pmamount, price;
    private RelativeLayout expand1, expand2, expand3, expand4, expand5,
            childexpand1, childexpand2, childexpand3, childexpand4, childexpand5;
    Button childpaysecurely1, childpaysecurely2, childpaysecurely3, childpaysecurely4,
            sendOTP, confirmorder, confirmOTP;
    TextView codText,enterotp,otpText,resend;
    EditText otp;
    String FinalPriceToSend,cashused ,otprandom ,totalCOD;
    String updatedValue, updatedValue2;
    String orderid, CartProductId, U_id;
    String P_Cost, Quantity;
    String ProductId, P_Name, codcharge, amountPayable, Promocode, fname, bline, bcity, bstate, bpincode, bmobile, email, cartId, pmcash, coddisable;
    private boolean fromCOD;

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

            email = Utility.getSharedPrefStringData(getActivity(), Constants.USER_EMAIL_ID);
            cartId = HomeActivity.mCartId.toString();
            System.out.println("amountPayable  val" +amountPayable);
        }
    }

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
                    public void onTransactionFailure(String inErrorMessage,Bundle inResponse) {
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
                    public void onErrorLoadingWebPage(int iniErrorCode,String inErrorMessage, String inFailingUrl) {
                    }
                    @Override
                    public void onBackPressedCancelTransaction() {
                    }

                });
    }

    private void initUI() {
//        cartId = "CT152632";
//        U_id = "193";
//        email = "papanianu@gmail.com";
        P_Cost = "599";
//        amountPayable = "999";
        ProductId = "PM010247";
        P_Name = "Ghost Busters Keychain";
//        codcharge = "75";
        Quantity = "3";
//        fname = "Anusha Papani";
        bline = "hyd";
        bstate = "hyd";
//        bpincode = "hyd";
//        bmobile = "7416393994";
//        orderid = "3PMPM100200400";
//        pmcash = "900";
//        coddisable = "0";


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

        childexpand1.setVisibility(View.GONE);
        childexpand2.setVisibility(View.GONE);
        childexpand3.setVisibility(View.GONE);
        childexpand4.setVisibility(View.GONE);
        childexpand5.setVisibility(View.GONE);

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

        codText  =(TextView)rootView.findViewById(R.id.codTV);
        confirmOTP =(Button)rootView.findViewById(R.id.confirmOTP);
        enterotp = (TextView)rootView.findViewById(R.id.enterOTPtext);
        otpText  = (TextView)rootView.findViewById(R.id.otpText);
        resend   = (TextView)rootView.findViewById(R.id.resend);
        otp =(EditText)rootView.findViewById(R.id.enterOTP);

        String newString = "Resend OTP?";
        SpannableString content = new SpannableString(newString);
        content.setSpan(new UnderlineSpan(), 0, newString.length(), 0);
        resend.setText(content);

        otpText.setText("Click here to get OTP on ("+bmobile+")");

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
                    calculateTotalFare("COD", Integer.parseInt(codcharge));
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
            }
        });
        if(coddisable.equals("1"))
        {
            otp.setVisibility(View.GONE);
            enterotp.setVisibility(View.GONE);
            sendOTP.setVisibility(View.GONE);
            otpText.setVisibility(View.GONE);
            confirmOTP.setVisibility(View.GONE);
            resend.setVisibility(View.GONE);
            codText.setVisibility(View.VISIBLE);
        }
        else {
            confirmorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CashSuccess().execute();
                }
            });
            sendOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cashused = pmamount.getText().toString();
                    new otpSend().execute();

                    sendOTP.setVisibility(View.GONE);
                    otpText.setText("OTP is sent to this number (" + bmobile + ")");
//								otpText.setVisibility(View.GONE);
                    confirmOTP.setVisibility(View.VISIBLE);
                    resend.setVisibility(View.VISIBLE);
                    otp.setVisibility(View.VISIBLE);
                    enterotp.setVisibility(View.VISIBLE);
                }
            });
            resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new otpSend().execute();
                    sendOTP.setVisibility(View.GONE);
                    otpText.setText("OTP is sent to this number (" + bmobile + ")");
                    confirmOTP.setVisibility(View.VISIBLE);
                    resend.setVisibility(View.VISIBLE);
                    otp.setVisibility(View.VISIBLE);
                    enterotp.setVisibility(View.VISIBLE);
                }
            });

            confirmOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    String userotp = otp.getText().toString();
                    System.out.println("USER OTP" + userotp);
//                systemotp = globalVariable.getOtpgenerate();
//                System.out.println("SYSTEM OTP"+systemotp);
                    if (userotp.equals(otprandom)) {
                        totalCOD =amounttotal.getText().toString();
                        new CodSuccess().execute();
                    } else {
                    /*TextView t =(TextView)toastRoot2.findViewById(R.id.errortoast);
                    t.setText("Incorrect OTP");
                    toast.setView(toastRoot2);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
                    toast.setDuration(20000);
                    toast.show();*/
                    }
                }
            });
        }
    }

    private void calculateTotalFare(String from, int number) {
        int total = Integer.parseInt(amountPayable);
        if (pmcheckbutton.isChecked()) {
//            int remaningAmount = Integer.parseInt(pmcash) - Integer.parseInt(amountPayable);
            if (Integer.parseInt(pmcash) > Integer.parseInt(amountPayable)) {
                pmamount.setText("" + Integer.parseInt(amountPayable));
                amounttotal.setText("0");
                cashtext.setText("" + (Integer.parseInt(pmcash) - Integer.parseInt(amountPayable)));
                confirmorder.setVisibility(View.VISIBLE);
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
//                childexpand5.setVisibility(View.GONE);
                confirmorder.setVisibility(View.GONE);

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
            confirmorder.setVisibility(View.GONE);

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
                calculateTotalFare("COD", Integer.parseInt(codcharge));
            }
        }
    }
    class otpSend extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog2;

        public otpSend() {
            mCustomProgressDialog2 = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mCustomProgressDialog2.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
        }
        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                Random r = new Random();
                int i1= (100000 + r.nextInt(900000));
                otprandom = Integer.toString(i1);

                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("Orderid", orderid);
                paramsList.put("U_id", U_id);
                paramsList.put("P_Type", "COD");
                paramsList.put("3pmcashused",pmamount.getText().toString() );
                paramsList.put("name", fname);
                paramsList.put("otpgenerate",otprandom);
                paramsList.put("cartId", cartId);
                result = Utility.httpPostRequestToServer(ApiConstants.SEND_OTP, Utility.getParams(paramsList));

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
                    System.out.println("OTP responce "+ response);
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject != null) {
                        JSONObject jObj = new JSONObject(response);
                        String success = jObj.getString("Success");
//                        String message = jObj.getString("message");
                        System.out.println("OTP send  "+success+ "  message");
                    }
                }
                mCustomProgressDialog2.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class CodSuccess extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public CodSuccess() {
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
                paramsList.put("Orderid", orderid);
                paramsList.put("U_id", U_id);
                paramsList.put("P_Type", "COD");
                paramsList.put("3pmcashused",pmamount.getText().toString() );
                paramsList.put("name", fname);
                paramsList.put("EmailID", email);
                paramsList.put("otpgenerate","PromoType");
                paramsList.put("cartId", cartId);
                paramsList.put("totalcod", totalCOD);

                result = Utility.httpPostRequestToServer(ApiConstants.COD_SUCCESS, Utility.getParams(paramsList));

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
                        System.out.println();
                        if(success.equals("1")) {
                            Intent i = new Intent(getActivity(), SuccessActivity.class);
                            i.putExtra("spiner", Quantity);
                            i.putExtra("Promo", amountPayable);
                            i.putExtra("Pmprice", pmcash);
                            i.putExtra("cost", amounttotal.getText().toString());
                            i.putExtra("coddisable", coddisable);
                            i.putExtra("otpmob", bmobile);
                            i.putExtra("Amount", amounttotal.getText().toString());
                            i.putExtra("P_Type", "COD");
                            i.putExtra("CodCash", amounttotal.getText().toString());
                            i.putExtra("Orderid", orderid);
                            i.putExtra("U_id", U_id);
                            i.putExtra("name",fname);
                            i.putExtra("EmailID",email);
                            i.putExtra("cartId", cartId);

                            startActivity(i);
//                        String message = jObj.getString("message");
                            System.out.println("Details " + success + " message");
                        }
                        else {
                            Intent i = new Intent(getActivity(), FailureActivity.class);
                            i.putExtra("spiner", Quantity);
                            i.putExtra("Promo",amountPayable);
                            i.putExtra("Pmprice",pmcash );
                            i.putExtra("cost", amounttotal.getText().toString());
                            i.putExtra("coddisable", "");
                            i.putExtra("otpmob",bmobile);
                            i.putExtra("Amount",amounttotal.getText().toString() );
                            i.putExtra("CodCash",codcharge);
                            startActivity(i);
                        }
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class CashSuccess extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public CashSuccess() {
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
                paramsList.put("Orderid", orderid);
                paramsList.put("U_id", U_id);
                paramsList.put("3pmcashused",pmamount.getText().toString() );
                paramsList.put("P_Type", "3PMstore Cash");
                paramsList.put("EmailID",email);
                paramsList.put("name",fname);
                paramsList.put("cartId", cartId);
                //System.out.println("cartId    "+cartId+"cashused    " +  pmamount.getText().toString());
//                String url=ApiConstants.HURRAY_NOTIFICATION+"?Ordders="+orderid+"&name="+"anusha"+"&EmailID="+email;
                result = Utility.httpPostRequestToServer(ApiConstants.HURRAY_NOTIFICATION, Utility.getParams(paramsList));
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
                        int s= jObj.getInt("success");
                        System.out.println("sssss "+ s + " message");
                        String success = jObj.getString("success");
                        System.out.println("Details "+success+ " message");
                        if(success.equals("1")) {
                            Intent i = new Intent(getActivity(), SuccessActivity.class);
                            i.putExtra("spiner", Quantity);
                            i.putExtra("Promo", amountPayable);
                            i.putExtra("Pmprice", pmcash);
                            i.putExtra("amounttotal", amounttotal.getText().toString());
                            i.putExtra("coddisable", coddisable);
                            i.putExtra("P_Type", "3PMstore Cash");
                            i.putExtra("bmobile", bmobile);
                            i.putExtra("Amount", amounttotal.getText().toString());
                            i.putExtra("CodCash", codcharge);
                            i.putExtra("Orderid", orderid);
                            i.putExtra("U_id", U_id);
                            i.putExtra("name",fname);
                            i.putExtra("EmailID",email);
                            i.putExtra("cartId", cartId);

                            startActivity(i);
                        }
                        else
                        {
                            Intent i = new Intent(getActivity(), FailureActivity.class);
                            i.putExtra("spiner", Quantity);
                            i.putExtra("Promo",amountPayable);
                            i.putExtra("Pmprice",pmcash );
                            i.putExtra("cost", amounttotal.getText().toString());
                            i.putExtra("coddisable", coddisable);
                            i.putExtra("otpmob",bmobile);
                            i.putExtra("Amount",amounttotal.getText().toString() );
                            i.putExtra("CodCash",codcharge);

                            startActivity(i);
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