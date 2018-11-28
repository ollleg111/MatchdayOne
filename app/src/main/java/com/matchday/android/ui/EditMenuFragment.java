package com.matchday.android.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.matchday.android.R;
import com.matchday.android.ui.adapter.RecyclerEditAdapter;
import com.matchday.android.util.GPUImageFilterToolsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageView;


//import static com.matchday.android.util.GPUImageFilterToolsUtils.createFilterForType;
//import static com.matchday.android.util.GPUImageFilterToolsUtils.createFilterGroupForType;
//import static com.matchday.android.util.GPUImageFilterToolsUtils.getFilterForType;
import static java.lang.String.valueOf;

/**
 * Created by marina on 14.09.16.
 */
public class EditMenuFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private GPUImageFilterToolsUtils.FilterList mFilterList;
    @BindView(R.id.recyclerview_effects)
    protected RecyclerView mRecyclerEdit;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerEditAdapter mEditAdapter;

    private GPUImageView mImageFilter;
    private FrameLayout mLinearContent;
    private ImageView mImageCancel;
    private ImageView mImageApply;
    private TextView mTextFilterValue;

    private SeekBar mSeekBarFilter;
    private GPUImageFilter mFilter;
    private FilterAdjuster mFilterAdjuster;
    GPUImageFilterToolsUtils.OnGpuImageFilterChosenListener listener;

    GPUImageFilterGroup mFilterGroup;
    GPUImageFilterToolsUtils filterToolsUtils;
    GPUImageFilterToolsUtils.FilterType mCurrentFilter;

    Handler seekBarHandler;
    float tempValue;
    float cancelValue;

    int i;
    int j;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_menu, container, false);
        mFilterList = GPUImageFilterToolsUtils.fillFilterList(getContext());

        ButterKnife.bind(this, view);

        initViews(view);

        openFilter();
        return view;
    }


    private void initViews(View view) {
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerEdit.setLayoutManager(mLinearLayoutManager);
        mEditAdapter = new RecyclerEditAdapter(mFilterList);
        mRecyclerEdit.setAdapter(mEditAdapter);

        filterToolsUtils = new GPUImageFilterToolsUtils();
        mFilterGroup = new GPUImageFilterGroup();

        seekBarHandler = new Handler();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLinearContent = (FrameLayout) getActivity().findViewById(R.id.content_filter);
        mImageCancel = (ImageView) getActivity().findViewById(R.id.img_cancel);
        mImageApply = (ImageView) getActivity().findViewById(R.id.img_apply);
        mImageFilter = (GPUImageView) getActivity().findViewById(R.id.img_gpu);
        mTextFilterValue = (TextView) getActivity().findViewById(R.id.txt_filter_value);

        mSeekBarFilter = (SeekBar) getActivity().findViewById(R.id.seekBerFilter);

        mSeekBarFilter.setOnSeekBarChangeListener(this);

        cancelFilter();
        applyButtonFilters();
    }

    private void openFilter() {
        mRecyclerEdit.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                (view, position) -> {
                    mLinearContent.setVisibility(View.VISIBLE);
                    mCurrentFilter = mFilterList.filters.get(position);
                    applyFilters(filterToolsUtils.createFilterGroupForType(
                            mFilterGroup, mFilterList.filters.get(position)),
                            mFilterList.filters.get(position));
                }));
    }


    private void applyFilters(final GPUImageFilterGroup filterGroup,
                              final GPUImageFilterToolsUtils.FilterType filter) {
        cancelValue = filterToolsUtils.getCurrentValueForTyp(filter);

        mImageFilter.setFilter(filterGroup);
        mFilter = filterToolsUtils.getFilterForType(filter);
        mFilterAdjuster = new FilterAdjuster(mFilter);
        mSeekBarFilter.setMax(100);
        mSeekBarFilter.setProgress((int) (filterToolsUtils.getCurrentValueForTyp(filter)));
    }

    private void applyButtonFilters() {
        mImageApply.setOnTouchListener((view, motionEvent) -> {
            mLinearContent.setVisibility(View.INVISIBLE);
            return false;
        });
    }


    private void cancelFilter() {
        mImageCancel.setOnTouchListener((view, motionEvent) -> {
            mLinearContent.setVisibility(View.INVISIBLE);
            filterToolsUtils.setCurrentValueForTyp(mCurrentFilter,
                    filterToolsUtils.getCurrentValue(mCurrentFilter, cancelValue));
            mSeekBarFilter.setMax(100);
            mSeekBarFilter.setProgress((int) cancelValue);
            mImageFilter.requestRender();
            return false;
        });
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, final int progress, boolean b) {
        if (mFilterAdjuster != null) {
            mFilterAdjuster.adjust(progress);
            mTextFilterValue.setText(String.valueOf(mFilterAdjuster.getCurrentValue()));
            if (Math.abs(tempValue - mFilterAdjuster.getCurrentValue()) > filterToolsUtils.getStepValue(mCurrentFilter)) {
                tempValue = mFilterAdjuster.getCurrentValue();
                mImageFilter.requestRender();
            } else {
                return;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        filterToolsUtils.setCurrentValueForTyp(mCurrentFilter, mFilterAdjuster.getCurrentValue());
    }


}
