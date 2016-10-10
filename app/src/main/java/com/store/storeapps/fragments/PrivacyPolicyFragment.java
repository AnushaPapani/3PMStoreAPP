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
public class PrivacyPolicyFragment extends Fragment {

    public static final String TAG = "PrivacyPolicyFragment";
    private View rootView;
    String Privacy = "Privacy Policy";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.privacypolicy, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        TextView privacy = (TextView) rootView.findViewById(R.id.privacypolicytext);
        SpannableString content = new SpannableString(Privacy);
        content.setSpan(new UnderlineSpan(), 0, Privacy.length(), 0);
        privacy.setText(content);
    }
}
