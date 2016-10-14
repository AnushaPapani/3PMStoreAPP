package com.store.storeapps.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;
import com.store.storeapps.customviews.CustomProgressDialog;
import com.store.storeapps.fragments.EditAddressFragment;
import com.store.storeapps.fragments.MyAddressFragment;
import com.store.storeapps.fragments.ReviewOrderFragment;
import com.store.storeapps.models.AddressesModel;
import com.store.storeapps.utility.ApiConstants;
import com.store.storeapps.utility.Constants;
import com.store.storeapps.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Shankar on 10/14/2016.
 */

public class AddressAdapter extends BaseAdapter {

    private Context mContext;
    private List<AddressesModel> addressesModels;
    private LayoutInflater mLayoutInflater;
    private String mSelectedId;
    private HomeActivity mParent;

    public AddressAdapter(Context mContext, List<AddressesModel> addressesModels, String mSelectedId, HomeActivity mParent) {
        this.mContext = mContext;
        this.mParent = mParent;
        this.addressesModels = addressesModels;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mSelectedId = mSelectedId;
    }

    @Override
    public int getCount() {
        return addressesModels.size();
    }

    @Override
    public AddressesModel getItem(int position) {
        return addressesModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AddressAdapter.AddressItemHolder mAddressItemHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.fragment_myaddresses_item,
                    null);
            mAddressItemHolder = new AddressAdapter.AddressItemHolder();
            mAddressItemHolder.id = (TextView) convertView.findViewById(R.id.id);
            mAddressItemHolder.Username = (TextView) convertView.findViewById(R.id.addressesname);
            mAddressItemHolder.bline = (TextView) convertView.findViewById(R.id.addressesline);
            mAddressItemHolder.bcity = (TextView) convertView.findViewById(R.id.addressescity);
            mAddressItemHolder.bstate = (TextView) convertView.findViewById(R.id.addressesstate);
            mAddressItemHolder.bpincode = (TextView) convertView.findViewById(R.id.addressespincode);
            mAddressItemHolder.bmobile = (TextView) convertView.findViewById(R.id.addressesmobile);
            mAddressItemHolder.addresscolorlayout = (RelativeLayout) convertView.findViewById(R.id.addresscolorlayout);
            mAddressItemHolder.edit = (Button) convertView.findViewById(R.id.edit);
            mAddressItemHolder.delete = (Button) convertView.findViewById(R.id.delete);
            /*mAddressItemHolder.chkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);*/
            mAddressItemHolder.imageViewCheck = (ImageView) convertView.findViewById(R.id.imageViewCheck);
            convertView.setTag(mAddressItemHolder);
        } else {
            mAddressItemHolder = (AddressAdapter.AddressItemHolder) convertView.getTag();
        }

        AddressesModel leftMenuModel = (AddressesModel) getItem(position);

        mAddressItemHolder.id.setText(leftMenuModel.getID());
        mAddressItemHolder.Username.setText(Utility.capitalizeFirstLetter(leftMenuModel.getUsername()));
        mAddressItemHolder.bline.setText(Utility.capitalizeFirstLetter(leftMenuModel.getBline()));
        mAddressItemHolder.bcity.setText(Utility.capitalizeFirstLetter(leftMenuModel.getBcity()));
        mAddressItemHolder.bstate.setText(Utility.capitalizeFirstLetter(leftMenuModel.getBstate()));
        mAddressItemHolder.bpincode.setText(leftMenuModel.getBpincode());
        mAddressItemHolder.bmobile.setText(leftMenuModel.getBmobile());
        if (mSelectedId.equalsIgnoreCase(leftMenuModel.getID())) {
            Utility.showLog("leftMenuModel.getID()", "leftMenuModel.getID() " + leftMenuModel.getID());
            mAddressItemHolder.imageViewCheck.setImageDrawable(Utility.getDrawable(mContext, R.drawable.tickboxone));
        } else {
            mAddressItemHolder.imageViewCheck.setImageDrawable(Utility.getDrawable(mContext, R.drawable.ticktwo));
        }

        mAddressItemHolder.imageViewCheck.setTag(position);
        mAddressItemHolder.imageViewCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                if (mSelectedId.equalsIgnoreCase(addressesModels.get(position).getID())){
                    mSelectedId = "-1";
                } else {
                    mSelectedId = addressesModels.get(position).getID();
                    ReviewOrderFragment.txt_name.setText(addressesModels.get(position).getUsername());
                    ReviewOrderFragment.txt_address_line.setText(addressesModels.get(position).getBline());
                    ReviewOrderFragment.txt_city.setText(addressesModels.get(position).getBcity());
                    ReviewOrderFragment.txt_address_state.setText(addressesModels.get(position).getBstate());
                    ReviewOrderFragment.txt_pin_code.setText(addressesModels.get(position).getBpincode());
                    ReviewOrderFragment.txt_mobile.setText(addressesModels.get(position).getBmobile());
                    mParent.onBackPressed();
                }
                notifyDataSetChanged();

            }
        });

        mAddressItemHolder.edit.setTag(position);
        mAddressItemHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                MyAddressFragment.addressid2= addressesModels.get(position).getID();
                MyAddressFragment.edit_username= addressesModels.get(position).getUsername();
                MyAddressFragment.edit_address= addressesModels.get(position).getBline();
                MyAddressFragment.edit_city= addressesModels.get(position).getBcity();
                MyAddressFragment.edit_state= addressesModels.get(position).getBstate();
                MyAddressFragment.edit_pincode= addressesModels.get(position).getBpincode();
                MyAddressFragment.edit_mobile= addressesModels.get(position).getBmobile();

                Utility.navigateDashBoardFragment(new EditAddressFragment(), EditAddressFragment.TAG, null, mParent);
            }
        });

        mAddressItemHolder.delete.setTag(position);
        mAddressItemHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                new DeleteAddress(addressesModels.get(position).getID()).execute();
            }
        });

        return convertView;
    }

    private class AddressItemHolder {
        private TextView id;
        private TextView Username;
        private TextView bline;
        private TextView bcity;
        private TextView bstate;
        private TextView bpincode;
        private TextView bmobile;
        private RelativeLayout addresscolorlayout;
        private ImageView imageViewCheck;
        private Button edit;
        private Button delete;
    }

    class DeleteAddress extends AsyncTask<String, String, String> {
        private CustomProgressDialog mCustomProgressDialog;
        private String id;

        public DeleteAddress(String id) {
            mCustomProgressDialog = new CustomProgressDialog(mContext);
            this.id = id;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCustomProgressDialog.showProgress(Utility.getResourcesString(mContext, R.string.please_wait));
        }
        protected String doInBackground(String... args) {
            String result = null;
            try {
                LinkedHashMap<String, String> paramsList = new LinkedHashMap<String, String>();
                paramsList.put("ID", id);
                result = Utility.httpPostRequestToServer(ApiConstants.DELETE_ADDRESS, Utility.getParams(paramsList));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                if (response != null) {
                    JSONObject jsonobject = new JSONObject(response);
                    if (jsonobject.optString("success").equalsIgnoreCase("1")) {
                        int position = -1;
                        for (int i = 0; i < addressesModels.size(); i++) {
                            if (id.equalsIgnoreCase(addressesModels.get(i).getID())){
                                position = i;
                            }
                        }
                        addressesModels.remove(position);
                        MyAddressFragment.addressesModels.remove(position);
                        notifyDataSetChanged();
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
}
