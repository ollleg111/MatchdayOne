package com.matchday.android.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.matchday.android.Const;
import com.matchday.android.R;
import com.matchday.android.prefs.Prefs;
import com.matchday.android.ui.widget.LatoRegularTextView;
import com.matchday.android.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  OnboardingActivity extends FragmentActivity {
    @BindView(R.id.container)
    protected ViewPager mViewPagerFullScreen;
    protected View view;
    @BindView(R.id.viewPagerCountDots)
    protected LinearLayout pager_indicator;
    protected int dotsCount;
    protected ImageView[] dots;
    protected int mCurrentPosition;
    @BindView(R.id.txt_skip)
    protected LatoRegularTextView mTextSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        ButterKnife.bind(this);

        mViewPagerFullScreen.setAdapter(adapter);

        setReference(mViewPagerFullScreen);
        finishOnboarding();
    }

    private void finishOnboarding() {
        mTextSkip.setOnTouchListener((view1, motionEvent) -> {
            Prefs.setBooleanProperty(OnboardingActivity.this, Const.PREFS_VALUE, true);
            Intent main = new Intent(OnboardingActivity.this, MainActivity.class);
            startActivity(main);
            // Close the OnboardingActivity
            finish();
            return false;
        });
    }

    FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return OnboardingFragmentOne.newInstance("first");
                case 1:
                    return OnboardingFragmentTwo.newInstance("second");
                case 2:
                    return OnboardingFragmentThree.newInstance("three");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    public void setReference(ViewPager view) {
        view.setCurrentItem(0);
        view.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_dot));
                }

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

                setStateSkipText(mCurrentPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUiPageViewController();
    }

    // method for hiding or showing Skip on screen
    private void setStateSkipText(int position) {
        switch (position) {
            case 0:
                UiUtils.showView(mTextSkip);
                break;
            case 1:
                UiUtils.showView(mTextSkip);
                break;
            case 2:
                UiUtils.hideView(mTextSkip);
                break;
            default:
                break;
        }
    }

    private void setUiPageViewController() {
        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(10, 0, 10, 0);
            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }
}
