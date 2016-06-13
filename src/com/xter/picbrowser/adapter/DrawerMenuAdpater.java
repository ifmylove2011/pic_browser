package com.xter.picbrowser.adapter;

import com.xter.picbrowser.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by XTER on 2016/1/31.
 */
public class DrawerMenuAdpater extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private int[] menuImages;
	private String[] menuTexts;

	public DrawerMenuAdpater(Context context, int[] imgSources, String[] textSources) {
		layoutInflater = LayoutInflater.from(context);
		menuImages = imgSources;
		menuTexts = textSources;
	}

	@Override
	public int getCount() {
		return menuImages.length;
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
		DrawerMenuViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item_img_text, null);
			holder = new DrawerMenuViewHolder();

			holder.menuImage = (ImageView) convertView.findViewById(R.id.item_image);
			holder.menuText = (TextView) convertView.findViewById(R.id.item_text);

			convertView.setTag(holder);
		} else {
			holder = (DrawerMenuViewHolder) convertView.getTag();
		}

		holder.menuImage.setImageResource(menuImages[position]);
		holder.menuText.setText(menuTexts[position]);
		return convertView;
	}

	static class DrawerMenuViewHolder {
		ImageView menuImage;
		TextView menuText;
	}
}
