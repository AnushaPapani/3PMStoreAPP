package com.store.storeapps.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which has Utility methods
 */
public class Utility {
    private static Pattern pattern;
    private static Matcher matcher;
    //Email Pattern
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Validate Email with regular expression
     *
     * @param email
     * @return true for Valid Email and false for Invalid Email
     */
    public static boolean validate(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Typeface setTypeFace_fontawesome(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
    }

    public static void navigateDashBoardFragment(Fragment fragment,
                                                 String tag, Bundle bundle, FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity
                .getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.content_frame, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState() == NetworkInfo.State.CONNECTED
                    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState() == NetworkInfo.State.CONNECTING) {
                return true;
            } else return connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState() == NetworkInfo.State.CONNECTED
                    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState() == NetworkInfo.State.CONNECTING;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getResourcesString(Context context, int id) {
        String value = null;
        if (context != null && id != -1) {
            value = context.getResources().getString(id);
        }
        return value;
    }

    public static boolean isValueNullOrEmpty(String value) {
        boolean isValue = false;
        if (value == null || value.equals(null) || value.equals("")
                || value.equals("null") || value.trim().length() == 0) {
            isValue = true;
        }
        return isValue;
    }

    public static void showLog(String logMsg, String logVal) {
        try {
            if (true) {
                if (!isValueNullOrEmpty(logMsg) && !isValueNullOrEmpty(logVal)) {
                    Log.e(logMsg, logVal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String httpGetRequestToServer(String url) {
        InputStream inputStream = null;
        String result = null;
        try {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, Constants.CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, Constants.CONNECTION_TIMEOUT);

            HttpClient httpclient = new DefaultHttpClient(httpParams);
            HttpGet request = new HttpGet(url);
            showLog("HttpGet URL : ", url);
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                result = null;
            }

            if (statusCode == 200) {
                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();
                // convert inputstream to string
                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                    showLog("HttpGet Response : ", result);
                } else {
                    result = "Did not work!";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static String httpPostRequestToServer(String url, Object paramsList) {
        String result = null;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, Constants.CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, Constants.CONNECTION_TIMEOUT);

        HttpClient httpclient = new DefaultHttpClient(httpParams);
        HttpPost httppost = new HttpPost(url);
        showLog("HttpPost URL : ", url);
        showLog("paramsList URL : ", paramsList.toString());
        InputStream is = null;
        try {
            if (paramsList != null) {
                httppost.setEntity(new UrlEncodedFormEntity((List<? extends NameValuePair>) paramsList));
            }

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity httpEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            showLog("statusCode", "statusCode" + statusCode);
            if (statusCode == 204) {
                result = null;
            }

            if (statusCode == 200) {
                is = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, Constants.CHARACTER_SET_ISO_8859_1), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 0)
                        sb.append(line + "\n");
                }
                result = sb.toString();
                is.close();
                showLog("HttpPost Response : ", result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    /**
     * ASSIGN THE DRAWBLE
     **/
    public static Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            return ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    /*SIDE MENU_ITEMS NAMES*/
    public static String[] getSideMenuItemsListName(Context context) {
        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(context, Constants.USER_NAME))) {
            return new String[]{
                    "Home",
                    "3PMstore Cash",
                    "My Orders",
                    "Testimonials",
                    "Blog",
                    "Invite Friends",
                    "Previous Products",
                    "Terms & Conditions",
                    "Contact Us",
                    "FAQs",
                    "Logout"};
        } else {
            return new String[]{
                    "Home",
                    "3PMstore Cash",
                    "Login",
                    "Register",
                    "Testimonials",
                    "Blog",
                    "Invite Friends",
                    "Previous Products",
                    "Terms & Conditions",
                    "Contact Us",
                    "FAQs"};
        }
    }

    /*SIDE MENU_ITEMS ICONS*/
    public static int[] getSideMenuItemsListIcons(Context context) {
        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(context, Constants.USER_NAME))) {
            return new int[]{
                    R.drawable.home,
                    R.drawable.wallet,
                    R.drawable.trackorder,
                    R.drawable.testimonials,
                    R.drawable.blog,
                    R.drawable.share,
                    R.drawable.arrowback,
                    R.drawable.purchase_order,
                    R.drawable.telephone,
                    R.drawable.faqicon,
                    R.drawable.logout_icon
            };
        }
        else {
            return new int[]{
                    R.drawable.home,
                    R.drawable.wallet,
                    R.drawable.logout_icon,
                    R.drawable.register,
                    R.drawable.testimonials,
                    R.drawable.blog,
                    R.drawable.share,
                    R.drawable.arrowback,
                    R.drawable.purchase_order,
                    R.drawable.telephone,
                    R.drawable.faqicon
            };
        }
    }

    public static void setSharedPrefBooleanData(Context context, String key, boolean value) {
        SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
        appInstallInfoEditor.putBoolean(key, value);
        appInstallInfoEditor.commit();
    }
    public static void setSharedpreferences(Context context, String key, boolean value){
        SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor appInfoEditor = appInstallInfoSharedPref.edit();
        appInfoEditor.putBoolean(key, value);
        appInfoEditor.commit();

    }

    public static String getSharedPrefStringData(Context context, String key) {

        try {
            SharedPreferences userAcountPreference = context
                    .getSharedPreferences(Constants.APP_PREF,
                            Context.MODE_PRIVATE);
            return userAcountPreference.getString(key, "");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "";

    }

    public static void setSharedPrefStringData(Context context, String key, String value) {
        try {
            if (context != null) {
                SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
                appInstallInfoEditor.putString(key, value);
                appInstallInfoEditor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteSharedPrefStringData(Context context, String key, String value) {
        try {
            if (context != null) {
                SharedPreferences appInstallInfoSharedPref = context.getSharedPreferences(Constants.APP_PREF,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor appInstallInfoEditor = appInstallInfoSharedPref.edit();
                appInstallInfoEditor.putString(key, value);
                appInstallInfoEditor.clear();
                appInstallInfoEditor.commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showToastMessage(Context context, String message) {
        try {
            if (!isValueNullOrEmpty(message) && context != null) {
                final Toast toast = Toast.makeText(
                        context.getApplicationContext(), message,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<NameValuePair> getParams(LinkedHashMap<String, String> paramMap) {
        if (paramMap == null) {
            return null;
        }

        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return paramsList;
    }

    public static void showOKOnlyDialog(Context context, String msg,
                                        String title) {
        SpannableString s = new SpannableString(msg);
        Linkify.addLinks(s, Linkify.ALL);

        AlertDialog d = new AlertDialog.Builder(context)
                .setMessage(s)
                .setTitle(title)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        }).show();

        ((TextView) d.findViewById(android.R.id.message))
                .setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static String capitalizeFirstLetter(String s) {
        if (s.length() == 0) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

}
