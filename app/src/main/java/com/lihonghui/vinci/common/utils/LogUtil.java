package com.lihonghui.vinci.common.utils;

import android.util.Log;

import com.socks.library.KLog;

/**
 * Created by yq05481 on 2016/10/27.
 */

public class LogUtil {
    private static final String LOG_TAG = "lhh";
    public static void init(boolean enable){
        KLog.init(enable, LOG_TAG);
    }

    public static void printLog(String s) {
        KLog.e(s);
    }
}
