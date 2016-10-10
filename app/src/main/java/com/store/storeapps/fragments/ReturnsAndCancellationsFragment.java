package com.store.storeapps.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.store.storeapps.R;

import java.util.ArrayList;

/**
Created by Anusha
 */
public class ReturnsAndCancellationsFragment extends Fragment {
    public static final String TAG = "ReturnsAndCancellationsFragment";
    private View rootView;
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
//        final ExpandableListView expand =(ExpandableListView) rootView.findViewById(R.id.expandableListView1);
//
//        expandableListDetail = ExpandableListDataPump.getData();
//        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
//        //		expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail);
//        expand.setAdapter(new SampleExpandableListAdapter(context, this,data ));
//
//        expand.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                // TODO Auto-generated method stub
//
//                if(parent.isGroupExpanded(groupPosition))
//                {
//                    parent.collapseGroup(groupPosition);
//                    // Do your Staff
//                }else if(parent.collapseGroup(groupPosition)){
//                    parent.expandGroup(groupPosition);
//                    //					parent.expandGroup(groupPosition);
//                    //					expandableListTitle.get(groupPosition);
//                }
//                return false;
//            }
//        });
//
//        expand.setOnGroupExpandListener(new OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                // TODO Auto-generated method stub
//                if(currentHourPosition != -1 && currentHourPosition !=
//                        groupPosition){
//                    expand.collapseGroup(currentHourPosition);
//                }
//                currentHourPosition = groupPosition;
//            }
//        });
//
//
//        expand.setOnChildClickListener(new OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                if (groupPosition == 0) {
//                    Toast.makeText(getApplicationContext(), "", 9000).show();
//                    //					Intent i =new Intent(DeliveryfaqActivity.this,InitialScreenActivity.class);
//                    //					startActivity(i);
//                    //					finish();
//                }else if (groupPosition == 1) {
//                    //					new Hurray().execute();
//                    //					Intent mobikwikIntent = new Intent(getApplicationContext(),MobikwikSDK.class);
//                    //					startActivity(mobikwikIntent);
//                    //							mobikwikIntent.putExtra(MobikwikSDK, value)
//                    //							mobikwikIntent.putExtra(MobikwikSDK. EXTRA_TRANSACTION_CONFIG , config);
//                    //							mobikwikIntent.putExtra(MobikwikSDK. EXTRA_TRANSACTION , newTransaction);
//                    //							startActivityForResult(mobikwikIntent, REQ_CODE );
//                    //					Intent i =new Intent(PaymentOption.this,PaymentActivity.class);
//                    //					startActivity(i);
//                    Toast.makeText(getApplicationContext(), "", 9000).show();
//                }else if (groupPosition == 2) {
//
//                }else if (groupPosition == 3) {
//                    Toast.makeText(getApplicationContext(), "", 9000).show();
//                    //					Intent i =new Intent(PaymentOption.this,Payumoney.class);
//                    //					startActivity(i);
//                    //					finish();
//                }else if (groupPosition == 4) {
//                    Toast.makeText(getApplicationContext(), "", 9000).show();
//                }
//                return false;
//            }
//
//        });
//    }
    }
}

//class SampleExpandableListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter  {
//    public Context context;
//    CheckBox checkBox;
//    private LayoutInflater vi;
//    private String[][] data;
//    int _objInt;
//    Boolean checked[] = new Boolean[1];
//
//    HashMap<Long,Boolean> checkboxMap = new HashMap<Long,Boolean>();
//    private final int GROUP_ITEM_RESOURCE = R.layout.groupfaq_item;
//    private final int CHILD_ITEM_RESOURCE = R.layout.childfaq_item;
//    public String []check_string_array;
//
//    public SampleExpandableListAdapter(Context context, Activity activity, String[][] data) {
//        this.data = data;
//        this.context = context;
//        vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        _objInt = data.length;
//        check_string_array = new String[_objInt];
//        popolaCheckMap();
//    }
//    public void popolaCheckMap(){
//
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
//        String buffer = null;
//
//        for(int i=0; i<_objInt; i++){
//            buffer = settings.getString(String.valueOf((int)i),"false");
//            if(buffer.equals("false"))
//                checkboxMap.put((long)i, false);
//            else checkboxMap.put((long)i, true);
//        }
//    }
//
//    public String getChild(int groupPosition, int childPosition) {
//        return data[groupPosition][childPosition];
//    }
//
//    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
//    }
//
//    public int getChildrenCount(int groupPosition) {
//        return data[groupPosition].length;
//    }
//
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        View v = convertView;
//        String child = getChild(groupPosition, childPosition);
//        int id_res = 0;
//        //			String k = null;
//
//        //			if(groupPosition == 0){
//        ////				if(childPosition == 0) id_res = R.string.faq1;
//        ////				t.setText(R.string.faq1);
//        ////				k = "gdfgdf";
//        //
//        //				v = vi.inflate(R.layout.childfaq_item, null);
//        //				v.setBackgroundResource(R.drawable.borders);
//        //				//				id_res = R.id.image1;
//        //				final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//        //				faq1.setText("Standard delivery:5-9 days");
//        //				ViewHolder holder = new ViewHolder(v);
//        ////				codCharges.setVisibility(View.GONE);
//        ////				codChargesValue.setVisibility(View.GONE);
//        ////				amounttotal.setText(""+Integer.parseInt(globalVariable.getAmount().toString()));
//        ////				globalVariable.setFinalcodamounttotal("0");
//        //			}
//        //			else if(groupPosition == 1){
//        ////				if(childPosition == 0) id_res = R.string.faq2;
//        //				v = vi.inflate(R.layout.childfaq_item, null);
//        //				v.setBackgroundResource(R.drawable.borders);
//        //				//				id_res = R.id.image1;
//        //				final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//        //				faq1.setText("Standard delivery:5-9 days");
//        //				ViewHolder holder = new ViewHolder(v);
//        ////				codCharges.setVisibility(View.GONE);
//        ////				codChargesValue.setVisibility(View.GONE);
//        ////				amounttotal.setText(""+Integer.parseInt(globalVariable.getAmount().toString()));
//        ////				globalVariable.setFinalcodamounttotal("0");
//        //			}
//        //			else if(groupPosition == 2){
//        //				if(childPosition == 0) id_res = R.string.faq3;
//        //					//					 id_res = R.layout.button;
//        ////				}
//        //
//        //			}else if (groupPosition == 3) {
//        //				if (childPosition == 0)id_res = 0;
//        ////				codCharges.setVisibility(View.GONE);
//        ////				codChargesValue.setVisibility(View.GONE);
//        ////				amounttotal.setText(""+Integer.parseInt(globalVariable.getAmount().toString()));
//        ////				globalVariable.setFinalcodamounttotal("0");
//        //
//        //			}else if (groupPosition == 4) {
//        //				if (childPosition == 0)id_res = 0;
//        ////				codCharges.setVisibility(View.GONE);
//        ////				codChargesValue.setVisibility(View.GONE);
//        ////				amounttotal.setText(""+Integer.parseInt(globalVariable.getAmount().toString()));
//        ////				globalVariable.setFinalcodamounttotal("0");
//        //
//        //			}
//
//        if (child != null) {
//            v = vi.inflate(CHILD_ITEM_RESOURCE, null);
//            ViewHolder holder = new ViewHolder(v);
//
//            //				holder.text.setText(Html.fromHtml(child));
//
//            //				holder.imageview.setImageResource(id_res);
//        }
//        if(groupPosition == 0){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("Drop us a mail regarding your cancellation request along with the reason for cancellation to hello@3pmstore.com");
//            ViewHolder holder = new ViewHolder(v);
//        }
//        if(groupPosition == 1){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("Write to us informing the request for return and after we look into the reason for return abiding our return policy, We will pick it up from you at absolutely no cost.");
//            ViewHolder holder = new ViewHolder(v);
//        }
//        if (groupPosition == 2) {
//            //				codChargesValue.setText("COD Charges: "+Codcash);
//            //				amounttotal.setText(""+(Integer.parseInt(globalVariable.getAmount().toString())+Integer.parseInt(Codcash)));
//            //				globalVariable.setFinalcodamounttotal(amounttotal.getText().toString());
//            //				codCharges.setVisibility(View.VISIBLE);
//            //				codChargesValue.setVisibility(View.VISIBLE);
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("You can return the product within 14 days after the date of delivery.");
//            ViewHolder holder = new ViewHolder(v);
//            //				otp =(EditText)v.findViewById(R.id.editText1);
//            //				otp.setVisibility(View.GONE);
//            //				codCharges.setVisibility(View.GONE);
//        }
//        if(groupPosition == 3){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("Once it is picked up from you, the money will be refunded  to your 3PMstore Cash Account  and will be reflected in 24 hours after the pick-up.");
//            ViewHolder holder = new ViewHolder(v);
//            //				v = vi.inflate(R.layout.childfaq_item, null);
//            //				v.setBackgroundResource(R.drawable.borders);
//            //				//				id_res = R.id.image1;
//            //				final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            //				faq1.setText("Standard :5-9 days");
//            //				ViewHolder holder = new ViewHolder(v);
//        }
//        if(groupPosition == 4){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("The money will be refunded to your 3PMstore cash account in 24-48 hours after the product is picked up from you.");
//            ViewHolder holder = new ViewHolder(v);
//            //				v = vi.inflate(R.layout.childfaq_item, null);
//            //				v.setBackgroundResource(R.drawable.borders);
//            //				//				id_res = R.id.image1;
//            //				final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            //				faq1.setText("Standard :5-9 days");
//            //				ViewHolder holder = new ViewHolder(v);
//        }
//        if(groupPosition == 5){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("Drop us a mail to hello@3pmstore.com and the variant of exchange that you are looking for. We will arrange for a pick up the product from you and dispatch the variant you requested for.");
//            ViewHolder holder = new ViewHolder(v);
//        }
//
//        if(groupPosition == 6){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("After the product is picked up from you,  We will dispatch the variant you requested for in the next 2 business days after the pick-up.");
//            ViewHolder holder = new ViewHolder(v);
//        }
//        return v;
//    }
//
//    public String getGroup(int groupPosition) {
//        return "group-" + groupPosition;
//    }
//
//    public int getGroupCount() {
//        return data.length;
//    }
//
//
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        View v = convertView;
//        String group = null;
//        int id_res = 0;
//        long group_id = getGroupId(groupPosition);
//        //			group = "COD"+context.getString(R.string.rs)+55 +" extra";
//        if(group_id == 0){
//            group = "<u>How do I cancel my Order?</u>";
//            //	        	id_res = R.drawable.audi;
//        }
//        else if(group_id == 1){
//            group = "<u>What is the return process?</u>";
//            //				id_res = R.drawable.paytm;
//        }
//        else if(group_id == 2){
//            group = "<u>When can I return the Product?</u>";
//            //				id_res = R.drawable.mobi;
//        }
//
//        else if (group_id == 3) {
//            group ="<u>Once I return the product, How is my money refunded?</u>";
//            //				id_res = R.drawable.payumoney;
//        }
//        else if (group_id == 4) {
//            group ="<u>How many days will it take for the refund?</u>";
//            //				id_res = R.drawable.mobi;
//        }
//        else if (group_id == 5) {
//            group ="<u>How do I exchange a product?</u>";
//            //				id_res = R.drawable.mobi;
//        }
//        else if (group_id == 6) {
//            group ="<u>How many days would it take for the product to be exchanged?</u>";
//            //				id_res = R.drawable.mobi;
//        }
//
//        if (group != null) {
//            v = vi.inflate(GROUP_ITEM_RESOURCE, null);
//            ViewHolder holder = new ViewHolder(v);
//
//            holder.text.setText(Html.fromHtml(group));
//            holder.imageview.setImageResource(id_res);
//        }
//        return v;
//    }
//
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return true;
//    }
//
//
//    public boolean hasStableIds() {
//        return true;
//    }
//
//
//}
//class SampleExpandableListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter  {
//    public Context context;
//    CheckBox checkBox;
//    private LayoutInflater vi;
//    private String[][] data;
//    int _objInt;
//    Boolean checked[] = new Boolean[1];
//
//    HashMap<Long,Boolean> checkboxMap = new HashMap<Long,Boolean>();
//    private final int GROUP_ITEM_RESOURCE = R.layout.groupfaq_item;
//    private final int CHILD_ITEM_RESOURCE = R.layout.childfaq_item;
//    public String []check_string_array;
//
//    public SampleExpandableListAdapter(Context context, Activity activity, String[][] data) {
//        this.data = data;
//        this.context = context;
//        vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        _objInt = data.length;
//        check_string_array = new String[_objInt];
//        popolaCheckMap();
//    }
//    public void popolaCheckMap(){
//
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
//        String buffer = null;
//
//        for(int i=0; i<_objInt; i++){
//            buffer = settings.getString(String.valueOf((int)i),"false");
//            if(buffer.equals("false"))
//                checkboxMap.put((long)i, false);
//            else checkboxMap.put((long)i, true);
//        }
//    }
//
//    public String getChild(int groupPosition, int childPosition) {
//        return data[groupPosition][childPosition];
//    }
//
//    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
//    }
//
//    public int getChildrenCount(int groupPosition) {
//        return data[groupPosition].length;
//    }
//
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        View v = convertView;
//        String child = getChild(groupPosition, childPosition);
//        int id_res = 0;
//
//        if (child != null) {
//            v = vi.inflate(CHILD_ITEM_RESOURCE, null);
//            ViewHolder holder = new ViewHolder(v);
//
//            //				holder.text.setText(Html.fromHtml(child));
//
//            //				holder.imageview.setImageResource(id_res);
//        }
//        if(groupPosition == 0){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("Drop us a mail regarding your cancellation request along with the reason for cancellation to hello@3pmstore.com");
//            ViewHolder holder = new ViewHolder(v);
//        }
//        if(groupPosition == 1){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("Write to us informing the request for return and after we look into the reason for return abiding our return policy, We will pick it up from you at absolutely no cost.");
//            ViewHolder holder = new ViewHolder(v);
//        }
//        if (groupPosition == 2) {
//            //				codChargesValue.setText("COD Charges: "+Codcash);
//            //				amounttotal.setText(""+(Integer.parseInt(globalVariable.getAmount().toString())+Integer.parseInt(Codcash)));
//            //				globalVariable.setFinalcodamounttotal(amounttotal.getText().toString());
//            //				codCharges.setVisibility(View.VISIBLE);
//            //				codChargesValue.setVisibility(View.VISIBLE);
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("You can return the product within 14 days after the date of delivery.");
//            ViewHolder holder = new ViewHolder(v);
//            //				otp =(EditText)v.findViewById(R.id.editText1);
//            //				otp.setVisibility(View.GONE);
//            //				codCharges.setVisibility(View.GONE);
//        }
//        if(groupPosition == 3){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("Once it is picked up from you, the money will be refunded  to your 3PMstore Cash Account  and will be reflected in 24 hours after the pick-up.");
//            ViewHolder holder = new ViewHolder(v);
//            //				v = vi.inflate(R.layout.childfaq_item, null);
//            //				v.setBackgroundResource(R.drawable.borders);
//            //				//				id_res = R.id.image1;
//            //				final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            //				faq1.setText("Standard :5-9 days");
//            //				ViewHolder holder = new ViewHolder(v);
//        }
//        if(groupPosition == 4){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("The money will be refunded to your 3PMstore cash account in 24-48 hours after the product is picked up from you.");
//            ViewHolder holder = new ViewHolder(v);
//            //				v = vi.inflate(R.layout.childfaq_item, null);
//            //				v.setBackgroundResource(R.drawable.borders);
//            //				//				id_res = R.id.image1;
//            //				final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            //				faq1.setText("Standard :5-9 days");
//            //				ViewHolder holder = new ViewHolder(v);
//        }
//        if(groupPosition == 5){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("Drop us a mail to hello@3pmstore.com and the variant of exchange that you are looking for. We will arrange for a pick up the product from you and dispatch the variant you requested for.");
//            ViewHolder holder = new ViewHolder(v);
//        }
//
//        if(groupPosition == 6){
//            v = vi.inflate(R.layout.childfaq_item, null);
//            v.setBackgroundResource(R.drawable.borders);
//            //				id_res = R.id.image1;
//            final TextView faq1 =(TextView)v.findViewById(R.id.faq);
//            faq1.setText("After the product is picked up from you,  We will dispatch the variant you requested for in the next 2 business days after the pick-up.");
//            ViewHolder holder = new ViewHolder(v);
//        }
//        return v;
//    }
//
//    public String getGroup(int groupPosition) {
//        return "group-" + groupPosition;
//    }
//
//    public int getGroupCount() {
//        return data.length;
//    }
//
//
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        View v = convertView;
//        String group = null;
//        int id_res = 0;
//        long group_id = getGroupId(groupPosition);
//        //			group = "COD"+context.getString(R.string.rs)+55 +" extra";
//        if(group_id == 0){
//            group = "<u>How do I cancel my Order?</u>";
//            //	        	id_res = R.drawable.audi;
//        }
//        else if(group_id == 1){
//            group = "<u>What is the return process?</u>";
//            //				id_res = R.drawable.paytm;
//        }
//        else if(group_id == 2){
//            group = "<u>When can I return the Product?</u>";
//            //				id_res = R.drawable.mobi;
//        }
//
//        else if (group_id == 3) {
//            group ="<u>Once I return the product, How is my money refunded?</u>";
//            //				id_res = R.drawable.payumoney;
//        }
//        else if (group_id == 4) {
//            group ="<u>How many days will it take for the refund?</u>";
//            //				id_res = R.drawable.mobi;
//        }
//        else if (group_id == 5) {
//            group ="<u>How do I exchange a product?</u>";
//            //				id_res = R.drawable.mobi;
//        }
//        else if (group_id == 6) {
//            group ="<u>How many days would it take for the product to be exchanged?</u>";
//            //				id_res = R.drawable.mobi;
//        }
//
//        if (group != null) {
//            v = vi.inflate(GROUP_ITEM_RESOURCE, null);
//            ViewHolder holder = new ViewHolder(v);
//
//            holder.text.setText(Html.fromHtml(group));
//            holder.imageview.setImageResource(id_res);
//        }
//        return v;
//    }
//
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return true;
//    }
//
//
//    public boolean hasStableIds() {
//        return true;
//    }
//}