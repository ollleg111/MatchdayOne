package com.matchday.android.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.matchday.android.util.TypefaceUtils;

/**
 *  Created by marina on 19.08.16.
 */
public class LatoLightTextView extends LatoTextView {
    public LatoLightTextView(Context context) {
        super(context);
    }

    public LatoLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LatoLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Typeface getFont() {
        return TypefaceUtils.getLatoLightTypeface(getContext());
    }
}
