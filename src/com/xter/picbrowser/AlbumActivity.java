package com.xter.picbrowser;

import java.util.ArrayList;
import java.util.List;

import com.xter.picbrowser.adapter.DrawerMenuAdpater;
import com.xter.picbrowser.adapter.PictureAdpater;
import com.xter.picbrowser.data.DataLayer;
import com.xter.picbrowser.demo.DemoActivity;
import com.xter.picbrowser.element.Folder;
import com.xter.picbrowser.element.Photo;
import com.xter.picbrowser.event.FolderEvent;
import com.xter.picbrowser.event.PhotoEvent;
import com.xter.picbrowser.event.StateEvent;
import com.xter.picbrowser.fragment.FolderFragment;
import com.xter.picbrowser.fragment.PhotosFragment;
import com.xter.picbrowser.fragment.PictureFragment;
import com.xter.picbrowser.lib.SystemBarTintManager;
import com.xter.picbrowser.util.ViewUtils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class AlbumActivity extends Activity{

	//全屏flag
	public static int FULLSCREEN_STATE = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.INVISIBLE;

	private FolderFragment folderFragment;
	private PhotosFragment photosFragment;
	private PictureFragment pictureFragment;
	private FragmentManager fm;
	SystemBarTintManager tintManager;

	private DrawerLayout drawerMenu;
	private ListView lvDrawerMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_album);
		initSystemBar();
		initLayout();
		initData();
	}

	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		//避免保留状态
	}

	/**
	 * 初始化布局
	 */
	protected void initLayout() {
		fm = getFragmentManager();
		drawerMenu = (DrawerLayout) findViewById(R.id.drawer_menu);
		lvDrawerMenu = (ListView) findViewById(R.id.lv_album_menu);
		setDefaultFragment();
	}

	/**
	 * 初始化数据
	 */
	protected void initData() {
		//使菜单在系统栏之下展开
		lvDrawerMenu.getLayoutParams().width = ViewUtils.getScreenSize().x / 3 * 2;
		((ViewGroup.MarginLayoutParams) lvDrawerMenu.getLayoutParams()).setMargins(0, ViewUtils.getSystemBarHeight(this) - 2, 0, 0);
		//菜单适配器
		lvDrawerMenu.setAdapter(new DrawerMenuAdpater(this, DataLayer.drawerMenuImages, DataLayer.drawerMenuTexts));
	}

	/**
	 * 调整系统状态栏
	 */
	protected void initSystemBar() {
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.darkgrey2);
		changeScreenState(false);
	}

	/**
	 * 设置默认fragment
	 */
	protected void setDefaultFragment() {
		if (folderFragment == null) {
			folderFragment = new FolderFragment();
			// 获取并传递数据
			new LoadMediaDataTask().execute();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_album, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_settings:
				Toast.makeText(getApplicationContext(), getString(R.string.action_settings), Toast.LENGTH_SHORT).show();
				drawerMenu.openDrawer(GravityCompat.START);
				return true;
			case R.id.action_search:
				Toast.makeText(getApplicationContext(), getString(R.string.action_search), Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getApplicationContext(), DemoActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Subscribe
	public void onEventMainThread(PhotoEvent event) {
		if (pictureFragment == null) {
			pictureFragment = new PictureFragment();
		}
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("pics", (ArrayList<? extends Parcelable>) event.getPics());
		bundle.putInt("pos", event.getPosition());
		bundle.putString("folderName", event.getFolderName());
		pictureFragment.setArguments(bundle);

		switchContent(photosFragment, pictureFragment, "pic");
	}

	@Subscribe
	public void onEventMainThread(FolderEvent event){
		if (photosFragment == null) {
			photosFragment = new PhotosFragment();
		}
		//准备传递数据
		Bundle bundle = new Bundle();
		bundle.putParcelable("folder", event.getFolder());
		photosFragment.setArguments(bundle);

		switchContent(folderFragment, photosFragment, "photos");
	}

	@Subscribe
	public void onEventMainThread(StateEvent event){
		changeScreenState(event.isState());
	}

	/**
	 * 从一个fragment跳转到另一个
	 *
	 * @param from 来时
	 * @param to   去处
	 * @param tag  标记
	 */
	public void switchContent(Fragment from, Fragment to, String tag) {
		FragmentTransaction ft = fm.beginTransaction();
		// 先判断是否被add过
		if (!to.isAdded()) {
			// 隐藏当前的fragment，add下一个到Activity中
//			ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
			ft.hide(from).add(R.id.album_content, to, tag);
			ft.addToBackStack(null);
			ft.commit();
		} else {
			// 隐藏当前的fragment，show下一个
//			ft.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
			ft.hide(from).show(to);
			ft.commit();
		}
	}

	/**
	 * 获取图库信息
	 *
	 * @return List<Folder>    图库集
	 */
	protected List<Folder> getFolders() {
		//定义将要查询的列
		String[] columns = new String[]{"Distinct " + MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
		//获取数据游标
		Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, MediaStore.Images.Media.DISPLAY_NAME + " IS NOT NULL", null, MediaStore.Images.Media._ID);
		//得到索引
		int indexFolderId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID);
		int indexFolderName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
		//填充值
		List<Folder> folders = new ArrayList<Folder>();
		while (cursor.moveToNext()) {
			Folder folder = new Folder();
			folder.setFolderId(cursor.getLong(indexFolderId));
			folder.setFolderName(cursor.getString(indexFolderName));
			folder.setPhotos(getPhotos(folder.getFolderId()));
			folder.setImgCount(folder.getPhotos().size());
			setFolderCoverUris(folder);
			folders.add(folder);
		}
		cursor.close();
		return folders;
	}

	/**
	 * 得到图片信息（创建类集）
	 *
	 * @param folderId 文件夹Id
	 * @return List<Photo>  图片列表
	 */
	protected List<Photo> getPhotos(long folderId) {
		//定义将要查询的列
		String[] columns = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA, MediaStore.Images.Media.WIDTH, MediaStore.Images.Media.HEIGHT, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.DATE_MODIFIED};
		//获取数据游标
		Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, MediaStore.Images.Media.BUCKET_ID + " = ?", new String[]{String.valueOf(folderId)}, MediaStore.Images.Media._ID);
		//得到索引
		int indexPhotoId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
		int indexPhotoName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
		int indexPhotoPath = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		int indexPhotoWidth = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH);
		int indexPhotoHeight = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
		int indexPhotoSize = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
		int indexPhotoDateAdded = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
		int indexPhotoDateModified = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
		//填充值
		List<Photo> photos = new ArrayList<Photo>();
		while (cursor.moveToNext()) {
			Photo photo = new Photo();
			photo.setId(cursor.getLong(indexPhotoId));
			photo.setName(cursor.getString(indexPhotoName));
			photo.setPath(cursor.getString(indexPhotoPath));
			photo.setWidth(cursor.getInt(indexPhotoWidth));
			photo.setHeight(cursor.getInt(indexPhotoHeight));
			photo.setSize(cursor.getInt(indexPhotoSize));
			photo.setDateAdded(cursor.getLong(indexPhotoDateAdded));
			photo.setDateModified(cursor.getLong(indexPhotoDateModified));
			photos.add(photo);
		}
		cursor.close();
		return photos;
	}

	/**
	 * 文件夹封面图片
	 *
	 * @param folder 文件夹
	 */
	protected void setFolderCoverUris(Folder folder) {
		List<Photo> photos = folder.getPhotos();
		int size = photos.size();
		if (size > 4)
			size = 4;
		String[] uris = new String[size];
		for (int i = 0; i < size; i++)
			uris[i] = "file://" + photos.get(i).getPath();
		folder.setCoverUris(uris);
	}

	/**
	 * 切换全屏与非全屏状态
	 *
	 * @param state 标志
	 */
	protected void changeScreenState(boolean state) {
		if (state) {
			if (getWindow().getDecorView().getSystemUiVisibility() == FULLSCREEN_STATE) {
				getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
				tintManager.setStatusBarTintResource(R.color.darkgrey2);
				getActionBar().show();
			} else {
				getActionBar().hide();
				getWindow().getDecorView().setSystemUiVisibility(FULLSCREEN_STATE);
				tintManager.setStatusBarTintResource(R.color.transparent);
			}
		} else {
			tintManager.setStatusBarTintResource(R.color.darkgrey2);
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			getActionBar().show();
		}
	}


	/**
	 * 加载查询任务
	 */
	private class LoadMediaDataTask extends AsyncTask<Void, Void, List<Folder>> {

		@Override
		protected List<Folder> doInBackground(Void... params) {
			return getFolders();
		}

		@Override
		protected void onPostExecute(List<Folder> folders) {
			Bundle bundle = new Bundle();
			bundle.putParcelableArrayList("folders", (ArrayList<? extends Parcelable>) folders);
			folderFragment.setArguments(bundle);
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.album_content, folderFragment, "folders");
			ft.commit();
		}
	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
}
