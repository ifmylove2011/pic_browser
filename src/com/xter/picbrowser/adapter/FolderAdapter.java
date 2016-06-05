package com.xter.picbrowser.adapter;

import java.util.List;

import com.xter.picbrowser.R;
import com.xter.picbrowser.element.Folder;
import com.xter.picbrowser.util.ImageLoader;
import com.xter.picbrowser.view.AlbumGridView;
import com.xter.picbrowser.view.SquareImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by XTER on 2016/1/11.
 * 直接存储view以达到缓存已经下载的图片
 * 能否将viewholder和list缓存结合使用？
 */
public class FolderAdapter extends BaseAdapter {

	LayoutInflater layoutInflater;
	List<Folder> folders;
	ImageLoader loader;

	/**
	 * 主要传入文件夹URL值
	 *
	 * @param context 所依赖的上下文
	 * @param list    文件夹
	 */
	public FolderAdapter(Context context, List<Folder> list) {
		this.layoutInflater = LayoutInflater.from(context);
		this.folders = list;
		loader =ImageLoader.build(context);
	}

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
