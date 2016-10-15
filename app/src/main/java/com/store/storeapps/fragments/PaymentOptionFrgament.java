package com.store.storeapps.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;

/**
 * Created by satyanarayana pdv on 10/13/2016.
 */

public class PaymentOptionFrgament extends Fragment {

    public static final String TAG = "PaymentOptionFrgament";
    private View rootView;
    private LayoutInflater mInflater;
    CheckBox pmcheckbutton;
    TextView codchargesHead, codchargesValue, codchargesQuote, amounttotal, cashtext,pmamount,price;
    private RelativeLayout expand1, expand2 ,expand3, expand4, expand5,
            childexpand1, childexpand2, childexpand3, childexpand4, childexpand5;
    Button childpaysecurely1, childpaysecurely2, childpaysecurely3, childpaysecurely4,
            sendOTP, confirmorder, confirmOTP;
    String updatedValue, updatedValue2;
    String orderid, CartProductId, U_id;
    String P_Cost,Quantity;
    String ProductId,P_Name, codcharge,  amountPayable, Promocode, fname, bline, bcity, bstate, bpincode, bmobile, email, cartId ,pmcash;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.payment_option, container, false);
//        orderid = MyOrderFragment.orderID;
//        System.out.println("CartProductId  " +CartProductId);
        cartId ="CT152632";
        U_id ="193";
        email ="papanianu@gmail.com";
        P_Cost ="599";
        amountPayable = "999";
        ProductId="PM010247";
        P_Name="Ghost Busters Keychain";
        codcharge = "75";
        Quantity = "3";
        fname="Anusha Papani";
        bline = "hyd";
        bstate = "hyd";
        bpincode = "hyd";
        bmobile = "7416393994";
        orderid = "3PMPM100200300";
        pmcash = "900";

        initUI();
        return rootView;
    }

    private void initUI() {
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

        childpaysecurely1.setText("Pay Securely");
        childpaysecurely2.setText("Pay Securely");
        childpaysecurely3.setText("Pay Securely");
        childpaysecurely4.setText("Pay Securely");
        sendOTP.setText("Send OTP");

        amounttotal = (TextView) rootView.findViewById(R.id.amountpaycash);
        cashtext = (TextView) rootView.findViewById(R.id.currentcashbal);
        price= (TextView) rootView.findViewById(R.id.totalprice);
        pmamount = (TextView) rootView.findViewById(R.id.pmstorecashamount);
        pmcheckbutton = (CheckBox) rootView.findViewById(R.id.pmcheck);
        codchargesValue = (TextView) rootView.findViewById(R.id.codchargesValue);

        price.setText(amountPayable);
        amounttotal.setText(amountPayable);
        cashtext.setText(pmcash);
        codchargesValue.setText(codcharge);

        codchargesHead = (TextView)  rootView.findViewById(R.id.codchargesHead);
        codchargesQuote = (TextView)  rootView.findViewById(R.id.codchargesQuote);
        codchargesValue = (TextView)  rootView.findViewById(R.id.codchargesValue);

        codchargesHead.setVisibility(View.GONE);
        codchargesQuote.setVisibility(View.GONE);
        codchargesValue.setVisibility(View.GONE);

        mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        try {
            if(Integer.parseInt(pmcash) >= Integer.parseInt(amountPayable))
            {
                amounttotal.setText(""+0);
                pmamount.setText(""+amountPayable);
                cashtext.setText(""+(Integer.parseInt(pmcash) - Integer.parseInt(amountPayable))+")");
                confirmorder.setVisibility(View.VISIBLE);

                codchargesHead.setVisibility(View.GONE);
                codchargesQuote.setVisibility(View.GONE);
                codchargesValue.setVisibility(View.GONE);
            }
            else if(Integer.parseInt(amountPayable) > Integer.parseInt(pmcash))
            {
                amounttotal.setText(""+(Integer.parseInt(amountPayable) - Integer.parseInt(pmcash)));
                int i = Integer.parseInt(amountPayable) - Integer.parseInt(pmcash);
//                updatedValue = Integer.toString(i);
                pmamount.setText(""+pmcash);
                cashtext.setText(""+0+")");
                confirmorder.setVisibility(View.GONE);

                expand1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(childexpand1.getVisibility() == View.VISIBLE)
                        {
                            childexpand1.setVisibility(View.GONE);
                            childexpand2.setVisibility(View.GONE);
                            childexpand3.setVisibility(View.GONE);
                            childexpand4.setVisibility(View.GONE);
                            childexpand5.setVisibility(View.GONE);

                            codchargesHead.setVisibility(View.GONE);
                            codchargesQuote.setVisibility(View.GONE);
                            codchargesValue.setVisibility(View.GONE);
                            confirmorder.setVisibility(View.GONE);

//                expand1.setBackground(R.drawable.paymentparentbackground);
                            expand1.setBackgroundResource(R.drawable.border);
                            expand2.setBackgroundResource(R.drawable.border);
                            expand3.setBackgroundResource(R.drawable.border);
                            expand4.setBackgroundResource(R.drawable.border);
                            expand5.setBackgroundResource(R.drawable.border);

                        }
                        else {
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
                        }
                    }
                });

                expand2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childexpand2.getVisibility() == View.VISIBLE)
                        {
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

                        }
                        else {
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
                        }
                    }
                });

                expand3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childexpand3.getVisibility() == View.VISIBLE)
                        {
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

                        }
                        else {

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
                        }
                    }
                });

                expand4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childexpand4.getVisibility() == View.VISIBLE)
                        {
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

                        }
                        else {
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
                        }
                    }
                });

                expand5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childexpand5.getVisibility() == View.VISIBLE)
                        {
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

                            amounttotal.setText(""+(Integer.parseInt(amountPayable)));

                        }
                        else {
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

                            amounttotal.setText(""+(Integer.parseInt(amountPayable) + Integer.parseInt(codcharge)));

                        }
                    }
                });

            }

            else if (Integer.parseInt(amountPayable) == 0) {
                pmcheckbutton.setEnabled(false);
                amounttotal.setText(""+amountPayable);
                cashtext.setText(""+pmcash+")");
                pmamount.setText(""+0);
                confirmorder.setVisibility(View.GONE);


            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            if(Integer.parseInt(pmcash) == 0){
                pmcheckbutton.setChecked(false);
                pmcheckbutton.setEnabled(false);

                expand1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(childexpand1.getVisibility() == View.VISIBLE)
                        {
                            childexpand1.setVisibility(View.GONE);
                            childexpand2.setVisibility(View.GONE);
                            childexpand3.setVisibility(View.GONE);
                            childexpand4.setVisibility(View.GONE);
                            childexpand5.setVisibility(View.GONE);

                            codchargesHead.setVisibility(View.GONE);
                            codchargesQuote.setVisibility(View.GONE);
                            codchargesValue.setVisibility(View.GONE);
                            confirmorder.setVisibility(View.GONE);

//                expand1.setBackground(R.drawable.paymentparentbackground);
                            expand1.setBackgroundResource(R.drawable.border);
                            expand2.setBackgroundResource(R.drawable.border);
                            expand3.setBackgroundResource(R.drawable.border);
                            expand4.setBackgroundResource(R.drawable.border);
                            expand5.setBackgroundResource(R.drawable.border);

                        }
                        else {
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
                        }
                    }
                });

                expand2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childexpand2.getVisibility() == View.VISIBLE)
                        {
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

                        }
                        else {
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
                        }
                    }
                });

                expand3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childexpand3.getVisibility() == View.VISIBLE)
                        {
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

                        }
                        else {

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
                        }
                    }
                });

                expand4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childexpand4.getVisibility() == View.VISIBLE)
                        {
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

                        }
                        else {
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
                        }
                    }
                });

                expand5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childexpand5.getVisibility() == View.VISIBLE)
                        {
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

                            amounttotal.setText(""+(Integer.parseInt(amountPayable)));


                        }
                        else {
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

                            amounttotal.setText(""+(Integer.parseInt(amountPayable) + Integer.parseInt(codcharge)));

                        }
                    }
                });
            }
            else{
                pmcheckbutton.setChecked(true);
                int intpmcash = Integer.parseInt(pmcash);
                String pmcashused = Integer.toString(intpmcash);
                System.out.println("SET PM CASH11111111"+intpmcash);
                codchargesHead.setVisibility(View.GONE);
                codchargesQuote.setVisibility(View.GONE);
                codchargesValue.setVisibility(View.GONE);
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        pmcheckbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pmcheckbutton.isChecked()) {
                    if(Integer.parseInt(pmcash) >= Integer.parseInt(amountPayable))
                    {
                        amounttotal.setText(""+0);
                        pmamount.setText(""+amountPayable);
                        cashtext.setText(""+(Integer.parseInt(pmcash) - Integer.parseInt(amountPayable))+")");
                        confirmorder.setVisibility(View.VISIBLE);

                        expand1.setEnabled(false);
                        expand2.setEnabled(false);
                        expand3.setEnabled(false);
                        expand4.setEnabled(false);
                        expand5.setEnabled(false);

//                        codchargesHead.setVisibility(View.GONE);
//                        codchargesQuote.setVisibility(View.GONE);
//                        codchargesValue.setVisibility(View.GONE);
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
                    }
                    else if(Double.parseDouble(amountPayable) > Double.parseDouble(pmcash))
                    {
                        amounttotal.setText(""+(Integer.parseInt(amountPayable) - Integer.parseInt(pmcash)));
                        pmamount.setText(""+pmcash);
                        cashtext.setText(""+0+")");
                        confirmorder.setVisibility(View.GONE);
                        expand1.setEnabled(true);
                        expand2.setEnabled(true);
                        expand3.setEnabled(true);
                        expand4.setEnabled(true);
                        expand5.setEnabled(true);

                        if(codchargesValue.getVisibility()== View.VISIBLE)
                        {
                            amounttotal.setText(""+(Integer.parseInt(amountPayable) - Integer.parseInt(pmcash)+ Integer.parseInt(codcharge)));
                        }
                        expand1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(childexpand1.getVisibility() == View.VISIBLE)
                                {
                                    childexpand1.setVisibility(View.GONE);
                                    childexpand2.setVisibility(View.GONE);
                                    childexpand3.setVisibility(View.GONE);
                                    childexpand4.setVisibility(View.GONE);
                                    childexpand5.setVisibility(View.GONE);

                                    codchargesHead.setVisibility(View.GONE);
                                    codchargesQuote.setVisibility(View.GONE);
                                    codchargesValue.setVisibility(View.GONE);
                                    confirmorder.setVisibility(View.GONE);

//                expand1.setBackground(R.drawable.paymentparentbackground);
                                    expand1.setBackgroundResource(R.drawable.border);
                                    expand2.setBackgroundResource(R.drawable.border);
                                    expand3.setBackgroundResource(R.drawable.border);
                                    expand4.setBackgroundResource(R.drawable.border);
                                    expand5.setBackgroundResource(R.drawable.border);

                                }
                                else {
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
                                }
                            }
                        });

                        expand2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(childexpand2.getVisibility() == View.VISIBLE)
                                {
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

                                }
                                else {
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
                                }
                            }
                        });

                        expand3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(childexpand3.getVisibility() == View.VISIBLE)
                                {
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

                                }
                                else {

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
                                }
                            }
                        });

                        expand4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(childexpand4.getVisibility() == View.VISIBLE)
                                {
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

                                }
                                else {
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
                                }
                            }
                        });

                        expand5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(childexpand5.getVisibility() == View.VISIBLE)
                                {
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

                                    amounttotal.setText(""+(Integer.parseInt(amountPayable) - Integer.parseInt(pmcash)));

                                }
                                else {
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

                                    amounttotal.setText(""+(Integer.parseInt(amountPayable) - Integer.parseInt(pmcash) + Integer.parseInt(codcharge)));

                                }
                            }
                        });

                    }
                } 	else {

                    if(codchargesValue.getVisibility()== View.VISIBLE)
                    {
                        amounttotal.setText(""+(Integer.parseInt(amountPayable) + Integer.parseInt(codcharge)));
                        cashtext.setText("" + pmcash + ")");
                        pmamount.setText("" + 0);

                    }
                    else
                    {
                        amounttotal.setText("" + amountPayable);
                        cashtext.setText("" + pmcash + ")");
                        pmamount.setText("" + 0);
                        confirmorder.setVisibility(View.GONE);
                        expand1.setEnabled(true);
                        expand2.setEnabled(true);
                        expand3.setEnabled(true);
                        expand4.setEnabled(true);
                        expand5.setEnabled(true);
                    }
                }
            }
        });

    }
}
