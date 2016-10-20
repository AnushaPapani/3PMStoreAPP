package com.three.pmstore.fragments;


import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.three.pmstore.R;
import com.three.pmstore.activities.Previous_ProductsActivity;
import com.three.pmstore.customviews.CustomProgressDialog;
import com.three.pmstore.customviews.DialogClass;
import com.three.pmstore.utility.ApiConstants;
import com.three.pmstore.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.three.pmstore.activities.Previous_ProductsActivity.mProductItemsList;


/**
 * Created by Shankar.
 */
public class PreviousProductFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "PreviousProductFragment";
    private TextView text_desc;
//    private Previous_ProductsActivity mParent;
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
    public static String P_ID, notLoggedUserEmail;
    Toast toast;
    View toastRoot;
    View toastRoot2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mParent = (Previous_ProductsActivity)getActivity();
        if (getArguments() != null) {
            mPosition = getArguments().getInt("prevprodposition");
            Utility.showLog("prevprodposition", "prevprodposition" + mPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_previous_product, container, false);
        toastRoot = inflater.inflate(R.layout.toast, container, false);
        toastRoot2 =inflater.inflate(R.layout.error_toast, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

        pname1.setText("" + mProductItemsList.get(mPosition).get(0).getP_P_Name());
        pname2.setText("" + mProductItemsList.get(mPosition).get(1).getP_P_Name());
        pname3.setText("" + mProductItemsList.get(mPosition).get(2).getP_P_Name());

        cost1.setText("" + mProductItemsList.get(mPosition).get(0).getP_P_Cost());
        cost2.setText("" + mProductItemsList.get(mPosition).get(1).getP_P_Cost());
        cost3.setText("" + mProductItemsList.get(mPosition).get(2).getP_P_Cost());

        if(Previous_ProductsActivity.isLogged)
        {
            getitback1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(Previous_ProductsActivity.loggedUserEmail != null ){
                        new getitback(mProductItemsList.get(mPosition).get(0).getP_ID(),"").execute();
                        Utility.showToastMessage(getActivity(), "We will notify you when it is back");
                    }
                    else
                    {
                        Utility.showToastMessage(getActivity(), "Please enter valid Email");
                    }
                }
            });

            getitback2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(Previous_ProductsActivity.loggedUserEmail != null ){
                        new getitback(mProductItemsList.get(mPosition).get(1).getP_ID(),"").execute();
                        Utility.showToastMessage(getActivity(), "We will notify you when it is back");
                    }
                    else
                    {
                        Utility.showToastMessage(getActivity(), "Please enter valid Email");
                    }
                }
            });

            getitback3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(Previous_ProductsActivity.loggedUserEmail != null ){
                        new getitback(mProductItemsList.get(mPosition).get(2).getP_ID(),"").execute();
                        Utility.showToastMessage(getActivity(), "We will notify you when it is back");
                    }
                    else
                    {
                        Utility.showToastMessage(getActivity(), "Please enter valid Email");
                    }
                }
            });
        }
        else
        {
            getitback1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.popup_screen);

                    final EditText editText = (EditText) dialog.findViewById(R.id.editText1);
                    Button button = (Button) dialog.findViewById(R.id.button1);
                    final TextView textView = (TextView) dialog.findViewById(R.id.textView1);
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            String enteremailid = editText.getText().toString();
                            if (Utility.validate(enteremailid)) {
                                new getitback(mProductItemsList.get(mPosition).get(0).getP_ID(), enteremailid).execute();
                                Utility.showToastMessage(getActivity(), "We will notify you when it is back");
                                dialog.dismiss();
                            } else {
                                textView.setVisibility(View.VISIBLE);
                                Utility.showToastMessage(getActivity(), "Please enter valid Email");
                            }

                        }
                    });

                    dialog.show();
                }
            });

            getitback2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.popup_screen);

                    final EditText editText = (EditText) dialog.findViewById(R.id.editText1);
                    Button button = (Button) dialog.findViewById(R.id.button1);
                    final TextView textView = (TextView) dialog.findViewById(R.id.textView1);
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            String enteremailid = editText.getText().toString();
                            if (Utility.validate(enteremailid)) {
                                new getitback(mProductItemsList.get(mPosition).get(1).getP_ID(), enteremailid).execute();
                                Utility.showToastMessage(getActivity(), "We will notify you when it is back");
                                dialog.dismiss();
                            } else {
                                textView.setVisibility(View.VISIBLE);
                                Utility.showToastMessage(getActivity(), "Please enter valid Email");
                            }

                        }
                    });

                    dialog.show();
                }
            });

            getitback3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.popup_screen);

                    final EditText editText = (EditText) dialog.findViewById(R.id.editText1);
                    Button button = (Button) dialog.findViewById(R.id.button1);
                    final TextView textView = (TextView) dialog.findViewById(R.id.textView1);
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            String enteremailid = editText.getText().toString();
                            if (Utility.validate(enteremailid)) {
                                new getitback(mProductItemsList.get(mPosition).get(2).getP_ID(), enteremailid).execute();
                                Utility.showToastMessage(getActivity(), "We will notify you when it is back");
                                dialog.dismiss();
                            } else {
                                textView.setVisibility(View.VISIBLE);
                                Utility.showToastMessage(getActivity(), "Please enter valid Email");
                            }

                        }
                    });

                    dialog.show();
                }
            });
        }

        Utility.showLog("Image Url 0", "Image Url 0" + getFullFilledImage(mProductItemsList.get(mPosition).get(0).getP_Image()));
        Utility.showLog("Image Url 1", "Image Url 1" + getFullFilledImage(mProductItemsList.get(mPosition).get(1).getP_Image()));
        Utility.showLog("Image Url 2", "Image Url 2" + getFullFilledImage(mProductItemsList.get(mPosition).get(2).getP_Image()));
        Picasso.with(getActivity()).load(getFullFilledImage(mProductItemsList.get(mPosition).get(0).getP_Image()))
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
                .into(previous_img_highlighted1);
        Picasso.with(getActivity()).load(getFullFilledImage(mProductItemsList.get(mPosition).get(1).getP_Image()))
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
                .into(previous_img_highlighted2);
        Picasso.with(getActivity()).load(getFullFilledImage(mProductItemsList.get(mPosition).get(2).getP_Image()))
                .placeholder(Utility.getDrawable(getActivity(), R.drawable.refresh))
                .into(previous_img_highlighted3);
    }

    private String getFullFilledImage(String image) {
        return image.replaceAll(" ", "%20");
    }

    class getitback extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String P_ID, email;
        public getitback(String pid, String constEmail) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            P_ID = pid;
            email = constEmail;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            // Building Parameters
            List<NameValuePair> prevparams = new ArrayList<NameValuePair>();
            if (Previous_ProductsActivity.isLogged) {
                prevparams.add(new BasicNameValuePair("productid", P_ID));
                prevparams.add(new BasicNameValuePair("email", Previous_ProductsActivity.loggedUserEmail));
            } else {
                prevparams.add(new BasicNameValuePair("productid", P_ID));
                prevparams.add(new BasicNameValuePair("email", email));
            }
            result = Utility.httpPostRequestToServer(ApiConstants.INSERT_PREVIOUS_PRODUCTS, prevparams);

            return result;
        }

        private final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    Toast.makeText(getActivity(), "We will notify you when it is back", Toast.LENGTH_LONG).show();
                }
            }
        };

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                JSONObject jsonobject = null;
                try {
                    jsonobject = new JSONObject(response);
                    if (jsonobject != null) {
                        if (jsonobject.getInt("success") != 1) {
                            Message msg = handler.obtainMessage();
                            msg.arg1 = 1;
                            handler.sendMessage(msg);
                        }
                    }
                    mCustomProgressDialog.dismissProgress();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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
        }
    }


    private void postSelectedItem() {
        if (Utility.isNetworkAvailable(getActivity())) {
        } else {
            DialogClass.createDAlertDialog(getActivity(), "The Internet connection appears to be offline.");
        }
    }
}
