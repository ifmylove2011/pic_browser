package com.xter.picbrowser.adapter;

import java.util.List;

import com.xter.picbrowser.R;
import com.xter.picbrowser.element.Photo;
import com.xter.picbrowser.util.ImageLoader;
import com.xter.picbrowser.view.AlbumGridView;
import com.xter.picbrowser.view.SquareImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by XTER on 2016/1/13.
 */
public class PhotosAdapter extends BaseAdapter {

	LayoutInflater layoutInflater;
	List<Photo> photos;
	ImageLoader loader;
	// DisplayImageOptions options;

	/**
	 * 传入图片URL
	 *
	 * @param context 所依赖的上下文环境
	 * @param list 图像资源列表
	 */
	public PhotosAdapter(Context context, List<Photo> list) {
		// initConfig();
		layoutInflater = LayoutInflater.from(context);
		this.photos = list;
		loader = ImageLoader.build(context);
	}


	@Override
	public int getCount() {
		return photos.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 获取图像资源的URL
		View view;
		if (convertView == null) {
			view = layoutInflater.inflate(R.layout.item_photos_album, parent, false);
		} else {
			view = convertView;
		}

		if (parent instanceof AlbumGridView) {
			if (((AlbumGridView) parent).isOnMeasure()) {
				return view;
			} else {
				String url = "file://" + photos.get(position).getPath();
				SquareImageView ivGalley = (SquareImageView) view.findViewById(R.id.iv_item_galley_album);
				Log.w("load", "" + url);
				// ImageAware imageAware = new ImageViewAware(ivGalley, false);
				// loader.displayImage(url, imageAware, options);
				loader.bindBitmap(url, ivGalley, 150, 150, true);
			}
		}
		return view;
	}

}
