package com.matchday.android.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.matchday.android.util.TypefaceUtils;

/**
 *  Created by marina on 19.08.16.
 */
public class LatoBlackTextView extends LatoTextView {
    public LatoBlackTextView(Context context) {
        super(context);
    }

    public LatoBlackTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LatoBlackTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Typeface getFont() {
        return TypefaceUtils.getLatoBlackTypeface(getContext());
    }
}
