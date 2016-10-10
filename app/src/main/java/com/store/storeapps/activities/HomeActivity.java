package com.store.storeapps.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.store.storeapps.R;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.fragments.AboutusFragment;
import com.store.storeapps.fragments.HomeFragment;
import com.store.storeapps.fragments.LoginFragment;
import com.store.storeapps.fragments.ReviewOrderFragment;
import com.store.storeapps.fragments.StoreCashFragment;
import com.store.storeapps.models.CartItemModel;
import com.store.storeapps.models.ItemDetails;
import com.store.storeapps.models.LeftMenuModel;
import com.store.storeapps.utility.AppController;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;
import com.store.storeapps.adapters.LeftMenuAdapter;
import com.store.storeapps.utility.ApiConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.store.storeapps.fragments.LoginFragment.EMAILID;
import static com.store.storeapps.fragments.LoginFragment.IsUid;
import static com.store.storeapps.fragments.LoginFragment.UID;
import static com.store.storeapps.fragments.LoginFragment.USERNAME;


/**
 * Created by Shankar.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_home_left_drawer_icon;
    AppController globalVariable;
    public DrawerLayout mDrawerLayout;
    public RelativeLayout cart_layout;
    public static Button cart_layout_button_set_text;
    public ImageView cart_icon;
    boolean IsLogged=false;
    public static ArrayList<ItemDetails> mProductItemsList;
    private ArrayList<LeftMenuModel> leftMenuList;
    public static ArrayList<CartItemModel> mCartItemsList;
    public static String mCartId = "";
    public static int mCartValue = 0;
    boolean showMenu =true;
    PopupMenu popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_home);
        initUI();

        globalVariable = (AppController) getApplicationContext();
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
        for (int i = 0; i < Utility.getSideMenuItemsListName().length; i++) {
            LeftMenuModel leftMenuModel = new LeftMenuModel();
            leftMenuModel.setmName(Utility.getSideMenuItemsListName()[i]);
            leftMenuModel.setmImage(Utility.getSideMenuItemsListIcons()[i]);
            leftMenuList.add(leftMenuModel);
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
                        navigateSideMenuClick(position);
                    }
                }, 300);

            }
        });

        setHeader(list_home_left_drawer);
    }

    private void navigateSideMenuClick(int position) {
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

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
        }
    }

    private void setHeader(ListView list_home_left_drawer) {
        LinearLayout layout_list_header = (LinearLayout) getLayoutInflater().inflate(R.layout.
        navigation_drawer_header_layout, null);
        TextView loginNameTextViews = (TextView) layout_list_header.findViewById(R.id.loginNameTextViews);
        TextView emails = (TextView) layout_list_header.findViewById(R.id.emails);
        TextView cashs = (TextView) layout_list_header.findViewById(R.id.cashs);
        TextView div = (TextView) layout_list_header.findViewById(R.id.div);
        emails.setText(Utility.getSharedPrefStringData(getApplicationContext(),EMAILID));
        loginNameTextViews.setText(Utility.getSharedPrefStringData(getApplicationContext(),USERNAME));
        System.out.println("username "+loginNameTextViews);
        System.out.println("uid "+Utility.getSharedPrefStringData(getApplicationContext(),UID));
        System.out.println("email "+emails);
        /*if (globalVariable.getUserid().toString() == null) {
           loginNameTextViews.setText("Welcome Guest");
        } else {
            loginNameTextViews.setText(globalVariable.getName().toString());
            emails.setText("" + globalVariable.getUserid().toString());
        }*/
        list_home_left_drawer.addHeaderView(layout_list_header);
    }

    private void homeScreenNavigation() {
        Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, HomeActivity.this);
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
                if (!Utility.isValueNullOrEmpty(mCartId) && (globalVariable.getUserid() != null)) {
                    Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, HomeActivity.this);
                }else if (!Utility.isValueNullOrEmpty(mCartId) && (globalVariable.getUserid() == null)){
                    Intent i=new Intent(HomeActivity.this,LoginFragment.class);
                    startActivity(i);
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
            if (tagName.equals(HomeFragment.TAG)) {
                finishAffinity();
            } else if (tagName.equals(ReviewOrderFragment.TAG)) {
                initUI();
            }
        }
    }
}
