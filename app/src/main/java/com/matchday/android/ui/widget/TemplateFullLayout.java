package com.matchday.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by marina on 20.09.16.
 */
public class TemplateFullLayout extends FrameLayout {
    public TemplateFullLayout(Context context) {
        super(context);
    }

    public TemplateFullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TemplateFullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
//
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(0, 0);
    }


}
