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

/**
 * Created by satyanarayana pdv on 10/13/2016.
 */

public class PaymentOptionFrgament extends Fragment {

    public static final String TAG = "PaymentOptionFrgament";
    private View rootView;
    private LayoutInflater mInflater;
    CheckBox pmcheckbox;
    TextView codchargesHead, codchargesValue ,codchargesQuote;
    private RelativeLayout expand1, expand2 ,expand3, expand4, expand5,
            childexpand1, childexpand2, childexpand3, childexpand4, childexpand5;
    Button childpaysecurely1, childpaysecurely2, childpaysecurely3, childpaysecurely4,
            sendOTP, confirmOrder, confirmOTP;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.payment_option, container, false);
//        orderid = MyOrderFragment.orderID;
//        CartProductId = MyOrderFragment.CartPID;
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
        confirmOrder = (Button) rootView.findViewById(R.id.confirmorder);
        //        confirmOTP =(Button) rootView.findViewById(R.id.confirmOTP);

        childpaysecurely1.setText("Pay Securely");
        childpaysecurely2.setText("Pay Securely");
        childpaysecurely3.setText("Pay Securely");
        childpaysecurely4.setText("Pay Securely");
        sendOTP.setText("Send OTP");

        codchargesHead = (TextView)  rootView.findViewById(R.id.codchargesHead);
        codchargesQuote = (TextView)  rootView.findViewById(R.id.codchargesQuote);
        codchargesValue = (TextView)  rootView.findViewById(R.id.codchargesValue);

        mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    confirmOrder.setVisibility(View.GONE);

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
                    confirmOrder.setVisibility(View.GONE);

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
                    confirmOrder.setVisibility(View.GONE);

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
                    confirmOrder.setVisibility(View.GONE);

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
                    confirmOrder.setVisibility(View.GONE);

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
                    confirmOrder.setVisibility(View.GONE);

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
                    confirmOrder.setVisibility(View.GONE);

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
                    confirmOrder.setVisibility(View.GONE);

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
                    confirmOrder.setVisibility(View.GONE);

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
                    childexpand4.setVisibility(View.GONE);
                    childexpand5.setVisibility(View.VISIBLE);

                    codchargesHead.setVisibility(View.VISIBLE);
                    codchargesQuote.setVisibility(View.VISIBLE);
                    codchargesValue.setVisibility(View.VISIBLE);
                    confirmOrder.setVisibility(View.GONE);

                    expand1.setBackgroundResource(R.drawable.border);
                    expand2.setBackgroundResource(R.drawable.border);
                    expand3.setBackgroundResource(R.drawable.border);
                    expand4.setBackgroundResource(R.drawable.border);
                    expand5.setBackgroundResource(R.drawable.paymentparentbackground);
                }
            }
        });


//        getMyOrdersData();
    }

}
