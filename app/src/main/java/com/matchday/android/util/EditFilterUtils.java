package com.matchday.android.util;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;

import com.matchday.android.R;
import com.matchday.android.model.EditFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marina on 15.09.16.
 */
public class EditFilterUtils {

    public static List<EditFilter> fillFilterList(Context context) {
        List<EditFilter> filterList = new ArrayList<>();
        EditFilter filterBright = new EditFilter(
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_brightness, null), "BRIGHTNESS");
        EditFilter filterContrast = new EditFilter(
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_contrast, null), "CONTRAST");
        EditFilter filterSharpness = new EditFilter(
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_sharpness, null), "SHARPNESS");
        EditFilter filterCropRotate = new EditFilter(
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_crop, null), "CROP AND ROTATE");
        EditFilter filterSaturation = new EditFilter(
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_saturation, null), "SATURATION");
        EditFilter filterTemperature = new EditFilter(
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_temperature, null), "TEMPERATURE");
        EditFilter filterShadows = new EditFilter(
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_shadows, null), "SHADOWS");

        filterList.add(filterBright);
        filterList.add(filterContrast);
        filterList.add(filterSharpness);
        filterList.add(filterCropRotate);
        filterList.add(filterSaturation);
        filterList.add(filterTemperature);
        filterList.add(filterShadows);

        return filterList;
    }
}
