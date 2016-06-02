package com.xter.picbrowser.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by XTER on 2016/1/11.
 */
public class SysUtils {

	/* 获取可用的存储路径 */
	public static String getAvailableStorage() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		else
			return null;
	}

	/**
	 * 获取磁盘缓存路径
	 *
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getDiskCacheDir(Context context, String fileName) {
		final String cachePath;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return cachePath + File.separator + fileName;
	}

	/* 将系统长整值转为时间格式 */
	public static String getFormatDate(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
		return sdf.format(new Date(date));
	}

	/**
	 * 字节转十六进制
	 *
	 * @param bytes
	 * @return
	 */
	public String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1)
				sb.append("0");
			sb.append(hex);
		}
		return sb.toString();
	}

	/* px转为dp */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/* dp转为px */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/* px转为sp */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/* sp转为px */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/* 闹钟 */
	public static void setAlarmTime(Context context, long time) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("com.xter.receiver.Life");
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, sender);
	}

	public static void cancelAlarm(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("com.xter.receiver.Life");
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.cancel(sender);
	}
}
