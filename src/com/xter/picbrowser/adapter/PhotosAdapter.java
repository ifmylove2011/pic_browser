package com.xter.picbrowser.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.xter.picbrowser.R;
import com.xter.picbrowser.element.Photo;
import com.xter.picbrowser.view.AlbumGridView;
import com.xter.picbrowser.view.SquareImageView;

import android.content.Context;
import android.graphics.Bitmap;
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
	DisplayImageOptions options;

	/**
	 * 传入图片URL
	 *
	 * @param context 所依赖的上下文环境
	 * @param list    图像资源列表
	 */
	public PhotosAdapter(Context context, List<Photo> list) {
		initConfig();
		layoutInflater = LayoutInflater.from(context);
		this.photos = list;
//		loader = ImageLoader.build(context);
	}

	protected void initConfig() {
		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.mipmap.loading) //设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.mipmap.empty_pic)//设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.mipmap.empty_pic)  //设置图片加载/解码过程中错误时候显示的图片
				.cacheOnDisk(true)
				.considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
				.resetViewBeforeLoading(false)//设置图片在下载前是否重置，复位
				.displayer(new FadeInBitmapDisplayer(50))//是否图片加载好后渐入的动画时间
				.build();//构建完成
		loader = ImageLoader.getInstance();
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
		//获取图像资源的URL
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
				Log.w("load",""+url);
				ImageAware imageAware = new ImageViewAware(ivGalley, false);
				loader.displayImage(url, imageAware, options);
//				loader.bindBitmap(url, ivGalley);
			}
		}
		return view;
	}

}
