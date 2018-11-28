package com.matchday.android.util;

import android.graphics.Bitmap;
import android.util.Log;

import com.matchday.android.ui.widget.ZoomView;

import java.io.ByteArrayOutputStream;

/**
 * Created by marina on 31.08.16.
 */
public class BitmapUtils {

    public static Bitmap compressBitmap(Bitmap realImage, float maxImageSize,
                                        boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, 1000,
                1000, filter);

        ByteArrayOutputStream imagefile = new ByteArrayOutputStream();
        // Write 'bitmap' to file using JPEG and 80% quality hint for JPEG:
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, imagefile);

        return newBitmap;

    }
}
