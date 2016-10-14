package com.store.storeapps.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class BoldCustomTextView extends TextView {

    public BoldCustomTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public BoldCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public BoldCustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("myriadprobold.otf", context);
        setTypeface(customFont);
    }
}