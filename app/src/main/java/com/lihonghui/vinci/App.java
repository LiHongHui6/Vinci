package com.lihonghui.vinci;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lihonghui.vinci.common.utils.LogUtil;

/**
 * Created by yq05481 on 2016/11/29.
 */

public class App extends Application {
    private static Context context;//Test
    public static Context getContext(){
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(true);
        Fresco.initialize(this);
        this.context = this;
    }
}
