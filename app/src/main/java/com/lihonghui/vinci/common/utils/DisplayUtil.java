package com.lihonghui.vinci.common.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

/**
 * 屏幕参数、操作工具
 *
 * @author Lhh
 *
 */
public class DisplayUtil {
	/** 屏幕宽度——px */
	private int screenWidth;
	/** 屏幕高度——px */
	private int screenHeight;
	/** 屏幕密度——dpi */
	private int densityDpi;
	/** 缩放系数——densityDpi/160 */
	private float scale;
	/** 文字缩放系数 */
	private float fontScale;

	private static DisplayUtil singleInstance;
	private Context mContext;

	/**
	 * 私有构造方法
	 *
	 * @param context
	 */
	private DisplayUtil(Context context) {
		mContext = context;
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		densityDpi = dm.densityDpi;
		scale = dm.density;
		fontScale = dm.scaledDensity;
	}

	/**
	 * 获取实例
	 *
	 * @param context
	 * @return
	 */
	public static DisplayUtil getInstance(Context context) {
		if (singleInstance == null) {
			singleInstance = new DisplayUtil(context);
		}
		return singleInstance;
	}

	/**
	 * 获取状态栏高度
	 */
	public int getStateBarHeight() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = mContext.getResources().getDimensionPixelSize(x);
			return sbar;
		} catch(Exception e1) {
			return 0;
		}

	}

	/**
	 * 获取新的实例
	 *
	 * @param context
	 * @return
	 */
	public static DisplayUtil getNewInstance(Activity context) {
		if (singleInstance != null) {
			singleInstance = null;
		}
		return getInstance(context);
	}

	/**
	 * 布局内容覆适配态栏
	 * @param rootView
	 */
	public void fitStatusBar(View rootView) {
		LayoutParams params = rootView.getLayoutParams();
		if (params instanceof RelativeLayout.LayoutParams) {
			((RelativeLayout.LayoutParams) params).setMargins(0, getStateBarHeight(), 0, 0);
		} else if (params instanceof LinearLayout.LayoutParams) {
			((LinearLayout.LayoutParams) params).setMargins(0, getStateBarHeight(), 0, 0);
		} else if (params instanceof FrameLayout.LayoutParams) {
			((FrameLayout.LayoutParams) params).setMargins(0, getStateBarHeight(), 0, 0);
		}
		rootView.setLayoutParams(params);
	}

	/**
	 * 布局内容覆盖状态栏
	 * @param rootView 根布局即contentView
     */
	public void coverStatusBar(View rootView) {
		LayoutParams params = rootView.getLayoutParams();
		if (params instanceof RelativeLayout.LayoutParams) {
			((RelativeLayout.LayoutParams) params).setMargins(0, 0, 0, 0);
		} else if (params instanceof LinearLayout.LayoutParams) {
			((LinearLayout.LayoutParams) params).setMargins(0, 0, 0, 0);
		} else if (params instanceof FrameLayout.LayoutParams) {
			((FrameLayout.LayoutParams) params).setMargins(0, 0, 0, 0);
		}
		rootView.setLayoutParams(params);
	}

	/**
	 *获取屏幕高度
	 */
	public int getScreenHeight(){
		return this.screenHeight;
	}

	/**
	 * 获取屏幕宽度
	 */
	public int getScreenWidth(){
		return this.screenWidth;
	}
}