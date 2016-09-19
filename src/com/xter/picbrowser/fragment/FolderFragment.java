package com.xter.picbrowser.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.xter.picbrowser.R;
import com.xter.picbrowser.adapter.FolderAdapter;
import com.xter.picbrowser.element.Folder;
import com.xter.picbrowser.event.FolderEvent;
import com.xter.picbrowser.util.ImageLoader;
import com.xter.picbrowser.util.ViewUtils;
import com.xter.picbrowser.view.AlbumGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * 文件夹页面
 */
public class FolderFragment extends Fragment {

	private View viewSpace;
	private AlbumGridView gvFolderAlbum;
	private FolderAdapter folderAdapter;

	private List<Folder> folders;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		folders = bundle.getParcelableArrayList("folders");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_folder, container, false);
		initLayout(view);
		initData();
		return view;
	}

	protected void initLayout(View view) {
		//设置空白区域（占位）
		viewSpace = view.findViewById(R.id.view_space);
		viewSpace.setLayoutParams(ViewUtils.getSystemBarParam(getActivity()));

		gvFolderAlbum = (AlbumGridView) view.findViewById(R.id.gv_folder);
	}

	protected void initData() {
		folderAdapter = new FolderAdapter(getActivity(), folders);
		gvFolderAlbum.setAdapter(folderAdapter);

		//设置滑动与猛滑动时暂停加载
//		gvFolderAlbum.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
		//点击事件
		gvFolderAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				EventBus.getDefault().post(new FolderEvent(folders.get(position)));
			}
		});
		//使空白区域获取焦点，避免因为gridview抢夺焦点而使其无法显示空白区域
		viewSpace.setFocusable(true);
		viewSpace.setFocusableInTouchMode(true);
		viewSpace.requestFocus();
	}

}
