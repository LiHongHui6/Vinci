package com.lihonghui.vinci.common.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

public class StatusBarColourHelper {
    private View vStatusBarBackground;
    private Activity mActivity;

    public StatusBarColourHelper(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void colourStatusBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();

            if (vStatusBarBackground == null) {
                vStatusBarBackground = new View(mActivity);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.getInstance(mActivity).getStateBarHeight());
                vStatusBarBackground.setLayoutParams(layoutParams);
            }

            vStatusBarBackground.setBackgroundColor(color);
            decorView.removeView(vStatusBarBackground);
            decorView.addView(vStatusBarBackground);
        }
    }
}
