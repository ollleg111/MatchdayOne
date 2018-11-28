package com.matchday.android.ui;

import android.opengl.Matrix;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

/**
 * Created by marina on 27.09.16.
 */
public class FilterAdjuster {
    private final Adjuster<? extends GPUImageFilter> adjuster;

    public FilterAdjuster(final GPUImageFilter filter) {
        if (filter instanceof GPUImageSharpenFilter) {
            adjuster = new SharpnessAdjuster().filter(filter);
        } else if (filter instanceof GPUImageContrastFilter) {
            adjuster = new ContrastAdjuster().filter(filter);
        } else if (filter instanceof GPUImageBrightnessFilter) {
            adjuster = new BrightnessAdjuster().filter(filter);
        } else if (filter instanceof GPUImageSaturationFilter) {
            adjuster = new SaturationAdjuster().filter(filter);
        } else if (filter instanceof GPUImageHighlightShadowFilter) {
            adjuster = new HighlightShadowAdjuster().filter(filter);
        } else if (filter instanceof GPUImageWhiteBalanceFilter) {
            adjuster = new WhiteBalanceAdjuster().filter(filter);
        } else if (filter instanceof GPUImageTransformFilter) {
            adjuster = new RotateAdjuster().filter(filter);
        } else {

            adjuster = null;
        }
    }

    public boolean canAdjust() {
        return adjuster != null;
    }

    public void adjust(final int percentage) {
        if (adjuster != null) {
            adjuster.adjust(percentage);
        }
    }

    public float getCurrentValue() {
        return adjuster.getCurrentValue();
    }

    private abstract class Adjuster<T extends GPUImageFilter> {
        private T filter;

        @SuppressWarnings("unchecked")
        public Adjuster<T> filter(final GPUImageFilter filter) {
            this.filter = (T) filter;
            return this;
        }


        public T getFilter() {
            return filter;
        }

        public abstract void adjust(int percentage);

        public abstract float getCurrentValue();

        protected float range(final int percentage, final float start, final float end) {
            return (end - start) * percentage / 100.0f + start;
        }

        protected int range(final int percentage, final int start, final int end) {
            return (end - start) * percentage / 100 + start;
        }
    }

    private class BrightnessAdjuster extends Adjuster<GPUImageBrightnessFilter> {
        private float i = 0.0f;

        @Override
        public void adjust(final int percentage) {
            getFilter().setBrightness(range(percentage, -1.0f, 1.0f));
            i = range(percentage, -1.0f, 1.0f);
        }

        @Override
        public float getCurrentValue() {
            return i;
        }

    }

    private class ContrastAdjuster extends Adjuster<GPUImageContrastFilter> {
        private float i = 1.0f;

        @Override
        public void adjust(final int percentage) {
            getFilter().setContrast(range(percentage, 0.0f, 2.0f));
            i = range(percentage, 0.0f, 2.0f);
        }

        @Override
        public float getCurrentValue() {
            return i;
        }
    }

    private class SharpnessAdjuster extends Adjuster<GPUImageSharpenFilter> {
        private float i = 0.0f;

        @Override
        public void adjust(final int percentage) {
            getFilter().setSharpness(range(percentage, -4.0f, 4.0f));
            i = range(percentage, -4.0f, 4.0f);
        }

        @Override
        public float getCurrentValue() {
            return i;
        }
    }

    private class RotateAdjuster extends Adjuster<GPUImageTransformFilter> {
        private float i;

        @Override
        public void adjust(final int percentage) {
            float[] transform = new float[16];
            Matrix.setRotateM(transform, 0, 360 * percentage / 100, 0, 0, 1.0f);
            getFilter().setTransform3D(transform);
            i = transform[15];
        }

        @Override
        public float getCurrentValue() {
            return i;
        }
    }


    private class SaturationAdjuster extends Adjuster<GPUImageSaturationFilter> {
        private float i = 1.0f;

        @Override
        public void adjust(final int percentage) {
            getFilter().setSaturation(range(percentage, 0.0f, 2.0f));
            i = range(percentage, 0.0f, 2.0f);
        }

        @Override
        public float getCurrentValue() {
            return i;
        }
    }

    private class WhiteBalanceAdjuster extends Adjuster<GPUImageWhiteBalanceFilter> {
        private float i = 5000.0f;

        @Override
        public void adjust(final int percentage) {
            getFilter().setTemperature(range(percentage, 2000.0f, 8000.0f));
            i = range(percentage, 2000.0f, 8000.0f);
            //getFilter().setTint(range(percentage, -100.0f, 100.0f));
        }

        @Override
        public float getCurrentValue() {
            return i;
        }
    }

    private class HighlightShadowAdjuster extends Adjuster<GPUImageHighlightShadowFilter> {
        private float i = 0.0f;

        @Override
        public void adjust(final int percentage) {
            getFilter().setShadows(range(percentage, 0.0f, 1.0f));
            getFilter().setHighlights(range(percentage, 0.0f, 1.0f));
            i = range(percentage, 0.0f, 1.0f);
        }

        @Override
        public float getCurrentValue() {
            return i;
        }
    }
}
