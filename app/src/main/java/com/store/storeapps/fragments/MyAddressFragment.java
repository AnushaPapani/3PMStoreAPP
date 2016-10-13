package com.store.storeapps.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.store.storeapps.R;
import com.store.storeapps.adapters.ReviewOrderAdapter;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.models.Movie;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/*
Created by BabuRao
 */
public class MyAddressFragment extends Fragment {
    public static final String TAG = "MyAddressFragment";
    private View rootView;
    String MyAddress = "Addresses";
    String addressid;
    ListView list;
    private static final String TAG_ID = "ID";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_BLINE = "bline";
    private static final String TAG_BCITY = "bcity";
    private static final String TAG_BSTATE = "bstate";
    private static final String TAG_BPINCODE = "bpincode";
    private static final String TAG_BMOBILE = "bmobile";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_O_ADDRESS = "tbl_addresses";
    private List<Movie> movieList = new ArrayList<Movie>();
    AddressAdapter adapter;
    TextView addnew;
    View toastRoot;
    View toastRoot2;
    Toast toast;
    public static String edit_username ="";
    public static String edit_address ="";
    public static String edit_city ="";
    public static String edit_state ="";
    public static String edit_pincode ="";
    public static String edit_mobile ="";
    public static String addressid2 ="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_myaddress, container, false);
        toastRoot = inflater.inflate(R.layout.toast, null);
        toastRoot2 = inflater.inflate(R.layout.error_toast, null);
        initUI();
        return rootView;
    }

    private void initUI() {
//        TextView aboutustext = (TextView) rootView.findViewById(R.id.aboutustext);
//        SpannableString content = new SpannableString(MyAddress);
//        content.setSpan(new UnderlineSpan(), 0, MyAddress.length(), 0);
//        aboutustext.setText(content);
        list = (ListView) rootView.findViewById(R.id.list);
        // Getting adapter by passing xml data ArrayList
        adapter = new AddressAdapter(getActivity(), movieList);
        list.setAdapter(adapter);
        toast = new Toast(getActivity());
        addnew = (TextView) rootView.findViewById(R.id.addanother);
        addnew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
//                Intent i = new Intent(getApplicationContext(), AddAddressActivity.class);
//                startActivity(i);

            }
        });
        new GetAddress(Utility.getSharedPrefStringData(getActivity(), Constants.USER_ID)).execute();
    }

    class GetAddress extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String userid;
        JSONArray addresseArray = null;

        public GetAddress(String userid) {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
            this.userid = userid;

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

                result = Utility.httpPostRequestToServer(ApiConstants.MY_ADDRESS+"?U_id=" + userid, Utility.getParams(paramsList));
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
                        addresseArray = jsonobject.getJSONArray("tbl_addresses");
                        for (int i = 0; i < addresseArray.length(); i++) {
                            JSONObject jsonObject = addresseArray.getJSONObject(i);
                            Movie movie = new Movie();
//                            String Id = jsonObject.getString(TAG_ID);
//                            String Username = jsonObject.getString(TAG_USERNAME);
//                            String Bline = jsonObject.getString(TAG_BLINE);
//                            String Bcity = jsonObject.getString(TAG_BCITY);
//                            String Bstate = jsonObject.getString(TAG_BSTATE);
//                            String Bpincode = jsonObject.getString(TAG_BPINCODE);
//                            String Bmobile = jsonObject.getString(TAG_BMOBILE);
                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            movie.setId(jsonObject.getString(TAG_ID));
                            movie.setUsername(jsonObject.getString(TAG_USERNAME));
                            movie.setBline(jsonObject.getString(TAG_BLINE));
                            movie.setBcity(jsonObject.getString(TAG_BCITY));
                            movie.setBstate(jsonObject.getString(TAG_BSTATE));
                            movie.setBpincode(jsonObject.getString(TAG_BPINCODE));
                            movie.setBmobile(jsonObject.getString(TAG_BMOBILE));

                            movieList.add(movie);
                        }
                        adapter = new AddressAdapter(getActivity(), movieList);
                        list.setAdapter(adapter);

                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class DeleteAddress extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;

        public DeleteAddress() {
            mCustomProgressDialog = new CustomProgressDialog(getActivity());
        }
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(getActivity(), R.string.please_wait));
        }

        /**
         * Deleting product
         */
        protected String doInBackground(String... args) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("ID", addressid);
                result = Utility.httpPostRequestToServer(ApiConstants.DELETE_ADDRESS, Utility.getParams(paramsList));
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return result;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            // dismiss the dialog once product deleted
            try {
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject.optString("success").equalsIgnoreCase("1")) {
                        TextView t = (TextView) toastRoot2.findViewById(R.id.validtoast);
                        t.setText("Address has been deleted successfully");
                        toast.setView(toastRoot2);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 80);
                        toast.setDuration(20000);
                        toast.show();
                        Utility.navigateDashBoardFragment(new MyAddressFragment(), MyAddressFragment.TAG, null, getActivity());

                    }
                }
                mCustomProgressDialog.dismissProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

    }

    class AddressAdapter extends BaseAdapter {

        private Activity activity;
        private ArrayList<HashMap<String, String>> data;
        private List<Movie> movieItems;
        LayoutInflater inflater;
        Movie m;
        int selected_position = 0;

        public AddressAdapter(Activity activity, List<Movie> movieItems) {
            this.activity = activity;
            this.movieItems = movieItems;

        }

        public int getCount() {
            return movieItems.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View vi = convertView;
            //			if(convertView==null)
            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.fragment_myaddresses_item, null);

//            if (inflater == null)
//                inflater = (LayoutInflater) activity
//                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            vi = inflater.inflate(R.layout.fragment_myaddresses_item, null);

            final TextView id = (TextView) vi.findViewById(R.id.id);
            final TextView Username = (TextView) vi.findViewById(R.id.addressesname);
            final TextView bline = (TextView) vi.findViewById(R.id.addressesline);
            final TextView bcity = (TextView) vi.findViewById(R.id.addressescity);
            final TextView bstate = (TextView) vi.findViewById(R.id.addressesstate);
            final TextView bpincode = (TextView) vi.findViewById(R.id.addressespincode);
            final TextView bmobile = (TextView) vi.findViewById(R.id.addressesmobile);
            final RelativeLayout addresscolorlayout = (RelativeLayout) vi.findViewById(R.id.addresscolorlayout);
            final CheckBox chkbox = (CheckBox) vi.findViewById(R.id.checkBox1);
            final ImageView imageViewCheck = (ImageView) vi.findViewById(R.id.imageViewCheck);
            HashMap<String, String> song = new HashMap<String, String>();

            m = movieItems.get(position);
            String name = m.getUsername().toString();
            System.out.println("name "+name);
            String id1 = m.getId();
            int id2 = Integer.parseInt(id1);

            id.setText(m.getId());
            //			id.setVisibility(View.GONE);

            Username.setText(m.getUsername());
            bline.setText(m.getBline());
            bcity.setText(m.getBcity());
            bstate.setText(m.getBstate());
            bpincode.setText(m.getBpincode());
            bmobile.setText(m.getBmobile());
            TextView edit = (TextView) vi.findViewById(R.id.edit);
            TextView delete = (TextView) vi.findViewById(R.id.delete);
            chkbox.setChecked(position == selected_position);
            chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selected_position = position;
                    } else {
                        selected_position = -1;
                    }
                    notifyDataSetChanged();
                }
            });

            chkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chkbox.isChecked()) {
                        addressid = id.getText().toString();
                        String adid = id.getText().toString();
                        String uname = Username.getText().toString();
                        String line = bline.getText().toString();
                        String city = bcity.getText().toString();
                        String state = bstate.getText().toString();
                        String pincode = bpincode.getText().toString();
                        String mobile = bmobile.getText().toString();
                        addresscolorlayout.setBackgroundResource(R.drawable.listviewbackground);
                        Utility.navigateDashBoardFragment(new ReviewOrderFragment(), ReviewOrderFragment.TAG, null, getActivity());
                        // send result code 100 to notify about product deletion
                    }

                    notifyDataSetChanged();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    addressid2= id.getText().toString();
                    edit_username= Username.getText().toString();
                    edit_address= bline.getText().toString();
                    edit_city= bcity.getText().toString();
                    edit_state= bstate.getText().toString();
                    edit_pincode= bpincode.getText().toString();
                    edit_mobile= bmobile.getText().toString();

                    Utility.navigateDashBoardFragment(new EditAddressFragment(), EditAddressFragment.TAG, null, getActivity());
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    addressid = id.getText().toString();
                    new DeleteAddress().execute();

                }
            });


            return vi;
        }
    }
}
