package com.xter.picbrowser.adapter;

import java.util.ArrayList;
import java.util.List;

import com.xter.picbrowser.R;
import com.xter.picbrowser.element.Photo;
import com.xter.picbrowser.event.StateEvent;
import com.xter.picbrowser.util.ImageLoader;
import com.xter.picbrowser.util.LogUtils;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.greenrobot.eventbus.EventBus;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by XTER on 2016/1/18. 图像显示适配器，主要用于单独显示图片
 */
public class PictureAdpater extends PagerAdapter {

	private ArrayList<View> viewlist;
	ImageLoader loader;
	List<Photo> photos;

	/**
	 * @param activity 所依赖的上下文环境
	 * @param viewlist 视图列表
	 * @param photos   文件夹中的所有图像资源文件
	 */
	public PictureAdpater(Activity activity, ArrayList<View> viewlist, List<Photo> photos) {
		this.viewlist = viewlist;
		this.photos = photos;
		loader = ImageLoader.build(activity);
	}


	@Override
	public int getCount() {
		return viewlist.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// 不在这里调用removeView
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 对ViewPager页号求模取出View列表中要显示的项
		// position %= viewlist.size();
		// if (position < 0) {
		// position = viewlist.size() + position;
		// }
		View view = viewlist.get(position);
		PhotoView iv = (PhotoView) view.findViewById(R.id.item_image);
		iv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View view, float x, float y) {
				EventBus.getDefault().post(new StateEvent(true));
			}
		});
		String uri = "file://" + photos.get(position).getPath();
		loader.bindBitmap(uri, iv, 0, 0, false);
		// 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
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
