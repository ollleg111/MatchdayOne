package com.matchday.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matchday.android.Const;
import com.matchday.android.R;
import com.matchday.android.prefs.Prefs;
import com.matchday.android.ui.widget.LatoRegularTextView;
import com.matchday.android.util.UiUtils;

/**
 * Created by marina on 19.08.16.
 */
public class OnboardingFragmentOne extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;

    public static OnboardingFragmentOne newInstance(String layoutResId) {
        OnboardingFragmentOne sampleSlide = new OnboardingFragmentOne();
        Bundle args = new Bundle();
        args.putString(ARG_LAYOUT_RES_ID, "first");
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    public OnboardingFragmentOne() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        return view;
    }
}
