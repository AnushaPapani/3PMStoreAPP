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

/*
Created by Anusha
 */
public class TestimonialsFragment extends Fragment {
    public static final String TAG = "AboutusFragment";
    private View rootView;
    String Aboutus="About Us";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.aboutus, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        TextView aboutustext =(TextView) rootView.findViewById(R.id.aboutustext);
        SpannableString content = new SpannableString(Aboutus);
        content.setSpan(new UnderlineSpan(), 0, Aboutus.length(), 0);
        aboutustext.setText(content);
    }
}
