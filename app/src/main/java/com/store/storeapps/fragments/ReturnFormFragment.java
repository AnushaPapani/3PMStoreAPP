package com.store.storeapps.fragments;

/**
 * Created by satyanarayana pdv on 10/11/2016.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.store.storeapps.R;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class ReturnFormFragment extends Fragment {

    public static final String TAG = "ReturnFormFragment";
    private View rootView;
    Button btn_submit, cancel, confirm;
    String orderid, cartProdId, orderpcost, bemail, payment;
    TextView textAboveCheckBox;
    TextView textBelowissueET;
    String returnorder = "Order Return Request";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.+[a-zA-Z]+";
    EditText issueexplainET, email, newproductET, nameET, banknameET,
            ifsccodeET, accountnoET, reenterAccountnoET, branchET;
    View relativeBank, relativeissue, returnTypeRelative;
    CheckBox confirmCheckBox;
    RadioGroup rg;
    int rgpos;
    RadioButton storeCash, bankDetails, rgbutton;
    ArrayList<String> returnIssue, returnType;
    Spinner issueSpinner, returnTypespinner, spinnerAccount;

    int pos, poss;
    String issue, returnTypeString, accountType, issueExplain, newProduct, bname, bemaill, branch, id, name,
            bankname, bifsccode, baccount, breenteraccount, first, radiodata, Uname, U_id, PaymentType, cartId,total;

    TextView textBelownameET, textBelowemailET, textBelowbranchET, textBelowbankET,
            textBelowifsccodeET, textBelowaccountnoET, textBelowreenterAccountnoET,odrid,cost;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.returnsform, container, false);
        orderid = MyOrderFragment.orderID;
        cartProdId = MyOrderFragment.CartPID;
        cartId = MyOrderFragment.cartID;
        Uname = MyOrderFragment.USername;
        U_id = MyOrderFragment.Uid;
        payment = MyOrderFragment.PaymentType;
        total = MyOrderFragment.GTOTAL;

        name = Utility.getSharedPrefStringData(getActivity(), Constants.USER_NAME);
        id = Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID);
        System.out.println("cartProdId   " + cartProdId);
        System.out.println("cartId    " + cartId);

        relativeBank = (RelativeLayout) rootView.findViewById(R.id.relativeBank);
        relativeissue = (RelativeLayout) rootView.findViewById(R.id.relative5);
        returnTypeRelative = (RelativeLayout) rootView.findViewById(R.id.relative3);
        TextView textorder = (TextView) rootView.findViewById(R.id.textView1);
        SpannableString content2 = new SpannableString(returnorder);
        content2.setSpan(new UnderlineSpan(), 0, returnorder.length(), 0);
        textorder.setText(content2);

        issueSpinner = (Spinner) rootView.findViewById(R.id.returnIssueSpinner);
        returnTypespinner = (Spinner) rootView.findViewById(R.id.spinnerreturntype);
        spinnerAccount = (Spinner) rootView.findViewById(R.id.spinnerAccount);
        issueexplainET = (EditText) rootView.findViewById(R.id.issueExplainET);
        newproductET = (EditText) rootView.findViewById(R.id.newProductET);
        nameET = (EditText) rootView.findViewById(R.id.nameET);
        banknameET = (EditText) rootView.findViewById(R.id.banknameET);
        ifsccodeET = (EditText) rootView.findViewById(R.id.ifsccodeET);
        accountnoET = (EditText) rootView.findViewById(R.id.accountnoET);
        reenterAccountnoET = (EditText) rootView.findViewById(R.id.reenterAccountnoET);
        branchET = (EditText) rootView.findViewById(R.id.branchET);
        textAboveCheckBox = (TextView) rootView.findViewById(R.id.textAboveCheckBox);
        odrid = (TextView)rootView.findViewById(R.id.order);
        cost =(TextView)rootView.findViewById(R.id.total);

        odrid.setText(orderid);
        cost.setText(total);

        textBelownameET = (TextView) rootView.findViewById(R.id.textBelownameET);
        textBelowemailET = (TextView) rootView.findViewById(R.id.textBelowemailET);
        textBelowbranchET = (TextView) rootView.findViewById(R.id.textBelowbranchET);
        textBelowbankET = (TextView) rootView.findViewById(R.id.textBelowbankET);
        textBelowifsccodeET = (TextView) rootView.findViewById(R.id.textBelowifsccodeET);
        textBelowaccountnoET = (TextView) rootView.findViewById(R.id.textBelowaccountnoET);
        textBelowreenterAccountnoET = (TextView) rootView.findViewById(R.id.textBelowreenterAccountnoET);

        storeCash = (RadioButton) rootView.findViewById(R.id.storeCash);
//        Typeface font = Typeface.createFromAsset(getAssets(), "myriadprobold.otf");
//        storeCash.setTypeface(font);
        bankDetails = (RadioButton) rootView.findViewById(R.id.bankAccount);
//        Typeface font2 = Typeface.createFromAsset(getAssets(), "myriadprobold.otf");
//        bankDetails.setTypeface(font2);
        rg = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        confirmCheckBox = (CheckBox) rootView.findViewById(R.id.confirmCheckBox);
//        Typeface font3 = Typeface.createFromAsset(getAssets(), "myriadprobold.otf");
//        confirmCheckBox.setTypeface(font3);

        cancel = (Button) rootView.findViewById(R.id.cancel);
        confirm = (Button) rootView.findViewById(R.id.confirm);
        TextView odrid = (TextView) rootView.findViewById(R.id.order);
        TextView cost = (TextView) rootView.findViewById(R.id.total);
        textBelowissueET = (TextView) rootView.findViewById(R.id.textBelowissueET);
        email = (EditText) rootView.findViewById(R.id.emailET);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        issueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                final int issueposition = issueSpinner.getSelectedItemPosition();
                System.out.println("issueposition" + issueposition);

                if (issueposition == 0) {
                    relativeissue.setVisibility(View.GONE);
                    returnTypeRelative.setVisibility(View.GONE);
                    rg.setVisibility(View.GONE);
                    confirmCheckBox.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    textBelowissueET.setVisibility(View.GONE);
                } else {
                    relativeissue.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        returnTypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                final int returnposition = returnTypespinner.getSelectedItemPosition();
                System.out.println("returnposition" + returnposition);

                if (returnposition == 0) {
                    returnTypeRelative.setVisibility(View.GONE);
                    rg.setVisibility(View.GONE);
                    confirmCheckBox.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    relativeBank.setVisibility(View.GONE);
                    textAboveCheckBox.setVisibility(View.GONE);
                } else if (returnposition == 1) {
                    Log.e("Position 1", "one ");
                    returnTypeRelative.setVisibility(View.VISIBLE);
                    confirmCheckBox.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    confirm.setVisibility(View.VISIBLE);
                    rg.setVisibility(View.GONE);
                    relativeBank.setVisibility(View.GONE);
                } else if (returnposition == 2) {
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

        new GetReturnType().execute();
        new GetStatus().execute();
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ReturnFormAsyncTask().execute();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final int returnposition2 = returnTypespinner.getSelectedItemPosition();
                System.out.println("returnposition" + returnposition2);
                textAboveCheckBox.setVisibility(View.GONE);
                if (returnposition2 == 1) {
                    returnTypeRelative.setVisibility(View.GONE);
                    rg.setVisibility(View.GONE);
                    confirmCheckBox.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    relativeBank.setVisibility(View.GONE);
                    returnTypespinner.setSelection(0);
                    textBelowissueET.setVisibility(View.GONE);
                } else {
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

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                        switch (checkedId) {

                            case R.id.storeCash:
                                System.out.println("payment store cash" );

//                                    getActivity().runOnUiThread(new Runnable() {
//
//                                        @Override
//                                        public void run() {
                                            confirmCheckBox.setVisibility(View.VISIBLE);
                                            cancel.setVisibility(View.GONE);
                                            confirm.setVisibility(View.VISIBLE);
                                            relativeBank.setVisibility(View.GONE);
//                                        }
//                                    });
                                break;

                            case R.id.bankAccount:
//                                    System.out.println("payment" + payment);
                                System.out.println("payment bank account" );

                                if (payment.equals("COD")) {
//                                    getActivity().runOnUiThread(new Runnable() {
//
//                                        @Override
//                                        public void run() {

                                            if (issueexplainET.getText().toString().length() < 15) {
//                                                getActivity().runOnUiThread(new Runnable() {
//
//                                                    @Override
//                                                    public void run() {
                                                        textBelowissueET.setVisibility(View.VISIBLE);
//										returnTypespinner.setSelection(0);
                                                        storeCash.setChecked(true);
//                                                    }
//                                                });
                                            } else {
                                                relativeBank.setVisibility(View.VISIBLE);
                                                cancel.setVisibility(View.VISIBLE);
                                            }
//                                        }
//                                    });
                                } else {
//                                    getActivity().runOnUiThread(new Runnable() {
//
//                                        @Override
//                                        public void run() {
                                            relativeBank.setVisibility(View.GONE);
                                            cancel.setVisibility(View.GONE);
//                                        }
//                                    });
                                }

                                break;
                        }

            }

        });


        initUI();
        return rootView;
    }
    private void initUI() {
    }

    class ReturnFormAsyncTask extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public ReturnFormAsyncTask() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            int selectedId = rg.getCheckedRadioButtonId();
            rgbutton = (RadioButton) rootView.findViewById(selectedId);
            //            Toast.makeText(MainActivity.this,radioSexButton.getText(),Toast.LENGTH_SHORT).show();
            radiodata = rgbutton.getText().toString();
            System.out.println("orderid radiodata" + radiodata);

            StringTokenizer tokens = new StringTokenizer(radiodata, "(");
            first = tokens.nextToken();
            System.out.println("orderid radiodata" + first);
            pos = issueSpinner.getSelectedItemPosition();

            issue = issueSpinner.getSelectedItem().toString();
            System.out.println("isssssssssssssssssssssssssssss" + issue);
            poss = returnTypespinner.getSelectedItemPosition();
            returnTypeString = returnTypespinner.getSelectedItem().toString();
            accountType = spinnerAccount.getSelectedItem().toString();

            System.out.println("orderid accountType" + accountType);
            System.out.println("orderid returnType oldddd" + returnType);
            System.out.println("orderid returnTypeString" + returnTypeString);
            System.out.println("orderid issueid position" + issue);
            issueExplain = issueexplainET.getText().toString();
            newProduct = newproductET.getText().toString();
            bname = nameET.getText().toString();
            bemaill = email.getText().toString();
            branch = branchET.getText().toString();
            bankname = banknameET.getText().toString();
            bifsccode = ifsccodeET.getText().toString();
            baccount = accountnoET.getText().toString();
            breenteraccount = reenterAccountnoET.getText().toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;

            int selectedId = rg.getCheckedRadioButtonId();
            rgbutton = (RadioButton) rootView.findViewById(selectedId);
            //            Toast.makeText(MainActivity.this,radioSexButton.getText(),Toast.LENGTH_SHORT).show();
            final String radiodata = rgbutton.getText().toString();
            System.out.println("orderid radiodata" + radiodata);

            StringTokenizer tokens = new StringTokenizer(radiodata, "(");
            final String first = tokens.nextToken();
            System.out.println("orderid radiodata" + first);
            final int pos = issueSpinner.getSelectedItemPosition();

            final String issue = issueSpinner.getSelectedItem().toString();
            final int poss = returnTypespinner.getSelectedItemPosition();
            final String returnType = returnTypespinner.getSelectedItem().toString();
            final String accountType = spinnerAccount.getSelectedItem().toString();

            System.out.println("orderid accountType" + accountType);
            System.out.println("orderid returnType" + returnType);
            System.out.println("orderid issueid position" + issue);
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
            System.out.println("orderid" + orderid);
            System.out.println("orderid" + U_id);
            System.out.println("orderid" + Uname);

            System.out.println("orderid" + issueExplain);
            System.out.println("orderid" + newProduct);
            System.out.println("orderid" + bname);
            System.out.println("email" + bemaill);
            System.out.println("orderid" + branch);
            System.out.println("orderid" + bankname);
            System.out.println("orderid" + bifsccode);
            System.out.println("orderid" + baccount);
            System.out.println("orderid accountType" + accountType);
            System.out.println("orderid returnType" + returnType);
            System.out.println("orderid issueid position" + issue);
            System.out.println("orderid payment" + payment);

            if (issueExplain.length() < 15) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        textBelowissueET.setVisibility(View.VISIBLE);
                        storeCash.setChecked(true);
                        bankDetails.setChecked(false);
                        relativeBank.setVisibility(View.GONE);
                    }
                });
            } else if (payment.equals("COD")) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        textBelowissueET.setVisibility(View.GONE);

                    }
                });
                final int returnposition3 = returnTypespinner.getSelectedItemPosition();
                System.out.println("returnposition" + returnposition3);

                if (returnposition3 == 2) {
                    if (first.equals("To My Bank Account")) {

                        final int posss = spinnerAccount.getSelectedItemPosition();
                        if (bname.equals("")) {
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    textBelownameET.setVisibility(View.VISIBLE);
                                }
                            });
                        } else if (bemaill.equals("")) {
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelowemailET.setVisibility(View.VISIBLE);

                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        }
//                        else if (bemaill.matches(emailPattern)) {
//                            getActivity().runOnUiThread(new Runnable() {
//
//                                @Override
//                                public void run() {
//                                    textBelowemailET.setVisibility(View.VISIBLE);
//
//                                    textBelownameET.setVisibility(View.GONE);
//                                }
//                            });
//                        }
                    else if (branch.equals("")) {
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelowbranchET.setVisibility(View.VISIBLE);

                                    textBelowemailET.setVisibility(View.GONE);
                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        } else if (bankname.equals("")) {
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textBelowbankET.setVisibility(View.VISIBLE);

                                    textBelowbranchET.setVisibility(View.GONE);
                                    textBelowemailET.setVisibility(View.GONE);
                                    textBelownameET.setVisibility(View.GONE);
                                }
                            });
                        } else if (bifsccode.equals("")) {
                            getActivity().runOnUiThread(new Runnable() {

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
                            getActivity().runOnUiThread(new Runnable() {

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
                            getActivity().runOnUiThread(new Runnable() {

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
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                            Toast.makeText(getActivity(), "Account Number Not Matching", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (posss == 0) {
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Select Account Type", Toast.LENGTH_SHORT).show();
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
                            getActivity().runOnUiThread(new Runnable() {

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
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textAboveCheckBox.setVisibility(View.GONE);
                                }
                            });

                            LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                            paramsList.put("name", name);
                            paramsList.put("id", id);
                            paramsList.put("returnissueId", issue);
                            paramsList.put("issueexplain", issueExplain);
                            paramsList.put("returntype", returnType);
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
                            paramsList.put("cartId", cartId);
                            paramsList.put("cartProdId", cartProdId);

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

                                        Utility.navigateDashBoardFragment(new MyOrderFragment(), MyOrderFragment.TAG, null, getActivity());

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
                        } else {
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textAboveCheckBox.setVisibility(View.VISIBLE);
                                }
                            });

                        }


                    } else if (confirmCheckBox.isChecked()) {
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textAboveCheckBox.setVisibility(View.GONE);
                            }
                        });
                        LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                        paramsList.put("name", name);
                        paramsList.put("id", id);
                        paramsList.put("returnissueId", issue);
                        paramsList.put("issueexplain", issueExplain);
                        paramsList.put("returntype", returnType);
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
                        paramsList.put("cartId", cartId);
                        paramsList.put("cartProdId", cartProdId);

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
                                Utility.navigateDashBoardFragment(new MyOrderFragment(), MyOrderFragment.TAG, null, getActivity());
                            } else {
                                Utility.showToastMessage(getActivity(), "form details not inserted");
                            }
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
//                            } else {
//                                System.out.println("Cancel Status found");
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textAboveCheckBox.setVisibility(View.VISIBLE);
                            }
                        });

                    }

                } else if (confirmCheckBox.isChecked()) {
                    getActivity().runOnUiThread(new Runnable() {

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
                    paramsList.put("returntype", returnType);
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
                    paramsList.put("cartId", cartId);
                    paramsList.put("cartProdId", cartProdId);
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
                            Utility.navigateDashBoardFragment(new MyOrderFragment(), MyOrderFragment.TAG, null, getActivity());
                        } else {
                            Utility.showToastMessage(getActivity(), "form details not inserted");
                        }

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
//                        } else {
//                            System.out.println("Cancel Status found");
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            textAboveCheckBox.setVisibility(View.VISIBLE);

                        }
                    });
                }


            } else if (confirmCheckBox.isChecked())

            {
                getActivity().runOnUiThread(new Runnable() {

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
                paramsList.put("returntype", returnType);
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
                paramsList.put("cartProdId", cartProdId);
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
//                    if (success == 1) {
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
                    if (success == 1) {
                        Utility.navigateDashBoardFragment(new MyOrderFragment(), MyOrderFragment.TAG, null, getActivity());
                    } else {
                        Utility.showToastMessage(getActivity(), "form details not inserted");
                    }


//                    } else {
//                        System.out.println("Cancel Status found");
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else

            {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        textAboveCheckBox.setVisibility(View.VISIBLE);
                    }
                });
            }
            return null;
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject != null) {
                        JSONObject jObj = new JSONObject(response);
                        String success = jObj.getString("success");
                        String message = jObj.getString("message");
                        System.out.println("Call me form Details " + success + " " + message);
                        if (success.equals("1")) {
                            Utility.navigateDashBoardFragment(new MyOrderFragment(), MyOrderFragment.TAG, null, getActivity());
                        } else {
                            Utility.showToastMessage(getActivity(), "form details not inserted");
                        }
                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class GetStatus extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public GetStatus() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
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
                issueSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
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
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
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
            returnTypespinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, returnType));
            mCustomProgressDialog.dismissProgress();
        }
    }
}
