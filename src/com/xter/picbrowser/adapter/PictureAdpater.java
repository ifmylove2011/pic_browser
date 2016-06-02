package com.xter.picbrowser.adapter;

import java.util.ArrayList;
import java.util.List;

import com.xter.picbrowser.R;
import com.xter.picbrowser.element.Photo;
import com.xter.picbrowser.util.ImageLoader;
import com.xter.picbrowser.util.LogUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by XTER on 2016/1/18.
 * 图像显示适配器，主要用于单独显示图片
 */
public class PictureAdpater extends PagerAdapter {

	/* 点击图像时的对外接口 */
	public interface OnPhotoViewClickListener {
		void onPhotoViewClick();
	}

	private ArrayList<View> viewlist;
	ImageLoader loader;
//	DisplayImageOptions options;
	List<Photo> photos;


	private OnPhotoViewClickListener onPhotoViewClickListener;

	/**
	 * @param activity 所依赖的上下文环境
	 * @param viewlist 视图列表
	 * @param photos   文件夹中的所有图像资源文件
	 */
	public PictureAdpater(Activity activity, ArrayList<View> viewlist, List<Photo> photos) {
//		initConfig();
		if (onPhotoViewClickListener == null)
			onPhotoViewClickListener = (OnPhotoViewClickListener) activity;
		this.viewlist = viewlist;
		this.photos = photos;
		loader = ImageLoader.build(activity);
	}

//	protected void initConfig() {
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.mipmap.loading) //设置图片在下载期间显示的图片
//				.showImageForEmptyUri(R.mipmap.empty_pic)//设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.mipmap.empty_pic)  //设置图片加载/解码过程中错误时候显示的图片
////				.cacheInMemory(true)//设置下载的图片是否缓存在内存中
//				.cacheOnDisk(true)
//				.considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
//				.imageScaleType(ImageScaleType.NONE)//设置图片以如何的编码方式显示
//				.bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
//				.resetViewBeforeLoading(false)//设置图片在下载前是否重置，复位
//				.displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
//				.build();//构建完成
//		loader = ImageLoader.getInstance();
//	}

	@Override
	public int getCount() {
		return viewlist.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position,
	                        Object object) {
		//不在这里调用removeView
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		//对ViewPager页号求模取出View列表中要显示的项
//		position %= viewlist.size();
//		if (position < 0) {
//			position = viewlist.size() + position;
//		}
		View view = viewlist.get(position);
		PhotoView iv = (PhotoView) view.findViewById(R.id.item_image);
		iv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View view, float x, float y) {
				onPhotoViewClickListener.onPhotoViewClick();
			}
		});
//		loader.displayImage("file://" + photos.get(position).getPath(), iv, options);
		loader.bindBitmap("file://" + photos.get(position).getPath(), iv);
		//如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
		ViewParent vp = view.getParent();
		if (vp != null) {
			ViewGroup parent = (ViewGroup) vp;
			parent.removeView(view);
		}
		container.addView(view);
		LogUtils.i("getview?");
		return view;
	}

}
