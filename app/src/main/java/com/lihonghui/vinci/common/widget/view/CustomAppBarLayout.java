package com.lihonghui.vinci.common.widget.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

/**
 * Created by yq05481 on 2016/12/13.
 */

public class CustomAppBarLayout extends AppBarLayout {
    public CustomAppBarLayout(Context context) {
        super(context);
    }

    public CustomAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(ev.getAction() == MotionEvent.ACTION_MOVE){
//            requestDisallowInterceptTouchEvent(false);
//            return isCanScroll;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_MOVE){
//            requestDisallowInterceptTouchEvent(false);
//            return isCanScroll;
//        }
//        return super.onTouchEvent(event);
//    }
//
//    private boolean isCanScroll = true;
//    public void scrollEnable(boolean isEnable){
//        this.isCanScroll = isEnable;
//    }
}
