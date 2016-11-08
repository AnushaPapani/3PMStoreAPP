package com.three.pmstore.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.three.pmstore.R;
import com.three.pmstore.adapters.LeftMenuAdapter;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.customviews.DialogClass;
import com.three.pmstore.fragments.Blog;
import com.three.pmstore.fragments.ContactUsFragment;
import com.three.pmstore.fragments.Faqview;
import com.three.pmstore.fragments.LoginFragment;
import com.three.pmstore.fragments.MyOrderFragment;
import com.three.pmstore.fragments.Previous_ProductsFragment;
import com.three.pmstore.fragments.RegistrationFragment;
import com.three.pmstore.fragments.StoreCashFragment;
import com.three.pmstore.fragments.TermsAndComditionsFragment;
import com.three.pmstore.fragments.TestimonialsFragment;
import com.three.pmstore.models.LeftMenuModel;
import com.three.pmstore.models.Previous_ItemDetails;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.AppController;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

//import com.store.storeapps.fragments.TestimonialsFragment;


/**
 * Created by Babu Rao.
 */
public class Previous_ProductsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_home_left_drawer_icon;
    private TextView txt_settings_icon;
    AppController globalVariable;
    public DrawerLayout mDrawerLayout;
    public RelativeLayout cart_layout;
    public static Button cart_layout_button_set_text;
    public ImageView cart_icon;
    public Previous_ItemDetails alldates = new Previous_ItemDetails();
    public static HashMap<Integer, ArrayList<Previous_ItemDetails>> mProductItemsList;
    public static ArrayList<Previous_ItemDetails> inProductItemsList;
    private static ArrayList<LeftMenuModel> leftMenuList;
    // public static ArrayList<CartItemModel> mCartItemsList;
    public static JSONArray dates;
    public static JSONObject products;
    public static boolean isLogged = false;
    public static String loggedUserEmail;
    private static ListView list_home_left_drawer;

    TextView textCounter, head, thour, tvHour, tminutes, tvMinute, tvSecond, s, info, descrip;
    private CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds;
    private static final String FORMAT = "%02d:%02d:%02d";
    // start time of start blinking
    private boolean blink; // controls the blinking .. on and off
    int seconds, minutes;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_previous);
        initUI();
        bundle =new Bundle();
        globalVariable = (AppController) getApplicationContext();
    }

    private void initUI() {
     /*DRAWER ICON*/
        txt_home_left_drawer_icon = (TextView) findViewById(R.id.txt_home_left_drawer_icon);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_home_layout);
        cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_layout_button_set_text = (Button) findViewById(R.id.cart_layout_button_set_text);
        cart_icon = (ImageView) findViewById(R.id.cart_icon);

        txt_home_left_drawer_icon.setTypeface(Utility.setTypeFace_fontawesome(this));
        //txt_settings_icon.setTypeface(Utility.setTypeFace_fontawesome(this));
        txt_home_left_drawer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        setLeftMenuData();
        getProductsList();

        cart_layout.setOnClickListener(this);
        cart_layout_button_set_text.setOnClickListener(this);
        cart_icon.setOnClickListener(this);
        head = (TextView) findViewById(R.id.timer_head);
        //		lgender =(LinearLayout)findViewById(R.id.radio);
        head.setTypeface(Typeface.createFromAsset(getAssets(), "LED.ttf"));
        head.setTextSize(20);
        thour = (TextView) findViewById(R.id.txt_time_hour);
        tvHour = (TextView) findViewById(R.id.txt_time_hour_h);
        tvMinute = (TextView) findViewById(R.id.txt_time_minutes);
        tminutes = (TextView) findViewById(R.id.txt_time_minutes_m);
        tvSecond = (TextView) findViewById(R.id.txt_time_sec);
        s = (TextView) findViewById(R.id.txt_time_sec_s);
        setTimer();
    }

    private void setLeftMenuData() {
        leftMenuList = new ArrayList<>();
        for (int i = 0; i < Utility.getSideMenuItemsListName(this).length; i++) {
            LeftMenuModel leftMenuModel = new LeftMenuModel();
            leftMenuModel.setmName(Utility.getSideMenuItemsListName(this)[i]);
            leftMenuModel.setmImage(Utility.getSideMenuItemsListIcons(this)[i]);
            leftMenuList.add(leftMenuModel);
        }

        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.USER_NAME))) {
            loggedUserEmail = Constants.USER_EMAIL_ID;
            isLogged = true;
        } else {
            loggedUserEmail = "";
            isLogged = false;
        }
        final LeftMenuAdapter leftMenuAdapter = new LeftMenuAdapter(this, leftMenuList);
        list_home_left_drawer = (ListView) findViewById(R.id.list_home_left_drawer);
        setHeader(list_home_left_drawer);
        list_home_left_drawer.setAdapter(leftMenuAdapter);
        leftMenuAdapter.notifyDataSetChanged();

        list_home_left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mDrawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawerLayout.closeDrawers();

                        if (isLogged) {
                            navigateSideMenuClickAfterLogin(position);
                            if (list_home_left_drawer.getItemAtPosition(position).equals("2")) {

                            }
                        } else {
                            navigateSideMenuClickBeforeLogin(position);
                        }
                    }
                }, 300);

            }
        });
//        setHeader(list_home_left_drawer);
    }

    private void navigateSideMenuClickBeforeLogin(int position) {
        switch (position) {
            case 1:
//                Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, HomeActivity.this);
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);

                startActivity(i);
                break;
            case 2:
                Utility.navigateDashBoardFragment(new StoreCashFragment(), StoreCashFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 3:

                bundle.putString("previouslogin", "previouslogin");
                Utility.navigateDashBoardFragment(new LoginFragment(), LoginFragment.TAG, bundle, Previous_ProductsActivity.this);
                break;
            case 4:

                bundle.putString("previousregister", "previousregister");
                Utility.navigateDashBoardFragment(new RegistrationFragment(), RegistrationFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 5:
                bundle.putString("previoustestimonials", "previoustestimonials");
                Utility.navigateDashBoardFragment(new TestimonialsFragment(), TestimonialsFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 6:
                Utility.navigateDashBoardFragment(new Blog(), Blog.TAG, null, Previous_ProductsActivity.this);


                break;
            case 7:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                //
                share.putExtra(Intent.EXTRA_SUBJECT, "Welcome to 3PMstore");
                share.putExtra(Intent.EXTRA_TEXT, "Have you checked out the fastest shopping experience yet? Click on the link below and download the https://play.google.com/store/apps/details?id=com.three.pmstore app in just 3 seconds!");
                //
                startActivity(Intent.createChooser(share, "Share !"));
                break;
            case 8:
                Intent prevProds = new Intent(this, Previous_ProductsActivity.class);
                startActivity(prevProds);
                //Utility.navigateDashBoardFragment(new PreviousProductFragment(), PreviousProductFragment.TAG, null, HomeActivity.this);
                break;
            case 9:
                Utility.navigateDashBoardFragment(new TermsAndComditionsFragment(), TermsAndComditionsFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 10:
                Utility.navigateDashBoardFragment(new ContactUsFragment(), ContactUsFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 11:
                Utility.navigateDashBoardFragment(new Faqview(), Faqview.TAG, null, Previous_ProductsActivity.this);
                break;

        }
    }

    private void navigateSideMenuClickAfterLogin(int position) {
        switch (position) {
            case 1:
//                Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, HomeActivity.this);
                Intent i = new Intent(Previous_ProductsActivity.this, HomeActivity.class);
                startActivity(i);
                break;
            case 2:
                Utility.navigateDashBoardFragment(new StoreCashFragment(), StoreCashFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 3:
                Utility.navigateDashBoardFragment(new MyOrderFragment(), MyOrderFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 4:
                Utility.navigateDashBoardFragment(new TestimonialsFragment(), TestimonialsFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 5:
//                Utility.navigateDashBoardFragment(new Blog(), Blog.TAG, null, HomeActivity.this);

                Utility.navigateDashBoardFragment(new Faqview(), Faqview.TAG, null, Previous_ProductsActivity.this);
                break;
            case 6:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, "Welcome to 3PMstore");
                share.putExtra(Intent.EXTRA_TEXT, "Have you checked out the fastest shopping experience yet? Click on the link below and download the https://play.google.com/store/apps/details?id=com.three.pmstore app in just 3 seconds!");
                startActivity(Intent.createChooser(share, "Share !"));
                break;
            case 7:
                Intent prevProds = new Intent(this, Previous_ProductsActivity.class);
                startActivity(prevProds);
                //Utility.navigateDashBoardFragment(new PreviousProductFragment(), PreviousProductFragment.TAG, null, HomeActivity.this);
                break;
            case 8:
                Utility.navigateDashBoardFragment(new TermsAndComditionsFragment(), TermsAndComditionsFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 9:
                Utility.navigateDashBoardFragment(new ContactUsFragment(), ContactUsFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 10:
                Utility.navigateDashBoardFragment(new Faqview(), Faqview.TAG, null, Previous_ProductsActivity.this);
                break;
            case 11:
                signOut();
                break;
        }
    }

    private void setTimer() {
        long minutesLeft = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("kk:mm:ss");
            Calendar calendar = Calendar.getInstance();
            String currenttime = new SimpleDateFormat("kk:mm:ss").format(Calendar.getInstance().getTime());
            System.out.println("CURRENT TIME FOR TIMER" + currenttime);

            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = calendar.getTime();
            String tomorrowAsString = dateFormat.format(tomorrow);

            System.out.println(tomorrowAsString);

            try {
                String dateStart = dateFormat.format(System.currentTimeMillis());
                Date date1 = dateFormat.parse(dateStart);

                Date date2 = dateFormat.parse(tomorrowAsString);
                Date time = dateFormat1.parse("15:00:00");
                Date time1 = dateFormat1.parse(currenttime);
                if (time1.getTime() > time.getTime() || time1.getTime() == time.getTime()) {
                    long different = (date2.getTime() + time.getTime()) - (date1.getTime() + time1.getTime());
                    long seconds = different / 1000;
                    minutesLeft = seconds / 60;
                    //printDifference(date1, date2, time, time1);
                } else {
                    long different = (date2.getTime() + time.getTime()) - (date1.getTime() + time1.getTime());
                    long seconds = different / 1000;
                    minutesLeft = seconds / 60;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (minutesLeft > 1440) {
            minutesLeft = minutesLeft - 1440;
        }

        totalTimeCountInMilliseconds = 60 * minutesLeft * 1000;
        timeBlinkInMilliseconds = 30 * 1000;
        startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

				/*set Timer and font type to Timer Texts*/
                thour.setTypeface(Typeface.createFromAsset(getAssets(), "LED.ttf"));
                tminutes.setTypeface(Typeface.createFromAsset(getAssets(), "LED.ttf"));
                s.setTypeface(Typeface.createFromAsset(getAssets(), "LED.ttf"));
                tvHour.setTypeface(Typeface.createFromAsset(getAssets(), "LED.ttf"));
                tvMinute.setTypeface(Typeface.createFromAsset(getAssets(), "LED.ttf"));
                tvSecond.setTypeface(Typeface.createFromAsset(getAssets(), "LED.ttf"));

                AppController app = (AppController) getApplicationContext();
                long hh = app.getHour();
                long mm = app.getMin();
                long ss = app.getSec();


                thour.setText("" + hh);
                tvHour.setText("H");
                tvMinute.setText("" + mm);
                tminutes.setText("M");
                tvSecond.setText("" + ss);
                s.setText("S");

                try {
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    String currenttime = new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime());
//					System.out.println("CURRENT TIME FOR TIMER" +currenttime);

                    calendar.add(Calendar.DAY_OF_YEAR, 1);

                    Date time = dateFormat1.parse("15:00:00");
                    Date time1 = dateFormat1.parse(currenttime);


                    String t = time.toString();
                    String t1 = time1.toString();
                    if (thour.equals("23") && tvMinute.equals("0") && tvSecond.equals("0")) {
                        Utility.setSharedPrefStringData(getApplicationContext(), Constants.CARTCOUNT, "0");
                        Intent i = new Intent(Previous_ProductsActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                    if (t.equals(t1)) {
                        Utility.setSharedPrefStringData(getApplicationContext(), Constants.CARTCOUNT, "0");
                        Intent i = new Intent(Previous_ProductsActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                    if (t.length() > t1.length()) {
                        Utility.setSharedPrefStringData(getApplicationContext(), Constants.CARTCOUNT, "0");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (hh < 10) {
                    thour.setText("" + "0" + hh);
                } else {
                    thour.setText("" + hh);

                }
                if (mm < 10) {
                    tvMinute.setText("" + "0" + mm);

                } else {
                    tvMinute.setText("" + mm);

                }

                if (ss < 10) {
                    tvSecond.setText("" + "0" + ss);
                } else {
                    tvSecond.setText("" + ss);
                }


                AppController app2 = (AppController) getApplicationContext();
                app.setHour(seconds / 3600);
                app.setMin((seconds / 60) % 60);
                app.setSec(seconds % 60);

                thour.setTextSize(30);
                tvMinute.setTextSize(30);
                tvSecond.setTextSize(30);
                // format the textview to show the easily readable format
            }

            @Override
            public void onFinish() {
            }

        }.start();

    }

    private void setHeader(ListView list_home_left_drawer) {
        LinearLayout layout_list_header = (LinearLayout) getLayoutInflater().inflate(R.layout.
                navigation_drawer_header_layout, null);
        TextView loginNameTextViews = (TextView) layout_list_header.findViewById(R.id.loginNameTextViews);
        TextView emails = (TextView) layout_list_header.findViewById(R.id.emails);
        TextView cashs = (TextView) layout_list_header.findViewById(R.id.cashs);
        TextView div = (TextView) layout_list_header.findViewById(R.id.div);

        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.USER_NAME))) {
            loginNameTextViews.setText(Utility.getSharedPrefStringData(this, Constants.USER_NAME));
            emails.setText(Utility.getSharedPrefStringData(this, Constants.USER_EMAIL_ID));
            //cashs.setText(R.string.rs + Utility.getSharedPrefStringData(this, Constants.PMCASH));

        } else {
            //cashs.setText(""+R.string.rs);
            loginNameTextViews.setText("Welcome");
        }

        list_home_left_drawer.addHeaderView(layout_list_header);
    }

    private void homeScreenNavigation() {
        Utility.navigateDashBoardFragment(new Previous_ProductsFragment(), Previous_ProductsFragment.TAG, null, Previous_ProductsActivity.this);
    }

    private void getProductsList() {
        if (Utility.isNetworkAvailable(this)) {
            new GetProductListAsyncTask().execute();
        } else {
            DialogClass.createDAlertDialog(this, "The Internet connection appears to be offline.");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_layout:
            case R.id.cart_layout_button_set_text:
            case R.id.cart_icon:
                break;
        }
    }


    class GetProductListAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        String date1;

        String pname;

        public GetProductListAsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(Previous_ProductsActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(Previous_ProductsActivity.this, R.string.please_wait));
            mProductItemsList = new HashMap<Integer, ArrayList<Previous_ItemDetails>>();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                Utility.showLog("data", "datadata" + paramsList.toString());
                result = Utility.httpGetRequestToServer(ApiConstants.PREVIOUS_PRODUCTS);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                ArrayList<String> mylist = new ArrayList<String>();
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject != null) {
                        int success = jsonobject.getInt("success");
                        String url = jsonobject.getString("url");
                        System.out.println("url   previous  " + url);

                        if (success == 1) {
                            products = jsonobject.getJSONObject("tbl_pproducts");
                            dates = jsonobject.getJSONArray("dates");
                            for (int i = 0; i < dates.length(); i++) {
                                String first_date = dates.getString(i).toString();
                                JSONArray c = products.getJSONArray(first_date);
                                System.out.println("All Dates" + dates);
                                inProductItemsList = new ArrayList<Previous_ItemDetails>();
                                for (int j = 0; j < c.length(); j++) {
                                    Previous_ItemDetails product_itemDetails_getters_setters = new Previous_ItemDetails();
                                    JSONObject jsonResponse_tag = c.getJSONObject(j);
                                    product_itemDetails_getters_setters.setP_P_Name(jsonResponse_tag.optString("P_Name"));
                                    product_itemDetails_getters_setters.setP_P_Cost(jsonResponse_tag.optString("P_Cost"));
                                    product_itemDetails_getters_setters.setP_ID(jsonResponse_tag.optString("P_ID"));
                                    product_itemDetails_getters_setters.setP_Image(jsonResponse_tag.optString("P_Image"));
                                    inProductItemsList.add(product_itemDetails_getters_setters);
                                }
                                mProductItemsList.put(i, inProductItemsList);
                                System.out.println("All" + pname);
                                System.out.println("date" + date1);
                            }
                            homeScreenNavigation();
                        } else {
                            Intent i = new Intent(Previous_ProductsActivity.this, NoPrevious.class);
                            i.putExtra("URL", url);
                            startActivity(i);
                            finish();
                        }
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
                    .getBackStackEntryAt(
                            getSupportFragmentManager()
                                    .getBackStackEntryCount() - 1);
            String tagName = backEntry.getName();
            if (tagName.equals(Previous_ProductsFragment.TAG)) {
                finishAffinity();
            }
        }
    }

    private void signOut() {
        Utility.setSharedPrefStringData(this, Constants.USER_ID, "");
        Utility.setSharedPrefStringData(this, Constants.USER_EMAIL_ID, "");
        Utility.setSharedPrefStringData(this, Constants.USER_NAME, "");
        Utility.setSharedPrefStringData(this, Constants.USER_CASH, "");
        Utility.setSharedPrefStringData(this, Constants.CARTCOUNT, "0");
        Utility.setSharedPrefStringData(this, Constants.CARTID, "");
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    public static void updateNavigationDrawer(Context context) {
        leftMenuList = new ArrayList<>();
        for (int i = 0; i < Utility.getSideMenuItemsListName(context).length; i++) {
            LeftMenuModel leftMenuModel = new LeftMenuModel();
            leftMenuModel.setmName(Utility.getSideMenuItemsListName(context)[i]);
            leftMenuModel.setmImage(Utility.getSideMenuItemsListIcons(context)[i]);
            leftMenuList.add(leftMenuModel);
        }

        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(context, Constants.USER_NAME))) {
            isLogged = true;
        } else {
            isLogged = false;
        }
        final LeftMenuAdapter leftMenuAdapter = new LeftMenuAdapter(context, leftMenuList);
        list_home_left_drawer.setAdapter(leftMenuAdapter);

    }
}
