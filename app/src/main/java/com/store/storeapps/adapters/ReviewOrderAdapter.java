package com.store.storeapps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.store.storeapps.R;
import com.store.storeapps.models.ReviewOrderModel;
import com.store.storeapps.utility.Utility;

import java.util.ArrayList;


/**
 * Created by shankar on 10/1/2016.
 */

public class ReviewOrderAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ReviewOrderModel> mReviewOrderModels;


    public ReviewOrderAdapter(Context context, ArrayList<ReviewOrderModel> mReviewOrderModels) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mReviewOrderModels = mReviewOrderModels;
    }

    @Override
    public int getCount() {
        return mReviewOrderModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mReviewOrderModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ReviewOrderItemHolder mReviewOrderItemHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.review_order_item,
                    null);
            mReviewOrderItemHolder = new ReviewOrderItemHolder();
            mReviewOrderItemHolder.img_order = (ImageView) convertView.findViewById(R.id.img_order);
            mReviewOrderItemHolder.txt_product_name = (TextView) convertView.findViewById(R.id.txt_product_name);
            mReviewOrderItemHolder.spin_qty = (Spinner) convertView.findViewById(R.id.spin_qty);
            mReviewOrderItemHolder.txt_unitPrice = (TextView) convertView.findViewById(R.id.txt_unitPrice);
            mReviewOrderItemHolder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
            mReviewOrderItemHolder.txt_subtotal = (TextView) convertView.findViewById(R.id.txt_subtotal);
            mReviewOrderItemHolder.txt_price_two = (TextView) convertView.findViewById(R.id.txt_price_two);
            mReviewOrderItemHolder.img_remove = (ImageView) convertView.findViewById(R.id.img_remove);

            convertView.setTag(mReviewOrderItemHolder);
        } else {
            mReviewOrderItemHolder = (ReviewOrderItemHolder) convertView.getTag();
        }

        ReviewOrderModel reviewOrderModel = (ReviewOrderModel) getItem(position);

        Picasso.with(mContext).load(getFullFilledImage(reviewOrderModel.getP_Image())).placeholder(Utility.getDrawable(mContext, R.drawable.refresh))
                .into(mReviewOrderItemHolder.img_order);
        mReviewOrderItemHolder.txt_product_name.setText(reviewOrderModel.getP_Name());
        mReviewOrderItemHolder.txt_price.setText(""+reviewOrderModel.getP_Cost());

        //mReviewOrderItemHolder.spin_qty.

        return convertView;
    }

    private String getFullFilledImage(String image) {
        return image.replaceAll(" ", "%20");
    }


    private class ReviewOrderItemHolder {
        private ImageView img_order;
        private TextView txt_product_name;
        private Spinner spin_qty;
        private TextView txt_unitPrice;
        private TextView txt_price;
        private TextView txt_subtotal;
        private TextView txt_price_two;
        private ImageView img_remove;
    }
}
