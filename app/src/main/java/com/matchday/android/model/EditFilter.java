package com.matchday.android.model;

import android.graphics.drawable.Drawable;

import com.matchday.android.ui.widget.LatoBlackTextView;

/**
 * Created by marina on 15.09.16.
 */
public class EditFilter {

    private Drawable mImageFilter;
    private String mTextFilter;

    public EditFilter(Drawable mImageFilter, String mTextFilter) {
        this.mImageFilter = mImageFilter;
        this.mTextFilter = mTextFilter;
    }

    public Drawable getImageFilter() {
        return mImageFilter;
    }

    public void setImageFilter(Drawable imageFilter) {
        this.mImageFilter = imageFilter;
    }

    public String getTextFilter() {
        return mTextFilter;
    }

    public void setTextFilter(String textFilter) {
        this.mTextFilter = mTextFilter;
    }
}
