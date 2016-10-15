package com.store.storeapps.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.store.storeapps.R;

/**
 * Created by home on 10/10/2016.
 */

public class StoreCashFragment extends Fragment {
    public static final String TAG = "AboutusFragment";
    private View rootView;
    String Aboutus="3PMstore Cash";
    TextView storeHead;
    TextView tv;
    String mystring = "3PMstore Cash";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.storecash, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {

        storeHead = (TextView)rootView.findViewById(R.id.pmstorecash);

        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        storeHead.setText(content);
        tv = (TextView)rootView.findViewById(R.id.fbwall);
        tv.setText(Html.fromHtml(getString(R.string.my_text)));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean installed  =   appInstalledOrNot("com.facebook.katana");
                if(installed)
                {
                    System.out.println("App already installed on your phone");
                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/675633212542434"));
                    startActivity(i);
                } else {
                    System.out.println("App is not installed on your phone");
                }
            }
        });

    }
    private boolean appInstalledOrNot(String uri)
    {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed = false;
        try
        {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed ;

    }

}
