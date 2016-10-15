package com.store.storeapps.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.store.storeapps.R;

/**
 * Created by shankar on 10/15/2016.
 */

public class PaymentOptionNewFrgament extends Fragment implements View.OnClickListener {

    public static final String TAG = "PaymentOptionNewFrgament";
    private View rootView;
    private LayoutInflater mInflater;

    CheckBox pmcheckbutton;
    TextView codchargesHead, codchargesValue, codchargesQuote, amounttotal, cashtext, pmamount, price;
    private RelativeLayout expand1, expand2, expand3, expand4, expand5,
            childexpand1, childexpand2, childexpand3, childexpand4, childexpand5;
    Button childpaysecurely1, childpaysecurely2, childpaysecurely3, childpaysecurely4,
            sendOTP, confirmorder, confirmOTP;

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
        orderid = "3PMPM100200300";
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
    }

    private void calculateTotalFare(String from, int number) {
        int total = Integer.parseInt(amountPayable);
        if (pmcheckbutton.isChecked()) {
            int remaningAmount = Integer.parseInt(pmcash) - Integer.parseInt(amountPayable);
            if (Integer.parseInt(pmcash) > Integer.parseInt(amountPayable)) {
                pmamount.setText("" + Integer.parseInt(amountPayable));
                amounttotal.setText("0");
                cashtext.setText("" + remaningAmount);
            } else {
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
