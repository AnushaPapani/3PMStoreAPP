package com.store.storeapps.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.store.storeapps.R;

/**
Created by Anusha
 */
public class DeliveryFragment extends Fragment {

    public static final String TAG = "DeliveryFragment";
    private View rootView;
    TextView deliverytext;
    String Delivery = "Delivery";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.delivery, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        deliverytext = (TextView) rootView.findViewById(R.id.deliverytext);
        SpannableString content = new SpannableString(Delivery);
        content.setSpan(new UnderlineSpan(), 0, Delivery.length(), 0);
        deliverytext.setText(content);
    }
}
