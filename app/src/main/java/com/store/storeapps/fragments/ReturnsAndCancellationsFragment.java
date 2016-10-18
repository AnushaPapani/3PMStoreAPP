package com.store.storeapps.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.store.storeapps.R;
import com.store.storeapps.activities.ExpandableListDataPump;
import com.store.storeapps.activities.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Anusha
 */
public class ReturnsAndCancellationsFragment extends Fragment {
    public static final String TAG = "ReturnsAndCancellationsFragment";
    private View rootView;
    ArrayList<String> arrayList = new ArrayList<String>();
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private ProgressDialog pDialog;
    private int currentHourPosition = -1; // initial value
    SharedPreferences settings;
    String[][] data = {{""}, {""}, {""}, {""}, {""}, {""}, {""}};
    String[][] data2 = {{""}, {""}, {""}, {""}};
    TextView pmrp, pcost, shortdesc, textCounter, head, thour, tvHour, tminutes, tvMinute, tvSecond, s, info, descrip;
    private static final String FORMAT = "%02d:%02d:%02d";
    private CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off
    int seconds, minutes;
    String Returns = "Returns And Cancellations";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.returnsandcancellations, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        TextView returns = (TextView) rootView.findViewById(R.id.textView2);
        SpannableString content = new SpannableString(Returns);
        content.setSpan(new UnderlineSpan(), 0, returns.length(), 0);
        returns.setText(content);
        String k = "gdfgdf";
        final ExpandableListView expand = (ExpandableListView) rootView.findViewById(R.id.expandableListView1);
        final ExpandableListView expand2 = (ExpandableListView) rootView.findViewById(R.id.expandableListView2);

        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        //		expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expand.setAdapter(new SampleExpandableListAdapter(getActivity(), data));
        expand2.setAdapter(new SampleExpandableListAdapter2(getActivity(), data2));

        expand.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO Auto-generated method stub

                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                    // Do your Staff
                } else if (parent.collapseGroup(groupPosition)) {
                    parent.expandGroup(groupPosition);
                    //					parent.expandGroup(groupPosition);
                    //					expandableListTitle.get(groupPosition);
                }
                return false;
            }
        });

        expand.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                // TODO Auto-generated method stub
                if (currentHourPosition != -1 && currentHourPosition !=
                        groupPosition) {
                    expand.collapseGroup(currentHourPosition);
                }
                currentHourPosition = groupPosition;
            }
        });
        expand2.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO Auto-generated method stub

                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                    // Do your Staff
                } else if (parent.collapseGroup(groupPosition)) {
                    parent.expandGroup(groupPosition);
                    //					parent.expandGroup(groupPosition);
                    //					expandableListTitle.get(groupPosition);
                }
                return false;
            }
        });

        expand2.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                // TODO Auto-generated method stub
                if (currentHourPosition != -1 && currentHourPosition !=
                        groupPosition) {
                    expand.collapseGroup(currentHourPosition);
                }
                currentHourPosition = groupPosition;
            }
        });

        expand.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    Toast.makeText(getActivity(), "", 9000).show();
                    //					Intent i =new Intent(DeliveryfaqActivity.this,InitialScreenActivity.class);
                    //					startActivity(i);
                    //					finish();
                } else if (groupPosition == 1) {
                    //					new Hurray().execute();
                    //					Intent mobikwikIntent = new Intent(getApplicationContext(),MobikwikSDK.class);
                    //					startActivity(mobikwikIntent);
                    //							mobikwikIntent.putExtra(MobikwikSDK, value)
                    //							mobikwikIntent.putExtra(MobikwikSDK. EXTRA_TRANSACTION_CONFIG , config);
                    //							mobikwikIntent.putExtra(MobikwikSDK. EXTRA_TRANSACTION , newTransaction);
                    //							startActivityForResult(mobikwikIntent, REQ_CODE );
                    //					Intent i =new Intent(PaymentOption.this,PaymentActivity.class);
                    //					startActivity(i);
                    Toast.makeText(getActivity(), "", 9000).show();
                } else if (groupPosition == 2) {

                } else if (groupPosition == 3) {
                    Toast.makeText(getActivity(), "", 9000).show();
                    //					Intent i =new Intent(PaymentOption.this,Payumoney.class);
                    //					startActivity(i);
                    //					finish();
                } else if (groupPosition == 4) {
                    Toast.makeText(getActivity(), "", 9000).show();
                }
                return false;
            }

        });

    }
}

class SampleExpandableListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {
    public Context context;
    CheckBox checkBox;
    private LayoutInflater vi;
    private String[][] data;
    int _objInt;
    Boolean checked[] = new Boolean[1];

    HashMap<Long, Boolean> checkboxMap = new HashMap<Long, Boolean>();
    private final int GROUP_ITEM_RESOURCE = R.layout.groupfaq_item;
    private final int CHILD_ITEM_RESOURCE = R.layout.childfaq_item;
    public String[] check_string_array;

    public SampleExpandableListAdapter(Context context, String[][] data) {
        this.data = data;
        this.context = context;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        _objInt = data.length;
        check_string_array = new String[_objInt];
        popolaCheckMap();
    }

    public void popolaCheckMap() {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String buffer = null;

        for (int i = 0; i < _objInt; i++) {
            buffer = settings.getString(String.valueOf((int) i), "false");
            if (buffer.equals("false"))
                checkboxMap.put((long) i, false);
            else checkboxMap.put((long) i, true);
        }
    }

    public String getChild(int groupPosition, int childPosition) {
        return data[groupPosition][childPosition];
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return data[groupPosition].length;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        String child = getChild(groupPosition, childPosition);
        int id_res = 0;

        if (child != null) {
            v = vi.inflate(CHILD_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);
        }
        if (groupPosition == 0) {
            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. Raise a cancellation request in your ‘My Orders’ Tab. We can cancel your order, only before we dispatch it. ");
            ViewHolder holder = new ViewHolder(v);
        }
        if (groupPosition == 1) {
            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. Raise a return request in your ‘My Orders’ tab and after we look into the reason for return abiding our return policy, we will pick it up from you at absolutely Zero cost.");
            ViewHolder holder = new ViewHolder(v);
        }
        if (groupPosition == 2) {
            //				codChargesValue.setText("COD Charges: "+Codcash);
            //				amounttotal.setText(""+(Integer.parseInt(globalVariable.getAmount().toString())+Integer.parseInt(Codcash)));
            //				globalVariable.setFinalcodamounttotal(amounttotal.getText().toString());
            //				codCharges.setVisibility(View.VISIBLE);
            //				codChargesValue.setVisibility(View.VISIBLE);
            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. You can return the product within 14 days after the date of delivery.");
            ViewHolder holder = new ViewHolder(v);
            //				otp =(EditText)v.findViewById(R.id.editText1);
            //				otp.setVisibility(View.GONE);
            //				codCharges.setVisibility(View.GONE);
        }
        if (groupPosition == 3) {
            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. You can avail it in the form of either 3PMstore cash or money will be refunded to your bank account that you have used to make the payment.");
            ViewHolder holder = new ViewHolder(v);
            //				v = vi.inflate(R.layout.childfaq_item, null);
            //				v.setBackgroundResource(R.drawable.borders);
            //				//				id_res = R.id.image1;
            //				final TextView faq1 =(TextView)v.findViewById(R.id.faq);
            //				faq1.setText("Standard :5-9 days");
            //				ViewHolder holder = new ViewHolder(v);
        }
        if (groupPosition == 4) {
            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. Once it is picked up from you, the money will be refunded  to your 3PMstore cash within 24-48hours. If you choose your refund to bank account, then you can expect within 7-14 days  after the pick up has been made. ");
            ViewHolder holder = new ViewHolder(v);
            //				v = vi.inflate(R.layout.childfaq_item, null);
            //				v.setBackgroundResource(R.drawable.borders);
            //				//				id_res = R.id.image1;
            //				final TextView faq1 =(TextView)v.findViewById(R.id.faq);
            //				faq1.setText("Standard :5-9 days");
            //				ViewHolder holder = new ViewHolder(v);
        }
        if (groupPosition == 5) {
            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. Raise an exchange request in your ‘My Orders’ Tab and after we look into the reason for exchange abiding our return policy, we will pick it up from you. Once the pickup has been done then we will dispatch the variant you requested for." +
                    "P.S : You can only exchange under the same category of product that you ordered for. Our exchange policy does not allow exchange between different products.");
            ViewHolder holder = new ViewHolder(v);
        }

        if (groupPosition == 6) {
            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. After the product is picked up from you, a new product with the variant you requested for, will be dispatched in the next 2 business days after the pick-up.");
            ViewHolder holder = new ViewHolder(v);
        }
        return v;
    }

    public String getGroup(int groupPosition) {
        return "group-" + groupPosition;
    }

    public int getGroupCount() {
        return data.length;
    }


    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        String group = null;
        int id_res = 0;
        long group_id = getGroupId(groupPosition);
        //			group = "COD"+context.getString(R.string.rs)+55 +" extra";
        if (group_id == 0) {
            group = "<u>1. How do I cancel my Order?</u>";
            //	        	id_res = R.drawable.audi;
        } else if (group_id == 1) {
            group = "<u>2. What is the return process?</u>";
            //				id_res = R.drawable.paytm;
        } else if (group_id == 2) {
            group = "<u>3. When can I return the Product?</u>";
            //				id_res = R.drawable.mobi;
        } else if (group_id == 3) {
            group = "<u>4. Once I return the product, How is my money refunded?</u>";
            //				id_res = R.drawable.payumoney;
        } else if (group_id == 4) {
            group = "<u>How many days will it take for the refund?</u>";
            //				id_res = R.drawable.mobi;
        } else if (group_id == 5) {
            group = "<u>5. like to know about exchanging my product?</u>";
            //				id_res = R.drawable.mobi;
        } else if (group_id == 6) {
            group = "<u>6. How many days would it take for the product to be exchanged?</u>";
            //				id_res = R.drawable.mobi;
        }

        if (group != null) {
            v = vi.inflate(GROUP_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);

            holder.text.setText(Html.fromHtml(group));
            holder.imageview.setImageResource(id_res);
        }
        return v;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public boolean hasStableIds() {
        return true;
    }
}

class SampleExpandableListAdapter2 extends BaseExpandableListAdapter implements ExpandableListAdapter {
    public Context context;
    CheckBox checkBox;
    private LayoutInflater vi;
    private String[][] data;
    int _objInt;
    Boolean checked[] = new Boolean[1];

    HashMap<Long, Boolean> checkboxMap = new HashMap<Long, Boolean>();
    private final int GROUP_ITEM_RESOURCE = R.layout.groupfaq_item;
    private final int CHILD_ITEM_RESOURCE = R.layout.childfaq_item;
    public String[] check_string_array;

    public SampleExpandableListAdapter2(Context context, String[][] data) {
        this.data = data;
        this.context = context;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        _objInt = data.length;
        check_string_array = new String[_objInt];
        popolaCheckMap();
    }

    public void popolaCheckMap() {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String buffer = null;

        for (int i = 0; i < _objInt; i++) {
            buffer = settings.getString(String.valueOf((int) i), "false");
            if (buffer.equals("false"))
                checkboxMap.put((long) i, false);
            else checkboxMap.put((long) i, true);
        }
    }

    public String getChild(int groupPosition, int childPosition) {
        return data[groupPosition][childPosition];
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return data[groupPosition].length;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        String child = getChild(groupPosition, childPosition);
        int id_res = 0;

        if (child != null) {
            v = vi.inflate(CHILD_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);
        }
        if (groupPosition == 0) {
            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. The Standard delivery would generally take about 5-9 days. But depending on your location, it might reach you even earlier!");
            ViewHolder holder = new ViewHolder(v);
        }
        if (groupPosition == 1) {
            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. Please check the 'My orders' tab on the website/app. Your tracking ID is visible right next to the order ID." +
                    "Also, once your order is dispatched, you will receive a mail with the tracking ID. ");
            ViewHolder holder = new ViewHolder(v);
        }
        if (groupPosition == 2) {

            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. You can track the order in your ‘My Orders’ tab ");
            ViewHolder holder = new ViewHolder(v);
            //				otp =(EditText)v.findViewById(R.id.editText1);
            //				otp.setVisibility(View.GONE);
            //				codCharges.setVisibility(View.GONE);
        }
        if (groupPosition == 3) {
            v = vi.inflate(R.layout.childfaq_item, null);
            v.setBackgroundResource(R.drawable.borders);
            //				id_res = R.id.image1;
            final TextView faq1 = (TextView) v.findViewById(R.id.faq);
            faq1.setText("A. We try to make you happy with our service, but few things are dependent on our delivery partner. All we promise is delivery within 5-9 days.");
            ViewHolder holder = new ViewHolder(v);

        }

        return v;
    }

    public String getGroup(int groupPosition) {
        return "group-" + groupPosition;
    }

    public int getGroupCount() {
        return data.length;
    }


    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        String group = null;
        int id_res = 0;
        long group_id = getGroupId(groupPosition);
        //			group = "COD"+context.getString(R.string.rs)+55 +" extra";
        if (group_id == 0) {
            group = "<u>1. Hey! when can I expect my order?</u>";
            //	        	id_res = R.drawable.audi;
        } else if (group_id == 1) {
            group = "<u>2. So, where do I find my tracking ID?</u>";
            //				id_res = R.drawable.paytm;
        } else if (group_id == 2) {
            group = "<u>3. Where do I track my order?</u>";
            //				id_res = R.drawable.mobi;
        } else if (group_id == 3) {
            group = "<u>4. Can you deliver an order on a particular day if I request you to?</u>";
            //				id_res = R.drawable.payumoney;
        }

        if (group != null) {
            v = vi.inflate(GROUP_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);

            holder.text.setText(Html.fromHtml(group));
            holder.imageview.setImageResource(id_res);
        }
        return v;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public boolean hasStableIds() {
        return true;
    }
}


