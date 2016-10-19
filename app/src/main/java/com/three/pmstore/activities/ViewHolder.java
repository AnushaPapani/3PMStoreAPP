package com.three.pmstore.activities;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.three.pmstore.R;


public class ViewHolder {

    public TextView text;
    public CheckBox checkbox;
    public ImageView imageview;
    public ViewHolder(View v) {
        this.text = (TextView)v.findViewById(R.id.text1);
        
        this.imageview = (ImageView)v.findViewById(R.id.image1);

    }



}
