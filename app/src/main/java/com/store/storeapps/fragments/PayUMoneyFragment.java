package com.store.storeapps.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.activities.StatusActivity;
import com.store.storeapps.activities.SuccessActivity;
import com.store.storeapps.utility.Utility;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.google.android.gcm.GCMBaseIntentService.TAG;

/**
 * Created by satyanarayana pdv on 10/15/2016.
 */

public class PayUMoneyFragment extends Fragment {
    WebView webviewPayment;
    String spinner_item, Intentcost, otpmob, pmcash, amount, Codcash, coddisable, amountPayable, orderid,
            fname, email, pname, phone, UID, pid,ptype,txnid;
    private HomeActivity mParent;
    private View rootView;
    public static final String TAG ="PayUMoneyFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (HomeActivity) getActivity();
        if (getArguments() != null) {
            orderid = getArguments().getString("orderid");
            spinner_item = getArguments().getString("Quantity");
            Intentcost = getArguments().getString("cost");
            phone = getArguments().getString("otpmob");
            pmcash = getArguments().getString("Pmprice");
            amountPayable = getArguments().getString("Amount");
            Codcash = getArguments().getString("CodCash");
            coddisable = getArguments().getString("coddisable");
            amount = getArguments().getString("Promo");
            fname = getArguments().getString("fname");
            email = getArguments().getString("email");
            pname = getArguments().getString("pname");
            UID = getArguments().getString("UID");
            pid = getArguments().getString("Pid");

            ptype =getArguments().getString("pname");
            txnid = getArguments().getString("orderid");

//            mPosition = getArguments().getInt("position");
//            Utility.showLog("position", "position" + mPosition);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.payu, container, false);
        webviewPayment = (WebView) rootView.findViewById(R.id.webview);
        webviewPayment.getSettings().setJavaScriptEnabled(true);
        webviewPayment.getSettings().setDomStorageEnabled(true);
        webviewPayment.getSettings().setLoadWithOverviewMode(true);
        webviewPayment.getSettings().setUseWideViewPort(true);

        StringBuilder url_s = new StringBuilder();
        url_s.append("https://secure.payu.in/_payment");
//        Log.e(TAG, "call url " + url_s);
        webviewPayment.postUrl(url_s.toString(), EncodingUtils.getBytes(getPostString(), "utf-8"));

        webviewPayment.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.indexOf("/payusuccess.php") != -1) {

                    String status = "PayU Transaction Successful!";
                    String P_Type = "PayU";
                    Intent intent = new Intent(getActivity(), StatusActivity.class);
                    intent.putExtra("P_Type", P_Type);
                    intent.putExtra("Orderid",orderid);
                    intent.putExtra("name",fname);
                    intent.putExtra("EmailID",email);
                    intent.putExtra("transStatus", status);
                    startActivity(intent);
                }
            }

            @SuppressWarnings("unused")
            public void onReceivedSslError(WebView view) {
                Log.e("Error", "Exception caught!");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        initUI();
        return rootView;
    }

    private String getPostString() {
        String key = "rX3zfA";
        String salt = "f6kkMCOH";

        StringBuilder post = new StringBuilder();
        post.append("Ordders=");
        post.append(pname);
        post.append("&");
        post.append("P_Type=");
        post.append(ptype);
        post.append("&");
        post.append("UID=");
        post.append(pname);
        post.append("&");
        post.append("name=");
        post.append(fname);
        post.append("&");
        post.append("key=");
        post.append(key);
        post.append("&");
        post.append("txnid=");
        post.append(orderid);
        post.append("&");
        post.append("amount=");
        post.append(amount);
        post.append("&");
        post.append("productinfo=");
        post.append(pname);
        post.append("&");
        post.append("firstname=");
        post.append(fname);
        post.append("&");
        post.append("email=");
        post.append(email);
        post.append("&");
        post.append("phone=");
        post.append(phone);
        post.append("&");
        post.append("surl=");
        post.append("http://www.3pmstore.com/android/android_connect/payusuccess.php");
        post.append("&");
        post.append("furl=");
        post.append("https://www.3pmstore.com/transactionfailure.php");
        post.append("&");

        StringBuilder checkSumStr = new StringBuilder();
        MessageDigest digest = null;
        String hash;
        try {
            digest = MessageDigest.getInstance("SHA-512");// MessageDigest.getInstance("SHA-256");

            checkSumStr.append(key);
            checkSumStr.append("|");
            checkSumStr.append(txnid);
            checkSumStr.append("|");
            checkSumStr.append(amount);
            checkSumStr.append("|");
            checkSumStr.append(pname);
            checkSumStr.append("|");
            checkSumStr.append(fname);
            checkSumStr.append("|");
            checkSumStr.append(email);
            checkSumStr.append("|||||||||||");
            checkSumStr.append(salt);

            digest.update(checkSumStr.toString().getBytes());

            hash = bytesToHexString(digest.digest());
            post.append("hash=");
            post.append(hash);
            post.append("&");
            Log.i(TAG, "SHA result is " + hash);
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        post.append("service_provider=");
        post.append("payu_paisa");
        return post.toString();
    }

    private JSONObject getProductInfo() {
        try {
            //create payment part object
            JSONObject productInfo = new JSONObject();

            JSONObject jsonPaymentPart = new JSONObject();
            jsonPaymentPart.put("name", "TapFood");
            jsonPaymentPart.put("description", "Lunchcombo");
            jsonPaymentPart.put("value", "500");
            jsonPaymentPart.put("isRequired", "true");
            jsonPaymentPart.put("settlementEvent", "EmailConfirmation");

            //create payment part array
            JSONArray jsonPaymentPartsArr = new JSONArray();
            jsonPaymentPartsArr.put(jsonPaymentPart);

            //paymentIdentifiers
            JSONObject jsonPaymentIdent = new JSONObject();
            jsonPaymentIdent.put("field", "CompletionDate");
            jsonPaymentIdent.put("value", "31/10/2012");

            //create payment part array
            JSONArray jsonPaymentIdentArr = new JSONArray();
            jsonPaymentIdentArr.put(jsonPaymentIdent);

            productInfo.put("paymentParts", jsonPaymentPartsArr);
            productInfo.put("paymentIdentifiers", jsonPaymentIdentArr);

            Log.e(TAG, "product Info = " + productInfo.toString());
            return productInfo;


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
