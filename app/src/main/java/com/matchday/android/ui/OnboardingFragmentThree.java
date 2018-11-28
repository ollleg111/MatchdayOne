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

import com.matchday.android.Const;
import com.matchday.android.R;
import com.matchday.android.prefs.Prefs;
import com.matchday.android.ui.widget.LatoBlackTextView;
import com.matchday.android.ui.widget.LatoRegularTextView;
import com.matchday.android.util.UiUtils;

/**
 * Created by marina on 19.08.16.
 */
public class OnboardingFragmentThree extends Fragment {
    private static final String ARG_LAYOUT_RES_ID_THREE = "layoutResIdThree";
    private int layoutResId;
    private LatoBlackTextView mTextEnter;

    public static OnboardingFragmentThree newInstance(String layoutResId) {
        OnboardingFragmentThree sampleSlide = new OnboardingFragmentThree();
        Bundle args = new Bundle();
        args.putString(ARG_LAYOUT_RES_ID_THREE, "three");
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    public OnboardingFragmentThree() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID_THREE))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID_THREE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding_third, container, false);
        mTextEnter = (LatoBlackTextView) view.findViewById(R.id.txt_enter);
        finishOnboarding();
        return view;
    }

    private void finishOnboarding() {
        mTextEnter.setOnTouchListener((view, motionEvent) -> {
            Prefs.setBooleanProperty(getContext(), Const.PREFS_VALUE, true);
            Intent main = new Intent(getContext(), MainActivity.class);
            startActivity(main);
            // Close the OnboardingActivity
            getActivity().finish();
            return false;
        });
    }
}
