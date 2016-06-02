package com.xter.picbrowser.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xter.picbrowser.R;
import com.xter.picbrowser.util.LogUtils;
import com.xter.picbrowser.util.SysUtils;

/**
 * Created by XTER on 2016/2/16.
 */
public class CascadeAlbum extends ViewGroup {

	private int hSpace;
	private int vSpace;

	public CascadeAlbum(Context context) {
		super(context);
	}

	public CascadeAlbum(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CascadeAlbum(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	protected void init(Context context, AttributeSet attrs) {
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CascadeAlbum);
		hSpace = a.getDimensionPixelSize(R.styleable.CascadeAlbum_horizontal_padding, getResources().getDimensionPixelSize(R.dimen.dimen_10));
		vSpace = a.getDimensionPixelSize(R.styleable.CascadeAlbum_vertical_padding, getResources().getDimensionPixelSize(R.dimen.dimen_10));
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int x = getPaddingLeft();
		int y = getPaddingTop();

		LogUtils.i("width -->" + x + " " + "height -->" + y);

		int chSpace = 0;
		int cvSpace = 0;

		//子视图测量
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);

			LayoutParam lp = (LayoutParam) child.getLayoutParams();

			chSpace += lp.hSpace;
			cvSpace += lp.vSpace;

			x = getPaddingLeft() + hSpace * i + chSpace;
			y = getPaddingTop() + vSpace * i + cvSpace;

			LogUtils.i(i + ":" + "width -->" + x + " " + "height -->" + y);

			//子视图坐标
			lp.l = x;
			lp.t = y;

			LogUtils.i("child" + i + "? " + child.getMeasuredWidth() + "," + child.getMeasuredHeight());
		}

		x += getPaddingRight();
		y += getPaddingBottom();

		LogUtils.i("width -->" + x + " " + "height -->" + y);


		//EXACTLY模式即match_parent下可自行获取较大值，AT_MOST模式即wrap_content下需要代码得到（不过也只是取掉高2位的值，即取掉代表模式的值）
		//怎么看都像在取最大值~~
		int finalWidth = getDefaultSize(x, widthMeasureSpec);
		int finalHeight = getDefaultSize(y, heightMeasureSpec);

		LogUtils.i("width final:" + finalWidth + " " + "height final:" + finalHeight);

		//决定此容器的视图大小
		setMeasuredDimension(finalWidth, finalHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int count = getChildCount();
//		for(int i=count-1;i>=0;i--){
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			LayoutParam lp = (LayoutParam) child.getLayoutParams();

			child.layout(lp.l, lp.t, lp.l + child.getMeasuredWidth(), lp.t + child.getMeasuredHeight());
			LogUtils.i("" + (lp.l + child.getMeasuredWidth()));
		}
	}


	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		LogUtils.i("invoke");
		return p instanceof LayoutParam;
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		LogUtils.i("invoke");
		return new LayoutParam(LayoutParam.WRAP_CONTENT, LayoutParam.WRAP_CONTENT);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		LogUtils.i("invoke1");
		return new LayoutParam(getContext(), attrs);
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		LogUtils.i("invoke2");
		return new LayoutParam(p.width, p.height);
	}

	public static class LayoutParam extends LayoutParams {

		int l;
		int t;
		int r;
		int b;
		public int vSpace;
		public int hSpace;

		public LayoutParam(Context c, AttributeSet attrs) {
			super(c, attrs);
			final TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CascadeAlbum_LayoutParam);
			hSpace = a.getDimensionPixelSize(R.styleable.CascadeAlbum_LayoutParam_layout_horizontal_padding, 0);
			vSpace = a.getDimensionPixelSize(R.styleable.CascadeAlbum_LayoutParam_layout_vertical_padding, 0);
			a.recycle();
		}

		public LayoutParam(int width, int height) {
			super(width, height);
		}
	}


}
