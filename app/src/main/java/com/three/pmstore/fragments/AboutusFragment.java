package com.three.pmstore.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.three.pmstore.R;


/*
Created by Anusha
 */
public class AboutusFragment extends Fragment {
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
        FacebookSdk.sdkInitialize(getActivity());
        AppEventsLogger.activateApp(getActivity());
        //		setContentView(R.layout.secs);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        AppEventsLogger logger = AppEventsLogger.newLogger(getActivity());
        logger.logEvent("pageview");
        TextView aboutustext =(TextView) rootView.findViewById(R.id.aboutustext);
        SpannableString content = new SpannableString(Aboutus);
        content.setSpan(new UnderlineSpan(), 0, Aboutus.length(), 0);
        aboutustext.setText(content);
    }
}
