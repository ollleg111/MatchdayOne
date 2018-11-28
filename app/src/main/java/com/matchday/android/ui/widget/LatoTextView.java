package com.matchday.android.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by marina on 19.08.16.
 */
public abstract class LatoTextView extends TextView {
    public LatoTextView(Context context) {
        super(context);
        init();
    }

    public LatoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected abstract Typeface getFont();

    private void init() {
        setTypeface(getFont());
    }
}
