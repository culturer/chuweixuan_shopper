package com.culturer.guishi_shoper.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.culturer.guishi_shoper.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

/**
 * Created by Administrator on 2018/7/27 0027.
 */

public class MediaLoader implements AlbumLoader {
	@Override
	public void load(ImageView imageView, AlbumFile albumFile) {
		load(imageView, albumFile.getPath());
	}
	
	@Override
	public void load(ImageView imageView, String url) {
		Glide.with(imageView.getContext())
				.load(url)
				.into(imageView);
	}
}
