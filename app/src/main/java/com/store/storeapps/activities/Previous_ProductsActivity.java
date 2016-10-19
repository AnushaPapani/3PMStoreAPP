package com.store.storeapps.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.store.storeapps.R;
import com.store.storeapps.adapters.LeftMenuAdapter;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.fragments.AddAddressFragment;
import com.store.storeapps.fragments.Blog;
import com.store.storeapps.fragments.ContactUsFragment;
import com.store.storeapps.fragments.Faqview;
import com.store.storeapps.fragments.HomeFragment;
import com.store.storeapps.fragments.LoginFragment;
import com.store.storeapps.fragments.MyOrderFragment;
import com.store.storeapps.fragments.PreviousProductFragment;
import com.store.storeapps.fragments.Previous_ProductsFragment;
import com.store.storeapps.fragments.RegistrationFragment;
import com.store.storeapps.fragments.StoreCashFragment;
import com.store.storeapps.fragments.TermsAndComditionsFragment;
//import com.store.storeapps.fragments.TestimonialsFragment;
import com.store.storeapps.fragments.TestimonialsFragment;
import com.store.storeapps.models.CartItemModel;
import com.store.storeapps.models.ItemDetails;
import com.store.storeapps.models.LeftMenuModel;
import com.store.storeapps.models.Previous_ItemDetails;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.AppController;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


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
    public Previous_ItemDetails alldates  =new Previous_ItemDetails();
    public static HashMap<Integer , ArrayList<Previous_ItemDetails>> mProductItemsList;
    public static ArrayList<Previous_ItemDetails> inProductItemsList;
    private ArrayList<LeftMenuModel> leftMenuList;
   // public static ArrayList<CartItemModel> mCartItemsList;
    public static JSONArray dates;
    public static JSONObject products;
    public static boolean isLogged = false;
    public static String loggedUserEmail;
    private static ListView list_home_left_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_previous);
        initUI();
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
    }

    private void setLeftMenuData() {
        leftMenuList = new ArrayList<>();
        for (int i = 0; i < Utility.getSideMenuItemsListName(this).length; i++) {
            LeftMenuModel leftMenuModel = new LeftMenuModel();
            leftMenuModel.setmName(Utility.getSideMenuItemsListName(this)[i]);
            leftMenuModel.setmImage(Utility.getSideMenuItemsListIcons(this)[i]);
            leftMenuList.add(leftMenuModel);
        }

        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.USER_NAME)))
        {
            loggedUserEmail = Constants.USER_EMAIL_ID;
            isLogged = true;
        }
        else
        {
            loggedUserEmail = "";
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

                        if(isLogged) {
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
                Intent homeAct = new Intent(this, HomeActivity.class);
                startActivity(homeAct);
                break;
            case 2:
                Utility.navigateDashBoardFragment(new StoreCashFragment(), StoreCashFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 3:
                Utility.navigateDashBoardFragment(new LoginFragment(), LoginFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 4:
                Utility.navigateDashBoardFragment(new RegistrationFragment(), RegistrationFragment.TAG, null, Previous_ProductsActivity.this);
                break;
            case 5:
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
                Intent homeAct = new Intent(this, HomeActivity.class);
                startActivity(homeAct);
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
                Utility.navigateDashBoardFragment(new Blog(), Blog.TAG, null, Previous_ProductsActivity.this);
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
            case 11:
                Utility.navigateDashBoardFragment(new Faqview(), Faqview.TAG, null, Previous_ProductsActivity.this);
                break;
            case 10:
                signOut();
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
                            products =jsonobject.getJSONObject("tbl_pproducts");
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
                                mProductItemsList.put(i,inProductItemsList);
                                System.out.println("All" + pname);
                                System.out.println("date" + date1);
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
            if (tagName.equals(Previous_ProductsFragment.TAG)) {
                finishAffinity();
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
}
