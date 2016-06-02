package com.xter.picbrowser.data;

import com.xter.picbrowser.R;
import com.xter.picbrowser.util.ContextUtils;

/**
 * Created by XTER on 2016/1/31.
 */
public class DataLayer {
	public static int[] drawerMenuImages = {R.mipmap.drawer_menu_folders,R.mipmap.drawer_menu_time,R.mipmap.drawer_menu_settings};
	public static String[] drawerMenuTexts = ContextUtils.getInstance().getResources().getStringArray(R.array.menu_text);
}
