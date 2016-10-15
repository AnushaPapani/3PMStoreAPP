package com.store.storeapps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.store.storeapps.R;
import com.store.storeapps.activities.HomeActivity;

/**
 * Created by Shankar on 5/14/2016.
 */
public class NoOrderFoundAdapter extends BaseAdapter {
    private View view = null;
    private PrivateHolder holder;
    private LayoutInflater mInflater;
    private HomeActivity homeActivity;

    public NoOrderFoundAdapter(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        mInflater = (LayoutInflater) homeActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = convertView;
        if (convertView == null) {
            holder = new PrivateHolder();
            view = mInflater.inflate(R.layout.noorders_found, null, false);
            holder.mTxtNoPosts = (TextView) view.findViewById(R.id.mTxtNoPosts);
            view.setTag(holder);
        } else {
            holder = (PrivateHolder) view.getTag();
        }

        return view;
    }

    private class PrivateHolder {
        private TextView mTxtNoPosts;
    }
}
