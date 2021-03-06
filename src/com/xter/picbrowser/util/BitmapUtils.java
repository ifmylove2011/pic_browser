package com.xter.picbrowser.util;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;

/**
 * Created by XTER on 2016/1/14.
 */
public class BitmapUtils {
	/**
	 * 由图像原始大小与目标View比较得出采样率
	 *
	 * @param options 参数设置
	 * @param reqWidth 目标宽度
	 * @param reqHeight 目标高度
	 * @return inSampleSize 采样率
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		if (reqHeight == 0 || reqWidth == 0)
			return 1;
		// 原始宽高信息
		final int height = options.outHeight;
		final int width = options.outWidth;
		// 计算采样率
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int h = height / 2;
			final int w = width / 2;
			while ((h / inSampleSize) >= reqHeight || (w / inSampleSize) >= reqWidth)
				inSampleSize *= 2;
		}
		LogUtils.i("inSampleSize:" + inSampleSize + ", raw:" + width + "*" + height+",req:"+reqWidth+"*"+reqHeight);
		return inSampleSize;
	}

	/**
	 * 得到方形图
	 *
	 * @param bitmap 位图对象
	 * @return
	 */
	public static Bitmap getSquareBitmap(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		final int rawWidth = bitmap.getWidth();
		final int rawHeight = bitmap.getHeight();
		LogUtils.i("raw:" + rawWidth + "," + rawHeight);
		final int startX, startY, reqSize;
		if (rawWidth > rawHeight) {
			reqSize = rawHeight;
			startX = (rawWidth - reqSize) / 2;
			startY = 0;
		} else {
			reqSize = rawWidth;
			startX = 0;
			startY = (rawHeight - reqSize) / 2;
		}
		return Bitmap.createBitmap(bitmap, startX, startY, reqSize, reqSize);
	}

	/**
	 * 自定义图像大小
	 *
	 * @param uri 地址
	 * @param reqWidth 要求宽度
	 * @param reqHeight 要高度
	 * @return bitmap 图像资源
	 */
	public static Bitmap decodeBitmapFromFile(String uri, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(uri, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(uri, options);
	}

	/**
	 * @param path
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Bitmap decodeBitmapFromPath(String path, int reqWidth, int reqHeight)
			throws FileNotFoundException, IOException {
		@SuppressWarnings("resource")
		FileDescriptor fd = new FileInputStream(path).getFD();
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFileDescriptor(fd, null, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFileDescriptor(fd, null, options);
	}

	/**
	 * @param fd
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Bitmap decodeBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight)
			throws IOException {
		@SuppressWarnings("resource")
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFileDescriptor(fd, null, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFileDescriptor(fd, null, options);
	}

	public static Bitmap decodeBitmapFromFileDescriptor(FileDescriptor fd) throws IOException {
		@SuppressWarnings("resource")
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inJustDecodeBounds = false;
		LogUtils.d("加载原图");
		return BitmapFactory.decodeFileDescriptor(fd, null, options);
	}

	public static Bitmap getSquareBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight)
			throws IOException {
		@SuppressWarnings("resource")
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFileDescriptor(fd, null, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inJustDecodeBounds = false;
		return getSquareBitmap(BitmapFactory.decodeFileDescriptor(fd, null, options));
	}

	/**
	 * 自定义图像大小
	 *
	 * @param res 文件资源
	 * @param resId 布局组件ID
	 * @param reqWidth 要求宽度
	 * @param reqHeight 要求高度
	 * @return bitmap 图像资源
	 */
	public static Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		// 计算采样率（大小比例）
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * 得到图像大小
	 *
	 * @param bitmap 传入位图对象
	 * @return bytes 图像大小
	 */
	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
			// 12
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
	}

	public static Bitmap getDefaultBitmap() {
		return Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
	}

	/**
	 * 合并图片
	 *
	 * @param bitmaps 位图资源
	 * @param size 大小
	 * @param padding 间距
	 * @return bitmap 位图
	 */
	public static Bitmap combineBitmaps(Bitmap[] bitmaps, int size, int padding) {
		int length = bitmaps.length;
		int columns = (int) (length / Math.sqrt(length) >= Math.sqrt(length) ? Math.sqrt(length)
				: Math.sqrt(length) + 1);
		Bitmap bitmap = Bitmap.createBitmap(columns * size + (columns - 1) * padding,
				columns * size + (columns - 1) * padding, Bitmap.Config.RGB_565);
		Paint paint = new Paint();
		Canvas c = new Canvas(bitmap);
		for (int i = 0; i < length; i++) {
			c.drawBitmap(bitmaps[i], i % columns * size + (i % columns - 1) * padding,
					i / columns * size + (i / columns - 1) * padding, paint);
		}
		return bitmap;
	}

	/**
	 * 高斯模糊效果
	 * @param context
	 * @param sentBitmap
	 * @param radius
	 * @return
	 */
	public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int temp = 256 * divsum;
		int dv[] = new int[temp];
		for (i = 0; i < temp; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}

		bitmap.setPixels(pix, 0, w, 0, 0, w, h);
		return bitmap;
	}
	
}
