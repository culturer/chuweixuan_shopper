package com.culturer.guishi_shoper.pages.sale_after.toushu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.culturer.guishi_shoper.R;

/**
 * Created by Administrator on 2018/7/10 0010.
 */

public class ToushuAdapter extends BaseAdapter {
	
	private Context context;
	
	public ToushuAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return 10;
	}
	
	@Override
	public Object getItem(int i) {
		return null;
	}
	
	@Override
	public long getItemId(int i) {
		return 0;
	}
	
	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		View contentView = LayoutInflater.from(context).inflate(R.layout.toushu_item,null);
		return contentView;
	}
}
