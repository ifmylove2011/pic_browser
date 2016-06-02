package com.xter.picbrowser.fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xter.picbrowser.R;
import com.xter.picbrowser.adapter.ImageInfoAdpater;
import com.xter.picbrowser.element.Photo;
import com.xter.picbrowser.util.SysUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * 图像单独显示时的详情页面
 */
public class ImageInfoFragment extends DialogFragment {

	private TextView tvImageIndex;
	private ListView lvImageInfo;
	private Button btnOk;

	private Photo photoInfo;
	private String index;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		index = bundle.getString("index");
		photoInfo = bundle.getParcelable("info");
		setCancelable(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.fragment_image_info, container, false);
		initLayout(view);
		initData();
		return view;
	}

	protected void initLayout(View view) {
		tvImageIndex = (TextView) view.findViewById(R.id.tv_image_index);
		lvImageInfo = (ListView) view.findViewById(R.id.lv_image_info);
		btnOk = (Button) view.findViewById(R.id.btn_ok);
	}

	protected void initData() {
		tvImageIndex.setText(index);
		lvImageInfo.setAdapter(getAdapter());
		btnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	protected ImageInfoAdpater getAdapter() {
		String[] infos = {photoInfo.getPath(), photoInfo.getWidth() + "*" + photoInfo.getHeight(), photoInfo.getSize() + "  bytes", SysUtils.getFormatDate(photoInfo.getDateModified() * 1000)};
		String[] headers = getResources().getStringArray(R.array.info_header);
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < infos.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(headers[i], infos[i]);
			list.add(map);
		}
		ImageInfoAdpater adpater = new ImageInfoAdpater(getActivity(), headers, infos);
		return adpater;
	}

}
