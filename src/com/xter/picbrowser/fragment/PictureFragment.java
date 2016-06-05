package com.xter.picbrowser.fragment;

import java.util.ArrayList;
import java.util.List;

import com.xter.picbrowser.R;
import com.xter.picbrowser.adapter.PictureAdpater;
import com.xter.picbrowser.element.Photo;
import com.xter.picbrowser.util.LogUtils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass. 单独显示某张图片，使用了viewpager
 */
public class PictureFragment extends Fragment {

	public interface OnPictureStateListener {
		void onPictureState(boolean state);
	}

	public static final int DELAY_TIME = 2000;
	public static final int SCROLL = 1;
	public static final int AUTO_PLAY = 2;
	public static final int SHARE = 3;

	private ViewPager vpPics;
	private List<Photo> photos;
	private int pos;

	private Activity mContext;
	private OnPictureStateListener onPictureStateListener;
	private String folderName;

	private Handler mHandler;

	ImageInfoFragment infoFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		photos = bundle.getParcelableArrayList("pics");
		pos = bundle.getInt("pos");
		folderName = bundle.getString("folderName");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_picture, container, false);
		initLayout(view);
		initData();
		return view;
	}

	protected void initLayout(View view) {
		onPictureStateListener.onPictureState(true);
		mContext.getActionBar().setTitle(photos.get(pos).getName());
		setHasOptionsMenu(true);
		vpPics = (ViewPager) view.findViewById(R.id.vp_pics);
	}

	protected void initData() {

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SCROLL:
					LogUtils.i("scroll");
					int position = (Integer) msg.obj;
					mContext.getActionBar().setTitle(photos.get(position).getName());
					vpPics.setCurrentItem(position);
					break;
				case AUTO_PLAY:
					int curIndex = (Integer) msg.obj;
					LogUtils.i("auto " + curIndex + ",total" + photos.size());
					mContext.getActionBar().setTitle(photos.get(curIndex).getName());
					vpPics.setCurrentItem(curIndex);
					curIndex++;
					if (curIndex < photos.size()) {
						mHandler.sendMessageDelayed(mHandler.obtainMessage(AUTO_PLAY, curIndex), DELAY_TIME);
					}
					break;
				case SHARE:
					LogUtils.i("share");
					shareImg(getString(R.string.action_share),
							Uri.parse("file://" + photos.get(vpPics.getCurrentItem()).getPath()));
					break;
				}
				return;
			}
		};
		// 初始化内容
		ArrayList<View> views = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		int length = photos.size();
		for (int i = 0; i < length; i++) {
			View view = inflater.inflate(R.layout.item_image, null);
			views.add(view);
		}
		// 适配器
		vpPics.setAdapter(new PictureAdpater(mContext, views, photos));
		// 监听器
		// vpPics.addOnPageChangeListener(new
		// ViewPager.SimpleOnPageChangeListener() {
		// @Override
		// public void onPageSelected(int position) {
		// }
		// });
	}

	// 分享图片
	protected void shareImg(String dlgTitle, Uri uri) {
		if (uri == null) {
			return;
		}
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_STREAM, uri);

		// 设置弹出框标题
		if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
			startActivity(Intent.createChooser(intent, dlgTitle));
		} else { // 系统默认标题
			startActivity(intent);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.menu_picture, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_detail:
			if (infoFragment == null)
				infoFragment = new ImageInfoFragment();
			Bundle bundle = new Bundle();
			int index = vpPics.getCurrentItem();
			bundle.putString("index", (index + 1) + "/" + photos.size());
			bundle.putParcelable("info", photos.get(index));
			infoFragment.setArguments(bundle);
			infoFragment.show(getFragmentManager(), "info");
			return true;
		case R.id.action_auto_play:
			onPictureStateListener.onPictureState(true);
			mHandler.obtainMessage(AUTO_PLAY, vpPics.getCurrentItem()).sendToTarget();
			return true;
		case R.id.action_share:
			mHandler.obtainMessage(SHARE).sendToTarget();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
		if (onPictureStateListener == null)
			onPictureStateListener = (OnPictureStateListener) activity;
	}

	@Override
	public void onStart() {
		super.onStart();
		Bundle bundle = getArguments();
		pos = bundle.getInt("pos");
		vpPics.setCurrentItem(pos);
		mContext.getActionBar().setTitle(photos.get(pos).getName());
	}

	@Override
	public void onStop() {
		super.onStop();
		mHandler.removeMessages(AUTO_PLAY);
		mHandler.removeMessages(SCROLL);
		onPictureStateListener.onPictureState(false);
		getActivity().getActionBar().setTitle(folderName);
	}
}
