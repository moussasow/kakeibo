package com.mas.kakeibo.utils;

import android.util.Log;

import com.mas.kakeibo.BuildConfig;

/**
 * Created by sow.m on 2018/11/09.
 */
public class LogUtil {

    public static void debug(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }
}
