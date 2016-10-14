package com.store.storeapps.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
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
import com.store.storeapps.fragments.RegistrationFragment;
import com.store.storeapps.fragments.ReviewOrderFragment;
import com.store.storeapps.fragments.ReviewOrderFragment_Before_Login;
import com.store.storeapps.fragments.StoreCashFragment;
import com.store.storeapps.fragments.TestimonialsFragment;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;


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
    private ArrayList<LeftMenuModel> leftMenuList;
    public static ArrayList<CartItemModel> mCartItemsList;
    public static String mCartId = "";
    public static int mCartValue = 0;
    public boolean isLogged = false;

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
        ListView list_home_left_drawer = (ListView) findViewById(R.id.list_home_left_drawer);
        list_home_left_drawer.setAdapter(leftMenuAdapter);


        list_home_left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mDrawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDrawerLayout.closeDrawers();
                        if (isLogged) {
                            navigateSideMenuClickAfterLogin(position);
                        } else {
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
                Utility.navigateDashBoardFragment(new AddAddressFragment(), AddAddressFragment.TAG, null, HomeActivity.this);
                break;
            case 6:

                break;
            case 7:
                Utility.navigateDashBoardFragment(new TestimonialsFragment(), TestimonialsFragment.TAG, null, HomeActivity.this);
                break;
            case 8:
                Utility.navigateDashBoardFragment(new Blog(), Blog.TAG, null, HomeActivity.this);
                break;
            case 9:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                //
                share.putExtra(Intent.EXTRA_SUBJECT, "Welcome to 3PMstore");
                share.putExtra(Intent.EXTRA_TEXT, "Have you checked out the fastest shopping experience yet? Click on the link below and download the https://play.google.com/store/apps/details?id=com.three.pmstore app in just 3 seconds!");
                //
                startActivity(Intent.createChooser(share, "Share !"));
//                Utility.setSharedPrefStringData(this, Constants.USER_NAME, "null");
//                Utility.setSharedPrefStringData(this, Constants.USER_ID, "null");
//                Utility.setSharedPrefStringData(this, Constants.USER_EMAIL_ID, "null");
                break;
            case 10:

                Utility.navigateDashBoardFragment(new ContactUsFragment(), Blog.TAG, null, HomeActivity.this);
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
                Bundle bundle = new Bundle();
                bundle.putString("from", "HomeActivity");
                Utility.navigateDashBoardFragment(new LoginFragment(), LoginFragment.TAG, null, HomeActivity.this);
                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:
                signOut();
//                Utility.navigateDashBoardFragment(new MyAddressFragment(), MyAddressFragment.TAG, null, HomeActivity.this);


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
        } else {
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
                if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this,Constants.USER_ID))) {
                    Utility.navigateDashBoardFragment(new ReviewOrderFragment_Before_Login(), ReviewOrderFragment_Before_Login.TAG, null, HomeActivity.this);
//                    if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.USER_EMAIL_ID))) {
//                        Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, HomeActivity.this);
//                    } else {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("from", "cart");
//                        Utility.navigateDashBoardFragment(new LoginFragment(), LoginFragment.TAG, bundle, HomeActivity.this);
//                    }
                }
                else if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this,Constants.USER_ID))){
                    Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, HomeActivity.this);
                }


                else {
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
                            product_itemDetails_getters_setters.setProduct_Type(jsonResponse_tag.optString("Product_Type"));
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

        Intent i= new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);


    }

//    @Override
//    public void onRefresh() {
//        mParent.getProductsList();
//        if (mCustomProgressDialog.isRefreshing()) {
//            mCustomProgressDialog.dismissProgress();
//        }
//    }
}
