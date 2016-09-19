package com.xter.picbrowser.util;

import android.app.Application;

/**
 * Created by XTER on 2015/10/14.
 * 全局单例
 */
public class ContextUtils extends Application {
	/* 获取context */
	private static ContextUtils instance;

	public static ContextUtils getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

}
