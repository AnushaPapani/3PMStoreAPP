package com.store.storeapps.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.store.storeapps.R;
import com.store.storeapps.adapters.LeftMenuAdapter;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.fragments.AddAddressFragment;
import com.store.storeapps.fragments.Blog;
import com.store.storeapps.fragments.ContactUsFragment;
import com.store.storeapps.fragments.HomeFragment;
import com.store.storeapps.fragments.LoginFragment;
import com.store.storeapps.fragments.MyAddressFragment;
import com.store.storeapps.fragments.MyOrderFragment;
import com.store.storeapps.fragments.PreviousProductFragment;
import com.store.storeapps.fragments.RegistrationFragment;
import com.store.storeapps.fragments.ReviewOrderFragment;
import com.store.storeapps.fragments.ReviewOrderFragment_Before_Login;
import com.store.storeapps.fragments.StoreCashFragment;
import com.store.storeapps.fragments.TermsAndComditionsFragment;
//import com.store.storeapps.fragments.TestimonialsFragment;
import com.store.storeapps.models.CartItemModel;
import com.store.storeapps.models.ItemDetails;
import com.store.storeapps.models.LeftMenuModel;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.AppController;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import static com.store.storeapps.R.layout.fragment_home;


/**
 * Created by Shankar.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_home_left_drawer_icon;
    public DrawerLayout mDrawerLayout;
    public RelativeLayout cart_layout;
    public static Button cart_layout_button_set_text;
    public static TextView txt_user_name;
    public static TextView txt_email;
    public ImageView cart_icon;
    public static ArrayList<ItemDetails> mProductItemsList;
    private static ArrayList<LeftMenuModel> leftMenuList;
    public static ArrayList<CartItemModel> mCartItemsList;
    public static String mCartId = "";
    public static int mCartValue = 0;
    public static int mCartTotal = 0;
    public static boolean isLogged = false;
    /*Timer*/
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
    LayoutInflater inflater;
    private static ListView list_home_left_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_home);

        initUI();
    }

    private void initUI() {
        mCartItemsList = new ArrayList<>();
     /*DRAWER ICON*/
        txt_home_left_drawer_icon = (TextView) findViewById(R.id.txt_home_left_drawer_icon);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_home_layout);
        cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_layout_button_set_text = (Button) findViewById(R.id.cart_layout_button_set_text);
        cart_icon = (ImageView) findViewById(R.id.cart_icon);

        txt_home_left_drawer_icon.setTypeface(Utility.setTypeFace_fontawesome(this));
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
                    if (t.equals(t1)) {
//                        Intent i = new Intent(AboutusActivity.this,ProductsPage.class);
//                        startActivity(i);
//                        finish();
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

    private void setLeftMenuData() {
        leftMenuList = new ArrayList<>();
        for (int i = 0; i < Utility.getSideMenuItemsListName(this).length; i++) {
            LeftMenuModel leftMenuModel = new LeftMenuModel();
            leftMenuModel.setmName(Utility.getSideMenuItemsListName(this)[i]);
            leftMenuModel.setmImage(Utility.getSideMenuItemsListIcons(this)[i]);
            leftMenuList.add(leftMenuModel);
        }

        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.USER_NAME))) {
            isLogged = true;
        } else {
            isLogged = false;
        }
        final LeftMenuAdapter leftMenuAdapter = new LeftMenuAdapter(this, leftMenuList);
        list_home_left_drawer = (ListView) findViewById(R.id.list_home_left_drawer);
        list_home_left_drawer.setAdapter(leftMenuAdapter);


        list_home_left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mDrawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawerLayout.closeDrawers();
                        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getApplicationContext(), Constants.USER_NAME))) {
                            navigateSideMenuClickAfterLogin(position);
                            if (list_home_left_drawer.getItemAtPosition(position).equals("2")){

                            }
                        }
                        else {
                            navigateSideMenuClickBeforeLogin(position);
                        }

                    }
                }, 300);

            }
        });

        setHeader(list_home_left_drawer);
    }

    private void navigateSideMenuClickBeforeLogin(int position) {
        switch (position) {
            case 1:
                Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, HomeActivity.this);
                break;
            case 2:
                Utility.navigateDashBoardFragment(new StoreCashFragment(), StoreCashFragment.TAG, null, HomeActivity.this);
                break;
            case 3:
                Utility.navigateDashBoardFragment(new LoginFragment(), LoginFragment.TAG, null, HomeActivity.this);
                break;
            case 4:
                Utility.navigateDashBoardFragment(new RegistrationFragment(), RegistrationFragment.TAG, null, HomeActivity.this);
                break;
            case 5:
                //Utility.navigateDashBoardFragment(new TestimonialsFragment(), TestimonialsFragment.TAG, null, HomeActivity.this);
                break;
            case 6:
                Utility.navigateDashBoardFragment(new Blog(), Blog.TAG, null, HomeActivity.this);
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
                Utility.navigateDashBoardFragment(new TermsAndComditionsFragment(), TermsAndComditionsFragment.TAG, null, HomeActivity.this);
                break;
            case 10:
                Utility.navigateDashBoardFragment(new ContactUsFragment(), ContactUsFragment.TAG, null, HomeActivity.this);
                break;

        }
    }

    private void navigateSideMenuClickAfterLogin(int position) {
        switch (position) {
            case 1:
                Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, HomeActivity.this);
                break;
            case 2:
                Utility.navigateDashBoardFragment(new StoreCashFragment(), StoreCashFragment.TAG, null, HomeActivity.this);
                break;
            case 3:
                Utility.navigateDashBoardFragment(new MyOrderFragment(), MyOrderFragment.TAG, null, HomeActivity.this);
                break;
            case 4:
               // Utility.navigateDashBoardFragment(new TestimonialsFragment(), TestimonialsFragment.TAG, null, HomeActivity.this);
                break;
            case 5:
                Utility.navigateDashBoardFragment(new Blog(), Blog.TAG, null, HomeActivity.this);
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
                Utility.navigateDashBoardFragment(new TermsAndComditionsFragment(), TermsAndComditionsFragment.TAG, null, HomeActivity.this);
                break;
            case 9:
                Utility.navigateDashBoardFragment(new ContactUsFragment(), ContactUsFragment.TAG, null, HomeActivity.this);
                break;
            case 10:
                signOut();
                break;
        }
    }


    private void setHeader(ListView list_home_left_drawer) {
        LinearLayout layout_list_header = (LinearLayout) getLayoutInflater().inflate(R.layout.
                navigation_drawer_header_layout, null);
        txt_user_name = (TextView) layout_list_header.findViewById(R.id.loginNameTextViews);
        txt_email = (TextView) layout_list_header.findViewById(R.id.emails);
        TextView cashs = (TextView) layout_list_header.findViewById(R.id.cashs);
        TextView div = (TextView) layout_list_header.findViewById(R.id.div);

        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.USER_NAME))) {
            txt_user_name.setText(Utility.getSharedPrefStringData(this, Constants.USER_NAME));
            txt_email.setText(Utility.getSharedPrefStringData(this, Constants.USER_EMAIL_ID));
            cashs.setText(R.string.rs + Utility.getSharedPrefStringData(this, Constants.PMCASH));

        } else {
            cashs.setText(""+R.string.rs);
            txt_user_name.setText("Welcome");
        }

        list_home_left_drawer.addHeaderView(layout_list_header);
    }

    private void homeScreenNavigation() {
        Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, HomeActivity.this);
    }

    public void getProductsList() {
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
                if(mCartId == "")
                {
                    Utility.showToastMessage(this, "Add at least one item to cart");
                }
                else if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.USER_ID))) {
                    Utility.navigateDashBoardFragment(new ReviewOrderFragment_Before_Login(), ReviewOrderFragment_Before_Login.TAG, null, HomeActivity.this);
                } else if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.USER_ID))) {
                    Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, HomeActivity.this);
                } else {
                    Utility.showToastMessage(this, "Add at least one item to cart");
                }

                break;

        }
    }

    class GetProductListAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetProductListAsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(HomeActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(HomeActivity.this, R.string.please_wait));
            mProductItemsList = new ArrayList<ItemDetails>();

        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                Utility.showLog("data", "datadata" + paramsList.toString());
                result = Utility.httpGetRequestToServer(ApiConstants.GET_ALL_PRODUCTS);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject != null) {
                        JSONArray products = jsonobject.optJSONArray("tbl_products");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject jsonResponse_tag = products.optJSONObject(i);
                            ItemDetails product_itemDetails_getters_setters = new ItemDetails();

                            product_itemDetails_getters_setters.setEnabled(jsonResponse_tag.optBoolean("IsEnabled"));
                            product_itemDetails_getters_setters.setCategory(jsonResponse_tag.optString("Category"));
                            product_itemDetails_getters_setters.setCategory_Icon(jsonResponse_tag.optString("Category_Icon"));
                            product_itemDetails_getters_setters.setCategory_Icon_grey(jsonResponse_tag.optString("Category_Icon_grey"));
                            product_itemDetails_getters_setters.setP_Cost(jsonResponse_tag.optInt("P_Cost"));
                            product_itemDetails_getters_setters.setP_Date(jsonResponse_tag.optString("P_Date"));
                            product_itemDetails_getters_setters.setP_Description(jsonResponse_tag.optString("P_Description"));
                            product_itemDetails_getters_setters.setP_hfeatures(jsonResponse_tag.optString("P_hfeatures"));
                            product_itemDetails_getters_setters.setP_id(jsonResponse_tag.optString("P_ID"));
                            product_itemDetails_getters_setters.setP_Information(jsonResponse_tag.optString("P_Information"));
                            product_itemDetails_getters_setters.setP_Name(jsonResponse_tag.optString("P_Name"));
                            product_itemDetails_getters_setters.setP_Qty(jsonResponse_tag.optInt("P_Qty"));
                            product_itemDetails_getters_setters.setP_Video(jsonResponse_tag.optString("P_Video"));
                            product_itemDetails_getters_setters.setP_shortdesc(jsonResponse_tag.optString("P_shortdesc"));
                            product_itemDetails_getters_setters.setStock(jsonResponse_tag.optString("Stock"));
                            product_itemDetails_getters_setters.setStrikeMrp(jsonResponse_tag.optString("StrikeMrp"));


                            JSONArray images = jsonResponse_tag.optJSONArray("Images");
                            ArrayList<String> imagesArray = new ArrayList<>();
                            for (int j = 0; j < images.length(); j++) {
                                imagesArray.add(images.optString(j));
                            }
                            product_itemDetails_getters_setters.setImages(imagesArray);

                            JSONArray attrTypes = jsonResponse_tag.optJSONArray("attrTypes");
                            ArrayList<String> attrTypesArray = new ArrayList<>();
                            if (attrTypes != null) {
                                for (int k = 0; k < attrTypes.length(); k++) {
                                    attrTypesArray.add(attrTypes.optString(k));
                                }
                            }
                            product_itemDetails_getters_setters.setAttrTypes(attrTypesArray);


                            JSONArray attrNames = jsonResponse_tag.optJSONArray("attrNames");
                            ArrayList<String> attrNamesArray = new ArrayList<>();
                            if (attrNames != null) {
                                for (int j = 0; j < attrNames.length(); j++) {
                                    attrNamesArray.add(attrNames.optString(j));
                                }
                            }
                            product_itemDetails_getters_setters.setAttrNames(attrNamesArray);

                            JSONArray attrValues = jsonResponse_tag.optJSONArray("attrValues");
                            ArrayList<String> attrValuesArray = new ArrayList<>();
                            if (attrValues != null) {
                                for (int j = 0; j < attrValues.length(); j++) {
                                    attrValuesArray.add(attrValues.optString(j));
                                }
                            }
                            product_itemDetails_getters_setters.setAttrValues(attrValuesArray);

                            mProductItemsList.add(product_itemDetails_getters_setters);
                        }

                        homeScreenNavigation();
                    }
                    mCustomProgressDialog.dismissProgress();
                }
//                mCustomProgressDialog.dismissProgress();
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
            if (tagName.equals(HomeFragment.TAG)) {
                finishAffinity();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void signOut() {
        Utility.setSharedPrefStringData(this, Constants.USER_ID, "");
        Utility.setSharedPrefStringData(this, Constants.USER_EMAIL_ID, "");
        Utility.setSharedPrefStringData(this, Constants.USER_NAME, "");
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    public static void updateNavigationDrawer(Context context){
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

//    @Override
//    public void onRefresh() {
//        mParent.getProductsList();
//        if (mCustomProgressDialog.isRefreshing()) {
//            mCustomProgressDialog.dismissProgress();
//        }
//    }
}
