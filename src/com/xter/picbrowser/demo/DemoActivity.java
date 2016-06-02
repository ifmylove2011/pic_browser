package com.xter.picbrowser.demo;

import com.xter.picbrowser.R;
import com.xter.picbrowser.util.ImageLoader;
import com.xter.picbrowser.view.CascadeAlbum;
import com.xter.picbrowser.view.CascadeView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DemoActivity extends Activity {

	CascadeAlbum ca;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_layout);
		initLayout();
		initData();
	}

	protected void initLayout() {
	}

	protected void initData(){
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_demo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
