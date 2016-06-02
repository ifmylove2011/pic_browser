package com.xter.picbrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xter.picbrowser.R;

/**
 * Created by XTER on 2016/1/22.
 */
public class ImageInfoAdpater extends BaseAdapter {

	LayoutInflater layoutInflater;
	private String[] headers;
	private String[] infos;

	public ImageInfoAdpater(Context context, String[] headers, String[] infos) {
		layoutInflater = LayoutInflater.from(context);
		this.headers = headers;
		this.infos = infos;
	}

	@Override
	public int getCount() {
		return headers.length;
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
		listTextHolder holder;
		if (convertView == null) {
			holder = new listTextHolder();
			convertView = layoutInflater.inflate(R.layout.item_image_info, null);
			holder.tvHeader = (TextView) convertView.findViewById(R.id.tv_header);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
			convertView.setTag(holder);
		} else {
			holder = (listTextHolder) convertView.getTag();
		}
		holder.tvHeader.setText(headers[position]);
		holder.tvContent.setText(infos[position]);
		return convertView;
	}

	static class listTextHolder {
		TextView tvHeader;
		TextView tvContent;
	}
}
