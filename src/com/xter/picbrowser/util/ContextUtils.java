package com.xter.picbrowser.util;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;

/**
 * Created by XTER on 2015/10/14.
 * 全局单例
 */
public class ContextUtils extends Application {
	/* 获取context */
	private static ContextUtils instance;

	public static ContextUtils getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		initConfig();
	}

	protected void initConfig() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(this)
				.memoryCacheExtraOptions(480, 320)
				.threadPoolSize(3)//线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new WeakMemoryCache())
//				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
//				.memoryCacheSize(5 * 1024 * 1024)
//				.diskCacheSize(50 * 1024 * 1024)
				.diskCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(getApplicationContext(), "img/cache")))
//				.diskCacheFileCount(100)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(this, 5 * 1000, 10 * 1000))
//				.writeDebugLogs()
				.build();//开始构建
		ImageLoader.getInstance().init(config);
	}

}
