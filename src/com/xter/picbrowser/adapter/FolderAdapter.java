package com.xter.picbrowser.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xter.picbrowser.R;
import com.xter.picbrowser.element.Folder;
import com.xter.picbrowser.util.ImageLoader;
import com.xter.picbrowser.view.AlbumGridView;
import com.xter.picbrowser.view.SquareImageView;

import java.util.List;

/**
 * Created by XTER on 2016/1/11.
 * 直接存储view以达到缓存已经下载的图片
 * 能否将viewholder和list缓存结合使用？
 */
public class FolderAdapter extends BaseAdapter {

	LayoutInflater layoutInflater;
	List<Folder> folders;
	ImageLoader loader;
//	DisplayImageOptions options;

	/**
	 * 主要传入文件夹URL值
	 *
	 * @param context 所依赖的上下文
	 * @param list    文件夹
	 */
	public FolderAdapter(Context context, List<Folder> list) {
//		initConfig();
		this.layoutInflater = LayoutInflater.from(context);
		this.folders = list;
		loader =ImageLoader.build(context);
	}

//	/* 初始化load的参数 */
//	protected void initConfig() {
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.mipmap.loading) //设置图片在下载期间显示的图片
//				.showImageForEmptyUri(R.mipmap.empty_pic)//设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.mipmap.empty_pic)  //设置图片加载/解码过程中错误时候显示的图片
////				.cacheInMemory(true)//设置下载的图片是否缓存在内存中
//				.cacheOnDisk(true)
//				.considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
//				.imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示
//				.bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
//				.resetViewBeforeLoading(false)//设置图片在下载前是否重置，复位
//				.displayer(new FadeInBitmapDisplayer(50))//是否图片加载好后渐入的动画时间
//				.build();//构建完成
//		loader = ImageLoader.getInstance();
//	}

	@Override
	public int getCount() {
		return folders.size();
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

		View view;
		if (convertView == null) {
			view = layoutInflater.inflate(R.layout.item_folder_album, parent, false);
		} else {
			view = convertView;
		}


		if (parent instanceof AlbumGridView) {
			if (((AlbumGridView) parent).isOnMeasure()) {
				return view;
			} else {
				SquareImageView ivFolderImage = (SquareImageView) view.findViewById(R.id.iv_item_folder_album);
				TextView tvFolderCapacity = (TextView) view.findViewById(R.id.tv_item_folder_cap);
				TextView tvFolderName = (TextView) view.findViewById(R.id.tv_item_folder_url);

				String[] uris = folders.get(position).getCoverUris();

//				ImageAware imageAware = new ImageViewAware(ivFolderImage, false);
//				loader.displayImage(uris[0], imageAware, options);
				loader.bindBitmap(uris[0], ivFolderImage);
				tvFolderCapacity.setText(String.valueOf(folders.get(position).getImgCount()));
				tvFolderName.setText(folders.get(position).getFolderName());
			}
		}
		return view;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

}
