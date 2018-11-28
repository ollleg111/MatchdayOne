package com.matchday.android.util;

import android.util.Log;

/**
 * Created by NewUser on 12/8/16.
 */

public class LogUtil {
    public static void info(Object o, String message){
        Log.d(o.getClass().getName(), message);
    }
}
