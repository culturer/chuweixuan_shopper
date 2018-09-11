package com.culturer.guishi_shoper;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.culturer.guishi_shoper.utils.MediaLoader;
import com.culturer.guishi_shoper.utils.PreferenceUtil;
import com.kymjs.okhttp3.OkHttpStack;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.http.RequestQueue;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

public class BaseApplication extends Application {
	
	public static final String HOST = "http://192.168.1.110:8080";
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}
	
	private void init(){
		//初始化Preference工具
		PreferenceUtil.init(this);
		//使用okhttp代替httpurlconnection
		RxVolley.setRequestQueue(RequestQueue.newRequestQueue(RxVolley.CACHE_FOLDER, new OkHttpStack(new OkHttpClient())));
		//初始化相册
		Album.initialize(AlbumConfig.newBuilder(this)
				.setAlbumLoader(new MediaLoader())
				.build());
	}
	
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		//解决方法数超65535的问题
		//https://www.jianshu.com/p/d714cf9a9b54
		MultiDex.install(this);
	}
}
