package com.store.storeapps.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.store.storeapps.R;
import com.store.storeapps.activities.Previous_ProductsActivity;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.utility.Utility;


/**
 * Created by Shankar.
 */
public class PreviousProductFragment extends Fragment implements View.OnClickListener {


    private TextView text_desc;
    private Previous_ProductsActivity mParent;
    private View rootView;
    public static String pname;
    public static TextView pname1;
    public static TextView pname2;
    public static TextView pname3;
    public static TextView cost1;
    public static TextView cost2;
    public static TextView cost3;
    private Button getitback1;
    private Button getitback2;
    private Button getitback3;
    private int mPosition = -1;
    private int mSelectedPosition = 0;
    private ImageView previous_img_highlighted1;
    private ImageView previous_img_highlighted2;
    private ImageView previous_img_highlighted3;
    private static final String TAG_SUCCESS = "success";
    Toast toast;
    View toastRoot;
    View toastRoot2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (Previous_ProductsActivity) getActivity();
        if (getArguments() != null) {
            mPosition = getArguments().getInt("prevprodposition");
            Utility.showLog("prevprodposition", "prevprodposition" + mPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_previous_product, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        previous_img_highlighted1 = (ImageView) rootView.findViewById(R.id.ppimage1);
        previous_img_highlighted2 = (ImageView) rootView.findViewById(R.id.ppimage2);
        previous_img_highlighted3 = (ImageView) rootView.findViewById(R.id.ppimage3);

        pname1 =(TextView)rootView.findViewById(R.id.ppname1);
        pname2 =(TextView)rootView.findViewById(R.id.ppname2);
        pname3 =(TextView)rootView.findViewById(R.id.ppname3);
        cost1 =(TextView)rootView.findViewById(R.id.ppcost1);
        cost2 =(TextView)rootView.findViewById(R.id.ppcost2);
        cost3 =(TextView)rootView.findViewById(R.id.ppcost3);
        getitback1 =(Button) rootView.findViewById(R.id.getitback1);
        getitback2 =(Button) rootView.findViewById(R.id.getitback2);
        getitback3 =(Button) rootView.findViewById(R.id.getitback3);
//        Picasso.with(getActivity()).load(getFullFilledImage("http://img.youtube.com/vi/" + Previous_ProductsActivity.mProductItemsList.get(mPosition).getP_Video() + "/0.jpg")).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
//                .into(image_thumbnail);




        pname1.setText("" + Previous_ProductsActivity.mProductItemsList.get(mPosition).get(0).getP_P_Name());
        pname2.setText("" + Previous_ProductsActivity.mProductItemsList.get(mPosition).get(1).getP_P_Name());
        pname3.setText("" + Previous_ProductsActivity.mProductItemsList.get(mPosition).get(2).getP_P_Name());

        cost1.setText("" + Previous_ProductsActivity.mProductItemsList.get(mPosition).get(0).getP_P_Cost());
        cost2.setText("" + Previous_ProductsActivity.mProductItemsList.get(mPosition).get(1).getP_P_Cost());
        cost3.setText("" + Previous_ProductsActivity.mProductItemsList.get(mPosition).get(2).getP_P_Cost());

//        if(globalVariable.getUserid()!= null){
//            getback.setText("Get it Back");

//        getitback1.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//						/*Get Product ID from (P_id) and user email,send them to database*/
////                     if(email != null ){
//                        new getitback().execute();
//                        TextView t =(TextView)toastRoot2.findViewById(R.id.validtoast);
//                        t.setText("We will notify you when it is back");
//                        toast.setView(toastRoot2);
//                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
//                        toast.setDuration(20000);
//                        toast.show();
//                    }
////                    else
////                    {
////                        TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
////                        t.setText("Please enter valid Email");
////                        toast.setView(toastRoot);
////                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
////                        toast.setDuration(20000);
////                        toast.show();
////                    }
//
//
////                }
////            });
//        }
//        else{
        getitback1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.popup_screen);

                    final EditText editText = (EditText)dialog.findViewById(R.id.editText1);
                    Button button = (Button)dialog.findViewById(R.id.button1);
                    final TextView textView = (TextView)dialog.findViewById(R.id.textView1);
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
//                            if(enteremailid.matches(emailPattern) && enteremailid.length() > 1)
//                            {
//                                new getitback().execute();
                                TextView t =(TextView)toastRoot2.findViewById(R.id.validtoast);
                                t.setText("We will notify you when it is back");
                                toast.setView(toastRoot2);
                                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
                                toast.setDuration(20000);
                                toast.show();
                                dialog.dismiss();
//                            }
//                            else
//                            {
//                                textView.setVisibility(View.VISIBLE);
//                                TextView t =(TextView)toastRoot.findViewById(R.id.errortoast);
//                                t.setText("Please enter valid Email");
//                                toast.setView(toastRoot);
//                                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.FILL_HORIZONTAL, 0, 80);
//                                toast.setDuration(20000);
//                                toast.show();

//                            }

                        }
                    });

                    dialog.show();
                }

            });
//        }
        getitback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.showToastMessage(getActivity(), Previous_ProductsActivity.mProductItemsList.get(mPosition).get(0).getP_ID());
            }
        });

        getitback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showToastMessage(getActivity(), Previous_ProductsActivity.mProductItemsList.get(mPosition).get(1).getP_ID());
            }
        });

        getitback3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showToastMessage(getActivity(), Previous_ProductsActivity.mProductItemsList.get(mPosition).get(2).getP_ID());
            }
        });

        Utility.showLog("Image Url 0", "Image Url 0" + getFullFilledImage(Previous_ProductsActivity.mProductItemsList.get(mPosition).get(0).getP_Image()));
        Utility.showLog("Image Url 1", "Image Url 1" + getFullFilledImage(Previous_ProductsActivity.mProductItemsList.get(mPosition).get(1).getP_Image()));
        Utility.showLog("Image Url 2", "Image Url 2" + getFullFilledImage(Previous_ProductsActivity.mProductItemsList.get(mPosition).get(2).getP_Image()));
        Picasso.with(getActivity()).load(getFullFilledImage(Previous_ProductsActivity.mProductItemsList.get(mPosition).get(0).getP_Image()))
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
                .into(previous_img_highlighted1);
        Picasso.with(getActivity()).load(getFullFilledImage(Previous_ProductsActivity.mProductItemsList.get(mPosition).get(1).getP_Image()))
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
                .into(previous_img_highlighted2);
        Picasso.with(getActivity()).load(getFullFilledImage(Previous_ProductsActivity.mProductItemsList.get(mPosition).get(2).getP_Image()))
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
                .into(previous_img_highlighted3);

        updateUI(mPosition, mSelectedPosition);



    }

    private String getFullFilledImage(String image) {
        return image.replaceAll(" ", "%20");
    }

    private void updateUI(int mPosition, int selectedPosition) {

        System.out.println("Getting dates" + Previous_ProductsActivity.dates);

    }

//    class getitback extends AsyncTask<String, String, String> {
//        private CustomProgressDialog mCustomProgressDialog;
//
//        public getitback() {
//            mCustomProgressDialog = new CustomProgressDialog(getActivity());
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
//            mProductItemsList = new ArrayList<ItemDetails>();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String result = null;
//            try {
//                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
//                Utility.showLog("data", "datadata" + paramsList.toString());
//                result = Utility.httpGetRequestToServer(ApiConstants.GET_ALL_PRODUCTS);
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String response) {
//            super.onPostExecute(response);
//
//            // Building Parameters
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
////            if(IsLogged){
//                params.add(new BasicNameValuePair("productid", Pid));
//                params.add(new BasicNameValuePair("email", email));
////			params.add(new BasicNameValuePair("email", enteremailid));
////            }else{
////                params.add(new BasicNameValuePair("productid", Pid));
//////				params.add(new BasicNameValuePair("email", email));
////                params.add(new BasicNameValuePair("email", popmail));
////            }
//            // getting JSON Object
//            // Note that create product url accepts POST method
//            json = jParser.makeHttpRequest(url_insert_previousproducts,
//                    "POST", params);
//
//            // check log cat fro response
//            Log.d("Create Response", json.toString());
//
//            // check for success tag
//            try {
//                int success = json.getInt(TAG_SUCCESS);
//                try {
//                    System.out.println("Previous products success"+success);
//                } catch (NullPointerException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                if (success == 1) {
//                    // successfully created user
////					Intent i = new Intent(getApplicationContext(), PreviousProductsActivity.class);
////					startActivity(i);
////					finish();
//
//
//                } else {
////					System.out.println("Address not found");
//                    Message msg = handler.obtainMessage();
//                    msg.arg1 = 1;
//                    handler.sendMessage(msg);
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left_icon:
               // updateLeftImage();
                break;
            case R.id.txt_right_icon:
              //  updateRightImage();
                break;
            case R.id.img_first:
             //   updateParticularImage(0);
                break;
            case R.id.img_second:
             //   updateParticularImage(1);
                break;
            case R.id.img_third:
             //   updateParticularImage(2);
                break;
            case R.id.img_four:
             //   updateParticularImage(3);
                break;
            case R.id.img_five:
              //  updateParticularImage(4);
                break;
            case R.id.txt_buy:
//                buyProduct();
                break;
//            case R.id.image_thumbnail:
//                Intent intent = new Intent(getActivity(), YoutubeVideoActivity.class);
//                intent.putExtra("videoId", Previous_ProductsActivity.mProductItemsList.get(mPosition).getP_Video());
//                startActivity(intent);
//                break;
//            case R.id.btn_desc:
//                text_desc.setText(Html.fromHtml(Previous_ProductsActivity.mProductItemsList.get(mPosition).getP_Description()));
//                break;
//            case R.id.btn_spec:
//                text_desc.setText(Html.fromHtml(Previous_ProductsActivity.mProductItemsList.get(mPosition).getP_Information()));
//                break;
        }
    }

//    private void buyProduct() {
//        if (txt_buy.getText().toString().equalsIgnoreCase("Out Of Stock")) {
//            Utility.showToastMessage(getActivity(), "Out Of Stock..! Try after sometime");
//        } else {
////            if (isValidFields()) {
//////                postSelectedItem();
////            }
//        }
//    }

    private void postSelectedItem() {
        if (Utility.isNetworkAvailable(getActivity())) {
//            new PostProductAsyncTask().execute();
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }


//    private boolean isValidFields() {
//        if (Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes() != null && Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().size() > 0) {
//            if (Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().size() == 1) {
//
//                if (!text_one.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))) {
//                    return true;
//                } else {
//                    Utility.showToastMessage(getActivity(), "Please select " + Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0));
//                    return false;
//                }
//            } else if (Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrNames().size() == 2) {
//
//                if (!text_one.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))
//                        && !text_two.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))) {
//                    return true;
//                } else {
//                    if (text_one.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))) {
//                        Utility.showToastMessage(getActivity(), "Please select " + Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0));
//                        return false;
//                    } else if (text_two.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))) {
//                        Utility.showToastMessage(getActivity(), "Please select " + Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1));
//                        return false;
//                    } else {
//                        return false;
//                    }
//                }
//            } else if (Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrNames().size() == 3) {
//                String text_one = spin_one.getSelectedItem().toString();
//                String text_two = spin_two.getSelectedItem().toString();
//                String text_three = spin_three.getSelectedItem().toString();
//                if (!text_one.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))
//                        && !text_two.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1)) &&
//                        !text_three.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2))) {
//                    return true;
//                } else {
//                    if (text_one.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))) {
//                        Utility.showToastMessage(getActivity(), "Please select " + Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0));
//                        return false;
//                    } else if (text_two.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))) {
//                        Utility.showToastMessage(getActivity(), "Please select " + Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1));
//                        return false;
//                    } else if (text_three.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2))) {
//                        Utility.showToastMessage(getActivity(), "Please select " + Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2));
//                        return false;
//                    } else {
//                        return false;
//                    }
//                }
//            } else if (Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrNames().size() == 4) {
//                String text_one = spin_one.getSelectedItem().toString();
//                String text_two = spin_two.getSelectedItem().toString();
//                String text_three = spin_three.getSelectedItem().toString();
//                String text_four = spin_four.getSelectedItem().toString();
//
//                if (!text_one.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))
//                        && !text_two.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))
//                        && !text_four.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))
//                        && !text_three.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2))) {
//                    return true;
//                } else {
//                    if (text_one.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0))) {
//                        Utility.showToastMessage(getActivity(), "Please select " + Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(0));
//                        return false;
//                    } else if (text_two.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1))) {
//                        Utility.showToastMessage(getActivity(), "Please select " + Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(1));
//                        return false;
//                    } else if (text_three.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2))) {
//                        Utility.showToastMessage(getActivity(), "Please select " + Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(2));
//                        return false;
//                    } else if (text_four.equalsIgnoreCase(Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(3))) {
//                        Utility.showToastMessage(getActivity(), "Please select " + Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().get(3));
//                        return false;
//                    } else {
//                        return false;
//                    }
//                }
//            } else {
//                return false;
//            }
//        } else {
//            return true;
//        }
//    }

//    private void updateParticularImage(int mSelectedPosition) {
//        this.mSelectedPosition = mSelectedPosition;
//        Picasso.with(getActivity()).load(getFullFilledImage(Previous_ProductsActivity.mProductItemsList.get(mPosition).getImages()
//                .get(mSelectedPosition))).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
//                .into(img_highlighted);
//        updateUI(mPosition, mSelectedPosition);
//    }
//
//    private void updateLeftImage() {
//        mSelectedPosition = mSelectedPosition - 1;
//        Picasso.with(getActivity()).load(getFullFilledImage(Previous_ProductsActivity.mProductItemsList.get(mPosition).getImages()
//                .get(mSelectedPosition))).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
//                .into(img_highlighted);
//        updateUI(mPosition, mSelectedPosition);
//    }
//
//    private void updateRightImage() {
//        mSelectedPosition = mSelectedPosition + 1;
//        Picasso.with(getActivity()).load(getFullFilledImage(Previous_ProductsActivity.mProductItemsList.get(mPosition).getImages()
//                .get(mSelectedPosition))).placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
//                .into(img_highlighted);
//        updateUI(mPosition, mSelectedPosition);
//    }

//    private class PostProductAsyncTask extends AsyncTask<String, String, String> {
//        private CustomProgressDialog mCustomProgressDialog;
//
//        public PostProductAsyncTask() {
//            mCustomProgressDialog = new CustomProgressDialog(getActivity());
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String result = null;
//            try {
//                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
//                paramsList.put("U_ID", "");
//                paramsList.put("P_ID", Previous_ProductsActivity.mProductItemsList.get(mPosition).getP_id());
//                paramsList.put("cartValue ", ""+Previous_ProductsActivity.mCartValue);
//                paramsList.put("P_Name", Previous_ProductsActivity.mProductItemsList.get(mPosition).getP_Name());
//                paramsList.put("P_Cost", "" + Previous_ProductsActivity.mProductItemsList.get(mPosition).getP_Cost());
//                //paramsList.put("P_Qty", getSelectedSpinner("Quantity"));
//                paramsList.put("P_Qty", "1");
//                paramsList.put("gender", getSelectedSpinner("Gender"));
//                paramsList.put("customattribute", getSelectedSpinner("Custom"));
//                paramsList.put("color", getSelectedSpinner("Color"));
//                paramsList.put("size", getSelectedSpinner("Size"));
//                paramsList.put("cartId", Previous_ProductsActivity.mCartId);
//
//                result = Utility.httpPostRequestToServer(ApiConstants.INSERT_CHECK_PRODUCTS,  Utility.getParams(paramsList));
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String response) {
//            super.onPostExecute(response);
//            try {
//                if (response != null) {
//                    JSONObject jsonobject = new JSONObject(response);
//                    Previous_ProductsActivity.mCartId = jsonobject.optString("cartId");
//                    Previous_ProductsActivity.mCartValue = jsonobject.optInt("cartCount");
//                    Previous_ProductsActivity.cart_layout_button_set_text.setText(""+Previous_ProductsActivity.mCartValue);
//                    Utility.showToastMessage(getActivity(), "Product Added Cart to Successfully");
//                }
//                mCustomProgressDialog.dismissProgress();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    private String getSelectedSpinner(String mSelectedName) {
//        String mSelectedThing = "";
//        if (Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().contains(mSelectedName)) {
//            int position = Previous_ProductsActivity.mProductItemsList.get(mPosition).getAttrTypes().indexOf(mSelectedName);
//            if (position == 0) {
//                mSelectedThing = spin_one.getSelectedItem().toString();
//            } else if (position == 1) {
//                mSelectedThing = spin_two.getSelectedItem().toString();
//            } else if (position == 2) {
//                mSelectedThing = spin_three.getSelectedItem().toString();
//            } else if (position == 3) {
//                mSelectedThing = spin_four.getSelectedItem().toString();
//            }
//        }
//        return mSelectedThing;
//    }
}
