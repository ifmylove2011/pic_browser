package com.xter.picbrowser.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.xter.picbrowser.R;
import com.xter.picbrowser.adapter.PhotosAdapter;
import com.xter.picbrowser.element.Folder;
import com.xter.picbrowser.element.Photo;
import com.xter.picbrowser.event.PhotoEvent;
import com.xter.picbrowser.util.LogUtils;
import com.xter.picbrowser.util.ViewUtils;
import com.xter.picbrowser.view.AlbumGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 具体显示某个目录下的图像资源
 */
public class PhotosFragment extends Fragment {

	private View viewSpace;
	private AlbumGridView gvPhotosAlbum;

	private List<Photo> photos;
	private String folderName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.i("photo fragment create");
		Bundle bundle = getArguments();
		Folder folder = bundle.getParcelable("folder");
		if (folder != null){
			photos = folder.getPhotos();
			folderName = folder.getFolderName();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_photos, container, false);
		initLayout(view);
		initData();
		return view;
	}

	protected void initLayout(View view) {
		getActivity().getActionBar().setTitle(folderName);
		//响应菜单
		setHasOptionsMenu(true);
		//设置空白区域（点位）
		viewSpace = view.findViewById(R.id.view_space);
		viewSpace.setLayoutParams(ViewUtils.getSystemBarParam(getActivity()));
		gvPhotosAlbum = (AlbumGridView) view.findViewById(R.id.gv_photos);
	}


	protected void initData() {
		PhotosAdapter photosAdapter = new PhotosAdapter(getActivity(), photos);
		gvPhotosAlbum.setAdapter(photosAdapter);

		gvPhotosAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				EventBus.getDefault().post(new PhotoEvent(photos, position, folderName));
			}
		});

		//使空白区域获取焦点，避免因为gridview抢夺焦点而使其无法显示空白区域
		viewSpace.setFocusable(true);
		viewSpace.setFocusableInTouchMode(true);
		viewSpace.requestFocus();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.menu_photo, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
//			case R.id.action_rename:
//				Toast.makeText(getActivity(), getString(R.string.action_rename), Toast.LENGTH_SHORT).show();
//				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		getActivity().setTitle(folderName);
	}

	@Override
	public void onStop() {
		super.onStop();
		getActivity().getActionBar().setTitle(getString(R.string.app_name));
	}

}
