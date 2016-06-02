package com.xter.picbrowser.util;

import android.util.Log;

public class LogUtils {

	public static void v(String msg) {
		Log.v(getMethodPath(4, 4), msg);
	}

	public static void i(String msg) {
		Log.i(getMethodPath(4, 4), msg);
	}

	public static void d(String msg) {
		Log.d(getMethodPath(4, 4), msg);
	}

	public static void w(String msg) {
		Log.w(getMethodPath(4, 4), msg);
	}

	public static void e(String msg) {
		Log.e(getMethodPath(4, 4), msg);
	}

	/* 得到调用此方法的类名与方法名，默认下标3 */
	public static String getMethodPath() {
		return Thread.currentThread().getStackTrace()[3].getClassName() + "."
				+ Thread.currentThread().getStackTrace()[3].getMethodName() + "-->";
	}

	/* 测试方法，将线程中的序列全部输出 */
	public static void logThreadSequence() {
		int length = Thread.currentThread().getStackTrace().length;
		for (int i = 0; i < length; i++) {
			Log.i(Thread.currentThread().getStackTrace()[i].getClassName(),
					Thread.currentThread().getStackTrace()[i].getMethodName());
		}
	}

	/* 得到调用此方法的类名与方法名 */
	public static String getMethodPath(int classPrior, int methodPrior) {
		int length = Thread.currentThread().getStackTrace().length;
		if (classPrior > length || methodPrior > length) {
			return null;
		} else
			return Thread.currentThread().getStackTrace()[classPrior].getClassName() + "."
					+ Thread.currentThread().getStackTrace()[methodPrior].getMethodName() + "-->";
	}
}
