package com.xter.picbrowser.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xter.picbrowser.R;

public class CascadeView extends View {

	public CascadeView(Context context) {
		super(context);
	}

	public CascadeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttrs(context, attrs);
	}

	public CascadeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAttrs(context, attrs);
	}

	@SuppressLint("NewApi")
	public CascadeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initAttrs(context, attrs);
	}

	private boolean isReady;
	private int totalWidth;
	private int totalHeight;
	private int imgSize;
	private Bitmap[] bitmaps;

	private int columns;
	private int row;
	private int imgCounts;

	private Rect rect;
	private Paint paint;

	protected void initAttrs(Context context, AttributeSet attrs) {
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CascadeView);
		final int count = a.getIndexCount();
		for (int i = 0; i < count; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CascadeView_columns:
				columns = a.getInt(attr, 2);
				break;
			}
		}
		a.recycle();
		rect = new Rect();
		paint = new Paint();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (isReady) {
			int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
			int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);

			Log.i("specWidth", ":" + specWidthSize);

			if (specWidthMode == MeasureSpec.EXACTLY) {
				totalWidth = specWidthSize;
			} else {
				int totalBitmapWidth = imgSize * columns + getPaddingLeft() + getPaddingRight();
				if (specWidthMode == MeasureSpec.AT_MOST) {
					totalWidth = Math.min(specWidthSize, totalBitmapWidth);
				}
			}

			int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
			int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);

			Log.i("specHeight", ":" + specHeightSize);

			if (specHeightMode == MeasureSpec.EXACTLY) {
				totalHeight = specHeightSize;
			} else {
				int totalBitmapHeight = imgSize * row + getPaddingTop() + getPaddingBottom();
				if (specHeightMode == MeasureSpec.AT_MOST) {
					totalHeight = Math.min(specHeightSize, totalBitmapHeight);
				}
			}
			Log.i("size", ":" + totalWidth + "," + totalHeight + "-->" + imgSize);
			setMeasuredDimension(totalWidth, totalHeight);
		} else {
			setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (isReady) {
			int paddingH = (totalWidth - imgSize * columns) / 2;
			int paddingV = (totalHeight - imgSize * row) / 2;
			for (int i = 0; i < imgCounts; i++) {
				rect.left = imgSize * (i % columns) + paddingH;
				rect.top = imgSize * (i / columns) + paddingV;
				rect.right = imgSize + rect.left;
				rect.bottom = imgSize + rect.top;
				 Log.i("location", ":" + rect.left + "," + rect.top + "," +
				 rect.right + "," + rect.bottom);
				canvas.drawBitmap(bitmaps[i], null, rect, paint);
			}
		}
	}


	public void setBitmaps(Bitmap[] bitmaps) {
		this.bitmaps = bitmaps;
		imgCounts = bitmaps.length;
		row = imgCounts % columns == 0 ? imgCounts / columns : imgCounts / columns + 1;
		isReady = true;
		imgSize = bitmaps[0].getWidth();
		// reset(imgSize);
		requestLayout();
		 invalidate();
		Log.i("setBitmaps", "Reset!");
	}

}
