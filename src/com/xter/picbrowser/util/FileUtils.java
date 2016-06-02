package com.xter.picbrowser.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by XTER on 2015/12/14.
 * 专注文件操作
 */
public class FileUtils {

	/* 拦截某类型文件的作用比较强 */
	public static FilenameFilter getImageFilter() {
		FilenameFilter filter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				int index = name.lastIndexOf(".");
				if (index > 1) {
					String suffix = name.substring(index);
					if (suffix.equalsIgnoreCase(".gif") || suffix.equalsIgnoreCase(".jpeg") || suffix.equalsIgnoreCase(".jpg")
							|| suffix.equalsIgnoreCase(".png") || suffix.equalsIgnoreCase(".bmp"))
						return true;
				} else {
					if (dir.isDirectory())
						return true;
					else
						return false;
				}
				return false;
			}
		};
		return filter;
	}

	public static FileFilter getFilter() {
		FileFilter filter = new FileFilter() {

			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else {
					int index = file.getName().lastIndexOf(".");
					if (index > 1) {
						String suffix = file.getName().substring(index);
						if (suffix.equalsIgnoreCase(".gif") || suffix.equalsIgnoreCase(".jpeg") || suffix.equalsIgnoreCase(".jpg")
								|| suffix.equalsIgnoreCase(".png") || suffix.equalsIgnoreCase(".bmp"))
							return true;
						else
							return false;
					} else
						return false;
				}
			}
		};
		return filter;
	}


	/* 获取一文件目录 */
	public static String getParentFolder(String url) {
		String[] strs = url.split("\\" + File.separator);
		return strs[strs.length - 2];
	}

	public static String getFolder(String url) {
		return url.substring(url.lastIndexOf(File.separator) + 1);
	}

	public static void close(InputStream is){
		if(is!=null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(OutputStream os){
		if(os!=null){
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
