package com.store.storeapps.adapters;

import android.app.Activity;
import android.content.Context;
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
import com.store.storeapps.fragments.EditAddressFragment;
import com.store.storeapps.fragments.MyAddressFragment;
import com.store.storeapps.models.AddressesModel;
import com.store.storeapps.utility.Utility;

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
    }
}
