package com.xter.picbrowser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.xter.picbrowser.util.LogUtils;

/**
 * Created by XTER on 2016/1/13.
 */
public class AlbumGridView extends GridView {
	public boolean isOnMeasure;

	public AlbumGridView(Context context) {
		super(context);
	}

	public AlbumGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AlbumGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		isOnMeasure = true;
		int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//		LogUtils.d("albumGrid" + widthMeasureSpec + "," + height);
		super.onMeasure(widthMeasureSpec, height);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		LogUtils.d("albumGrid" + changed + "," + l + "," + t + "," + r + "," + b);
		isOnMeasure = false;
		super.onLayout(changed, l, t, r, b);
	}

	public boolean isOnMeasure() {
		return isOnMeasure;
	}
}
