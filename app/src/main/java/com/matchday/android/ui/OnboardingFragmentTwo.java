package com.matchday.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matchday.android.R;


/**
 * Created by marina on 19.08.16.
 */
public class OnboardingFragmentTwo extends Fragment {
    private static final String ARG_LAYOUT_RES_ID_TWO = "layoutResIdTwo";
    private int layoutResId;

    public static OnboardingFragmentTwo newInstance(String layoutResId) {
        OnboardingFragmentTwo sampleSlide = new OnboardingFragmentTwo();
        Bundle args = new Bundle();
        args.putString(ARG_LAYOUT_RES_ID_TWO, "second");
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    public OnboardingFragmentTwo() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID_TWO))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID_TWO);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding_second, container, false);
        return view;
    }
}
