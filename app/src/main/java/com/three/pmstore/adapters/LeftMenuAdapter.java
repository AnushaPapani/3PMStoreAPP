package com.three.pmstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.three.pmstore.R;
import com.three.pmstore.models.LeftMenuModel;
import com.three.pmstore.utility.Constants;
import com.three.pmstore.utility.Utility;

import java.util.ArrayList;

/**
 * Created by Srikanth on 7/6/2016.
 */
public class LeftMenuAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<LeftMenuModel> leftMenuModels;


    public LeftMenuAdapter(Context context, ArrayList<LeftMenuModel> leftMenuModels) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.leftMenuModels = leftMenuModels;
    }

    @Override
    public int getCount() {
        return leftMenuModels.size();
    }

    @Override
    public Object getItem(int position) {
        return leftMenuModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LeftMenuItemHolder mLeftMenuItemHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.drawer_item,
                    null);
            mLeftMenuItemHolder = new LeftMenuItemHolder();
            mLeftMenuItemHolder.img_icon = (ImageView) convertView.findViewById(R.id.icon);
            mLeftMenuItemHolder.txt_title = (TextView) convertView.findViewById(R.id.title);
            mLeftMenuItemHolder.txt_cash = (TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(mLeftMenuItemHolder);
        } else {
            mLeftMenuItemHolder = (LeftMenuItemHolder) convertView.getTag();
        }

        LeftMenuModel leftMenuModel = (LeftMenuModel) getItem(position);

        mLeftMenuItemHolder.img_icon.setImageDrawable(Utility.getDrawable(mContext, leftMenuModel.getmImage()));
        mLeftMenuItemHolder.txt_title.setText(leftMenuModel.getmName());
        if (position == 1) {
            mLeftMenuItemHolder.txt_cash.setVisibility(View.VISIBLE);
            if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(mContext, Constants.USER_CASH))) {
                mLeftMenuItemHolder.txt_cash.setText("(" + Utility.getResourcesString(mContext, R.string.rs) + Utility.getSharedPrefStringData(mContext, Constants.USER_CASH) + ")");
            } else {
                mLeftMenuItemHolder.txt_cash.setText("(" + Utility.getResourcesString(mContext, R.string.rs) + "0"+ ")");
            }
        } else {
            mLeftMenuItemHolder.txt_cash.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class LeftMenuItemHolder {
        private ImageView img_icon;
        private TextView txt_title;
        private TextView txt_cash;
    }
}
