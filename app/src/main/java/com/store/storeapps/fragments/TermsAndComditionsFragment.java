package com.store.storeapps.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.utility.Utility;

/**
Created by Anusha
 */
public class TermsAndComditionsFragment extends Fragment {
    Button about;
    Button privacy;
    Button delivery;
    Button buynowproductbutton;
    Button returnsandc;
    TextView termsandcontions;
    public static final String TAG = "TermsAndComditionsFragment";
    private View rootView;
    String mystring= "Terms & Conditions";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.termsandconditions, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        termsandcontions = (TextView) rootView.findViewById(R.id.termsandconditions);
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        termsandcontions.setText(content);

        about = (Button)rootView.findViewById(R.id.aboutusbutton);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.navigateDashBoardFragment(new AboutusFragment(), AboutusFragment.TAG, null, getActivity());
            }
        });
        privacy = (Button)rootView.findViewById(R.id.privacypolicybutton);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.navigateDashBoardFragment(new PrivacyPolicyFragment(), PrivacyPolicyFragment.TAG, null, getActivity());
            }
        });
        delivery = (Button)rootView.findViewById(R.id.deliverybutton);
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.navigateDashBoardFragment(new DeliveryFragment(), DeliveryFragment.TAG, null, getActivity());
            }
        });
        returnsandc = (Button)rootView.findViewById(R.id.returnsandcancellationbutton);
        returnsandc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.navigateDashBoardFragment(new ReturnsAndCancellationsFragment(), ReturnsAndCancellationsFragment.TAG, null, getActivity());
            }
        });
    }

}
