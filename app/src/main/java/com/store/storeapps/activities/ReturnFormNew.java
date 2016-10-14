package com.store.storeapps.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.store.storeapps.R;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.customviews.DialogClass;
import com.store.storeapps.fragments.MyOrderFragment;
import com.store.storeapps.fragments.ReturnFormFragment;
import com.store.storeapps.models.ModelArray;
import com.store.storeapps.models.Movie;
import com.store.storeapps.models.MyOrdersModel;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import static com.store.storeapps.R.layout.toast;

/**
 * Created by satyanarayana pdv on 10/4/2016.
 */

public class ReturnFormNew extends Activity {

    private LayoutInflater mInflater;
    String issue,orderid,CartProductId,payment, returnTypeString, accountType, issueExplain, newProduct, bname, bemaill, branch,
            bankname, bifsccode, baccount, breenteraccount, first, radiodata, Uname, U_id , PaymentType;
    int posss;

    EditText issueexplainET,email,newproductET,nameET,banknameET,
            ifsccodeET,accountnoET,reenterAccountnoET,branchET;
    View relativeBank ,relativeissue,returnTypeRelative;
    CheckBox confirmCheckBox;
    RadioGroup rg;
    int rgpos;
    RadioButton storeCash,bankDetails,rgbutton;
    Button confirm,cancel;


    String orderpcost,bemail;
    JSONParser jsonParser = new JSONParser();
    Spinner issueSpinner,returnTypespinner,spinnerAccount;
    ArrayList<String> returnIssue,returnType;
    JSONObject json;
//    Toast toast;
//    View toastRoot;
//    View toastRoot2;
    TextView textAboveCheckBox;
    TextView textBelowissueET;
    String returnorder ="Order Return Request";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.+[a-zA-Z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.returnsform);

        orderid = MyOrderFragment.orderID;
        CartProductId = MyOrderFragment.CartPID;
        Uname = MyOrderFragment.USername;
        U_id = MyOrderFragment.Uid;
        payment = MyOrderFragment.PaymentType;
        System.out.println("payment" +payment );

        final Context context = getApplicationContext();
        // Create layout inflator object to inflate toast.xml file
        final LayoutInflater inflater = getLayoutInflater();
        // Call toast.xml file for toast layout
//            toastRoot = inflater.inflate(R.layout.toast, null);
//            toastRoot2 =inflater.inflate(R.layout.error_toast, null);
//            toast = new Toast(context);
        relativeBank = (RelativeLayout)findViewById(R.id.relativeBank);
        relativeissue = (RelativeLayout)findViewById(R.id.relative5);
        returnTypeRelative = (RelativeLayout)findViewById(R.id.relative3);
        TextView textorder =(TextView)findViewById(R.id.textView1);
        SpannableString content2 = new SpannableString(returnorder);
        content2.setSpan(new UnderlineSpan(), 0, returnorder.length(), 0);
        textorder.setText(content2);

        issueSpinner = (Spinner) findViewById(R.id.returnIssueSpinner);
        returnTypespinner = (Spinner) findViewById(R.id.spinnerreturntype);
        spinnerAccount = (Spinner) findViewById(R.id.spinnerAccount);

        //		confirmCheckBox = (CheckBox)findViewById(R.id.confirmCheckBox);

        issueexplainET =(EditText)findViewById(R.id.issueExplainET);
        newproductET = (EditText)findViewById(R.id.newProductET);
        nameET = (EditText)findViewById(R.id.nameET);
        //		emailET = (EditText)findViewById(R.id.emailET);
        banknameET = (EditText)findViewById(R.id.banknameET);
        ifsccodeET = (EditText)findViewById(R.id.ifsccodeET);
        accountnoET = (EditText)findViewById(R.id.accountnoET);
        reenterAccountnoET = (EditText)findViewById(R.id.reenterAccountnoET);
        branchET = (EditText)findViewById(R.id.branchET);

        textAboveCheckBox = (TextView)findViewById(R.id.textAboveCheckBox);


        storeCash= (RadioButton)findViewById(R.id.storeCash);
//        Typeface font = Typeface.createFromAsset(getAssets(), "myriadprobold.otf");
//        storeCash.setTypeface(font);
        bankDetails= (RadioButton)findViewById(R.id.bankAccount);
//        Typeface font2 = Typeface.createFromAsset(getAssets(), "myriadprobold.otf");
//        bankDetails.setTypeface(font2);

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        confirmCheckBox = (CheckBox)findViewById(R.id.confirmCheckBox);
//        Typeface font3 = Typeface.createFromAsset(getAssets(), "myriadprobold.otf");
//        confirmCheckBox.setTypeface(font3);

        cancel =(Button)findViewById(R.id.cancel);
        confirm =(Button)findViewById(R.id.confirm);


        TextView odrid = (TextView)findViewById(R.id.order);
        TextView cost =(TextView)findViewById(R.id.total);
        textBelowissueET = (TextView)findViewById(R.id.textBelowissueET);

        email = (EditText)findViewById(R.id.emailET);

        new GetStatus().execute();
        new GetReturnType().execute();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        issueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                final int issueposition=issueSpinner.getSelectedItemPosition();
                System.out.println("issueposition"+issueposition);

                if(issueposition==0)
                {
                    relativeissue.setVisibility(View.GONE);
                    returnTypeRelative.setVisibility(View.GONE);
                    rg.setVisibility(View.GONE);
                    confirmCheckBox.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    textBelowissueET.setVisibility(View.GONE);
                }
                else
                {
                    relativeissue.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        returnTypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                final int returnposition=returnTypespinner.getSelectedItemPosition();
                System.out.println("returnposition"+returnposition);

                if(returnposition==0)
                {
                    returnTypeRelative.setVisibility(View.GONE);
                    rg.setVisibility(View.GONE);
                    confirmCheckBox.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    relativeBank.setVisibility(View.GONE);
                    textAboveCheckBox.setVisibility(View.GONE);
                }
                else if(returnposition==1)
                {
                    Log.e("Position 1", "one ");
                    returnTypeRelative.setVisibility(View.VISIBLE);
                    confirmCheckBox.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    confirm.setVisibility(View.VISIBLE);
                    rg.setVisibility(View.GONE);
                    relativeBank.setVisibility(View.GONE);
                }
                else if(returnposition == 2)
                {
                    returnTypeRelative.setVisibility(View.GONE);

                    storeCash.setVisibility(View.VISIBLE);
                    bankDetails.setVisibility(View.VISIBLE);
                    confirmCheckBox.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.GONE);
                    confirm.setVisibility(View.VISIBLE);

                    rg.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ReturnFormAsyncTask().execute();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                final int returnposition2=returnTypespinner.getSelectedItemPosition();
                System.out.println("returnposition"+returnposition2);
                textAboveCheckBox.setVisibility(View.GONE);
                if(returnposition2==1)
                {
                    returnTypeRelative.setVisibility(View.GONE);
                    rg.setVisibility(View.GONE);
                    confirmCheckBox.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    relativeBank.setVisibility(View.GONE);
                    returnTypespinner.setSelection(0);
                    textBelowissueET.setVisibility(View.GONE);
                }
                else
                {

                    relativeBank.setVisibility(View.GONE);
                    relativeBank.invalidate();
                    //	 email,newproductET,nameET,banknameET,ifsccodeET,accountnoET,reenterAccountnoET,branchET
                    email.setText("");
                    nameET.setText("");
                    banknameET.setText("");
                    ifsccodeET.setText("");
                    accountnoET.setText("");
                    reenterAccountnoET.setText("");
                    branchET.setText("");

                    storeCash.setChecked(true);
                    bankDetails.setChecked(false);
                }



            }
        });

        initUI();

    }

    private void initUI() {
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        getMyOrdersData();
    }

//    private void getMyOrdersData() {
//        if (Utility.isNetworkAvailable(this)) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                new GetReturnType().execute();
//                new GetStatus().execute();
//            }
//        });


//        } else {
//            DialogClass.createDAlertDialog(this, "The Internet connection appears to be offline.");
//        }
//    }

    class ReturnFormAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public ReturnFormAsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(ReturnFormNew.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(ReturnFormNew.this, R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;

            int selectedId=rg.getCheckedRadioButtonId();
            rgbutton=(RadioButton)findViewById(selectedId);
            //            Toast.makeText(MainActivity.this,radioSexButton.getText(),Toast.LENGTH_SHORT).show();
            final String radiodata= rgbutton.getText().toString();
            System.out.println("orderid radiodata"+radiodata);

            StringTokenizer tokens = new StringTokenizer(radiodata, "(");
            final String first = tokens.nextToken();
            System.out.println("orderid radiodata"+first);
            final int pos=issueSpinner.getSelectedItemPosition();

            final String issue = issueSpinner.getSelectedItem().toString();
            final int poss=returnTypespinner.getSelectedItemPosition();
            final String returnType = returnTypespinner.getSelectedItem().toString();
            final String accountType = spinnerAccount.getSelectedItem().toString();

            System.out.println("orderid accountType"+accountType);
            System.out.println("orderid returnType"+returnType);
            System.out.println("orderid issueid position"+issue);
            final String issueExplain = issueexplainET.getText().toString();
            final String newProduct = newproductET.getText().toString();
            final String bname = nameET.getText().toString();
            final String bemaill = email.getText().toString();
            final String branch = branchET.getText().toString();
            final String bankname = banknameET.getText().toString();
            final String bifsccode = ifsccodeET.getText().toString();
            final String baccount = accountnoET.getText().toString();
            final String breenteraccount = reenterAccountnoET.getText().toString();


//            final String name = globalVariable.getName();
//            final String U_id = globalVariable.getUserid().toString();

            //			String orderid = globalVariable.getOrderid();
            System.out.println("orderid"+orderid);
            System.out.println("orderid"+U_id);
            System.out.println("orderid"+Uname);

            System.out.println("orderid"+issueExplain);
            System.out.println("orderid"+newProduct);
            System.out.println("orderid"+bname);
            System.out.println("email"+bemaill);
            System.out.println("orderid"+branch);
            System.out.println("orderid"+bankname);
            System.out.println("orderid"+bifsccode);
            System.out.println("orderid"+baccount);
            System.out.println("orderid accountType"+accountType);
            System.out.println("orderid returnType"+returnType);
            System.out.println("orderid issueid position"+issue);
            System.out.println("orderid payment"+payment);

            if(issueExplain.length()<15)
            {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        textBelowissueET.setVisibility(View.VISIBLE);
                        storeCash.setChecked(true);
                        bankDetails.setChecked(false);
                        relativeBank.setVisibility(View.GONE);
                    }});
            }
            else if(payment.equals("COD")) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        textBelowissueET.setVisibility(View.GONE);

                    }
                });
                final int returnposition3 = returnTypespinner.getSelectedItemPosition();
                System.out.println("returnposition" + returnposition3);


                if (returnposition3 == 2) {
                    if (first.equals("To My Bank Account")) {
                        final TextView textBelownameET, textBelowemailET, textBelowbranchET, textBelowbankET,
                                textBelowifsccodeET, textBelowaccountnoET, textBelowreenterAccountnoET;


                        textBelownameET = (TextView) findViewById(R.id.textBelownameET);
                        textBelowemailET = (TextView) findViewById(R.id.textBelowemailET);
                        textBelowbranchET = (TextView) findViewById(R.id.textBelowbranchET);
                        textBelowbankET = (TextView) findViewById(R.id.textBelowbankET);
                        textBelowifsccodeET = (TextView) findViewById(R.id.textBelowifsccodeET);
                        textBelowaccountnoET = (TextView) findViewById(R.id.textBelowaccountnoET);
                        textBelowreenterAccountnoET = (TextView) findViewById(R.id.textBelowreenterAccountnoET);


                        final int posss = spinnerAccount.getSelectedItemPosition();
                        if (bname.equals("")) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    textBelownameET.setVisibility(View.VISIBLE);
                                }
                            });
                        } else if (bemaill.equals("")) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelowemailET.setVisibility(View.VISIBLE);

                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        } else if (bemaill.matches(emailPattern)) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelowemailET.setVisibility(View.VISIBLE);

                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        } else if (branch.equals("")) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelowbranchET.setVisibility(View.VISIBLE);

                                    textBelowemailET.setVisibility(View.GONE);
                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        } else if (bankname.equals("")) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelowbankET.setVisibility(View.VISIBLE);

                                    textBelowbranchET.setVisibility(View.GONE);
                                    textBelowemailET.setVisibility(View.GONE);
                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        } else if (bifsccode.equals("")) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelowifsccodeET.setVisibility(View.VISIBLE);

                                    textBelowbankET.setVisibility(View.GONE);
                                    textBelowbranchET.setVisibility(View.GONE);
                                    textBelowemailET.setVisibility(View.GONE);
                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        } else if (baccount.equals("")) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelowaccountnoET.setVisibility(View.VISIBLE);

                                    textBelowifsccodeET.setVisibility(View.GONE);
                                    textBelowbankET.setVisibility(View.GONE);
                                    textBelowbranchET.setVisibility(View.GONE);
                                    textBelowemailET.setVisibility(View.GONE);
                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        } else if (breenteraccount.equals("")) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelowreenterAccountnoET.setVisibility(View.VISIBLE);

                                    textBelowaccountnoET.setVisibility(View.GONE);
                                    textBelowifsccodeET.setVisibility(View.GONE);
                                    textBelowbankET.setVisibility(View.GONE);
                                    textBelowbranchET.setVisibility(View.GONE);
                                    textBelowemailET.setVisibility(View.GONE);
                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        } else if (!baccount.equals(breenteraccount)) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Account Number Not Matching", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (posss == 0) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Select Account Type", Toast.LENGTH_SHORT).show();
                                    textBelowreenterAccountnoET.setVisibility(View.GONE);
                                    textBelowaccountnoET.setVisibility(View.GONE);
                                    textBelowifsccodeET.setVisibility(View.GONE);
                                    textBelowbankET.setVisibility(View.GONE);
                                    textBelowbranchET.setVisibility(View.GONE);
                                    textBelowemailET.setVisibility(View.GONE);
                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        } else if (bname.equals("") && bemaill.equals("") && branch.equals("") && bankname.equals("")
                                && bifsccode.equals("") && baccount.equals("") && breenteraccount.equals("") && posss == 0) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelownameET.setVisibility(View.VISIBLE);
                                    textBelowemailET.setVisibility(View.VISIBLE);
                                    textBelowbranchET.setVisibility(View.VISIBLE);
                                    textBelowbankET.setVisibility(View.VISIBLE);
                                    textBelowifsccodeET.setVisibility(View.VISIBLE);
                                    textBelowaccountnoET.setVisibility(View.VISIBLE);
                                    textBelowreenterAccountnoET.setVisibility(View.VISIBLE);
                                }
                            });
                        } else if (confirmCheckBox.isChecked()) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textAboveCheckBox.setVisibility(View.GONE);
                                }
                            });

                            LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                            paramsList.put("callmeOrderid", orderid);
                            paramsList.put("name", Uname);
                            paramsList.put("id", U_id);
                            paramsList.put("returnissueId", issue);
                            paramsList.put("issueexplain", issueExplain);
                            paramsList.put("returntype", returnTypeString);
                            paramsList.put("returnpaytype", first);
                            paramsList.put("returnpaytype", first);
                            paramsList.put("exchangecomments", newProduct);
                            paramsList.put("email", bemaill);
                            paramsList.put("returncodbankname", bankname);
                            paramsList.put("branchname", branch);
                            paramsList.put("returncodaccounttype", accountType);
                            paramsList.put("returncodcustname", bname);
                            paramsList.put("returncodbankifsccode", bifsccode);
                            paramsList.put("returncodbankifsccode", bifsccode);
                            paramsList.put("returncodaccountno", baccount);
                            paramsList.put("returnOrderid", orderid);
                            paramsList.put("StatusType", "Return");

                            paramsList.put("cartProdId", CartProductId);

                            result = Utility.httpPostRequestToServer(ApiConstants.FORMS_SUBMIT, Utility.getParams(paramsList));

                            JSONObject jsonobject = null;
                            try {
                                jsonobject = new JSONObject(result);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (jsonobject != null) {
                                Log.d("Create Response", jsonobject.toString());

                                try {
                                    int success = jsonobject.getInt("success");
                                    String message = jsonobject.getString("message");
                                    System.out.println("Retirn Status success" + success);
                                    if (success == 1) {
                                        // successfully created user
//                                    TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
//                                    t.setText(message);
//                                    toast.setView(toastRoot2);
//                                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
//                                    toast.setDuration(20000);
//                                    toast.show();
//                                    Intent i = new Intent(getApplicationContext(), MyOrder.class);
//                                    startActivity(i);
//                                    finish();


                                    } else {
                                        System.out.println("Cancel Status found");
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                            else {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        textAboveCheckBox.setVisibility(View.VISIBLE);
                                    }
                                });

                            }


                        } else if (confirmCheckBox.isChecked()) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textAboveCheckBox.setVisibility(View.GONE);
                                }
                            });
                            LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
//                                paramsList.put("name", name);
//                                paramsList.put("id", uid);
                            paramsList.put("returnissueId", issue);
                            paramsList.put("issueexplain", issueExplain);
                            paramsList.put("returntype", returnTypeString);
                            paramsList.put("returnpaytype", first);
                            paramsList.put("exchangecomments", newProduct);
                            paramsList.put("email", bemaill);
                            paramsList.put("returncodbankname", bankname);
                            paramsList.put("branchname", branch);
                            paramsList.put("returncodaccounttype", accountType);
                            paramsList.put("returncodcustname", bname);
                            paramsList.put("returncodbankifsccode", bifsccode);
                            paramsList.put("returncodaccountno", baccount);
                            paramsList.put("returnOrderid", orderid);
                            paramsList.put("StatusType", "Return");
                            paramsList.put("cartProdId", CartProductId);

                            result = Utility.httpPostRequestToServer(ApiConstants.FORMS_SUBMIT, Utility.getParams(paramsList));
                            JSONObject jsonobject = null;
                            try {
                                jsonobject = new JSONObject(result);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.d("Create Response", jsonobject.toString());
                            try {
                                int success = jsonobject.getInt("success");
                                String message = jsonobject.getString("message");
                                System.out.println("Return Status success" + success);
                                if (success == 1) {
                                    // successfully created user
//                                TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
//                                t.setText(message);
//                                toast.setView(toastRoot2);
//                                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
//                                toast.setDuration(20000);
//                                toast.show();
//                                Intent i = new Intent(getApplicationContext(), MyOrder.class);
//                                startActivity(i);
//                                finish();


                                } else {
                                    System.out.println("Cancel Status found");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textAboveCheckBox.setVisibility(View.VISIBLE);
                                }
                            });

                        }

                    } else if (confirmCheckBox.isChecked()) {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textAboveCheckBox.setVisibility(View.GONE);
                            }
                        });
                        LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                        paramsList.put("name", Uname);
                        paramsList.put("id", U_id);
                        paramsList.put("returnissueId", issue);
                        paramsList.put("issueexplain", issueExplain);
                        paramsList.put("returntype", returnTypeString);
                        paramsList.put("returnpaytype", first);
                        paramsList.put("exchangecomments", newProduct);
                        paramsList.put("email", bemaill);
                        paramsList.put("returncodbankname", bankname);
                        paramsList.put("branchname", branch);
                        paramsList.put("returncodaccounttype", accountType);
                        paramsList.put("returncodcustname", bname);
                        paramsList.put("returncodbankifsccode", bifsccode);
                        paramsList.put("returncodaccountno", baccount);
                        paramsList.put("returnOrderid", orderid);
                        paramsList.put("cartProdId", CartProductId);
                        paramsList.put("StatusType", "Return");
                        result = Utility.httpPostRequestToServer(ApiConstants.FORMS_SUBMIT, Utility.getParams(paramsList));
                        JSONObject jsonobject = null;
                        try {
                            jsonobject = new JSONObject(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Create Response", jsonobject.toString());

                        try {
                            int success = jsonobject.getInt("success");
                            String message = jsonobject.getString("message");
                            System.out.println("Retirn Status success" + success);
                            if (success == 1) {
                                // successfully created user
//                            TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
//                            t.setText(message);
//                            toast.setView(toastRoot2);
//                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
//                            toast.setDuration(20000);
//                            toast.show();
//                            Intent i = new Intent(getApplicationContext(), MyOrder.class);
//                            startActivity(i);
//                            finish();


                            } else {
                                System.out.println("Cancel Status found");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textAboveCheckBox.setVisibility(View.VISIBLE);

                            }
                        });
                    }

                } else if (confirmCheckBox.isChecked()) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            textAboveCheckBox.setVisibility(View.GONE);
                            textBelowissueET.setVisibility(View.GONE);
                        }
                    });
                    // Building Parameters
                    LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                    paramsList.put("name", Uname);
                    paramsList.put("id", U_id);
                    paramsList.put("returnissueId", issue);
                    paramsList.put("issueexplain", issueExplain);
                    paramsList.put("returntype", returnTypeString);
                    paramsList.put("returnpaytype", first);
                    paramsList.put("exchangecomments", newProduct);
                    paramsList.put("email", bemaill);
                    paramsList.put("returncodbankname", bankname);
                    paramsList.put("branchname", branch);
                    paramsList.put("returncodaccounttype", accountType);
                    paramsList.put("returncodcustname", bname);
                    paramsList.put("returncodbankifsccode", bifsccode);
                    paramsList.put("returncodaccountno", baccount);
                    paramsList.put("returnOrderid", orderid);
                paramsList.put("cartProdId", CartProductId);
                    paramsList.put("StatusType", "Return");
                    result = Utility.httpPostRequestToServer(ApiConstants.FORMS_SUBMIT, Utility.getParams(paramsList));
                    JSONObject jsonobject = null;
                    try {
                        jsonobject = new JSONObject(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("Create Response", jsonobject.toString());

                    try {
                        int success = jsonobject.getInt("success");
                        String message = jsonobject.getString("message");
                        System.out.println("Retirn Status success" + success);
                        if (success == 1) {
                            // successfully created user
//                        TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
//                        t.setText(message);
//                        toast.setView(toastRoot2);
//                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
//                        toast.setDuration(20000);
//                        toast.show();
//                        // successfully created user
//                        Intent i = new Intent(getApplicationContext(), MyOrder.class);
//                        startActivity(i);
//
//                        finish();


                        } else {
                            System.out.println("Cancel Status found");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        textAboveCheckBox.setVisibility(View.VISIBLE);
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

                mCustomProgressDialog.dismissProgress();
            }
        }

    public void onRadioButtonClicked(View v)
    {

        boolean  checked = ((RadioButton) v).isChecked();

        switch(v.getId()){

            case R.id.storeCash:
                if(checked)
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            confirmCheckBox.setVisibility(View.VISIBLE);
                            cancel.setVisibility(View.GONE);
                            confirm.setVisibility(View.VISIBLE);
                            relativeBank.setVisibility(View.GONE);
                        }});
                break;

            case R.id.bankAccount:
                if(checked)
                    System.out.println("payment"+payment);
                if(payment.equals("COD"))
                {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            if(issueexplainET.getText().toString().length()<15)
                            {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        textBelowissueET.setVisibility(View.VISIBLE);
//										returnTypespinner.setSelection(0);
                                        storeCash.setChecked(true);
                                    }});
                            }
                            else
                            {
                                relativeBank.setVisibility(View.VISIBLE);
                                cancel.setVisibility(View.VISIBLE);
                            }
                        }});
                }
                else
                {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            relativeBank.setVisibility(View.GONE);
                            cancel.setVisibility(View.GONE);
                        }});
                }

                break;
        }
    }


    class GetStatus extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetStatus() {
            mCustomProgressDialog = new CustomProgressDialog(ReturnFormNew.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(ReturnFormNew.this, R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                Utility.showLog("data", "datadata" + paramsList.toString());
                result = Utility.httpGetRequestToServer(ApiConstants.RETURN_REASONS);
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
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject != null) {
                        JSONArray jsonArrayReasons = jsonObject.getJSONArray("tbl_returnreasons");
                        ArrayList<String> spinnerdataList = new ArrayList<String>();

                        returnIssue = new ArrayList<String>();
                        int lengthJsonArr = jsonArrayReasons.length();
                        returnIssue.add(0, "Select issue");
                        for (int i = 0; i < lengthJsonArr; i++) {
                            JSONObject json2 = jsonArrayReasons.getJSONObject(i);
                            String reason_name = json2.getString("Status");
                            spinnerdataList.add(json2.getString("Status"));
                            System.out.println("reason_name" + reason_name);

                            returnIssue.add(reason_name);
                        }
                    }
                }
                issueSpinner.setAdapter(new ArrayAdapter<String>(ReturnFormNew.this,
                        android.R.layout.simple_spinner_dropdown_item, returnIssue));
                mCustomProgressDialog.dismissProgress();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class GetReturnType extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetReturnType() {
            mCustomProgressDialog = new CustomProgressDialog(ReturnFormNew.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(ReturnFormNew.this, R.string.please_wait));
        }

        protected String doInBackground(String... params) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                Utility.showLog("data", "datadata" + paramsList.toString());
                result = Utility.httpGetRequestToServer(ApiConstants.RETURN_TYPE);
                JSONObject jsonObject = new JSONObject(result);

                JSONArray user = jsonObject.getJSONArray("tbl_returntype");
                ArrayList<String> spinnerdataList = new ArrayList<String>();

                returnType = new ArrayList<String>();
                int lengthJsonArr = user.length();
                returnType.add(0, "Select ReturnType");
                for (int i = 0; i < lengthJsonArr; i++) {
                    JSONObject json2 = user.getJSONObject(i);
                    String reason_name = json2.getString("Status");
                    spinnerdataList.add(json2.getString("Status"));
                    System.out.println("reason_name" + reason_name);

                    returnType.add(reason_name);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // Spinner adapter
            returnTypespinner.setAdapter(new ArrayAdapter<String>(ReturnFormNew.this,
                    android.R.layout.simple_spinner_dropdown_item, returnType));
            mCustomProgressDialog.dismissProgress();
        }
    }


}