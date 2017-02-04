package com.lihonghui.vinci.user.personal.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.lihonghui.vinci.R;
import com.lihonghui.vinci.common.utils.DensityUtil;
import com.lihonghui.vinci.common.utils.DisplayUtil;

/**
 * Created by yq05481 on 2016/11/23.
 */

public class AvatarBehavior extends CoordinatorLayout.Behavior<ImageView> {
    private Context mContext;
    private int appBarTotalScrollRange;
    private AppBarLayout mAppBarLayout;
    private final int stateBarHeight;
    private final float toolBarHeight;

    //头像的最小尺寸
    private final int avatarMinWidth = 26;//dp
    private final int avatarMinHeight = 26;//dp

    //头像的最大尺寸（初始的大小）
    private final float avatarMaxWidth = 60;//dp
    private final float avatarMaxHeight = 60;//dp

    //头像最后停留的位置
    private int targetX;
    private int targetY;

    //移动的总距离
    private int totalDistanceX;
    private int totalDistanceY;

    //头像最初的位置
    private int originalX = 0;
    private int originalY = 0;

    //最小尺寸与最大尺寸的比例
    float sizeRate = avatarMinWidth / avatarMaxWidth;
    public AvatarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        stateBarHeight = DisplayUtil.getInstance(mContext).getStateBarHeight();
        toolBarHeight = mContext.getResources().getDimension(R.dimen.tool_bar_height);

        targetX = DensityUtil.dip2px(mContext, 16) - (DensityUtil.dip2px(mContext, avatarMaxWidth)- DensityUtil.dip2px(mContext, avatarMinWidth))/2;
        targetY = stateBarHeight + DensityUtil.dip2px(mContext, 15) - (DensityUtil.dip2px(mContext, avatarMaxWidth)- DensityUtil.dip2px(mContext, avatarMinWidth))/2;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        mAppBarLayout = (AppBarLayout) dependency;
        appBarTotalScrollRange = mAppBarLayout.getTotalScrollRange();
        totalDistanceX = (int) (targetX - child.getX());
        totalDistanceY = (int) (targetY - child.getY());
        int appBarCurrentBottom = (int) (mAppBarLayout.getBottom() - mContext.getResources().getDimension(R.dimen.personal_tab_height));
        int appBarDy = (int) (appBarTotalScrollRange - (appBarCurrentBottom - (stateBarHeight + toolBarHeight)));
        float appBarDyPercent = (float) appBarDy / appBarTotalScrollRange;
        int currentChildViewWidth = (int) (avatarMaxWidth * (1 - (1 - sizeRate) * appBarDyPercent));

        child.setScaleX(((float) DensityUtil.dip2px(mContext,currentChildViewWidth))/child.getWidth());
        child.setScaleY(((float) DensityUtil.dip2px(mContext,currentChildViewWidth))/child.getWidth());

        child.offsetLeftAndRight((int) (totalDistanceX * appBarDyPercent));
        child.offsetTopAndBottom((int) (totalDistanceY * appBarDyPercent));

      //  LogUtil.printLog("targetX:"+targetX+"  targetY:"+targetY+"  X:"+child.getX()+"  Y:"+child.getY());
        return true;
    }
}
