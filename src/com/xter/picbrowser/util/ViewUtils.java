package com.xter.picbrowser.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by XTER on 2016/1/24.
 */
public class ViewUtils {
	@SuppressLint("NewApi")
	public static void hideSystemUI(View view) {
		view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	}

	@SuppressLint("NewApi")
	public static void showSystemUI(View view) {
		view.setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	}

	/* 获得状态栏高度 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/* 获取操作拦高度 */
	public static int getActionBarHeight(Context context) {
		int actionBarHeight = 0;
		TypedValue tv = new TypedValue();
		if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
		}
		return actionBarHeight;
	}

	/* 得到系统栏高度 */
	public static int getSystemBarHeight(Context context){
		return getActionBarHeight(context)+getStatusBarHeight(context);
	}

	/* 得到系统栏（包括状栏和操作栏）而已参数 */
	public static LinearLayout.LayoutParams getSystemBarParam(Context context) {
		int occupyHeight = getActionBarHeight(context) + getStatusBarHeight(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, occupyHeight);
		return layoutParams;
	}

	/*  获取屏幕像素大小 */
	public static Point getScreenSize() {
		DisplayMetrics dm = ContextUtils.getInstance().getResources().getDisplayMetrics();
		Point size = new Point(dm.widthPixels, dm.heightPixels);
		return size;
	}
}
