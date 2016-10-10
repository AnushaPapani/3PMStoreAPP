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
import com.store.storeapps.fragments.Previous_ProductsFragment;
import com.store.storeapps.models.CartItemModel;
import com.store.storeapps.models.ItemDetails;
import com.store.storeapps.models.LeftMenuModel;
import com.store.storeapps.models.Previous_ItemDetails;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.AppController;
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
    public static ArrayList<CartItemModel> mCartItemsList;
    public static JSONArray dates;
    public static String mCartId = "";
    public static int mCartValue = 0;
    public static JSONObject products;
//    private List<DataConstants> datalist = new ArrayList<DataConstants>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_previous);
        initUI();
        globalVariable = (AppController) getApplicationContext();
    }

    private void initUI() {
        mCartItemsList = new ArrayList<>();
     /*DRAWER ICON*/
        txt_home_left_drawer_icon = (TextView) findViewById(R.id.txt_home_left_drawer_icon);
        txt_settings_icon = (TextView) findViewById(R.id.txt_home_right_drawer_icon);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_home_layout);
        cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_layout_button_set_text = (Button) findViewById(R.id.cart_layout_button_set_text);
        cart_icon = (ImageView) findViewById(R.id.cart_icon);

        txt_home_left_drawer_icon.setTypeface(Utility.setTypeFace_fontawesome(this));
        txt_settings_icon.setTypeface(Utility.setTypeFace_fontawesome(this));
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

//        setHeader(list_home_left_drawer);
    }

    private void navigateSideMenuClick(int position) {
        switch (position) {
            case 0:
                Intent i=new Intent(Previous_ProductsActivity.this,HomeActivity.class);
                startActivity(i);
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

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

        /*if (globalVariable.getUserid().toString() == null) {
           loginNameTextViews.setText("Welcome Guest");
        } else {
            loginNameTextViews.setText(globalVariable.getName().toString());
            emails.setText("" + globalVariable.getUserid().toString());
        }*/
        list_home_left_drawer.addHeaderView(layout_list_header);
    }

    private void homeScreenNavigation() {
        Utility.navigateDashBoardFragment(new Previous_ProductsFragment(), Previous_ProductsFragment.TAG, null, Previous_ProductsActivity.this);
    }

    private void getProductsList() {
        if (Utility.isNetworkAvailable(this)) {
            //homeScreenNavigation();
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
//                if (!Utility.isValueNullOrEmpty(mCartId) && (globalVariable.getUserid() != null)) {
//                    Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, Previous_ProductsActivity.this);
//                }else if (!Utility.isValueNullOrEmpty(mCartId) && (globalVariable.getUserid() == null)){
//                    Utility.navigateDashBoardFragment(new LoginFragment(), LoginFragment.TAG, null, Previous_ProductsActivity.this);
//                }
//                else {
//                    Utility.showToastMessage(this, "Add at least one item to cart");
//                }
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

//                if (response != null) {
//                    JSONObject jsonobject = new JSONObject(response);
                    if (response != null) {
                        JSONObject jsonobject = new JSONObject(response);
                        if (jsonobject != null) {
                            products =jsonobject.getJSONObject("tbl_pproducts");
                            dates = jsonobject.getJSONArray("dates");
//                            JSONArray product1 =products.getJSONArray(date1)
                            for (int i = 0; i < dates.length(); i++) {
                                String first_date = dates.getString(i).toString();
                                JSONArray c = products.getJSONArray(first_date);
                                System.out.println("All Dates" + dates);
                                inProductItemsList = new ArrayList<Previous_ItemDetails>();
                                for (int j = 0; j < c.length(); j++) {

                                    Previous_ItemDetails product_itemDetails_getters_setters = new Previous_ItemDetails();
                                    JSONObject jsonResponse_tag = c.getJSONObject(j);
                                    //pname = jsonResponse_tag.getString("P_Name");

                                    product_itemDetails_getters_setters.setP_P_Name(jsonResponse_tag.optString("P_Name"));
                                    product_itemDetails_getters_setters.setP_P_Cost(jsonResponse_tag.optString("P_Cost"));
                                    product_itemDetails_getters_setters.setP_ID(jsonResponse_tag.optString("P_ID"));
                                    product_itemDetails_getters_setters.setP_Image(jsonResponse_tag.optString("P_Image"));


                                    inProductItemsList.add(product_itemDetails_getters_setters);
                                //System.out.println("pname " + product_itemDetails_getters_setters.getP_P_Name().toString());
                                }
                                mProductItemsList.put(i,inProductItemsList);
                               // System.out.println("fetchdates" + alldates.getDates().toString());
                                System.out.println("All" + pname);
                                System.out.println("date" + date1);


                            }
                            //alldates.setDates(mylist);
                            homeScreenNavigation();
                        }
//                    if (jsonobject != null) {
////                        JSONArray products = jsonobject.optJSONArray("tbl_products");
//                        JSONObject products =jsonobject.getJSONObject("tbl_pproducts");
//                        JSONArray dates = jsonobject.getJSONArray("dates");
//                        ItemDetails product_itemDetails_getters_setters = new ItemDetails();
//                        for (int i = 0; i < dates.length(); i++) {
//                            String first_date = dates.getString(i).toString();
//                            JSONArray c = products.getJSONArray(first_date);
//                            System.out.println("All Dates " + dates);
//                            for (int j = 0; j < c.length(); i++) {
//                                JSONObject jsonResponse_tag = c.getJSONObject(j);
//                                pname = jsonResponse_tag.getString("P_Name");
//
//
//                                product_itemDetails_getters_setters.setP_P_Name(jsonResponse_tag.optString("P_Name"));
////                                System.out.println("pname " + product_itemDetails_getters_setters.getP_P_Name().toString());
//
//
//
//                            }
//                            System.out.println("All" + pname);
//                            mProductItemsList.add(product_itemDetails_getters_setters);
//                        }
//
////                        for (int i = 0; i < products.length(); i++) {
////                            JSONObject jsonResponse_tag = products.optJSONObject(i);
////                            ItemDetails product_itemDetails_getters_setters = new ItemDetails();
////
////                            product_itemDetails_getters_setters.setEnabled(jsonResponse_tag.optBoolean("IsEnabled"));
////                            product_itemDetails_getters_setters.setProduct_Type(jsonResponse_tag.optString("Product_Type"));
////                            product_itemDetails_getters_setters.setP_Cost(jsonResponse_tag.optInt("P_Cost"));
////                            product_itemDetails_getters_setters.setP_Date(jsonResponse_tag.optString("P_Date"));
////                            product_itemDetails_getters_setters.setP_Description(jsonResponse_tag.optString("P_Description"));
////                            product_itemDetails_getters_setters.setP_hfeatures(jsonResponse_tag.optString("P_hfeatures"));
////                            product_itemDetails_getters_setters.setP_id(jsonResponse_tag.optString("P_ID"));
////                            product_itemDetails_getters_setters.setP_Information(jsonResponse_tag.optString("P_Information"));
////                            product_itemDetails_getters_setters.setP_Name(jsonResponse_tag.optString("P_Name"));
////                            product_itemDetails_getters_setters.setP_Qty(jsonResponse_tag.optInt("P_Qty"));
////                            product_itemDetails_getters_setters.setP_Video(jsonResponse_tag.optString("P_Video"));
////                            product_itemDetails_getters_setters.setP_shortdesc(jsonResponse_tag.optString("P_shortdesc"));
////                            product_itemDetails_getters_setters.setStock(jsonResponse_tag.optString("Stock"));
////                            product_itemDetails_getters_setters.setStrikeMrp(jsonResponse_tag.optString("StrikeMrp"));
////
////
////                            JSONArray images = jsonResponse_tag.optJSONArray("Images");
////                            ArrayList<String> imagesArray = new ArrayList<>();
////                            for (int j = 0; j < images.length(); j++) {
////                                imagesArray.add(images.optString(j));
////                            }
////                            product_itemDetails_getters_setters.setImages(imagesArray);
////
////                            JSONArray attrTypes = jsonResponse_tag.optJSONArray("attrTypes");
////                            ArrayList<String> attrTypesArray = new ArrayList<>();
////                            if (attrTypes != null) {
////                                for (int k = 0; k < attrTypes.length(); k++) {
////                                    attrTypesArray.add(attrTypes.optString(k));
////                                }
////                            }
////                            product_itemDetails_getters_setters.setAttrTypes(attrTypesArray);
////
////
////                            JSONArray attrNames = jsonResponse_tag.optJSONArray("attrNames");
////                            ArrayList<String> attrNamesArray = new ArrayList<>();
////                            if (attrNames != null) {
////                                for (int j = 0; j < attrNames.length(); j++) {
////                                    attrNamesArray.add(attrNames.optString(j));
////                                }
////                            }
////                            product_itemDetails_getters_setters.setAttrNames(attrNamesArray);
////
////                            JSONArray attrValues = jsonResponse_tag.optJSONArray("attrValues");
////                            ArrayList<String> attrValuesArray = new ArrayList<>();
////                            if (attrValues != null) {
////                                for (int j = 0; j < attrValues.length(); j++) {
////                                    attrValuesArray.add(attrValues.optString(j));
////                                }
////                            }
////                            product_itemDetails_getters_setters.setAttrValues(attrValuesArray);
////
////                            mProductItemsList.add(product_itemDetails_getters_setters);
////                        }
//
//                        homeScreenNavigation();
//                    }
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
//            else if (tagName.equals(ReviewOrderFragment.TAG)) {
//                initUI();
//            }
        }
    }
}
